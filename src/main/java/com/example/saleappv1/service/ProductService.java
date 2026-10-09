package com.example.saleappv1.service;

import thjava.thbuoi2.models.Product;
import thjava.thbuoi2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Sản phẩm ID " + product.getId() + " không tồn tại."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        if (product.getImage() != null && !product.getImage().trim().isEmpty()) {
            existingProduct.setImage(product.getImage());
        }
        existingProduct.setCategory(product.getCategory());
        return productRepository.save(existingProduct);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Sản phẩm ID " + id + " không tồn tại.");
        }
        productRepository.deleteById(id);
    }
}
