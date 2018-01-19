package com.gch.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// @AutoConfigureMockMvc 接口测试用这个可以使用http请求
// @Slf4j lombok的注解,可以省去写:
// private final static Logger log = LoggerFactory.getLogger(LoggerTest.class) ;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

    @Test
    public void test1(){
        String name = "cheng" ;
        Integer age = 21 ;
        log.error("error_name = {}, error_age = {}", name, age);
        log.info("info_name ={}, info_age = {}", name, age) ;
        log.debug("debug_name = {}, debug_age = {}", name, age);
    }
}
