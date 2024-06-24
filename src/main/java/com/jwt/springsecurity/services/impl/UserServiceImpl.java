package com.jwt.springsecurity.services.impl;

import com.jwt.springsecurity.repository.UserRepository;
import com.jwt.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){
            public UserDetails loadUserByUsername(String username){
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
            }

        };
    }
}
