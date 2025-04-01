package com.effortstone.backend.global.auth;

import com.effortstone.backend.global.common.IosDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
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


    /**
     * iOS 영수증 검증 메서드
     *
     * @param iosDto 클라이언트에서 전달받은 영수증 데이터(Base64 인코딩된 receipt data)
     * @return iOS 구매 내역 응답 DTO
     */
    public  Map<String, Object> verifyReceipt(IosDto iosDto) {
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
        return response;
    }
}
