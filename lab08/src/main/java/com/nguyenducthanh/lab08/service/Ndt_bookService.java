package com.nguyenducthanh.lab08.service;

import com.nguyenducthanh.lab08.entity.Ndt_book;
import com.nguyenducthanh.lab08.repository.Ndt_bookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Ndt_bookService {
    @Autowired
    private Ndt_bookRepository ndt_bookRepository;

    public Ndt_bookService(Ndt_bookRepository ndt_bookRepository) {
        this.ndt_bookRepository = ndt_bookRepository;
    }
    //Lấy danh sách
    public List<Ndt_book> getAllNdt_books(){
        System.out.println(ndt_bookRepository.findAll());
        return ndt_bookRepository.findAll();
    }

    //Lấy theo id
    public Optional<Ndt_book> getNdt_bookById(Long Ndt_id){
        return ndt_bookRepository.findById(Ndt_id);
    }

    //Lấy dữ liệu bàng book: create / update
    public Ndt_book saveNdt_book(Ndt_book ndt_book){
        return ndt_bookRepository.save  (ndt_book);
    }

    //Xóa theo id
    public void deleteNdt_bookById(Long Ndt_id){
        ndt_bookRepository.deleteById(Ndt_id);
    }

}
