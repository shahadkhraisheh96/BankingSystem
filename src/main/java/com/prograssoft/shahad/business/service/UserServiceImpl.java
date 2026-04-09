package com.prograssoft.shahad.business.service;

import com.prograssoft.shahad.business.model.User;
import com.prograssoft.shahad.business.service.interfaces.UserService;
import com.prograssoft.shahad.business.storage.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

public class UserServiceImpl implements UserService {
    private final HashMap<String, User> users = new HashMap<>();
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean matchPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public User saveUser(User user) {
      User newUser=User.builder().id(user.getId())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .userName(user.getUserName())
                .build();
      return userRepository.save(newUser);
    }

    @Override
    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User fetchUserByUsername(String username) {
        if(userRepository.existsByUsername(username)) {
            return users.values().stream()
                    .filter(u->u.getUserName().equals(username))
                    .findFirst().orElse(null);
        }else{
            return null;
        }
    }
}
