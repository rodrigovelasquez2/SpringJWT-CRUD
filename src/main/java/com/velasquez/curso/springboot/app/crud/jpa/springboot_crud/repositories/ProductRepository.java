package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.repositories;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    boolean existsBySKU(String SKU);

}//ProductRepository
