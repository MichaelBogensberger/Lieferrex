package com.htlimst.lieferrex.constraint.ValidPhoneNumber;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {
    String message() default "keine Valide Telefonnummer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
