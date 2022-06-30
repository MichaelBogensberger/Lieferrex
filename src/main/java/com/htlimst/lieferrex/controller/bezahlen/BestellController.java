package com.htlimst.lieferrex.controller.bezahlen;

import com.htlimst.lieferrex.dto.BestellDto;
import com.htlimst.lieferrex.service.bestellung.BestellungService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BestellController {

    public BestellungService bestellungService;

    @Autowired
    public BestellController(BestellungService bestellungService) {
        this.bestellungService = bestellungService;
    }

    @GetMapping("/orders")
    public String showOrdersPage(@AuthenticationPrincipal UserPrincipal principal, Model model) {
        List<BestellDto> bestellDtos = bestellungService.getBestellDto(principal.getUsername());

        for (BestellDto bestellDto: bestellDtos) {
            System.out.println(bestellDto.toString());
        }

        model.addAttribute("bestellungen", bestellDtos);

        return "main/orders";
    }

    @GetMapping("/rate/{bestellId}")
    public String showOrdersPage(@AuthenticationPrincipal UserPrincipal principal, @PathVariable long bestellId, @RequestParam int rating) {
        System.out.println(principal.getUsername());
        System.out.println(bestellId);
        System.out.println(rating);

        if (bestellungService.makeRating(principal, bestellId, rating)){
            return "redirect:/orders?success";
        }else {
            return "redirect:/orders?error";
        }

    }

}
