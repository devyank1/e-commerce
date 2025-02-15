package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.CategoryDTO;
import com.dev.yank.ecommerce.exception.CategoryNotFoundException;
import com.dev.yank.ecommerce.mapper.CategoryMapper;
import com.dev.yank.ecommerce.model.Category;
import com.dev.yank.ecommerce.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    // Injection
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    // Methods
    public List<CategoryDTO> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO).toList();
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO newCategory) {
        Category category = categoryMapper.toEntity(newCategory);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO updateCategory) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        existingCategory.setName(updateCategory.name());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toDTO(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }
    }
}
