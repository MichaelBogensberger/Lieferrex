package com.htlimst.lieferrex.exceptions;

public class ServersideMandantPaymentException extends Exception{
    public ServersideMandantPaymentException() {
        super("Server-Seitiger fehler bei Mandanten bezahlung");
    }

}
