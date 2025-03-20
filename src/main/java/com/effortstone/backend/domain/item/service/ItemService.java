package com.effortstone.backend.domain.item.service;


import com.effortstone.backend.domain.item.dto.request.ItemRequestDto;
import com.effortstone.backend.domain.item.dto.response.ItemResponseDto;
import com.effortstone.backend.domain.item.entity.Item;
import com.effortstone.backend.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 아이템 생성
     */
    @Transactional
    public ItemResponseDto createItem(ItemRequestDto requestDto) {
        Item item = Item.builder()
                .itemName(requestDto.getItemName())
                .itemType(requestDto.getItemType())
                .itemPrice(requestDto.getItemPrice())
                .build();
        Item savedItem = itemRepository.save(item);
        return convertToDto(savedItem);
    }

    /**
     * 아이템 단건 조회
     */
    public ItemResponseDto getItem(Long itemCode) {
        Item item = itemRepository.findById(itemCode)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemCode));
        return convertToDto(item);
    }

    /**
     * 전체 아이템 목록 조회
     */
    public List<ItemResponseDto> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * 아이템 수정
     */
    @Transactional
    public ItemResponseDto updateItem(Long itemCode, ItemRequestDto requestDto) {
        Item item = itemRepository.findById(itemCode)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemCode));
        item.update(requestDto.getItemName(), requestDto.getItemType(), requestDto.getItemPrice());
        // 수정된 엔티티 저장
        Item updatedItem = itemRepository.save(item);
        return convertToDto(updatedItem);
    }

    /**
     * 아이템 삭제
     */
    @Transactional
    public void deleteItem(Long itemCode) {
        if (!itemRepository.existsById(itemCode)) {
            throw new RuntimeException("Item not found with id: " + itemCode);
        }
        itemRepository.deleteById(itemCode);
    }

    private ItemResponseDto convertToDto(Item item) {
        return new ItemResponseDto(
                item.getItemCode(),
                item.getItemUrl(),
                item.getItemName(),
                item.getItemPriceType(),
                item.getItemPrice(),
                item.getItemType(),
                item.getItemRatio(),
                item.getItemSrcSizeX(),
                item.getItemSrcSizeY(),
                item.getItemRow(),
                item.getItemStepTime(),
                item.getItemFrom(),
                item.getItemTo(),
                item.getItemPositionX(),
                item.getItemPositionY()
        );
    }

}
