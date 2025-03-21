package com.effortstone.backend.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // User
    USER_REGISTER_CHECK_SUCCESS(OK, "회원가입 여부 확인 성공"),
    USER_REGISTER_CHECK_EMAIL_SUCCESS(OK, "이메일 중복 확인 성공"),
    USER_REGISTER_CHECK_ID_SUCCESS(OK, "아이디 중복 확인 성공"),
    USER_REGISTER_SUCCESS(CREATED, "회원가입 성공"),
    USER_LOGIN_SUCCESS(OK, "로그인 성공"),
    USER_FIND_ID_SUCCESS(OK, "아이디 찾기 성공"),
    USER_REISSUE_PWD_SUCCESS(OK, "비밀번호 재발급 성공"),
    USER_LOGOUT_SUCCESS(OK, "로그아웃 성공"),
    USER_GET_INFO_SUCCESS(OK, "회원 정보 조회 성공"),
    USER_UPDATE_INFO_SUCCESS(OK, "회원 정보 수정 성공"),
    USER_GET_JOB_INFO_SUCCESS(OK, "직업 정보 조회 성공"),
    USER_UPDATE_JOB_INFO_SUCCESS(OK, "직장 정보 수정 성공"),
    SEND_EMAIL_CODE_SUCCESS(OK, "이메일 인증 코드 전송 성공"),
    USER_REISSUE_SUCCESS(OK, "토큰 재발급 성공"),
    SEND_EMAIL_TEMP_PASSWORD_SUCCESS(OK, "임시 비밀번호 발급 성공"),
    SEND_SMS_SUCCESS(OK, "휴대폰 인증번호 발송 성공"),
    SEND_VERIFY_SUCCESS(OK, "휴대폰 인증번호 검증 성공"),
    USER_DAILY_AMOUNT_CHECK_SUCCESS(OK, "사용자 일일 이체 요금 조회 성공"),
    USER_GET_ALL_SUCCESS(OK,"모든사용자조회성공"),
    USER_CREATE_SUCCESS(CREATED, "유저생성 성공"), 
    USER_UPDATE_SUCCESS(OK, "유저정보 업데이트 성공"), 
    USER_DELETE_SUCCESS(OK, "유저정보 삭제 성공"),
    USER_CALENDER_SEARCH_SUCCESS(OK,"캘린더 정보조회 성공" ), 

    // DIARY
    DIARY_SEARCH_ALL(OK,"다이어리전체조회" ),
    DIARY_DELETE_OK(OK,"다이어리삭제" ), 
    DIARY_SEARCH_CODE(OK,"특정 다이어리 조회 성공" ),
    DIARY_CREATE_OK(OK, "다이어리 생성 성공" ),
    DIARY_UPDATE_OK(OK,"다이어리 업데이트 성공" ),

    // TODO
    TODO_LIST_FETCH_SUCCESS(OK, "투두 목록 조회 성공"),
    TODO_FETCH_SUCCESS(OK, "투두 조회 성공"),
    TODO_CREATE_SUCCESS(OK, "투두 생성 성공"),
    TODO_UPDATE_SUCCESS(OK, "투두 수정 성공"),
    TODO_DELETE_SUCCESS(OK, "투두 삭제 성공"),

    // ROUTINE
    ROUTINE_LIST_FETCH_SUCCESS(OK, "루틴 목록 조회 성공"),
    ROUTINE_FETCH_SUCCESS(OK, "루틴 조회 성공"),
    ROUTINE_USER_LIST_FETCH_SUCCESS(OK, "사용자 루틴 목록 조회 성공"),
    ROUTINE_CREATE_SUCCESS(OK, "루틴 생성 성공"),
    ROUTINE_UPDATE_SUCCESS(OK, "루틴 수정 성공"),
    ROUTINE_DELETE_SUCCESS(OK, "루틴 삭제 성공"),
    ROUTINE_CALENDAR_FETCH_SUCCESS(OK, "월간 캘린더 조회 성공"),

    // STONE
    STONE_CREATE_SUCCESS(OK, "돌 생성 성공"),
    STONE_FETCH_SUCCESS(OK, "돌 조회 성공"),
    STONE_UPDATE_SUCCESS(OK, "돌 수정 성공"),
    STONE_DELETE_SUCCESS(OK, "돌 삭제 성공"),

    // STONE_WEARABLE_ITEM
    STONE_WEARABLE_ITEM_ACQUIRE_SUCCESS(OK,"아이템 획득에 성공했습니다."),
    STONE_WEARABLE_ITEM_EQUIP_SUCCESS(OK,"아이템 장착에 성공했습니다."),
    STONE_WEARABLE_ITEM_UNEQUIP_SUCCESS(OK,"아이템 장착 해제에 성공했습니다."),
    STONE_WEARABLE_ITEM_OWNED_FETCH_SUCCESS(OK,"보유한 아이템 조회에 성공했습니다."),
    STONE_WEARABLE_ITEM_EQUIPPED_FETCH_SUCCESS(OK,"장착한 아이템 조회에 성공했습니다."),
    STONE_WEARABLE_ITEM_DELETE_SUCCESS(OK,"아이템 삭제에 성공했습니다."),

    // ITEM
    ITEM_CREATE_SUCCESS(OK,"Item created successfully."),
    ITEM_FETCH_SUCCESS(OK,"Item fetched successfully."),
    ITEM_LIST_FETCH_SUCCESS(OK,"Item list fetched successfully."),
    ITEM_UPDATE_SUCCESS(OK,"Item updated successfully."),
    ITEM_DELETE_SUCCESS(OK,"Item deleted successfully."),

    // APP
    APP_INITIAL_FETCH_SUCCESS(OK,"초기정보조회에 성공했습니다."),
    APP_SUCCESS_SERVER_CHECK(OK,"서버점검중입니다."),
    APP_VERSION_UPDATE(OK,"앱 업데이트 필요합니다."),
    APP_SUCCESS_SERVER(OK,"앱 성공."),


    //SUBSCRIPTION
    SUBSCRIPTION_GET_SUCCESS(OK,"상품 조회 성공했습니다." ),

    // ROUTINE_PROGRESS
    ROUTINE_PROGRESS_UPDATE_SUCCESS(OK,"루틴 상세 수정 성공" ), 
    ROUTINE_PROGRESS_DELETE_SUCCESS(OK,"루틴 상세 삭제 성공" ),
    ITEM_GET_SUCCESS(OK, "아이템획득성공" ),
    ACCOUNT_LINK_DATA_SUCCESS(OK,"계정 연동 및 데이터 조회에 성공했습니다." );





    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusValue() {
        return httpStatus.value();
    }
}
