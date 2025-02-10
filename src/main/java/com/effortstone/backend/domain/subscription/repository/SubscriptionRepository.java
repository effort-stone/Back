package com.effortstone.backend.domain.subscription.repository;

import com.effortstone.backend.domain.subscription.entity.Subscription;
import com.effortstone.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

}
