package com.htlimst.lieferrex.exceptions;

public class ClientsideMandantPaymentException extends Exception{
    public ClientsideMandantPaymentException() {
        super("Client-Seitiger fehler bei Mandanten bezahlung");
    }

}
