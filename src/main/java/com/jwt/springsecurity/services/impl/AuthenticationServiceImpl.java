package com.jwt.springsecurity.services.impl;

import com.jwt.springsecurity.dto.*;
import com.jwt.springsecurity.entities.Role;
import com.jwt.springsecurity.entities.User;
import com.jwt.springsecurity.repository.UserRepository;
import com.jwt.springsecurity.services.AuthenicationService;
import com.jwt.springsecurity.services.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenicationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private JWTService jwtService;


    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;

        this.jwtService = jwtService;
    }

    public UserDetailResponce signup(SignUpRequest signUpRequest){
        User user = new User();

        user.setFirstname(signUpRequest.getFirstname());
        user.setSecondname(signUpRequest.getSecondname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);


        return mapToUserDto(userRepository.save(user));
    }


    public JwtAuthenticationResponce signin(SignInRequest signInRequest){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
            var User = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid Username or Password"));

            var jwt = jwtService.generateToken(User);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),User);

            JwtAuthenticationResponce responce = new JwtAuthenticationResponce();
            responce.setJwt(jwt);
            responce.setRefreshToken(refreshToken);
            return  responce;
    }

    public JwtAuthenticationResponce refreshToken(RefreshTokenRequest refreshTokenRequest){
        String email = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(email).orElseThrow();

        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){

            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponce jwtAuthenticationResponce = new JwtAuthenticationResponce();
            jwtAuthenticationResponce.setJwt(jwt);
            jwtAuthenticationResponce.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponce;
        }

       return null;

    }


    public UserDetailResponce mapToUserDto(User user){
        UserDetailResponce userDto = new UserDetailResponce();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
