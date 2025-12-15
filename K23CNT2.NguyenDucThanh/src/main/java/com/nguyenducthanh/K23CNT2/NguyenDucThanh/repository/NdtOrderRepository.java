    package com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository;

    import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtOrder;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface NdtOrderRepository extends JpaRepository<NdtOrder, Long> {
        List<NdtOrder> findTop5ByOrderByOrderDateDesc();

        List<NdtOrder> findAllByOrderByOrderDateDesc();
    }
