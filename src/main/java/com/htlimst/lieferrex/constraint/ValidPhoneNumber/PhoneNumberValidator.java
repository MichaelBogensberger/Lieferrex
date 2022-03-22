package com.htlimst.lieferrex.constraint.ValidPhoneNumber;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {


    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^([+](\\d{1,3})\\s?)?((\\(\\d{3,5}\\)|\\d{3,5})(\\s)?)\\d{3,8}$");
        Matcher matcher = pattern.matcher(s);

        return matcher.matches();
    }
}
