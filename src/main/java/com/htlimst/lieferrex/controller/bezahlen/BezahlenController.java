package com.htlimst.lieferrex.controller.bezahlen;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.htlimst.lieferrex.dto.*;
import com.htlimst.lieferrex.exceptions.ClientsideMandantPaymentException;
import com.htlimst.lieferrex.exceptions.ServersideMandantPaymentException;
import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.bestellung.BestellungService;
import com.htlimst.lieferrex.service.paypal.PaypalService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/restaurant")
public class BezahlenController {

    public PaypalService paypalService;
    public BestellungService bestellungService;
    public AngestellterService angestellterService;

    @Autowired
    public BezahlenController(PaypalService paypalService, BestellungService bestellungService, AngestellterService angestellterService) {
        this.paypalService = paypalService;
        this.bestellungService = bestellungService;
        this.angestellterService = angestellterService;
    }




    public static final String SUCCESS_URL = "success";
    public static final String CANCEL_URL = "cancel";

    @GetMapping("/{id}/checkout")
    public String showCheckoutPage(@PathVariable int id, @AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null){
            return "redirect:/login?notAuthenticated";
        }
        System.out.println(id);
        return "main/checkout";
    }


    @PostMapping("/{id}/checkout")
    public String payment(@PathVariable String id, @CookieValue(name = "warenkorb") String cookie, @AuthenticationPrincipal UserPrincipal principal) {
        if (principal == null){
            return "redirect:/login?401";
        }
        System.out.println(cookie + "from cookie");
        List<WarenkorbDetailDto> warenkorb = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            warenkorb = Arrays.asList(mapper.readValue(cookie, WarenkorbDetailDto[].class));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(id);
        EinkaufswagenDto einkaufswagenDto = bestellungService.deserializeEinkaufswagen(warenkorb, Long.valueOf(id));
        BezahlDto bezahlDto = bestellungService.getBezahlDto(einkaufswagenDto);

        try {
            Payment payment = paypalService.createPayment(bezahlDto.getPreis(), bezahlDto.getKundenNachricht(),"http://localhost:8080/restaurant/"+ id+ "/checkout/cancel",
                    "http://localhost:8080/restaurant/" + id+ "/checkout/success");
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }


    @GetMapping("/{id}/checkout/cancel")
    public String cancelPay(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "main/cancel";
    }

    @GetMapping("/{id}/checkout/success")
    public String successPay(@PathVariable String id, @AuthenticationPrincipal UserPrincipal principal,
                             @RequestParam(name="paymentId", required = false) String paymentId, @RequestParam(name="PayerID", required = false)
                                         String payerId, @CookieValue(name = "warenkorb") String cookie, HttpServletResponse response) {
        if (payerId==null || paymentId==null){
            return "redirect:/";
        }

        List<WarenkorbDetailDto> warenkorb = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            warenkorb = Arrays.asList(mapper.readValue(cookie, WarenkorbDetailDto[].class));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        EinkaufswagenDto einkaufswagenDto = bestellungService.deserializeEinkaufswagen(warenkorb, Long.valueOf(id));

        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());

            if (payment.getState().equals("approved")) {
                BezahlDto bezahlInfos = bestellungService.getBezahlDto(einkaufswagenDto);
                paypalService.payMandant(bezahlInfos);
                bestellungService.bestellungAufgeben(einkaufswagenDto, principal, bezahlInfos);

            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        } catch (ServersideMandantPaymentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        } catch (ClientsideMandantPaymentException e) {
            System.out.println(e.getMessage());
            return "redirect:/";
        }

        Cookie resCookie = new Cookie("warenkorb", null); // Not necessary, but saves bandwidth.
        resCookie.setPath("/");
        resCookie.setHttpOnly(true);
        resCookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
        response.addCookie(resCookie);

        return "redirect:/orders";
    }



}
