package com.buss.base.controller;
import com.buss.base.entity.BaseBrandEntity;
import com.buss.base.entity.TBrandShowEntity;
import com.buss.base.service.TBrandShowServiceI;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.ResourceUtil;
import java.util.Map;

/**   
 * @Title: Controller
 * @Description: 零售商品牌展示
 * @author onlineGenerator
 * @date 2016-06-17 14:55:16
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBrandShowController")
public class TBrandShowController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBrandShowController.class);

	@Autowired
	private TBrandShowServiceI tBrandShowService;
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
	 * 平台设置 零售商品牌展示列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("retailer_id", request.getParameter("retailer_id"));
		return new ModelAndView("com/buss/base/tBrandShowList");
	}
	/**
	 * 零售商可见品牌列表
	 * @return
	 */
	@RequestMapping(params = "tBrandShowListOfRetailer")
	public ModelAndView tBrandShowListOfRetailer(HttpServletRequest request) {
		return new ModelAndView("com/buss/base/tBrandShowListOfRetailer");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBrandShowEntity tBrandShow,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		CriteriaQuery cq = new CriteriaQuery(TBrandShowEntity.class, dataGrid);
		//查询条件组装器
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBrandShow, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = request.getParameter("retailer_id");
			String isSelfBrand = request.getParameter("is_Self_Brand");
			if(StringUtil.isEmpty(retailerId)){
				retailerId = ResourceUtil.getRetailerId();
			}
			StringBuffer sql = new StringBuffer("select s.id,s.retailer_id as retailerId,s.brand_id as brandId,s.is_self_brand as isSelfBrand,b.brand_name as brandName,IFNULL(s.free_amount,'') as freeAmount,IFNULL(s.fare,'') as fare,IFNULL(s.order_num,'') as orderNum,")
				.append("b.brand_code as brandCode,b.brand_pic as brandPic from t_brand_show s left join base_brand b on s.brand_id = b.id  where s.status = 'A' and s.retailer_id ='").append(retailerId).append("'");
			StringBuffer countSql = new StringBuffer("select count(1) from t_brand_show s left join base_brand b on s.brand_id = b.id where s.status = 'A' and s.retailer_id ='").append(retailerId).append("'");
			String brandName = request.getParameter("brandName");
			if(!Utility.isEmpty(brandName)){
				sql.append(" and b.brand_name like '%").append(brandName).append("%'");
				countSql.append(" and b.brand_name like '%").append(brandName).append("%'");
			}
			if(!Utility.isEmpty(isSelfBrand)){
				sql.append(" and s.is_self_brand = '").append(isSelfBrand).append("'");
				countSql.append(" and s.is_self_brand = '").append(isSelfBrand).append("'");
			}
			String sort = dataGrid.getSort();
			if(StringUtil.isEmpty(sort)){
					sql.append(" order by orderNum asc");
			}else{
				sql.append(" order by ").append(dataGrid.getSort()).append(" ").append(dataGrid.getOrder());
			}
			List<Map<String, Object>> resultList =  systemService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
			int total = 0;
			if(!Utility.isEmpty(resultList)){
				total = this.systemService.getCountForJdbc(countSql.toString()).intValue();
			}else{
				resultList = new ArrayList<Map<String,Object>>();
			}
			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 零售商 选择品牌跳转页面
	 * @return
	 */
	@RequestMapping(params = "findBrandList")
	public ModelAndView findBrandList(HttpServletRequest request) {
		String rId = request.getParameter("rId");
		if(Utility.isEmpty(rId)){
			rId = ResourceUtil.getRetailerId(); 
		}
		request.setAttribute("retailer_id",rId);
		request.setAttribute("is_Self_Brand", request.getParameter("isSelfBrand"));
		return new ModelAndView("com/buss/goods/brandList");
	}
	
	/**
	 * 后台 选择零售商可见品牌跳转页面
	 * @return
	 */
	@RequestMapping(params = "findSelfBrandList")
	public ModelAndView findSelfBrandList(HttpServletRequest request) {
		request.setAttribute("retailer_id", request.getParameter("storeId"));
		request.setAttribute("is_Self_Brand", request.getParameter("isSelfBrand"));
		return new ModelAndView("com/buss/goods/brandList");
	}
	
	/**
	 * 删除零售商品牌展示
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBrandShowEntity tBrandShow, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBrandShow = systemService.flushEntity(TBrandShowEntity.class, tBrandShow.getId());
		message = "品牌删除成功";
		try{
			tBrandShow.setStatus(common.GlobalConstants.STATUS_INACTIVE);
			tBrandShow.setUpdateDate(Utility.getCurrentTimestamp());
			tBrandShowService.updateEntitie(tBrandShow);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "品牌删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除零售商品牌展示
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "品牌删除成功";
		try{
			for(String id:ids.split(",")){
				TBrandShowEntity tBrandShow = systemService.flushEntity(TBrandShowEntity.class, id	);
				tBrandShow.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				tBrandShow.setUpdateDate(Utility.getCurrentTimestamp());
				tBrandShowService.updateEntitie(tBrandShow);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "品牌删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加零售商品牌展示
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBrandShowEntity tBrandShow, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌添加成功";
		try{
			String brandIds = request.getParameter("brandIds");
			if(!Utility.isEmpty(brandIds)){
				List<TBrandShowEntity> entitys = new ArrayList<TBrandShowEntity>();
				String[] arr = brandIds.split(",");
				for(String id : arr){
					List<TBrandShowEntity> existBrand = this.systemService.findByQueryString("from TBrandShowEntity where  retailerId='"
							+tBrandShow.getRetailerId()+"' and brandId = '"+id+"'");
					if(existBrand.size()==0){
//						BaseBrandEntity brand = this.systemService.get(BaseBrandEntity.class, id);
						TBrandShowEntity entity = new TBrandShowEntity();
						entity.setBrandId(id);
//						entity.setBrandCode(brand.getBrandCode());
//						entity.setBrandName(brand.getBrandName());
//						entity.setBrandPic(brand.getBrandPic());
						entity.setFare(new BigDecimal(12));
						entity.setRetailerId(tBrandShow.getRetailerId());
						entity.setStatus(common.GlobalConstants.STATUS_ACTIVE);
						entitys.add(entity);
					}
				}
				if(!Utility.isEmpty(entitys)){
					tBrandShowService.batchSave(entitys);
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "品牌添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新零售商品牌展示
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBrandShowEntity tBrandShow, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌信息更新成功";
		try {
			tBrandShowService.updateBrandInfo(tBrandShow);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "品牌信息更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 零售商品牌展示新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBrandShowEntity tBrandShow, HttpServletRequest req) {
		req.setAttribute("retailerId", req.getParameter("retailerId"));
		return new ModelAndView("com/buss/base/tBrandShow-add");
	}
	
	/**
	 * 零售商品牌展示编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBrandShowEntity tBrandShow, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBrandShow.getId())) {
			tBrandShow = tBrandShowService.flushEntity(TBrandShowEntity.class, tBrandShow.getId());
			BaseBrandEntity baseBrand = this.systemService.get(BaseBrandEntity.class, tBrandShow.getBrandId());
			req.setAttribute("baseBrand", baseBrand);
			req.setAttribute("tBrandShowPage", tBrandShow);
		}
		return new ModelAndView("com/buss/base/tBaseBrandPics-update");
	}
	
	/**
	 * 零售商品牌展示排序页面跳转
	 * @return
	 */
	@RequestMapping(params = "goSort")
	public ModelAndView goSort(TBrandShowEntity tBrandShow, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBrandShow.getId())) {
			tBrandShow = tBrandShowService.flushEntity(TBrandShowEntity.class, tBrandShow.getId());
			BaseBrandEntity baseBrand = this.systemService.get(BaseBrandEntity.class, tBrandShow.getBrandId());
			req.setAttribute("baseBrand", baseBrand);
			req.setAttribute("tBrandShowPage", tBrandShow);
		}
		return new ModelAndView("com/buss/base/tBaseBrandPics-sort");
	}
	
	/**
	 * 零售商自有品牌设置
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doSelfBrand")
	@ResponseBody
	public AjaxJson doSelfBrand(TBrandShowEntity tBrandShow, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌设置成功";
		try {
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_brand_show set update_by = '").append(user.getUserName()).append("',update_name = '")
			.append(user.getRealName()).append("',update_date ='").append(Utility.getCurrentTimestamp())
			.append("',is_self_brand = '").append(type).append("' where id = '").append(id).append("'");
			tBrandShowService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "品牌设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
}
