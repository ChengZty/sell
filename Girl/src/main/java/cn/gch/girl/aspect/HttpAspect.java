package cn.gch.girl.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 记录每一个http请求
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class) ;

    @Pointcut("execution(public * cn.gch.girl.controller.GirlController.*(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest() ;

        //url
        logger.info("url = {}",request.getRequestURI());
        //ip
        logger.info("ip = {}", request.getRemoteAddr());
        //method
        logger.info("method = {}", request.getMethod());

        //类方法
        logger.info("class_method = {}", joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        //参数
        logger.info("method_args = {}", joinPoint.getArgs());
    }

    /**
     * 在方法执行后, return之前执行
     */
    @After("log()")
    public void doAfter(){
        logger.info("after111111111111111");
    }


    /**
     * 在方法return之后执行
     * 可以获取response的内容
     */
    @AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturning(Object object){
        logger.info("response = {}", object);
    }
}
