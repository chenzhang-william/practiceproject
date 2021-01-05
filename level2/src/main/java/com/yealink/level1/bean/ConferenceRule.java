package com.yealink.level1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author zhangchen
 * @description ConferenceRule
 * @date 2021/1/5 17:12
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRule {
    @Id
    @GeneratedValue
    private String id;

    private byte type;

    private byte gap;

    private byte day;

    private byte week;

    private byte ordinalWeek;

    private byte ordinalMonth;

    private long startDay;

    private long endDay;

    private long createTime;

    private long modifyTime;
}
