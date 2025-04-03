package com.effortstone.backend.global.auth;

import com.effortstone.backend.domain.subscriptionpurchase.dto.Response.SubscriptionResponseDto;
import com.effortstone.backend.domain.subscriptionpurchase.entity.SubscriptionPurchases;
import com.effortstone.backend.domain.subscriptionpurchase.repository.SubscriptionPurchasesRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.common.IosDto;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import com.effortstone.backend.global.error.ErrorCode;
import com.effortstone.backend.global.error.exception.CustomException;
import com.effortstone.backend.global.security.SecurityUtil;
import com.google.api.services.androidpublisher.AndroidPublisher;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AppleReceiptService {
    // Apple의 영수증 검증 URL (생산/샌드박스)
    private static final String APPLE_PRODUCTION_VERIFY_URL = "https://buy.itunes.apple.com/verifyReceipt";
    private static final String APPLE_SANDBOX_VERIFY_URL = "https://sandbox.itunes.apple.com/verifyReceipt";

    // iOS 인앱 정기 결제(구독) shared secret (설정 파일 혹은 환경변수에서 관리)

    @Value(value = "${apple.shared.secret}")
    private String appleSharedSecret;

    private final RestTemplate restTemplate = new RestTemplate(); // ✅ 바로 사용 OK
    @Autowired
    private SubscriptionPurchasesRepository subscriptionPurchasesRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     * iOS 영수증 검증 메서드
     *
     * @param iosDto 클라이언트에서 전달받은 영수증 데이터(Base64 인코딩된 receipt data)
     * @return iOS 구매 내역 응답 DTO
     */
    public  ApiResponse<SubscriptionResponseDto> verifyReceipt(IosDto iosDto) {
        // 요청 페이로드 준비: 영수증 데이터, shared secret, (옵션) 오래된 트랜잭션 제외 여부
        Map<String, Object> payload = new HashMap<>();
        payload.put("receipt-data", iosDto.getPurchaseToken());
        payload.put("password", appleSharedSecret);
        payload.put("exclude-old-transactions", true);

        Map<String, Object> response;

        try {
            // 우선 생산(Production) URL로 요청
            response = restTemplate.postForObject(APPLE_PRODUCTION_VERIFY_URL, payload, Map.class);
            // 응답의 status 값이 21007이면, 이는 샌드박스 영수증임을 의미하므로 샌드박스 URL로 재요청
            if (response != null && response.get("status") != null &&
                    Integer.parseInt(response.get("status").toString()) == 21007) {
                response = restTemplate.postForObject(APPLE_SANDBOX_VERIFY_URL, payload, Map.class);
            }
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("iOS 영수증 검증 중 오류 발생: " + ex.getMessage(), ex);
        }

        Map<String, Object> receipt = (Map<String, Object>) response.get("receipt");
        List<Map<String, Object>> inAppList = (List<Map<String, Object>>) receipt.get("in_app");
        List<Map<String, Object>> inAppTimeList = (List<Map<String, Object>>) response.get("latest_receipt_info");

        System.out.println("❤❤전체 영수증"+response);
        System.out.println("💨💨in_app 영수증"+inAppList);
        System.out.println("💦💦최근 트랜잭션 정보"+inAppTimeList);


        Map<String, Object> latest = inAppList.get(0);
        //Map<String, Object> lastInApp = inAppList.get(inAppList.size() - 1);
        Map<String, Object> latestTime = inAppTimeList.get(0);

        String startMs = (String) latest.get("purchase_date_ms");
        String expiryMs = (String) latest.get("expires_date_ms");
        String startMsTime = (String) latestTime.get("purchase_date_ms");
        String expiryMsTime = (String) latestTime.get("expires_date_ms");

        ZoneId seoulZone = ZoneId.of("Asia/Seoul");

        LocalDateTime startTime = Instant.ofEpochMilli(Long.parseLong(startMs))
                .atZone(seoulZone)
                .toLocalDateTime();
        LocalDateTime startTimeTime = Instant.ofEpochMilli(Long.parseLong(startMsTime))
                .atZone(seoulZone)
                .toLocalDateTime();

        LocalDateTime expiryTime = Instant.ofEpochMilli(Long.parseLong(expiryMs))
                .atZone(seoulZone)
                .toLocalDateTime();


        System.out.println("UTC 기준 시간: " + Instant.ofEpochMilli(Long.parseLong(startMs)));
        System.out.println("UTC 기준 시간 새롭게: " + Instant.ofEpochMilli(Long.parseLong(startMsTime)));
        System.out.println("서울 시간: " + startTime); // ZonedDateTime 또는 LocalDateTime
        System.out.println("서울 시간 새롭게: " + startTimeTime); // ZonedDateTime 또는 LocalDateTime

        User user = userRepository.findById(SecurityUtil.getCurrentUserCode()).orElseThrow();

        // Google API의 SubscriptionPurchase 정보를 DB 엔티티로 매핑
        SubscriptionPurchases entity = new SubscriptionPurchases();
        entity.setAutoRenewing("true".equals(String.valueOf(response.get("auto_renew_status"))));
        entity.setOrderId((String) latest.get("transaction_id"));
        entity.setStartTime(startTime);
        entity.setExpiryTime(expiryTime);
        entity.setSource("app_store");
        entity.setUser(user);

        // 엔티티 DB 저장
        try{
            SubscriptionPurchases savedEntity = subscriptionPurchasesRepository.save(entity);
            // 저장된 엔티티를 DTO로 변환
            SubscriptionResponseDto srdDto = SubscriptionResponseDto.fromEntity(savedEntity);
            return ApiResponse.success(SuccessCode.SUBSCRIPTION_PURCHASE_SUCCESS,srdDto);
        }catch (DuplicateKeyException e){
            throw new CustomException(ErrorCode.IS_SUBSCRIPTION_PURCHASE);
        }catch (Exception e ){
            throw new RuntimeException();
        }
    }
}
