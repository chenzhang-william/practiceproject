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
 * @description Conference
 * @date 2021/1/5 17:11
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Conference {
    @Id
    @GeneratedValue
    private String id;

    private String title;

    @NotNull(message = "会议号")
    private String conferenceNo;

    private String ruleId;

    private long startTime;

    private long endTime;

    private long createTime;

    private long modifyTime;
}
