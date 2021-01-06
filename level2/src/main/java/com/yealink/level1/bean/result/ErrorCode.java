package com.yealink.level1.bean.result;

/**
 * @author zhangchen
 * @description ResultCode
 * @date 2020/12/30 14:50
 */

public enum ErrorCode {
    PARAM_IS_INVALID("1000","参数不正确"),
    ACCOUNT_IS_NOT_EXIST("2000","账号不存在"),
    PASSWORD_IS_WRONG("2001","密码错误"),
    ENTERPRISE_HAS_EXIST("2003","企业已存在"),
    STAFF_IS_NOT_EXIST("2004","员工不存在"),
    ENTERPRISE_IS_NOT_EXIST("2005","企业不存在"),
    STAFF_HAS_EXIST("2006","员工已存在"),
    DEPARTMENT_HAS_EXIST("2007","部门已存在"),
    DEPARTMENT_IS_NOT_EXIST("2008","部门不存在"),
    ENTERPRISE_MISMATCH("2009","企业信息不匹配"),
    DEPRELATION_HAS_EXIST("2010","已在该部门"),
    ROLERELATION_HAS_EXIST("2011","角色关系已存在"),
    ROLERELATION_IS_NOT_EXIST("2012","角色关系不存在"),

    TIME_IS_ILLEGAL("2018","时间不合法"),
    CONFERENCE_HAS_EXIST("2013","会议已存在"),
    CONFERENCE_IS_NOT_EXIST("2015","会议不存在"),
    PARTICIPANT_HAS_EXIST("2016","参会者已存在"),
    PARTICIPANT_IS_NOT_EXIST("2017","参会者不存在"),
    NO_PERMISSION("2014","无权限"),
    ACCOUNT_HAS_EXIST("2002","账号已存在");


    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
