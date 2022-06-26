package com.htlimst.lieferrex.dto;

import com.htlimst.lieferrex.constraint.ValidPhoneNumber.ValidPhoneNumber;
import com.htlimst.lieferrex.constraint.fieldMatch.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
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

    @ValidPhoneNumber
    private String telefonnummer;

    private String adresse;

    //from Adresse
    @NotEmpty
    private String ort;

    private String plz;

    @NotEmpty
    private String strasse;

    @NotEmpty
    private String hausnummer;

    @NotEmpty
    private String land;

    @NotEmpty
    private String placeId;

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

    @AssertTrue
    private boolean agb;

    private boolean newsletter;

}
