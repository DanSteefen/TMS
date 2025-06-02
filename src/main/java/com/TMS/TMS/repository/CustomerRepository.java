package com.TMS.TMS.repository;

import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.status.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);

    List<Customer> findByAccountStatus(AccountStatus status);
}
