package com.yealink.level1.util;

import com.yealink.level1.bean.result.ErrorCode;
import com.yealink.level1.bean.result.Result;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangchen
 * @description CtrlAdvice
 * @date 2020/12/30 17:26
 */

@ControllerAdvice(basePackages = "com.yealink.level1.controller")
@ResponseBody
public class CtrlAdvice {
    @ExceptionHandler
    public Result exceptionHandler(MethodArgumentNotValidException e){
        Map<String,String> collect = e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return Result.failure(ErrorCode.PARAM_IS_INVALID,collect);
    }

    @ExceptionHandler
    public Result exceptionHandler(ConstraintViolationException e){
        Map<Path, String> collect = e.getConstraintViolations().stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
        return Result.failure(ErrorCode.PARAM_IS_INVALID,collect);
    }
}
