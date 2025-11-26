package com.nguyenducthanh.lab08.service;

import com.nguyenducthanh.lab08.entity.Ndt_author;
import com.nguyenducthanh.lab08.repository.Ndt_authorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Ndt_authorService {
    @Autowired
    private Ndt_authorRepository ndt_authorRepository;
    //Đọc toàn bộ dữ liệu bàng author
    public List<Ndt_author> getAllNdt_authors() {
        return ndt_authorRepository.findAll();
    }

    //Đọc dữ liệu bảng author theo id
    public Optional<Ndt_author> findById(Long Ndt_id) {
        return ndt_authorRepository.findById(Ndt_id);
    }

    //Cập nhật: create / update
    public Ndt_author saveNdt_author(Ndt_author ndt_author) {
        System.out.println(ndt_author);
        return ndt_authorRepository.save(ndt_author);
    }

    //Xóa author theo id
    public void deleteNdt_author(Long Ndt_id) {
        ndt_authorRepository.deleteById(Ndt_id);
    }
}
