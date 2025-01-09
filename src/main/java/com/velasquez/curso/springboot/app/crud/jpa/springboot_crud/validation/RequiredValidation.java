package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class RequiredValidation implements ConstraintValidator<IsRequired, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
//        return value != null && value.isEmpty() && !value.isBlank(); // Es forma mas larga
        return StringUtils.hasText(value); // Si el value tiene texto, es true, mas corta
    }
}
