package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ndt-admin/users")
public class NdtAdminUserController {

    private final NdtUserRepository userRepo;

    public NdtAdminUserController(NdtUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("activeMenu", "user");
        model.addAttribute("users", userRepo.findAll());
        return "admin/user-list";     // templates/admin/user-list.html
    }
}
