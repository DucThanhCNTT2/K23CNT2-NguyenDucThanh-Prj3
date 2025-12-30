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

    // 1. HIỂN THỊ DANH SÁCH
    @GetMapping
    public String listUsers(Model model) {
        List<NdtUser> users = userRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("activeMenu", "user");
        return "admin/user-list";
    }

    // 2. FORM THÊM MỚI
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new NdtUser()); // Đối tượng rỗng
        model.addAttribute("roles", roleRepo.findAll()); // Lấy danh sách Role để chọn
        return "admin/user-form";
    }

    // 3. FORM SỬA (Lấy theo ID)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        NdtUser user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepo.findAll());
        return "admin/user-form";
    }

    // 4. LƯU NGƯỜI DÙNG (Xử lý cho cả Thêm và Sửa)
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") NdtUser user) {
        // Lưu ý: Nếu bạn có mã hóa mật khẩu thì cần xử lý ở đây
        // Ví dụ: if (user.getId() == null) { mã hóa pass } ...

        userRepo.save(user);
        return "redirect:/ndt-admin/users";
    }

    // 5. XÓA NGƯỜI DÙNG
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        // Kiểm tra xem có phải xóa chính mình (Admin đang login) không để tránh lỗi
        // Ở đây làm đơn giản là xóa luôn
        try {
            userRepo.deleteById(id);
        } catch (Exception e) {
            // Có thể user này đang dính khóa ngoại (Foreign Key) với bảng Order
            // Bạn có thể thông báo lỗi hoặc set isActive = false thay vì xóa hẳn
            e.printStackTrace();
        }
        return "redirect:/ndt-admin/users";
    }
}
