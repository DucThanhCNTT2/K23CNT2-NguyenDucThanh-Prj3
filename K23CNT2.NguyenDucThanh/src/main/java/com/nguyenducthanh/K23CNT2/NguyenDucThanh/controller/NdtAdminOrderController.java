package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtOrder;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/ndt-admin/orders")
public class NdtAdminOrderController {

    private final NdtOrderRepository orderRepo;

    public NdtAdminOrderController(NdtOrderRepository orderRepo) {
        this.orderRepo = orderRepo;
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
}