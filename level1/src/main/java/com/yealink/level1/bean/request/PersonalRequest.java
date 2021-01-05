package com.yealink.level1.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author zhangchen
 * @description personalRequest
 * @date 2020/12/29 20:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalRequest {

    public interface Login{}
    public interface StaffInsert{}
    public interface StaffUpdate{}
    public interface StaffSelect{}
    public interface AccountInsert{}
    public interface AccountUpdate{}
    public interface AccountSelect{}
    public interface AccountDelete{}
    public interface EnterpriseInsert{}
    public interface EnterpriseSelect{}
    public interface EnterpriseName{}


    @NotNull(message = "手机号不能为空",groups = {StaffInsert.class,StaffUpdate.class,StaffSelect.class})
    private String mobile;

    private String newMobile;

    @NotNull(message = "账号不能为空",groups = {AccountInsert.class,Login.class,AccountUpdate.class,AccountSelect.class,AccountDelete.class})
    private String username;

    private String newUsername;

    @NotNull(message = "密码不能为空",groups = {AccountInsert.class,Login.class,AccountDelete.class})
    private String password;

    private String name;

    private int gender;

    private String email;

    @NotNull(message = "企业号不能为空",groups = {EnterpriseInsert.class,EnterpriseSelect.class})
    private String enterpriseNo;

    @NotNull(message = "企业名不能为空",groups = {EnterpriseInsert.class,EnterpriseName.class})
    private String enterpriseName;


}
