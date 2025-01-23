package com.springboot.ecommerce.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecommerce.entity.Product;
import com.springboot.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    // Admin can add new product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        logger.info("Request to add a new product: {}", product.getProductName());
        return ResponseEntity.ok(productService.addProduct(product));
    }

    // Admin can update any product, but users only have read access
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        logger.info("Request to update product with ID: {}", productId);
        return ResponseEntity.ok(productService.updateProduct(productId, updatedProduct));
    }

    // Admin can delete products, users can't delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        logger.info("Request to delete product with ID: {}", productId);
        try {
            productService.deleteProduct(productId);
            logger.info("Product with ID {} deleted successfully", productId);
        } catch (Exception e) {
            logger.error("Error occurred while deleting product with ID {}: {}", productId, e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    // Admin and users can view all products
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("Request to fetch all products");
        List<Product> products = productService.getAllProducts();
        logger.debug("Number of products fetched: {}", products.size());
        return ResponseEntity.ok(products);
    }

    // Admin and users can view a product by ID
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        logger.info("Request to fetch product by ID: {}", productId);
        return productService.getProductById(productId)
                .map(product -> {
                    logger.debug("Product found: {}", product);
                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> {
                    logger.warn("Product with ID {} not found", productId);
                    return ResponseEntity.notFound().build();
                });
    }

    // Admin and users can view products by category
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        logger.info("Request to fetch products by category: {}", category);
        List<Product> products = productService.getProductsByCategory(category);
        logger.debug("Number of products in category {}: {}", category, products.size());
        return ResponseEntity.ok(products);
    }

    // Admin and users can view products within a price range
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/price")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        logger.info("Request to fetch products within price range: {} - {}", minPrice, maxPrice);
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        logger.debug("Number of products in price range {} - {}: {}", minPrice, maxPrice, products.size());
        return ResponseEntity.ok(products);
    }
}
