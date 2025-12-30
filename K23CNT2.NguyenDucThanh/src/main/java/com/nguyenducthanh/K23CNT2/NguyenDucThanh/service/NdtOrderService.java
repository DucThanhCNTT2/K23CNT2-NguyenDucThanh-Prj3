package com.nguyenducthanh.K23CNT2.NguyenDucThanh.service;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto.NdtCartItem;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.*;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderDetailRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository; // ⭐ Cần thêm cái này để trừ kho
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NdtOrderService {

    private final NdtOrderRepository orderRepo;
    private final NdtOrderDetailRepository orderDetailRepo;
    private final NdtProductRepository productRepo; // ⭐ Khai báo thêm Repository sản phẩm

    // Tiêm (Inject) đủ 3 Repository vào Constructor
    public NdtOrderService(NdtOrderRepository orderRepo,
                           NdtOrderDetailRepository orderDetailRepo,
                           NdtProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.orderDetailRepo = orderDetailRepo;
        this.productRepo = productRepo;
    }

    // --- 1. CHỨC NĂNG ĐẶT HÀNG (Dành cho khách) ---
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
        order.setStatus(NdtOrderStatus.PENDING); // Mặc định là Chờ xác nhận

        // Lưu Order trước để lấy ID
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

    // --- 2. CHỨC NĂNG CẬP NHẬT TRẠNG THÁI & QUẢN LÝ KHO (Dành cho Admin) ---
    @Transactional
    public void updateOrderStatus(Long orderId, NdtOrderStatus newStatus) {
        NdtOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy đơn hàng ID: " + orderId));

        NdtOrderStatus oldStatus = order.getStatus();

        // LOGIC 1: BÁN HÀNG (Chuyển sang DELIVERED -> Trừ kho)
        if (newStatus == NdtOrderStatus.DELIVERED && oldStatus != NdtOrderStatus.DELIVERED) {
            List<NdtOrderDetail> details = orderDetailRepo.findByOrderId(orderId);
            for (NdtOrderDetail detail : details) {
                NdtProduct product = detail.getProduct();
                // Trừ số lượng tồn kho
                product.setQuantity(product.getQuantity() - detail.getQuantity());
                productRepo.save(product);
            }
        }

        // LOGIC 2: HOÀN HÀNG (Đang DELIVERED mà chuyển sang CANCELED -> Cộng lại kho)
        else if (oldStatus == NdtOrderStatus.DELIVERED && newStatus == NdtOrderStatus.CANCELED) {
            List<NdtOrderDetail> details = orderDetailRepo.findByOrderId(orderId);
            for (NdtOrderDetail detail : details) {
                NdtProduct product = detail.getProduct();
                // Cộng lại số lượng vào kho
                product.setQuantity(product.getQuantity() + detail.getQuantity());
                productRepo.save(product);
            }
        }

        // Cập nhật trạng thái mới cho đơn hàng
        order.setStatus(newStatus);
        orderRepo.save(order);
    }
}