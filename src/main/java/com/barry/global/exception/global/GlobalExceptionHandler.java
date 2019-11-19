package com.barry.global.exception.global;

import com.barry.global.exception.common.ResultStatusCode;
import com.barry.global.exception.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * 类描述: 全局异常拦截处理器
 *  1.处理自定义异常
 *  2.未知异常统一返回服务器错误
 *  3.已经catch到的异常不会被捕获
 *  4.异常的体系结构中，哪个异常与目标方法抛出的异常血缘关系越紧密，就会被哪个捕捉到。
 * @see ExceptionHandler：统一处理某一类异常，从而能够减少代码重复率和复杂度
 * @see ControllerAdvice：异常集中处理，更好的使业务逻辑与异常处理剥离开
 * @see ResponseStatus：可以将某种异常映射为HTTP状态码 成功则Status Code: 200
 * @author licanfeng
 * @date 2019/3/11 16:13
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Result handleHttpMessageNotReadableException(Exception e, HttpServletRequest request) {
        log.error("参数解析失败,URI:"+request.getRequestURI()+",headers:"+request.getHeaderNames().toString(), e);
        if (e instanceof BindException){
            return new Result(ResultStatusCode.BAD_REQUEST.getCode(), ((BindException)e).getAllErrors().get(0).getDefaultMessage());
        } else if (e instanceof ServletRequestBindingException){
            return new Result(ResultStatusCode.INVALID_REQUEST.getCode(), ((ServletRequestBindingException)e).getMessage());
        }
        return new Result(ResultStatusCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return new Result(ResultStatusCode.METHOD_NOT_ALLOWED, null);
    }

    /**
     * 其他全局异常在此捕获
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {
        log.error("服务运行异常", e);
//        if (e instanceof ShiroException) {
//            return new Result(ResultStatusCode.UNAUTHO_ERROR);
//        } else if (e instanceof JedisConnectionException) {
//            //redis连接异常
//            return new Result(ResultStatusCode.REDIS_CONNECT_ERROR);
//        } else if (e instanceof JedisException) {
//            //redis异常
//            return new Result(ResultStatusCode.REDIS_ERROR);
//        }
        return new Result(ResultStatusCode.SYSTEM_ERR, null);
    }

}