package com.nguyenducthanh.lab08.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Ndt_authors")
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ndt_author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Ndt_id;
    String Ndt_code;
    String Ndt_name;
    String Ndt_description;
    String Ndt_imgUrl;
    String Ndt_email;
    String Ndt_phone;
    String Ndt_address;
    Boolean Ndt_isActive;
    @ManyToOne
    @JoinColumn(name = "Ndt_authorid", nullable = false)
    Ndt_author Ndt_author;
}
