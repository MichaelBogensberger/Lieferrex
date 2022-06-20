package com.htlimst.lieferrex.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MandantSuchDto {
    private Long id;
    private String firmenname;
    private String ort;
    private String plz;
    private String adresse;
    private int rating;


    private boolean geöffnet;
}
