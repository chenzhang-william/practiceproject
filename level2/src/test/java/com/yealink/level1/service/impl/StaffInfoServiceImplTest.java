package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Staff;
import com.yealink.level1.service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangchen
 * @description StaffInfoServiceImplTest
 * @date 2020/12/30 18:14
 */
@SpringBootTest
class StaffInfoServiceImplTest {

    @Autowired
    private StaffService staffInfoService;

    @Test
    void add() {
        Staff staff = new Staff();
        staffInfoService.add(staff);
    }


}