package com.htlimst.lieferrex.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MandantSuchDto {
    private String firmenname;
    private String ort;
    private String adresse;
    private int rating;
}
