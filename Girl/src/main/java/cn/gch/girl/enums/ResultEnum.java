package cn.gch.girl.enums;

/**
 * 作用: 统一管理异常信息code和message
 */
public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误") ,
    SUCCESS(0, "成功"),
    PRIMARY_SCHOOL(101, "你还在读小学吧"),
    MIDDLE_SCHOOL(102, "你可能在读高中");


    private Integer code ;

    private String message ;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
