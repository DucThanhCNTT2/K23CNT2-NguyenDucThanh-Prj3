package com.nguyenducthanh.K23CNT2.NguyenDucThanh.service;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto.NdtCartItem;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.*;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderDetailRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NdtOrderService {

    private final NdtOrderRepository orderRepo;
    private final NdtOrderDetailRepository orderDetailRepo;

    // Bỏ NdtCartService, ta sẽ nhận dữ liệu trực tiếp từ Controller
    public NdtOrderService(NdtOrderRepository orderRepo,
                           NdtOrderDetailRepository orderDetailRepo) {
        this.orderRepo = orderRepo;
        this.orderDetailRepo = orderDetailRepo;
    }

    @Transactional
    public NdtOrder placeOrder(NdtUser user, List<NdtCartItem> cartItems) {

        // 1. Kiểm tra giỏ hàng
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalStateException("Giỏ hàng đang trống");
        }

        // 2. Tính tổng tiền
        double total = 0;
        for (NdtCartItem item : cartItems) {
            total += item.getLineTotal();
        }

        // 3. Tạo Order
        NdtOrder order = new NdtOrder();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.valueOf(total));

        // 🔥 SỬA LỖI Ở ĐÂY: Dùng Enum thay vì String
        order.setStatus(NdtOrderStatus.PENDING);

        // Lưu Order để lấy ID
        order = orderRepo.save(order);

        // 4. Lưu chi tiết đơn hàng (Order Details)
        for (NdtCartItem item : cartItems) {
            NdtOrderDetail detail = new NdtOrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());

            // Lấy giá và convert sang BigDecimal
            BigDecimal unitPrice = BigDecimal.valueOf(item.getUnitPrice());
            detail.setUnitPrice(unitPrice);

            orderDetailRepo.save(detail);
        }

        return order;
    }
    public void updateOrderStatus(Long orderId, NdtOrderStatus newStatus) {
        NdtOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy đơn hàng ID: " + orderId));

        order.setStatus(newStatus);
        orderRepo.save(order);
    }
}