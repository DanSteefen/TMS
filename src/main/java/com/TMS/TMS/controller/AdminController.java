package com.TMS.TMS.controller;

import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.modules.KCYVerification;
import com.TMS.TMS.modules.Plan;
import com.TMS.TMS.modules.Subscription;
import com.TMS.TMS.servise.CustomerService;
import com.TMS.TMS.servise.KCYVerificationService;
import com.TMS.TMS.servise.PlanService;
import com.TMS.TMS.servise.SubscriptionService;
import com.TMS.TMS.status.AccountStatus;
import com.TMS.TMS.status.PlanStatus;
import com.TMS.TMS.status.SubscriptionStatus;
import com.TMS.TMS.status.VerificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private KCYVerificationService kcyVerificationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    PlanService planService;

    @Autowired
    SubscriptionService subscriptionService;

    @PatchMapping("/kcyVerification{id}/status{{status}")
    public ResponseEntity<Customer> updateCustomerStatus(@PathVariable Long id, @PathVariable AccountStatus status) throws Exception{

        Customer customer = customerService.updateCustomerAccountStatus(id, status);
        return ResponseEntity.ok(customer);
    }

    @PatchMapping("/kcyVerification{id}/status{{status}")
    public ResponseEntity<KCYVerification> updateKCYVerificationStatus(@PathVariable Long id, @PathVariable VerificationStatus status) throws Exception{

        KCYVerification updateKCYVerification = kcyVerificationService.updateKCYVerificationStatus(id, status);
        return ResponseEntity.ok(updateKCYVerification);
    }

    @PatchMapping("/plan{id}/status{{status}")
    public ResponseEntity<Plan> updatePlanStatus(@PathVariable Long id, @PathVariable PlanStatus status) throws Exception{

        Plan updatePlan = planService.updatePlanStatus(id, status);
        return ResponseEntity.ok(updatePlan);
    }

    @PatchMapping("/subscription{id}/status{{status}")
    public ResponseEntity<Subscription> updateSubscriptionStatus(@PathVariable Long id, @PathVariable SubscriptionStatus status) throws Exception{

        Subscription updateSubscription = subscriptionService.updateSubscriptionStatus(id, status);
        return ResponseEntity.ok(updateSubscription);
    }
}
