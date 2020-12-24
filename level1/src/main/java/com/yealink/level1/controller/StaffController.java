package com.yealink.level1.controller;

import com.yealink.level1.bean.Staff;
import com.yealink.level1.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/add")
    public int add(@RequestBody Staff staff){
        return staffService.add(staff);
    }

    @PutMapping("/update/")
    public int updateStaff(@RequestBody Staff staff){
        return staffService.update(staff);
    }

    @PutMapping("/updateEnterprise/{id}")
    public int updateEnterprise(@PathVariable("id")String id,
                                @RequestParam(value = "enterpriseId",required = true) String enterpriseId){
        return staffService.updateEnterprise(id,enterpriseId);
    }
    /*测试put请求
    @PutMapping("/updatename/{id}")
    public int updateName(@PathVariable("id")String id, @RequestParam(value = "name",required = true) String name){
        return staffService.updateName(name);
    }
     */
    @DeleteMapping("/delete/{id}")
    public void deleteStaff(@PathVariable("id") String id){
        staffService.delete(id);
    }

    @GetMapping("/find/{id}")
    public Staff findStaffById(@PathVariable("id") String id){
        return staffService.findStaffById(id);
    }

    @GetMapping("/list")
    public List<Staff> findStaffList(){
        return staffService.findStaffList();
    }

    @GetMapping("/find1/{id}")
    public Staff findStaffWithEnterprise(@PathVariable("id") String id) {
        return staffService.findStaffWithEnterprise(id);
    }

    @GetMapping("/getStaffWithAccount/{id}")
    public Staff getStaffWithAccount(@PathVariable("id") String id){
        return staffService.getStaffWithAccount(id);
    }

}
