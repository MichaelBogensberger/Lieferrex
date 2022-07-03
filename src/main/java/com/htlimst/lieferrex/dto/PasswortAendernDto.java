package com.htlimst.lieferrex.dto;



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
public class PasswortAendernDto {

    @NotEmpty
    private String altesPasswort;

    @NotEmpty
    @Size(min = 8)
    private String passwort;

    @NotEmpty
    @Size(min = 8)
    private String confirmPasswort;
}
