package com.example.saleappv1.controller;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.example.saleappv1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String products(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double fromPrice,
            @RequestParam(required = false) Double toPrice,
            Model model) {

        List<Product> productList = productService.filterProducts(categoryId, keyword, fromPrice, toPrice);
        List<Category> categoryList = productService.getAllCategories();

        model.addAttribute("products", productList);
        model.addAttribute("categories", categoryList);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("fromPrice", fromPrice);
        model.addAttribute("toPrice", toPrice);

        // Tên danh mục đang lọc (nếu có)
        String currentCategoryName = "Tất cả sản phẩm";
        if (categoryId != null && categoryId > 0) {
            Category cat = productService.getCategoryById(categoryId);
            if (cat != null) {
                currentCategoryName = cat.getName();
            }
        }
        model.addAttribute("currentCategoryName", currentCategoryName);

        return "products";
    }

    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable Integer productId, Model model) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            model.addAttribute("errorMessage", "Không tìm thấy sản phẩm có mã: " + productId);
            return "redirect:/products";
        }

        Category category = productService.getCategoryById(product.getCategoryId());
        model.addAttribute("product", product);
        model.addAttribute("categoryName", category != null ? category.getName() : "Khác");
        model.addAttribute("categories", productService.getAllCategories());

        return "product-detail";
    }
}
