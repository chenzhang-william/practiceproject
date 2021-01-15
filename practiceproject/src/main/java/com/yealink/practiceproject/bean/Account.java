package com.yealink.practiceproject.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {


    @Id
    @GeneratedValue
    private String id;

    @NotNull(message = "账号不能为空")
    @NotBlank
    private String username;

    private String password;

    private long createTime;

    private long modifyTime;

    private String staffId;

    private String enterpriseId;
}
