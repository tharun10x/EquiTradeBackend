package com.tharun.EquiTrade.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tharun.EquiTrade.Dto.JwtResponse;
import com.tharun.EquiTrade.Dto.LoginRequest;
import com.tharun.EquiTrade.Dto.RegisterRequest;
import com.tharun.EquiTrade.Model.User;
import com.tharun.EquiTrade.Repository.UserRepository;
import com.tharun.EquiTrade.security.JwtUtils;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepo,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {

        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse authenticateUser(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(
                                request.getUserName(),
                                request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateJwtToken(authentication);

        User user = userRepo.findByUserName(request.getUserName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new JwtResponse(
        token,
        "Bearer",
        user.getId(),
        user.getUserName(),
        user.getRole(),
        user.getWalletBalance());
    }

    public void registerUser(RegisterRequest request) {

        if (userRepo.existsByUserName(request.getUserName())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        User user = new User();

        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRole() != null && !request.getRole().isBlank()) {
            user.setRole(request.getRole());
        } else {
            user.setRole("ROLE_USER");
        }

        userRepo.save(user);
    }
}