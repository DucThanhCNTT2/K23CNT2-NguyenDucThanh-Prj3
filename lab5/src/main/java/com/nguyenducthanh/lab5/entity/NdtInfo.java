package com.nguyenducthanh.lab5.entity;

public class NdtInfo {
    private String name;
    private String nickname;
    private String email;
    private String Website;

    public NdtInfo() {
    }

    public NdtInfo(String name, String nickname, String email, String Website) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.Website = Website;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getWebsite() {
        return Website;
    }
    public void setWebsite(String Website) {
        this.Website = Website;
    }
}
