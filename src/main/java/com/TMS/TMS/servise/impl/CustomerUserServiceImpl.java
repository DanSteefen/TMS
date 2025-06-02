package com.TMS.TMS.servise.impl;

import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.modules.User;
import com.TMS.TMS.repository.CustomerRepository;
import com.TMS.TMS.repository.UserRepository;
import com.TMS.TMS.status.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String CUSTOMER_PREFIX = "customer_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.startsWith(CUSTOMER_PREFIX)){
            String actualUserName = username.substring(CUSTOMER_PREFIX.length());
            Customer seller = customerRepository.findByEmail(actualUserName);

            if (seller != null){
                return buildUserDetails(seller.getEmail(), seller.getPassword(), seller.getRole());
            }

        } else {
            User user = userRepository.findByEmail(username);
            if (user != null){
                return buildUserDetails(user.getEmail(), user.getPassword(), user.getRole());
            }
        }
        throw new UsernameNotFoundException("User or Seller not found with this email -  " + username);

    }

    private UserDetails buildUserDetails(String email, String password, UserRole role) {

        if (role == null) role = UserRole.CUSTOMER;

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(email, password, authorityList);
    }
}
