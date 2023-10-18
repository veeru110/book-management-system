package com.bookstore.controller;

import com.bookstore.command.LoginCommand;
import com.bookstore.command.UserRegistrationCommand;
import com.bookstore.service.IUserService;
import com.bookstore.vo.LoginResponseVo;
import com.bookstore.vo.UserRegistrationResponseVo;
import com.bookstore.vo.UserVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponseVo> registerUser(@Valid @RequestBody UserRegistrationCommand userRegistrationCommand, @RequestHeader(value = "privateKey", required = false) String privateKey) throws Exception {
        UserRegistrationResponseVo userRegistrationResponseVo = userService.registerUser(userRegistrationCommand, privateKey);
        return new ResponseEntity<>(userRegistrationResponseVo, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseVo> login(@Valid @RequestBody LoginCommand loginCommand) throws Exception{
        LoginResponseVo loginResponseVo = userService.login(loginCommand);
        return new ResponseEntity<>(loginResponseVo, HttpStatus.OK);
    }

    @GetMapping("/getUserInfo")
    @PreAuthorize("hasRole('ADMIN','BUYER','SELLER')")
    public ResponseEntity<UserVo> getUserInfo() {
        return new ResponseEntity<>(userService.getUserVo(), HttpStatus.OK);
    }
}
