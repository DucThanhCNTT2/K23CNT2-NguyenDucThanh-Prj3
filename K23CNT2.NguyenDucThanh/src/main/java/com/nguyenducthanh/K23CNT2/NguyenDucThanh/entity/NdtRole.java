package com.nguyenducthanh.K23CNT2.NguyenDucThanh.entity;

import jakarta.persistence.*;

@Entity
@Table(name="ndt_roles")
public class NdtRole {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ndt_role_id")
    private Long id;

    @Column(name="ndt_role_name", nullable=false, length =50)
    private String roleName;

    public NdtRole() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
