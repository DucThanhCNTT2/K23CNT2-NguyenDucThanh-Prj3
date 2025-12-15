package com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NdtRoleRepository extends JpaRepository<NdtRole, Long> {
    Optional<NdtRole> findByroleName(String roleName);
}
