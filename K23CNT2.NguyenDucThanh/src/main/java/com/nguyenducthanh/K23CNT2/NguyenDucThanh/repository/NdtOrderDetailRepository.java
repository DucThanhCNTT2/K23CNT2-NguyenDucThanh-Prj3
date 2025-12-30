package com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NdtOrderDetailRepository extends JpaRepository<NdtOrderDetail, Long> {
    List<NdtOrderDetail> findByOrderId(Long orderId);
}
