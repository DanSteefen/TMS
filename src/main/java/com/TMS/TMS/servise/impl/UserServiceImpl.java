package com.TMS.TMS.servise.impl;

import com.TMS.TMS.config.JwtProvider;
import com.TMS.TMS.modules.User;
import com.TMS.TMS.repository.UserRepository;
import com.TMS.TMS.servise.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new Exception("User not found with this email - " + email);
        }
        return user;
    }
}
