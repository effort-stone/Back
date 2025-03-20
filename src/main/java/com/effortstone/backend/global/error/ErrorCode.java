package com.effortstone.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // User
    USER_NOT_FOUND("E100", "존재하지 않는 회원입니다."),
    USER_LOGIN_INCORRECT("E101", "아이디 또는 비밀번호가 다릅니다."),
    USER_REGISTER_VALIDATION_FAIL("E102", "비밀번호 또는 이메일 형식이 올바르지 않습니다."),
    USER_LOGOUT_ACCESS_TOKEN_VALIDATION_FAIL("E103", "잘못된 요청입니다."),
    USER_TRSF_LIMIT_NOT_FOUND("E104", "사용자 일일 이체 한도 조회 불가"),
    USER_AUTHENTICATION_FAIL("E105", "사용자 인증 실패"),
    USER_GET_INFO_FAIL("E106", "회원 정보 조회 실패"),
    USER_UPDATE_INFO_FAIL("E107", "회원 정보 수정 실패"),
    USER_GET_JOB_INFO_FAIL("E108", "직업 정보 조회 실패"),
    USER_UPDATE_JOB_INFO_FAIL("E109", "직업 정보 수정 실패"),
    SEND_EMAIL_CODE_FAIL("E110", "이메일 인증 코드 전송 실패"),
    USER_ACCESS_FAIL("E111", "사용자 인가 실패"),
    USER_REISSUE_FAIL("E112", "토큰 재발급 실패"),
    SEND_EMAIL_TEMP_PASSWORD_FAIL("E113", "임시 비밀번호 발급 실패"),
    SMS_SEND_FAIL("E114", "휴대폰 인증번호 발송 실패"),
    SMS_VERIFY_FAIL("E115", "휴대폰 인증번호 검증 실패"),
    USER_ADMIN_LOGIN_FAIL("E116", "관리자 권한이 없는 계정입니다."),
    ITEM_NOT_FOUND("E117", "아이템이 없습니다." ),
    ITEM_NOT_OWNED("E118", "사용자가 착용 중인 아이템이 아닙니다." );

    private final String code;
    private final String message;
}
