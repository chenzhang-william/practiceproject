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
