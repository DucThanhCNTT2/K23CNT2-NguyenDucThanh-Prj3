    package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

    import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
    import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository;
    import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtCategoryRepository;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.server.ResponseStatusException;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    @Controller
    @RequestMapping("/ndt-admin/products")
    public class NdtAdminProductController {

        private final NdtProductRepository productRepo;
        private final NdtCategoryRepository categoryRepo;

        public NdtAdminProductController(NdtProductRepository productRepo,
                                         NdtCategoryRepository categoryRepo) {
            this.productRepo = productRepo;
            this.categoryRepo = categoryRepo;
        }

        // -------- LIST SẢN PHẨM --------
        @GetMapping
        public String list(Model model) {
            model.addAttribute("activeMenu", "product");
            model.addAttribute("products", productRepo.findAll());
            return "admin/product-list";   // -> templates/admin/product-list.html
        }

        // -------- FORM THÊM --------
        @GetMapping("/create")
        public String showCreateForm(Model model) {
            model.addAttribute("activeMenu", "product");
            model.addAttribute("product", new NdtProduct());
            model.addAttribute("categories", categoryRepo.findAll()); // <-- CÓ DÒNG NÀY
            return "admin/product-create";
        }

        @PostMapping("/create")
        public String create(@ModelAttribute("product") NdtProduct product,
                             RedirectAttributes ra) {
            product.setIsActive(true);   // nếu có cột này
            productRepo.save(product);
            ra.addFlashAttribute("msg", "Đã thêm sản phẩm mới");
            return "redirect:/ndt-admin/products";
        }

        // -------- FORM SỬA --------
        @GetMapping("/edit/{id}")
        public String showEditForm(@PathVariable Long id, Model model) {
            NdtProduct product = productRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            model.addAttribute("activeMenu", "product");
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepo.findAll());
            return "admin/product-edit";
        }

        @PostMapping("/edit/{id}")
        public String update(@PathVariable Long id,
                             @ModelAttribute("product") NdtProduct form,
                             RedirectAttributes ra) {
            NdtProduct product = productRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            product.setName(form.getName());
            product.setPrice(form.getPrice());
            product.setQuantity(form.getQuantity());
            product.setDescription(form.getDescription());
            product.setImgUrl(form.getImgUrl());
            product.setDiscountPercent(form.getDiscountPercent());
            product.setCategory(form.getCategory());
            product.setIsActive(form.getIsActive());

            productRepo.save(product);

            ra.addFlashAttribute("msg", "Đã cập nhật sản phẩm");
            return "redirect:/ndt-admin/products";
        }

        // -------- XOÁ --------
        @PostMapping("/delete/{id}")
        public String delete(@PathVariable Long id, RedirectAttributes ra) {
            if (productRepo.existsById(id)) {
                productRepo.deleteById(id);
                ra.addFlashAttribute("msg", "Đã xoá sản phẩm");
            } else {
                ra.addFlashAttribute("msg", "Sản phẩm không tồn tại");
            }
            return "redirect:/ndt-admin/products";
        }
    }