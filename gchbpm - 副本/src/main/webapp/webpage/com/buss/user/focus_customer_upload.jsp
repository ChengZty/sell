<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>通用Excel导入${controller_name}</title>
<t:base type="jquery,easyui,tools"></t:base>
<style type="text/css">
#shadow_div{
	display:none;
/*  	background: rgba(128,128,128,0.5);  */
	background:white;
	height:100%;
	width: 100%;
	z-index:100;
	position: fixed;
	top: 0;
	left: 0;
	text-align: center;
}
</style>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
	<fieldset class="step">
	<div class="form"><t:upload name="fiels" buttonText="选择要导入的文件" uploader="${controller_name}.do?importExcel&extraParam=${extraParam }" extend="*.xls;*.xlsx" id="file_upload" formData="documentTitle" onUploadSuccess="updateEmptyPhoneArea"></t:upload></div>
	<div class="form" id="filediv" style="height: 50px">
	</div>
	</fieldset>
</t:formvalid>
<div id="shadow_div">
	<img alt="" src="plug-in/uploadify/img/loading.gif" height="100%"/>
</div>
</body>
<script type="text/javascript">
//上传完成后调用的方法
//更新手机归属地
function updateEmptyPhoneArea(data) {
	console.log(data.obj);
	var key = data.obj;
	if(key!=null){
		//下载错误的记录
		var win = frameElement.api.opener;
		if (data.success == true) {
// 			win.tip(data.msg);
			win.ExportXlsxByFailList(data.obj);
		}
	}
// 	parent.frames[1].updateEmptyPhoneArea();
	$.ajax({
		url : "tFocusCustomerController.do?updateEmptyPhoneArea",
		type : 'post',
		cache : false,
		async: false,//使用同步的方式,true为异步方式
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				console.log("更新手机归属地");
			}
		}
	})
}



</script>
</html>
