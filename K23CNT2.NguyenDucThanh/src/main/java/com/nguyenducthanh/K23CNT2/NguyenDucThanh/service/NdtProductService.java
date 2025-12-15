package com.nguyenducthanh.K23CNT2.NguyenDucThanh.service;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import java.util.List;

public interface NdtProductService {
    List<NdtProduct> getAll();
    NdtProduct getById(Long id);
    NdtProduct save(NdtProduct product);
    void deleteById(Long id);
}
