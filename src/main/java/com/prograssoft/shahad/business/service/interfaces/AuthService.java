package com.prograssoft.shahad.business.service.interfaces;

import com.prograssoft.shahad.business.model.User;

public interface AuthService {
    User signUp(User user);
     String logIn(String email, String password);
     User getByUsername(String username);
}
