package com.nguyenducthanh.K23CNT2.NguyenDucThanh.service;


import com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto.NdtCartItem;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NdtCartService {

    private final NdtProductRepository productRepo;

    private final Map<Long, NdtCartItem> items = new LinkedHashMap<>();

    public NdtCartService(NdtProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Collection<NdtCartItem> getItems() {
        return items.values();
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void addProduct(Long productId, int qty) {
        if (qty <= 0) return;

        NdtCartItem item = items.get(productId);
        if (item == null) {
            NdtProduct product = productRepo.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
            item = new NdtCartItem(product, 0);
            items.put(productId, item);
        }
        item.setQuantity(item.getQuantity() + qty);
    }

    public void updateQuantity(Long productId, int qty) {
        if (!items.containsKey(productId)) return;

        if (qty <= 0) {
            items.remove(productId);
        } else {
            items.get(productId).setQuantity(qty);
        }
    }

    public void removeProduct(Long productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public long getTotal() {
        return items.values().stream()
                .mapToLong(NdtCartItem::getLineTotal)
                .sum();
    }

    public long getSubTotal() {
        return items.values().stream()
                .mapToLong(NdtCartItem::getOriginalLineTotal)
                .sum();
    }

    public long getTotalDiscount() {
        return getSubTotal() - getTotal();
    }
}