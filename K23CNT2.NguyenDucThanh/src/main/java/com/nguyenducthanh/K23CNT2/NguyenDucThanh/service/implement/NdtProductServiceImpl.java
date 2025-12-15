package com.nguyenducthanh.K23CNT2.NguyenDucThanh.service.implement;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.service.NdtProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NdtProductServiceImpl implements NdtProductService {

    private final NdtProductRepository productRepository;

    public NdtProductServiceImpl(NdtProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<NdtProduct>getAll() {
        return productRepository.findAll();
    }

    @Override
    public NdtProduct getById(Long id) {
        return productRepository.findById(id)
                .orElse(null);
    }

    @Override
    public NdtProduct save(NdtProduct product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
