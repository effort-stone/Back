package com.effortstone.backend.global.auth;

import com.effortstone.backend.global.common.GoogleDto;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.ProductPurchase;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GooglePlayService {
    // 애플리케이션 이름 설정 (API 호출 시 사용됨)
    private static final String APPLICATION_NAME = "effort-stone";
    // JSON 처리를 위한 Jackson 기반의 JsonFactory 인스턴스 생성
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    // 서비스 계정 키 파일의 파일명. 이 파일은 클래스패스에 존재해야 함.
    private static final String SERVICE_ACCOUNT_KEY_FILE = "effort-stone-service-account.json";

    /**
     * Google Play AndroidPublisher 클라이언트를 생성하여 반환합니다.
     *
     * @return 인증된 AndroidPublisher 클라이언트
     * @throws GeneralSecurityException 보안 관련 예외 발생 시
     * @throws IOException IO 관련 예외 발생 시
     */
    public AndroidPublisher getAndroidPublisher() throws GeneralSecurityException, IOException {
        // 클래스 로더를 통해 클래스패스에서 서비스 계정 JSON 파일을 읽어들임
        InputStream serviceAccountStream = getClass().getClassLoader().getResourceAsStream(SERVICE_ACCOUNT_KEY_FILE);
        // 파일을 찾지 못한 경우 예외를 발생시킴
        if (serviceAccountStream == null) {
            throw new FileNotFoundException("Resource not found: " + SERVICE_ACCOUNT_KEY_FILE);
        }

        // JSON 파일로부터 서비스 계정 정보를 읽어 GoogleCredentials 객체를 생성
        // 이후 AndroidPublisher API 호출에 필요한 스코프를 추가함
        GoogleCredentials credentials = ServiceAccountCredentials
                .fromStream(serviceAccountStream)
                .createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER));

        // 안전한 HTTP 통신을 위한 트랜스포트, JSON 처리, 인증 정보를 포함하여 AndroidPublisher 클라이언트를 빌드
        return new AndroidPublisher.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),  // 안전한 HTTP 전송을 위한 트랜스포트 생성
                JSON_FACTORY,                                    // JSON 처리 객체 사용
                new HttpCredentialsAdapter(credentials))         // 인증 정보 어댑터 적용
                .setApplicationName(APPLICATION_NAME)             // 애플리케이션 이름 설정
                .build();                                         // AndroidPublisher 인스턴스 생성 후 반환
    }

    /**
     * 인앱 구매 검증 메서드.
     *
     * @param googleDto 인앱 구매 관련 정보를 담은 객체 (패키지명, 제품ID, 구매 토큰 포함)
     * @return Google Play에서 반환된 구매 정보(ProductPurchase 객체)
     * @throws IOException IO 관련 예외 발생 시
     * @throws GeneralSecurityException 보안 관련 예외 발생 시
     */
    public ProductPurchase getProductPurchase(GoogleDto googleDto)
            throws IOException, GeneralSecurityException {
        // 앱의 패키지 이름 (Google Play Developer Console에서 확인 가능)
        String packageName = "com.goodday.effortStone";

        // 인증된 AndroidPublisher 클라이언트 생성
        AndroidPublisher publisher = getAndroidPublisher();

        // 전달받은 googleDto 객체의 내용을 로그에 출력하여 디버깅에 활용
        System.out.println("$$$$$$$$$$$$$" + googleDto.toString());

        // 인앱 구매 검증 요청을 생성
        // - packageName: 앱 패키지명
        // - googleDto.getProductId(): 인앱 상품의 고유 식별자 (SKU)
        // - googleDto.getPurchaseToken(): 구매가 유효함을 증명하는 토큰
        AndroidPublisher.Purchases.Products.Get request = publisher.purchases().products().get(
                packageName,
                googleDto.getProductId(),
                googleDto.getPurchaseToken()
        );

        // API 요청을 실행하여 구매 정보를 받아오고 반환
        return request.execute();
    }
}

