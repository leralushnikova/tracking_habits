package com.lushnikova.middleware;

import com.lushnikova.dto.resp.AdminResponse;


public class AdminMiddleware implements Middleware{

    @Override
    public boolean checkEmail(String email, Object object) {
        if (object instanceof AdminResponse) {
            return ((AdminResponse) object).getEmail().equals(email);
        }
        return false;
    }

    @Override
    public boolean checkPassword(String password, Object object) {
        if (object instanceof AdminResponse) {
            return ((AdminResponse) object).getPassword().equals(password);
        }
        return false;
    }

}
