package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtRole;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtUser;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtRoleRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class NdtAuthController {

    private final NdtUserRepository userRepo;
    private final NdtRoleRepository roleRepo;

    public NdtAuthController(NdtUserRepository userRepo,
                             NdtRoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    // ---------- ĐĂNG KÝ ----------
    @PostMapping("/ndt-register")
    public String register(
            @RequestParam String username,
            @RequestParam(required = false) String fullName,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam String password,
            RedirectAttributes ra) {

        if (userRepo.existsByUsername(username)) {
            ra.addFlashAttribute("registerError", "Username đã tồn tại");
            return "redirect:/";
        }
        if (userRepo.existsByEmail(email)) {
            ra.addFlashAttribute("registerError", "Email đã được dùng");
            return "redirect:/";
        }

        // mặc định role GUEST
        NdtRole guestRole = roleRepo.findByroleName("GUEST")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy role GUEST"));

        NdtUser u = new NdtUser();
        u.setUsername(username);
        u.setFullName(fullName);
        u.setEmail(email);
        u.setPhone(phone);
        u.setPassword(password);   // demo, chưa mã hoá
        u.setRole(guestRole);
        u.setIsActive(true);

        userRepo.save(u);

        ra.addFlashAttribute("registerSuccess", "Đăng ký thành công, hãy đăng nhập.");
        return "redirect:/";
    }

    // ---------- ĐĂNG NHẬP ----------
    @PostMapping("/ndt-login")
    public String login(
            @RequestParam("usernameOrEmail") String usernameOrEmail,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes ra) {

        Optional<NdtUser> opt =
                userRepo.findByUsernameAndPasswordAndIsActiveTrue(usernameOrEmail, password);

        if (opt.isEmpty()) {
            opt = userRepo.findByEmailAndPasswordAndIsActiveTrue(usernameOrEmail, password);
        }

        if (opt.isEmpty()) {
            ra.addFlashAttribute("loginError", "Sai tài khoản hoặc mật khẩu");
            return "redirect:/";
        }

        NdtUser user = opt.get();

        // Lưu user vào session
        session.setAttribute("ndtCurrentUser", user);

        // nếu thích lưu role vào session:
        if (user.getRole() != null) {
            session.setAttribute("ndtRoleId", user.getRole().getId());
            session.setAttribute("ndtRoleName", user.getRole().getRoleName());
        }

        // ===== PHÂN QUYỀN BẰNG ndt_role_id =====
        Long roleId = (user.getRole() != null) ? user.getRole().getId() : null;

        // ví dụ: 1 = ADMIN, 2 = STAFF
        if (roleId != null && (roleId == 1L || roleId == 2L)) {
            return "redirect:/ndt-admin";   // sang trang quản trị
        }

        // các role khác (3 = shipper, 4 = guest...) quay về trang chủ
        return "redirect:/";
    }

    // ---------- ĐĂNG XUẤT ----------
    @GetMapping("/ndt-logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}