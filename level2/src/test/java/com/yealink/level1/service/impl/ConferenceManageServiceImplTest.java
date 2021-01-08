package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Conference;
import com.yealink.level1.bean.ConferenceRule;
import com.yealink.level1.bean.result.Schedule;
import com.yealink.level1.service.ConferenceManageService;
import com.yealink.level1.util.ScheduleComparator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.yealink.level1.util.DateUtil.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void singleConference() {

        //拼接小时数和日期

        String str = "2021-01-10 10:00:00";
        String str1 = "2021-01-09 ";
        String str2 = "10:00";


        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd ");


        try {
            Date planDay =(Date) f.parse(str1);
            System.out.println(planDay.getTime());
            long day = planDay.getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(day);
            calendar.add(Calendar.DATE,1);
            System.out.println(f.format(calendar.getTime()));
            System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
            System.out.println("-------------------------");
        } catch (ParseException e) {
            e.printStackTrace();
        }




        //化成这种形式展示出来
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {
            Date planTime = (Date)ft.parse(str);
            System.out.println(planTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat r  = new SimpleDateFormat("HH:MM");


        try {
            Date startTime = r.parse(str2);
            System.out.println(startTime.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void WeekTransferToInt(){
        String week ="sunday,friday,wednesday";
        System.out.println(Arrays.toString(weekTransferToInt(week)));
        }


    @Test
    void getFirstDay(){
        long day = new Date().getTime();
        day = addDay(day,-getDayOfMonth(day)+1);
        System.out.println( getDayOfMonth(day));

    }

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
    void singleConferenceTest(){
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