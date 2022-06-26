package com.htlimst.lieferrex.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AdressDto {
    private String adresse;
    private String land;
    private String ort;
    private String strasse;
    private String hausnummer;
    private String placeId;
}
