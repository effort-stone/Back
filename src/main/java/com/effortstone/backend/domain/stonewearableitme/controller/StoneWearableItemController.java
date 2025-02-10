package com.effortstone.backend.domain.stonewearableitme.controller;


import com.effortstone.backend.domain.stonewearableitme.dto.response.StoneWearableItemDto;
import com.effortstone.backend.domain.stonewearableitme.service.StoneWearableItemService;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/StoneWearableItem")
public class StoneWearableItemController {

    private final StoneWearableItemService stoneWearableItemService;

    /**
     * 사용자가 아이템을 획득하는 API
     */
    @PostMapping("/{stoneId}/acquire/{itemId}")
    public ResponseEntity<ApiResponse<String>> acquireItem(
            @PathVariable Long stoneId,
            @PathVariable Long itemId) {
        stoneWearableItemService.acquireItem(stoneId, itemId);
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.STONE_WEARABLE_ITEM_ACQUIRE_SUCCESS, "아이템 획득에 성공했습니다."));
    }

    /**
     * 사용자가 아이템을 장착하는 API
     */
    @PostMapping("/{stoneId}/equip/{itemId}")
    public ResponseEntity<ApiResponse<String>> equipItem(
            @PathVariable Long stoneId,
            @PathVariable Long itemId) {
        stoneWearableItemService.equipItem(stoneId, itemId);
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.STONE_WEARABLE_ITEM_EQUIP_SUCCESS, "아이템 장착에 성공했습니다."));
    }

    /**
     * 사용자가 아이템 장착을 해제하는 API
     */
    @PostMapping("/{stoneId}/unequip/{itemId}")
    public ResponseEntity<ApiResponse<String>> unequipItem(
            @PathVariable Long stoneId,
            @PathVariable Long itemId) {
        stoneWearableItemService.unequipItem(stoneId, itemId);
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.STONE_WEARABLE_ITEM_UNEQUIP_SUCCESS, "아이템 장착 해제에 성공했습니다."));
    }

    /**
     * 사용자가 보유한 아이템 목록을 조회하는 API
     */
    @GetMapping("/{stoneId}/items")
    public ResponseEntity<ApiResponse<List<StoneWearableItemDto>>> getOwnedItems(
            @PathVariable Long stoneId) {
        List<StoneWearableItemDto> response = stoneWearableItemService.getOwnedItems(stoneId);
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.STONE_WEARABLE_ITEM_OWNED_FETCH_SUCCESS, response));
    }


    /**
     * 사용자가 장착한 아이템 목록을 조회하는 API
     */
    /**
     * 사용자가 장착한 아이템 목록을 조회하는 API
     */
    @GetMapping("/{stoneId}/equipped")
    public ResponseEntity<ApiResponse<List<StoneWearableItemDto>>> getEquippedItems(
            @PathVariable Long stoneId) {
        List<StoneWearableItemDto> response = stoneWearableItemService.getEquippedItems(stoneId);
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.STONE_WEARABLE_ITEM_EQUIPPED_FETCH_SUCCESS, response));
    }


    /**
     * 사용자가 아이템을 삭제(버리기)하는 API
     */
    @DeleteMapping("/{stoneId}/item/{itemId}")
    public ResponseEntity<ApiResponse<String>> deleteItem(
            @PathVariable Long stoneId,
            @PathVariable Long itemId) {
        stoneWearableItemService.deleteItem(stoneId, itemId);
        return ResponseEntity.ok(
                ApiResponse.success(SuccessCode.STONE_WEARABLE_ITEM_DELETE_SUCCESS, "아이템 삭제에 성공했습니다."));
    }

}
