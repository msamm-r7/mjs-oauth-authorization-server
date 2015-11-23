package com.rapid7.poc.oauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping({"/","/home"})
    public String get() { 
        return "home";
    }
    
}
