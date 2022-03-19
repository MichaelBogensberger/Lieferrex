package com.htlimst.lieferrex.dto;

import com.htlimst.lieferrex.service.security.constraint.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldMatch.List({
        @FieldMatch(first = "passwort", second = "confirmPasswort", message = "Passwörter müssen übereinstimmen"),
})
public class MandantRegistrationDto {

    @NotEmpty
    private String firmenname;

    @NotEmpty
    @Email
    private String firmenemail;


    private double mindestbestellwert;


    private double lieferkosten;

    @NotEmpty
    private String telefonnummer;

    @NotEmpty
    private String ort;


    private int plz;

    @NotEmpty
    private String strasse;

    @NotEmpty
    private String hausnummer;

    @NotEmpty
    private String land;

    @NotEmpty
    private String vorname;

    @NotEmpty
    private String nachname;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8)
    private String passwort;

    @NotEmpty
    @Size(min = 8)
    private String confirmPasswort;

    private boolean agb;
    private boolean newsletter;

}
