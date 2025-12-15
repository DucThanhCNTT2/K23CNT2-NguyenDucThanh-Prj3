package com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  NdtUserRepository extends JpaRepository<NdtUser, Long> {
    Optional<NdtUser> findByUsernameAndPasswordAndIsActiveTrue(String username, String password);

    Optional<NdtUser> findByEmailAndPasswordAndIsActiveTrue(String email, String password);

    Optional<NdtUser> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
