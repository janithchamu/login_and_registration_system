package com.jwt.springsecurity.controller;

import com.jwt.springsecurity.dto.*;
import com.jwt.springsecurity.entities.User;
import com.jwt.springsecurity.services.AuthenicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

     @Autowired
    private AuthenicationService authenicationService;

    @PostMapping("/signup")
    public UserDetailResponce signup(@RequestBody SignUpRequest signUpRequest){
        return (authenicationService.signup(signUpRequest));
    }


    @PostMapping("/signin")
    public JwtAuthenticationResponce signin(@RequestBody SignInRequest signInRequest){
        return authenicationService.signin(signInRequest);
    }

    @PostMapping("/refreshtoken")
    public JwtAuthenticationResponce refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return authenicationService.refreshToken(refreshTokenRequest);
    }


}
