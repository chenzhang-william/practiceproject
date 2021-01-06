package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Staff;
import com.yealink.level1.service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhangchen
 * @description StaffServiceImplTest
 * @date 2021/1/6 15:50
 */
@SpringBootTest
class StaffServiceImplTest {
    @Autowired
    private StaffService staffService;
    @Test
    void add() {
        Staff staff = new Staff();
        staff.setMobile("test");
        staffService.add(staff);
        System.out.println(staff.getId());
    }
}