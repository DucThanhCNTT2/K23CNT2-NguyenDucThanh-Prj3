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
    private final NdtProductRepository productRepo;

    public NdtOrderService(NdtOrderRepository orderRepo,
                           NdtOrderDetailRepository orderDetailRepo,
                           NdtProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.orderDetailRepo = orderDetailRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public NdtOrder placeOrder(NdtUser user, List<NdtCartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalStateException("Giỏ hàng đang trống");
        }
        double total = 0;
        for (NdtCartItem item : cartItems) {
            total += item.getLineTotal();
        }

        NdtOrder order = new NdtOrder();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.valueOf(total));
        order.setStatus(NdtOrderStatus.PENDING);

        order = orderRepo.save(order);

        for (NdtCartItem item : cartItems) {
            NdtOrderDetail detail = new NdtOrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());

            BigDecimal unitPrice = BigDecimal.valueOf(item.getUnitPrice());
            detail.setUnitPrice(unitPrice);

            orderDetailRepo.save(detail);
        }

        return order;
    }

    @Transactional
    public void updateOrderStatus(Long orderId, NdtOrderStatus newStatus) {
        NdtOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy đơn hàng ID: " + orderId));

        NdtOrderStatus oldStatus = order.getStatus();

        if (newStatus == NdtOrderStatus.DELIVERED && oldStatus != NdtOrderStatus.DELIVERED) {
            List<NdtOrderDetail> details = orderDetailRepo.findByOrderId(orderId);
            for (NdtOrderDetail detail : details) {
                NdtProduct product = detail.getProduct();
                product.setQuantity(product.getQuantity() - detail.getQuantity());
                productRepo.save(product);
            }
        }

        else if (oldStatus == NdtOrderStatus.DELIVERED && newStatus == NdtOrderStatus.CANCELED) {
            List<NdtOrderDetail> details = orderDetailRepo.findByOrderId(orderId);
            for (NdtOrderDetail detail : details) {
                NdtProduct product = detail.getProduct();
                product.setQuantity(product.getQuantity() + detail.getQuantity());
                productRepo.save(product);
            }
        }

        order.setStatus(newStatus);
        orderRepo.save(order);
    }
}