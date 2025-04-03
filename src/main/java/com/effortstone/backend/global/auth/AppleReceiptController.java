package com.effortstone.backend.global.auth;

import com.effortstone.backend.domain.subscriptionpurchase.dto.Response.SubscriptionResponseDto;
import com.effortstone.backend.global.common.IosDto;
import com.effortstone.backend.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ios")
@RequiredArgsConstructor
public class AppleReceiptController {

    private final AppleReceiptService appleReceiptService;

    @PostMapping("/verifyPurchase")
    public ResponseEntity<?> verifyPurchase(@RequestBody IosDto requestDto) {
        //System.out.println("IosDto는 어떻게 생겻을까"+requestDto.toString());
        try {
            ApiResponse<List<SubscriptionResponseDto>> response = appleReceiptService.verifyReceipt(requestDto);
            //System.out.println("과연리스폰스가 나올까"+response.toString());
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            // 실제 운영 환경에서는 로그를 남기고 더 상세한 예외 처리를 할 수 있습니다.
            System.out.println("💢 삐익 에러입니다. " +  e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error Ios verifying purchase: " + e.getMessage());
        }

    }
}
