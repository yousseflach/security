package com.lachgar.security.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("user")
    public String testUser(){
        return "sucesse test User";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin")
    public String testAdmin(){
        return "sucesse test Admin";
    }


    @GetMapping("all")
    public String testAll(){
        return "Hello World";
    }


}
