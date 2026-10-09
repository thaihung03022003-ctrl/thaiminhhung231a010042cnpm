package com.example.saleappv1.controller;

import com.example.saleappv1.service.CategoryService;
import com.example.saleappv1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
}
