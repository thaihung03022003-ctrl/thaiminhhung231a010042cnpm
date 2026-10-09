package com.example.saleappv1.controller;

import thjava.thbuoi2.models.Product;
import com.example.saleappv1.service.CategoryService;
import com.example.saleappv1.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // 1. Hiển thị danh sách sản phẩm
    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/products-list";
    }

    // 2. Hiển thị form thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/add-product";
    }

    // 3. Xử lý thêm mới kèm tải lên hình ảnh
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "products/add-product";
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = saveImageFile(imageFile);
                product.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productService.addProduct(product);
        return "redirect:/products";
    }

    // 4. Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID sản phẩm không hợp lệ: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/update-product";
    }

    // 5. Xử lý cập nhật kèm tải lên hình ảnh mới
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid Product product, BindingResult result,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "products/update-product";
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = saveImageFile(imageFile);
                product.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        product.setId(id);
        productService.updateProduct(product);
        return "redirect:/products";
    }

    // 6. Xử lý xóa
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    private String saveImageFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "image.jpg");
        String fileName = UUID.randomUUID().toString() + "_" + originalFilename;
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return fileName;
    }
}