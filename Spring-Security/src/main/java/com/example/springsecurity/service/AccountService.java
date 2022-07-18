package com.example.springsecurity.service;

import com.example.springsecurity.entity.Account;
import com.example.springsecurity.entity.Credential;
import com.example.springsecurity.entity.dto.AccountLoginDTO;
import com.example.springsecurity.entity.dto.AccountRegisterDTO;
import com.example.springsecurity.repository.AccoutRepository;
import com.example.springsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    final AccoutRepository accoutRepository;
    final PasswordEncoder passwordEncoder;

    public AccountRegisterDTO register(AccountRegisterDTO accountRegisterDTO) {
        Optional<Account> optionalAccount = accoutRepository.findAccountByUsername(accountRegisterDTO.getUsername());
        if (optionalAccount.isPresent()) {
            return null;
        }
        Account account = Account.builder()
                .username(accountRegisterDTO.getUsername())
                .passwordHash(passwordEncoder.encode(accountRegisterDTO.getPassword()))
                .role(accountRegisterDTO.getRole())
                .build();
        accoutRepository.save(account);
        accountRegisterDTO.setId((int) account.getId());
        return accountRegisterDTO;
    }

    public Credential login(AccountLoginDTO accountLoginDTO) {
        // 1. tim user theo username
        Optional<Account> optionalAccount = accoutRepository.findAccountByUsername(accountLoginDTO.getUsername());
        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("user is not found!");
        }
        Account account = optionalAccount.get();
        // so sanh password match
        boolean isMatch = passwordEncoder.matches(accountLoginDTO.getPassword(), account.getPasswordHash());
        if (isMatch) {
            int expiredAfterDay = 7;
            String accessToken = JwtUtil.generateTokenByAccount(account, expiredAfterDay * 24 * 60 * 60 * 1000);
            String refreshToken = JwtUtil.generateTokenByAccount(account, 14 * 24 * 60 * 60 * 1000);
            Credential credential = new Credential();
            Credential.setAccessToken(accessToken);
            Credential.setRefreshToken(refreshToken);
            Credential.setExpiredAt(expiredAfterDay);
            Credential.setScope("basic_information");
            return credential;
        } else {
            throw new UsernameNotFoundException("password is not match");
        }
    }

    public void getInformation() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accoutRepository.findAccountByUsername(username);
        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("user name is not found;");
        }
        Account account = optionalAccount.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(account.getRole() == 1 ? "Admin" : "user");
        authorities.add(simpleGrantedAuthority);
        return new User(account.getUsername(), account.getPasswordHash(), authorities);
    }
}
