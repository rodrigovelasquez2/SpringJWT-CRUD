package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.services;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    Optional<Product> update(Product product, Long id);
    Optional<Product> delete (Long id);
    boolean existsBySKU(String SKU);


}//Fin ProductService
