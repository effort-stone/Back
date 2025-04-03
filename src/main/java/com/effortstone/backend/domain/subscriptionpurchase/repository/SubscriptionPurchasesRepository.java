package com.effortstone.backend.domain.subscriptionpurchase.repository;

import com.effortstone.backend.domain.subscriptionpurchase.entity.SubscriptionPurchases;
import com.effortstone.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionPurchasesRepository extends JpaRepository<SubscriptionPurchases, Long> {

    boolean existsByOrderId(String orderId);

    List<SubscriptionPurchases> findAllByUser(User user);
}
