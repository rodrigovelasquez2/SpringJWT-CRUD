package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.controller;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.Product;
import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.services.ProductService;
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
import java.util.Optional;

@RestController()
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

//    @Autowired
//    private ProductValidation productValidation;

    // Trae todos los productos en una lista de Productos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Product> listProduct() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id); // Busca el producto
        if (productOptional.isPresent()) { // si esta presente
            return ResponseEntity.ok(productOptional.orElseThrow()); // 202 OK
        }
        return ResponseEntity.notFound().build(); // 404 NOT FOUND
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody @Valid Product product, BindingResult result) {
//        productVal idation.validate(product, result);

        if (result.hasFieldErrors()) { //Si hay etiquetas de erroes
            return validation(result);
        }
        Product productNew = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productNew); // Guardar el nuevo producto
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {
//        productValidation.validate(product, result);

        if (result.hasFieldErrors()) { //Si hay etiquetas de erroes
            return validation(result);
        }
        Optional<Product> productOptional = productService.update(product, id);
        if (productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build(); // No se encontro
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Product> productOptional = productService.delete(id); // Busca el producto
        if (productOptional.isPresent()) { // si el producto existe
            return ResponseEntity.ok(productOptional.orElseThrow()); // Mandar un OK
        }
        return ResponseEntity.notFound().build(); // No se encontro
    }


    //Metodos aparte:
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }//validation

}//Fin ProductController
