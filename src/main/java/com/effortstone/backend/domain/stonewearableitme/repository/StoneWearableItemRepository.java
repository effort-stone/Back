package com.effortstone.backend.domain.stonewearableitme.repository;

import com.effortstone.backend.domain.stonewearableitme.entity.StoneWearableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoneWearableItemRepository extends JpaRepository<StoneWearableItem, Long> {

    // 특정 캐릭터가 착용한 아이템 목록
    List<StoneWearableItem> findByStone_StoneCode(Long stoneId);

    //특정 아이템이 이미 장착되어 있는지 확인
    Optional<StoneWearableItem> findByStone_StoneCodeAndItem_ItemCode(Long stoneId, Long itemId);

}
