<%@ page contentType="text/html;charset=utf-8" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<meta charset="UTF-8">
	<title>商城首页</title>
	<script src="webpage/daggerSrc/common/js/flexible.min.js"></script>
	<meta charset="utf-8"/>
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no,email=no,adress=no">

	<link rel="stylesheet" type="text/css" href="webpage/daggerSrc/common/css/base.css">
	<link rel="stylesheet" href="plug-in/slick/slick.css" />
    <link rel="stylesheet" href="plug-in/slick/slick-theme.css" />
	<link rel="stylesheet" type="text/css" href="webpage/daggerSrc/mobileShopHome/css/mobileShopHome.css?v=1.0.3">
	<script type="text/javascript">
		var posterCount = "${posterCount}";//资讯总数
		var goodsCount = "${goodsCount}";//商品总数
		var rId = "${rId}";//零售商ID
	</script>
</head>
<body>
	<div id="content">
		${content}
	</div>

	<!-- <div class="btn_goto_center"></div> -->

	<script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
	<script src="http://cdn.bootcss.com/fastclick/1.0.6/fastclick.min.js"></script>
	<script src="http://cdn.bootcss.com/jquery_lazyload/1.9.7/jquery.lazyload.min.js"></script>
	<script src="plug-in/slick/slick.min.js"></script>
    <script src="plug-in/iscroll/iscroll.min.js"></script>

	<script src="webpage/daggerSrc/mobileShopHome/js/mobileShopHome.js?v=1.0.3"></script>
</body>
</html>
