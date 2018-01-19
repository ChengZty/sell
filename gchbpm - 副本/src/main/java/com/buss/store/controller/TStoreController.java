package com.buss.store.controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.buss.goods.service.TGoodsServiceI;
import com.buss.store.entity.TStoreEntity;
import com.buss.store.entity.TStorePicsEntity;
import com.buss.store.service.TStoreServiceI;
import com.buss.store.vo.StoreImportVo;

import common.GlobalConstants;



/**   
 * @Title: Controller
 * @Description: 实体店
 * @author onlineGenerator
 * @date 2016-09-22 20:39:02
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tStoreController")
public class TStoreController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TStoreController.class);

	@Autowired
	private TStoreServiceI tStoreService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TGoodsServiceI tGoodsService;
	private String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 实体店列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tStore")
	public ModelAndView tStore(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("userType", user.getUserType());
		return new ModelAndView("com/buss/store/tStoreList");
	}

	/**
	 * 公司通知按实体店选择导购页面跳转
	 * @return
	 */
	@RequestMapping(params = "storeList")
	public ModelAndView storeList(HttpServletRequest request) {
		return new ModelAndView("com/buss/store/tStoreList2");
	}
	
	/**
	 * 优惠券选择实体店列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tStoreForTicket")
	public ModelAndView tStoreForTicket(HttpServletRequest request) {
		request.setAttribute("ids", request.getParameter("storeIds"));
		return new ModelAndView("com/buss/ticket/tStoreForTicket");
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
	public void datagrid(TStoreEntity tStore,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TStoreEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tStore, request.getParameterMap());
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(retailerId!=null){
				cq.eq("retailerId", retailerId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tStoreService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除实体店
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TStoreEntity tStore, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "实体店删除成功";
		try{
			message = tStoreService.deleteStore(tStore.getId());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "实体店删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除实体店
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "实体店删除成功";
		try{
			for(String id:ids.split(",")){
				message = tStoreService.deleteStore(id);
				/*TStoreEntity tStore = systemService.flushEntity(TStoreEntity.class, id);
				tStoreService.delete(tStore);*/
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "实体店删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加实体店
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TStoreEntity tStore, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "实体店添加成功";
		try{
			tStoreService.saveStore(tStore);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "实体店添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新实体店
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TStoreEntity tStore, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "实体店更新成功";
		TStoreEntity t = tStoreService.get(TStoreEntity.class, tStore.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tStore, t);
//			t.setName(t.getName().replace("\"", "&quot;"));el表达式显示出来还是会变成双引号，还是不能解决显示问题
			tStoreService.updateStore(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "实体店更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 实体店新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TStoreEntity tStore, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tStore.getId())) {
			tStore = tStoreService.flushEntity(TStoreEntity.class, tStore.getId());
			req.setAttribute("tStorePage", tStore);
		}
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/store/tStore-add");
	}
	/**
	 * 实体店编辑页面跳转
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TStoreEntity tStore, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tStore.getId())) {
			tStore = tStoreService.flushEntity(TStoreEntity.class, tStore.getId());
			List<TStorePicsEntity> storePics = this.tStoreService.findByProperty(TStorePicsEntity.class, "storeId", tStore.getId());
			req.setAttribute("tStorePage", tStore);
			req.setAttribute("picNums", storePics.size());
			req.setAttribute("storePics", storePics);
		}
		String load= req.getParameter("load");
		if("detail".equals(load)){
			return new ModelAndView("com/buss/store/tStore-review");
		}
		req.setAttribute("domain", GlobalConstants.DOMAIN);//七牛上传 域名
		String retailerCode = ResourceUtil.getRetailerCode(systemService);
		req.setAttribute("directory", retailerCode+"/");//七牛上传 目录(零售商userCode)
		return new ModelAndView("com/buss/store/tStore-update");
	}
	
	
	/**
	 * 上传场景图片
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "uploadPic", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadPic(HttpServletRequest request) throws Exception {
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
			ftpUtil.uploadFileToFtpServer(GlobalConstants.STORE_PICS, myfilename, is);
			String imgPath = ResourceUtil.getConfigByName("imgWWW")+"/"
							 +ResourceUtil.getConfigByName("imgRootPath")+"/"
							 +GlobalConstants.STORE_PICS+"/"+myfilename;
			j.setMsg(imgPath);
		}
		return j;
	}
	
	/**
	 * 更新实体店
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "checkStoreCode")
	@ResponseBody
	public AjaxJson checkStoreCode( HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "实体店编号已存在";
		j.setObj(false);
		String storeID = request.getParameter("id");
		String storeCode = request.getParameter("storeCode");
		String retailerId = ResourceUtil.getRetailerId();
		try {
			String sql = "select * from t_store where status='A' and store_code='"+storeCode+"' and retailer_id='"+retailerId+"'";
			List<TStoreEntity> t =systemService.findObjForJdbc(sql, TStoreEntity.class);
//			List<TStoreEntity> t = systemService.findByProperty(TStoreEntity.class, "storeCode", storeCode);
			
			if(t.size() < 1 || t.get(0).getId().equals(storeID)){
				j.setObj(true);
				message = "实体店编号不存在";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "实体店编号查询失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
 
/**
 * 下载库存excel模板
 * @param request
 * @param response
 */
@RequestMapping(params = "downloadTemp")
public ModelAndView downloadStockTemp( HttpServletRequest request,	HttpServletResponse response) throws Exception {
	String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
	String fileName = "实体店导入模板.xlsx";
	String filePath = path + "\\" + fileName;
	FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
	return null;
}

/**批量导入实体店
 * @param request
 * @param response
 * @return
 */
@RequestMapping(params = "imporExcel", method = RequestMethod.POST)
@ResponseBody
public AjaxJson imporExcel(HttpServletRequest request, HttpServletResponse response) {
	AjaxJson j = new AjaxJson();
	try{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			FileInputStream is = (FileInputStream) file.getInputStream();
			//导入数据，做非空校验及数字校验
			Long tt1 = System.currentTimeMillis();
			Map<String,Object> map = loadExecl(is);
			Long tt2 = System.currentTimeMillis();
			System.out.println("excel非空校验共耗时====="+(tt2-tt1)+"ms");
			Object errMsg = map.get("errMsg");//错误信息
			Object errKey = map.get("errKey");//错误excel上传七牛的key
			if(Utility.isNotEmpty(errMsg)){
				j.setMsg(errMsg+"");
				if(Utility.isNotEmpty(errKey)){
					j.setObj(errKey);//从七牛下载错误信息excel
					j.setSuccess(false);
				}
			}else if(Utility.isNotEmpty(errKey)){
				j.setObj(errKey);//从七牛下载错误信息excel
				j.setSuccess(false);
			}else{
				//校验并批量保存商品相关信息
				@SuppressWarnings("unchecked")
				List<StoreImportVo> importList = (List<StoreImportVo>) map.get("importList");//导入数据列表
				if(Utility.isEmpty(importList)){
					j.setMsg("导入数据不能为空");
				}else{
					//批量保存实体店
					Map<String,String> resultMap = tStoreService.batchSaveStores(importList);
					String result = resultMap.get("result");
					errKey = resultMap.get("errKey");
					if("OK".equals(result)){//导入成功
						String successCount = resultMap.get("successCount");
						j.setMsg(successCount+"条记录已成功导入");
					}else{
						String errCount = resultMap.get("errCount");
						j.setObj(errKey);//从七牛下载错误信息excel
						j.setMsg(errCount+"条有问题的记录导出中...");
						j.setSuccess(false);
					}
				}
			}
		}
	} catch (Exception e) {
		j.setMsg("文件导入失败，请检查单元格格式和模版格式是否一致！");
		logger.error(ExceptionUtil.getExceptionMessage(e));
	}
	return j;
}

/**获取导入的excel的实体店列表
 * @param is
 * @return List<StoreImportVo> importList 无错误的时候返回数据列表
 * errMsg 超过3000记录的时候会返回该key
 * errKey 导入记录有错误的时候会返回该key，用于导出错误信息列表
 * @throws IOException
 */
public Map<String,Object> loadExecl(FileInputStream is) throws IOException {
	Map<String,Object> map = new HashMap<String, Object>();
	String errMsg = null;//错误消息
	// 根据指定的文件输入流导入Excel从而产生Workbook对象
	Workbook wb;
	try {
		wb = WorkbookFactory.create(is);
		// 获取Excel文档中的第一个表单
		Sheet sht0 = wb.getSheetAt(0);//商品sheet页
		// 对Sheet中的每一行进行迭代
		int n0 = sht0.getLastRowNum();//从0开始
		System.out.println("sht0检测总RowNum:"+(n0+1));
		if(n0>3000){
			errMsg = "单次上传请勿超过3000条记录,检测到共有"+n0+"条记录";
			map.put("errMsg", errMsg);
			return map;
		}
		int rowNum0 = 0;//基础信息行数
		boolean hasError = false;//是否有错误信息
		if(n0>0){
			List<StoreImportVo> importList = new ArrayList<StoreImportVo>();
			for(int i=0;i<=n0;i++){
				Row r = sht0.getRow(i);
				if(r==null){
					break;
				}
				Cell c0 = r.getCell(0);//店铺名称
				Cell c1 = r.getCell(1);//店铺编号
				Cell c2 = r.getCell(2);//地址
				Cell c3 = r.getCell(3);//电话
				Cell c4 = r.getCell(4);//排序
				if(i==0){//导入列名
					//
				}
				if(i>0){
					String v0 = c0+"";
					String v1 = c1+"";
					String v2 = c2+"";
					String v3 = c3+"";
					String v4 = c4+"";
					if((c0==null||Utility.isEmpty(v0))&&(c1==null||Utility.isEmpty(v1))
							&&(c2==null||Utility.isEmpty(v2))
							&&(c3==null||Utility.isEmpty(v3))
							&&(c4==null||Utility.isEmpty(v4))
							){
						//全部列为空则不继续判断
						break;
					}
					rowNum0++;
					StoreImportVo baseVo = new StoreImportVo();
					if(Utility.isNotEmpty(v0)){//店铺名称
						baseVo.setName(v0.trim());
					}else{
						baseVo.setRemark(baseVo.getRemark()+"，店铺名称不能为空");
						hasError = true;
					}
					if(Utility.isNotEmpty(v1)){//店铺编号
						baseVo.setStoreCode(v1.trim());
					}else{
						baseVo.setRemark(baseVo.getRemark()+"，店铺编号不能为空");
						hasError = true;
					}
					if(Utility.isNotEmpty(v2)){//地址
						baseVo.setAddress(v2.trim());
					}else{
						baseVo.setRemark(baseVo.getRemark()+"，地址不能为空");
						hasError = true;
					}
					if(Utility.isNotEmpty(v3)){//电话
						baseVo.setPhoneNo(v3.trim());
					}
					if(Utility.isNotEmpty(v4)){//排序
						//TODO 判断整数
						int n = v4.trim().indexOf(".");
						if(n>0){
							v4=v4.substring(0, n);
						}
						try {
							Integer sortNum = Integer.valueOf(v4);
							if(sortNum<0){
								baseVo.setRemark(baseVo.getRemark()+"，排序不是有效数字");
								hasError = true;
							}else{
								baseVo.setSortNum(sortNum);
							}
						} catch (Exception e) {
							baseVo.setRemark(baseVo.getRemark()+"，排序不是有效数字");
							hasError = true;
							e.printStackTrace();
						}
					}else{
						baseVo.setRemark(baseVo.getRemark()+"，排序不能为空");
						hasError = true;
					}
					importList.add(baseVo);
				}
			}
			if(hasError){
				for(int i=0;i<importList.size();i++){
					StoreImportVo vo = importList.get(i);
					if(Utility.isEmpty(vo.getRemark())){
						importList.remove(vo);//去掉没有错误的记录
						i--;
					}else{
						vo.setRemark(vo.getRemark().substring(1));
					}
				}
				errMsg = importList.size()+"条有问题的记录导出中...";
				map.put("errMsg", errMsg);
				//上传七牛
				String title = "实体店导入错误提示（请在原导入表中修改好后再次导入）";
				String key = this.tGoodsService.uploadExcelFileToQN(StoreImportVo.class,importList,null,"storeUpload",title);
				map.put("errKey", key);
			}else{
				map.put("importList", importList);//导入记录
			}
		}
		System.out.println("sht0有效数据总RowNum:"+rowNum0);
	} catch (InvalidFormatException e) {
		e.printStackTrace();
		logger.error(e.getMessage());
	} catch (IOException e) {
		e.printStackTrace();
		logger.error(e.getMessage());
	}finally {
		is.close();
	}
	return map;
}
	
	
}
