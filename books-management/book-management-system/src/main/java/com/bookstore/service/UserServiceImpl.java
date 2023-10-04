package com.bookstore.service;

import com.bookstore.command.LoginCommand;
import com.bookstore.command.UserRegistrationCommand;
import com.bookstore.config.jwt.JWTService;
import com.bookstore.constants.UserRole;
import com.bookstore.dao.IUserManager;
import com.bookstore.model.User;
import com.bookstore.utils.UserInfoDetails;
import com.bookstore.vo.LoginResponseVo;
import com.bookstore.vo.UserRegistrationResponseVo;
import com.bookstore.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;


@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IUserManager userManager;

    private static final Mapper mapper = new DozerBeanMapper();

    public UserServiceImpl(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                           JWTService jwtService, PasswordEncoder passwordEncoder, IUserManager userManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userManager = userManager;
    }

    //TO DO Error handling
    @Override
    public UserRegistrationResponseVo registerUser(UserRegistrationCommand userRegistrationCommand, String privateKey) {
        UserRole userRole = UserRole.valueOf(userRegistrationCommand.getRole());
        if (Objects.equals(userRole, UserRole.ADMIN)) {
            if (!StringUtils.equals(new String(JWTService.getPrivateKey().getEncoded()), privateKey)) {
                throw new AuthenticationServiceException("Not allowed");
            }
        }
        User user = mapper.map(userRegistrationCommand, User.class);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(userRole.name());
        userManager.save(user);
        return new UserRegistrationResponseVo(user.getEmail(), LocalDateTime.now(), true, null);
    }


    @Override
    public LoginResponseVo login(LoginCommand loginCommand) throws UsernameNotFoundException {
        try {
            var userInfoDetails = (UserInfoDetails) userDetailsService.loadUserByUsername(loginCommand.getUserName());
            if (Objects.isNull(userInfoDetails)) {
                throw new UsernameNotFoundException(loginCommand.getUserName() + " not found");
            }
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCommand.getUserName(), loginCommand.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var jwtTokenInfo = jwtService.generateJWTToken(userInfoDetails);
            return new LoginResponseVo(jwtTokenInfo.getFirst(), jwtTokenInfo.getSecond());
        } catch (Exception e) {
            logger.error("Error while user login", e);
            throw e;
        }
    }

    @Override
    public UserVo getUserVo() throws UsernameNotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authenticationToken.getPrincipal())) {
            throw new UsernameNotFoundException("User not found");
        }
        String userName = ((UserInfoDetails) authenticationToken.getPrincipal()).getUsername();
        Optional<User> userOptional = userManager.findByUserName(userName);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username " + userName);
        }
        return mapper.map(userOptional.get(), UserVo.class);
    }


}
