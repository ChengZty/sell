package com.buss.goods.controller;
import java.io.FileInputStream;
import java.io.IOException;
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
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.FileUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
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

import com.buss.goods.entity.TGoodsActDetailEntity;
import com.buss.goods.entity.TGoodsActEntity;
import com.buss.goods.service.TGoodsActDetailServiceI;
import com.buss.goods.service.TGoodsServiceI;
import com.buss.goods.vo.ActPriceImportVo;
import com.buss.store.vo.StoreImportVo;



/**   
 * @Title: Controller
 * @Description: 商品活动明细
 * @author onlineGenerator
 * @date 2017-09-13 16:27:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tGoodsActDetailController")
public class TGoodsActDetailController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TGoodsActDetailController.class);

	@Autowired
	private TGoodsActDetailServiceI tGoodsActDetailService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TGoodsServiceI tGoodsService;
//	@Autowired
//	private RedisService redisService;
//	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 商品活动明细列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String goodsActId = request.getParameter("goodsActId");
		request.setAttribute("goodsActId", goodsActId);
		TGoodsActEntity tGoodsAct = this.systemService.get(TGoodsActEntity.class, goodsActId);
		if(Utility.isNotEmpty(tGoodsAct)){
			request.setAttribute("auditStatus", tGoodsAct.getAuditStatus());
		}
		request.setAttribute("view", request.getParameter("view"));
		request.setAttribute("add", request.getParameter("add"));
		return new ModelAndView("com/buss/goods/tGoodsActDetailList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TGoodsActDetailEntity tGoodsActDetail,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsActDetailEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoodsActDetail, request.getParameterMap());
		try{
		//自定义追加查询条件
			String goodsActId = request.getParameter("actId");
			cq.eq("goodsActId", goodsActId);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tGoodsActDetailService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除商品活动明细
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TGoodsActDetailEntity tGoodsActDetail, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tGoodsActDetail = systemService.flushEntity(TGoodsActDetailEntity.class, tGoodsActDetail.getId());
		message = "商品活动明细删除成功";
		try{
			tGoodsActDetailService.delete(tGoodsActDetail);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品活动明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除商品活动明细
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "商品活动明细删除成功";
		try{
			TSUser user = ResourceUtil.getSessionUserName();
			StringBuffer sql = new StringBuffer("update t_goods_act_detail set status='I',update_date = '").append(Utility.getCurrentTimestamp())
					.append("',update_by='").append(user.getUserName()).append("' where id in (");
			for(String id:ids.split(",")){
				sql.append("'").append(id).append("',");
			}
			sql = sql.deleteCharAt(sql.length()-1).append(")");
			systemService.updateBySqlString(sql.toString());
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "商品活动明细删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导入功能跳转
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tGoodsActDetailController");
		req.setAttribute("goodsActId",req.getParameter("goodsActId"));
		return new ModelAndView("com/buss/goods/act_price_upload");
	}
	
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TGoodsActDetailEntity tGoodsActDetail,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TGoodsActDetailEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tGoodsActDetail, request.getParameterMap());
		List<TGoodsActDetailEntity> tGoodsActDetails = this.tGoodsActDetailService.getListByCriteriaQuery(cq,false);
		String goodsActId = request.getParameter("goodsActId");
		TGoodsActEntity tGoodsActEntity = this.systemService.get(TGoodsActEntity.class, goodsActId);
		if(Utility.isNotEmpty(tGoodsActEntity)){
			modelMap.put(NormalExcelConstants.FILE_NAME,"商品活动明细");
			modelMap.put(NormalExcelConstants.CLASS,TGoodsActDetailEntity.class);
			ExportParams params = new ExportParams();
			params.setTitle(tGoodsActEntity.getTitle());
			params.setSecondTitle("活动时间："+DateUtil.dateToString(tGoodsActEntity.getBeginTime(), DateUtil.DATETIME_PATTERN)+" ~ "
					+DateUtil.dateToString(tGoodsActEntity.getEndTime(), DateUtil.DATETIME_PATTERN));
			params.setSheetName("导出信息");
			modelMap.put(NormalExcelConstants.PARAMS,params);
			modelMap.put(NormalExcelConstants.DATA_LIST,tGoodsActDetails);
			return NormalExcelConstants.JEECG_EXCEL_VIEW;
		}
		return null;
	}
	
	/**
	 * 导出excel 使模板
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(HttpServletRequest request,	HttpServletResponse response) throws Exception{
		String path = request.getSession().getServletContext().getRealPath("\\WEB-INF\\templates");
		String fileName = "商品活动价导入模板.xlsx";
		String filePath = path + "\\" + fileName;
		FileUtils.downLoadFile(filePath, response, request, fileName, "xlsx");
		return null;
	}
	
	/**批量导入商品活动价
	 * 此处不确定活动时间，不做商品是否和其他商品活动时间冲突校验，点击保存活动的时候再校验
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
			String goodsActId = request.getParameter("goodsActId");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile file = entity.getValue();// 获取上传文件对象
				FileInputStream is = (FileInputStream) file.getInputStream();
				//导入数据，做非空校验及数字校验
				Map<String,Object> map = loadExecl(is);
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
					List<ActPriceImportVo> ActPriceImportVoList = (List<ActPriceImportVo>) map.get("importList");//导入数据列表
					if(ActPriceImportVoList.size()>0){
						String retailerId = ResourceUtil.getRetailerId();
						//校验并保存活动价，如果有错误记录则导出
						Map<String,Object> resultMap = this.tGoodsActDetailService.batchSaveActPrice(goodsActId,ActPriceImportVoList,retailerId);
						int successCount = (Integer) resultMap.get("successCount");//成功导入的条数
						if(successCount>0){
							message = "商品活动价成功导入"+successCount+"条记录";
						}
						List<ActPriceImportVo> failList = (List<ActPriceImportVo>) resultMap.get("failList");
						if(failList.size()>0){//导出失败的数据
							if(message==null){
								message = failList.size()+"条有问题的记录导出中...";
							}else{
								message+=","+failList.size()+"条有问题的记录导出中...";
							}
							String title = "商品活动价导入错误提示";
							String key = this.tGoodsService.uploadExcelFileToQN(ActPriceImportVo.class, failList, null, "uploadGoodsActPrice",title);
							j.setObj(key);
							j.setSuccess(false);
							failList.clear();
						}
						j.setMsg(message);
					}else{
						j.setMsg("导入数据不能为空");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			j.setMsg("文件导入失败，请检查单元格格式和模版格式是否一致！");
		}
		System.out.println("商品活动价导入结果========="+j.getMsg());
		return j;
	}
	
	/**获取导入的excel的实体店列表
	 * @param is
	 * @return List<ActPriceImportVo> importList 无错误的时候返回数据列表
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
				List<ActPriceImportVo> importList = new ArrayList<ActPriceImportVo>();
				for(int i=0;i<=n0;i++){
					Row r = sht0.getRow(i);
					if(r==null){
						break;
					}
					Cell c0 = r.getCell(0);//款号
					Cell c1 = r.getCell(1);//折扣
					Cell c2 = r.getCell(2);//活动价
					if(i==0){//导入列名
						//
					}
					if(i>0){
						String v0 = c0+"";
						String v1 = c1+"";
						String v2 = c2+"";
						if((c0==null||Utility.isEmpty(v0))&&(c1==null||Utility.isEmpty(v1))
								&&(c2==null||Utility.isEmpty(v2))
								){
							//全部列为空则不继续判断
							break;
						}
						rowNum0++;
						ActPriceImportVo baseVo = new ActPriceImportVo();
						if(Utility.isNotEmpty(v0)){//款号
							baseVo.setGoodsCode(v0.trim());
						}else{
							baseVo.setErrTip(baseVo.getErrTip()+"，款号不能为空");
							hasError = true;
						}
						if(Utility.isNotEmpty(v2)){//活动价
							int n = v2.indexOf(".")+1;
							int l = v2.length();
							if(l-n>2&&n>0){
								baseVo.setErrTip(baseVo.getErrTip()+"，活动价不能超过2位小数");
								hasError = true;
							}else{
								try {
									baseVo.setActPrice(new BigDecimal(v2));
								} catch (Exception e) {
									baseVo.setErrTip(baseVo.getErrTip()+"，活动价不是数字");
									hasError = true;
									e.printStackTrace();
								}
							}
						}else{
							if(Utility.isNotEmpty(v1)){//活动价折扣
								int n = v1.indexOf(".")+1;
								int l = v1.length();
								if(l-n>2&&n>0){
									baseVo.setErrTip(baseVo.getErrTip()+"，活动价折扣不能超过2位小数");
									hasError = true;
								}else{
									try {
										baseVo.setDiscount(new BigDecimal(v1));
									} catch (Exception e) {
										baseVo.setErrTip(baseVo.getErrTip()+"，活动价折扣不是数字");
										hasError = true;
										e.printStackTrace();
									}
								}
							}else{
								baseVo.setErrTip(baseVo.getErrTip()+"，活动价和折扣不能同时为空");
								hasError = true;
							}
						}
						importList.add(baseVo);
					}
				}
				if(hasError){
					for(int i=0;i<importList.size();i++){
						ActPriceImportVo vo = importList.get(i);
						if(Utility.isEmpty(vo.getErrTip())){
							importList.remove(vo);//去掉没有错误的记录
							i--;
						}else{
							vo.setErrTip(vo.getErrTip().substring(1));
						}
					}
					errMsg = importList.size()+"条有问题的记录导出中...";
					map.put("errMsg", errMsg);
					//上传七牛
					String title = "商品活动价导入错误提示（请在原导入表中修改好后再次导入）";
					String key = this.tGoodsService.uploadExcelFileToQN(ActPriceImportVo.class,importList,null,"uploadGoodsActPrice",title);
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
