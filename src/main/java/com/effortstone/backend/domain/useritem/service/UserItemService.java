package com.effortstone.backend.domain.useritem.service;


import com.effortstone.backend.domain.item.entity.Item;
import com.effortstone.backend.domain.item.repository.ItemRepository;
import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.user.repository.UserRepository;
import com.effortstone.backend.domain.useritem.dto.response.UserItemResponseDto;
import com.effortstone.backend.domain.useritem.entity.UserItem;
import com.effortstone.backend.domain.useritem.repository.UserItemRepository;
import com.effortstone.backend.global.common.response.ApiResponse;
import com.effortstone.backend.global.common.response.SuccessCode;
import com.effortstone.backend.global.error.ErrorCode;
import com.effortstone.backend.global.error.exception.CustomException;
import com.effortstone.backend.global.security.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserItemService {

    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;
    private final UserRepository userRepository;


    /**
     * 1️⃣ 사용자가 아이템을 얻음 (아이템 획득)
     */
    public UserItem acquireItem(Long itemId) {
        String userCode = SecurityUtil.getCurrentUserCode();
        User user = userRepository.findById(userCode).orElseThrow();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("아이템을 찾을 수 없습니다."));

        UserItem userItem = UserItem.builder()
                .user(user)
                .item(item)
                .build();

        return userItemRepository.save(userItem);
    }


    /**
     * 4️⃣ 사용자가 보유한 아이템 목록 조회
     */
    public List<UserItemResponseDto> getOwnedItems() {
        User user = userRepository.findById(SecurityUtil.getCurrentUserCode()).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
        return userItemRepository.findByUser(user)
                .stream()
                .map(UserItemResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

}
