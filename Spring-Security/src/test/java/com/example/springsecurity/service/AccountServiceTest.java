package com.example.springsecurity.service;

import com.example.springsecurity.SpringSecurityApplication;
import com.example.springsecurity.entity.Credential;
import com.example.springsecurity.entity.dto.AccountLoginDTO;
import com.example.springsecurity.entity.dto.AccountRegisterDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringSecurityApplication.class)
class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Test
    void register() {
        AccountRegisterDTO accountRegisterDTO = new AccountRegisterDTO();
        accountRegisterDTO.setUsername("dddd");
        accountRegisterDTO.setPassword("111222");
        accountRegisterDTO.setRole(1);
        AccountRegisterDTO afterCreate = accountService.register(accountRegisterDTO);
        System.out.println(afterCreate);
    }
    @Test
    void login() {
        AccountLoginDTO accountLoginDTO = new AccountLoginDTO();
        accountLoginDTO.setUsername("asdasd");
        accountLoginDTO.setPassword("12121");
        Credential credential = accountService.login(accountLoginDTO);
        System.out.println(credential.toString());
    }
}