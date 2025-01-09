package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.services;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    boolean existsByUsername(String username);

}
