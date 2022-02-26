package com.htlimst.lieferrex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginRegistrationController {


    @GetMapping("/login")
    public String showLoginPage() {
        return "main/login";
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "main/register";
    }
}
