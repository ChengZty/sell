package com.buss.order.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.vo.CommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.buss.order.entity.TStoreOrderInfoEntity;
import com.buss.order.service.TStoreOrderInfoServiceI;
import com.buss.store.entity.TStoreEntity;
import com.buss.store.service.TStoreServiceI;
import com.buss.user.entity.TPersonEntity;



/**   
 * @Title: Controller
 * @Description: 订单明细
 * @author onlineGenerator
 * @date 2016-03-15 17:45:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tStoreOrderInfoController")
public class TStoreOrderInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TStoreOrderInfoController.class);

	@Autowired
	private TStoreServiceI tStoreService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TStoreOrderInfoServiceI TStoreOrderInfoService;
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 线下订单明细列表 页面跳转
	 * @return
	 */
	@RequestMapping(params = "tStoreOrderInfoList")
	public ModelAndView tStoreOrderInfoList(HttpServletRequest request) {
		//查询店铺
		String storeStr = "";
		List<CommonVo> storeList = tStoreService.getStoreList();
		for (CommonVo commonVo : storeList) {
			storeStr = storeStr + commonVo.getName() + "_" + commonVo.getId() + ",";
		}
		if(Utility.isNotEmpty(storeStr)){
			request.setAttribute("stores", storeStr.subSequence(0, storeStr.length()-1));
		}
		
		//封装初始时间
		request.setAttribute("payTime_begin", DateUtils.getFirstDayOfMonth(new Date()));
		request.setAttribute("payTime_end", DateUtils.getDataString(DateUtils.date_sdf));
		
		return new ModelAndView("com/buss/order/tStoreOrderInfoList");
	}
	
	/**
	 * 线下订单明细列表
	 * @return
	 */
	@RequestMapping(params = "datagrid")
	public void storeDatagrid(TStoreOrderInfoEntity tStoreOrderInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TStoreOrderInfoService.getTStoreOrderInfo(request, dataGrid,"list");
		TagUtil.datagrid(response, dataGrid);
	}
	 
	/**
	 * 订单明细查看
	 * 
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView(TStoreOrderInfoEntity StoreOrderInfo, HttpServletRequest req) {
		StringBuffer sql = new StringBuffer("select tsoi.id id,tsoi.order_no orderNo,tsoi.user_name userName,");
		sql.append("tsoi.user_phone userPhone,pay_amount payAmount,quantity_amount quantityAmount,pay_time payTime,");
		sql.append("tsoi.pic_url picUrl,tp.real_name guideName,tp.phone_no guidePhone,ts.name storeName,ts.id storeId FROM ");
		sql.append("t_store_order_info tsoi left join t_person tp on tp.user_id = tsoi.to_guide_id ");
		sql.append("left join t_store ts on tp.store_id = ts.id where tsoi.status='A' and tsoi.id='").append(StoreOrderInfo.getId()).append("'");
		List<TStoreOrderInfoEntity> list = this.systemService.findObjForJdbc(sql.toString(), TStoreOrderInfoEntity.class);
		if(Utility.isNotEmpty(list)){
			req.setAttribute("StoreOrderInfo", list.get(0));
		}
		/*StoreOrderInfo = this.systemService.get(TStoreOrderInfoEntity.class, StoreOrderInfo.getId());
		
		TPersonEntity guide = this.systemService.findUniqueByProperty(TPersonEntity.class, "userId", StoreOrderInfo.getToGuideId());
		StoreOrderInfo.setGuideName(guide.getRealName());
		StoreOrderInfo.setGuidePhone(guide.getPhoneNo());
		TStoreEntity store = this.systemService.findUniqueByProperty(TStoreEntity.class, "id", guide.getStoreId());
		StoreOrderInfo.setStoreName(store.getName());
		req.setAttribute("StoreOrderInfo", StoreOrderInfo);*/
		return new ModelAndView("com/buss/order/tStoreOrderInfo-view");
	}
	
	
	/**
	 * 导出订单明细excel
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(HttpServletRequest request,HttpServletResponse response	, DataGrid dataGrid,ModelMap modelMap) {
		String exlTitle = "线下订单明细信息";
		String retailerId = ResourceUtil.getRetailerId();
		List<TStoreOrderInfoEntity> list = TStoreOrderInfoService.getTStoreOrderInfo(request, dataGrid,"all");
		modelMap.put(NormalExcelConstants.FILE_NAME,exlTitle);
		modelMap.put(NormalExcelConstants.CLASS,TStoreOrderInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(exlTitle, "导出人:"+ResourceUtil.getSessionUserName().getRealName(),"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,list);
		logger.info(retailerId+"导出线下订单明细excel");
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
}