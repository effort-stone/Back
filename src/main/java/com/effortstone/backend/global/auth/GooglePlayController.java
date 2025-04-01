package com.effortstone.backend.global.auth;

import com.effortstone.backend.global.common.GoogleDto;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class GooglePlayController {

    private final GooglePlayService googlePlayService;


//    /**
//     * 인앱 구매 검증 API 엔드포인트.
//     * GET 요청으로 packageName, productId, purchaseToken을 전달받아 구매 정보를 검증합니다.
//     *
//     * 예시 요청: GET /api/googleplay/verifyPurchase?packageName=com.example.app&productId=product_001&purchaseToken=abcdefg
//     *
//     * @param googleDto 구매한 제품의 ID
//     * @param googleDto 구매 토큰
//     * @return 검증된 구매 정보(ProductPurchase 객체) 또는 오류 메시지
//     */
//    @PostMapping("/verifyPurchase")
//    public ResponseEntity<?> verifyPurchase(
//            GoogleDto googleDto) {
//        try {
//            ProductPurchase purchase = googlePlayService.getProductPurchase(googleDto);
//            System.out.println("🔹 purchase : " + purchase.toString());
//            return ResponseEntity.ok(purchase);
//        } catch (Exception e) {
//            // 실제 운영 환경에서는 로그를 남기고 더 상세한 예외 처리를 할 수 있습니다.
//            System.out.println("💢 삐익 에러입니다. " +  e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error verifying purchase: " + e.getMessage());
//        }
//    }


    // 간단테스트용
    @PostMapping("/verifyPurchase")
    public ResponseEntity<?> verifyPurchase(@RequestBody  Map<String, String> requestParams) {
        System.out.println("🔹 #####requestParams : " + requestParams.toString());
        try {
            // 요청 파라미터 Map에서 직접 값 조회
            String productId = requestParams.get("productId");
            String purchaseToken = requestParams.get("purchaseToken");

            // 필요한 경우 다른 파라미터도 처리
            GoogleDto googleDto = new GoogleDto();
            googleDto.setProductId(productId);
            googleDto.setPurchaseToken(purchaseToken);

            ProductPurchase purchase = googlePlayService.getProductPurchase(googleDto);
            System.out.println("🔹 purchase : " + purchase.toString());
            return ResponseEntity.ok(purchase);
        } catch (Exception e) {
            System.out.println("💢 삐익 에러입니다. " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error verifying purchase: " + e.getMessage());
        }
    }

}
