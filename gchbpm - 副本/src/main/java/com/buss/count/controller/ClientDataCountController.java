package com.buss.count.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.count.service.ClientDataCountServiceI;

import org.jeecgframework.core.util.DateUtils;



/**   
 * @Title: Controller
 * @Description: 客户数据汇总报表
 * @author onlineGenerator
 * @date 2017-05-17 11:28:10
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/clientDataCountController")
public class ClientDataCountController extends BaseController {
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(ClientDataCountController.class);
	@Autowired
	private ClientDataCountServiceI clientDataCountServiceI;
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
	 * 客户数据统计 页面跳转
	 * @return
	 */
	@RequestMapping(params = "goClientDataPage")
	public ModelAndView tStoreMonTarget(HttpServletRequest request) {
 		String retailerId = ResourceUtil.getRetailerId();
		String sql = "select id,name from t_store WHERE status='A' and retailer_id='"+retailerId+"' ORDER BY id";
		List<Map<String,Object>> storeList = systemService.findForJdbc(sql, null);
		request.setAttribute("storeList", storeList);
		
		request.setAttribute("start_time", DateUtils.getFirstDayOfMonth(new Date()));
		request.setAttribute("end_time", DateUtils.getDataString(DateUtils.date_sdf));
		return new ModelAndView("com/buss/count/clientDataPage");
	}
	
	//ajax 请求获取零售商的导购统计汇总信息
	@RequestMapping(params = "getGuideCountInfo")
	@ResponseBody
	public AjaxJson getGuideCountInfo(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> allGuideList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> guideNumList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> addGuideNumList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> outGuideNumList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> clickGuideNumList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> payGuideNumList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> finishJobGuideNumList = new ArrayList<Map<String,Object>>();
		allGuideList = clientDataCountServiceI.getGuideNumList(request,"all"); //所有的导购数
 		guideNumList = clientDataCountServiceI.getGuideNumList(request,"g+"); //使用G+的导购数
 		addGuideNumList = clientDataCountServiceI.getGuideNumList(request,"add");//新增导购数
 		outGuideNumList = clientDataCountServiceI.getGuideNumList(request,"out");//退出导购数
 		clickGuideNumList = clientDataCountServiceI.getClickGuideNumList(request);//每日点击APP的导购数
 		payGuideNumList = clientDataCountServiceI.getGuideOrderList(request);//在线成交的导购数
 		finishJobGuideNumList = clientDataCountServiceI.finishJobGuideNumList(request);//每日在线完成任务导购数

		resultMap.put("allGuideList", allGuideList);
		resultMap.put("guideNumList", guideNumList);
		resultMap.put("addGuideNumList", addGuideNumList);
		resultMap.put("outGuideNumList", outGuideNumList);
		resultMap.put("clickGuideNumList", clickGuideNumList);
		resultMap.put("payGuideNumList", payGuideNumList);
		resultMap.put("finishJobGuideNumList", finishJobGuideNumList);
		j.setObj(resultMap);
		j.setSuccess(true);
		j.setMsg("查询成功");
		return j;
	}
	//ajax 请求获取零售商的成交统计汇总信息
	@RequestMapping(params = "getOrderCountInfo")
	@ResponseBody
	public AjaxJson getOrderCountInfo(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> payOrderNumList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> outTimeOrderNumList = new ArrayList<Map<String,Object>>();
		payOrderNumList = clientDataCountServiceI.getPayOrderNumList(request); //成交单数//在线销售总件数//在线销售总金额//在线导购平均产出
		outTimeOrderNumList = clientDataCountServiceI.getOutTimeOrderNumList(request);//非实体营业时间成交单数
		
		resultMap.put("payOrderNumList", payOrderNumList);
		resultMap.put("outTimeOrderNumList", outTimeOrderNumList);
		j.setObj(resultMap);
		j.setSuccess(true);
		j.setMsg("查询成功");
		return j;
	}

	/**
	 * 部分话题统计接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getNewsCountInfo")
	@ResponseBody
	public AjaxJson getNewsCountInfo1(HttpServletRequest request, HttpServletResponse response) { 
		AjaxJson j = new AjaxJson();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>>  uploadedList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> clickList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> issendList = new ArrayList<Map<String,Object>>();
		uploadedList = clientDataCountServiceI.hasUploadedNumList(request); //已发布话题数
		clickList = clientDataCountServiceI.hasClickNumList(request);//点击打开的话题数
		issendList = clientDataCountServiceI.hasIssendList(request);//已推送话题数
		
		resultMap.put("uploadedList", uploadedList);
		resultMap.put("clickList", clickList);
		resultMap.put("issendList", issendList);
		j.setObj(resultMap);
		j.setSuccess(true);
		j.setMsg("查询成功");
		return j;
	}
	/**
	 * 部分话术统计接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "getWordsCountInfo")
	@ResponseBody
	public AjaxJson getWordsCountInfo(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>>  custWordsList = new ArrayList<Map<String,Object>>();
		custWordsList = clientDataCountServiceI.hasCustWordsNumList(request);//撩客话术总量
		
		resultMap.put("custWordsList", custWordsList);
		j.setObj(resultMap);
		j.setSuccess(true);
		j.setMsg("查询成功");
		return j;
	}
	
	
	/**
	 * 顾客模块统计汇总
	 */
	@RequestMapping(params = "getCustomerInfo")
	@ResponseBody
	public  AjaxJson countCustomer(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> allCustomer = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> waitCustomer = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> hasTrasactCustomer = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> zeroToTwenty = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> TwentyToFourty = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> fourtyToSixty = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> sixtyToEighty = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> eightyToHundred = new ArrayList<Map<String,Object>>();

		allCustomer = this.clientDataCountServiceI.countCustomer(request, 3);//G+顾客数量
		waitCustomer = this.clientDataCountServiceI.countCustomer(request, 1);//待发展
		hasTrasactCustomer = this.clientDataCountServiceI.countCustomer(request, 2);//已成交
		//zeroToTwenty = this.clientDataCountServiceI.countCustomerPercent(request, 0, 20);//资料完成度在0%~20%之间的顾客数
		TwentyToFourty =  this.clientDataCountServiceI.countCustomerPercent(request, 20, 40);//资料完成度在20%~40%之间的顾客数
		fourtyToSixty =  this.clientDataCountServiceI.countCustomerPercent(request, 40, 60);//资料完成度在40%~60%之间的顾客数
		sixtyToEighty =  this.clientDataCountServiceI.countCustomerPercent(request, 60, 80);//资料完成度在60%~80%之间的顾客数
		eightyToHundred =  this.clientDataCountServiceI.countCustomerPercent(request, 80, 100);//资料完成度在80%~100%之间的顾客数
		//手机通讯录导入为0-20的 用总数与其他完成度的相减
		for (int i=0;i<allCustomer.size()-1;i++) {  //每个店铺数据相减
			Map<String,Object> map = new HashMap<String,Object>();
			int allCustomerCount = Integer.parseInt(allCustomer.get(i).get("count").toString());
			int TwentyToFourtyCount = Integer.parseInt(TwentyToFourty.get(i).get("count").toString());
			int fourtyToSixtyCount = Integer.parseInt(fourtyToSixty.get(i).get("count").toString());
			int sixtyToEightyCount = Integer.parseInt(sixtyToEighty.get(i).get("count").toString());
			int eightyToHundredCount = Integer.parseInt(eightyToHundred.get(i).get("count").toString());
			int zeroToTwentyCount = allCustomerCount - TwentyToFourtyCount - fourtyToSixtyCount - sixtyToEightyCount - eightyToHundredCount;
			map.put("storeId", allCustomer.get(i).get("storeId"));
			map.put("count", zeroToTwentyCount);
			zeroToTwenty.add(map);
		}
		//合计数据相减
		Map<String,Object> mapTotal = new HashMap<String,Object>();  
		int allCustomerTotal = Integer.parseInt(allCustomer.get(allCustomer.size()-1).get("totalNum").toString());
		int TwentyToFourtyTotal = Integer.parseInt(TwentyToFourty.get(TwentyToFourty.size()-1).get("totalNum").toString());
		int fourtyToSixtyTotal = Integer.parseInt(fourtyToSixty.get(fourtyToSixty.size()-1).get("totalNum").toString());
		int sixtyToEightyTotal = Integer.parseInt(sixtyToEighty.get(sixtyToEighty.size()-1).get("totalNum").toString());
		int eightyToHundredTotal = Integer.parseInt(eightyToHundred.get(eightyToHundred.size()-1).get("totalNum").toString());
		int zeroToTwentyTotal = allCustomerTotal - TwentyToFourtyTotal - fourtyToSixtyTotal - sixtyToEightyTotal - eightyToHundredTotal;
		mapTotal.put("totalNum", zeroToTwentyTotal);
		zeroToTwenty.add(mapTotal);
		
		resultMap.put("allCustomer", allCustomer);
		resultMap.put("waitCustomer", waitCustomer);
		resultMap.put("hasTrasactCustomer", hasTrasactCustomer);
		resultMap.put("zeroToTwenty", zeroToTwenty);
		resultMap.put("TwentyToFourty", TwentyToFourty);
		resultMap.put("fourtyToSixty", fourtyToSixty);
		resultMap.put("sixtyToEighty", sixtyToEighty);
		resultMap.put("eightyToHundred", eightyToHundred);
		
		j.setObj(resultMap);
		j.setSuccess(true);
		j.setMsg("查询成功");
		return j;
	}
	
	@RequestMapping(params = "countGoodsInfo")
	@ResponseBody
	public  AjaxJson countGoodsInfo(HttpServletRequest request) {
		
		AjaxJson j = new AjaxJson();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> hasPublish = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> allGoods = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> waitPublish = new ArrayList<Map<String,Object>>();
//		List<Map<String,Object>> hasWordsPercent = new ArrayList<Map<String,Object>>();
		
		waitPublish = this.clientDataCountServiceI.countGoods(request, 1);  //查询待上架商品数量
		hasPublish = this.clientDataCountServiceI.countGoods(request, 2);  //查询已上架商品数量
		allGoods = this.clientDataCountServiceI.countGoods(request, 3);  //查询所有商品数量
		//hasWordsPercent = this.clientDataCountServiceI.countHasWordsPercent(request); //查询含有话术所占商品总量
		
		resultMap.put("waitPublish", waitPublish);
		resultMap.put("hasPublish", hasPublish);
		resultMap.put("allGoods", allGoods);
		//resultMap.put("hasWordsPercent", hasWordsPercent);
		
		j.setObj(resultMap);
		j.setSuccess(true);
		j.setMsg("查询成功");
		return j;
	}
	
	@RequestMapping(params = "countJobsInfo")
	@ResponseBody
	public  AjaxJson countJobsInfo(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> onlineJobsList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> jobParamCountList = new ArrayList<Map<String,Object>>();
		
		onlineJobsList = this.clientDataCountServiceI.onlineJobNumList(request);  //在线任务数
		jobParamCountList = this.clientDataCountServiceI.jobFinishCountList(request);  //任务项完成度统计
		
		resultMap.put("onlineJobsList", onlineJobsList);
		resultMap.put("jobParamCountList", jobParamCountList);
		
		j.setObj(resultMap);
		j.setSuccess(true);
		j.setMsg("查询成功");
		return j;
	}
}
