package com.buss.news.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.buss.news.entity.TNewsEntity;
import com.buss.news.entity.TNewsTypeEntity;
import com.buss.news.service.TNewsTypeServiceI;
import com.buss.news.vo.TNewsTypeVO;
import com.buss.template.entity.TTemplateTypeEntity;

import cn.redis.service.RedisService;
import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 话题分类
 * @author onlineGenerator
 * @date 2016-04-09 12:14:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tNewsTypeController")
public class TNewsTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TNewsTypeController.class);

	@Autowired
	private TNewsTypeServiceI tNewsTypeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 平台话题分类tab页
	 * @return
	 */
	@RequestMapping(params = "newsTypeTabs")
	public ModelAndView newsTypeTabs(HttpServletRequest request) {
//		return new ModelAndView("com/buss/news/tNewsTypeTabs");
		return new ModelAndView("com/buss/news/tNewsTypeList");
	}
	/**
	 * 零售商话题分类tab页
	 * @return
	 */
	@RequestMapping(params = "retailerNewsTypeTabs")
	public ModelAndView retailerNewsTypeTabs(HttpServletRequest request) {
		return new ModelAndView("com/buss/news/tRetailerNewsTypeTabs");
	}

	/**
	 * 话题分类列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/news/tNewsTypeList");
	}
	
	/**
	 * 零售商话题分类列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "retailerList")
	public ModelAndView retailerList(HttpServletRequest request) {
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/news/tNewsTypeListOfRetailer");
	}
	
	/**
	 * 零售商APP首页选中话题分类列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tNewsTypelist")
	public ModelAndView tNewsTypelist(HttpServletRequest request) {
		return new ModelAndView("com/buss/news/tNewsTypeAppList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TTemplateTypeEntity tNewsType,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TTemplateTypeEntity.class, dataGrid);
		//查询条件组装器
		String sqlstr = "";
		String userType = ResourceUtil.getSessionUserName().getUserType();
		cq.eq("level", "1");
		cq.eq("platformType", TTemplateTypeEntity.PLATFORM_TYPE_1);
		cq.eq("templateType", TTemplateTypeEntity.TEMPLATE_TYPE_2);
		if(!TSUser.USER_TYPE_01.equals(userType)){
			sqlstr = "id not in ('"+TNewsEntity.NEWS_TYPE_COURSE+"','"+TNewsEntity.NEWS_TYPE_STORY+"')";//零售商要过滤掉 1：管家课堂，2：管家故事
		}
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tNewsType, request.getParameterMap(),sqlstr);
		try{
		//自定义追加查询条件
//			String tp = request.getParameter("tp");//平台类别
//			if(TNewsTypeEntity.PLATFORM_TYPE_2.equals(tp)){//零售商话题分类
//				cq.eq("retailerId", ResourceUtil.getRetailerId());
//			}
//			cq.eq("platformType", tp);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tNewsTypeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除话题分类
	 * @return
	 */
	/*@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TNewsTypeEntity tNewsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tNewsType = systemService.flushEntity(TNewsTypeEntity.class, tNewsType.getId());
		message = "话题分类删除成功";
		try{
			tNewsType.setStatus("I");
			tNewsTypeService.updateEntitie(tNewsType);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "话题分类删除失败";
			throw new BusinessException(e.getMessage());
		}
		tNewsTypeService.clearNewsTypeReids();
		j.setMsg(message);
		return j;
	}*/
	
	/**
	 * 批量删除话题分类
	 * @return
	 */
	/* @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "话题分类删除成功";
		List<TNewsTypeEntity> list = new ArrayList<TNewsTypeEntity>();
		try{
			for(String id:ids.split(",")){
				TNewsTypeEntity tNewsType = systemService.flushEntity(TNewsTypeEntity.class,id);
				tNewsType.setStatus("I");
				tNewsTypeService.updateEntitie(tNewsType);
				list.add(tNewsType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "话题分类删除失败";
			throw new BusinessException(e.getMessage());
		}
		tNewsTypeService.clearNewsTypeReids();
		j.setMsg(message);
		return j;
	}*/


	/**
	 * 添加话题分类
	 * @param ids
	 * @return
	 */
	/*@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TNewsTypeEntity tNewsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "话题分类添加成功";
		try{
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isEmpty(retailerId)){
				retailerId = "admin";
			}
			String code = tNewsTypeService.getUniqueMaxCode();
			tNewsType.setCode(code);
			tNewsTypeService.save(tNewsType);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "话题分类添加失败";
			throw new BusinessException(e.getMessage());
		}
		tNewsTypeService.clearNewsTypeReids();
		j.setMsg(message);
		return j;
	}*/
	
	/**
	 * 更新话题分类
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TTemplateTypeEntity tNewsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "话题分类更新成功";
		TTemplateTypeEntity t = tNewsTypeService.get(TTemplateTypeEntity.class, tNewsType.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tNewsType, t);
			tNewsTypeService.updateEntitie(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "话题分类更新失败";
			throw new BusinessException(e.getMessage());
		}
//		try
//		{
//			this.updateNewsTypeRedis(tNewsType,"U");
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		tNewsTypeService.clearNewsTypeReids();
		j.setMsg(message);
		return j;
	}
	

	/**更新话题分类redis
	 * @param tNewsType
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	private void updateNewsTypeRedis(TNewsTypeEntity tNewsType,String type) throws JsonGenerationException, JsonMappingException, IOException {
		String value = redisService.get(GlobalConstants.NEWS_TYPE);
		if(Utility.isNotEmpty(value)){
			Map<String,Object> mapResult = new HashMap<String, Object>();
			try {
				mapResult =  (HashMap<String, Object>) JSONUtils.parse(value);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if(Utility.isNotEmpty(mapResult) && !mapResult.isEmpty()){
				List<HashMap<String, Object>> typeList = (List<HashMap<String, Object>>)mapResult.get("typeList");
				if(Utility.isNotEmpty(typeList)){
					if("A".equals(type)){//新增
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("code", tNewsType.getCode()+"");
						map.put("coverPic", tNewsType.getCoverPic());
						map.put("name", tNewsType.getName());
						typeList.add(map);
					}else if("U".equals(type)){//修改
						for(HashMap<String, Object> typeVo : typeList){
							if(typeVo.get("code").equals(tNewsType.getCode()+"")){
								typeVo.put("coverPic", tNewsType.getCoverPic());
								typeVo.put("name", tNewsType.getName());
								break;
							}
						}
					}else if("D".equals(type)){//删除
						for(HashMap<String, Object> typeVo : typeList){
							if(typeVo.get("code").equals(tNewsType.getCode()+"")){
								typeList.remove(typeVo);
								break;
							}
						}
					}
					mapResult.put("typeList", typeList);
					String val = MAPPER.writeValueAsString(mapResult);
					redisService.set(GlobalConstants.NEWS_TYPE,val);
				}
			}
		}else{//初始化话题分类
			List<TNewsTypeEntity> allList = this.systemService.getList(TNewsTypeEntity.class);
			if(Utility.isNotEmpty(allList)){
				Map<String,Object> mapResult = new HashMap<String, Object>();
				List<TNewsTypeVO> list = new ArrayList<TNewsTypeVO>();
				for(TNewsTypeEntity entity : allList){
					TNewsTypeVO vo = new TNewsTypeVO();
					vo.setCode(entity.getCode()+"");
					vo.setName(entity.getName());
					vo.setCoverPic(entity.getCoverPic());
					list.add(vo);
				}
				mapResult.put("typeList", list);
				String val = MAPPER.writeValueAsString(mapResult);
				redisService.set(GlobalConstants.NEWS_TYPE,val);
			}
		}
		
	}

	/**
	 * 话题分类新增页面跳转
	 * 
	 * @return
	 */
	/*@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TNewsTypeEntity tNewsType, HttpServletRequest req) {
		String retailerId = ResourceUtil.getRetailerId();
		String platformType = null;
		if(StringUtil.isEmpty(retailerId)){
			retailerId = "admin";//平台
			platformType = TNewsTypeEntity.PLATFORM_TYPE_1;
		}else{
			platformType = TNewsTypeEntity.PLATFORM_TYPE_2;
		}
		req.setAttribute("retailerId", retailerId);
		req.setAttribute("platformType", platformType);
//		if (StringUtil.isNotEmpty(tNewsType.getId())) {
//			tNewsType = tNewsTypeService.flushEntity(TNewsTypeEntity.class, tNewsType.getId());
//			req.setAttribute("tNewsTypePage", tNewsType);
//		}
		return new ModelAndView("com/buss/news/tNewsType-add");
	}*/
	
	/**
	 * 话题分类编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TTemplateTypeEntity tNewsType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tNewsType.getId())) {
			tNewsType = tNewsTypeService.flushEntity(TTemplateTypeEntity.class, tNewsType.getId());
			req.setAttribute("tNewsTypePage", tNewsType);
		}
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode
		req.setAttribute("load", req.getParameter("load"));//是否是查看
		return new ModelAndView("com/buss/news/tNewsType-update");
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(params = "uploadPic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadPic(TSCategoryEntity category,HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FtpUtil ftpUtil = FtpUtil.getInstance();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();// 获取上传文件对象
			String fileName = mf.getOriginalFilename();// 获取文件名
			String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
			String myfilename="";
			String noextfilename="";//不带扩展名
			noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+StringUtil.random(8);//自定义文件名称
			myfilename = noextfilename+"."+extend;//自定义文件名称
			InputStream is = mf.getInputStream();
			ftpUtil.uploadFileToFtpServer(GlobalConstants.CATEGORY_NEWS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.CATEGORY_NEWS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}*/
	
	/**
	 * 复制话题分类
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "doCopy")
	@ResponseBody
	public AjaxJson doCopy(TNewsTypeEntity tNewsType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "话题分类复制成功";
		TNewsTypeEntity t = tNewsTypeService.get(TNewsTypeEntity.class, tNewsType.getId());
		try {
			tNewsTypeService.doCopy(t);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "话题分类复制失败";
			throw new BusinessException(e.getMessage());
		}
		tNewsTypeService.clearNewsTypeReids();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量复制话题分类
	 * @return
	 */
	 @RequestMapping(params = "doBatchCopy")
	@ResponseBody
	public AjaxJson doBatchCopy(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "话题分类复制成功";
		try{
			for(String id:ids.split(",")){
				TNewsTypeEntity tNewsType = systemService.get(TNewsTypeEntity.class,id);
				tNewsTypeService.doCopy(tNewsType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "话题分类复制失败";
			throw new BusinessException(e.getMessage());
		}
		tNewsTypeService.clearNewsTypeReids();
		j.setMsg(message);
		return j;
	}
	
		/**
		 * 全部复制话题分类
		 * @return
		 */
		 @RequestMapping(params = "doAllCopy")
		@ResponseBody
		public AjaxJson doAllCopy(String ids,HttpServletRequest request){
			AjaxJson j = new AjaxJson();
			message = "话题分类复制成功";
			String hql = "from TNewsTypeEntity where  platformType = '1'";
			List<TNewsTypeEntity> list = tNewsTypeService.findHql(hql, null);
			try{
				if(list.size()>0){
					for(TNewsTypeEntity tNewsType : list){
						tNewsTypeService.doCopy(tNewsType);
						systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				message = "话题分类复制失败";
				throw new BusinessException(e.getMessage());
			}
			tNewsTypeService.clearNewsTypeReids();
			j.setMsg(message);
			return j;
		}
	
		 }
