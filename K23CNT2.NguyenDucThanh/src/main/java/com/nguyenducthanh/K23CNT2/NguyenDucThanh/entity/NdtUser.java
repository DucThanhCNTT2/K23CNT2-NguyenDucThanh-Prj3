package com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ndt_users")
public class NdtUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ndt_user_id")
    private Long id;

    @Column(name = "ndt_username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "ndt_password", nullable = false, length = 255)
    private String password;

    @Column(name = "ndt_full_name", length = 100)
    private String fullName;

    @Column(name = "ndt_email", length = 100)
    private String email;

    @Column(name = "ndt_phone", length = 20)
    private String phone;

    // ⭐ quan hệ với bảng ndt_roles (ndt_role_id)
    @ManyToOne
    @JoinColumn(name = "ndt_role_id")
    private NdtRole role;

    // ⭐ cột ndt_is_active (TINYINT(1))
    @Column(name = "ndt_is_active")
    private Boolean isActive;

    // 1 user có nhiều order
    @OneToMany(mappedBy = "user")
    private List<NdtOrder> orders;

    public NdtUser() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public NdtRole getRole() {
        return role;
    }

    public void setRole(NdtRole role) {
        this.role = role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public List<NdtOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<NdtOrder> orders) {
        this.orders = orders;
    }
}
