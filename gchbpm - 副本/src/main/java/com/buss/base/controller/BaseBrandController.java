package com.buss.base.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.FtpUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.CategoryServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.redis.service.RedisService;

import com.buss.base.datahandler.BaseBrandDataHandler;
import com.buss.base.entity.BaseBrandEntity;
import com.buss.base.entity.TBrandCategoryEntity;
import com.buss.base.entity.TBrandDescEntity;
import com.buss.base.entity.TBrandPicsEntity;
import com.buss.base.service.BaseBrandServiceI;
import com.buss.base.service.TBrandCategoryServiceI;
import com.buss.bill.entity.TCommissionCloudEntity;
import com.buss.goods.entity.TGoodsEntity;
import com.buss.news.entity.TNewsGoodsEntity;
import com.buss.user.entity.TPersonTagsEntity;
import common.GlobalConstants;

/**
 * @Title: Controller
 * @Description: 品牌
 * @author onlineGenerator
 * @date 2016-03-08 18:27:43
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/baseBrandController")
public class BaseBrandController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BaseBrandController.class);

	@Autowired
	private BaseBrandServiceI baseBrandService;
	@Autowired
	private TBrandCategoryServiceI tBrandCategoryService;
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
	private RedisService redisService;
	@Autowired
	private CategoryServiceI categoryService;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 品牌列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		//商品录入页面，查询品牌列表页面，不可编辑
		String isForChoose = request.getParameter("isForChoose");
		if("1".equals(isForChoose)){
			String retailerId = request.getParameter("retailerId");//平台录入导购活动选品牌的时候会传入零售商id
			request.setAttribute("retailerId", retailerId);
			return new ModelAndView("com/buss/base/baseBrandListForChoose");
		}else{//可编辑的查询列表页面
			request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		}
		return new ModelAndView("com/buss/base/baseBrandList");
	}

	/**
	 * 品牌类型列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "typeList")
	public ModelAndView typeList(HttpServletRequest request) {
		request.setAttribute("brand_Id", request.getParameter("brand_Id"));
		request.setAttribute("userType", ResourceUtil.getSessionUserName().getUserType());
		return new ModelAndView("com/buss/base/baseBrandTypeList");
	}
	
	/**
	 * 品牌列表 页面跳转 优惠券选品牌页面
	 * @return
	 */
	@RequestMapping(params = "ticketBrandList")
	public ModelAndView ticketBrandList(HttpServletRequest request) {
		request.setAttribute("ticketId", request.getParameter("ticketId"));
		request.setAttribute("rId", request.getParameter("rId"));//零售商ID
		return new ModelAndView("com/buss/ticket/ticketBrandList");
	}
	
	/**
	 * easyui AJAX请求数据 获取品牌的类型
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "getTypeList")
	public void getTypeList(TBrandCategoryEntity brandCategory, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		try {
			// 自定义追加查询条件
			String brandId = request.getParameter("brandId");
			String sql = "SELECT tbc.id, brand_Id as brandId, tsc1.name as topCategoryId, tsc2.name as subCategoryId FROM "+
			"t_brand_category tbc left join t_s_category tsc1 on tbc.top_Category_Id = tsc1.id " +
			" left join t_s_category tsc2 on tbc.sub_Category_Id = tsc2.id  where brand_Id ='"+brandId +"' and tbc.status = 'A'";
			String countSql = "select count(1) from t_brand_category tbc where tbc.brand_Id ='"+brandId +"' and tbc.status = 'A'";
			List<Map<String, Object>> resultList = systemService.findForJdbc( sql, dataGrid.getPage(), dataGrid.getRows());
			int total = 0;
			if (Utility.isEmpty(resultList)) {
				resultList = new ArrayList<Map<String, Object>>();
			}else{
				total = this.systemService.getCountForJdbc(countSql).intValue();
			}

			dataGrid.setResults(resultList);
			dataGrid.setTotal(total);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		TagUtil.datagrid(response, dataGrid);
		
		
/*		CriteriaQuery cq = new CriteriaQuery(TCommissionCloudEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tNewsGoods, request.getParameterMap());
		try{
		//自定义追加查询条件
			String brandId = request.getParameter("brandId");
			cq.eq("brandId", brandId);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBrandCategoryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);*/
	}

	/**
	 * easyui AJAX请求数据 获取品牌的类型
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "delType")
	@ResponseBody
	public AjaxJson delType(TBrandCategoryEntity brandCategory, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		
			String id = request.getParameter("id");
			AjaxJson j = new AjaxJson();
			try{
				brandCategory = systemService.flushEntity(TBrandCategoryEntity.class, id);
				brandCategory.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				brandCategory.setUpdateDate(DateUtils.gettimestamp());
				tBrandCategoryService.saveOrUpdate(brandCategory);
				message = "品牌类目删除成功";
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "品牌类目删除失败";
				throw new BusinessException(e.getMessage());
			}
			categoryService.clearCategoryRedis();
			j.setMsg(message);
			return j;
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
	public void datagrid(BaseBrandEntity baseBrand, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BaseBrandEntity.class, dataGrid);
		try {
			// 自定义追加查询条件
//			String retailer_Id = request.getParameter("retailer_Id");//录入商品选择品牌列表
//			if (StringUtil.isNotEmpty(retailer_Id)) {
//				String sqlstr = " NOT EXISTS ( select 1 from t_brand_show where  retailer_id = '"
//						+ retailer_Id
//						+ "' and status = 'A' and brand_id = this_.id)";
//				org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil
//						.installHql(cq, baseBrand, request.getParameterMap(),
//								sqlstr);
//			}
			String retailerId = ResourceUtil.getRetailerId();
			if(StringUtil.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baseBrand, request.getParameterMap());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.baseBrandService.getDataGridReturn(cq, true);
		List<BaseBrandEntity> list = dataGrid.getResults();
		if(Utility.isNotEmpty(list)){
			for(BaseBrandEntity entity : list){
				entity.setBrandPic(entity.getBrandPic()+"?imageView2/1/w/80/h/80");
				entity.setBigPic(entity.getBigPic()+"?imageView2/1/w/130/h/80");
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui 优惠券查询品牌列表（过滤已经选过的品牌）
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagridOfTicket")
	public void datagridOfTicket(BaseBrandEntity baseBrand, HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BaseBrandEntity.class, dataGrid);
		Map<String, String[]> map = request.getParameterMap();
		try {
			String retailerId = request.getParameter("rId");
			String ticketId = request.getParameter("ticketId");//优惠券ID
			String condtions =" NOT EXISTS ( select	1	from t_ticket_goods where  status = 'A' and type='2' and goods_id = this_.id and ticket_Id = '"+ticketId+"' )";
			if(StringUtil.isNotEmpty(retailerId)){
				cq.eq("retailerId", retailerId);
			}
			cq.add();
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, baseBrand, map,condtions.toString());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		this.baseBrandService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除品牌
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BaseBrandEntity baseBrand, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		baseBrand = systemService.flushEntity(BaseBrandEntity.class, baseBrand.getId());
		baseBrand.setStatus(common.GlobalConstants.STATUS_INACTIVE);
		// TSUser user = ResourceUtil.getSessionUserName();
		// baseBrand.setUpdateBy(user.getUserName());
		baseBrand.setUpdateDate(DateUtils.gettimestamp());
		message = "品牌删除成功";
		try {
			baseBrandService.saveOrUpdate(baseBrand);
			// baseBrandService.delete(baseBrand);
			systemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "品牌删除失败";
			throw new BusinessException(e.getMessage());
		}
		this.clearBrandReids();
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除品牌
	 * 
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌删除成功";
		try {
			for (String id : ids.split(",")) {
				BaseBrandEntity baseBrand = systemService.flushEntity(
						BaseBrandEntity.class, id);
				baseBrand.setStatus(common.GlobalConstants.STATUS_INACTIVE);
				baseBrand.setUpdateDate(DateUtils.gettimestamp());
				baseBrandService.saveOrUpdate(baseBrand);
				// baseBrandService.delete(baseBrand);
				systemService.addLog(message, Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "品牌删除失败";
			throw new BusinessException(e.getMessage());
		}
		this.clearBrandReids();
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加品牌
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BaseBrandEntity baseBrand, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌添加成功";
		try {
			baseBrandService.saveBrand(baseBrand);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "品牌添加失败";
			throw new BusinessException(e.getMessage());
		}
		this.clearBrandReids();
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 清除品牌缓存 key:getAllBaseBrand* 零售商新增，删除，修改品牌
	 */
	private void clearBrandReids() {
		try {
			redisService.batchDel("getAllBaseBrand*"+ResourceUtil.getRetailerId()+"*");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 添加品牌类目
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddType")
	@ResponseBody
	public AjaxJson doAddType(TBrandCategoryEntity brandCategory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌类目添加成功";
		try {
			String sql = "select count(1) from t_brand_category where status='A' and brand_id='"+brandCategory.getBrandId()
			+"' and top_category_id='"+brandCategory.getTopCategoryId()+"' and sub_category_id='"+brandCategory.getSubCategoryId()+"'";
			int total = this.systemService.getCountForJdbc(sql).intValue();
			if(total > 0){
				message = "品牌类目已存在";
			}else{
				tBrandCategoryService.save(brandCategory);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "品牌类目添加失败";
			throw new BusinessException(e.getMessage());
		}
		categoryService.clearCategoryRedis();
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新品牌
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BaseBrandEntity baseBrand,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "品牌更新成功";
		BaseBrandEntity t = baseBrandService.get(BaseBrandEntity.class,
				baseBrand.getId());
		// TSUser user = ResourceUtil.getSessionUserName();
		// baseBrand.setUpdateBy(user.getUserName());
		baseBrand.setUpdateDate(DateUtils.gettimestamp());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(baseBrand, t);
			baseBrandService.updateBrand(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "品牌更新失败";
			throw new BusinessException(e.getMessage());
		}
		this.clearBrandReids();
		j.setMsg(message);
		return j;
	}

	/**
	 * 品牌新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(HttpServletRequest req) {
		String retailerId = ResourceUtil.getRetailerId();
		req.setAttribute("retailerId", retailerId);
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/base/baseBrand-add");
	}
	
	/**
	 * 品牌类目新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAddPage")
	public ModelAndView goAddPage(HttpServletRequest req) {
		String brandId = req.getParameter("brandId");
		req.setAttribute("brandId", brandId);
		return new ModelAndView("com/buss/base/tBrandCategory-add");
	}

	/**
	 * 品牌编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BaseBrandEntity baseBrand,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(baseBrand.getId())) {
			baseBrand = baseBrandService.flushEntity(BaseBrandEntity.class,baseBrand.getId());
			TBrandDescEntity brandDesc = this.baseBrandService
					.findUniqueByProperty(TBrandDescEntity.class, "brandId",baseBrand.getId());
			req.setAttribute("baseBrandPage", baseBrand);
			req.setAttribute("brandDesc", brandDesc);
			req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
			String retailerCode = ResourceUtil.getRetailerCode(systemService);
			req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		}
		return new ModelAndView("com/buss/base/baseBrand-update");
	}

	/**
	 * 品牌查看页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goReview")
	public ModelAndView goReview(BaseBrandEntity baseBrand,
			HttpServletRequest req) {
		if (StringUtil.isNotEmpty(baseBrand.getId())) {
			baseBrand = baseBrandService.flushEntity(BaseBrandEntity.class,
					baseBrand.getId());
			TBrandDescEntity brandDesc = this.baseBrandService
					.findUniqueByProperty(TBrandDescEntity.class, "brandId",
							baseBrand.getId());
			req.setAttribute("baseBrandPage", baseBrand);
			req.setAttribute("brandDesc", brandDesc);
		}
		return new ModelAndView("com/buss/base/baseBrand-review");
	}

	/**
	 * 云商选择品牌跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "findBrandList")
	public ModelAndView findBrandList(HttpServletRequest request) {
		return new ModelAndView("com/buss/goods/brandList2");
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "baseBrandController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 上传证书
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadPic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadPic(TPersonTagsEntity tPersonTags,
			HttpServletRequest request) throws Exception {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FtpUtil ftpUtil = FtpUtil.getInstance();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();// 获取上传文件对象
			String fileName = mf.getOriginalFilename();// 获取文件名
			String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
			String myfilename = "";
			String noextfilename = "";// 不带扩展名
			noextfilename = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)
					+ StringUtil.random(8);// 自定义文件名称
			myfilename = noextfilename + "." + extend;// 自定义文件名称
			InputStream is = mf.getInputStream();
			ftpUtil.uploadFileToFtpServer(GlobalConstants.BASE_BRAND,
					myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW") + "/"
					+ ResourceUtil.getConfigByName("imgRootPath") + "/"
					+ GlobalConstants.BASE_BRAND + "/" + myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BaseBrandEntity baseBrand,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BaseBrandEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				baseBrand, request.getParameterMap());
		cq.eq(common.GlobalConstants.FIELD_STATUS,
				common.GlobalConstants.STATUS_ACTIVE);// 查询状态为A的数据
		List<BaseBrandEntity> baseBrands = this.baseBrandService
				.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "品牌");
		modelMap.put(NormalExcelConstants.CLASS, BaseBrandEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("品牌列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, baseBrands);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BaseBrandEntity baseBrand,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME, "品牌");
		modelMap.put(NormalExcelConstants.CLASS, BaseBrandEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("品牌列表",
				"导入人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导入信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxJson j = new AjaxJson();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			BaseBrandDataHandler handler = new BaseBrandDataHandler();
			handler.setNeedHandlerFields(new String[] { "排序", "品牌编码" });
			params.setDataHanlder(handler);

			try {
				List<BaseBrandEntity> listBaseBrandEntitys = ExcelImportUtil
						.importExcel(file.getInputStream(),
								BaseBrandEntity.class, params);
				if (!Utility.isEmpty(listBaseBrandEntitys)) {
					HashSet<String> set = new HashSet<String>();// 导入的excel中的手机号集合
					String msg = checkImportCode(listBaseBrandEntitys, set);
					// 导入excel列表中有重复的号码
					if (!StringUtil.isEmpty(msg)) {
						j.setMsg("导入的excel列表中编码：" + msg + " 出现重复");
						// j.setSuccess(false);
					} else {
						List<Object> list = null;
						String sql = "select brand_code from base_brand where  status = 'A' ";// 所有的编码
						list = this.systemService.findListbySql(sql);
						msg = checkDatabaseCode(list, set);
						// 数据库中已经存在该编码
						if (!StringUtil.isEmpty(msg)) {
							j.setMsg("数据库中已经存在编码：" + msg);
							// j.setSuccess(false);
						} else {
							baseBrandService.saveBatch(listBaseBrandEntitys);
							j.setMsg("文件导入成功！");
						}
					}
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			} finally {
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 检查导入的编码是否有重复,返回重复的编码，否则返回 null,并把编码存在set中
	 * 
	 * @param listTPersonEntitys
	 * @return
	 */
	private String checkImportCode(List<BaseBrandEntity> listBaseBrandEntitys,
			HashSet<String> set) {
		for (BaseBrandEntity entity : listBaseBrandEntitys) {
			if (set.contains(entity.getBrandCode())) {
				return entity.getBrandCode();
			} else {
				set.add(entity.getBrandCode());
			}
		}
		return null;
	}

	/**
	 * 校验是否和数据库中的编码有重复的，有则返回重复编码，没有则返回null
	 * 
	 * @param list
	 *            数据库手机号
	 * @param set
	 *            导入excel中的手机号
	 * @return
	 */
	private String checkDatabaseCode(List<Object> list, HashSet<String> set) {
		for (Object code : list) {
			if (set.contains(code.toString())) {
				return code.toString();
			}
		}
		return null;
	}
}
