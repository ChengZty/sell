package com.buss.report.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.Highchart;
import org.jeecgframework.core.util.DateUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.Utility;
import org.jeecgframework.core.util.oConvertUtils;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.buss.sms.entity.TSmsSendInfoEntity;


/**
 * 报表
 * @author 
 */
@Controller
@RequestMapping("/reportController")
public class ReportController extends BaseController {
	private static final Logger logger = Logger.getLogger(ReportController.class);
	@Autowired
	private SystemService systemService;
	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSLog.class, dataGrid);
		String loglevel = request.getParameter("loglevel");
		if (loglevel == null || loglevel.equals("0")) {
		} else {
			cq.eq("loglevel", oConvertUtils.getShort(loglevel));
			cq.add();
		}
        String operatetime_begin = request.getParameter("operatetime_begin");
        if(operatetime_begin != null) {
            Timestamp beginValue = null;
            try {
                beginValue = DateUtils.parseTimestamp(operatetime_begin, "yyyy-MM-dd");
            } catch (Exception e) {
                e.printStackTrace();
            }
            cq.ge("operatetime", beginValue);
        }
        String operatetime_end = request.getParameter("operatetime_end");
        if(operatetime_end != null) {
            if (operatetime_end.length() == 10) {
                operatetime_end =operatetime_end + " 23:59:59";
            }
            Timestamp endValue = null;
            try {
                endValue = DateUtils.parseTimestamp(operatetime_end, "yyyy-MM-dd hh:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cq.le("operatetime", endValue);
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	
	
	/**
	 * 短信推送 统计图表页面
	 * @return
	 */
	@RequestMapping(params = "smsInfoTabs")
	public ModelAndView smsInfoTabs(HttpServletRequest request) {
		request.setAttribute("smsId", request.getParameter("smsId"));
		return new ModelAndView("com/buss/report/smsInfoTabs");
	}
	
	/**
	 * 短信推送统计图（默认reportType=line）
	 * @return
	 */
	@RequestMapping(params = "smsSendReport")
	public ModelAndView smsSendReport(String reportType,String smsId, HttpServletRequest request) {
		request.setAttribute("smsId", smsId);
		request.setAttribute("reportType", reportType);
		if("pie".equals(reportType)){
			return new ModelAndView("system/log/userBroswerPie");
		}else if("line".equals(reportType)) {
			return new ModelAndView("com/buss/report/smsReportLine");
		}else if("column".equals(reportType)){
			return new ModelAndView("com/buss/report/smsReportCol");
		}
		return new ModelAndView("com/buss/report/smsReportLine");
	}

	/**
	 * 统计集合页面
	 * @return
	 */
	@RequestMapping(params = "smsReportSummaryPage")
	public ModelAndView smsReportSummaryPage(HttpServletRequest req) {
		req.setAttribute("templateName", req.getParameter("templateName"));
		req.setAttribute("sendTime_begin", req.getParameter("sendTime_begin"));
		req.setAttribute("sendTime_end", req.getParameter("sendTime_end"));
		req.setAttribute("sendStatus", req.getParameter("sendStatus"));
		return new ModelAndView("com/buss/report/reportSummary");
	}
	

	/**
	 * 报表三角图数据生成 单次推送短信
	 * reportType 类型 
	 * @return
	 */
	@RequestMapping(params = "getSmsReportById")
	@ResponseBody
	public AjaxJson getSmsReportById(HttpServletRequest request,String reportType,String smsId, HttpServletResponse response) {
		Highchart hc = new Highchart();
		List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
		hc.setName("推送报表");
		hc.setType(reportType);
		StringBuffer sql =  new StringBuffer();
		sql.append("SELECT i.push_count pushCount,")
		.append("i.reach,i.click_number clickNumber,")
//		.append("(SELECT count(1) from t_sms_send s1 where s1.send_info_id = i.id and s1.receive_status='1' and s1.status = 'A') reach,")
//		.append("(SELECT count(1) from t_sms_send s2 where s2.send_info_id = i.id and s2.is_click='1' and s2.status = 'A') clickNumber,")
		.append(" i.buy_single buySingle FROM t_sms_send_info i ")
		.append(" WHERE i.id = '").append(smsId).append("' ");

		Map<String, Object> countMap = systemService.findOneForJdbc(sql.toString(), null);
		
		getReportListMap(lt, countMap);
		

		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(lt);
		j.setSuccess(true);

		return j;
	}
	
	
	/**
	 * 漏斗形的短信转化情况报表 - 查看综合报表
	 * reportType 类型  
	 * @return
	 */
	@RequestMapping(params = "getSmsReportSummary")
	@ResponseBody
	public AjaxJson getSmsReportSummary(HttpServletRequest request,String reportType, HttpServletResponse response) {
		new ArrayList<Highchart>();
		Highchart hc = new Highchart();
		List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
		hc = new Highchart();
		hc.setName("推送报表");
		hc.setType(reportType);
		Map<String, Object> map;
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql =  new StringBuffer();
			sql.append("SELECT SUM(t.pushCount) pushCount,SUM(t.reach) reach,SUM(t.clickNumber) clickNumber,SUM(t.buySingle) buySingle from")
			.append(" ( SELECT i.id,i.push_count pushCount,")
			.append("i.reach,i.click_number clickNumber,")
//			.append("(SELECT count(1) from t_sms_send s1 where s1.send_info_id = i.id and s1.receive_status='1' and s1.status = 'A') reach,")
//			.append("(SELECT count(1) from t_sms_send s2 where s2.send_info_id = i.id and s2.is_click='1' and s2.status = 'A') clickNumber,")
			.append(" i.buy_single buySingle FROM t_sms_send_info i ")
			.append(" WHERE i.retailer_id = '").append(retailerId).append("' AND i.send_status = '").append(TSmsSendInfoEntity.SEND_STATUS_4)
			.append("' AND i.STATUS = 'A' ) t");
		
		Map<String, Object> countMap = systemService.findOneForJdbc(sql.toString(), null);
		
		//组装要返回的数据
		getReportListMap(lt, countMap);

		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(lt);
		j.setSuccess(true);

		return j;
	}



	private void getReportListMap(List<Map<String, Object>> lt, Map<String, Object> countMap) {
		Map<String, Object> map;
		if(countMap.size() > 0){
			map = new LinkedHashMap<String, Object>();
			Double  percentage = 0.0;
			map.put("value",100);
			map.put("name",  "发送数量:"+countMap.get("pushCount"));
			lt.add(map);
			int count = Integer.valueOf(countMap.get("pushCount")+"");
			//计算送达率
			map = new HashMap<String, Object>();
			if(count != 0){
				percentage = Double.valueOf(countMap.get("reach")+"")/count;
			}
			map.put("value",percentage*100);
			map.put("name",  "送达人数:"+countMap.get("reach") + " (送达率：" + String.format("%.2f", percentage*100) + "% )");
			lt.add(map);
			//计算点击率
			map = new HashMap<String, Object>();
			if(count != 0){
				percentage = Double.valueOf(countMap.get("clickNumber")+"")/count;
			}else{
				percentage = 0.0;
			}
			map.put("value",percentage*100);
			map.put("name",  "点击人数:"+countMap.get("clickNumber") + " (点击率：" + String.format("%.2f", percentage*100) + "% )");
			lt.add(map);
			//计算购买率
			map = new HashMap<String, Object>();
			if(count != 0){
				percentage = Double.valueOf(countMap.get("buySingle")+"")/count;
			}else{
				percentage = 0.0;
			}
			map.put("value",percentage*100);
			map.put("name",  "购买人数:"+countMap.get("buySingle") + " (购买率：" + String.format("%.2f", percentage*100) + "% )");
			lt.add(map);
		}else{
			map = new HashMap<String, Object>();
			map.put("value",0);
			map.put("name",  "发送数量:0");
			lt.add(map);
			map = new HashMap<String, Object>();
			map.put("value",0);
			map.put("name",  "送达人数:0");
			lt.add(map);
			map = new HashMap<String, Object>();
			map.put("value",0);
			map.put("name",  "点击人数:0");
			lt.add(map);
			map = new HashMap<String, Object>();
			map.put("value",0);
			map.put("name",  "购买人数:0");
			lt.add(map);
		}
	}
	
	
	/**
	 * hightchart导出图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "export")
	public void export(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String type = request.getParameter("type");
		String svg = request.getParameter("svg");
		String filename = request.getParameter("filename");

		filename = filename == null ? "chart" : filename;
		ServletOutputStream out = response.getOutputStream();
		try {
			if (null != type && null != svg) {
				svg = svg.replaceAll(":rect", "rect");
				String ext = "";
				Transcoder t = null;
				if (type.equals("image/png")) {
					ext = "png";
					t = new PNGTranscoder();
				} else if (type.equals("image/jpeg")) {
					ext = "jpg";
					t = new JPEGTranscoder();
				} else if (type.equals("application/pdf")) {
					ext = "pdf";
					t = (Transcoder) new PDFTranscoder();
				} else if (type.equals("image/svg+xml"))
					ext = "svg";
				response.addHeader("Content-Disposition",
						"attachment; filename=" + new String(filename.getBytes("GBK"),"ISO-8859-1") + "." + ext);
				response.addHeader("Content-Type", type);

				if (null != t) {
					TranscoderInput input = new TranscoderInput(
							new StringReader(svg));
					TranscoderOutput output = new TranscoderOutput(out);

					try {
						t.transcode(input, output);
					} catch (TranscoderException e) {
						out.print("Problem transcoding stream. See the web logs for more details.");
						e.printStackTrace();
					}
				} else if (ext.equals("svg")) {
					// out.print(svg);
					OutputStreamWriter writer = new OutputStreamWriter(out,
							"UTF-8");
					writer.append(svg);
					writer.close();
				} else
					out.print("Invalid type: " + type);
			} else {
				response.addHeader("Content-Type", "text/html");
				out
						.println("Usage:\n\tParameter [svg]: The DOM Element to be converted."
								+ "\n\tParameter [type]: The destination MIME type for the elment to be transcoded.");
			}
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	
	
	
	//过去一周短信转化情况
	@RequestMapping(params = "getSmsConversionReport")
	@ResponseBody
	public AjaxJson getSmsConversionReport (HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		Date date = new Date();
		String startDateStr = DateUtil.dateToString(DateUtil.addDay(date, -6),"yyyy-MM-dd");
		String endDateStr = DateUtil.dateToString(date,"yyyy-MM-dd");
		
		StringBuffer sql =  new StringBuffer();
		sql.append("SELECT SUM(t.pushCount) pushCount,SUM(t.reach) reach,SUM(t.clickNumber) clickNumber,SUM(t.buySingle) buySingle from")
		.append(" ( SELECT i.id,DATE_FORMAT(i.send_time, '%Y-%m-%d') sendTime,i.push_count pushCount,")
		.append("i.reach,i.click_number clickNumber,")
//		.append("(SELECT count(1) from t_sms_send s1 where s1.send_info_id = i.id and s1.receive_status='1' and s1.status = 'A') reach,")
//		.append("(SELECT count(1) from t_sms_send s2 where s2.send_info_id = i.id and s2.is_click='1' and s2.status = 'A') clickNumber,")
		.append(" i.buy_single buySingle FROM t_sms_send_info i ")
		.append(" WHERE i.retailer_id = '").append(retailerId).append("' AND i.send_status = '").append(TSmsSendInfoEntity.SEND_STATUS_4)
		.append("' AND i.send_time >= '").append(startDateStr).append(" 00:00:00' AND i.send_time <= '").append(endDateStr)
		.append(" 23:59:59' AND i.STATUS = 'A' ORDER BY i.send_time ASC) t");
		
		
		
		List<Map<String, Object>> lt = new ArrayList<Map<String, Object>>();
		Map<String, Object> map =null;
		Map<String, Object> countMap = systemService.findOneForJdbc(sql.toString(), null);
		//组装要返回的数据
		if(countMap.size() > 0 && countMap.get("pushCount") != null){
			map = new HashMap<String, Object>();
			Double  percentage = 0.0;
			map.put("value",100);
			map.put("name",  "发送数量:"+countMap.get("pushCount"));
			lt.add(map);
			//计算送达率
			int count = Integer.valueOf(countMap.get("pushCount")+"");
			map = new HashMap<String, Object>();
			if(count != 0){
				percentage = Double.valueOf(countMap.get("reach")+"")/count;
			}
			map.put("value",percentage*100);
			map.put("name",  "送达人数:"+countMap.get("reach") + " (送达率：" + String.format("%.2f", percentage*100) + "% )");
			lt.add(map);
			//计算点击率
			map = new HashMap<String, Object>();
			if(count != 0){
				percentage = Double.valueOf(countMap.get("clickNumber")+"")/count;
			}else{
				percentage = 0.0;
			}
			map.put("value",percentage*100);
			map.put("name",  "点击人数:"+countMap.get("clickNumber") + " (点击率：" + String.format("%.2f", percentage*100) + "% )");
			lt.add(map);
		}else{
			map = new HashMap<String, Object>();
			map.put("value",0);
			map.put("name",  "发送数量:0");
			lt.add(map);
			
			map = new HashMap<String, Object>();
			map.put("value",0);
			map.put("name",  "送达人数:0");
			lt.add(map);
			
			map = new HashMap<String, Object>();
			map.put("value",0);
			map.put("name",  "点击人数:0");
			lt.add(map);
		}

		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(lt);
		j.setSuccess(true);

		return j;
	}
	
	//过去一周短信送达情况
	@RequestMapping(params = "getSmsSendReport")
	@ResponseBody
	public AjaxJson getSmsSendReport (HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		List<String> dateList = new ArrayList<String>();
		Date date = new Date();
		String startDateStr = DateUtil.dateToString(DateUtil.addDay(date, -6),"yyyy-MM-dd");
		String endDateStr = DateUtil.dateToString(date,"yyyy-MM-dd");
		for(int i=0;i>-7;i--){
			dateList.add(DateUtil.dateToString(DateUtil.addDay(date, i),"yyyy-MM-dd"));
		}
		StringBuffer sql =  new StringBuffer();
		sql.append("SELECT t.sendTime,SUM(t.pushCount) pushCount,SUM(t.reach) reach,SUM(t.clickNumber) clickNumber,SUM(t.buySingle) buySingle from")
		.append(" ( SELECT i.id,DATE_FORMAT(i.send_time, '%Y-%m-%d') sendTime,i.push_count pushCount,")
		.append("i.reach,i.click_number clickNumber,")
//		.append("(SELECT count(1) from t_sms_send s1 where s1.send_info_id = i.id and s1.receive_status='1' and s1.status = 'A') reach,")
//		.append("(SELECT count(1) from t_sms_send s2 where s2.send_info_id = i.id and s2.is_click='1' and s2.status = 'A') clickNumber,")
		.append(" i.buy_single buySingle FROM t_sms_send_info i ")
		.append(" WHERE i.retailer_id = '").append(retailerId).append("' AND i.send_status = '").append(TSmsSendInfoEntity.SEND_STATUS_4)
		.append("' AND i.send_time >= '").append(startDateStr).append(" 00:00:00' AND i.send_time <= '").append(endDateStr)
		.append(" 23:59:59' AND i.STATUS = 'A' ORDER BY i.send_time ASC)")
		.append(" t GROUP BY t.sendTime");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> countMap = systemService.findForJdbc(sql.toString(), null);

		List<Object> pushCountList = new ArrayList<Object>();  //推送数
		List<Object> reachList = new ArrayList<Object>();  //送达数
		List<Object> clickNumberList = new ArrayList<Object>();  //点击数
		List<Object> buySingleList = new ArrayList<Object>();  //购买数
		//组装返回的数据
		for (String dateStr : dateList) {
			Object count = 0;
			Object reach = 0;
			Object click = 0;
			Object buy = 0;
			for (Map<String, Object> countmap : countMap) {
				if(dateStr.equals(countmap.get("sendTime"))){
					count = countmap.get("pushCount") == null ? 0 :countmap.get("pushCount");
					reach = countmap.get("reach") == null ? 0 :countmap.get("reach");
					click = countmap.get("clickNumber") == null ? 0 :countmap.get("clickNumber");
					buy = countmap.get("buySingle") == null ? 0 :countmap.get("buySingle");
				}
			}
			pushCountList.add(count);
			reachList.add(reach);
			clickNumberList.add(click);
			buySingleList.add(buy);
		}
		
		resultMap.put("time", dateList);
		resultMap.put("pushCount", pushCountList);
		resultMap.put("reach", reachList);

		AjaxJson j = new AjaxJson();
//		j.setMsg("success");
		j.setObj(resultMap);
//		j.setSuccess(true);

		return j;
	}

	
	//顾客资料完整度
	@RequestMapping(params = "getIntegrityReport")
	@ResponseBody
	public AjaxJson getIntegrityReport (HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql =  new StringBuffer();
		sql.append("select count(name) userName, ");
		sql.append(" count(sex) sex, ");
		sql.append(" count(birthday_rank) birthdayRank, ");
		sql.append(" count(phone_no) phoneNo, ");
		sql.append(" count(vip_level_id) vipLevel, ");
		sql.append(" count(birthday) birthday, ");
		sql.append(" count(constellation) constellation, ");
		sql.append(" sum(case when type=3 then 1 else 0 end) buyCount ");
		sql.append(" from  t_focus_customer ");
		sql.append(" where to_retailer_id = '").append(retailerId);
		sql.append("' and status = 'A' ");
		
		List<Object> list= new ArrayList<Object>();
		Map<String, Object> countMap = systemService.findOneForJdbc(sql.toString(), null);
		list.add(countMap.get("userName"));
		list.add(countMap.get("sex"));
		list.add(countMap.get("birthdayRank"));
		list.add(countMap.get("phoneNo"));
		list.add(countMap.get("vipLevel"));
		list.add(countMap.get("birthday"));
		list.add(countMap.get("constellation"));
		list.add(countMap.get("buyCount"));
		//计算顾客资料的完整度 每个人资料 各项总和
		double count = Integer.parseInt(countMap.get("userName").toString())+
		Integer.parseInt(countMap.get("sex").toString())+
		Integer.parseInt(countMap.get("birthdayRank").toString())+
		Integer.parseInt(countMap.get("phoneNo").toString())+
		Integer.parseInt(countMap.get("vipLevel").toString())+
		Integer.parseInt(countMap.get("birthday").toString())+
		Integer.parseInt(countMap.get("constellation").toString())+
		Integer.parseInt((Utility.isEmpty(countMap.get("buyCount"))?0:countMap.get("buyCount")).toString());
		//计算顾客资料的完整度 每个人资料 各项总和/总人数的项目数
		double completeness = (count*100) / (8*Integer.parseInt(countMap.get("phoneNo").toString()));
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");  
		//封装返回的数据
		Map<String, Object> attributes =  new HashMap<String, Object>();
		attributes.put("completeness", df.format(completeness));
		attributes.put("maxNumber", countMap.get("phoneNo"));
		
		AjaxJson j = new AjaxJson();
		j.setAttributes(attributes);
		j.setMsg("success");
		j.setObj(list);
		j.setSuccess(true);

		return j;
	}
	
	//顾客资料--性别比例
	@RequestMapping(params = "getSexChartReport")
	@ResponseBody
	public AjaxJson getSexChartReport (HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql =  new StringBuffer();
		sql.append("select sex ,count(*) sexCount from t_focus_customer where to_retailer_id = '").append(retailerId);
		sql.append("' and status = 'A' group by sex ");
		List<Map<String, Object>> countMap = systemService.findForJdbc(sql.toString(), null);

		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		List<Object> sexList= new ArrayList<Object>();
		sexList.add("男");
		sexList.add("女");
		sexList.add("未知");
		Object count0 = 0;
		Object count1 = 0;
		Object count = 0;
		List<Object> numList= new ArrayList<Object>();
		//统计数据
		for (Map<String, Object> map : countMap) {
			if("0".equals(map.get("sex"))){
				count0 = map.get("sexCount");
			}else if("1".equals(map.get("sex"))){
				count1 = map.get("sexCount");
			}else{
				count = Integer.parseInt(count.toString()) + Integer.parseInt(map.get("sexCount").toString());
			}
		}
		numList.add(count0);
		numList.add(count1);
		numList.add(count);
		resultMap.put("sexList", sexList);
		resultMap.put("numList", numList);
		
		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(resultMap);
		j.setSuccess(true);

		return j;
	}
	
	//顾客资料--手机号码所属运营商
	@RequestMapping(params = "getOperatorChartReport")
	@ResponseBody
	public AjaxJson getOperatorChartReport (HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();

		StringBuffer sql =  new StringBuffer();
		sql.append("select company, count(*) number from t_focus_customer where to_retailer_id = '").append(retailerId);
		sql.append("' and status = 'A' group by company");
		List<Map<String, Object>> countMap = systemService.findForJdbc(sql.toString(), null);
		//统计的项目
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		List<Object> operatorList= new ArrayList<Object>();
		operatorList.add("移动");
		operatorList.add("联通");
		operatorList.add("电信");
//		operatorList.add("其他");
		//统计数据
		int maxNumber = 0;
		for(int i = 0; i < operatorList.size(); i++){
			int count = 0;
			Map<String, Object> map0 = new LinkedHashMap<String, Object>();
			map0.put("name", operatorList.get(i));
			for (Map<String, Object> map : countMap) {
				if(map.get("company") == null || "".equals(map.get("company"))){
					if( i == 3){
						count = count + Integer.parseInt(map.get("number")+"");
					}
				}else if(("中国"+operatorList.get(i)).equals(map.get("company").toString())){
					count = Integer.parseInt(map.get("number")+"");
				}
			}
			map0.put("value", count);
			if(maxNumber < count){
				maxNumber = count;
			}
			listMap.add(map0);
		}
		
		//封装返回的数据
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("operatorList", operatorList);
		resultMap.put("contentMap", listMap);
		resultMap.put("maxNumber", maxNumber);
		
		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(resultMap);
		j.setSuccess(true);

		return j;
	}
	

	//顾客资料--年龄段
	@RequestMapping(params = "getAgeChartReport")
	@ResponseBody
	public AjaxJson getAgeChartReport(HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql =  new StringBuffer();
		sql.append("select birthday_rank, count(*) number from t_focus_customer where to_retailer_id = '").append(retailerId);
		sql.append("' and status = 'A' group by birthday_rank ");
		List<Map<String, Object>> countMap = systemService.findForJdbc(sql.toString(), null);
		//数据的统计项
		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		List<Object> ageList= new ArrayList<Object>();
		ageList.add("50后");
		ageList.add("60后");
		ageList.add("70后");
		ageList.add("80后");
		ageList.add("90后");
		ageList.add("00后");
		ageList.add("其他");
		List<Object> numList= new ArrayList<Object>();
		//统计各项数据
		for(int i = 0; i < 7; i++){
			int count = 0;
			for (Map<String, Object> map : countMap) {
				if(i == 0){
					map.put("istrue", "false");  //设置标记量
				}
				if(map.get("birthday_rank") == null || "".equals(map.get("birthday_rank"))){
					if( i == 6){
						count = count + Integer.parseInt(map.get("number")+"");
					}
				}else if((ageList.get(i)).equals(map.get("birthday_rank").toString())){
					count = Integer.parseInt(map.get("number")+"");
					map.put("istrue", "true");
				}else{
					if( i == 6 && "false".equals(map.get("istrue"))){ //根据标记量的值判断是否添加到 ‘其他’
						count = count + Integer.parseInt(map.get("number")+"");
					}
				}
			}
			numList.add(count);
		}
		resultMap.put("ageList", ageList);
		resultMap.put("numList", numList);
		
		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(resultMap);
		j.setSuccess(true);

		return j;
	}
	

	//顾客生日分布
	@RequestMapping(params = "getBirthdayReport")
	@ResponseBody
	public AjaxJson getBirthdayReport(HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql =  new StringBuffer();
		sql.append("select month(birthday) month, count(*) number from t_focus_customer where to_retailer_id = '").append(retailerId);
		sql.append("' and status = 'A' group by month(birthday) ");
		List<Map<String, Object>> countMap = systemService.findForJdbc(sql.toString(), null);

		List<Object> resultMap = new ArrayList<Object>();
		//统计12个月中的人数
		for(int i = 1; i < 13; i++){
			Object count = 0;
			for (Map<String, Object> map : countMap) {
				if(map.get("month") == null){
				}else if((i+"").equals(map.get("month").toString())){
					count = map.get("number");
				}
			}
			resultMap.add(count);
		}
		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(resultMap);
		j.setSuccess(true);

		return j;
	}
	
	//顾客星座分布
	@RequestMapping(params = "getConstellationChartReport")
	@ResponseBody
	public AjaxJson getConstellationChartReport(HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		
		StringBuffer sql =  new StringBuffer();
		sql.append("select constellation, count(*) number from t_focus_customer where to_retailer_id = '").append(retailerId);
		sql.append("' and status = 'A' group by constellation ");
		List<Map<String, Object>> countMap = systemService.findForJdbc(sql.toString(), null);

		List<Object> resultList = new ArrayList<Object>();
		String[] strs = {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
		//统计顾客的星座
		for(int i = 0; i < 12; i++){
			Object count = 0;
			for (Map<String, Object> map : countMap) {
				if(map.get("constellation") == null){
					
				}else if(strs[i].equals(map.get("constellation").toString())){
					count = map.get("number");
				}
			}
			resultList.add(count);
		}
		
		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(resultList);
		j.setSuccess(true);

		return j;
	}
	//顾客地区分布
	@RequestMapping(params = "getRegionChartReport")
	@ResponseBody
	public AjaxJson getRegionChartReport(HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		
		StringBuffer sql =  new StringBuffer();
		sql.append("select province, count(*) number from t_focus_customer where to_retailer_id = '").append(retailerId);
		sql.append("' and status = 'A' group by province ");
		List<Map<String, Object>> countMap = systemService.findForJdbc(sql.toString(), null);

		List<Object> resultList = new ArrayList<Object>();
		String[] strs = {"北京","天津","上海","重庆","河北","山西","辽宁","吉林","黑龙江","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东","海南","四川","贵州","云南","陕西","甘肃","青海","西藏","广西","内蒙古","宁夏","新疆","香港","澳门","台湾"};
		
		//统计顾客的地区
		for(int i = 0; i < strs.length; i++){
			Object count = 0;
			Map<String, Object> map0 = new LinkedHashMap<String, Object>();
			map0.put("name", strs[i]);
			for (Map<String, Object> map : countMap) {
				if(map.get("province") == null){
				}else if(strs[i].equals(map.get("province").toString())){
					count = map.get("number");
				}
			}
			map0.put("value", count);
			resultList.add(map0);
		}
		
		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(resultList);
		j.setSuccess(true);

		return j;
	}

	
	//顾客资料--vip等级比例
	@RequestMapping(params = "getVipChartReport")
	@ResponseBody
	public AjaxJson getVipChartReport (HttpServletRequest request, HttpServletResponse response) {
		String retailerId = ResourceUtil.getRetailerId();
		StringBuffer sql =  new StringBuffer();
		//获取顾客等级数据
		sql.append("select vip_level ,count(*) number from t_focus_customer where to_retailer_id = '").append(retailerId);
		sql.append("' and status = 'A' group by vip_level ");
		List<Map<String, Object>> countMap = systemService.findForJdbc(sql.toString(), null);
		//获取顾客等级名称
		String vipSql = "select vip_name from t_vip_level where retailer_id = '"+retailerId+"' and status = 'A' order by discount ";
		List<Map<String, Object>> vipMap = systemService.findForJdbc(vipSql.toString(), null);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Object> vipList= new ArrayList<Object>();
		for (Map<String, Object> map : vipMap) {
			vipList.add(map.get("vip_name"));
		}
		List<Object> mapList= new ArrayList<Object>();
		//统计数据
		int maxNumber = 0;
		for(int i = 0; i < vipList.size(); i++){
			int count = 0;
			Map<String, Object> map0 = new LinkedHashMap<String, Object>();
			map0.put("name", vipList.get(i));
			for (Map<String, Object> map : countMap) {
				if(map.get("vip_level") == null || "".equals(map.get("vip_level"))){
				}else if((vipList.get(i)).equals(map.get("vip_level").toString())){
					count = Integer.parseInt(map.get("number")+"");
				}
			}
			map0.put("value", count);
			if(maxNumber < count){
				maxNumber = count;
			}
			mapList.add(map0);
		}
		resultMap.put("vipList", vipList);
		resultMap.put("mapList", mapList);
		resultMap.put("maxNumber", maxNumber);
		
		AjaxJson j = new AjaxJson();
		j.setMsg("success");
		j.setObj(resultMap);
		j.setSuccess(true);
		
		return j;
	}
}
