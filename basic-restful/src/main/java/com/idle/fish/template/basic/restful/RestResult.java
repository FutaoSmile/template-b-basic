package com.idle.fish.template.basic.restful;


import com.idle.fish.template.basic.restful.constants.ErrorMessages;
import com.idle.fish.template.basic.restful.constants.ResponseCodes;
import lombok.Getter;
import lombok.Setter;

/**
 * 封装的返回值
 *
 * @author idle fish
 * @since 2023/11/9
 */
@Getter
@Setter
public class RestResult<T> {
    /**
     * 系统繁忙
     */
    private static final RestResult<String> SYS_ERROR = new RestResult<>(ResponseCodes.SYSTEM_ERROR_FAIL, ErrorMessages.SYSTEM_ERROR, (String) null);
    /**
     * 响应状态码，0=正常，非0=异常，401=未登录，403=无权限
     */
    private int code;
    /**
     * 当code!=0时的异常提示信息
     */
    private String message;
    /**
     * 当code=0时的正常业务数据
     */
    private T data;

    private RestResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private RestResult() {
    }


    /**
     * 逻辑异常，失败结果
     *
     * @param message 错误提示
     * @return
     */
    public static RestResult<String> logicFail(String message) {
        return logicFail(message, ResponseCodes.LOGIC_FAIL);
    }

    public static RestResult<String> logicFail(String message, Integer code) {
        RestResult<String> fail = new RestResult<String>();
        fail.setCode(code == null ? ResponseCodes.LOGIC_FAIL : code);
        fail.setMessage(message);
        return fail;
    }


    /**
     * 系统异常，失败结果
     *
     * @return
     */
    public static RestResult<String> sysFail() {
        return SYS_ERROR;
    }


    /**
     * 成功结果
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> RestResult<T> simpleSuccess(T result) {
        RestResult<T> success = new RestResult<T>();
        success.setCode(ResponseCodes.SUCCESS);
        success.setData(result);
        return success;
    }
}
