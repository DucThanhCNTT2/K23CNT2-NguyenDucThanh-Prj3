package com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NdtProductRepository extends JpaRepository<NdtProduct, Long> {

    Page<NdtProduct> findByIsActiveTrueOrderByIdDesc(Pageable pageable);

    Page<NdtProduct> findByIsActiveTrueAndDiscountPercentGreaterThanOrderByIdDesc(
            int discountPercent, Pageable pageable
    );

    Optional<NdtProduct> findById(Long productId);
}
