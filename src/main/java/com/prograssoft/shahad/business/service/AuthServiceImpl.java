package com.prograssoft.shahad.business.service;

import com.prograssoft.shahad.business.model.User;
import com.prograssoft.shahad.business.service.interfaces.AuthService;
import com.prograssoft.shahad.business.storage.repository.interfaces.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User signUp(User user) {
        if (userRepository.existsByUsername(user.getUserName())) {
            throw new IllegalArgumentException("Username already exists");

        }
        User newUser = User.builder().userName(user.getUserName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
      return   userRepository.save(newUser);

    }

    public String logIn(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Invalid email ");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");

        }
        return jwtService.generateToken(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
