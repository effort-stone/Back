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
    private static final String APPLICATION_NAME = "effort-stone";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    // 실제 서비스 계정 키 파일의 경로로 수정하세요.
    //private static final String SERVICE_ACCOUNT_KEY_FILE = "src/main/resources/effort-stone-service-account.json";

    private static final String SERVICE_ACCOUNT_KEY_FILE = "effort-stone-service-account.json";

    public AndroidPublisher getAndroidPublisher() throws GeneralSecurityException, IOException {
        // 클래스 로더를 통해 클래스패스 리소스로부터 JSON 파일을 읽습니다.
        InputStream serviceAccountStream = getClass().getClassLoader().getResourceAsStream(SERVICE_ACCOUNT_KEY_FILE);
        if (serviceAccountStream == null) {
            throw new FileNotFoundException("Resource not found: " + SERVICE_ACCOUNT_KEY_FILE);
        }

        GoogleCredentials credentials = ServiceAccountCredentials
                .fromStream(serviceAccountStream)
                .createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER));

        return new AndroidPublisher.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    /**
     * 인앱 구매 검증.
     * @param googleDto 앱의 패키지 이름
     * @param googleDto 구매한 제품의 ID
     * @param googleDto 구매 토큰
     * @return 구매 정보(ProductPurchase 객체)
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public ProductPurchase getProductPurchase(GoogleDto googleDto)
            throws IOException, GeneralSecurityException {
        String packageName = "com.goodday.effortStone";
        AndroidPublisher publisher = getAndroidPublisher();
        AndroidPublisher.Purchases.Products.Get request = publisher.purchases().products().get(packageName, googleDto.getProductId(),googleDto.getPurchaseToken());
        return request.execute();
    }
}
