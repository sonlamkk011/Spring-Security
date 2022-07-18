package com.example.springsecurity.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurity.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class MyAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            logger.info("Call filter...");
            String fullToken = request.getHeader("Authorization");
            String  originaToken = fullToken.replace("Bearer", "").trim();
            DecodedJWT decodedJWT = JwtUtil.getDecodedJwt(originaToken);
            String accountId = decodedJWT.getSubject();
            String username = decodedJWT.getClaim("username").asString();
            String role = decodedJWT.getClaim("role").asString();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
            logger.info("Role: " + role);
            UsernamePasswordAuthenticationToken usernameToken = new UsernamePasswordAuthenticationToken(accountId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(usernameToken);
        }catch (Exception ex){
            ex.printStackTrace();

        }
        filterChain.doFilter(request, response);
    }
}
