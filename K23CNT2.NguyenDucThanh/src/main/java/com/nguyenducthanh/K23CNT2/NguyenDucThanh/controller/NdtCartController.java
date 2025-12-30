package com.nguyenducthanh.K23CNT2.NguyenDucThanh.controller;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto.NdtCartItem;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtOrder;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtProduct;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity.NdtUser;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtOrderRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.repository.NdtProductRepository;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.service.NdtCartService;
import com.nguyenducthanh.K23CNT2.NguyenDucThanh.service.NdtOrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class NdtCartController {

    private final NdtCartService cartService;
    private final NdtOrderService orderService;
    private final NdtOrderRepository orderRepository;
    private final NdtProductRepository productRepo;

    public NdtCartController(NdtCartService cartService, NdtOrderService orderService, NdtProductRepository productRepo,  NdtOrderRepository orderRepository) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.productRepo = productRepo;
        this.orderRepository = orderRepository;
    }

    @SuppressWarnings("unchecked")
    private List<NdtCartItem> getCart(HttpSession session) {
        List<NdtCartItem> cart =
                (List<NdtCartItem>) session.getAttribute("ndtCart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("ndtCart", cart);
        }
        return cart;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "qty", defaultValue = "1") int qty,
                            @RequestParam(value = "redirect", required = false) String redirectUrl,
                            HttpSession session) {

        Optional<NdtProduct> optProduct = productRepo.findById(productId);
        if (optProduct.isEmpty()) {
            return "redirect:/";
        }

        NdtProduct product = optProduct.get();
        List<NdtCartItem> cart = getCart(session);

        NdtCartItem exist = cart.stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (exist != null) {
            exist.setQuantity(exist.getQuantity() + qty);
        } else {
            cart.add(new NdtCartItem(product, qty));
        }

        String target = (redirectUrl != null && !redirectUrl.isBlank())
                ? redirectUrl
                : "/";

        if (target.contains("?")) {
            target += "&addedToCart=1";
        } else {
            target += "?addedToCart=1";
        }

        return "redirect:" + target;
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        List<NdtCartItem> cart = getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/";
        }

        long subTotal = 0L;
        long subAfterDiscount = 0L;
        long totalDiscount = 0L;

        for (NdtCartItem item : cart) {
            subTotal += item.getOriginalLineTotal();
            subAfterDiscount += item.getLineTotal();
            totalDiscount += item.getDiscountAmount();
        }

        long shippingFee = subAfterDiscount >= 2_000_000 ? 0 : 30_000;
        long grandTotal = subAfterDiscount + shippingFee;

        model.addAttribute("cartItems", cart);
        model.addAttribute("subTotal", subTotal);
        model.addAttribute("subAfterDiscount", subAfterDiscount);
        model.addAttribute("totalDiscount", totalDiscount);
        model.addAttribute("shippingFee", shippingFee);
        model.addAttribute("grandTotal", grandTotal);

        return "checkout";
    }

    @PostMapping("/checkout/place-order")
    public String placeOrder(HttpSession session, RedirectAttributes ra) {

        NdtUser currentUser = (NdtUser) session.getAttribute("ndtCurrentUser");
        if (currentUser == null) {
            ra.addFlashAttribute("msg", "Bạn cần đăng nhập để đặt hàng");
            return "redirect:/checkout";
        }

        List<NdtCartItem> cart = getCart(session);
        if (cart.isEmpty()) {
            ra.addFlashAttribute("msg", "Giỏ hàng đang trống");
            return "redirect:/";
        }

        try {
            NdtOrder order = orderService.placeOrder(currentUser, cart);
            session.removeAttribute("ndtCart");

            return "redirect:/thank-you?orderId=" + order.getId();

        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("msg", "Lỗi đặt hàng: " + e.getMessage());
            return "redirect:/checkout";
        }
    }

    @GetMapping("/thank-you")
    public String thankYou(@RequestParam("orderId") Long orderId,
                           Model model) {
        Optional<NdtOrder> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
        } else {
            return "redirect:/";
        }

        return "order/thank-you";
    }

}
