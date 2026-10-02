package thjava.thbuoi2.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Quan hệ 1-Nhiều với Product: 1 danh mục có nhiều sản phẩm
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}