package cn.gch.girl.controller;

import cn.gch.girl.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private GirlProperties girlProperties ;

    @RequestMapping(value = {"/hello", "/hi"},method = RequestMethod.POST)
    public String hello(){
        return  girlProperties.getCupSize()+girlProperties.getAge() ;
    }
}
