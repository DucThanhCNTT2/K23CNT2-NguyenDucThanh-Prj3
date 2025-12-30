package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtUser;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtRoleRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ndt-admin/users")
public class NdtAdminUserController {

    private final NdtUserRepository userRepo;
    private final NdtRoleRepository roleRepo;

    public NdtAdminUserController(NdtUserRepository userRepo, NdtRoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<NdtUser> users = userRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("activeMenu", "user");
        return "admin/user-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new NdtUser()); // Đối tượng rỗng
        model.addAttribute("roles", roleRepo.findAll()); // Lấy danh sách Role để chọn
        return "admin/user-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        NdtUser user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepo.findAll());
        return "admin/user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") NdtUser user) {
        userRepo.save(user);
        return "redirect:/ndt-admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        try {
            userRepo.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/ndt-admin/users";
    }
}
