package com.bookstore.service;

import com.bookstore.command.LoginCommand;
import com.bookstore.command.UserRegistrationCommand;
import com.bookstore.config.jwt.JWTService;
import com.bookstore.constants.EmailEvents;
import com.bookstore.constants.UserRole;
import com.bookstore.dao.IUserManager;
import com.bookstore.model.BookRack;
import com.bookstore.model.User;
import com.bookstore.utils.UserInfoDetails;
import com.bookstore.utils.UserUtils;
import com.bookstore.vo.LoginResponseVo;
import com.bookstore.vo.UserRegistrationResponseVo;
import com.bookstore.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.bookstore.constants.Constants.USER_MESSAGE_GENRES_NOT_PRESENT;


@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final IUserManager userManager;
    private final MailService mailService;

    private final BookRackManagementService bookRackManagementService;

    private static final Mapper mapper = new DozerBeanMapper();

    @Autowired
    public UserServiceImpl(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                           JWTService jwtService, PasswordEncoder passwordEncoder, IUserManager userManager, BookRackManagementService bookRackManagementService, MailService mailService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userManager = userManager;
        this.bookRackManagementService = bookRackManagementService;
        this.mailService = mailService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public UserRegistrationResponseVo registerUser(UserRegistrationCommand userRegistrationCommand, String privateKey) throws Exception {
        try {
            if (Objects.equals(userRegistrationCommand.getRole(), UserRole.ADMIN)) {
                if (!StringUtils.equals(JWTService.getRawSha256Key(), privateKey)) {
                    throw new AuthenticationServiceException("Not allowed");
                }
            }
            Optional<User> userOptional = userManager.findByUserName(userRegistrationCommand.getEmail());
            if (userOptional.isPresent()) {
                throw new Exception("User Already exists");
            }
            User user = mapper.map(userRegistrationCommand, User.class);
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRole(userRegistrationCommand.getRole());
            String userReturnMessage = addUserInterestedGenresAndReturnMessageToUser(userRegistrationCommand.getGenresInterested(), user);
            userManager.save(user);
            mailService.sendEmailSimple(EmailEvents.REGISTRATION, user);
            return new UserRegistrationResponseVo(user.getEmail(), LocalDateTime.now(), true, null, userReturnMessage);
        } catch (Exception e) {
            logger.error("Error while registering user with username {}", userRegistrationCommand.getEmail(), e);
            throw e;
        }
    }

    private String addUserInterestedGenresAndReturnMessageToUser(Set<String> genresInterested, User user) {
        Set<BookRack> allBookRacks = bookRackManagementService.getAllBookRacks();
        Map<String, BookRack> allBookRacksMap = allBookRacks.stream().collect(Collectors.toMap(BookRack::getRackName, a -> a));
        Set<BookRack> userInterestedBookRacks = new HashSet<>();
        for (String genreInterested : genresInterested) {
            if (StringUtils.isEmpty(genreInterested)) {
                continue;
            }
            genreInterested = StringUtils.trim(genreInterested);
            genreInterested = StringUtils.capitalize(genreInterested);
            if (allBookRacks.contains(new BookRack(genreInterested))) {
                userInterestedBookRacks.add(allBookRacksMap.get(genreInterested));
            }
        }
        user.setGenresInterested(userInterestedBookRacks);
        genresInterested.removeAll(userInterestedBookRacks.stream().map(BookRack::getRackName).collect(Collectors.toSet()));
        if (!CollectionUtils.isEmpty(genresInterested)) {
            return USER_MESSAGE_GENRES_NOT_PRESENT.formatted(String.join(",", genresInterested));
        }
        return null;

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
        try {
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
        } catch (Exception e) {
            logger.error("Error while fetching user info", e);
            throw e;
        }
    }

}
