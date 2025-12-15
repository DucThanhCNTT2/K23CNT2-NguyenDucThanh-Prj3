package com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="ndt_orders")
public class NdtOrder {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ndt_order_id")
    private Long id;

    @Column(name = "ndt_order_date", nullable = false)
    private LocalDateTime orderDate;  // ndt_order_date (DATETIME)

    @Column(name = "ndt_total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;   // ndt_total_amount (DECIMAL)

    @Enumerated(EnumType.STRING)
    @Column(name = "ndt_status", length = 20)
    private NdtOrderStatus status;   // <--- THÊM

    // khóa ngoại tới ndt_users.ndt_user_id
    @ManyToOne
    @JoinColumn(name = "ndt_user_id")
    private NdtUser user;

    // Quan hệ 1 đơn hàng - N chi tiết đơn hàng (nếu bạn có bảng ndt_order_details)
    @OneToMany(mappedBy = "order")
    private List<NdtOrderDetail> orderDetails;

    public NdtOrder() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public NdtOrderStatus getStatus() {
        return status;
    }

    public void setStatus(NdtOrderStatus status) {
        this.status = status;
    }

    public NdtUser getUser() {
        return user;
    }

    public void setUser(NdtUser user) {
        this.user = user;
    }

    public List<NdtOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<NdtOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
