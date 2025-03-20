package com.effortstone.backend.domain.item.repository;

import com.effortstone.backend.domain.item.entity.Item;
import com.effortstone.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

//    // 특정 아이템을 착용한 캐릭터들 찾기
//    @Query("SELECT  s from stone s join stone_wearable_item swi on s.stoneCode == swi.stone.stoneCode where swi.item = :itmeCode")
//    List<Stone> findByItem(@Param("itmeCode") Long itmeCode);

    Optional<Item> findByItemCode(Long itemCode);

}
