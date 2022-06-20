package com.htlimst.lieferrex.controller.bezahlen;

import com.htlimst.lieferrex.dto.BezahlDto;
import com.htlimst.lieferrex.service.paypal.PaypalService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
@RequestMapping("/restaurant")
public class BezahlenController {

    PaypalService paypalService;

    @Autowired
    public BezahlenController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    public static final String SUCCESS_URL = "success";
    public static final String CANCEL_URL = "cancel";

    @GetMapping("/{id}/checkout")
    public String showCheckoutPage(@PathVariable int id) {
        System.out.println(id);
        return "main/checkout";
    }


    @PostMapping("/{id}/checkout")
    public String payment(@PathVariable String id, @ModelAttribute("order") BezahlDto bezahlDto) {
        System.out.println(id);
        bezahlDto.setPrice(10.657);
        bezahlDto.setMethod("paypal");
        bezahlDto.setIntent("ORDER");
        bezahlDto.setDescription("Bestell Nr. 001");
        try {
            Payment payment = paypalService.createPayment(bezahlDto.getPrice(), bezahlDto.getMethod(),
                    bezahlDto.getIntent(), bezahlDto.getDescription(), "http://localhost:8080/restaurant/"+ id+ "/checkout/cancel",
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
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {

        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                System.out.println("Ich bezahlt ---------------------------");
                paypalService.payMandant(9.55, "mandant4@business.example.com", "2x Pizza | 2x Döner", "Pizz World", "012");
                return "main/success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
