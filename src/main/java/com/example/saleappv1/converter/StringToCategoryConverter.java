package com.example.saleappv1.converter;

import thjava.thbuoi2.models.Category;
import thjava.thbuoi2.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        try {
            Long id = Long.parseLong(source.trim());
            return categoryRepository.findById(id).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
