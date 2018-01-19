<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>导购标签</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
		$("#tag").bind("change",function(){
			if(""==this.value){
				$("#professionalCode").val("");
			}else{
				getProfessional();
			}
			if("1"==this.value){//专家
				$("#c_pic").show();
				$("#picUrl").attr("datatype","*");
			}else{
				$("#c_pic").hide();
				$("#picUrl").removeAttr("datatype");
				$("#main_pics").empty();
			}
			$("#picUrl").val("");
		})
		
	        $('#templatePic_u').uploadify({buttonText:'浏览证书',
	            progressData:'speed',
	            multi:true,
	            height:25,
	            queueID:'progress_bar_m',
	            overrideEvents:['onDialogClose'],
	            fileTypeDesc:'文件格式:',
	            fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
	            fileSizeLimit:'200KB',
	            queueSizeLimit:4,
	            swf:'plug-in/uploadify/uploadify.swf',
	            uploader:'tPersonTagsController.do?uploadPic&sessionId=${pageContext.session.id}',
	            auto:true,
	            onUploadSuccess : function(file, data, response) {
	                var d=$.parseJSON(data);
	            	if(d.success){
	            		var div_imgs = "<div style='float:left'><a class='delete' onclick='delPic(this)' href='#'></a><img src='"+d.msg+"' width='100' height='100' ></img></div>"
						$("#main_pics").append(div_imgs);
	            		var imgUrlStr = $("#picUrl").val()+","+d.msg;
	            		var position = imgUrlStr.indexOf(","); 
	            		  if(position==0){
	            			  imgUrlStr = imgUrlStr.slice(1);
	            		  }
	            		$("#picUrl").val(imgUrlStr);
// 	                    $("#prePic").attr("src",d.msg);
	                }
	            }
	        });
	});
  
//设置标签类目
  function setVisibleCatgs(tagId){
  	var url = "tPersonTagsController.do?categryList&tagId="+tagId;
  	$.dialog.setting.zIndex = 5000;
  	if(typeof(windowapi) == 'undefined'){
  		$.dialog({
  			content: "url:"+url,
  			lock : true,
  			title:"选择类目",
  			width:400,
  			height: 500,
  			cache:false,
  			ok: function(){
  				var iframe_doc = this.iframe.contentWindow.document;
  				var root_node = $(iframe_doc).find('#node_root');
  				var node = $(root_node).tree('getChecked');
  				var cnodes = '';
  				var pnodes = '';
  				var pnode = null; //保存上一步所选父节点
  				for ( var i = 0; i < node.length; i++) {
  					if ($(root_node).tree('isLeaf', node[i].target)) {
  						cnodes += $(node[i].target).attr("node-id") + ',';
//  						cnodes += node[i].id + ',';
  						pnode = $(root_node).tree('getParent', node[i].target); //获取当前节点的父节点
  						while (pnode!=null) {//添加全部父节点
  							pnodes += $(pnode.target).attr("node-id") + ',';
//  							pnodes += pnode.id + ',';
  							pnode = $(root_node).tree('getParent', pnode.target); 
  						}
  					}
  				}
  				cnodes = cnodes.substring(0, cnodes.length - 1);
  				pnodes = pnodes.substring(0, pnodes.length - 1);
  				$("#categries").val(cnodes + "," + pnodes);
  			},
  			cancelVal: '关闭',
  			cancel: true /*为true等价于function(){}*/
  		}).zindex();
  	} else{
  		$.dialog({
  			content: "url:"+url,
  			lock : true,
  			title:"选择类目",
  			width:400,
  			height: 500,
  			parent:windowapi,
  			cache:false,
  			ok: function(){
  				var iframe_doc = this.iframe.contentWindow.document;
  				var root_node = $(iframe_doc).find('#node_root');
  				var node = $(root_node).tree('getChecked');
  				var cnodes = '';
  				var pnodes = '';
  				var pnode = null; //保存上一步所选父节点
  				for ( var i = 0; i < node.length; i++) {
  					if ($(root_node).tree('isLeaf', node[i].target)) {
  						cnodes += $(node[i].target).attr("node-id") + ',';
//  						cnodes += node[i].id + ',';
  						pnode = $(root_node).tree('getParent', node[i].target); //获取当前节点的父节点
  						while (pnode!=null) {//添加全部父节点
  							pnodes += $(pnode.target).attr("node-id") + ',';
  							pnode = $(root_node).tree('getParent', pnode.target); 
  						}
  					}
  				}
  				cnodes = cnodes.substring(0, cnodes.length - 1);
  				pnodes = pnodes.substring(0, pnodes.length - 1);
  				$("#categries").val(cnodes + "," + pnodes);
  			},
  			cancelVal: '关闭',
  			cancel: true /*为true等价于function(){}*/
  		}).zindex();
  	}
  }
  
  //检查标签，最多有2个标签
  function checkTagNum(){
	  if($("#categries").val()==""){
		  alert("请选择类目");
		  return false;
	  }
	  var flag = true;
	  $.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "tPersonTagsController.do?checkTagNum&userId=${user_id }",// 
			error : function() {// 请求失败处理函数
			
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if(d.success){

				}else{
					alert(d.msg);
					flag = false;
				}
			}
		});
	  setName();
	  return flag;
  }

  function setName(){
		  var name1 = $("#topCategoryId option:selected").text();
		  var name2 = $("#professionalCode option:selected").text();
		  $("#topCategoryName").val(name1);
		  $("#professionalName").val(name2);
  }
  
//获取职称
  function getProfessional(){
	var topCategoryId = $("#topCategoryId").val();
	var helperType = $("#tag").val();
	if(topCategoryId!=""&&helperType!=""){
	  	var url = "tProfessionalController.do?getProfessional&topCategoryId="+topCategoryId+"&helperType="+helperType;
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
	  				$("#professionalCode").empty();
	  				var ops = "<option value=''>---请选择---</option>";
	  				for(i=0;i<d.obj.length;i++){
	  					ops+="<option value='"+d.obj[i].professionalCode+"'>"+d.obj[i].professionalName+"</option>"
	  				}
	  				$("#professionalCode").append(ops);
	  			}
	  		}
	  	});
	}
  }
  
  function delPic(obj){
	  $(obj).parent().remove();
	  setPicUrlVal();
  }
  
  
  function setPicUrlVal(){
	  var imgUrlStr = "";
	  $("#main_pics img").each(function(){
		  imgUrlStr +=","+$(this).attr("src");
	  })
	  var position = imgUrlStr.indexOf(","); 
	  if(position==0){
		  imgUrlStr = imgUrlStr.slice(1);
	  }
	  $("#picUrl").val(imgUrlStr);
  }
  
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPersonTagsController.do?doAdd" tiptype="4" beforeSubmit="checkTagNum()">
					<input id="id" name="id" type="hidden" value="${tPersonTagsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tPersonTagsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tPersonTagsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tPersonTagsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tPersonTagsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPersonTagsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPersonTagsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="userId" name="userId" type="hidden" value="${user_id }">
					<input id="categries" name="categries" type="hidden" value=""><!-- 类目 -->
					<input id="topCategoryName" name="topCategoryName" type="hidden" value=""><!-- 一级类目名称 -->
					<input id="professionalName" name="professionalName" type="hidden" value=""><!-- 职称名称 -->
		<table cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100px">
						<label class="Validform_label">
							大类:
						</label>
					</td>
					<td class="value">
							  <select name="topCategoryId" id="topCategoryId" datatype="*" onchange="getProfessional()">
									<c:forEach var="category" items="${categoryList}">
									<option value="${category[0] }">${category[1]}</option>
									</c:forEach>
								</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类目</label>
						</td>
				</tr>
				<tr>
					<td align="right" width="100px">
						<label class="Validform_label">
							标签:
						</label>
					</td>
					<td class="value">
							  <t:dictSelect field="tagCode" type="list"	datatype="*"  typeGroupCode="hp_type" defaultVal="${tPersonTagsPage.tagCode}" hasLabel="false"  id="tag" title="标签"></t:dictSelect>     
							  <a href="#" class="easyui-linkbutton" plain="true" style="margin-left: 50px" onclick="setVisibleCatgs('')" ><span class="icon-search l-btn-icon-left">选择类目</span></a>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标签</label>
						</td>
				</tr>
				<tr>
					<td align="right" width="100px">
						<label class="Validform_label">
							职称:
						</label>
					</td>
					<td class="value">
							<select name="professionalCode" id="professionalCode" datatype="*">
							<option value="">---请选择---</option>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">职称</label>
						</td>
				</tr>
				<tr id="c_pic" style="display: none">
					<td align="right">
						<label class="Validform_label">
							证书:
						</label>
					</td>
					<td>
						<div>
							<input type="file" name="templatePic_u" id="templatePic_u" />
        				</div>
        				<div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;display: none"></div>
        				<div  id="main_pics" style="height: 130px;"></div>
<!-- 					    <img id="prePic" src="" style="background-color: rgba(68, 111, 128, 0.67)"  width="200px" height="150px"    /> -->
					    <input id="picUrl" name="picUrl" type="hidden" value="ss"  errormsg="请上传证书图片" >
					    <span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">证书图片</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
<!--   <script src = "webpage/com/buss/user/tPersonTags.js"></script>		 -->