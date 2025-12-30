package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtOrder;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtOrderDetail;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtUser;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderDetailRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class NdtClientOrderController {

    private final NdtOrderRepository orderRepo;
    private final NdtOrderDetailRepository orderDetailRepo;

    public NdtClientOrderController(NdtOrderRepository orderRepo,
                                    NdtOrderDetailRepository orderDetailRepo) {
        this.orderRepo = orderRepo;
        this.orderDetailRepo = orderDetailRepo;
    }

    // Xem danh sách đơn hàng của tôi (Order History)
    @GetMapping("/history")
    public String orderHistory(HttpSession session, Model model) {
        NdtUser user = (NdtUser) session.getAttribute("ndtCurrentUser");
        if (user == null) {
            return "redirect:/"; // Hoặc trang login
        }

        // Tìm các đơn hàng của user này (Sắp xếp mới nhất trước)
        // Lưu ý: Bạn cần đảm bảo OrderRepo có hàm findByUserOrderByOrderDateDesc
        // Hoặc dùng: List<NdtOrder> orders = orderRepo.findByUserId(user.getId());
        List<NdtOrder> orders = orderRepo.findByUserOrderByOrderDateDesc(user);

        model.addAttribute("orders", orders);
        return "admin/order-history";
    }

    // ⭐ XEM CHI TIẾT 1 ĐƠN HÀNG
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable("id") Long orderId,
                              HttpSession session, Model model) {

        // 1. Kiểm tra đăng nhập
        NdtUser user = (NdtUser) session.getAttribute("ndtCurrentUser");
        if (user == null) {
            return "redirect:/";
        }

        // 2. Lấy thông tin đơn hàng
        NdtOrder order = orderRepo.findById(orderId).orElse(null);

        // 3. Kiểm tra bảo mật: Đơn hàng này có phải của người đang đăng nhập không?
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return "redirect:/order/history"; // Không có quyền xem đơn người khác
        }

        // 4. Lấy danh sách sản phẩm trong đơn
        List<NdtOrderDetail> details = orderDetailRepo.findByOrderId(orderId);

        model.addAttribute("order", order);
        model.addAttribute("details", details);

        return "admin/order-detail";
    }
}
