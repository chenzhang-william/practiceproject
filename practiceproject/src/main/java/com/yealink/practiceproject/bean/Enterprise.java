package com.yealink.practiceproject.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise {
    @Id
    @GeneratedValue
    private String id;

    private String no;

    private String name;

    private long createTime;

    private long modifyTime;


}
