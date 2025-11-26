package com.nguyenducthanh.lab08.repository;

import com.nguyenducthanh.lab08.entity.Ndt_book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Ndt_bookRepository extends JpaRepository<Ndt_book, Long> {

}
