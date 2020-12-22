package com.yealink.level1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigInteger;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise {
    @Id
    @GeneratedValue
    private String id;

    private String no;

    private String name;

    private BigInteger createTime;

    private BigInteger modifyTime;


}
