package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtUser;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ndt-admin/profile")
public class NdtAdminProfileController {

    private final NdtUserRepository userRepo;

    public NdtAdminProfileController(NdtUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String showProfile(Model model, HttpSession session) {
        NdtUser currentUser = (NdtUser) session.getAttribute("ndtCurrentUser");

        if (currentUser == null) {
            return "redirect:/ndt-login"; // Chưa đăng nhập thì đá về login
        }
        NdtUser user = userRepo.findById(currentUser.getId()).orElse(currentUser);

        model.addAttribute("user", user);
        return "admin/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("user") NdtUser formUser, HttpSession session) {
        NdtUser currentUser = userRepo.findById(formUser.getId()).orElse(null);

        if (currentUser != null) {
            currentUser.setFullName(formUser.getFullName());
            currentUser.setEmail(formUser.getEmail());
            currentUser.setPhone(formUser.getPhone());

            if (formUser.getPassword() != null && !formUser.getPassword().isEmpty()) {
                currentUser.setPassword(formUser.getPassword());
            }

            userRepo.save(currentUser);

            session.setAttribute("ndtCurrentUser", currentUser);
        }

        return "redirect:/ndt-admin/profile?success";
    }
}