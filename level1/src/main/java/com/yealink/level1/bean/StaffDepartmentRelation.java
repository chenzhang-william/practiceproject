package com.yealink.level1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StaffDepartmentRelation {
    @Id
    @GeneratedValue
    private String id;

    private String staffId;

    private String departmentId;

    private String position;

    private BigInteger createTime;

    private BigInteger modifyTime;
}
