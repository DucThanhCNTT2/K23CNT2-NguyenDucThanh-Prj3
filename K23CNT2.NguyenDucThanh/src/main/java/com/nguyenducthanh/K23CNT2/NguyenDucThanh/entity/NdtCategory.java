package com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "ndt_categories")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NdtCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ndt_category_id")
    private Long id;

    @Column(name = "ndt_category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "ndt_description")
    private String description;

    @Column(name = "ndt_img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "category")
    private List<NdtProduct> products;


}
