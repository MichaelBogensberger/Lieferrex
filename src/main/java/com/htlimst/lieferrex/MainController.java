package com.htlimst.lieferrex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("")
    public String showIndexPage() {
        System.out.println("Index page loaded");

        return "main/index";
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "main/search";
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "main/login";
    }


    @GetMapping("/register")
    public String showRegisterPage() {
        return "main/register";
    }


    @GetMapping("/buildOne")
    public String showBaukastenPage() {
        return "baukasten/one.html";
    }


}
