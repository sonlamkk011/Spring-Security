package com.example.springsecurity.util;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurity.entity.Account;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    public void testGenarateToken() {
//        String accessToken = JwtUtil.generateToken("1221", "user", "T2009M1", 11);
//        System.out.println(accessToken);
//        DecodedJWT decodedJWT = JwtUtil.getVerifier().verify(accessToken);


        Account account = Account.builder()
                .id(System.currentTimeMillis())
                .role(1)
                .username("dddd")
                .passwordHash("1212")
                .build();
        String accessToken = JwtUtil.generateTokenByAccount(account, 15 * 24 * 60 * 60 * 1000);
        System.out.println(accessToken);

    }


}