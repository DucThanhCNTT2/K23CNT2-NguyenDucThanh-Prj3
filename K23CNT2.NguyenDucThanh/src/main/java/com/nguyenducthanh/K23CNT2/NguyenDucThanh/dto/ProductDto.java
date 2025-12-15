package com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;          // giá gốc
    private Integer discountPercent;   // % giảm
    private BigDecimal finalPrice;     // giá sau giảm
    private String imgUrl;

    public ProductDto(NdtProduct p) {
        this.id = p.getId();
        this.name = p.getName();
        this.price = p.getPrice();
        this.discountPercent = p.getDiscountPercent();
        this.imgUrl = p.getImgUrl();

        if (discountPercent != null && discountPercent > 0) {
            // final = price * (100 - discount)/100
            this.finalPrice = price
                    .multiply(BigDecimal.valueOf(100 - discountPercent))
                    .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        } else {
            this.finalPrice = price;
        }
    }

        // getter / setter (có thể dùng Lombok @Data nếu bạn biết)
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public  void setName(String name) { this.name = name; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }

        public String getImgUrl() { return imgUrl; }
        public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    }

