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

    // key = productId
    private final Map<Long, NdtCartItem> items = new LinkedHashMap<>();

    public NdtCartService(NdtProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    // Lấy toàn bộ item trong giỏ
    public Collection<NdtCartItem> getItems() {
        return items.values();
    }

    // Số dòng trong giỏ
    public int getItemCount() {
        return items.size();
    }

    // Giỏ rỗng?
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Thêm sản phẩm (tăng số lượng nếu đã tồn tại)
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

    // Cập nhật số lượng (0 hoặc <0 => remove)
    public void updateQuantity(Long productId, int qty) {
        if (!items.containsKey(productId)) return;

        if (qty <= 0) {
            items.remove(productId);
        } else {
            items.get(productId).setQuantity(qty);
        }
    }

    // Xoá 1 sản phẩm khỏi giỏ
    public void removeProduct(Long productId) {
        items.remove(productId);
    }

    // Xoá sạch giỏ
    public void clear() {
        items.clear();
    }

    // ⭐ Tổng tiền giỏ hàng (sau giảm giá)
    public long getTotal() {
        return items.values().stream()
                .mapToLong(NdtCartItem::getLineTotal)
                .sum();
    }

    // (tuỳ chọn) Tổng tiền gốc chưa giảm
    public long getSubTotal() {
        return items.values().stream()
                .mapToLong(NdtCartItem::getOriginalLineTotal)
                .sum();
    }

    // (tuỳ chọn) Tổng tiền giảm
    public long getTotalDiscount() {
        return getSubTotal() - getTotal();
    }
}