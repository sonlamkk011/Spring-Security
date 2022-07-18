package com.example.springsecurity.restApi;

import com.example.springsecurity.entity.dto.AccountLoginDTO;
import com.example.springsecurity.entity.dto.AccountRegisterDTO;
import com.example.springsecurity.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    final AccountService accountService;

    @RequestMapping(path = "register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody AccountRegisterDTO accountRegisterDTO) {
        return ResponseEntity.ok().body(accountService.register(accountService.register(accountRegisterDTO)));

    }

    @RequestMapping(path = "login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody AccountLoginDTO accountLoginDTO) {
        return ResponseEntity.ok().body(accountService.login(accountLoginDTO));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getInformation() {
        return "";
    }
}
