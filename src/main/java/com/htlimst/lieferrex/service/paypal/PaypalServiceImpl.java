package com.htlimst.lieferrex.service.paypal;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


import com.htlimst.lieferrex.dto.BezahlDto;
import com.htlimst.lieferrex.exceptions.ClientsideMandantPaymentException;
import com.htlimst.lieferrex.exceptions.ServersideMandantPaymentException;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.payouts.CreatePayoutRequest;
import com.paypal.payouts.CreatePayoutResponse;
import com.paypal.payouts.Currency;
import com.paypal.payouts.PayoutsPostRequest;
import com.paypal.payouts.SenderBatchHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaypalServiceImpl implements PaypalService {

    private APIContext apiContext;
    private PayPalHttpClient apiClient;

    @Autowired
    public PaypalServiceImpl(APIContext apiContext, PayPalHttpClient apiClient) {
        this.apiContext = apiContext;
        this.apiClient = apiClient;
    }



    @Override
    public Payment createPayment(Double total, String description, String cancelUrl, String successUrl) throws PayPalRESTException {

        Amount amount = new Amount();
        amount.setCurrency("EUR");
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        System.out.println(String.valueOf(total));
        amount.setTotal(String.valueOf(total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);

    }


    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }



    @Override
    public void payMandant(BezahlDto bezahlDto) throws ClientsideMandantPaymentException, ServersideMandantPaymentException {
        double preis = new BigDecimal(bezahlDto.getPreis()).setScale(2, RoundingMode.HALF_UP).doubleValue();

        List<com.paypal.payouts.PayoutItem> items = new ArrayList<com.paypal.payouts.PayoutItem>();
        items.add(new com.paypal.payouts.PayoutItem().senderItemId("")
                .note("").receiver(bezahlDto.getMandantEmail())
                .amount(new Currency().currency("EUR").value(String.valueOf(preis))));

        CreatePayoutRequest request = new CreatePayoutRequest()
                .senderBatchHeader(new SenderBatchHeader()
                        .senderBatchId(bezahlDto.getBestellNr()).emailMessage(bezahlDto.getMandantNachricht())
                        .emailSubject("Eingehende Lieferrex bestellung").note("Beste Grüße dein Lieferrex-Team").recipientType("EMAIL")).items(items);

        try {
            PayoutsPostRequest httpRequest = new PayoutsPostRequest().requestBody(request);
            HttpResponse<CreatePayoutResponse> response = apiClient.execute(httpRequest);

            CreatePayoutResponse payouts = response.result();
            payouts.links().forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
        } catch (IOException ioe) {
            if (ioe instanceof HttpException) {
                // Something went wrong server-side
                HttpException he = (HttpException) ioe;
                System.out.println(he.getMessage());
                throw new ServersideMandantPaymentException();
            } else {
                // Something went wrong client-side
                throw new ClientsideMandantPaymentException();
            }
        }
    }
}
