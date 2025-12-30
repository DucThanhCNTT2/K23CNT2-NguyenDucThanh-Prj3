package com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface NdtProductRepository extends JpaRepository<NdtProduct, Long> {

    Page<NdtProduct> findByIsActiveTrueOrderByIdDesc(Pageable pageable);

    Page<NdtProduct> findByIsActiveTrueAndDiscountPercentGreaterThanOrderByIdDesc(
            int discountPercent, Pageable pageable
    );

    // 🔥 HÀM MỚI: Tìm theo Tên HOẶC Tên Danh Mục (Thương hiệu)
    @Query("SELECT p FROM NdtProduct p WHERE p.isActive = true AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<NdtProduct> searchByNameOrBrand(@Param("keyword") String keyword);

    Optional<NdtProduct> findById(Long productId);
}

