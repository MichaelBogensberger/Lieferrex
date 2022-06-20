package com.htlimst.lieferrex.service.paypal;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


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
    public Payment createPayment(Double total, String method, String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {

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
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
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
    public void payMandant(Double total, String email, String emailMessage, String mandantName, String bestellNr) {
        // Construct a request object and set desired parameters
        // Here, CreatePayoutRequest() creates a POST request to /v1/payments/payouts
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();

        List<com.paypal.payouts.PayoutItem> items = new ArrayList<com.paypal.payouts.PayoutItem>();
        items.add(new com.paypal.payouts.PayoutItem().senderItemId("")
                .note("").receiver(email)
                .amount(new Currency().currency("EUR").value(String.valueOf(total))));

        CreatePayoutRequest request = new CreatePayoutRequest()
                .senderBatchHeader(new SenderBatchHeader()
                        .senderBatchId(bestellNr).emailMessage(emailMessage)
                        .emailSubject("Eingehende Lieferrex bestellung").note("Beste Grüße dein Lieferrex-Team").recipientType("EMAIL")).items(items);

        try {
            // Call API with your client and get a response for your call
            PayoutsPostRequest httpRequest = new PayoutsPostRequest().requestBody(request);
            HttpResponse<CreatePayoutResponse> response = apiClient.execute(httpRequest);

            // If call returns body in response, you can get the de-serialized version by
            // calling result() on the response
            CreatePayoutResponse payouts = response.result();
            System.out.println("Payouts Batch ID: " + payouts.batchHeader().payoutBatchId());
            payouts.links().forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
        } catch (IOException ioe) {
            if (ioe instanceof HttpException) {
                // Something went wrong server-side
                HttpException he = (HttpException) ioe;
                System.out.println(he.getMessage());
                he.headers().forEach(x -> System.out.println(x + " :" + he.headers().header(x)));
            } else {
                // Something went wrong client-side
                System.out.println("Mandant konnte nicht gezahlt werden");
            }
        }
    }
}