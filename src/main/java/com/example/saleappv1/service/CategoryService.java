package com.example.saleappv1.service;

import thjava.thbuoi2.models.Category;
import thjava.thbuoi2.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new IllegalStateException("Danh mục ID " + category.getId() + " không tồn tại."));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalStateException("Danh mục ID " + id + " không tồn tại.");
        }
        categoryRepository.deleteById(id);
    }
}