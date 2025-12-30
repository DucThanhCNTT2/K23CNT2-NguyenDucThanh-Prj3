package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtOrder;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtOrderStatus;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.service.NdtOrderService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/ndt-admin/orders")
public class NdtAdminOrderController {

    private final NdtOrderRepository orderRepo;
    private final NdtOrderService orderService;

    public NdtAdminOrderController(NdtOrderRepository orderRepo, NdtOrderService orderService) {
        this.orderRepo = orderRepo;
        this.orderService = orderService;
    }

    // --------- LIST ĐƠN HÀNG ----------
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("activeMenu", "order");

        // nếu chưa tạo hàm findAllByOrderByOrderDateDesc()
        // thì có thể dùng findAll() cũng được
        model.addAttribute("orders",
                orderRepo.findAllByOrderByOrderDateDesc());

        return "admin/order-list";    // templates/admin/order-list.html
    }

    // --------- CHI TIẾT ĐƠN HÀNG ----------
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        NdtOrder order = orderRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("activeMenu", "order");
        model.addAttribute("order", order);
        return "admin/order-detail";  // templates/admin/order-detail.html
    }

    // --- QUẢN LÝ ĐƠN HÀNG (Đưa từ class con ra ngoài này) ---


    // --------- XỬ LÝ CẬP NHẬT TRẠNG THÁI ----------

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam("id") Long orderId,
                               @RequestParam("status") NdtOrderStatus newStatus) {

        // Gọi Service cập nhật
        orderService.updateOrderStatus(orderId, newStatus);

        // 🔥 QUAN TRỌNG: Phải dùng "redirect" để load lại trang mới,
        // nếu dùng return "admin/order-list" thì dữ liệu cũ vẫn còn đó.
        return "redirect:/ndt-admin/orders";
    }
}