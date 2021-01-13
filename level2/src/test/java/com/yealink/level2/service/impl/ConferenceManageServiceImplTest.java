package com.yealink.level2.service.impl;

import com.yealink.level2.bean.Conference;
import com.yealink.level2.bean.ConferenceRule;
import com.yealink.level2.bean.result.Schedule;
import com.yealink.level2.domain.ConferenceMapper;
import com.yealink.level2.domain.ConferenceRuleMapper;
import com.yealink.level2.service.ConferenceManageService;
import com.yealink.level2.util.ScheduleComparator;
import org.junit.jupiter.api.*;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.yealink.level2.util.DateUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


/**
 * @author zhangchen
 * @description ConferenceManageServiceImplTest
 * @date 2021/1/7 9:01
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConferenceManageServiceImplTest {

    @Mock
    ConferenceMapper mockConferenceMapper;
    @Mock
    ConferenceRuleMapper mockConferenceRuleMapper;

    @InjectMocks
    ConferenceManageServiceImpl conferenceManageService;

    @BeforeAll
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
    @AfterEach
    public void clearTestData(){
        reset(mockConferenceMapper);
        reset(mockConferenceRuleMapper);
    }

    @Test
    void addConferenceTest(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();
        when(mockConferenceRuleMapper.add(ruleTable.get("singleConference"))).thenReturn(1);
        when(mockConferenceMapper.add(conferenceTable.get("singleConference"))).thenReturn(1);
        conferenceManageService.addConference(conferenceTable.get("singleConference"),ruleTable.get("singleConference"));

        verify(mockConferenceRuleMapper).add(ruleTable.get("singleConference"));
        verify(mockConferenceMapper).add(conferenceTable.get("singleConference"));
    }

    @Test
    void findConferenceByNoTest(){
        Conference conference = Conference.builder().conferenceNo("123").build();
        when(mockConferenceMapper.findByNo("123")).thenReturn(conference);
        assertEquals(conference,conferenceManageService.findConferenceByNo(conference));
    }


    @Test
    void cycleByYearMonth(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();

        List<Schedule> scheduleList = conferenceManageService.cycleByYearMonth(conferenceTable.get("cycleByYearWeek"),ruleTable.get("cycleByYearWeek"));
        assertEquals(5,scheduleList.size());
    }

    @Test
    void cycleByYearDay(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();

        List<Schedule> scheduleList = conferenceManageService.cycleByYearDay(conferenceTable.get("cycleByYearDay"),ruleTable.get("cycleByYearDay"));
        assertEquals(5,scheduleList.size());
    }

    @Test
    void cycleByMonthWeek(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();

        List<Schedule> scheduleList = conferenceManageService.cycleByMonthWeek(conferenceTable.get("cycleByMonthWeek"),ruleTable.get("cycleByMonthWeek"));

        assertEquals(4,scheduleList.size());
    }

    @Test
    void cycleByMonthDay(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();

        List<Schedule> scheduleList = conferenceManageService.cycleByMonthDay(conferenceTable.get("cycleByMonthDay"),ruleTable.get("cycleByMonthDay"));
        assertEquals(4,scheduleList.size());
    }

    @Test
    void cycleByWeek(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();

        List<Schedule> scheduleList = conferenceManageService.cycleByWeek(conferenceTable.get("cycleByWeek"),ruleTable.get("cycleByWeek"));

        assertEquals(4,scheduleList.size());
    }
    @Test
    void cycleByDay(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();

        List<Schedule> scheduleList = conferenceManageService.cycleByDay(conferenceTable.get("cycleByDay"),ruleTable.get("cycleByDay"));
        assertEquals(5,scheduleList.size());

        scheduleList = conferenceManageService.cycleByDay(conferenceTable.get("cycleByDay"),ruleTable.get("cycleByDayNoGap"));
        assertEquals(1,scheduleList.size());

    }

    @Test
    void singleConference(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();

        List<Schedule> scheduleList = conferenceManageService.singleConference(conferenceTable.get("singleConference"),ruleTable.get("singleConference"));

        assertEquals(1,scheduleList.size());
    }

    @Test
    void findScheduleOfStaffTest(){
        HashMap<String,Conference> conferenceTable = conferenceTable();
        HashMap<String,ConferenceRule> ruleTable = ruleTable();
        List<String> idList = new ArrayList<>();
        idList.add("000");
        idList.add("100");
        idList.add("200");

        List<Conference> conferenceList = new ArrayList<>();
        conferenceList.add(conferenceTable.get("singleConference"));
        conferenceList.add(conferenceTable.get("cycleByDay"));
        conferenceList.add(conferenceTable.get("cycleByWeek"));
        when(mockConferenceMapper.findByIdList(idList)).thenReturn(conferenceList);

        List<String> ruleIdList = new ArrayList<>();
        for(Conference s: conferenceList){
            ruleIdList.add(s.getRuleId());
        }
        List<ConferenceRule> ruleList = new ArrayList<>();
        ruleList.add(ruleTable.get("singleConference"));
        ruleList.add(ruleTable.get("cycleByDay"));
        ruleList.add(ruleTable.get("cycleByWeek"));
        when(mockConferenceRuleMapper.findByIdList(ruleIdList)).thenReturn(ruleList);




        assertEquals(10,conferenceManageService.findScheduleOfStaff(idList).size());
    }

    public HashMap<String,Conference> conferenceTable(){
        HashMap<String,Conference> conferenceTable = new HashMap<>();
        conferenceTable.put("singleConference",Conference.builder().id("000").title("singleConference").conferenceNo("000").ruleId("000").startTime("00:00").endTime("00:10").build());
        conferenceTable.put("cycleByDay",Conference.builder().id("100").title("cycleByDay").conferenceNo("100").ruleId("100").startTime("01:00").endTime("01:10").build());
        conferenceTable.put("cycleByWeek",Conference.builder().id("200").title("cycleByWeek").conferenceNo("200").ruleId("200").startTime("02:00").endTime("02:10").build());
        conferenceTable.put("cycleByMonthDay",Conference.builder().id("300").title("cycleByMonthDay").conferenceNo("300").ruleId("300").startTime("03:00").endTime("01:10").build());
        conferenceTable.put("cycleByMonthWeek",Conference.builder().id("310").title("cycleByMonthWeek").conferenceNo("310").ruleId("310").startTime("03:10").endTime("03:20").build());
        conferenceTable.put("cycleByYearDay",Conference.builder().id("400").title("cycleByYearDay").conferenceNo("400").ruleId("400").startTime("04:00").endTime("04:10").build());
        conferenceTable.put("cycleByYearWeek",Conference.builder().id("410").title("cycleByYearWeek").conferenceNo("410").ruleId("410").startTime("04:10").endTime("04:20").build());
        return conferenceTable;
    }

    public HashMap<String,ConferenceRule> ruleTable(){
        HashMap<String,ConferenceRule> ruleTable = new HashMap<>();
        ruleTable.put("singleConference",ConferenceRule.builder().id("000").type(0).startDay(getYMDTimeStamp("2021-01-01")).endDay(getYMDTimeStamp("2021-01-02")).build());
        ruleTable.put("cycleByDay",ConferenceRule.builder().id("100").type(1).gap(1).startDay(getYMDTimeStamp("2021-01-05")).endDay(getYMDTimeStamp("2021-01-10")).build());
        ruleTable.put("cycleByDayNoGap",ConferenceRule.builder().id("100").type(1).startDay(getYMDTimeStamp("2021-01-05")).endDay(getYMDTimeStamp("2021-01-10")).build());
        ruleTable.put("cycleByWeek",ConferenceRule.builder().id("200").type(2).gap(1).week("sunday,saturday").startDay(getYMDTimeStamp("2021-01-04")).endDay(getYMDTimeStamp("2021-01-18")).build());
        ruleTable.put("cycleByMonthDay",ConferenceRule.builder().id("300").type(30).gap(1).day(10).startDay(getYMDTimeStamp("2021-01-01")).endDay(getYMDTimeStamp("2021-04-11")).build());
        ruleTable.put("cycleByMonthWeek",ConferenceRule.builder().id("310").type(31).gap(1).ordinalWeek(2).week("monday").startDay(getYMDTimeStamp("2021-01-01")).endDay(getYMDTimeStamp("2021-05-01")).build());
        ruleTable.put("cycleByYearDay",ConferenceRule.builder().id("400").type(40).gap(1).ordinalMonth(1).day(20).startDay(getYMDTimeStamp("2021-01-01")).endDay(getYMDTimeStamp("2025-01-21")).build());
        ruleTable.put("cycleByYearWeek",ConferenceRule.builder().id("410").type(41).gap(1).ordinalMonth(1).ordinalWeek(2).week("sunday").startDay(getYMDTimeStamp("2021-01-01")).endDay(getYMDTimeStamp("2025-01-06")).build());
        return ruleTable;
    }


}