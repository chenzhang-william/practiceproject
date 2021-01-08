package com.yealink.level2.domain;

import com.yealink.level2.bean.Conference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangchen
 * @description ConferenceMapperTest
 * @date 2021/1/7 10:58
 */
@SpringBootTest
class ConferenceMapperTest {
    @Autowired
    private ConferenceMapper conferenceMapper;

    @Test
    void add() {
        Conference conference = new Conference();
        conference.setTitle("12323213");
        conference.setConferenceNo("123131313");
        conference.setStartTime("10:00");
        conference.setEndTime("12:00");
        conferenceMapper.add(conference);
    }
}