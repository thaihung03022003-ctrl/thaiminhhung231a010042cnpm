package thjava.thbuoi2.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String shippingAddress;

    private String phoneNumber;

    // Quan hệ 1-Nhiều với OrderDetail: 1 đơn hàng có nhiều chi tiết đơn hàng
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}