<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>g+后台管理系统</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" href="plug-in/ace/login/bootstrap.min.css" />
<link rel="stylesheet" href="plug-in/ace/login/css/camera.css" />
<link rel="stylesheet" href="plug-in/ace/login/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="plug-in/ace/login/matrix-login.css" />
<link href="plug-in/ace/login/font-awesome.css" rel="stylesheet" />
<script type="text/javascript" src="plug-in/ace/login/js/jquery-1.5.1.min.js"></script>

</head>
<body>

	<div
		style="width:100%;text-align: center;margin: 0 auto;position: absolute;">
		<div id="loginbox">
			<form action="" method="post" name="loginForm" id="loginForm">
				<div class="control-group normal_text">
					<h3>
						<img src="plug-in/ace/login/logo.png" alt="Logo" />
					</h3>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_lg">
							<i><img height="37" src="plug-in/ace/login/user.png" /></i>
							</span><input type="text" name="userName" id="userName" value="" placeholder="请输入用户名" />
						</div>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<div class="main_input_box">
							<span class="add-on bg_ly">
							<i><img height="37" src="plug-in/ace/login/suo.png" /></i>
							</span><input type="password" name="password" id="password" placeholder="请输入密码" value="" />
						</div>
					</div>
				</div>
				<div style="float:right;padding-right:10%;">
					<div style="float: left;margin-top:3px;margin-right:2px;">
						<font color="white">记住用户名</font>
					</div>
					<div style="float: left;">
						<input name="form-field-checkbox" id="saveid" type="checkbox"
							onclick="saveUserName();" style="padding-top:0px;" />
					</div>
				</div>
				<div class="form-actions">
					<div style="width:86%;padding-left:8%;">

						<div style="float: left;">
							<i><img src="plug-in/ace/login/yan.png" /></i>
						</div>
						<div style="float: left;" class="codediv">
							<input type="text" name="randCode" id="randCode" class="login_code"
								style="height:16px; padding-top:0px;" />
						</div>
						<div style="float: left;">
							<i><img style="height:22px;" id="randCodeImage" alt="点击更换"
								title="点击更换" src="" /></i>
						</div>

						<span class="pull-right" style="padding-right:3%;"><a
							href="javascript:quxiao();" class="btn btn-success">取消</a></span> <span
							class="pull-right"><a onclick="severCheck();"
							class="flip-link btn btn-info" id="to-recover">登录</a></span>

					</div>
				</div>

			</form>


			<div class="controls">
				<div class="main_input_box">
					<font color="white"><span id="nameerr">Copyright ©2016 guanchaohui. All rights reserved. </span></font>
					<a href="http://www.miitbeian.gov.cn/" target=_blank><font ><b>粤ICP备16035732号-1</b></font></a>
				</div>
			</div>
		</div>
	</div>
	<div id="templatemo_banner_slide" class="container_wapper">
		<div class="camera_wrap camera_emboss" id="camera_slide">
			<div data-src="plug-in/ace/login/images/banner_slide_01.jpg"></div>
			<div data-src="plug-in/ace/login/images/banner_slide_02.jpg"></div>
			<div data-src="plug-in/ace/login/images/banner_slide_03.jpg"></div>
		</div>
		<!-- #camera_wrap_3 -->
	</div>

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
							window.location.href="loginController.do?login";
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
			$("#randCodeImage").attr("src", 'randCodeImage?a=' + genTimestamp());
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
			top.location.href = location.href;
		}
	</script>

	<script src="plug-in/ace/login/js/bootstrap.min.js"></script>
	<script src="plug-in/ace/login/js/jquery-1.7.2.js"></script>
	<script src="plug-in/ace/login/js/jquery.easing.1.3.js"></script>
	<script src="plug-in/ace/login/js/jquery.mobile.customized.min.js"></script>
	<script src="plug-in/ace/login/js/camera.min.js"></script>
	<script src="plug-in/ace/login/js/templatemo_script.js"></script>
	<script type="text/javascript" src="plug-in/ace/login/js/jquery.tips.js"></script>
	<script type="text/javascript" src="plug-in/ace/login/js/jquery.cookie.js"></script>
</body>

</html>