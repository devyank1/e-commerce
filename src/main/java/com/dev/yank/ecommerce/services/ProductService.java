package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.ProductDTO;
import com.dev.yank.ecommerce.exception.ProductNotFoundException;
import com.dev.yank.ecommerce.mapper.ProductMapper;
import com.dev.yank.ecommerce.model.Product;
import com.dev.yank.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<ProductDTO> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toDTO);
    }

    public ProductDTO getProductsById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO newProduct) {
        Product product = productMapper.toEntity(newProduct);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Transactional
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

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }
}
