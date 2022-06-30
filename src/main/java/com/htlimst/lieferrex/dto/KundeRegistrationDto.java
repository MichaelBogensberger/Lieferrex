package com.htlimst.lieferrex.dto;

import com.htlimst.lieferrex.constraint.ValidPhoneNumber.ValidPhoneNumber;
import com.htlimst.lieferrex.constraint.fieldMatch.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldMatch.List({
        @FieldMatch(first = "passwort", second = "confirmPasswort", message = "Passwörter müssen übereinstimmen"),
})
public class KundeRegistrationDto {

    @NotEmpty(message = "Vorname darf nicht leer sein")
    private String vorname;

    @NotEmpty(message = "Nachname darf nicht leer sein")
    private String nachname;

    @NotEmpty(message = "Email darf nicht leer sein")
    @Email
    private String email;

    @NotEmpty(message = "Passwort darf nicht leer sein")
    @Size(min = 8)
    private String passwort;

    @NotEmpty(message = "Prüf Passwort darf nicht leer sein")
    @Size(min = 8)
    private String confirmPasswort;

    @NotEmpty(message = "Adresse darf nicht leer sein")
    private String adresse;

    private String ort;

    private String placeId;

    private String land;

    private String plz;

    private String strasse;

    private String hausnummer;

    @ValidPhoneNumber
    private String telefonnummer;


    @AssertTrue(message = "Agb's müssen angekreutzt sein")
    private boolean agb;

    private boolean newsletter;

}
