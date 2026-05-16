package com.ecommerce.taller_backend.controller;

import com.ecommerce.taller_backend.entity.Product;
import com.ecommerce.taller_backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "API para la gestión de productos del ecommerce")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Listar productos con paginación", 
               description = "Obtiene una lista paginada. Use 'page' (desde 0) y 'size'.")
    @GetMapping
    public ResponseEntity<Page<Product>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado para actualizar"),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @Operation(summary = "Eliminar un producto")
    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}