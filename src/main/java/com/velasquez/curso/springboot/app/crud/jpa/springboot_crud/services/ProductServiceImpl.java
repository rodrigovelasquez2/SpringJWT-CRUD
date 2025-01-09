package com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.services;

import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.entities.Product;
import com.velasquez.curso.springboot.app.crud.jpa.springboot_crud.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }//findAll


    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }//findById


    @Transactional
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }//save

    @Transactional
    @Override
    public Optional<Product> update(Product product, Long id) {
        //Buscar en BD
        Optional<Product> productOptional = productRepository.findById(id); // Obtener el id del producto

        if(productOptional.isPresent()) { // Existe?
            Product productDb = productOptional.orElseThrow();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setDescription(product.getDescription());
            productDb.setSKU(product.getSKU());
            return Optional.of(productRepository.save(productDb));
        }

        return productOptional; //Retornar los productos.
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        //Buscar en BD
        Optional<Product> productOptional = productRepository.findById(id); // Obtener el id del producto
        productOptional.ifPresent(productDb -> { // Existe?
            productRepository.delete(productDb); // Eliminar
        });

        return productOptional; //Retornar los productos.
    }//delete

    @Override
    @Transactional (readOnly = true)
    public boolean existsBySKU(String SKU) {
        return productRepository.existsBySKU(SKU);
    }
}
