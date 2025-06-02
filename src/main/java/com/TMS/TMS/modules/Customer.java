package com.TMS.TMS.modules;

import com.TMS.TMS.status.AccountStatus;
import com.TMS.TMS.status.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerName;

    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Address customerAddress = new Address();

    private UserRole role = UserRole.CUSTOMER;

    private boolean isEmailVerified = false;

    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;

}
