package com.TMS.TMS.repository;

import com.TMS.TMS.modules.Subscription;
import com.TMS.TMS.status.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByName(String name);
    List<Subscription> findBySubscriptionStatus(SubscriptionStatus status);
}
