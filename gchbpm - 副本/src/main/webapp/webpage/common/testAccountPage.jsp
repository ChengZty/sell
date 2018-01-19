<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- 声明文档使用的字符编码 -->
  <meta charset='utf-8'>
 <!-- 为移动设备添加 viewport -->
 <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
 <style type="text/css">
 .layer{
 	margin: 2rem 1rem;
 }

 .text{
 	margin: 0.5rem;
 }
 .sp{
 	padding-right: 1rem;
 }
 
 #info_div{
 	display: none;
 }
 </style>
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="plug-in/jquery-plugs/cookie/jquery.cookie.js"></script>
<script type="text/javascript">
$(function(){
	var tPhone = $.cookie("tPhone");
	var tPwd = $.cookie("tPwd");
	if(tPhone){
		$("#phoneNo").text(tPhone);
		$("#pwd").text(tPwd);
		$("#info_div").show();
	}else{
		<c:if test="${resultMap.success == true }">
		getTestAccount();
		</c:if>
	}
})

//获取测试帐号
function getTestAccount(){
	var url = "tPersonController.do?getTestAccount&token=${token}";
	$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					$("#phoneNo").text(d.phoneNo);
					$("#pwd").text(d.pwd);
					$.cookie("tPhone",d.phoneNo,{expires:3});//3天有效期
					$.cookie("tPwd",d.pwd);
// 					$("#btn").hide();
					$("#info_div").show();
				}
			}
		});
}

	/* function getText(){
		// 动态创建 input 元素
		  var aux = document.createElement("input");
		  // 获得需要复制的内容
		  aux.setAttribute("value", document.getElementById("phoneNo").innerHTML);
		  // 添加到 DOM 元素中
		  document.body.appendChild(aux);
		  // 执行选中
		  // 注意: 只有 input 和 textarea 可以执行 select() 方法.
		  aux.select();
		  // 获得选中的内容
		    var content = window.getSelection().toString();
		  // 执行复制命令
		  document.execCommand("copy");
		  // 将 input 元素移除
		  document.body.removeChild(aux);
		  alert("复制成功");
	} */
</script>
<title>导购测试帐号获取页面</title>
</head>
<body>
<div class="layer">
	<c:if test="${resultMap.success == true }">
		<div class="text">测试帐号有效期为3天</div>
		<div id="info_div">
			<div class="text"><span class="sp">帐号</span><span id="phoneNo"></span>
	<!-- 			<input type="button" onclick="getText()" value="点击复制"/> -->
			</div>
			<div class="text"><span class="sp">密码</span><span id="pwd"></span></div>
		</div>
		<div><input type="button" id="btn" onclick="getTestAccount()" value="重新获取帐号"/></div>
	</c:if>
	<c:if test="${resultMap.success == false }">
		<div>${resultMap.msg }</div>
	</c:if>
</div>
</body>
</html>
