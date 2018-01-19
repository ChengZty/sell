<%@ page language="java" import="java.util.*,org.jeecgframework.core.util.LogUtil" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<body>
<%
//微信授权回调页面（该页面会带有微信返回的code和state字段）
try{
	String REQUEST_URL = application.getAttribute("REQUEST_URL")+"";
// 	System.out.println("REQUEST_URL1====="+REQUEST_URL);
	if(REQUEST_URL==null||REQUEST_URL.equalsIgnoreCase("null")||REQUEST_URL.equals("")){
		ResourceBundle env = ResourceBundle.getBundle("env");
		REQUEST_URL = env.getObject("REQUEST_URL")+"";//导购端域名端口
		application.setAttribute("REQUEST_URL",REQUEST_URL);
// 		System.out.println("REQUEST_URL2====="+REQUEST_URL);
	}
	String type = request.getParameter("type");
	String  method = null;
	if("1".equals(type)){//商品H5快速购买页面
		method = "tGoodsController.do?goodsViewDetailByWx&";
	}else if("2".equals(type)){//订单分享H5页面
		method = "tOrderInfoController.do?orderToPayByWx&";
	}else if("3".equals(type)){//话题分享H5页面
		method = "tNewsController.do?viewNewsContent&";
	}else if("4".equals(type)){//直播H5页面
		method = "tNewsController.do?viewLive&";
	}
	String paramString = request.getQueryString();
	if(method!=null){
		String redirectUrl = REQUEST_URL+method+paramString;
// 		System.out.println(redirectUrl);
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.sendRedirect(redirectUrl);
	}else{
		LogUtil.info("微信授权回调页面参数============="+paramString);
	}
// 	request.setAttribute("redirectUrl",redirectUrl);
// 	System.out.print(redirectUrl);
// response.setHeader("Location", redirectUrl);  
	
}catch(Exception e){
	LogUtil.info(e);
	e.printStackTrace();
}

%>
<script type="text/javascript">
// location.href = "${redirectUrl}";
</script>
</body>
</html>
