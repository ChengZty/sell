<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
   <!--  <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
    <title>g+后台管理系统</title>
    <style type="text/css">
    	.main
    	{
    		height:400px;border:1px solid #ccc;padding:0.5%;width: 48%;display: inline-block;margin-top: 0.5%;
    	}
    </style>
    <script type="text/javascript">var ctx = '${webRoot}';</script>
</head>
<body>
	 <div>
	 	 <div id="sms_conversion_div"  class="main"></div>
	 	 <div id="sms_send_div"  class="main"></div>
		 <div id="integrity_div"  class="main"></div>
		 <div id="sex_div"   class="main"></div>
		 <div id="age_div"   class="main" ></div>
<!-- 		 <div id="vip_div"   class="main"></div> -->
		 <div id="operator_div" class="main" ></div>
		 <div id="birthday_div" class="main" ></div>
		 <div id="constellation_div" class="main"></div>
		 <div id="region_div" class="main"></div>
	 </div>
</body>
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
    <script src="plug-in/echarts/dist/echarts.js"></script>
    <script src = "webpage/main/report.js"></script>		
    <script src = "webpage/main/acehomeReport.js?v=1.00"></script>	

</html>