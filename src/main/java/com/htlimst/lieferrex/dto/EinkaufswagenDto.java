package com.htlimst.lieferrex.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EinkaufswagenDto {
    List<EinkaufswagenDatailDto> einkaufswagenDatails;
    long mandantId;
    String bestellArt;
}
