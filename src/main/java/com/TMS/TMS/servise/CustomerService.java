package com.TMS.TMS.servise;

import com.TMS.TMS.exception.CustomerException;
import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.status.AccountStatus;

import java.util.List;

public interface CustomerService {

    Customer getCustomerProfile(String jwt) throws Exception;

    Customer createCustomer(Customer customer) throws Exception;

    Customer getCustomerById(Long id) throws CustomerException;
    Customer getCustomerByEmail(String email) throws Exception;
    Customer updateCustomer(Long id, Customer customer) throws Exception;
    Customer verifyEmail(String email, String otp) throws Exception;
    Customer updateCustomerAccountStatus(Long customerId, AccountStatus status) throws Exception;
    List<Customer> getAllCustomer(AccountStatus status);
    void deleteCustomer(Long id) throws Exception;
}
