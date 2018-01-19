package cn.gch.girl.entity;

/**
 *
 * http请求返回的最外层对象
 * 为了解决响应json格式不统一的问题：
 *
 */
public class Result<T> {

    /** 错误码 0-成功，1-失败 */
    private Integer code ;

    /** 提示信息 */
    private String message ;

    /** 具体的内容 */
    private T data ;

    public Result() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
