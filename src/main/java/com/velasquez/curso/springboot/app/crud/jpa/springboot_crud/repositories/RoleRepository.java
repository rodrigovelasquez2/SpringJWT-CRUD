package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.repositories;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
