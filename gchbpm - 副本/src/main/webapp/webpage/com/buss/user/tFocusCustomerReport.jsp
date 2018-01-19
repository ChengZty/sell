<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>顾客分析</title>
    <t:base type="jquery"></t:base>
    <style type="text/css">
		.main
    	{
    		height:400px;border:1px solid #ccc;padding:0.5%;width: 48%;display: inline-block;margin-top: 0.5%;
    	}
    </style>
</head>
<script type="text/javascript">
<!--
function backList() {
	document.location="tFocusCustomerController.do?${page}";
}
//-->
</script>
<body >
	<div   style="height:50px;"  >
		<input type="button" onclick="backList()" value="返回">
	</div>
	 <div>
		  <div id="integrity_div"  class="main"></div>
		 <div id="sex_div"   class="main"></div>
		 <div id="age_div"   class="main" ></div>
<!-- 		 <div id="vip_div"   class="main"></div> -->
		 <div id="operator_div" class="main" ></div>
		 <div id="birthday_div" class="main" ></div>
		 <div id="constellation_div" class="main"></div>
		 <div id="region_div" class="main"></div>
	 </div>
	 <div    >
	 	<input type="button" onclick="backList()" value="返回">
	 </div>
    <!-- ECharts单文件引入 -->
    <script src="plug-in/echarts/dist/echarts.js"></script>
    <script src="plug-in/echarts/theme/macarons.js"></script>
    <script src = "webpage/main/report.js"></script>		
    <script src = "webpage/com/buss/user/tFocusCustomerReport.js?v=1.01"></script>		
</body>