package com.effortstone.backend.domain.subscriptionpurchase.repository;

import com.effortstone.backend.domain.subscriptionpurchase.entity.SubscriptionPurchases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPurchasesRepository extends JpaRepository<SubscriptionPurchases, Long> {

    boolean existsByOrderId(String orderId);
}
