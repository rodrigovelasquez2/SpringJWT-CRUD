package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.repositories;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
