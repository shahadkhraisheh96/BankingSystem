package com.prograssoft.shahad.application.api;

import com.prograssoft.shahad.business.model.LogIn;
import com.prograssoft.shahad.business.model.User;
import com.prograssoft.shahad.business.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {
    private final AuthService authService;
    public AuthApi(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Validated User user){
        authService.signUp(user);
        return ResponseEntity.ok("user registered");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LogIn login){
        String token = authService.logIn(login.getEmail(), login.getPassword());
        return ResponseEntity.ok(token);

    }

}
