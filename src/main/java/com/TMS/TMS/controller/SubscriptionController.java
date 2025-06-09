package com.TMS.TMS.controller;

import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.modules.Subscription;
import com.TMS.TMS.repository.CustomerRepository;
import com.TMS.TMS.servise.SubscriptionService;
import com.TMS.TMS.status.SubscriptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/create")
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) throws Exception{

        Subscription saveSubscription = subscriptionService.createSubscription(subscription);
        return new ResponseEntity<>(saveSubscription, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) throws Exception {

        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Subscription> getSubscriptionByName(@PathVariable String name) throws Exception {

        Subscription subscription = subscriptionService.getSubscriptionByName(name);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @PatchMapping("/renew/subscription")
    public ResponseEntity<Subscription> autoRenewSubscription(@PathVariable String email) throws Exception{

        Customer customer = customerRepository.findByEmail(email);

        if (customer == null || !customer.getCustomerName().equals(customer)){
            throw new Exception("Wrong customer...");
        }

        Subscription subscription = subscriptionService.autoRenewSubscription(customer.getEmail(), email);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscription(@RequestParam(required = false) SubscriptionStatus status){

        List<Subscription> subscriptions = subscriptionService.getAllSubscription(status);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }
}
