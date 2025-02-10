package com.effortstone.backend.domain.stone.repository;

import com.effortstone.backend.domain.item.entity.Item;
import com.effortstone.backend.domain.stone.entity.Stone;
import com.effortstone.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoneRepository extends JpaRepository<Stone, Long> {

    @Query("SELECT s FROM stone s JOIN FETCH s.stoneWearableItems swi JOIN FETCH swi.item WHERE s.id = :id")
    Optional<Stone> findByIdWithItems(@Param("id") Long id);
    Optional<Stone> findByUser(User user);
    Optional<Stone> findByStoneCode(Long stoneCode);


}
