package com.bookstore.config.jwt;

import com.bookstore.dao.ICredentialManager;
import com.bookstore.model.BookStorePrivateKeys;
import com.bookstore.utils.UserInfoDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

    private static final Integer JWT_EXPIRATION_TIME_IN_HRS = 1;

    private static Key PRIVATE_KEY;
    private static String RAW_SHA256_KEY;

    public static Key getPrivateKey() {
        return PRIVATE_KEY;
    }

    public static String getRawSha256Key() {
        return RAW_SHA256_KEY;
    }

    private final ICredentialManager credentialManager;

    public JWTService(ICredentialManager credentialManager) {
        this.credentialManager = credentialManager;
    }

    @PostConstruct
    public void initJWTService() {
        Optional<BookStorePrivateKeys> serverPrivateKeys = credentialManager.getLatestPrivateKey();
        if (serverPrivateKeys.isEmpty()) {
            throw new RuntimeException("Server is not configured with private keys");
        }
        RAW_SHA256_KEY = serverPrivateKeys.get().getPrivateKey();
        PRIVATE_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(serverPrivateKeys.get().getPrivateKey()));
    }

    public Pair<String, Date> generateJWTToken(UserInfoDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateJWTToken(claims, userDetails);
    }

    public String getUserNameFromJWTToken(String jwtToken) {
        return getClaims(jwtToken, Claims::getSubject);
    }

    public Pair<String, Date> generateJWTToken(Map<String, Object> userClaims, UserInfoDetails userInfoDetails) {
        var currentTime = new Date();
        var expiryTime = DateUtils.addHours(currentTime, JWT_EXPIRATION_TIME_IN_HRS);
        var token = Jwts.builder().setClaims(userClaims).setSubject(userInfoDetails.getUsername())
                .setIssuedAt(currentTime).setExpiration(expiryTime)
                .signWith(PRIVATE_KEY, SignatureAlgorithm.HS256).compact();
        return Pair.of(token, expiryTime);
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromJWTToken(token);
        return StringUtils.equals(userName, userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationTime(token).before(new Date());
    }

    private Date getExpirationTime(String token) {
        return getClaims(token, Claims::getExpiration);
    }

    public <T> T getClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(PRIVATE_KEY).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
