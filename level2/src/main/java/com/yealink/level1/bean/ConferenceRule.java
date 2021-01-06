package com.yealink.level1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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

    @NotNull(message = "类型不能为空")
    private int type;

    private int gap;

    private int day;

    private int week;

    private int ordinalWeek;

    private int ordinalMonth;

    private long startDay;

    private long endDay;

    private long createTime;

    private long modifyTime;
}
