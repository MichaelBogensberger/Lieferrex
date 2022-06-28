package com.htlimst.lieferrex.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BestellDto {

    private long bestellId;
    private boolean bestellTyp;
    private String datum;
    private double preis;
    private String status;
    private String restaurantName;
    private String StrasseHausnummer;
    private List<EinkaufswagenDatailDto> einkaufswagenDatailDtoList;

}
