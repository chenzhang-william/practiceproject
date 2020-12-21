package com.yealink.level1.bean;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.math.BigInteger;


/**
 * 员工的实体类
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String enterpriseId;

    private BigInteger createTime;

    private BigInteger modifyTime;

    private String mobile;

    private String email;

    private int gender;

}
