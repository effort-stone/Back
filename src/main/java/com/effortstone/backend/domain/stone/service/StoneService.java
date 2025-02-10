package com.effortstone.backend.domain.stone.service;


import com.effortstone.backend.domain.stone.dto.request.StoneRequestDto;
import com.effortstone.backend.domain.stone.dto.response.StoneDto;
import com.effortstone.backend.domain.stone.entity.Stone;
import com.effortstone.backend.domain.stone.repository.StoneRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.global.security.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoneService {

    private final StoneRepository stoneRepository;
    private final UserRepository userRepository;

    /**
     * CREATE: 돌(캐릭터) 생성
     */
    public StoneDto createStone(StoneRequestDto.StoneCreateRequest dto) {
        // 1) userCode로 User 엔티티 조회
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2) Stone 엔티티 생성
        Stone stone = Stone.builder()
                .stoneName(dto.getStoneName())
                .user(user)
                .stoneExp(0L)
                .stoneLevel(1)
                .build();

        // 3) DB 저장
        Stone saved = stoneRepository.save(stone);

        // 4) 엔티티 -> DTO 변환
        return entityToDto(saved);
    }

    /**
     * READ: 특정 stoneCode로 조회
     */
    public StoneDto getStone(Long stoneCode) {
        Stone stone = stoneRepository.findById(stoneCode)
                .orElseThrow(() -> new RuntimeException("Stone not found with id=" + stoneCode));
        return entityToDto(stone);
    }

    /**
     * READ: 전체 Stone 목록 조회
     */
    public List<StoneDto> getAllStones() {
        return stoneRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * UPDATE: stoneCode에 해당하는 Stone 수정
     */
    public StoneDto updateStone(StoneRequestDto.StoneUpdateRequest dto) {
        // 1) userCode로 User 엔티티 조회
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new RuntimeException("User not found: " + userCode));
        Stone stone = stoneRepository.findById(dto.getStoneCode())
                .orElseThrow(() -> new RuntimeException("Stone not found with id="));

        // 필드 업데이트
        // 2) 엔티티 빌더로 Stone 생성
        Stone newstone = Stone.builder()
                .stoneCode(stone.getStoneCode()) // 기존코드 유지
                .stoneName(dto.getStoneName())
                .stoneLevel(dto.getStoneLevel())
                .stoneExp(dto.getStoneExp())
                .user(user)
                .build();

        Stone updated = stoneRepository.save(newstone);
        return entityToDto(updated);
    }

    /**
     * DELETE: stoneCode로 삭제
     */
    public void deleteStone(Long stoneCode) {
        Stone stone = stoneRepository.findById(stoneCode)
                .orElseThrow(() -> new RuntimeException("Stone not found with id=" + stoneCode));
        stoneRepository.delete(stone);
    }

    // 캐릭터 조회시 착용한 아이템들 가져오는 코드
    public Stone getStoneWithItems(Long stoneId) {
        return stoneRepository.findByIdWithItems(stoneId)
                .orElseThrow(() -> new EntityNotFoundException("캐릭터를 찾을 수 없습니다."));
    }

    //==============================
    // 엔티티 <-> DTO 변환 유틸 메서드
    //==============================

    private StoneDto entityToDto(Stone stone) {
        return StoneDto.builder()
                .stoneCode(stone.getStoneCode())
                .stoneName(stone.getStoneName())
                .stoneLevel(stone.getStoneLevel())
                .stoneExp(stone.getStoneExp())
                .userCode(stone.getUser().getUserCode()) // User PK
                .build();
    }

}
