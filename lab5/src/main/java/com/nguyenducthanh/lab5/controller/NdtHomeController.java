package com.nguyenducthanh.lab5.controller;

import com.nguyenducthanh.lab5.entity.NdtInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class NdtHomeController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "NguyenDucThanh::Trang chủ");
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }
    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }

    @GetMapping
    public String index(){
        return "index";
    }
    @GetMapping("/profile")
    public String profile(Model model){
        List<NdtInfo> profile = new ArrayList<>();
// Tạo thông tin profile
        profile.add(new NdtInfo("Thông tin cá nhân",
                "Nguyễn Đức Thành",
                "dthann2005@gmail.com",
                "https://devmaster.edu.vn"));
// Đưa profile vào model
        model.addAttribute("MyProfile", profile);
        return "profile";
    }
}
