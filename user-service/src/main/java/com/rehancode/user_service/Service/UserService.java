package com.rehancode.user_service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rehancode.user_service.DTO.LoginRequest;
import com.rehancode.user_service.DTO.LoginResponse;
import com.rehancode.user_service.DTO.RegisterRequest;
import com.rehancode.user_service.DTO.RegisterResponse;
import com.rehancode.user_service.DTO.UserResponse;
import com.rehancode.user_service.Entity.UserEntity;
import com.rehancode.user_service.Exceptions.AllFieldsRequired;
import com.rehancode.user_service.Exceptions.InvalidCredentialsException;
import com.rehancode.user_service.Jwt.JwtService;
import com.rehancode.user_service.Repository.UserRepository;

@Service
public class UserService {

        private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final JwtService jwtService;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public UserService(UserRepository userRepo, JwtService jwtService, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }
    public UserResponse convertToUserResponse(UserEntity user) {
    return UserResponse.builder()
            .id(user.getId())
            .username(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .build();
}


    // Convert Entity -> DTO
    public RegisterResponse convertToDto(UserEntity user) {
        return RegisterResponse.builder()
                .username(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    // Convert DTO -> Entity
    public UserEntity convertToEntity(RegisterRequest req) {
        return UserEntity.builder()
                .name(req.getUsername().trim())
                .email(req.getEmail().trim())
                .phone(req.getPhone().trim())
                .build();
    }
    public RegisterResponse registerUser(RegisterRequest req) {
          logger.info("Attempting to register user: {}", req.getUsername());
        if (req.getUsername() == null || req.getUsername().trim().isEmpty() ||
            req.getEmail() == null || req.getEmail().trim().isEmpty() ||
            req.getPassword() == null || req.getPassword().trim().isEmpty()) {
                   logger.warn("Registration failed: missing fields for user {}", req.getUsername());

            throw new AllFieldsRequired("All fields are required");
        }

        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
              logger.warn("Registration failed: Email already exists - {}", req.getEmail());
            throw new AllFieldsRequired("Email already exists");
        }

        if (userRepo.findByName(req.getUsername()).isPresent()) {
              logger.warn("Registration failed: username already exists - {}", req.getUsername());
            throw new AllFieldsRequired("Username already exists");
        }
        UserEntity user = convertToEntity(req);
        String encodedPassword = passwordEncoder.encode(req.getPassword());
        user.setPassword(encodedPassword);
        
        System.out.println("DEBUG - Registering user: " + req.getUsername());
        System.out.println("DEBUG - Encoded password: " + encodedPassword);

        UserEntity savedUser = userRepo.save(user);
                logger.info("User registered successfully: {}", savedUser.getName());


        return convertToDto(savedUser);
    }
public LoginResponse loginUser(LoginRequest req) {
      logger.info("Login attempt for user: {}", req.getUsername());
    if (req.getUsername() == null || req.getUsername().trim().isEmpty() ||
        req.getPassword() == null || req.getPassword().trim().isEmpty()) {
            logger.warn("Login failed: missing username or password");
        throw new AllFieldsRequired("Username and password are required");
    }

    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
    } catch (Exception ex) {
        // Catch authentication failures and throw custom exception
        logger.warn("Login failed: invalid credentials for user {}", req.getUsername());
        throw new InvalidCredentialsException("Invalid username or password");
    }

    UserEntity user = userRepo.findByName(req.getUsername())
                         .orElseThrow(() -> {
                            logger.error("Login failed: user not found - {}", req.getUsername());
                            return new UsernameNotFoundException("User not found");
                        });
    String token = jwtService.generateToken(user.getName());
       logger.info("Login successful for user: {}", user.getName());


    return LoginResponse.builder()
            .username(user.getName())
            .email(user.getEmail())
            .token(token)
            .phone(user.getPhone())
            .build();
}
public UserResponse getUserById(Long id) {
    UserEntity user = userRepo.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return convertToUserResponse(user);
}



}