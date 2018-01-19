<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品表</title>
  <t:base type="jquery,easyui,tools"></t:base>
    <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
  $('#templatePic_main_cover').uploadify({buttonText:'浏览封面图',
	    	queueID:'progress_bar_m_samll',
	        progressData:'speed',
	        height:25,
	        overrideEvents:['onDialogClose'],
	        fileTypeDesc:'文件格式:',
	        fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
	        fileSizeLimit:'100KB',
	        swf:'plug-in/uploadify/uploadify.swf',
	        uploader:'tGoodsController.do?uploadSmallPic&sessionId=${pageContext.session.id}',
	        auto:true,
	        multi:true,
	        queueSizeLimit:5,
	        onUploadSuccess : function(file, data, response) {
	                var d=$.parseJSON(data);
	            if(d.success){
	                var div_imgs = "<img src='"+d.msg+"' width='375' height='223' ></img>"
					$("#main_pics_cover").html(div_imgs);
					$("#coverPic").val(d.msg);
				    };
	            },
	        onFallback : function() {
				tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
			},
			onSelectError : function(file, errorCode, errorMsg) {
				switch (errorCode) {
				case -110:
					$.messager.alert('错误提示',
							'文件 大小超出系统限制的100KB大小');
					break;
				case -120:
					$.messager.alert('错误提示', '文件大小异常');
					break;
				case -130:
					$.messager.alert('错误提示', '文件类型不正确!');
					break;
				}
			}
	    });
  })
  
  function checkPic(){
		var exist = false;
		var val = $("#coverPic").val();
		if(val!=""){
			exist = true;
		}else{
			$.messager.alert('错误提示', '请上传封面图');
		}
		return exist;
	}
  </script>
 
 </head>
 <body>
 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGoodsCombinationController.do?doSaveAd" tiptype="4" beforeSubmit="checkPic()">
					<input id="id" name="id" type="hidden" value="${id }">
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
							<tr>
								<td  width="180px">
									<div>
										<input type="file"  id="templatePic_main_cover" />
										<input type="hidden" name="coverPic" id="coverPic"/>
										<div id="progress_bar_m_cover" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
			        					</div>
			        					<div  id="main_pics_cover" style="min-height: 230px;">
			        						<c:if test="${not empty coverPic }">
			        						<img src='${coverPic }' width='375' height='223' ></img>
			        						</c:if>
			        					</div>
							     		<div><span>封面图750*446，100KB以内</span></div>
								</td>
							</tr>
					</table>
		</t:formvalid>
 </body>
