package com.buss.kuaidi.postOrder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buss.kuaidi.pojo.TaskResponse;
import com.buss.kuaidi.util.MD5;




/**   
 * @Title: Controller
 * @Description: 快递
 * @author onlineGenerator
 * @date 2016-03-31 15:29:52
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/postOrderController")
public class PostOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = Logger.getLogger(PostOrderController.class);

	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



	/**
	 * 订阅
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "postOrder")
	@ResponseBody
	public AjaxJson postOrder(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "订阅成功";
		try{
			com.buss.kuaidi.pojo.TaskRequest req = new com.buss.kuaidi.pojo.TaskRequest();
			req.setCompany("zhongtong");
			req.setFrom("上海浦东新区");
			req.setTo("广东深圳南山区");
			req.setNumber("768404530475");
//			req.getParameters().put("callbackurl", "http://120.76.182.98:8080/testCallback/callback");
			req.getParameters().put("callbackurl", ResourceUtil.getConfigByName("callbackurl"));
			req.setKey(ResourceUtil.getConfigByName("expressKey"));
			
			HashMap<String, String> p = new HashMap<String, String>(); 
			p.put("schema", "json");
			p.put("param", JacksonHelper.toJSON(req));
			try {
				String ret = HttpRequest.postData(ResourceUtil.getConfigByName("expressUrl"), p, "UTF-8");
				com.buss.kuaidi.pojo.TaskResponse resp = JacksonHelper.fromJSON(ret, com.buss.kuaidi.pojo.TaskResponse.class);
				if(resp.getResult()==true){
					//记录订阅日志
//					System.out.println("订阅成功");
				}else{
//					System.out.println("订阅失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "订阅失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 查询
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(params = "queryOrder")
	@ResponseBody
	public AjaxJson queryOrder(HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		message = "查询成功";
		try{
			String customer = ResourceUtil.getConfigByName("expressCustKey");
			String key = ResourceUtil.getConfigByName("expressKey");
//			String param = "{\"com\":\"shunfeng\",\"num\":\"664455288628\"}";
//			String param = "{\"com\":\"zhongtong\",\"num\":\"768404530475\",\"from\":\"邯郸馆陶县\",\"to\":\"深圳龙华\"}";
//			TSUser user = ResourceUtil.getSessionUserName();
			String com = request.getParameter("com");
			String num = request.getParameter("num");
//			String from = user.getArea();
//			String to = request.getParameter("to");
			String param = "{\"com\":\""+com+"\",\"num\":\""+num+"\"}";
	         //加密后的字符串(MD5(param+key+customer)签名，用于验证身份，按key+customer+para的顺序进行MD5加密)
	        String sign=MD5.encode(param+key+customer);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("param",param);
			params.put("sign",sign);
			params.put("customer",customer);
			String ret;
			try {
				ret = new HttpRequest().postData(ResourceUtil.getConfigByName("expressQUrl"), params, "utf-8").toString();
//				System.out.println(ret);
				TaskResponse resp = JacksonHelper.fromJSON(ret, TaskResponse.class);
				//错误信息格式{"result":false,"returnCode":"500","message":"查询无结果，请隔段时间再查"}
				if(resp.getResult()==null){
					message = ret;
				}else{//出错
					j.setSuccess(false);
					message = resp.getMessage();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			message = "查询失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

}
