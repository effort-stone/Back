package com.effortstone.backend.domain.item.controller;


import com.effortstone.backend.domain.item.dto.request.ItemRequestDto;
import com.effortstone.backend.domain.item.dto.response.ItemResponseDto;
import com.effortstone.backend.domain.item.service.ItemService;
import com.effortstone.backend.domain.user.service.UserService;
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
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    /**
     * 아이템 생성 API
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDto>> createItem(@RequestBody ItemRequestDto requestDto) {
        ItemResponseDto responseDto = itemService.createItem(requestDto);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ITEM_CREATE_SUCCESS, responseDto));
    }

    /**
     * 아이템 단건 조회 API
     */
    @GetMapping("/{itemCode}")
    public ResponseEntity<ApiResponse<ItemResponseDto>> getItem(@PathVariable Long itemCode) {
        ItemResponseDto responseDto = itemService.getItem(itemCode);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ITEM_FETCH_SUCCESS, responseDto));
    }

    /**
     * 전체 아이템 목록 조회 API
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ItemResponseDto>>> getAllItems() {
        List<ItemResponseDto> responseList = itemService.getAllItems();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ITEM_LIST_FETCH_SUCCESS, responseList));
    }

    /**
     * 아이템 수정 API
     */
    @PutMapping("/{itemCode}")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItem(
            @PathVariable Long itemCode,
            @RequestBody ItemRequestDto requestDto) {
        ItemResponseDto responseDto = itemService.updateItem(itemCode, requestDto);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ITEM_UPDATE_SUCCESS, responseDto));
    }

    /**
     * 아이템 삭제 API
     */
    @DeleteMapping("/{itemCode}")
    public ResponseEntity<ApiResponse<String>> deleteItem(@PathVariable Long itemCode) {
        itemService.deleteItem(itemCode);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ITEM_DELETE_SUCCESS, "Item deleted successfully"));
    }

}
