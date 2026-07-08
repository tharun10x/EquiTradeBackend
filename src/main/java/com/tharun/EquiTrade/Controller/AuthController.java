package com.tharun.EquiTrade.Controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.tharun.EquiTrade.Dto.JwtResponse;
import com.tharun.EquiTrade.Dto.LoginRequest;
import com.tharun.EquiTrade.Dto.RegisterRequest;
import com.tharun.EquiTrade.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {

        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest) {

        try {

            authService.registerUser(registerRequest);

            return ResponseEntity.ok("User registered successfully!");

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/whoami")
    public ResponseEntity<?> whoami(Authentication authentication){

    return ResponseEntity.ok(authentication);

}
}