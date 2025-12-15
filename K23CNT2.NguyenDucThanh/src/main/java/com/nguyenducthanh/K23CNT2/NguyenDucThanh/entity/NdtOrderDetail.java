package com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ndt_order_details")
public class NdtOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ndt_order_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ndt_order_id")
    private NdtOrder order;

    @ManyToOne
    @JoinColumn(name = "ndt_product_id")
    private NdtProduct product;

    @Column(name = "ndt_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ndt_unit_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    public NdtOrderDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NdtOrder getOrder() {
        return order;
    }

    public void setOrder(NdtOrder order) {
        this.order = order;
    }

    public NdtProduct getProduct() {
        return product;
    }

    public void setProduct(NdtProduct product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
