package com.TMS.TMS.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;
    private String fullName;
    private String mobileNumber;
    private String otp;
}
