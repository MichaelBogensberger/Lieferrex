package com.htlimst.lieferrex.service.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {
    public Payment createPayment(Double total, String method, String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException;
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
    public void payMandant(Double total, String email, String emailMessage, String MandantName, String BestellNr);
}
