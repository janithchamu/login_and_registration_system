package com.jwt.springsecurity.controller;

import com.jwt.springsecurity.dto.SignInRequest;
import com.jwt.springsecurity.dto.SignUpRequest;
import com.jwt.springsecurity.dto.UserDetailResponce;
import com.jwt.springsecurity.entities.User;
import com.jwt.springsecurity.services.AuthenicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @GetMapping("/")
    public String hellUser(){
        return "Hi User";
    }


}
