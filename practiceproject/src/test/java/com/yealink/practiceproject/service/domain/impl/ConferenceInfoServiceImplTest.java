package com.yealink.practiceproject.service.domain.impl;

import com.yealink.practiceproject.bean.Conference;
import com.yealink.practiceproject.bean.request.ConferenceRequest;
import com.yealink.practiceproject.dao.ConferenceMapper;
import com.yealink.practiceproject.service.userservice.impl.ConferenceInfoServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.reset;

/**
 * @author zhangchen
 * @description ConferenceInfoServiceImplTest
 * @date 2021/1/15 9:28
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConferenceInfoServiceImplTest {

    @Mock
    ConferenceManageServiceImpl mockConferenceManageService;
    @Mock
    ConferenceMapper mockConferenceMapper;


    @InjectMocks
    ConferenceInfoServiceImpl conferenceInfoService;

    @BeforeAll
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
    @AfterEach
    public void clearTestData(){
        reset(mockConferenceMapper);
        reset(mockConferenceManageService);
    }
    @Test
    void updateConferenceInfo() {
        ConferenceRequest conferenceRequest = ConferenceRequest.builder().conferenceNo("1").newConferenceNo("123").mobile("1").build();
        Conference conference = Conference.builder().conferenceNo("2").build();
        when(mockConferenceManageService.hasPermission("1", "1")).thenReturn(true);
        when(mockConferenceManageService.findConferenceByNo(any(Conference.class))).thenReturn(conference);
        when(mockConferenceManageService.isConferenceExist("1")).thenReturn(false);
        when(mockConferenceMapper.update(any(Conference.class))).thenReturn(1);
        conferenceInfoService.updateConferenceInfo(conferenceRequest);
        assertEquals("123",conference.getConferenceNo());
    }
}