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


    private final NdtOrderService orderService;


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


    @GetMapping("/ndt-admin")
    public String dashboard(HttpSession session, Model model) {


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

        return "admin";
    }


}