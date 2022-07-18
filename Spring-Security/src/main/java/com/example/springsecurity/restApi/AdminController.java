package com.example.springsecurity.restApi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @RequestMapping
    public String say(){
        return "hello admin";
    }
}
