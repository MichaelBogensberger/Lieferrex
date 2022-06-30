package com.htlimst.lieferrex.dto;

import lombok.*;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BestellDto {

    private long bestellId;
    private String bestellArt;
    private String datum;
    private double preis;
    private String status;
    private String restaurantName;
    private String strasseHausnummer;
    private Integer rating;
    private HashMap<String, Integer> gerichtNameAnzahl;

}
