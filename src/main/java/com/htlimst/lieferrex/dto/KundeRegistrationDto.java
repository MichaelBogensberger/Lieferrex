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

    @NotEmpty
    private String ort;

    @Min(3)
    private String plz;

    @NotEmpty
    private String strasse;

    @NotEmpty
    private String hausnummer;

    @ValidPhoneNumber
    private String telefonnummer;

    @NotEmpty
    private String land;

    @AssertTrue
    private boolean agb;

    private boolean newsletter;

}
