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
import lombok.Data;
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
import java.util.Comparator;
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

    @Data
    public static class IosDtoWrapper {
        private List<IosDto> purchaseToken;
    }

    /**
     * iOS 영수증 검증 메서드
     *
     * @param iosDtos 클라이언트에서 전달받은 영수증 데이터(Base64 인코딩된 receipt data)
     * @return iOS 구매 내역 응답 DTO
     */
    public ApiResponse<List<SubscriptionResponseDto>> verifyReceipt(List<IosDto> iosDtos) {


        // 가장 최근 내역만 가져옴
        IosDto iosDto = iosDtos.get(iosDtos.size()-1);
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

        System.out.println("💟💟💟 검증 요청값입니당"+response);
        Map<String, Object> receipt = (Map<String, Object>) response.get("receipt");

        User user = userRepository.findById(SecurityUtil.getCurrentUserCode()).orElseThrow();

        // in_app 저장
        List<Map<String, Object>> inAppList = (List<Map<String, Object>>) receipt.get("in_app");

        Object autoRenewObj = ((List<Map<String, Object>>) response.get("pending_renewal_info"))
                .get(0)
                .get("auto_renew_status");
        List<SubscriptionPurchases> toSave = inAppList.stream()
                .filter(item -> {
                    String orderId = String.valueOf(item.get("web_order_line_item_id"));
                    return !subscriptionPurchasesRepository.existsByOrderId(orderId);
                })
                .map(item -> {
                    SubscriptionPurchases purchase = new SubscriptionPurchases();
                    boolean autoRenewing = 1 == Integer.parseInt(String.valueOf(autoRenewObj));
                    purchase.setAutoRenewing(autoRenewing);
                    String orderId = String.valueOf(item.get("web_order_line_item_id"));
                    purchase.setOrderId(orderId);
                    purchase.setStartTime(
                            Instant.ofEpochMilli(Long.parseLong(String.valueOf(item.get("purchase_date_ms"))))
                                    .atZone(ZoneId.of("Asia/Seoul"))
                                    .toLocalDateTime()
                    );
                    purchase.setExpiryTime(
                            Instant.ofEpochMilli(Long.parseLong(String.valueOf(item.get("expires_date_ms"))))
                                    .atZone(ZoneId.of("Asia/Seoul"))
                                    .toLocalDateTime()
                    );
                    purchase.setUser(user);
                    purchase.setSource("app_store");
                    return purchase;
                })
                .toList();
        // 저장
        try {
            List<SubscriptionPurchases> savedEntities = subscriptionPurchasesRepository.saveAll(toSave);
            try {
                List<SubscriptionPurchases> savedUserEntities = subscriptionPurchasesRepository.findAllByUser(user);
                System.out.println("💟💟💟 새로운 리턴값 이였습니당."+savedUserEntities);
            }catch (Exception e){
                throw new RuntimeException();
            }
            System.out.println("💟💟💟 ㅊ초반 리턴값 이였습니당."+savedEntities);

            // 응답 DTO로 변환
            List<SubscriptionResponseDto> srdDtoList = savedEntities.stream()
                    .sorted(Comparator.comparing(SubscriptionPurchases::getExpiryTime)) // 오름차순
                    .map(SubscriptionResponseDto::fromEntity)
                    .toList();
            return ApiResponse.success(SuccessCode.SUBSCRIPTION_PURCHASE_SUCCESS,srdDtoList);
        }catch (Exception e ){
            throw new RuntimeException();
        }
    }
}
