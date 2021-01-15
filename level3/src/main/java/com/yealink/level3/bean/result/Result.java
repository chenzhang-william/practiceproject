package com.yealink.level3.bean.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangchen
 * @description Result
 * @date 2020/12/30 14:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private boolean success;

    private String code;

    private String msg;

    private Object data;

    public static Result success(){
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static Result failure(ErrorCode errorCode){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(errorCode.getCode());
        result.setMsg(errorCode.getMsg());
        return result;
    }

    public static Result failure(ErrorCode errorCode, Object data){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(errorCode.getCode());
        result.setMsg(errorCode.getMsg());
        result.setData(data);
        return result;
    }


}
