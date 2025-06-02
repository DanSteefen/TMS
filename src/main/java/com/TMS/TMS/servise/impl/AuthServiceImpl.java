package com.TMS.TMS.servise.impl;

import com.TMS.TMS.config.JwtProvider;
import com.TMS.TMS.modules.Customer;
import com.TMS.TMS.modules.User;
import com.TMS.TMS.modules.VerificationCode;
import com.TMS.TMS.repository.CustomerRepository;
import com.TMS.TMS.repository.UserRepository;
import com.TMS.TMS.repository.VerificationCodeRepository;
import com.TMS.TMS.request.LoginRequest;
import com.TMS.TMS.request.SignupRequest;
import com.TMS.TMS.response.AuthResponse;
import com.TMS.TMS.servise.AuthService;
import com.TMS.TMS.status.UserRole;
import com.TMS.TMS.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerUserServiceImpl customerUserService;

    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Wrong otp...");
        }

        User user = userRepository.findByEmail(req.getEmail());

        if (user == null){
            User createUser = new User();
            createUser.setEmail(req.getEmail());
            createUser.setName(req.getFullName());
            createUser.setRole(UserRole.CUSTOMER);
            createUser.setPassword(passwordEncoder.encode(req.getOtp()));
            user = userRepository.save(createUser);

        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(UserRole.CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return null;
    }

    @Override
    public void sentLoginOtp(String email, UserRole role) throws Exception {

        String SIGNING_PREFIX = "signing_";

        if (email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());

            if (role.equals(UserRole.CUSTOMER)){
                Customer customer = customerRepository.findByEmail(email);
                if(customer == null){
                    throw new Exception("Seller not found...");
                }
            } else {
                User user = userRepository.findByEmail(email);
                if(user == null){
                    throw new Exception("User doesn't exist with provided this email...");
                }
            }
        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);
        if (isExist != null){
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "TMS login/signup otp";
        String text = "Your login/signup otp is - " + otp;

        emailService.sendVerificationOtpMail(email, otp, subject, text);
    }

    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {

        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login is successfully");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null: authorities.iterator().next().getAuthority();

        authResponse.setRole(UserRole.valueOf(roleName));
        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {

        UserDetails userDetails = customerUserService.loadUserByUsername(username);

        String CUSTOMER_PREFIX = "customer_";
        if (username.startsWith(CUSTOMER_PREFIX)){
            username = username.substring(CUSTOMER_PREFIX.length());
        }

        if (userDetails == null){
            throw new BadCredentialsException("Invalid username or password..");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("Wrong otp..");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
