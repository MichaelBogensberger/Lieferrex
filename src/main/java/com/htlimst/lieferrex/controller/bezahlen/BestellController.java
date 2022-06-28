package com.htlimst.lieferrex.controller.bezahlen;

import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.bestellung.BestellungService;
import com.htlimst.lieferrex.service.paypal.PaypalService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BestellController {

    public BestellungService bestellungService;

    @Autowired
    public BestellController(BestellungService bestellungService) {
        this.bestellungService = bestellungService;
    }

    @GetMapping("/orders")
    public String showOrdersPage(@AuthenticationPrincipal UserPrincipal principal) {
        bestellungService.getBestellDto(principal.getUsername());
        return "main/orders";
    }

}
