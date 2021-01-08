package com.yealink.level2.domain;

import com.yealink.level2.bean.ConferenceRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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