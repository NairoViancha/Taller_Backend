package com.ecommerce.taller_backend.service;

import com.ecommerce.taller_backend.entity.Product;
import com.ecommerce.taller_backend.exception.ProductNotFoundException;
import com.ecommerce.taller_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

  
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
    }


    public Product save(Product product) {
        return productRepository.save(product);
    }


    public Product update(Long id, Product productDetails) {
        Product product = findById(id); 
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setCategory(productDetails.getCategory());
        
        return productRepository.save(product);
    }


    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}