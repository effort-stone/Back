package com.effortstone.backend.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int status;
    private final String message;
    @JsonInclude(NON_NULL)
    private T data;

    private ApiResponse() {
        throw new IllegalStateException();
    }

    // 성공 여부만 중요한 값
    public static <T> ApiResponse<T> success(SuccessCode success) {
        return new ApiResponse<>(success.getHttpStatusValue(), success.getMessage());
    }

    // 성공에 데이터도 필요한 값
    public static <T> ApiResponse<T> success(SuccessCode success, T data){
        return new ApiResponse<>(success.getHttpStatusValue(), success.getMessage(), data);
    }
}
