package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.ProductDTO;
import com.dev.yank.ecommerce.exception.ProductNotFoundException;
import com.dev.yank.ecommerce.mapper.ProductMapper;
import com.dev.yank.ecommerce.model.Product;
import com.dev.yank.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    // Injection
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // Methods
    public List<ProductDTO> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO).toList();
    }

    public ProductDTO getProductsById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        return productMapper.toDTO(product);
    }

    public ProductDTO createProduct(ProductDTO newProduct) {
        Product product = productMapper.toEntity(newProduct);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO updateProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        existingProduct.setName(updateProduct.name());
        existingProduct.setDescription(updateProduct.description());
        existingProduct.setPrice(updateProduct.price());
        existingProduct.setStockQuantity(updateProduct.stockQuantity());

        Product productUpdated = productRepository.save(existingProduct);
        return productMapper.toDTO(productUpdated);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }
}
