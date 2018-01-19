package cn.gch.girl.service;

import cn.gch.girl.entity.Girl;
import cn.gch.girl.enums.ResultEnum;
import cn.gch.girl.exception.GirlException;
import cn.gch.girl.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务层
 */
@Service
public class GirlService {

    @Autowired
    private GirlRepository girlRepository ;
    /**
     * 获取年龄并判断
     * @param id
     */
    public Integer getAge(Integer id) throws Exception{
        Girl girl = this.girlRepository.findOne(id) ;
        Integer age = girl.getAge() ;

        if(age < 10){
            // 返回“你还在上小学吧”
            throw new GirlException(ResultEnum.PRIMARY_SCHOOL);
        } else if (age > 10 && age < 16){
            // 返回“你可能在上高中吧”
            throw new GirlException(ResultEnum.MIDDLE_SCHOOL) ;
        }

        // 如果>16：加钱
        return 0  ;
    }

    public Girl findOne(Integer id){
        return this.girlRepository.findOne(id) ;
    }
}
