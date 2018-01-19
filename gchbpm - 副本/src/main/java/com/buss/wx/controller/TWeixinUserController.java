package com.buss.wx.controller;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.goods.vo.AllActGoodsVo;
import com.buss.wx.entity.TWeixinUserEntity;
import com.buss.wx.service.TWeixinUserServiceI;
import com.buss.wx.vo.WeixinUserBrowseContentVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.jeecgframework.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 微信用户表
 * @author onlineGenerator
 * @date 2018-01-05 11:39:50
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tWeixinUserController")
public class TWeixinUserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TWeixinUserController.class);

	@Autowired
	private TWeixinUserServiceI tWeixinUserService;
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
	 * 微信用户表列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tWeixinUser")
	public ModelAndView tWeixinUser(HttpServletRequest request) {
		return new ModelAndView("com/buss/wx/tWeixinUserList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TWeixinUserEntity tWeixinUser,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TWeixinUserEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tWeixinUser, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tWeixinUserService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据 查询微信浏览记录的列表
	 * @param weixinUserBrowseContentVo
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridOfListWeixinBrowseContent")
	public void datagridOfListWeixinBrowseContent(WeixinUserBrowseContentVo weixinUserBrowseContentVo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try{
			Map<String,String> sqlMap = this.getListWeixinBrowseContentSqlMap(request,dataGrid);
			String sql = sqlMap.get("sql");
			String sqlCount = sqlMap.get("sqlCount");
			List<WeixinUserBrowseContentVo> resultList = new ArrayList<WeixinUserBrowseContentVo>();
			int total = this.systemService.getCountForJdbc(sqlCount.toString()).intValue();
			if(total>0){
				resultList =  systemService.findObjForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows(),WeixinUserBrowseContentVo.class);
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 获取查询微信浏览记录的sqlMap
	 * @param request
	 * @param dataGrid
	 * @return
	 */
	private Map<String, String> getListWeixinBrowseContentSqlMap(HttpServletRequest request, DataGrid dataGrid) {
		Map<String, String> map = new HashMap<String, String>();
		String retailerId = ResourceUtil.getRetailerId();
		String nikeName = request.getParameter("nikeName") ; // 用户昵称
		String type  = request.getParameter("type")  ; // 类型（1:商品、2:活动、3:话题、4:券）
		String province = request.getParameter("province") ; // 省份
		String city = request.getParameter("city") ; // 城市
		StringBuffer sql = new StringBuffer("SELECT b.retailer_id retailerId, b.guide_id guideId, b.content_id contentId, b.content, u.nickname, u.country, u.province, u.city, u.headimgurl, ")
				.append("(CASE u.sex WHEN '1' THEN '男' ELSE '女' END) sex,")
				.append("(CASE b.type WHEN '1' THEN '商品' WHEN '2' THEN '活动' WHEN '3' THEN '话题' WHEN '4' THEN '券' END ) type")
				.append(" FROM t_weixin_user_browse b LEFT JOIN t_weixin_user u ON b.wx_user_id = u.id ")
		.append("WHERE b.retailer_id = '").append(retailerId).append("'")
		;
		StringBuffer sqlCount = new StringBuffer("select count(1) FROM t_weixin_user_browse b LEFT JOIN t_weixin_user u ON b.wx_user_id = u.id WHERE b.retailer_id = '").append(retailerId).append("'");
		if (Utility.isNotEmpty(nikeName)) {
			sql.append(" and u.nikeName like '%").append(nikeName).append("%'");
			sqlCount.append(" and u.nikeName like '%").append(nikeName).append("%'");
		}
		if (Utility.isNotEmpty(type)) {
			sql.append(" and b.type like '%").append(type).append("%'");
			sqlCount.append(" and b.type like '%").append(type).append("%'");
		}
		if (Utility.isNotEmpty(province)) {
			sql.append(" and u.province like '%").append(province).append("%'");
			sqlCount.append(" and u.province like '%").append(province).append("%'");
		}
		if (Utility.isNotEmpty(city)) {
			sql.append(" and u.city like '%").append(city).append("%'");
			sqlCount.append(" and u.city like '%").append(city).append("%'");
		}
		/*if (Utility.isNotEmpty(beginTime)) {
			sql.append(" and a.begin_time >= '").append(beginTime).append(" 00:00:00'");
			sqlCount.append(" and a.begin_time >= '").append(beginTime).append(" 00:00:00'");
		}
		if (Utility.isNotEmpty(endTime)) {
			sql.append(" and a.end_time <= '").append(endTime).append(" 23:59:59'");
			sqlCount.append(" and a.end_time <= '").append(endTime).append(" 23:59:59'");
		}*/
		
		String sortName = dataGrid.getSort();
		if (Utility.isEmpty(sortName)) {
			sql.append(" ORDER BY b.create_date desc");
		} else {
			sql.append(" ORDER BY ").append(sortName).append(" ").append(dataGrid.getOrder());
		}
		map.put("sql", sql.toString());
		map.put("sqlCount", sqlCount.toString());
		return map;
	}

	/**
	 * 微信用户表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TWeixinUserEntity tWeixinUser, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tWeixinUser.getId())) {
			tWeixinUser = tWeixinUserService.flushEntity(TWeixinUserEntity.class, tWeixinUser.getId());
			req.setAttribute("tWeixinUserPage", tWeixinUser);
		}
		return new ModelAndView("com/buss/wx/tWeixinUser-update");
	}
	

	/**
	 * 微信用户浏览记录页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params="listWeixinBrowseContent")
	public ModelAndView listWeixinBrowseContent(HttpServletRequest request) { 
		
		return new ModelAndView("com/buss/wx/tWeixinBrowseContent") ;
	}
	
}
