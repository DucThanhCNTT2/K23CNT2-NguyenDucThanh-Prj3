package com.nguyenducthanh.lab08.controller;

import com.nguyenducthanh.lab08.entity.Ndt_book;
import com.nguyenducthanh.lab08.service.Ndt_bookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ndt_book")
public class Ndt_bookController {
    @Autowired
    private Ndt_bookService ndt_bookService;
    @GetMapping
    public String listNdt_books(Model model) {
        model.addAttribute("ndt_books", ndt_bookService.getAllNdt_books());
        return "ndt_book/Ndt_book-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("ndt_book", new Ndt_book());
        return "ndt_book/ndt_book-form";
    }

    @GetMapping("/edit/{Ndt_id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("ndt_book", ndt_bookService.getNdt_bookById(id).orElse(null));
        return "ndt_book/ndt_book-form";
    }

    @PostMapping("/save")
    public String saveNdt_book(@ModelAttribute ("ndt_book")  Ndt_book ndt_book) {
        ndt_bookService.saveNdt_book(ndt_book);
        return "redirect:/ndt_book";
    }

    @PostMapping("/create/{Ndt_id}")
    public String upadteNdt_book(@PathVariable Long Ndt_id, @ModelAttribute Ndt_book ndt_book) {
        ndt_book.setNdt_id(Ndt_id);
        ndt_bookService.saveNdt_book(ndt_book);
        return "redirect:/ndt_book";
    }

    @GetMapping("/delete/{Ndt_id}")
    public String deleteNdt_book(@PathVariable("Ndt_id") Long Ndt_id) {
        ndt_bookService.deleteNdt_bookById(Ndt_id);
        return "redirect:/ndt_book";
    }

}
