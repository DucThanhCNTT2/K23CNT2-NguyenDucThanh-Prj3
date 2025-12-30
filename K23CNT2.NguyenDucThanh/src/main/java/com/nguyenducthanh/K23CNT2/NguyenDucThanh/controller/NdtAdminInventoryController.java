package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ndt-admin/inventory")
public class NdtAdminInventoryController {

    private final NdtProductRepository productRepo;

    public NdtAdminInventoryController(NdtProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public String inventory(Model model) {
        model.addAttribute("activeMenu", "inventory");
        // Sắp xếp sản phẩm theo ID giảm dần để dễ nhìn cái mới nhập
        model.addAttribute("products", productRepo.findAll());
        return "admin/inventory";
    }

    // SỬA LẠI ĐƯỜNG DẪN Ở ĐÂY
    @PostMapping("/add-stock/{id}")
    public String addStock(@PathVariable Long id,
                           @RequestParam("addQty") int addQty) {

        if (addQty < 0) return "redirect:/ndt-admin/inventory"; // Không cho nhập số âm

        NdtProduct p = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Cộng thêm vào số lượng cũ
        p.setQuantity(p.getQuantity() + addQty);

        productRepo.save(p);

        return "redirect:/ndt-admin/inventory";
    }
}