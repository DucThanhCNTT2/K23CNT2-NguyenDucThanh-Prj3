package com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;

public class NdtCartItem {

    private NdtProduct product;   // sản phẩm
    private int quantity;         // số lượng

    private long unitPrice;       // giá 1 sản phẩm (gốc)
    private int discountPercent;  // % giảm giá (0 nếu null)

    public NdtCartItem(NdtProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;

        // product.getPrice() là BigDecimal -> dùng longValue()
        this.unitPrice = product.getPrice() == null
                ? 0L
                : product.getPrice().longValue();

        this.discountPercent =
                product.getDiscountPercent() == null
                        ? 0
                        : product.getDiscountPercent();
    }

    /* --- getter / setter đơn giản --- */

    public NdtProduct getProduct() {
        return product;
    }

    public void setProduct(NdtProduct product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    /* --- các hàm tính tiền --- */

    // tổng gốc = giá * số lượng
    public long getOriginalLineTotal() {
        return unitPrice * quantity;
    }

    // số tiền giảm
    public long getDiscountAmount() {
        return getOriginalLineTotal() * discountPercent / 100;
    }

    // tổng sau giảm
    public long getLineTotal() {
        return getOriginalLineTotal() - getDiscountAmount();
    }
}
