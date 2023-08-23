package com.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String hello(){
        return "<h1><em>Welcome</em></h1>";
    }


    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
