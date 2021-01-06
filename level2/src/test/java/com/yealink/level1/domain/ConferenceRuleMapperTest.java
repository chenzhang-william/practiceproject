package com.yealink.level1.domain;

import com.yealink.level1.bean.Conference;
import com.yealink.level1.bean.ConferenceRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zhangchen
 * @description ConferenceRuleMapperTest
 * @date 2021/1/6 15:39
 */
@SpringBootTest
class ConferenceRuleMapperTest {


    @Autowired
    private ConferenceRuleMapper conferenceRuleMapper;

    @Test
    void add() {
        ConferenceRule conferenceRule = new ConferenceRule();
        conferenceRuleMapper.add(conferenceRule);
        System.out.println(conferenceRule.getId());
    }
}