package com.example.spring3_demo.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    @PostMapping("/demo")
    public String register() {
        return "{ \"message\": \"User registered successfully\" }";
    }
}

