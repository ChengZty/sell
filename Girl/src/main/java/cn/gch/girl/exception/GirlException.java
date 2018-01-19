package cn.gch.girl.exception;

import cn.gch.girl.enums.ResultEnum;

/**
 * 不要继承Exception, 抛出Exception事务不会回滚
 * 抛出RuntimeException时事务才会回滚
 */
public class GirlException extends RuntimeException {
    /** message父类有就不要重复定义了*/

    /** 错误码 */
    private Integer code ;



    public Integer getCode() {
        return code;
    }


    public void setCode(Integer code) {
        this.code = code;
    }

   public GirlException(Integer code, String message) {
        super(message);
        this.code = code ;
   }

   public GirlException(ResultEnum resultEnum){
        super(resultEnum.getMessage()) ;
        this.code = resultEnum.getCode() ;
   }
}
