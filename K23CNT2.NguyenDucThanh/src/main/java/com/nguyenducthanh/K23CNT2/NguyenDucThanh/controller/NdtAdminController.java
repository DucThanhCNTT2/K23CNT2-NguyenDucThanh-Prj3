package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.*;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.*;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.service.NdtOrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NdtAdminController {

    private final NdtProductRepository productRepo;
    private final NdtCategoryRepository categoryRepo;
    private final NdtOrderRepository orderRepo;
    private final NdtUserRepository userRepo;

    // 1. Thêm Service vào đây để xử lý đơn hàng
    private final NdtOrderService orderService;

    // 2. Cập nhật Constructor để tiêm (Inject) Service vào
    public NdtAdminController(NdtProductRepository productRepo,
                              NdtCategoryRepository categoryRepo,
                              NdtOrderRepository orderRepo,
                              NdtUserRepository userRepo,
                              NdtOrderService orderService) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.orderService = orderService;
    }

    // --- DASHBOARD (Trang chủ Admin) ---
    @GetMapping("/ndt-admin")
    public String dashboard(HttpSession session, Model model) {

        // CHECK ĐĂNG NHẬP + QUYỀN
        NdtUser user = (NdtUser) session.getAttribute("ndtCurrentUser");
        if (user == null) {
            return "redirect:/";
        }
        String roleName = "";
        if (user.getRole() != null) {
            roleName = user.getRole().getRoleName();
        }

        if (!"ADMIN".equalsIgnoreCase(roleName) && !"STAFF".equalsIgnoreCase(roleName)) {
            return "redirect:/";
        }

        // LẤY THỐNG KÊ
        long productCount = productRepo.count();
        long categoryCount = categoryRepo.count();
        long orderCount = orderRepo.count();
        long userCount = userRepo.count();

        model.addAttribute("activeMenu", "dashboard");

        List<NdtOrder> latestOrders = orderRepo.findTop5ByOrderByOrderDateDesc();

        model.addAttribute("currentUser", user);
        model.addAttribute("productCount", productCount);
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("latestOrders", latestOrders);

        return "admin/index";   // Đảm bảo file này là templates/admin/index.html
    }

    // --- QUẢN LÝ ĐƠN HÀNG (Đưa từ class con ra ngoài này) ---

    // 1. Hiển thị danh sách đơn hàng
    @GetMapping("/ndt-admin/orders")
    public String listOrders(Model model) {
        // Lấy danh sách sắp xếp theo ID giảm dần
        List<NdtOrder> list = orderRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("orders", list);
        return "admin/order-list"; // Trỏ đến templates/admin/order-list.html
    }

    // 2. Xử lý cập nhật trạng thái
    @PostMapping("/ndt-admin/orders/update-status")
    public String updateStatus(@RequestParam("id") Long orderId,
                               @RequestParam("status") NdtOrderStatus newStatus) {
        // Gọi Service cập nhật
        orderService.updateOrderStatus(orderId, newStatus);

        // Quay lại trang danh sách
        return "redirect:/ndt-admin/orders";
    }
}