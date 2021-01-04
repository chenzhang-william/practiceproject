package com.yealink.level1.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author zhangchen
 * @description EnterpriseRequest
 * @date 2021/1/4 18:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseRequest {

    public interface StaffInsert{}
    public interface StaffUpdate{}
    public interface StaffSelect{}
    public interface AccountInsert{}
    public interface AccountUpdate{}
    public interface AccountSelect{}
    public interface AccountDelete{}
    public interface EnterpriseInsert{}
    public interface EnterpriseSelect{}
    public interface EnterpriseUpdate{}
    public interface EnterpriseDelete{}

    @NotNull(message = "手机号不能为空",groups = {EnterpriseRequest.StaffInsert.class, EnterpriseRequest.StaffUpdate.class, EnterpriseRequest.StaffSelect.class})
    private String mobile;

    @NotNull(message = "企业号不能为空",groups = {EnterpriseRequest.EnterpriseInsert.class, EnterpriseRequest.EnterpriseSelect.class,EnterpriseUpdate.class,EnterpriseDelete.class})
    private String enterpriseNo;

    private String newEnterpriseNo;

    @NotNull(message = "企业名不能为空",groups = {EnterpriseRequest.EnterpriseInsert.class})
    private String enterpriseName;
    
    @NotNull(message = "邮箱不能为空",groups = {EnterpriseRequest.StaffInsert.class})
    private String email;

    @NotNull(message = "性别不能为空",groups = {EnterpriseRequest.StaffInsert.class})
    private int gender;

    @NotNull(message = "邮箱不能为空",groups = {EnterpriseRequest.StaffInsert.class})
    private String name;

    @NotNull(message = "账号不能为空",groups = {EnterpriseRequest.AccountUpdate.class})
    private String username;

    @NotNull(message = "密码不能为空",groups = {EnterpriseRequest.AccountInsert.class, EnterpriseRequest.AccountDelete.class})
    private String password;


}
