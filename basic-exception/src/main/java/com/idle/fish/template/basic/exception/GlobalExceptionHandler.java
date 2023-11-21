package com.idle.fish.template.basic.exception;

import com.idle.fish.tamplate.basic.user.BasicUser;
import com.idle.fish.tamplate.basic.user.CurrentUser;
import com.idle.fish.template.basic.restful.RestResult;
import com.idle.fish.template.basic.util.constants.CommonConstant;
import com.idle.fish.template.basic.util.HttpContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 统一异常处理器
 * 如果没有指定basePackages，那么默认会扫描所有的controller
 *
 * @author idle fish
 * @since 2023/11/21
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private Environment environment;

    private boolean isTestEnv() {
        return environment.acceptsProfiles(Profiles.of("test"));
    }

    /**
     * 逻辑异常
     *
     * @param e 异常对象
     * @return 错误提示
     */
    @ExceptionHandler(LogicException.class)
    public RestResult<String> logicExceptionHandler(LogicException e) {
        if (isTestEnv()) {
            log.error("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        }
        return RestResult.logicFail(e.getMessage(), e.getCode());
    }

    /**
     * 必要的参数没传
     *
     * @param e 异常对象
     * @return 错误提示
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RestResult<String> missParam(MissingServletRequestParameterException e) {
        if (isTestEnv()) {
            log.error("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        } else {
            log.warn("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        }
        String parameterName = e.getParameterName();
        return RestResult.logicFail("参数[" + parameterName + "]不可为空");
    }

    /**
     * 处理404
     *
     * @param e 404异常
     * @return 错误提示
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResult<String> noHandler(NoHandlerFoundException e) {
        if (isTestEnv()) {
            log.error("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        } else {
            log.warn("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        }
        return RestResult.logicFail("接口[" + e.getRequestURL() + "]不存在，请检查接口地址或者HttpMethod是否正确");
    }

    /**
     * 请求方式不支持
     *
     * @param e 异常
     * @return 错误提示
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestResult<String> methodNotSupport(HttpRequestMethodNotSupportedException e) {
        if (isTestEnv()) {
            log.error("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        } else {
            log.warn("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        }
        return RestResult.logicFail("当前接口不支持[" + e.getMethod() + "]方法，请使用" + Arrays.toString(e.getSupportedMethods()) + "方法");
    }

    /**
     * 参数校验1：@RequestParam上校验失败
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public RestResult<String> paramCheckError1(ConstraintViolationException e) {
        if (isTestEnv()) {
            log.error("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        } else {
            log.warn("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        }
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));
        return RestResult.logicFail(message);
    }


    /**
     * 参数校验2：单独使用@Valid @Validated验证路径中请求实体
     */
    @ExceptionHandler(BindException.class)
    public RestResult<String> paramCheckError2(BindException e) {
        if (isTestEnv()) {
            log.error("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        } else {
            log.warn("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        }
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return RestResult.logicFail(message);
    }

    /**
     * 参数Valid失败
     *
     * @param e 异常
     * @return 错误提示
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult<String> argument(MethodArgumentNotValidException e) {
        if (isTestEnv()) {
            log.error("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        } else {
            log.warn("{}{}", printRequestUrlAndOperator(), e.getMessage(), e);
        }
        StringBuilder sb = new StringBuilder();
        e.getBindingResult().getAllErrors()
                .forEach(x -> sb.append(x.getDefaultMessage()).append(";"));
        return RestResult.logicFail(sb.toString());
    }

    /**
     * 请求参数转换异常
     *
     * @param e 异常
     * @return 错误提示
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestResult<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("{}请求参数转换异常", printRequestUrlAndOperator(), e);
        return RestResult.logicFail("您输入的数据格式有异常，请确认后再提交");
    }

    /**
     * 请求参数转换异常
     *
     * @param e 异常
     * @return 错误提示
     */
    @ExceptionHandler(TypeMismatchException.class)
    public RestResult<String> typeMismatchException(TypeMismatchException e) {
        log.warn("{}请求参数转换异常", printRequestUrlAndOperator(), e);
        return RestResult.logicFail("您输入的数据格式有异常，请确认后再提交");
    }

    /**
     * 系统未捕获的异常
     *
     * @param e 异常对象
     * @return 错误提示
     */
    @ExceptionHandler({Exception.class, ApplicationException.class})
    public RestResult<String> exceptionHandler(Exception e) {
        log.error("{}系统未捕获的异常", printRequestUrlAndOperator(), e);
        return RestResult.sysFail();
    }

//    /**
//     * EasyExcel异常
//     *
//     * @param e 异常对象
//     * @return 错误提示
//     */
//    @ExceptionHandler({ExcelRuntimeException.class})
//    public RestResult<String> excelRuntimeExceptionHandler(ExcelRuntimeException e) {
//        log.error("{}easyExcel异常", printRequestUrlAndOperator(), e);
//        return RestResult.logicFail("请确认选择的excel文件是否正确以及excel内容是否符合要求");
//    }

    private static String printRequestUrlAndOperator() {
        StringBuilder sb = new StringBuilder();
        Optional<ServletRequestAttributes> request = HttpContextHolder.getRequest();
        request.ifPresent(servletRequestAttributes -> sb.append("【request url】:")
                .append(servletRequestAttributes.getRequest().getRequestURI())
                .append(CommonConstant.LINE_BREAK)
        );
        BasicUser user = CurrentUser.getUser();
        sb.append("【currentUser】：")
                .append(user)
                .append(CommonConstant.LINE_BREAK);
        return sb.toString();
    }
}
