package com.effortstone.backend.domain.stone.controller;


import com.effortstone.backend.domain.stone.dto.request.StoneRequestDto;
import com.effortstone.backend.domain.stone.dto.response.StoneDto;
import com.effortstone.backend.domain.stone.service.StoneService;
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
@RequestMapping("/api/v1/stone")
public class StoneController {

    private final StoneService stoneService;

    /**
     * CREATE (POST)
     */
    @PostMapping
    public ResponseEntity<ApiResponse<StoneDto>> createStone(@RequestBody StoneRequestDto.StoneCreateRequest dto) {
        StoneDto created = stoneService.createStone(dto);
        // 예) STONE_CREATE_SUCCESS
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STONE_CREATE_SUCCESS, created));
    }

    /**
     * READ (GET) - 단건
     */
    @GetMapping("/{stoneCode}")
    public ResponseEntity<ApiResponse<StoneDto>> getStone(@PathVariable Long stoneCode) {
        StoneDto stone = stoneService.getStone(stoneCode);
        // 예) STONE_FETCH_SUCCESS
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STONE_FETCH_SUCCESS, stone));
    }

    /**
     * READ (GET) - 전체
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<StoneDto>>> getAllStones() {
        List<StoneDto> stones = stoneService.getAllStones();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STONE_FETCH_SUCCESS, stones));
    }

    /**
     * UPDATE (PUT)
     */
    @PutMapping()
    public ResponseEntity<ApiResponse<StoneDto>> updateStone(
            @RequestBody StoneRequestDto.StoneUpdateRequest dto) {

        StoneDto updated = stoneService.updateStone(dto);
        // 예) STONE_UPDATE_SUCCESS
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STONE_UPDATE_SUCCESS, updated));
    }

    /**
     * DELETE (DELETE)
     */
    @DeleteMapping("/{stoneCode}")
    public ResponseEntity<ApiResponse<Void>> deleteStone(@PathVariable Long stoneCode) {
        stoneService.deleteStone(stoneCode);
        // 예) STONE_DELETE_SUCCESS
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STONE_DELETE_SUCCESS, null));
    }


}
