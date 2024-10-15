package com.lushnikova.homework_1.middleware;

import com.lushnikova.homework_1.dto.resp.UserResponse;


public class UserMiddleware implements Middleware{

    @Override
    public boolean checkEmail(String email, Object object) {
        if (object instanceof UserResponse) {
            return ((UserResponse) object).getEmail().equals(email);
        }
        return false;
    }

    @Override
    public boolean checkPassword(String password, Object object) {
        if (object instanceof UserResponse) {
            return ((UserResponse) object).getPassword().equals(password);
        }
        return false;
    }

}
