package com.yealink.practiceproject.service.domain.impl;


import com.yealink.practiceproject.bean.Enterprise;
import com.yealink.practiceproject.bean.Staff;
import com.yealink.practiceproject.dao.StaffMapper;
import com.yealink.practiceproject.service.domain.EnterpriseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.yealink.practiceproject.util.DataConversion.genderTransfer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author zhangchen
 * @description StaffServiceImplTest
 * @date 2021/1/6 15:50
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StaffServiceImplTest {

    @Mock
    Staff mockStaff;
    @Mock
    Enterprise mockEnterprise;
    @Mock
    EnterpriseService mockEnterpriseService;
    @Mock
    Staff mockNewStaff;
    @Mock
    StaffMapper mockStaffMapper;
    @InjectMocks
    StaffServiceImpl mockStaffService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void cleanTestData() {
        reset(mockStaff);
        reset(mockNewStaff);
        reset(mockEnterprise);
        reset(mockStaffMapper);
        reset(mockEnterpriseService);
    }

    @Test
    public void findIdByMobile() {
        when(mockStaffMapper.findIdByMobile("1")).thenReturn("1");
        assertEquals("1", mockStaffService.findIdByMobile("1"));

        when(mockStaffMapper.findIdByMobile("1")).thenReturn(null);
        assertEquals(null, mockStaffService.findIdByMobile("1"));
        verify(mockStaffMapper, times(2)).findIdByMobile("1");

    }

    @Test
    public void addTest() {

        when(mockStaffMapper.add(mockStaff)).thenReturn(1);
        mockStaffService.add(mockStaff);
        verify(mockStaffMapper, times(1)).add(mockStaff);
    }

    @Test
    public void updateTest() {
        when(mockStaff.getMobile()).thenReturn("1");
        when(mockStaffMapper.findStaffByMobile("1")).thenReturn(mockStaff);
        when(mockStaff.getId()).thenReturn("1");
        mockStaffService.update(mockStaff, mockNewStaff);
        verify(mockStaffMapper, times(1)).findStaffByMobile("1");
        verify(mockStaffMapper, times(1)).update(mockNewStaff);
        verify(mockStaff, times(1)).getId();
        verify(mockStaff, times(1)).getMobile();
    }

    @Test
    public void deleteTest() {
        when(mockStaff.getMobile()).thenReturn("1");
        when(mockStaffMapper.findStaffByMobile("1")).thenReturn(mockStaff);
//        doReturn(mockStaff).when(mockStaffService.findStaffByMobile(mockStaff));
        when(mockStaff.getId()).thenReturn("1");
        assertEquals("1", mockStaffService.findStaffByMobile(mockStaff).getId());
        when(mockStaffMapper.delete("1")).thenReturn(1);
        mockStaffService.delete(mockStaff);
        verify(mockStaffMapper, times(2)).findStaffByMobile("1");
        verify(mockStaffMapper, times(1)).delete("1");
        verify(mockStaff, times(2)).getId();
        verify(mockStaff, times(2)).getMobile();
    }

    @Test
    public void bindStaffEnterpriseTest() {

        when(mockEnterpriseService.findEnterpriseByNo(mockEnterprise)).thenReturn(mockEnterprise);
        when(mockEnterprise.getId()).thenReturn("1");

        when(mockStaff.getMobile()).thenReturn("1");
        when(mockStaffMapper.findStaffByMobile("1")).thenReturn(mockStaff);
        when(mockStaff.getId()).thenReturn("1");

        mockStaffService.bindStaffEnterprise(mockEnterprise, mockStaff);
        verify(mockEnterpriseService, times(1)).findEnterpriseByNo(mockEnterprise);
    }

    @Test
    public void findStaffByMobileTest() {
        when(mockStaff.getMobile()).thenReturn("1");
        when(mockStaffMapper.findStaffByMobile("1")).thenReturn(mockStaff);
        assertEquals(mockStaff, mockStaffService.findStaffByMobile(mockStaff));
    }

    @Test
    public void findStaffByNameTest() {
        when(mockStaff.getMobile()).thenReturn("1");
        when(mockStaffMapper.findStaffByMobile("1")).thenReturn(mockStaff);
        when(mockStaff.getName()).thenReturn("1");
        List<Staff> staffList = new ArrayList<>();
        staffList.add(mockStaff);
        when(mockStaffMapper.findStaffByName("1")).thenReturn(staffList);
        assertEquals(staffList, mockStaffService.findStaffByName(mockStaff));

        when(mockStaffMapper.findStaffByName("1")).thenReturn(null);
        assertEquals(null, mockStaffService.findStaffByName(mockStaff));
    }

    @Test
    public void findStaffByEnterpriseNoTest() {
        when(mockEnterprise.getNo()).thenReturn("1");
        when(mockStaffMapper.findStaffByEnterpriseNo("1")).thenReturn(null);

        assertEquals(null, mockStaffService.findStaffByEnterpriseNo(mockEnterprise));
    }

    @Test
    public void findStaffOfRoleTest() {

        when(mockStaffMapper.findStaffOfRole("1")).thenReturn(null);
        assertEquals(null, mockStaffService.findStaffOfRole("1"));
    }

    @Test
    public void isStaffExistTest() {
        when(mockStaff.getMobile()).thenReturn("1");
        when(mockStaffMapper.findIdByMobile("1")).thenReturn(null);
        assertEquals(false, mockStaffService.isStaffExist(mockStaff.getMobile()));
        when(mockStaffMapper.findIdByMobile("1")).thenReturn("1");
        assertEquals(true, mockStaffService.isStaffExist(mockStaff.getMobile()));
    }

    @Test
    public void unbindEnterpriseTest() {
        when(mockStaff.getMobile()).thenReturn("1");
        when(mockStaffMapper.unbindEnterprise("1")).thenReturn(1);
        mockStaffService.unbindEnterprise(mockStaff);
        verify(mockStaffMapper, times(1)).unbindEnterprise("1");
    }

    @Test
    public void genderTransferTest() {
        assertEquals(1, genderTransfer("男"));
        assertEquals(2, genderTransfer("女"));
        assertEquals(0, genderTransfer("12313213213"));
    }

    @Test
    public void findIdByMobileTest() {
        when(mockStaffMapper.findIdByMobile("1")).thenReturn("1");
        assertEquals("1", mockStaffService.findIdByMobile("1"));
    }

}