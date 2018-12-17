package com.sanjiv.dbconfigserver.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/ping")
    public String sayHello() {
    	return "OK: DbConfingSever Service is working fine";
    }
}
