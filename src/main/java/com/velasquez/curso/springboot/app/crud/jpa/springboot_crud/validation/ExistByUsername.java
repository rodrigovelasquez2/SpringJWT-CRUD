package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy =ExistByUsernameValidation.class )
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistByUsername {
    String message() default "Ya existe en la base de datos, escoja otro username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
