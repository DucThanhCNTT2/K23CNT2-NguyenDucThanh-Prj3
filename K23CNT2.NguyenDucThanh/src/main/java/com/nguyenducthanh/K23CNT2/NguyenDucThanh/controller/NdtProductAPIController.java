package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto.ProductDto;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class NdtProductAPIController {

    private final NdtProductRepository productRepository;

    public NdtProductAPIController(NdtProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/api/products")
    public List<ProductDto> loadMoreProducts(@RequestParam int page,
                                             @RequestParam int size) {
        Page<NdtProduct> productPage =
                productRepository.findByIsActiveTrueOrderByIdDesc(
                        PageRequest.of(page, size));

        return productPage.getContent()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/discount-products")
    @ResponseBody
    public Map<String, Object> getDiscountProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<NdtProduct> productPage =
                productRepository.findByIsActiveTrueAndDiscountPercentGreaterThanOrderByIdDesc(
                        0, pageable);  // discount > 0

        List<ProductDto> items = productPage.getContent()
                .stream()
                .map(ProductDto::new)
                .toList();

        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("hasNext", productPage.hasNext());
        return result;
    }
}
