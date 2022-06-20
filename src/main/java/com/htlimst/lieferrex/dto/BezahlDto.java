package com.htlimst.lieferrex.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BezahlDto {

    private double price;
    private String method;
    private String intent;
    private String description;

}
