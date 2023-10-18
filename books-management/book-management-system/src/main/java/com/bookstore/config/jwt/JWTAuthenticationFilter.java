package com.bookstore.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.bookstore.constants.Constants.*;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    private final ThreadLocal<String> threadLocalAuthBearer = new ThreadLocal<>();

    private static final Logger logger = LogManager.getLogger(JWTAuthenticationFilter.class);

    @Autowired
    public JWTAuthenticationFilter(JWTService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            logger.info("Context path is {}", request.getServletPath());
            for (String contextPath : UNAUTHENTICATED_CONTEXT_PATHS) {
                if (StringUtils.contains(request.getServletPath(), contextPath)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            String authHeader = request.getHeader(AUTHORIZATION);
            if (!StringUtils.startsWith(authHeader, BEARER)) {
                //send to Dispatcher servlet
                filterChain.doFilter(request, response);
                return;
            }
            String jwtToken = authHeader.substring(7);
            String userName = jwtService.getUserNameFromJWTToken(jwtToken);
            //check if user is not authenticated yet
            if (!StringUtils.isEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if (jwtService.isValidToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            threadLocalAuthBearer.set(authHeader);
            //send to Dispatcher servlet
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error while forwarding request filter chain", e);
            throw e;
        }
    }
}
