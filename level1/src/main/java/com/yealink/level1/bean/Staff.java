package com.yealink.level1.bean;



import com.alibaba.druid.support.json.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Many;

import javax.persistence.*;

import java.math.BigInteger;
import java.util.List;


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

    private String mobile;

    private String email;

    private int gender;

    @OneToMany(targetEntity = Account.class)
    private List<Account> accounts;

    private BigInteger createTime;

    private BigInteger modifyTime;

}
