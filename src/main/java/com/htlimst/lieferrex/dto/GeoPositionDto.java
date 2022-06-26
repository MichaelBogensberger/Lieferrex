package com.htlimst.lieferrex.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GeoPositionDto {
    private double lat;
    private double lng;
    private String plz;
}
