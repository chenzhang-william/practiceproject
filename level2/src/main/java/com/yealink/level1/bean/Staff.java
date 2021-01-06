package com.yealink.level1.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.*;


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

    @NotNull(message = "手机不能为空")
    private String mobile;

    private String email;

    private int gender;

    private long createTime;

    private long modifyTime;

}
