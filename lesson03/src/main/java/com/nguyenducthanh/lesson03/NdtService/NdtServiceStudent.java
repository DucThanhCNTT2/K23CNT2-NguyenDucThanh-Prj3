package com.nguyenducthanh.lesson03.NdtService;

import com.nguyenducthanh.lesson03.NdtEntity.NdtStudent;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class NdtServiceStudent {
    List<NdtStudent> Ndtstudents =  new ArrayList<>();
    public NdtServiceStudent(){
        Ndtstudents.addAll(Arrays.asList(
                new NdtStudent(1L,"Nguyễn Đức Thành", 20, "Nam",
                        "Bắc Ninh", "0367907165", "Dthann2005@gmail.com"),

                new NdtStudent(2L, "Nguyễn Văn A", 19, "Nam",
                        "Hà Nội", "0901111222", "vana@example.com"),

                new NdtStudent(3L, "Trần Thị B", 20, "Nữ",
                        "Hồ Chí Minh", "0902222333", "thib@example.com"),

                new NdtStudent(4L, "Lê Văn C", 21, "Nam",
                        "Đà Nẵng", "0903333444", "vanc@example.com"),

                new NdtStudent(5L, "Phạm Thị D", 22, "Nữ",
                        "Cần Thơ", "0904444555", "thid@example.com")
        ));
    }
    //Lấy toàn bộ danh sách sinh viên
    public  List<NdtStudent> getNdtstudents() {
        return Ndtstudents;
    }

    //Lấy sinh viên theo id
    public NdtStudent getNdtstudent(Long NdtId){
        return Ndtstudents.stream().filter(NdtStudent -> NdtStudent.getNdtId().equals(NdtId))
                .findFirst().orElse(null);
    }

    //Thêm mới một sinh viên
    public NdtStudent addNdtstudent(NdtStudent ndtstudent){
        Ndtstudents.add(ndtstudent);
        return ndtstudent;
    }

    //Cập nhật thông tin sinh viên
    public NdtStudent updateNdtstudent(Long NdtId, NdtStudent ndtstudent){
        NdtStudent check =  getNdtstudent(NdtId);
        if(check != null){
            return null;
        }
        Ndtstudents.forEach(item->{
            if  (item.getNdtId() == NdtId) {
                item.setNdtName(ndtstudent.getNdtName());
                item.setNdtEmail(ndtstudent.getNdtEmail());
                item.setNdtPhone(ndtstudent.getNdtPhone());
                item.setNdtAddress(ndtstudent.getNdtAddress());
                item.setNdtAge(ndtstudent.getNdtAge());
                item.setNdtGender(ndtstudent.getNdtGender());
            }
        });
        return ndtstudent;
    }

    //Xóa thông tin sinh viên
    public boolean deleteNdtstudent(Long NdtId){
        NdtStudent check =  getNdtstudent(NdtId);
        return Ndtstudents.remove(check);
    }
}
