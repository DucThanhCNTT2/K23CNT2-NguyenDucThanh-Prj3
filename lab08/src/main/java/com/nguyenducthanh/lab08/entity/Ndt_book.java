package com.nguyenducthanh.lab08.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ndt_books")   // nên để trùng với tên table MySQL (thường dùng chữ thường)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ndt_book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ndt_id")
    Long ndt_id;

    @Column(name = "ndt_code")
    String ndt_code;

    @Column(name = "ndt_name")
    String ndt_name;

    @Column(name = "ndt_description")
    String ndt_description;

    @Column(name = "ndt_img_url")
    String ndt_imgUrl;

    @Column(name = "ndt_quantity")
    Integer ndt_quantity;

    @Column(name = "ndt_price")
    Double ndt_price;

    @Column(name = "ndt_is_active")
    Boolean ndt_isActive;

    // ❌ BỎ HOÀN TOÀN field ManyToOne tự tham chiếu này
    // @ManyToOne
    // @JoinColumn(name = "Ndt_bookid", nullable = false)
    // Ndt_book Ndt_book;
}
