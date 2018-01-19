package com.buss.shop.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.buss.shop.entity.TSmsPicClassEntity;
import com.buss.shop.service.TSmsPicClassServiceI;
import com.buss.shop.service.TSmsPicInfoServiceI;




/**   
 * @Title: Controller
 * @Description: t_sms_send
 * @author onlineGenerator
 * @date 2017-02-15 15:07:48
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSmsPicClassController")
public class TSmsPicClassController extends BaseController {
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = Logger.getLogger(TSmsPicClassController.class);
//	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	
	@Autowired
	private TSmsPicClassServiceI tSmsPicClassService;

	@Autowired
	private TSmsPicInfoServiceI tSmsPicInfoService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * t_sms_send列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tSmsSend")
	public ModelAndView tSmsSend(HttpServletRequest request) {
		request.setAttribute("sendInfoId", request.getParameter("sendInfoId"));
		return new ModelAndView("com/buss/sms/tSmsSendList");
	}

	/**
	 * 平台编辑图片模版页面（上传图片，增加分类）
	 * @return
	 */
	@RequestMapping(params = "picsPage")
	public ModelAndView picsPage(HttpServletRequest request) {
		return new ModelAndView("com/dagger/platformPicsPage");
	}

	/**
	 * 获取零售商的图片分类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getPicClass", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson getPicClass(HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();//零售商ID
		if(retailerId==null){
			retailerId = "platform";//平台
		}
		List<TSmsPicClassEntity> picClass = this.tSmsPicClassService.findByProperty(TSmsPicClassEntity.class, "retailerId", retailerId);
		//计算全部分类的个数统计
		int total = 0;
		if(Utility.isNotEmpty(picClass)){
			for (TSmsPicClassEntity tSmsPicClassEntity : picClass) {
				total += tSmsPicClassEntity.getCount();
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("total", total);//总数
		
		AjaxJson j = new AjaxJson();
		j.setMsg("获取分类成功");
		j.setObj(picClass);
		j.setAttributes(paramMap);
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 获取平台的图片分类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getPlatformPicClassList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPlatformPicClassList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		int status = 500;//错误
		try {
			List<TSmsPicClassEntity> picClassList = this.tSmsPicClassService.findByProperty(TSmsPicClassEntity.class, "retailerId", "platform");
			//计算全部分类的个数统计
			if(Utility.isNotEmpty(picClassList)){
				for (TSmsPicClassEntity tSmsPicClassEntity : picClassList) {
					total += tSmsPicClassEntity.getCount();
				}
				TSmsPicClassEntity allPicClass = new TSmsPicClassEntity();//全部
				allPicClass.setId("0");
				allPicClass.setName("全部");
				allPicClass.setCount(total);
				picClassList.add(allPicClass);
			}
			status = 200;//成功
//			map.put("total", total);//总数
			map.put("picClassList", picClassList);//分类列表
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", status);
		return map;
	}
	
	
	/**
	 * 添加零售商图片分类
	 */
	@RequestMapping(params = "addPicClass", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson addPicClass(HttpServletRequest request, HttpServletResponse response) {
		
		String retailerId = ResourceUtil.getRetailerId();
		if(retailerId==null){
			retailerId = "platform";//平台
		}
		String className = request.getParameter("classname").toString();
        UUID uuid = UUID.randomUUID(); 
		TSmsPicClassEntity tSmsPicClassEntity = new TSmsPicClassEntity();
		tSmsPicClassEntity.setId(uuid.toString());
		tSmsPicClassEntity.setStatus("A");
		tSmsPicClassEntity.setCreateDate(new Date());
		tSmsPicClassEntity.setCreateDate(tSmsPicClassEntity.getCreateDate());
		tSmsPicClassEntity.setCreateBy(retailerId);
		tSmsPicClassEntity.setCount(0);
		tSmsPicClassEntity.setRetailerId(retailerId);//平台录入:platform
		tSmsPicClassEntity.setName(className);
		AjaxJson j = new AjaxJson();
		
		
		List<TSmsPicClassEntity> list = this.tSmsPicClassService.findListbySql("select * from t_sms_pic_class where status='A' and retailer_id='"+retailerId+"' and name='"+className+"'");
		if(list.size()>0){
			j.setMsg("false");
			j.setObj(tSmsPicClassEntity);
			j.setSuccess(true);
			return j;
		}
		
		try {
			this.tSmsPicClassService.save(tSmsPicClassEntity);
			j.setMsg("添加成功");
			j.setObj(tSmsPicClassEntity);
			j.setSuccess(true);
		} catch (Exception e1) {
			j.setMsg("false");
			j.setSuccess(false);
			e1.printStackTrace();
		}
		
		return j;
	}
	
	/**
	 * 删除零售商的图片分类
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "deletePicClass", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson deletePicClass(HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();//零售商ID
		if(retailerId==null){
			retailerId = "platform";//平台
		}
		String classId = request.getParameter("classId").toString();
		String unclassified = request.getParameter("unclassified").toString();

		TSmsPicClassEntity tSmsPicClassEntity = this.tSmsPicClassService.get(TSmsPicClassEntity.class, classId);
		tSmsPicClassEntity.setStatus("I");
		tSmsPicClassEntity.setUpdateDate(new Date());
		//更新分类状态
		this.tSmsPicClassService.updateEntitie(tSmsPicClassEntity);

		List<TSmsPicClassEntity> tSmsPicClassList = this.tSmsPicClassService.getClassByNameAndRetailerId("未分组", retailerId);
		if(classId == unclassified || unclassified.equals(classId)){
			//删除的分组是未分组，直接改变图片的状态
			this.tSmsPicInfoService.delPicStatusByClass(classId);
		}else{
			//判断是否存在未分组分类
			if("0".equals(unclassified)){  
				TSmsPicClassEntity tSmsPicClass = new TSmsPicClassEntity();
				tSmsPicClass.setId(Utility.getUUID());
				tSmsPicClass.setStatus("A");
				tSmsPicClass.setCreateDate(new Date());
				tSmsPicClass.setCreateDate(tSmsPicClass.getCreateDate());
				tSmsPicClass.setCreateBy(retailerId);
				tSmsPicClass.setCount(0);
				tSmsPicClass.setRetailerId(retailerId);
				tSmsPicClass.setName("未分组");
				this.tSmsPicClassService.save(tSmsPicClass);
				unclassified = tSmsPicClass.getId();
			}
			//将删除分类下的图片全部放到未分类
			int count = this.tSmsPicInfoService.updatePicStatusByClass(classId,unclassified);

			//更新未分类的图片数量
			TSmsPicClassEntity tSmsPicClass1 = this.tSmsPicClassService.get( TSmsPicClassEntity.class, unclassified);
			tSmsPicClass1.setCount(tSmsPicClass1.getCount() + count);
			tSmsPicClass1.setUpdateDate(new Date());
			this.tSmsPicClassService.doUpdateSql(tSmsPicClass1);
		}
		
		AjaxJson j = new AjaxJson();
		j.setMsg("删除分类成功");
		j.setSuccess(true);
		
		return j;
	}
	
	/**
	 * 修改分类名称
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "doUpdatePicClassName")
	@ResponseBody
	public AjaxJson doUpdatePicClassName(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		j.setMsg("分类名称修改成功");
		String classId = request.getParameter("classId").toString();
		String name = request.getParameter("name").toString();

		try {
			TSmsPicClassEntity tSmsPicClassEntity = this.tSmsPicClassService.get(TSmsPicClassEntity.class, classId);
			tSmsPicClassEntity.setName(name);
			this.tSmsPicClassService.updateEntitie(tSmsPicClassEntity);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("分类名称修改失败");
			e.printStackTrace();
		}
		return j;
	}
	
}
