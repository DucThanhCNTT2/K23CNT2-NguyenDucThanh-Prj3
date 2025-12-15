package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto.NdtCartItem;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtCategoryRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NdtHomeController {

    private final NdtCategoryRepository categoryRepository;
    private final NdtProductRepository productRepository;

    public NdtHomeController(NdtCategoryRepository categoryRepository,
                             NdtProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String index(Model model,
                        HttpSession session,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "saleSize", defaultValue = "4") int saleSize) {

        // ====== XỬ LÝ GIỎ HÀNG CHO HEADER ======
        @SuppressWarnings("unchecked")
        List<NdtCartItem> cart = (List<NdtCartItem>) session.getAttribute("ndtCart");
        double cartTotal = 0;
        if (cart != null) {
            for (NdtCartItem item : cart) {
                cartTotal += item.getLineTotal();
            }
        } else {
            cart = List.of(); // list rỗng tránh null pointer
        }
        model.addAttribute("cartItems", cart);
        model.addAttribute("cartTotal", cartTotal);
        // =======================================

        // ====== BÁN CHẠY NHẤT ======
        Pageable bestPageable = PageRequest.of(0, size);
        Page<NdtProduct> bestPage =
                productRepository.findByIsActiveTrueOrderByIdDesc(bestPageable);

        model.addAttribute("bestProducts", bestPage.getContent());
        model.addAttribute("size", size);
        model.addAttribute("bestHasMore", bestPage.hasNext());

        // ====== ĐẠI TIỆC JEANS SALE 50%++ (chỉ sản phẩm có discount > 0) ======
        Pageable salePageable = PageRequest.of(0, saleSize);
        Page<NdtProduct> salePage =
                productRepository.findByIsActiveTrueAndDiscountPercentGreaterThanOrderByIdDesc(
                        0,   // discountPercent > 0
                        salePageable
                );

        model.addAttribute("saleProducts", salePage.getContent());
        model.addAttribute("saleSize", saleSize);
        model.addAttribute("saleHasMore", salePage.hasNext());

        // ====== Danh mục tròn ======
        model.addAttribute("categories", categoryRepository.findAll());

        return "index";
    }
}