package com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "ndt_products")
@Data
@Getter
@Setter
public class NdtProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ndt_product_id")
    private Long id;

    @Column(name = "ndt_name", nullable = false, length = 200)
    private String name;

    @Column(name = "ndt_price", nullable = false)
    private BigDecimal price;

    @Column(name = "ndt_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ndt_img_url")
    private String imgUrl;

    @Column(name = "ndt_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "ndt_is_active")
    private Boolean isActive = true;

    @Column(name = "ndt_discount_percent")
    private Integer discountPercent;

    @ManyToOne
    @JoinColumn(name = "ndt_category_id")
    private NdtCategory category;

    public NdtProduct() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public NdtCategory getCategory() {
        return category;
    }

    public void setCategory(NdtCategory category) {
        this.category = category;
    }

    public Integer getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }

    // ✅ GIÁ SAU GIẢM (tự tính)
    @Transient
    public BigDecimal getDiscountedPrice() {
        if (price == null) return null;
        if (discountPercent == null || discountPercent <= 0) return price;

        int percentLeft = 100 - discountPercent; // còn lại sau giảm
        return price
                .multiply(BigDecimal.valueOf(percentLeft))
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP); // làm tròn 0 số lẻ (VND)
    }
}
