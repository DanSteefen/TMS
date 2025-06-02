package com.TMS.TMS.servise;

import com.TMS.TMS.request.LoginRequest;
import com.TMS.TMS.request.SignupRequest;
import com.TMS.TMS.response.AuthResponse;
import com.TMS.TMS.status.UserRole;

public interface AuthService {

    String createUser(SignupRequest req) throws Exception;
    void sentLoginOtp(String email, UserRole role) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;
}
