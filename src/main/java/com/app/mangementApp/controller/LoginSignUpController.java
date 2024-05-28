package com.app.mangementApp.controller;

import com.app.mangementApp.Dto.UserDto;
import com.app.mangementApp.security.jwt.JwtAuthRequest;
import com.app.mangementApp.security.jwt.JwtAuthResponse;
import com.app.mangementApp.service.Service.IAuthService;
import com.app.mangementApp.service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginSignUpController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest authRequest) {
        JwtAuthResponse authResponse = this
                .authService
                .login(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        String newUser = this
                .userService
                .createNewUser(userDto);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    @GetMapping("/get/user")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto user = this
                .userService
                .getUserByEmailAdd(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
