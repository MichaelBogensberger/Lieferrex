package com.htlimst.lieferrex.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BezahlDto {

    private double preis;
    private String kundenNachricht;
    private String mandantEmail;
    private String mandantNachricht;
    private String mandantName;
    private String bestellNr;

}
