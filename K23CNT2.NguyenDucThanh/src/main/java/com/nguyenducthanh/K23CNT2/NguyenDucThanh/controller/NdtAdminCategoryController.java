package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtCategory;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ndt-admin/categories")
public class NdtAdminCategoryController {

    private final NdtCategoryRepository categoryRepo;

    public NdtAdminCategoryController(NdtCategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // -------- LIST --------
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("activeMenu", "category");
        model.addAttribute("categories", categoryRepo.findAll());
        return "admin/category-list";   // templates/admin/category-list.html
    }

    // -------- CREATE FORM --------
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("activeMenu", "category");
        model.addAttribute("category", new NdtCategory());
        return "admin/category-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("category") NdtCategory category,
                         RedirectAttributes ra) {
        categoryRepo.save(category);
        ra.addFlashAttribute("msg", "Đã thêm danh mục mới");
        return "redirect:/ndt-admin/categories";
    }

    // -------- EDIT --------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        NdtCategory category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("activeMenu", "category");
        model.addAttribute("category", category);
        return "admin/category-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("category") NdtCategory form,
                         RedirectAttributes ra) {

        NdtCategory category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        category.setCategoryName(form.getCategoryName());
        category.setDescription(form.getDescription());

        categoryRepo.save(category);
        ra.addFlashAttribute("msg", "Đã cập nhật danh mục");
        return "redirect:/ndt-admin/categories";
    }

    // -------- DELETE --------
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        if (categoryRepo.existsById(id)) {
            categoryRepo.deleteById(id);
            ra.addFlashAttribute("msg", "Đã xoá danh mục");
        } else {
            ra.addFlashAttribute("msg", "Danh mục không tồn tại");
        }
        return "redirect:/ndt-admin/categories";
    }
}
