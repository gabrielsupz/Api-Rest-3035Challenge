package com.teach.challenge.controllers;


import com.teach.challenge.domain.models.user.User;
import com.teach.challenge.domain.repositorys.UserRepository;
import com.teach.challenge.infra.security.DTOs.DadosTokenJWT;
import com.teach.challenge.infra.security.DTOs.LoginDataDTO;
import com.teach.challenge.infra.security.services.AuthService;
import com.teach.challenge.infra.security.services.TokenService;
import com.teach.challenge.services.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;
@Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid LoginDataDTO data) {

 UserDetails userDetails =  userRepository.findByEmail(data.email());

        Authentication authentication = this.authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), data.password()));

        var user = (User) authentication.getPrincipal();



        var token =  tokenService.gerarToken(user);

        return ResponseEntity.ok( new DadosTokenJWT(token));

    }
}
