package com.htlimst.lieferrex.dto;

import com.htlimst.lieferrex.service.security.constraint.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "Passwörter müssen übereinstimmen"),
})
public class KundeRegistrationDto {

    private String vorname;
    private String nachname;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, message = "muss mehr als 8 Zeichen lang sein")
    private String passwort;

    @NotEmpty
    @Size(min = 8, message = "muss mehr als 8 Zeichen lang sein")
    private String confirmPasswort;

    private String ort;

    @Size(min=4,max = 6, message = "muss mehr als 8 Zeichen lang sein")
    private Integer plz;
    private String strasse;
    private String hausnummer;
    private Integer telefonnummer;
    private String land;
    private boolean newsletter;
    private boolean agb;

}
