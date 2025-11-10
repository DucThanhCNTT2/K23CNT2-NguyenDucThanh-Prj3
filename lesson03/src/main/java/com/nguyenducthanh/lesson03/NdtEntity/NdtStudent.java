package com.nguyenducthanh.lesson03.NdtEntity;

public class NdtStudent {
    Long NdtId;
    String NdtName;
    int NdtAge;
    String NdtGender;
    String NdtAddress;
    String NdtPhone;
    String NdtEmail;

    public NdtStudent() {
    }

    public NdtStudent(Long NdtId, String NdtName, int NdtAge, String NdtGender,
                      String NdtAddress, String NdtPhone, String NdtEmail) {
        this.NdtId = NdtId;
        this.NdtName = NdtName;
        this.NdtAge = NdtAge;
        this.NdtGender = NdtGender;
        this.NdtAddress = NdtAddress;
        this.NdtPhone = NdtPhone;
        this.NdtEmail = NdtEmail;
    }

    public Long getNdtId() {
        return NdtId;
    }
    public void setNdtId(Long ndtId) {
        this.NdtId = ndtId;
    }
    public String getNdtName() {
        return NdtName;
    }
    public void setNdtName(String ndtName) {
        this.NdtName = ndtName;
    }
    public int getNdtAge() {
        return NdtAge;
    }
    public void setNdtAge(int ndtAge) {
        this.NdtAge = ndtAge;
    }
    public String getNdtGender() {
        return NdtGender;
    }
    public void setNdtGender(String ndtGender) {
        this.NdtGender = ndtGender;
    }
    public String getNdtAddress() {
        return NdtAddress;
    }
    public void setNdtAddress(String ndtAddress) {
        this.NdtAddress = ndtAddress;
    }
    public String getNdtPhone() {
        return NdtPhone;
    }
    public void setNdtPhone(String ndtPhone) {
        this.NdtPhone = ndtPhone;
    }
    public String getNdtEmail() {
        return NdtEmail;
    }
    public void setNdtEmail(String NdtEmail) {
        this.NdtEmail = NdtEmail;
    }
}
