package com.nguyenducthanh.K23CNT2.NguyenDucThanh.config;

import com.nguyenducthanh.K23CNT2.NguyenDucThanh.dto.NdtCartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(annotations = Controller.class)
public class NdtGlobalModelAttributes {

    @SuppressWarnings("unchecked")
    @ModelAttribute
    public void addGlobalAttrs(Model model, HttpSession session) {

        List<NdtCartItem> cart =
                (List<NdtCartItem>) session.getAttribute("ndtCart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        long cartTotal = 0L;
        for (NdtCartItem item : cart) {
            cartTotal += item.getLineTotal();  // đã gồm discount
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("cartTotal", cartTotal);
    }
}
