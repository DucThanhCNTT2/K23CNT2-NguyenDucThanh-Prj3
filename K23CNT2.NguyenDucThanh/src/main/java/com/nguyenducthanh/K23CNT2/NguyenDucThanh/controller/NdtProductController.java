package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtCategoryRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class NdtProductController {
    private final NdtProductRepository productRepository;
    private final NdtCategoryRepository categoryRepository;

    public NdtProductController(NdtProductRepository productRepository,
                                NdtCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // /product/1, /product/2,...
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {

        NdtProduct product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());

        // tên file thymeleaf: product-detail.html
        return "product-detail";
    }
}

