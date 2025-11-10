package com.nguyenducthanh.lesson03.NdtController;

import com.nguyenducthanh.lesson03.NdtEntity.NdtStudent;
import com.nguyenducthanh.lesson03.NdtService.NdtServiceStudent;
import
        org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class NdtStudentController {
    @Autowired
    private NdtServiceStudent ndtServiceStudent;

    @GetMapping("/student-list")
    public List<NdtStudent> getAllStudentList() {
        return ndtServiceStudent.getNdtstudents();
    }
    @GetMapping("/student/{NdtId}")
    public NdtStudent getStudent(@PathVariable("NdtId") String NdtId) {
        Long param = Long.parseLong(NdtId);
        return ndtServiceStudent.getNdtstudent(param);
    }

    @PostMapping("/student-add")
    public NdtStudent addStudent(@RequestBody NdtStudent ndtStudent) {
        return ndtServiceStudent.addNdtstudent(ndtStudent);
    }

    @PutMapping("/student/ {NdtId}")
    public NdtStudent updateStudent(@PathVariable String NdtId,@RequestBody NdtStudent ndtStudent) {
        Long param = Long.parseLong(NdtId);
        return ndtServiceStudent.updateNdtstudent(param, ndtStudent);
    }

    @DeleteMapping("/student/ {NdtId}")
    public boolean deleteStudent(@PathVariable String NdtId) {
        Long param = Long.parseLong(NdtId);
        return ndtServiceStudent.deleteNdtstudent(param);
    }
}
