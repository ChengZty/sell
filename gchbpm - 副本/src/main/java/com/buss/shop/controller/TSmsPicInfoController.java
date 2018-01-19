package com.buss.shop.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGridJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.buss.shop.entity.TSmsPicClassEntity;
import com.buss.shop.entity.TSmsPicInfoEntity;
import com.buss.shop.service.TSmsPicClassServiceI;
import com.buss.shop.service.TSmsPicInfoServiceI;
import common.GlobalConstants;

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
@RequestMapping("/tSmsPicInfoController")
public class TSmsPicInfoController extends BaseController {

	@Autowired
	private TSmsPicInfoServiceI tSmsPicInfoService;
	@Autowired
	private TSmsPicClassServiceI tSmsPicClassService;

	@Autowired
	private SystemService systemService;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * t_sms_send列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tSmsSend")
	public ModelAndView tSmsSend(HttpServletRequest request) {
		request.setAttribute("sendInfoId", request.getParameter("sendInfoId"));
		return new ModelAndView("com/buss/sms/tSmsSendList");
	}

	/**
	 * easyui AJAX请求数据 获取全部的图片
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
//	@RequestMapping(params = "datagrid")
//	public void datagrid(TSmsPicInfoEntity tSmsPicClass,
//			HttpServletRequest request, HttpServletResponse response,
//			DataGrid dataGrid) {
//		String retailerId = ResourceUtil.getRetailerId();// 零售商ID
//		if(retailerId==null){
//			retailerId = "platform";//平台
//		}
//		CriteriaQuery cq = new CriteriaQuery(TSmsPicInfoEntity.class, dataGrid);
//		cq.eq("retailerId", retailerId);
//		// 查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
//				tSmsPicClass, request.getParameterMap());
//		try {
//			// 自定义追加查询条件
//		} catch (Exception e) {
//			throw new BusinessException(e.getMessage());
//		}
//		cq.add();
//		this.tSmsPicInfoService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
//	}

	/**自定义查询列表
	 * @param request
	 * @param response
	 * @param grid
	 * @return
	 */
	@RequestMapping(params = "mydatagrid")
	@ResponseBody
	public DataGridJson mydatagrid(HttpServletRequest request, HttpServletResponse response,DataGridJson grid) {
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();//零售商id
			String isPlatform = request.getParameter("isPlatform");//1表示是平台的图片
			if(retailerId==null||"1".equals(isPlatform)){
				retailerId = "platform";//平台
			}
			String classId = request.getParameter("classId");//图片分类id
			String fields = "id,name,pic_url";
			StringBuffer sqlCount = new StringBuffer("select count(1) from t_sms_pic_info where status = 'A' ");
			sqlCount.append(" and retailer_id = '").append(retailerId).append("'");
			if(Utility.isNotEmpty(classId) && !"0".equals(classId)){//不是全部分类
				sqlCount.append(" and class_id = '").append(classId).append("'");
			}
			sqlCount.append(" order by create_Date desc");
			Long total = systemService.getCountForJdbc(sqlCount.toString());
			List list = systemService.findForJdbc(sqlCount.toString().replace("count(1)", fields), grid.getPage(), grid.getRows());
			DataGridJson.setDataGrid(grid,list,total.intValue());
		}catch (Exception e) {
			e.printStackTrace();
			grid.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		return grid;
	}
	
	/**
	 * 获取零售商的图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getPicByClassId", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson getPicByClassId(HttpServletRequest request,
			HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();// 零售商ID
		String classId = request.getParameter("classId");
		CriteriaQuery cq = new CriteriaQuery(TSmsPicInfoEntity.class);
		cq.eq("retailerId", retailerId);
		cq.addOrder("updateDate", SortDirection.desc);
		
		if(classId == "0" || "0".equals(classId) ){
		}else{
			cq.eq("classId", classId);
		}
		cq.add();
		List<TSmsPicInfoEntity> picInfos = this.tSmsPicInfoService.getListByCriteriaQuery(cq, false);

		List<TSmsPicInfoEntity> picInfo = new ArrayList<TSmsPicInfoEntity>();

		int totalPage = picInfos.size() % 10 == 0 ? (picInfos.size() / 10)
				: (picInfos.size() / 10 + 1);
		totalPage = totalPage == 0 ? 1 : totalPage;
		
		if (picInfos.size() > 10) {
			picInfo = picInfos.subList(0, 10);
		} else {
			picInfo = picInfos;
		}
		AjaxJson j = new AjaxJson();
		j.setMsg(totalPage + ""); // 总页数
		j.setObj(picInfo);
		j.setSuccess(true);
		return j;
	}

	/**
	 * 根据分类获取平台的图片(分页)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getPlatformPicByClassId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getPlatformPicByClassId(HttpServletRequest request,	HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		int pageSize = 10;//每页条数10条
		int status = 500;//错误
		try {
			String classId = request.getParameter("classId");
			String page = request.getAttribute("page").toString();
			String totalSql = "select count(1) from t_sms_pic_info where retailer_id = 'platform' and status = 'A' and class_id = '"+classId+"'";
			int totalCount = this.tSmsPicInfoService.getCountForJdbc(totalSql).intValue();
			int totalPage = totalCount % 10 == 0 ? (totalCount / pageSize): (totalCount/ pageSize + 1);
			List<TSmsPicInfoEntity> picList = this.tSmsPicInfoService.getPicByClassAndPage("platform", classId,Integer.parseInt(page), pageSize);
			map.put("totalPage", totalPage);//总页数
			map.put("picList", picList);//图片list
			status = 200;//成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", status);
		return map;
	}
	
	/**
	 * 分页获取零售商的图片
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getPicByClassAndPage", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson getPicByClassAndPage(HttpServletRequest request,
			HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();// 零售商ID
		String classId = request.getAttribute("classId").toString();
		String page = request.getAttribute("page").toString();
//		CriteriaQuery cq = new CriteriaQuery(TSmsPicClassEntity.class);
//		cq.eq("retailerId", retailerId);
//		cq.eq("classId", classId);
//		cq.add();
		List<TSmsPicInfoEntity> picInfos = this.tSmsPicInfoService
				.getPicByClassAndPage(retailerId, classId,
						Integer.parseInt(page), 10);

		AjaxJson j = new AjaxJson();
		j.setMsg("获取图片成功");
		j.setObj(picInfos);
		j.setSuccess(true);

		return j;
	}

	/**
	 * 添加零售商图片   --  上传到 七牛
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadClassPic", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public AjaxJson uploadClassPic(HttpServletResponse response,HttpServletRequest request) throws Exception {
		String retailerId = ResourceUtil.getRetailerId();
		if(retailerId==null){
			retailerId = "platform";//平台
		}
		String classId = request.getParameter("classId");
		String imgPath = request.getParameter("imgPath");
		AjaxJson j = new AjaxJson();
        
		//如果用户上传时点中的是全部图片 就将图片放到 未分组 中
		if(classId == "0" || "0".equals(classId)){  
			List<TSmsPicClassEntity> tSmsPicClassList = this.tSmsPicClassService.getClassByNameAndRetailerId("未分组", retailerId);
			TSmsPicClassEntity tSmsPicClassEntity = new TSmsPicClassEntity();
			if(tSmsPicClassList == null || tSmsPicClassList.size() == 0){
				tSmsPicClassEntity.setId(Utility.getUUID());
				tSmsPicClassEntity.setStatus("A");
				tSmsPicClassEntity.setCreateDate(new Date());
				tSmsPicClassEntity.setCreateDate(tSmsPicClassEntity.getCreateDate());
				tSmsPicClassEntity.setCreateBy(retailerId);
				tSmsPicClassEntity.setCount(0);
				tSmsPicClassEntity.setRetailerId(retailerId);
				tSmsPicClassEntity.setName("未分组");
				this.tSmsPicClassService.save(tSmsPicClassEntity);
				classId = tSmsPicClassEntity.getId();
			}else{
				classId = tSmsPicClassList.get(0).getId();
			}
		}

		/*FtpUtil ftpUtil = FtpUtil.getInstance();
		MultipartFile mf = uploadFile;// 获取上传文件对象
		String fileName = mf.getOriginalFilename();// 获取文件名
		String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
		String myfilename = "";
		String noextfilename = "";// 不带扩展名
		noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)
				+ StringUtil.random(8);// 自定义文件名称
		myfilename = noextfilename + "." + extend;// 自定义文件名称
		InputStream is = mf.getInputStream();
		ftpUtil.uploadFileToFtpServer(GlobalConstants.SMS_POSTER_PIC,
				myfilename, is);
		String imgPath = ResourceUtil.getConfigByName("imgWWW") + "/"
				+ ResourceUtil.getConfigByName("imgRootPath") + "/"
				+ GlobalConstants.SMS_POSTER_PIC + "/" + myfilename;*/
		
		String myfilename = imgPath.substring(imgPath.lastIndexOf("/")+1, imgPath.length());
		//封装图片实体
		TSmsPicInfoEntity tSmsPicInfoEntity = new TSmsPicInfoEntity();
		tSmsPicInfoEntity.setId(Utility.getUUID());
		tSmsPicInfoEntity.setPicUrl(imgPath);
		tSmsPicInfoEntity.setRetailerId(retailerId);
		tSmsPicInfoEntity.setClassId(classId);
		tSmsPicInfoEntity.setName(myfilename);
		tSmsPicInfoEntity.setCreateDate(new Date());
		tSmsPicInfoEntity.setUpdateDate(tSmsPicInfoEntity.getCreateDate());
		tSmsPicInfoEntity.setStatus("A");

		//将文件保存到数据库
		this.tSmsPicInfoService.save(tSmsPicInfoEntity);
		
		CriteriaQuery cq = new CriteriaQuery(TSmsPicInfoEntity.class);
		cq.eq("retailerId", retailerId);
		
		cq.eq("classId", classId);
		cq.add();
		List<TSmsPicInfoEntity> picInfos = this.tSmsPicInfoService.getListByCriteriaQuery(cq, false);
		
		TSmsPicClassEntity tSmsPicClassEntity = this.tSmsPicClassService.get( TSmsPicClassEntity.class, classId);
		tSmsPicClassEntity.setCount(picInfos.size());
		tSmsPicClassEntity.setUpdateDate(new Date());
		//更新图片分类的图片数量
		this.tSmsPicClassService.doUpdateSql(tSmsPicClassEntity);
		
		
		/*CriteriaQuery cq1 = new CriteriaQuery(TSmsPicClassEntity.class);
		cq1.eq("retailerId", retailerId);
		cq1.eq("status", "A");
		cq1.add();
		List<TSmsPicClassEntity> picClass = this.tSmsPicClassService.getListByCriteriaQuery(cq1, false);*/
		
		j.setObj(tSmsPicInfoEntity);
		j.setMsg("上传成功！");
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 获取平台的分类图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getPlatformPicsByClassId", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson getPlatformPicsByClassId(HttpServletRequest request,
			HttpServletResponse response) {
		String classId = request.getParameter("classId");
		CriteriaQuery cq = new CriteriaQuery(TSmsPicInfoEntity.class);
		cq.eq("retailerId", "platform");//平台
		cq.addOrder("updateDate", SortDirection.desc);
		
		if(classId == "0" || "0".equals(classId) ){
		}else{
			cq.eq("classId", classId);
		}
		cq.add();
		List<TSmsPicInfoEntity> picInfos = this.tSmsPicInfoService.getListByCriteriaQuery(cq, false);

		List<TSmsPicInfoEntity> picInfo = new ArrayList<TSmsPicInfoEntity>();

		int totalPage = picInfos.size() % 10 == 0 ? (picInfos.size() / 10)
				: (picInfos.size() / 10 + 1);
		totalPage = totalPage == 0 ? 1 : totalPage;
		
		if (picInfos.size() > 10) {
			picInfo = picInfos.subList(0, 10);
		} else {
			picInfo = picInfos;
		}
		AjaxJson j = new AjaxJson();
		j.setMsg(totalPage + ""); // 总页数
		j.setObj(picInfo);
		j.setSuccess(true);

		return j;
	}
	
	/**
	 * 批量删除图片
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "图片删除成功";
		try{
			this.tSmsPicInfoService.doBatchDel(ids);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			message = "图片删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

}
