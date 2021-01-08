package com.yealink.level2.service.impl;

import com.yealink.level2.bean.Conference;
import com.yealink.level2.bean.ConferenceRule;
import com.yealink.level2.bean.result.Schedule;
import com.yealink.level2.service.ConferenceManageService;
import com.yealink.level2.util.ScheduleComparator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.yealink.level2.util.DateUtil.*;

/**
 * @author zhangchen
 * @description ConferenceManageServiceImplTest
 * @date 2021/1/7 9:01
 */
@SpringBootTest
class ConferenceManageServiceImplTest {

    @Autowired
    private ConferenceManageService conferenceManageService;

    @Test
    void cycleByYearMonth(){
        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();
        conference.setTitle("123123");
        conference.setConferenceNo("123123");
        conference.setStartTime("10:00");
        conference.setEndTime("12:00");

        conferenceRule.setType(41);
        conferenceRule.setGap(1);
        conferenceRule.setOrdinalMonth(1);
        conferenceRule.setOrdinalWeek(3);
        conferenceRule.setWeek("friday");
        String startDay = "2021-01-01";
        String endDay = "2023-06-01";
        conferenceRule.setStartDay(getYMDTimeStamp(startDay));
        conferenceRule.setEndDay(getYMDTimeStamp(endDay));

        List<Schedule> scheduleList = conferenceManageService.cycleByYearMonth(conference, conferenceRule);

    }

    @Test
    void cycleByYearDay(){
        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();
        conference.setTitle("123123");
        conference.setConferenceNo("123123");
        conference.setStartTime("10:00");
        conference.setEndTime("12:00");

        conferenceRule.setType(40);
        conferenceRule.setGap(1);
        conferenceRule.setOrdinalMonth(2);
        conferenceRule.setDay(10);
        String startDay = "2021-01-01";
        String endDay = "2023-06-01";
        conferenceRule.setStartDay(getYMDTimeStamp(startDay));
        conferenceRule.setEndDay(getYMDTimeStamp(endDay));

        List<Schedule> scheduleList = conferenceManageService.cycleByYearDay(conference,conferenceRule);
        System.out.println(scheduleList);
    }

    @Test
    void cycleByMonthWeek(){
        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();
        conference.setTitle("123123");
        conference.setConferenceNo("123123");
        conference.setStartTime("10:00");
        conference.setEndTime("12:00");

        conferenceRule.setType(31);
        conferenceRule.setGap(1);
        conferenceRule.setOrdinalWeek(2);
        conferenceRule.setWeek("thursday");
        String startDay = "2021-01-01";
        String endDay = "2021-06-01";
        conferenceRule.setStartDay(getYMDTimeStamp(startDay));
        conferenceRule.setEndDay(getYMDTimeStamp(endDay));

        List<Schedule> scheduleList = conferenceManageService.cycleByMonthWeek(conference,conferenceRule);
        System.out.println(scheduleList);
    }

    @Test
    void cycleByMonthDay(){
        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();
        conference.setTitle("123123");
        conference.setConferenceNo("123123");
        conference.setStartTime("10:00");
        conference.setEndTime("12:00");

        conferenceRule.setType(30);
        conferenceRule.setGap(2);
        conferenceRule.setDay(18);
        String startDay = "2021-01-01";
        String endDay = "2021-06-01";
        conferenceRule.setStartDay(getYMDTimeStamp(startDay));
        conferenceRule.setEndDay(getYMDTimeStamp(endDay));

        List<Schedule> scheduleList = conferenceManageService.cycleByMonthDay(conference,conferenceRule);
        System.out.println(scheduleList);
    }

    @Test
    void cycleByWeek(){

        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();
        conference.setTitle("123123");
        conference.setConferenceNo("123123");
        conference.setStartTime("10:00");
        conference.setEndTime("12:00");

        conferenceRule.setType(2);
        conferenceRule.setGap(1);
        conferenceRule.setWeek("thursday,saturday");
        String startDay = "2021-01-08";
        String endDay = "2021-02-01";
        conferenceRule.setStartDay(getYMDTimeStamp(startDay));
        conferenceRule.setEndDay(getYMDTimeStamp(endDay));

        List<Schedule> scheduleList = conferenceManageService.cycleByWeek(conference,conferenceRule);

        ScheduleComparator scheduleComparator = new ScheduleComparator();
        Collections.sort(scheduleList,scheduleComparator);
        System.out.println(scheduleList);
    }
    @Test
    void cycleByDay(){
        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();
        conference.setTitle("123123");
        conference.setConferenceNo("123123");
        conference.setStartTime("10:00");
        conference.setEndTime("12:00");

        conferenceRule.setType(1);
        conferenceRule.setGap(3);
        String startDay = "2021-01-08";
        String endDay = "2021-02-01";
        conferenceRule.setStartDay(getYMDTimeStamp(startDay));
        conferenceRule.setEndDay(getYMDTimeStamp(endDay));

        List<Schedule> scheduleList = conferenceManageService.cycleByDay(conference,conferenceRule);
        System.out.println(scheduleList);
    }

    @Test
    void singleConference(){
        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();
        conference.setTitle("123123");
        conference.setConferenceNo("123123");
        conference.setStartTime("10:00");
        conference.setEndTime("12:00");

        conferenceRule.setType(0);
        String startDay = "2021-01-08";
        String endDay = "2021-02-01";
        conferenceRule.setStartDay(getYMDTimeStamp(startDay));
        conferenceRule.setEndDay(getYMDTimeStamp(endDay));

        List<Schedule> scheduleList = conferenceManageService.singleConference(conference,conferenceRule);
        System.out.println(scheduleList);
    }

    @Test
    void findScheduleOfStaffTest(){

        List<String> idList = new ArrayList<>();

        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();
        conference.setTitle("123123");
        conference.setConferenceNo("123123");
        conference.setStartTime("11:00");
        conference.setEndTime("13:00");

        conferenceRule.setType(1);
        conferenceRule.setGap(1);
        String startDay = "2021-01-08";
        String endDay = "2021-01-18";
        conferenceRule.setStartDay(getYMDTimeStamp(startDay));
        conferenceRule.setEndDay(getYMDTimeStamp(endDay));

        conferenceManageService.addConference(conference,conferenceRule);
        idList.add(conference.getId());

        Conference conference1 = new Conference();
        ConferenceRule conferenceRule1 = new ConferenceRule();

        conference1.setTitle("123123");
        conference1.setConferenceNo("1231234");
        conference1.setStartTime("10:00");
        conference1.setEndTime("12:00");

        conferenceRule1.setType(2);
        conferenceRule1.setGap(1);
        conferenceRule1.setWeek("thursday,saturday");
        String startDay1 = "2021-01-08";
        String endDay1 = "2021-02-01";
        conferenceRule1.setStartDay(getYMDTimeStamp(startDay1));
        conferenceRule1.setEndDay(getYMDTimeStamp(endDay1));

        conferenceManageService.addConference(conference1,conferenceRule1);
        idList.add(conference1.getId());

        System.out.println(conferenceManageService.findScheduleOfStaff(idList));

    }
}