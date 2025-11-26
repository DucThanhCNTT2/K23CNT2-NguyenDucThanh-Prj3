package com.nguyenducthanh.lab08.repository;

import com.nguyenducthanh.lab08.entity.Ndt_author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ndt_authorRepository extends JpaRepository<Ndt_author,Long> {
}
