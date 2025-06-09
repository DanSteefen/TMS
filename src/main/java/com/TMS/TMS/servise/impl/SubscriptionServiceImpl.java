package com.TMS.TMS.servise.impl;

import com.TMS.TMS.exception.SubscriptionException;
import com.TMS.TMS.modules.Plan;
import com.TMS.TMS.modules.Subscription;
import com.TMS.TMS.repository.PlanRepository;
import com.TMS.TMS.repository.SubscriptionRepository;
import com.TMS.TMS.servise.SubscriptionService;
import com.TMS.TMS.status.SubscriptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(Subscription subscription) throws Exception {

        Subscription subscriptionExist = subscriptionRepository.findByName(subscription.getName());
        if (subscriptionExist != null){
            throw new Exception("Subscription plan already exist using different plan names...");
        }

        Plan savePlan = planRepository.save(subscription.getPlan());
        Subscription newSubscription = new Subscription();
        newSubscription.setName(subscription.getName());
        newSubscription.setPlan(savePlan);
        newSubscription.setUser(subscription.getUser());
        newSubscription.setStartDate(LocalDateTime.now());
        newSubscription.setEndDate(LocalDateTime.now());
        newSubscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);

        return subscriptionRepository.save(newSubscription);
    }

    @Override
    public Subscription getSubscriptionById(Long id) throws SubscriptionException {

        return subscriptionRepository.findById(id).orElseThrow(() -> new SubscriptionException("Subscription not found this id " + id));
    }

    @Override
    public Subscription getSubscriptionByName(String name) throws Exception {

        Subscription subscription = subscriptionRepository.findByName(name);
        if (subscription == null){
            throw new Exception("Subscription not found");
        }
        return subscription;
    }

    @Override
    public Subscription autoRenewSubscription(String name, String email) throws Exception {

        Subscription subscription = getSubscriptionByName(name);
        subscription.setAutoRenew(true);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscriptionStatus(Long subscriptionId, SubscriptionStatus status) throws SubscriptionException {

        Subscription subscription = getSubscriptionById(subscriptionId);
        subscription.setSubscriptionStatus(status);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getAllSubscription(SubscriptionStatus status) {

        return subscriptionRepository.findBySubscriptionStatus(status);
    }
}
