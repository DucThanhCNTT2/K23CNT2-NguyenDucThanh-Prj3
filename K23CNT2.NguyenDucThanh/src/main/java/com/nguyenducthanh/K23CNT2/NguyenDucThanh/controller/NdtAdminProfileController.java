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

    // 1. Hiển thị trang thông tin cá nhân
    @GetMapping
    public String showProfile(Model model, HttpSession session) {
        // Lấy user từ session (người đang đăng nhập)
        NdtUser currentUser = (NdtUser) session.getAttribute("ndtCurrentUser");

        if (currentUser == null) {
            return "redirect:/ndt-login"; // Chưa đăng nhập thì đá về login
        }

        // Lấy dữ liệu mới nhất từ DB để đảm bảo chính xác
        NdtUser user = userRepo.findById(currentUser.getId()).orElse(currentUser);

        model.addAttribute("user", user);
        return "admin/profile";
    }

    // 2. Xử lý cập nhật thông tin
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("user") NdtUser formUser, HttpSession session) {
        // Lấy user cũ từ DB
        NdtUser currentUser = userRepo.findById(formUser.getId()).orElse(null);

        if (currentUser != null) {
            // Cập nhật các thông tin cho phép sửa
            currentUser.setFullName(formUser.getFullName());
            currentUser.setEmail(formUser.getEmail());
            currentUser.setPhone(formUser.getPhone());

            // Logic đổi mật khẩu: Chỉ đổi nếu người dùng nhập gì đó vào ô mật khẩu
            if (formUser.getPassword() != null && !formUser.getPassword().isEmpty()) {
                currentUser.setPassword(formUser.getPassword());
            }

            // Lưu xuống DB
            userRepo.save(currentUser);

            // Cập nhật lại Session để hiển thị tên mới ngay lập tức trên Topbar
            session.setAttribute("ndtCurrentUser", currentUser);
        }

        return "redirect:/ndt-admin/profile?success";
    }
}