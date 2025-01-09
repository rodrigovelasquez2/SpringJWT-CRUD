package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.validation;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.services.ProductService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IsExistDBValidation implements ConstraintValidator<IsExistDB, String> {
    @Autowired
    private ProductService service;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(service==null){
            return true;
        }
        return !service.existsBySKU(value);
    }
}
