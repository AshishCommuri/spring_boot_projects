package com.springboot.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.entity.Product;
import com.springboot.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        logger.info("Adding new product: {}", product.getProductName());
        Product savedProduct = productRepository.save(product);
        logger.info("Product '{}' added successfully with ID: {}", savedProduct.getProductName(), savedProduct.getProductId());
        return savedProduct;
    }

    public List<Product> getAllProducts() {
        logger.info("Fetching all products.");
        List<Product> products = productRepository.findAll();
        logger.info("Found {} product(s).", products.size());
        return products;
    }

    public Optional<Product> getProductById(Long productId) {
        logger.info("Fetching product with ID: {}", productId);
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            logger.info("Product found: {}", product.get().getProductName());
        } else {
            logger.warn("Product with ID {} not found.", productId);
        }
        return product;
    }

    public List<Product> getProductsByCategory(String category) {
        logger.info("Fetching products in category: {}", category);
        List<Product> products = productRepository.findByCategory(category);
        logger.info("Found {} product(s) in category '{}'.", products.size(), category);
        return products;
    }

    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        logger.info("Fetching products in price range: {} - {}", minPrice, maxPrice);
        List<Product> products = productRepository.findByPriceRange(minPrice, maxPrice);
        logger.info("Found {} product(s) in price range {} - {}.", products.size(), minPrice, maxPrice);
        return products;
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        logger.info("Updating product with ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("Product with ID {} not found.", productId);
                    return new IllegalArgumentException("Product not found");
                });

        logger.debug("Updating product details for product ID: {}", productId);
        product.setProductName(updatedProduct.getProductName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        product.setStockQuantity(updatedProduct.getStockQuantity());

        Product savedProduct = productRepository.save(product);
        logger.info("Product with ID {} updated successfully.", productId);
        return savedProduct;
    }

    public void deleteProduct(Long productId) {
        logger.info("Deleting product with ID: {}", productId);
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            logger.info("Product with ID {} deleted successfully.", productId);
        } else {
            logger.warn("Product with ID {} does not exist. Deletion skipped.", productId);
        }
    }
}
