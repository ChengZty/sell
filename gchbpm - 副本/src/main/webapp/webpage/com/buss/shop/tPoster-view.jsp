<%@ page contentType="text/html;charset=utf-8" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<meta charset="UTF-8">
	<!--海报页面-->
	<title>${posterVo.title }</title>
	<script src="webpage/daggerSrc/common/js/flexible.min.js"></script>
	<meta charset="utf-8"/>
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no,email=no,adress=no">

	<link rel="stylesheet" type="text/css" href="webpage/daggerSrc/common/css/base.css">
	<link rel="stylesheet" href="plug-in/slick/slick.css" />
    <link rel="stylesheet" href="plug-in/slick/slick-theme.css" />
	<link rel="stylesheet" href="webpage/daggerSrc/mCustomScrollbar/css/jquery.mCustomScrollbar.min.css" />  
	<link rel="stylesheet" type="text/css" href="webpage/daggerSrc/contentPage/css/contentPage.css?v=1.0.5">
	 <script type="text/javascript">
		var domain = "";
		var sid = "";
		var rId = "";
	</script> 
</head>
<body>
	
	<!-- 点击预览 -->
	<div class="mobile_content">
		${content}
	</div>

	<!--点击进入首页-->
	<!-- <div class="btn_home_page"></div> -->

	<script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/fastclick/1.0.6/fastclick.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery_lazyload/1.9.7/jquery.lazyload.min.js"></script>
	<!--轮播-->
	<script src="plug-in/slick/slick.js"></script>
	<!--滚动条-->
	<script src="webpage/daggerSrc/mCustomScrollbar/js/jquery.mCustomScrollbar.concat.min.js"></script>
	
	<script src="webpage/daggerSrc/contentPage/js/contentPage.js?v=1.0.5"></script>
</body>
</html>