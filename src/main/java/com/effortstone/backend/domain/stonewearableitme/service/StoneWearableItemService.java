package com.effortstone.backend.domain.stonewearableitme.service;


import com.effortstone.backend.domain.item.entity.Item;
import com.effortstone.backend.domain.item.repository.ItemRepository;
import com.effortstone.backend.domain.stone.entity.Stone;
import com.effortstone.backend.domain.stone.repository.StoneRepository;
import com.effortstone.backend.domain.stonewearableitme.dto.response.StoneWearableItemDto;
import com.effortstone.backend.domain.stonewearableitme.entity.StoneWearableItem;
import com.effortstone.backend.domain.stonewearableitme.repository.StoneWearableItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class StoneWearableItemService {

    private final StoneWearableItemRepository stoneWearableItemRepository;
    private final ItemRepository itemRepository;
    private final StoneRepository stoneRepository;

    /**
     * 1️⃣ 사용자가 아이템을 얻음 (아이템 획득)
     */
    public StoneWearableItem acquireItem(Long stoneId, Long itemId) {
        Stone stone = stoneRepository.findById(stoneId)
                .orElseThrow(() -> new EntityNotFoundException("캐릭터를 찾을 수 없습니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("아이템을 찾을 수 없습니다."));

        StoneWearableItem stoneitem = StoneWearableItem.builder()
                .stone(stone)
                .item(item)
                .stoneWearableItemEquipped(false) // 처음에는 장착안함
                .build();

        return stoneWearableItemRepository.save(stoneitem);
    }

    /**
     * 2️⃣ 사용자가 아이템을 장착
     */
    public StoneWearableItem equipItem(Long stoneId, Long itemId) {
        StoneWearableItem stoneWearableItem = stoneWearableItemRepository.findByStone_StoneCodeAndItem_ItemCode(stoneId, itemId)
                .orElseThrow(() -> new IllegalStateException("해당 아이템을 소지하고 있지 않습니다."));

        // 기존 객체의 장착 상태를 업데이트
        stoneWearableItem.updateEquippedStatus(true);  // 장착 상태로 변경
        return stoneWearableItemRepository.save(stoneWearableItem);
    }

    /**
     * 3️⃣ 사용자가 아이템을 해제
     */
    public StoneWearableItem unequipItem(Long stoneId, Long itemId) {
        StoneWearableItem stoneWearableItem = stoneWearableItemRepository.findByStone_StoneCodeAndItem_ItemCode(stoneId, itemId)
                .orElseThrow(() -> new IllegalStateException("해당 아이템을 소지하고 있지 않습니다."));

        stoneWearableItem.updateEquippedStatus(false);  // 장착 상태로 변경
        return stoneWearableItemRepository.save(stoneWearableItem);
    }

    /**
     * 4️⃣ 사용자가 보유한 아이템 목록 조회
     */
    public List<StoneWearableItemDto> getOwnedItems(Long stoneId) {
        return stoneWearableItemRepository.findByStone_StoneCode(stoneId)
                .stream()
                .map(item -> new StoneWearableItemDto(
                        item.getItem().getItemCode(),       // 아이템 코드
                        item.getItem().getItemName(),       // 아이템 이름
                        item.getStoneWearableItemEquipped()  // 장착 여부
                ))
                .collect(Collectors.toList());
    }

    /**
     * 5️⃣ 사용자가 장착한 아이템 목록 조회
     * * 캐릭터가 소지 중인 아이템 중 장착된 아이템들을 DTO로 변환하여 반환합니다.
     */
    public List<StoneWearableItemDto> getEquippedItems(Long stoneId) {
        return stoneWearableItemRepository.findByStone_StoneCode(stoneId)
                .stream()
                // 여기서 실제로 장착된 아이템만 필터링할 수도 있습니다.
                // .filter(StoneWearableItem::isStoneWearableItemEquipped)
                .map(item -> new StoneWearableItemDto(
                        item.getItem().getItemCode(),       // 아이템 코드
                        item.getItem().getItemName(),       // 아이템 이름
                        item.getStoneWearableItemEquipped()  // 장착 여부
                ))
                .collect(Collectors.toList());
    }

    /**
     * 6️⃣ 아이템을 삭제 (아이템 버리기)
     */
    public void deleteItem(Long stoneId, Long itemId) {
        StoneWearableItem stoneWearableItem = stoneWearableItemRepository.findByStone_StoneCodeAndItem_ItemCode(stoneId, itemId)
                .orElseThrow(() -> new IllegalStateException("해당 아이템을 소지하고 있지 않습니다."));

        stoneWearableItemRepository.delete(stoneWearableItem);
    }

}
