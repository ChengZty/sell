package com.buss.order.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
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

import cn.redis.service.RedisService;

import com.buss.base.entity.BaseDeliveryEntity;
import com.buss.order.entity.TOrderDetailEntity;
import com.buss.order.entity.TOrderInfoEntity;
import com.buss.order.entity.TOrderLogEntity;
import com.buss.order.service.TOrderInfoServiceI;
import com.buss.order.vo.FahuoListVo;
import com.buss.order.vo.TOrderDeliveryVo;
import com.buss.page.order.TOrderInfoPage;
/**   
 * @Title: Controller
 * @Description: 订单信息
 * @author onlineGenerator
 * @date 2016-03-15 17:20:38
 * @version V1.0   
 *
 */
@Scope("prototype") 
@Controller
@RequestMapping("/tOrderInfoController")
public class TOrderInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TOrderInfoController.class);

	@Autowired
	private TOrderInfoServiceI tOrderInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RedisService redisService;


	/**
	 * 订单信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/buss/order/tOrderInfoList");
	}

	/**
	 * 订单信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tOrderToPay")
	public ModelAndView tOrderToPay(HttpServletRequest request) {
		return new ModelAndView("com/buss/order/tOrderToPayList");
	}
	
	/**
	 * easyui AJAX请求数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TOrderInfoEntity tOrderInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TOrderInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOrderInfo);
		try{
		//自定义追加查询条件
			String retailerId = ResourceUtil.getRetailerId();
			if(Utility.isNotEmpty(retailerId)){
				cq.eq("toRetailerId", retailerId);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tOrderInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 订单信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TOrderInfoEntity tOrderInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOrderInfo.getId())) {
			tOrderInfo = tOrderInfoService.flushEntity(TOrderInfoEntity.class, tOrderInfo.getId());
			req.setAttribute("tOrderInfoPage", tOrderInfo);
		}
		return new ModelAndView("com/buss/order/tOrderInfo-update");
	}
	
	/**
	 * 订单信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goGiveDiscount")
	public ModelAndView goGiveDiscount(TOrderInfoEntity tOrderInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tOrderInfo.getId())) {
			tOrderInfo = tOrderInfoService.flushEntity(TOrderInfoEntity.class, tOrderInfo.getId());
			req.setAttribute("tOrderInfoPage", tOrderInfo);
		}
		return new ModelAndView("com/buss/order/tOrderInfo-update");
	}
	
	/**
	 * 付款  临时测试用
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doPay")
	@ResponseBody
	public AjaxJson doPay(TOrderInfoEntity tOrderInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "付款成功";
		try{
			tOrderInfoService.doPay(tOrderInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单付款失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	
	/**
	 * 加载明细列表[订单明细]
	 * @return
	 */
	@RequestMapping(params = "tOrderDetails")
	public ModelAndView tOrderDetails(TOrderInfoEntity tOrderInfo, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tOrderInfo.getId();
		//===================================================================================
		//查询-订单明细(状态和主单一致)
	    String hql0 = "from TOrderDetailEntity where 1 = 1 and status = 'A' AND orderId = ? ";
	    try{
	    	List<TOrderDetailEntity> tOrderDetailEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tOrderDetails", tOrderDetailEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/buss/order/tOrderDetails");
	}
	
	/**
	 * 加载明细列表[订单明细]
	 * @return
	 */
	@RequestMapping(params = "getOrderDetails")
	@ResponseBody
	public AjaxJson getOrderDetails(String  orderId, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
	    String hql0 = "from TOrderDetailEntity where  orderId = ? ";
	    try{
	    	List<TOrderDetailEntity> tOrderDetailList = systemService.findHql(hql0,orderId);
	    	j.setObj(tOrderDetailList);
		}catch(Exception e){
			j.setSuccess(false);
			logger.info(e.getMessage());
		}
		return j;
	}

    /**
    * 导出excel
    * @param request
    * @param response
    */
    @RequestMapping(params = "exportXls")
    public String exportXls(TOrderInfoEntity tOrderInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,ModelMap map) {
    	CriteriaQuery cq = new CriteriaQuery(TOrderInfoEntity.class, dataGrid);
    	//查询条件组装器
    	org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tOrderInfo);
    	try{
    	//自定义追加查询条件
    	}catch (Exception e) {
    		throw new BusinessException(e.getMessage());
    	}
    	cq.add();
    	List<TOrderInfoEntity> list=this.tOrderInfoService.getListByCriteriaQuery(cq, false);
    	List<TOrderInfoPage> pageList=new ArrayList<TOrderInfoPage>();
        if(list!=null&&list.size()>0){
        	for(TOrderInfoEntity entity:list){
        		try{
        		TOrderInfoPage page=new TOrderInfoPage();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
            	    Object id0 = entity.getId();
				    String hql0 = "from TOrderDetailEntity where 1 = 1 AND oRDER_ID = ? ";
        	        List<TOrderDetailEntity> tOrderDetailEntityList = systemService.findHql(hql0,id0);
            		page.setTOrderDetails(tOrderDetailEntityList);
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
        map.put(NormalExcelConstants.FILE_NAME,"订单信息");
        map.put(NormalExcelConstants.CLASS,TOrderInfoPage.class);
        map.put(NormalExcelConstants.PARAMS,new ExportParams("订单信息列表", "导出人:Jeecg",
            "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST,pageList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

  
	/**
	* 导出excel 使模板
	*/
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ModelMap map) {
		map.put(NormalExcelConstants.FILE_NAME,"订单信息");
		map.put(NormalExcelConstants.CLASS,TOrderInfoPage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("订单信息列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
		"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}


	/**
	 * 修改地址
	 * @return
	 */
	@RequestMapping(params = "goChangeAddress")
	public ModelAndView goChangeAddress(String orderId, HttpServletRequest req) {
		TOrderInfoEntity tOrderInfo = tOrderInfoService.flushEntity(TOrderInfoEntity.class, orderId);
		req.setAttribute("tOrderInfoPage", tOrderInfo);
		return new ModelAndView("com/buss/order/tOrderInfo-updateAddress");
	}
	
	/**
	 * 更新地址
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdateAddress")
	@ResponseBody
	public AjaxJson doUpdateAddress(TOrderInfoEntity tOrderInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tOrderInfoService.doUpdateAddress(tOrderInfo);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新地址失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 订单明细查看
	 * @return
	 */
	@RequestMapping(params = "goView")
	public ModelAndView goView( String id, HttpServletRequest req) {
		TOrderInfoEntity tOrderInfo = tOrderInfoService.get(TOrderInfoEntity.class, id);
		List<TOrderDetailEntity> tOrderDetailList = tOrderInfoService.findByProperty(TOrderDetailEntity.class, "orderId", id);
		//订单日志
		List<TOrderLogEntity> logList = this.systemService.findHql("from TOrderLogEntity where orderId=? and type=? order by logTime desc", id,TOrderLogEntity.TYPE_1);
		//20170624更改 用于兼容查询以前记录的是订单明细的记录
//		List<TOrderLogEntity> logList = this.systemService.findHql("from TOrderLogEntity where orderNo like'%"+tOrderInfo.getOrderNo()+"%' and type=? order by logTime desc", TOrderLogEntity.TYPE_1);
		req.setAttribute("tOrderDetailList", tOrderDetailList);
		req.setAttribute("tOrderInfo", tOrderInfo);
		req.setAttribute("logList", logList);
		return new ModelAndView("com/buss/order/tOrderDetail-view");
	}
	
	/**
	 * 获取待发货订单明细个数
	 * @return
	 */
	 @RequestMapping(params = "getToBeFahuoCount")
	@ResponseBody
	public AjaxJson getToBeFahuoCount(){
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();
		Long count = this.tOrderInfoService.getToBeFahuoCount(user);
		if(count>99){
			j.setMsg("99+");
		}else{
			j.setMsg(count.toString());
		}
		return j;
	}
	
		/**
		 * 跳向发货页面
		 * @return
		 */
		@RequestMapping(params = "goFahuo")
		public ModelAndView goFahuo(String orderNos,HttpServletRequest req) {
			if(StringUtil.isNotEmpty(orderNos)){
				Set<String> set = new HashSet<String>();
				String[] arr = orderNos.split(",");
				for (int i = 0; i < arr.length; i++) {
					set.add(arr[i]);
				}
				req.setAttribute("orderNos", set);
				List<BaseDeliveryEntity> list = this.systemService.getList(BaseDeliveryEntity.class);
				req.setAttribute("deliveryList", list);
			}
			return new ModelAndView("com/buss/order/tOrderDetail-fahuo");
		}
	
		/**
		 * 批量发货
		 * @return
		 */
		@RequestMapping(params = "doBatchFahuo")
		@ResponseBody
		public AjaxJson doBatchFahuo(FahuoListVo vo,HttpServletRequest request){
			AjaxJson j = new AjaxJson();
			String message = "订单发货成功";
			try{
				//批量发货并更新订单和明细的状态和发货信息，记录订单日志，判断是否需要发送收货短信
				Map<String,Object> map = tOrderInfoService.doBatchFahuo(vo.getDeliveryList());
				//发短信,更新订单版本号小红点,订阅物流
				this.updateFahuoBuss(map);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "订单发货失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
		
		/**发短信,更新订单版本号小红点,订阅物流
		 * @param map
		 */
		@SuppressWarnings("unchecked")
		private void updateFahuoBuss(Map<String, Object> map) {
			Set<String> guideIds = (Set<String>) map.get("guideIds");
			if(guideIds.size()>0){
				try{
					for(String guideId : guideIds){
						//更新订单版本号小红点
						redisService.incr(common.GlobalConstants.GUIDE_ORDER_VERSION+"_"+guideId);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			//发短信不用跟改变订单状态放在一个事物
			List<TOrderInfoEntity> list = (List<TOrderInfoEntity>) map.get("orderList");
			if(list.size()>0){
					for(TOrderInfoEntity tOrderInfo : list){
						try{
							//发送发货短信
							tOrderInfoService.sendFahuoMsg(tOrderInfo);
							//订阅物流 20170901取消订阅
//							tOrderInfoService.subscription(tOrderInfo);
						}catch (Exception e) {
							logger.info("订单号："+tOrderInfo.getOrderNo()+" 发货短信回复失败，errMsg:"+e.getMessage());
							e.printStackTrace();
						}
					}
			}
		}

		/**
		 * 导出批量发货excel模板
		 * @param request
		 * @param response
		 */
		@RequestMapping(params = "downLoadFahuoT")
		public String downLoadFahuoT(TOrderDeliveryVo vo,HttpServletRequest request,HttpServletResponse response
				, DataGrid dataGrid,ModelMap modelMap) {
			modelMap.put(NormalExcelConstants.FILE_NAME,"批量发货");
			modelMap.put(NormalExcelConstants.CLASS,TOrderDeliveryVo.class);
			modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("批量发货列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
			modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
			return NormalExcelConstants.JEECG_EXCEL_VIEW;
		}
		
		
		/**
		* 导入功能跳转
		* @return
		*/
		@RequestMapping(params = "upload")
		public ModelAndView upload(HttpServletRequest req) {
			req.setAttribute("controller_name", "tOrderInfoController");
			return new ModelAndView("common/upload/pub_excel_upload");
		}
		
		/**导入发货*/
		@RequestMapping(params = "importExcel", method = RequestMethod.POST)
		@ResponseBody
		public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
			AjaxJson j = new AjaxJson();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile file = entity.getValue();// 获取上传文件对象
				ImportParams params = new ImportParams();
				params.setTitleRows(2);
				params.setHeadRows(1);
				params.setNeedSave(false);
				try {
					List<TOrderDeliveryVo> voList = ExcelImportUtil.importExcel(file.getInputStream(),TOrderDeliveryVo.class,params);
					if(Utility.isNotEmpty(voList)){
						HashSet<String> set = new HashSet<String>();//导入的excel中的订单号集合
						HashSet<String> deliverySet = new HashSet<String>();//导入的excel中的子订单号集合
						//校验excel中是否有重复的子订单号
						String msg = checkImportOrderNo(voList,set,deliverySet);
						if(!StringUtil.isEmpty(msg)){
							j.setMsg(msg);
						}else{
//							List<Object> list = null;
							String sql_delivery = "select delivery_name,delivery_code from base_delivery where  status = 'A' ";//所有的物流公司
							List<Map<String, Object>> mapList = this.systemService.findForJdbc(sql_delivery, null);
							Map<String, String> mapDelivery = new HashMap<String, String>();//物流map<name,code>
							for(Map<String, Object> map : mapList){
								mapDelivery.put(map.get("delivery_name").toString(), map.get("delivery_code").toString());
							}
							//检查物流公司是否存在于系统中
							msg = checkExistDelivery(deliverySet,mapDelivery);
							if(!StringUtil.isEmpty(msg)){
								j.setMsg("系统中不存在物流公司："+msg+"，请联系g+管理员");
							}else{
								//检验订单号是否是对应所属零售商的单号，导入的excel中的订单号是否都是系统中后台待发货状态的订单，必须存在于系统中
								msg = checkExitsOrderNo(set);
								if(!StringUtil.isEmpty(msg)){
									j.setMsg(msg);
								}else{
									Map<String,Object> map =this.tOrderInfoService.doBatchFahuoByExcel(voList,mapDelivery);
									//发短信,更新订单版本号小红点,订阅物流
									this.updateFahuoBuss(map);
									j.setMsg("文件导入成功！");
								}
							}
						}
					}else{
						j.setMsg("文件是空的！");
					}
				} catch (Exception e) {
					j.setMsg("文件导入失败！");
					logger.error(ExceptionUtil.getExceptionMessage(e));
				}finally{
					try {
						file.getInputStream().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return j;
		}
		
		/**检查导入的订单号是否有重复,通过校验否则返回 null,并把子订单号存在set中,把物流公司名称放入deliverySet
		 * @param voList
		 * @param set
		 * @param deliverySet
		 * @return
		 */
		private String checkImportOrderNo(List<TOrderDeliveryVo> voList,HashSet<String> set,HashSet<String> deliverySet) {
			for(TOrderDeliveryVo entity:voList){
				if(StringUtil.isEmpty(entity.getDeliveryName())||StringUtil.isEmpty(entity.getDeliveryNo())
						||StringUtil.isEmpty(entity.getOrderNo())){
					return "请同时填写订单号，物流公司，物流单号";
				}
				deliverySet.add(entity.getDeliveryName());
				if(set.contains(entity.getOrderNo())){
					return "订单号:"+entity.getOrderNo()+"出现重复，请检查";
				}else{
					set.add(entity.getOrderNo());
				}
			}
			return null;
		}
		
		/**返回不存在的物流公司名字，否则返回null
		 * @param deliverySet
		 * @param mapDelivery
		 * @return
		 */
		private String checkExistDelivery(HashSet<String> deliverySet,	Map<String, String> mapDelivery) {
			for(String name : deliverySet){
				if(!mapDelivery.containsKey(name)){
					return name;
				}
			}
			return null;
		}
		
		/**校验导入的订单号是否是系统中类型为后台发货的待发货的订单号
		 * @param set 导入的订单号集合
		 * @return
		 */
		private String checkExitsOrderNo(HashSet<String> set) {
			String retailerId = ResourceUtil.getRetailerId();
			String  sql_order_no = "select order_no from t_order_info where to_retailer_id ='"+retailerId +"' and order_status ='2' and delivery_type='1' and status ='A'";
			List<Object> list = this.systemService.findListbySql(sql_order_no);
			if(Utility.isEmpty(list)){
				return "当前无后台可发货的订单";
			}else{
				HashSet<String> setOrderNos = new HashSet<String>();
				for(Object obj:list){
					setOrderNos.add(obj.toString());
				}
				for(String orderNo : set){
					if(!setOrderNos.contains(orderNo)){
						return "系统中不存在该待发货订单号："+orderNo;
					}
				}
			}
			return null;
		}
		
		/**
		 * 取消订单，并还库存等业务
		 * @param id
		 * @return
		 */
		@RequestMapping(params = "cancelOrder")
		@ResponseBody
		public AjaxJson cancelOrder(String id, HttpServletRequest request) {
			AjaxJson j = new AjaxJson();
			String message = "订单取消成功";
			try{
				tOrderInfoService.doCancelOrder(id);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}catch(Exception e){
				e.printStackTrace();
				message = "订单取消失败";
				throw new BusinessException(e.getMessage());
			}
			j.setMsg(message);
			return j;
		}
		
		
}
