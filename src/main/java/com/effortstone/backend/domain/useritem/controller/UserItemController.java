package com.effortstone.backend.domain.useritem.controller;


import com.effortstone.backend.domain.useritem.dto.response.UserItemResponseDto;
import com.effortstone.backend.domain.useritem.service.UserItemService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/UserItem")
public class UserItemController {

    private final UserItemService userItemService;

    /**
     * 사용자가 아이템을 획득하는 API
     */
    @PostMapping("/{itemId}")
    public ResponseEntity<ApiResponse<String>> acquireItem(
            @PathVariable Long itemId) {
        userItemService.acquireItem(itemId);
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.STONE_WEARABLE_ITEM_ACQUIRE_SUCCESS, "아이템 획득에 성공했습니다."));
    }


    /**
     * 사용자가 보유한 아이템 목록을 조회하는 API
     */
    @GetMapping("/{stoneId}/items")
    public ResponseEntity<ApiResponse<List<UserItemResponseDto>>> getOwnedItems(
            @PathVariable Long stoneId) {
        List<UserItemResponseDto> response = userItemService.getOwnedItems();
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.STONE_WEARABLE_ITEM_OWNED_FETCH_SUCCESS, response));
    }


}
