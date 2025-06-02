package com.TMS.TMS.controller;

import com.TMS.TMS.exception.CustomerException;
import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.modules.VerificationCode;
import com.TMS.TMS.repository.VerificationCodeRepository;
import com.TMS.TMS.request.LoginRequest;
import com.TMS.TMS.response.AuthResponse;
import com.TMS.TMS.servise.AuthService;
import com.TMS.TMS.servise.CustomerService;
import com.TMS.TMS.servise.impl.EmailServiceImpl;
import com.TMS.TMS.status.AccountStatus;
import com.TMS.TMS.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginCustomer(@RequestBody LoginRequest req) throws Exception{

        String otp = req.getOtp();
        String email = req.getEmail();

        req.setEmail("customer" + email);
        AuthResponse authResponse = authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Customer> verifyCustomerEmail(@PathVariable String otp) throws Exception{

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if (verificationCode == null){
            throw new Exception("Wrong otp...");
        }

        Customer customer = customerService.verifyEmail(verificationCode.getEmail(), otp);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws Exception, MessagingException{

        Customer savedCustomer = customerService.createCustomer(customer);

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(customer.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "TMS Email Verification Code";
        String text = "Welcome to our TMS, verify your account using this link";
        String frontend_url = "http://localhost:3000/verify-customer/";
        emailService.sendVerificationOtpMail(customer.getEmail(), verificationCode.getOtp(), subject, text + frontend_url);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) throws CustomerException {

        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Customer> getCustomerByJwtToken(@RequestHeader("Authorization") String jwtToken) throws Exception {

        Customer customer = customerService.getCustomerProfile(jwtToken);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) AccountStatus status) {

        List<Customer> customers = customerService.getAllCustomer(status);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Customer> updateCustomer(@RequestHeader("Authorization") String jwtToken, @RequestBody Customer customer) throws Exception {

        Customer profile = customerService.getCustomerProfile(jwtToken);
        Customer updateCustomer = customerService.updateCustomer(profile.getId(), customer);
        return  ResponseEntity.ok(updateCustomer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception {

        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
