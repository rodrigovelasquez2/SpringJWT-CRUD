package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.validation;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername,String> {

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(service==null){
            return true;
        }
        //Si no existe en el username es por que no esta creado en la base de datos.
        return !service.existsByUsername(username);
    }
}
