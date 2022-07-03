package com.htlimst.lieferrex.controller.bezahlen;

import com.htlimst.lieferrex.dto.BezahlDto;
import com.htlimst.lieferrex.dto.EinkaufswagenDatailDto;
import com.htlimst.lieferrex.dto.EinkaufswagenDto;
import com.htlimst.lieferrex.exceptions.ClientsideMandantPaymentException;
import com.htlimst.lieferrex.exceptions.ServersideMandantPaymentException;
import com.htlimst.lieferrex.model.Angestellter;
import com.htlimst.lieferrex.model.Kunde;
import com.htlimst.lieferrex.service.angestellter.AngestellterService;
import com.htlimst.lieferrex.service.bestellung.BestellungService;
import com.htlimst.lieferrex.service.paypal.PaypalService;
import com.htlimst.lieferrex.service.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.io.IOException;
import java.util.ArrayList;
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
    public String showCheckoutPage(@PathVariable int id) {
        System.out.println(id);
        return "main/checkout";
    }


    @PostMapping("/{id}/checkout")
    public String payment(@PathVariable String id) {
        System.out.println(id);
        EinkaufswagenDto einkaufswagenDto = bestellungService.deserializeEinkaufswagen("Einkaufswagen");
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
    public String cancelPay() {
        return "main/cancel";
    }

    @GetMapping("/{id}/checkout/success")
    public String successPay(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(name="paymentId", required = false) String paymentId, @RequestParam(name="PayerID", required = false) String payerId) {
        if (payerId==null || paymentId==null){
            return "redirect:/";
        }

        EinkaufswagenDto einkaufswagenDto = bestellungService.deserializeEinkaufswagen("Einkaufswagen");

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


        return "main/success";
    }



}
