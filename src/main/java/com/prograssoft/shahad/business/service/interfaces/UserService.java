package com.prograssoft.shahad.business.service.interfaces;

import com.prograssoft.shahad.business.model.LogIn;
import com.prograssoft.shahad.business.model.User;
import com.prograssoft.shahad.business.storage.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User fetchUserByEmail(String email);
    User fetchUserByUsername(String username);


}
