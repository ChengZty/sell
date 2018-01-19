package cn.gch.girl.controller;

import cn.gch.girl.entity.Girl;
import cn.gch.girl.entity.Result;
import cn.gch.girl.repository.GirlRepository;
import cn.gch.girl.service.GirlService;
import cn.gch.girl.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GirlController {

    private final static Logger logger = LoggerFactory.getLogger(GirlController.class) ;

    @Autowired
    private GirlRepository girlRepository ;

    @Autowired
    private GirlService girlService ;

    /**
     * 查询所有女生列表
     * @return
     */
    @GetMapping(value = "/girls")
    public List<Girl> girlList(){
        logger.info("方法执行中girlList");
        return this.girlRepository.findAll() ;
    }

    /**
     * 保存女生
     * @return
     */
    @PostMapping(value = "/girls")
    public Result<Girl> girlAdd(@Valid Girl g, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        Girl girl = new Girl() ;
        girl.setName(g.getName());
        girl.setCupSize(g.getCupSize());
        girl.setAge(g.getAge());

       return ResultUtil.success() ;
    }

    // 查询一个女生
    @GetMapping(value = "/girls/{id}")
    public Girl girlFindOne(@PathVariable Integer id){
        return  this.girlRepository.findOne(id)  ;
    }

    // 更新
    @PutMapping(value = "/girls/{id}")
    public Girl girlUpdate(@PathVariable(value = "id") Integer id,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "cupSize") String cupSize,
                           @RequestParam(value = "age") Integer age){
        Girl girl = new Girl() ;
        girl.setId(id);
        girl.setName(name);
        girl.setCupSize(cupSize);
        girl.setAge(age);

        return this.girlRepository.save(girl) ;

    }

    // 删除
    @DeleteMapping(value = "/girls/{id}")
    public void girlDelete(@PathVariable(value = "id") Integer id){
        this.girlRepository.delete(id);
    }

    // 根据年龄查询女生
    @GetMapping(value = "/girls/age/{age}")
    public List<Girl> girlListByAge(@PathVariable(value = "age") Integer age){
        return girlRepository.findByAge(age) ;
    }

    @GetMapping(value = "/girls/getAge/{id}")
    public void getAge(@PathVariable("id") Integer id) throws Exception{
        this.girlService.getAge(id);
    }
}
