package com.bookstore.service;

import com.bookstore.command.LoginCommand;
import com.bookstore.command.UserRegistrationCommand;
import com.bookstore.vo.LoginResponseVo;
import com.bookstore.vo.UserRegistrationResponseVo;
import com.bookstore.vo.UserVo;


public interface IUserService {

    UserRegistrationResponseVo registerUser(UserRegistrationCommand userRegistrationCommand, String privateKey) throws Exception;

    LoginResponseVo login(LoginCommand loginCommand) throws Exception;

    UserVo getUserVo() throws RuntimeException;

}
