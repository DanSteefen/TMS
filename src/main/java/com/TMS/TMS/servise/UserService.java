package com.TMS.TMS.servise;

import com.TMS.TMS.modules.User;

public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;

}
