package com.yealink.level1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author zhangchen
 * @description ConferenceParticipant
 * @date 2021/1/5 17:12
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceParticipant {
    @Id
    @GeneratedValue
    private String id;

    private String conferenceId;

    private String participantId;

    private byte status;

    private long createTime;

    private long modifyTime;

}
