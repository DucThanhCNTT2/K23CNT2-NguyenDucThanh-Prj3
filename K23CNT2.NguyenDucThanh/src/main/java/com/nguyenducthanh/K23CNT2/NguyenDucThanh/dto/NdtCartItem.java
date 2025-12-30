package com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;

public class NdtCartItem {

    private NdtProduct product;
    private int quantity;

    private long unitPrice;
    private int discountPercent;

    public NdtCartItem(NdtProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;

        this.unitPrice = product.getPrice() == null
                ? 0L
                : product.getPrice().longValue();

        this.discountPercent =
                product.getDiscountPercent() == null
                        ? 0
                        : product.getDiscountPercent();
    }


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

    public long getOriginalLineTotal() {
        return unitPrice * quantity;
    }

    public long getDiscountAmount() {
        return getOriginalLineTotal() * discountPercent / 100;
    }

    public long getLineTotal() {
        return getOriginalLineTotal() - getDiscountAmount();
    }
}
