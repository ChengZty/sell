package cn.gch.girl.utils;

import cn.gch.girl.entity.Result;

/**
 * 处理http请求响应结果的工具类
 */
public class ResultUtil {

    public static Result success(Object object){
        Result result = new Result() ;
        result.setCode(0);
        result.setMessage("success");
        result.setData(object);
        return result ;
    }

    public static Result error(Integer code, String message){
        Result result = new Result() ;
        result.setCode(code);
        result.setMessage(message);
        return  result ;
    }

    public static Result success(){
        return success(null) ;
    }
}
