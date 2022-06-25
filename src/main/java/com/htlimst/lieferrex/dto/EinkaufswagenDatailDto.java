package com.htlimst.lieferrex.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EinkaufswagenDatailDto {

    long gerichtID;
    String anmerkung;
    int anzahl;

}
