package com.jwt.springsecurity.services;

import com.jwt.springsecurity.dto.*;
import com.jwt.springsecurity.entities.User;

public interface AuthenicationService {
    UserDetailResponce signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponce signin(SignInRequest signInRequest);
    JwtAuthenticationResponce refreshToken(RefreshTokenRequest refreshTokenRequest);
}
