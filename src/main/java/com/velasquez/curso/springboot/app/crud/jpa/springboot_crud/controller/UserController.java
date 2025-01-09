package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.controller;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.User;
import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) { // Si hay Errores?
            return validation(result); // Retornar los errores
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }//create


    //Registra un usuario que no SEA ADMIN
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) { // Si hay Errores?
            return validation(result); // Retornar los errores
        }
        user.setAdmin(false);
        return create(user,result);
    }//register

    //Metodos aparte:
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }//validation
}
