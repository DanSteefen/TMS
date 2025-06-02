package com.TMS.TMS.servise.impl;

import com.TMS.TMS.config.JwtProvider;
import com.TMS.TMS.exception.CustomerException;
import com.TMS.TMS.modules.Address;
import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.repository.AddressRepository;
import com.TMS.TMS.repository.CustomerRepository;
import com.TMS.TMS.servise.CustomerService;
import com.TMS.TMS.status.AccountStatus;
import com.TMS.TMS.status.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Customer getCustomerProfile(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getCustomerByEmail(email);
    }

    @Override
    public Customer createCustomer(Customer customer) throws Exception {

        Customer customerExist = customerRepository.findByEmail(customer.getEmail());
        if (customerExist != null){
            throw new Exception("Customer already exist and used different email...");
        }

        Address saveAddress = addressRepository.save(customer.getCustomerAddress());
        Customer newCustomer = new Customer();
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPassword(customer.getPassword());
        newCustomer.setCustomerName(customer.getCustomerName());
        newCustomer.setCustomerAddress(saveAddress);
        newCustomer.setRole(UserRole.CUSTOMER);
        newCustomer.setMobile(customer.getMobile());

        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer getCustomerById(Long id) throws CustomerException {

        return customerRepository.findById(id).orElseThrow(() -> new CustomerException("Customer not found with this id " + id));
    }

    @Override
    public Customer getCustomerByEmail(String email) throws Exception {

        Customer customer = customerRepository.findByEmail(email);
        if (customer == null){
            throw new Exception("Customer not found...");
        }
        return customer;
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) throws Exception {

        Customer existingCustomer = this.getCustomerById(id);

        if (customer.getCustomerName() != null){
            existingCustomer.setCustomerName(customer.getCustomerName());
        }
        if (customer.getMobile() != null){
            existingCustomer.setMobile(customer.getMobile());
        }
        if (customer.getEmail() != null){
            existingCustomer.setEmail(customer.getEmail());
        }
        if (customer.getCustomerAddress() != null
                && customer.getCustomerAddress().getAddress() != null
                && customer.getCustomerAddress().getMobile() != null
                && customer.getCustomerAddress().getLocalCity() != null){

            existingCustomer.getCustomerAddress().setAddress(customer.getCustomerAddress().getAddress());
            existingCustomer.getCustomerAddress().setMobile(customer.getCustomerAddress().getMobile());
            existingCustomer.getCustomerAddress().setLocalCity(customer.getCustomerAddress().getLocalCity());
            existingCustomer.getCustomerAddress().setPinCode(customer.getCustomerAddress().getPinCode());
        }

        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer verifyEmail(String email, String otp) throws Exception {

        Customer customer = getCustomerByEmail(email);
        customer.setEmailVerified(true);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomerAccountStatus(Long customerId, AccountStatus status) throws Exception {

        Customer customer = getCustomerById(customerId);
        customer.setAccountStatus(status);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomer(AccountStatus status) {

        return customerRepository.findByAccountStatus(status);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {

        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

}
