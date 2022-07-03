package com.htlimst.lieferrex.service.paypal;

import com.htlimst.lieferrex.dto.BezahlDto;
import com.htlimst.lieferrex.exceptions.ClientsideMandantPaymentException;
import com.htlimst.lieferrex.exceptions.ServersideMandantPaymentException;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {
    Payment createPayment(Double total, String description, String cancelUrl, String successUrl) throws PayPalRESTException;
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
    public void payMandant(BezahlDto bezahlDto)throws ClientsideMandantPaymentException, ServersideMandantPaymentException;
}
