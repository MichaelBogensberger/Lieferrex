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
public class UserRegistrationDto {

    private String firstName;
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, message = "muss mehr als 8 Zeichen lang sein")
    private String password;

    @NotEmpty
    @Size(min = 8, message = "muss mehr als 8 Zeichen lang sein")
    private String confirmPassword;

}
