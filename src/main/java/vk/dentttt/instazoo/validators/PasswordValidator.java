package vk.dentttt.instazoo.validators;

import vk.dentttt.instazoo.annotations.ValidatedPasswords;
import vk.dentttt.instazoo.dtos.SignupDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidatedPasswords, Object> {

    @Override
    public void initialize(ValidatedPasswords constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        SignupDto signupDto = (SignupDto) value;
        return signupDto.getPassword().equals(signupDto.getConfirmPassword());
    }

}
