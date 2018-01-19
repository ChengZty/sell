<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>g+后台管理系统</title>
<meta charset="UTF-8" />
	<script>
		//判断手机浏览器平台，如果是手机则跳转到手机页面
		var client=function(){var e={ie:0,gecko:0,webkit:0,khtml:0,opera:0,ver:null},i={ie:0,firefox:0,safari:0,konq:0,opera:0,chrome:0,ver:null},r={win:!1,mac:!1,x11:!1,iphone:!1,ipod:!1,ipad:!1,ios:!1,android:!1,nokiaN:!1,winMobile:!1,wii:!1,ps:!1},a=navigator.userAgent;if(window.opera)e.ver=i.ver=window.opera.version(),e.opera=i.opera=parseFloat(e.ver);else if(/AppleWebKit\/(\S+)/.test(a))if(e.ver=RegExp.$1,e.webkit=parseFloat(e.ver),/Chrome\/(\S+)/.test(a))i.ver=RegExp.$1,i.chrome=parseFloat(i.ver);else if(/Version\/(\S+)/.test(a))i.ver=RegExp.$1,i.safari=parseFloat(i.ver);else{var o=1;o=e.webkit<100?1:e.webkit<312?1.2:e.webkit<412?1.3:2,i.safari=i.ver=o}else/KHTML\/(\S+)/.test(a)||/Konqueror\/([^;]+)/.test(a)?(e.ver=i.ver=RegExp.$1,e.khtml=i.konq=parseFloat(e.ver)):/rv:([^\)]+)\) Gecko\/\d{8}/.test(a)?(e.ver=RegExp.$1,e.gecko=parseFloat(e.ver),/Firefox\/(\S+)/.test(a)&&(i.ver=RegExp.$1,i.firefox=parseFloat(i.ver))):/MSIE ([^;]+)/.test(a)&&(e.ver=i.ver=RegExp.$1,e.ie=i.ie=parseFloat(e.ver));i.ie=e.ie,i.opera=e.opera;var n=navigator.platform;if(r.win=0==n.indexOf("Win"),r.mac=0==n.indexOf("Mac"),r.x11="X11"==n||0==n.indexOf("Linux"),r.win&&/Win(?:dows )?([^do]{2})\s?(\d+\.\d+)?/.test(a))if("NT"==RegExp.$1)switch(RegExp.$2){case"5.0":r.win="2000";break;case"5.1":r.win="XP";break;case"6.0":r.win="Vista";break;case"6.1":r.win="7";break;default:r.win="NT"}else"9x"==RegExp.$1?r.win="ME":r.win=RegExp.$1;return r.iphone=a.indexOf("iPhone")>-1,r.ipod=a.indexOf("iPod")>-1,r.ipad=a.indexOf("iPad")>-1,r.nokiaN=a.indexOf("NokiaN")>-1,"CE"==r.win?r.winMobile=r.win:"Ph"==r.win&&/Windows Phone OS (\d+.\d+)/.test(a)&&(r.win="Phone",r.winMobile=parseFloat(RegExp.$1)),r.mac&&a.indexOf("Mobile")>-1&&(/CPU (?:iPhone )?OS (\d+_\d+)/.test(a)?r.ios=parseFloat(RegExp.$1.replace("_",".")):r.ios=2),/Android (\d+\.\d+)/.test(a)&&(r.android=parseFloat(RegExp.$1)),r.wii=a.indexOf("Wii")>-1,r.ps=/playstation/i.test(a),{engine:e,browser:i,system:r}}();
		//移动设备
		var flag = client.system.iphone ||
				   client.system.ipod ||
				   client.system.ipad ||
				   client.system.ios ||
				   client.system.android ||
				   client.system.nokiaN ||
				   client.system.winMobile;
				   
		if(flag){
			window.location = 'loginController.do?mobileFirstPage';
		}
	</script>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="author" content="观潮汇数据科技">
    <meta name="description" content="观潮汇管家系统，短信制作，商品SHOW" />
    <meta name="Keywords" content="观潮汇管家系统，短信制作，商品资讯，图文制作，内容排版，大数据处理" />

    <link rel="stylesheet" href="webpage/daggerSrc/common/css/reset.css" />
    <link rel="stylesheet" href="webpage/daggerSrc/pcHomePage/css/pcHomePage.css?v=1.0.5" />

</head>
<body class="">

	<div class="container">
		<div class="login_content">
			<div class="desc_box">
				<img class="logo_img" src="plug-in/ace/img/login1_logo.png" alt="">
				<p class="desc_01">以业绩目标为导向的</p>
				<p class="desc_02">新零售主动营销工具</p>
			</div>
			<div class="measure_top login_div" style="min-height:374px;">
				<div class="login_box">
					<input class="login_name" type="text" name="userName" id="userName" placeholder="用户名">
					<input class="login_password" type="password" name="password" id="password" placeholder="密码">
					<div class="code_div">
						<input class="verify_code" type="text" name="randCode" id="randCode" placeholder="输入验证码">
						<img class="verify_code_img" id="randCodeImage" alt="" title="" src="" >
					</div>
					<button class="btn_affirm" id="to-recover" onclick="severCheck();">确认</button>
				</div>
			</div>
			<div class="controls">
				<div class="main_input_box">
					<font><span id="nameerr" style="color: #fff;">Copyright ©2016 guanchaohui. All rights reserved. </span></font>
					<a href="http://www.miitbeian.gov.cn/" target=_blank><font ><b style="color: #fff;">粤ICP备16035732号-1</b></font></a>
				</div>
			</div>
		</div>
		<div class="dots_content">
			<canvas class="dots_canvas"></canvas>
		</div>
	</div>
	
	
    <script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<!-- <script src="plug-in/dots/dots.js"></script> -->
	<!-- <script src="plug-in/dots/main.js"></script> -->
	<script type="text/javascript" src="plug-in/ace/login/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="plug-in/ace/login/js/jquery.tips.js"></script>
	<script type="text/javascript">
	
		//服务器校验
		function severCheck(){
			if(check()){
				
				var userName = $("#userName").val();
				var password = $("#password").val();
				var code = "qq987654321gch"+userName+",gch,"+password+"QQ123456789gch"+",gch,"+$("#randCode").val();
				$.ajax({
					type: "POST",
					url: 'loginController.do?checkuser',
			    	data: {KEYDATA:code,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if(data.success){
							saveCookie();
							window.location.href="loginController.do?login1";
						}else if("usererror" == data.obj){
							$("#userName").tips({
								side : 1,
								msg : data.msg,
								bg : '#FF5080',
								time : 15
							});
							$("#userName").focus();
						}else if("codeerror" == data.obj){
							$("#randCode").tips({
								side : 1,
								msg : data.msg,
								bg : '#FF5080',
								time : 15
							});
							$("#randCode").focus();
						}else{
							$("#userName").tips({
								side : 1,
								msg : "缺少参数",
								bg : '#FF5080',
								time : 15
							});
							$("#userName").focus();
						}
					}
				});
			}
		}
	
		$(document).ready(function() {
			
			changeCode();
			$("#randCodeImage").bind("click", changeCode);
		});

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode() {
			$("#randCodeImage").attr("src", 'randCodeImage?a=' + genTimestamp()+'&loginType=1');//loginType为1代表尖刀产品
		}

		//客户端校验
		function check() {

			if ($("#userName").val() == "") {

				$("#userName").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#userName").focus();
				return false;
			} else {
				$("#userName").val(jQuery.trim($('#userName').val()));
			}

			if ($("#password").val() == "") {

				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#password").focus();
				return false;
			}
			if ($("#randCode").val() == "") {

				$("#randCode").tips({
					side : 1,
					msg : '验证码不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#randCode").focus();
				return false;
			}

			$("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 10
			});

			return true;
		}

		function saveUserName() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('userName', '', {
					expires : -1
				});
				$("#userName").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('userName', $("#userName").val(), {
					expires : 7
				});
			}
		}
		function quxiao() {
			$("#userName").val('');
			$("#password").val('');
		}
		
		jQuery(function() {
			var userName = $.cookie('userName');
			if (typeof(userName) != "undefined") {
				$("#userName").val(userName);
				$("#saveid").attr("checked", true);
				$("#password").focus();
			}
		});
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页 
		if (window != top) {
			top.location.href = "loginController.do?login1";
		}
	</script>
</body>

</html>