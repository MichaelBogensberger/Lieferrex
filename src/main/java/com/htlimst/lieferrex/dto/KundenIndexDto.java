package com.htlimst.lieferrex.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class KundenIndexDto {

    private Long kundenid;
    private String kundenname;
    private String kundennameNachname;
    private String initialen;
    private String StrasseHausnummer;
    private String PlzOrt;
}
