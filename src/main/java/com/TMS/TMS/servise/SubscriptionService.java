package com.TMS.TMS.servise;

import com.TMS.TMS.exception.SubscriptionException;
import com.TMS.TMS.modules.Subscription;
import com.TMS.TMS.status.SubscriptionStatus;

import java.util.List;

public interface SubscriptionService {

    Subscription createSubscription(Subscription subscription) throws Exception;
    Subscription getSubscriptionById(Long id) throws SubscriptionException;
    Subscription getSubscriptionByName(String name) throws Exception;
    Subscription autoRenewSubscription(String name, String email) throws Exception;
    Subscription updateSubscriptionStatus(Long subscriptionId, SubscriptionStatus status) throws SubscriptionException;
    List<Subscription> getAllSubscription(SubscriptionStatus status);
}
