package com.htlimst.lieferrex.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WarenkorbDetailDto {
    String anmerkung;
    String anzahl;
    String beschreibung;
    String id;
    String madant;
    String name;
    String preis;
}
