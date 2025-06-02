package com.TMS.TMS.response;

import com.TMS.TMS.status.UserRole;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private UserRole role;

}
