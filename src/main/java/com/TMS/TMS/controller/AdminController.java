package com.TMS.TMS.controller;

import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.modules.KCYVerification;
import com.TMS.TMS.servise.CustomerService;
import com.TMS.TMS.servise.KCYVerificationService;
import com.TMS.TMS.status.AccountStatus;
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
}
