package com.bookstore.controller;

import com.bookstore.command.LoginCommand;
import com.bookstore.command.UserRegistrationCommand;
import com.bookstore.service.IUserService;
import com.bookstore.vo.LoginResponseVo;
import com.bookstore.vo.UserRegistrationResponseVo;
import com.bookstore.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Authorization")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "This is used to register user themselves as Buyer or Seller")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "400", description = "Bad Request, User is suggested to check request body")
    })
    public ResponseEntity<UserRegistrationResponseVo> registerUser(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true) @RequestBody UserRegistrationCommand userRegistrationCommand, @RequestHeader(value = "privateKey", required = false) String privateKey) throws Exception {
        UserRegistrationResponseVo userRegistrationResponseVo = userService.registerUser(userRegistrationCommand, privateKey);
        return new ResponseEntity<>(userRegistrationResponseVo, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseVo> login(@Valid @RequestBody LoginCommand loginCommand) throws Exception {
        LoginResponseVo loginResponseVo = userService.login(loginCommand);
        return new ResponseEntity<>(loginResponseVo, HttpStatus.OK);
    }

    @GetMapping("/getUserInfo")
    @PreAuthorize("hasAnyRole('ADMIN','BUYER','SELLER')")
    public ResponseEntity<UserVo> getUserInfo() {
        return new ResponseEntity<>(userService.getUserVo(), HttpStatus.OK);
    }
}
