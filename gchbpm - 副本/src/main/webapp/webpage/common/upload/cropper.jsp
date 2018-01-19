<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="plug-in/cropper/css/cropper.min.css">
	<link rel="stylesheet" href="plug-in/cropper/css/cropper-customed.css">

	<script src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="plug-in/cropper/js/cropper.min.js"></script>
	
	<script>
		var qiniuDomain = '${domain}';  //七牛 域名，用于下载文件，拼接后，存入数据库
		var cropperWidth = '${cropperWidth}';	//裁剪宽度  默认100 
		cropperWidth = cropperWidth ? parseFloat(cropperWidth) : 100;
		var cropperHeight = '${cropperHeight}';	//裁剪高度  默认100
		cropperHeight = cropperHeight ? parseFloat(cropperHeight) : 100;
		var moduleName = '${moduleName}';	//模块名称
	</script>
</head>
<body>
	<!-- 图片剪切 -->
	<div class="cropper-box">
		<div class="avatar-wrapper">
			<img class="crop-img" src="">
		</div>
		<div class="avatar-upload">
			<input class="avatar-src" name="avatar_src" type="hidden">
			<input class="avatar-data" name="avatar_data" type="hidden">
			<div class="load_blue upload_pic">
				上传图片
			</div>
			<div class="load_blue">
				重选图片
				<input class="avatar-input imgInp" id="avatarInput" name="avatar_file" type="file">
			</div>
		</div>
		<div class="ui_loading_box">
			<div class="ui_loading"></div>
		</div>
	</div>

	<script src="plug-in/cropper/js/cropper-customed.js"></script>
</body>
</html>
