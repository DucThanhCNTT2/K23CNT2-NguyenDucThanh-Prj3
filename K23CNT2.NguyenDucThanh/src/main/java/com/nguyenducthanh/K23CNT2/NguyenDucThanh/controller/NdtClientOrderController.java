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

    @GetMapping("/history")
    public String orderHistory(HttpSession session, Model model) {
        NdtUser user = (NdtUser) session.getAttribute("ndtCurrentUser");
        if (user == null) {
            return "redirect:/"; // Hoặc trang login
        }
        List<NdtOrder> orders = orderRepo.findByUserOrderByOrderDateDesc(user);

        model.addAttribute("orders", orders);
        return "admin/order-history";
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable("id") Long orderId,
                              HttpSession session, Model model) {

        NdtUser user = (NdtUser) session.getAttribute("ndtCurrentUser");
        if (user == null) {
            return "redirect:/";
        }

        NdtOrder order = orderRepo.findById(orderId).orElse(null);
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return "redirect:/order/history";
        }
        List<NdtOrderDetail> details = orderDetailRepo.findByOrderId(orderId);

        model.addAttribute("order", order);
        model.addAttribute("details", details);

        return "admin/order-detail";
    }
}
