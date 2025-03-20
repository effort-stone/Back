package com.effortstone.backend.domain.useritem.repository;

import com.effortstone.backend.domain.user.entity.User;
import com.effortstone.backend.domain.useritem.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    //특정 아이템을 가지고 있는지 확인
    Boolean existsByUser_UserCodeAndItem_ItemCode(String userCode, Long itemId);

    // 사용자가 가지고 있는 아이템들 다 보여줌
    List<UserItem> findByUser(User user);
}
