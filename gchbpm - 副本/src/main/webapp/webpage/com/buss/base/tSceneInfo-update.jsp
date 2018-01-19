<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>场景</title>
  <t:base type="jquery,easyui,tools"></t:base>
    <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
   <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <link rel="stylesheet" type="text/css" href="plug-in/picMap/css/annotator-pro-editor.css">
	<link rel="stylesheet" type="text/css" href="plug-in/picMap/css/annotator-pro.min.css">
	<link href="plug-in/divView/css/bootstrap.min2.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	#content_view p{
		margin: 0;
	}
  .pic_div{
  	width: 240px;
    float: left;
    position: relative;
    margin-left: 20px;
  }
  .delete {
    display: block;
    color: white;
    background: rgba(255, 255, 255, 0.2);
    width: 40px;
    height: 40px;
    top: 0;
    right: 0;
    position: absolute;
    text-align: center;
    line-height: 40px;
}
</style>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tSceneInfoController.do?doUpdate" tiptype="4" beforeSubmit="setPicContent()" callback="backList">
					<input id="id" name="id" type="hidden" value="${tSceneInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSceneInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSceneInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSceneInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSceneInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSceneInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSceneInfoPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tSceneInfoPage.status }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tSceneInfoPage.retailerId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								标题:
							</label>
						</td>
						<td class="value">
						     	 <input id="title" name="title" type="text" style="width: 350px" class="inputxt" maxlength="25" datatype="*" value='${tSceneInfoPage.title}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
						</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							封面图:
						</label>
					</td>
					<td class="value">
					    <div>
							<input type="file" name="templatePic_u" id="templatePic_u" />
        				</div>
        				<div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;display: none"></div>
					    <img id="prePic" src="${tSceneInfoPage.coverPic }"  width="375px" height="223px"    />
					    <input id="coverPic" name="coverPic" type="hidden" value="${tSceneInfoPage.coverPic }" />
					    <span class="Validform_checktip">封面图尺寸：750*446</span>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							首页展示图:
						</label>
					</td>
					<td class="value" height="400">
					    <div>
							<input type="file" name="templatePic_u" id="templatePic_u2" />
        				</div>
        				<div id="progress_bar_m2" style="position: absolute;right:0px;bottom: 0px;display: none"></div>
					    <img id="prePic2" src="${tSceneInfoPage.indexPic }"  width="270px" height="320px"   />
					    <input id="indexPic" name="indexPic" type="hidden" value="${tSceneInfoPage.indexPic }" />
					    <div><span class="Validform_checktip">首页展示图：726*860</span></div>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							场景图片:
						</label>
					</td>
					<td class="value" min-height="480">
						<div>
							<input type="file"  id="pic_u" />
        				</div>
        				<div id="progress_bar_muti" style="position: absolute;right:0px;bottom: 0px;display: none"></div>
	        			<div id="aaaaaaa" style="min-height: 440px;position: relative;">
	        				<c:forEach items="${detailPics}" varStatus="status" var="poVal">
	        				<div id='${poVal.id }_' class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='${poVal.picUrl }' width='240' height='427' onclick='addTags(this,"${poVal.id }_")'></img>
	            				<div>
	            				<input type='hidden' class='id' value='${poVal.id }'/>
	            				<input type='hidden' class='picUrl' value='${poVal.picUrl }'/>
	            				<input type='hidden' class='picMapContent' value='${poVal.picMapContent }'/>
	            				</div>
	            				<input id='${poVal.id }__cnt' type='hidden'/>
	            			</div>
	        				</c:forEach>
	        			</div>
	        			<div><span class="Validform_checktip">图片尺寸：720*1280</span></div>
						</td>
					</tr>
					<tr height="40">
					<td class="upload" colspan="6" align="center">
						<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="sub()" iconCls="icon-save">提交</a> 
						<a href="javascript:back()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
					</td>
					</tr>
			</table>
		</t:formvalid>
		
<div id="content_view" style="display: none;background: gray" class="modal fade in"  tabindex="-1" >		
	<div   class="modal-dialog" style="width:360px;">
		<div class="modal-content" >
			<div class="modal-header">
                <button type="button" class="close" onclick="closeDiv()" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">图文预览</h4>
            </div>
            <div class="modal-body" id="preview-msg-content" style="overflow-y: auto; overflow-x: hidden; padding: 0px; margin: 10px; height: 400px;word-break:break-all">
			</div>
			<div class="modal-footer">
                <p class="text-center text-danger">此处预览请作为参考，最终效果已手机为准</p>
            </div>
         </div>
	</div>
</div>	
<div id="addTagsDiv" style="display: none;position:absolute;top:0px;left: 0px;height: 720px;width: 100%;margin:0;z-index: 9999;background: gray">
	<div style="width: 360px;height: 640px;margin: auto;margin-top: 50px;">
		<div id="wrap">
		<div class="container-fluid">
			<form role="form" class="form">
				<div class="btn-group" data-toggle="buttons" style="display:none ">
					<label class="btn btn-lg btn-default active"> <input
						type="radio" name="radio-editor-mode" id="radio-editor-mode-edit"
						checked="">编辑 </label> 
						<label class="btn btn-lg btn-default"
						id="radio-editor-mode-preview-label"> <input type="radio"
						name="radio-editor-mode" id="radio-editor-mode-preview">预览
					</label> 
					<label class="btn btn-lg btn-default"
						id="radio-editor-mode-jquery-label"> <input type="radio"
						name="radio-editor-mode" id="radio-editor-mode-jquery">jQuery代码
					</label> <label class="btn btn-lg btn-default"
						id="radio-editor-mode-load-label"> <input type="radio"
						name="radio-editor-mode" id="radio-editor-mode-load">测试代码正确性
					</label>
				</div>
			</form>
			<div class="panel panel-default" id="panel-editor" >
				<div class="panel-body" id="panel-canvas" >
					<div class="ndd-drawable-canvas" style="margin: auto;overflow: hidden;">
						<img id="pic_add_tags" src="" class="ndd-drawable-canvas-image">
						<div class="ndd-drawables-container"></div>
					</div>
				</div>
			</div>
			<div class="panel panel-default" id="panel-preview"
				style="display: none;">
				<div class="panel-body" id="plugin-container"
					style="width: 100%; height: 600px;"></div>
			</div>
			<div class="panel panel-default" id="panel-jquery"
				style="display: none;">
				<div class="panel-body">
					<label class="btn btn-primary" id="button-select-jquery">
						全选</label><br> <br>
					<div class="well" id="well-jquery"></div>
				</div>
			</div>
			<div class="panel panel-default" id="panel-load"
				style="display: none;">
				<div class="panel-body">
					<textarea class="form-control" rows="10" id="textarea-load"></textarea>
					<br> <label class="btn btn-lg btn-primary" id="button-load"
						disabled=""> 验证</label>
				</div>
			</div>
<!-- 			<h2>设置</h2> -->
			<div class="row" style="display: none">
				<!-- Popups -->
				<div class="col-md-8">
					<div class="panel panel-default">
						<div id="panel-disabler"></div>
<!-- 						<div class="panel-heading">注释</div> -->
						<div class="panel-body">
							<form role="form" class="form-horizontal">
								<div class="col-md-6 col-sm-6"  >
									<!-- Content Type -->
									<div class="form-group" >
										<label class="col-md-3 control-label"> 内容类别</label>
										<div class="col-md-9">
											<div class="btn-group" data-toggle="buttons"> <label class="btn btn-default active"> <input type="radio" name="radio-content-type" id="radio-content-type-text" checked="">文本 </label> 
													<label	class="btn btn-default" style="display: none">  <input type="radio" name="radio-content-type" id="radio-content-type-custom-html">自定义HTML </label>
											</div>
										</div>
									</div>
									<!-- Title -->
									<div class="form-group" >
										<label class="col-md-3 control-label"> 标题</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="input-title"
												placeholder="Enter title">
										</div>
									</div>
									<!-- Text -->
									<div class="form-group" >
										<label class="col-md-3 control-label"> 文本</label>
										<div class="col-md-9">
											<textarea class="form-control" rows="3" id="textarea-text" ></textarea>
										</div>
									</div>
									<!-- Text Color -->
									<div class="form-group">
										<label for="color-text-color" class="col-md-3 control-label">
											文本颜色</label>
										<div class="col-md-9">
											<div class="input-group">
												<input type="color" class="form-control"
													id="color-text-color" value="#ffffff"> <span
													class="input-group-addon" id="color-text-color-hex">#000000</span>
											</div>
										</div>
									</div>
									<!-- HTML -->
									<div class="form-group" >
										<label class="col-md-3 control-label"> HTML</label>
										<div class="col-md-9">
											<textarea class="form-control" rows="3" id="textarea-html"
												disabled="disabled"></textarea>
										</div>
									</div>
									<!-- ID -->
									<div class="form-group">
										<label class="col-md-3 control-label"> ID</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="input-id"
												placeholder="Enter ID">
											<p class="help-block">用于深度链接</p>
										</div>
									</div>
									<!-- Deep linking URL -->
									<div class="form-group">
										<label class="col-md-3 control-label"> 深度链接URL</label>
										<div class="col-md-9">
											<div id="input-deep-link-url" class="well"></div>
											<p id="input-deep-link-url-help" class="help-block"></p>
										</div>
									</div>
									<!-- Delete -->
									<div class="form-group">
<!-- 										<label class="col-md-3 control-label"> 删除</label> -->
										<div class="col-md-9">
											<label class="btn btn-danger" data-toggle="modal"
												data-target="#modal-delete"> 删除注释</label>
										</div>
									</div>
								</div>
								<div class="col-md-6 col-sm-6">
									<!-- Tint Color -->
									<div class="form-group" style="display: none;">
										<label for="color-tint-color" class="col-md-3 control-label">
											提示框颜色</label>
										<div class="col-md-9">
											<div class="input-group">
												<input type="color" class="form-control"
													id="color-tint-color" value="#ffff00"> <span
													class="input-group-addon" id="color-tint-color-hex">#ffff00</span>
											</div>
										</div>
									</div>
									<!-- Style -->
									<div class="form-group" style="display: none">
										<label class="col-md-3 control-label"> 风格</label>
										<div class="col-md-9">
											<div class="btn-group btn-group-no-margin"
												data-toggle="buttons" id="btn-group-style-circle">
												<label class="btn btn-default active"> <input
													type="radio" name="radio-popup-position"
													id="radio-popup-style-1" checked="">
												<div class="icon-in-label ndd-spot-icon icon-style-1">
														<div class="ndd-icon-main-element"></div>
														<div class="ndd-icon-border-element"></div>
													</div> </label> <label class="btn btn-default"> <input
													type="radio" name="radio-popup-position"
													id="radio-popup-style-0">
												<div style="height: 44px; line-height: 44px;">隐藏</div> </label>
											</div>
											<div class="btn-group btn-group-no-margin"
												data-toggle="buttons" id="btn-group-style-rect">
												<label class="btn btn-default active"> <input
													type="radio" name="radio-popup-position"
													id="radio-popup-style-rect-1" checked="">
												<div class="icon-in-label ndd-spot-icon icon-style-rect-1">
														<div class="ndd-icon-main-element"></div>
														<div class="ndd-icon-border-element"></div>
													</div> </label> <label class="btn btn-default"> <input
													type="radio" name="radio-popup-position"
													id="radio-popup-style-rect-2">
												<div class="icon-in-label ndd-spot-icon icon-style-rect-2">
														<div class="ndd-icon-main-element"></div>
														<div class="ndd-icon-border-element"></div>
													</div> </label> <label class="btn btn-default"> <input
													type="radio" name="radio-popup-position"
													id="radio-popup-style-rect-3">
												<div class="icon-in-label ndd-spot-icon icon-style-rect-3">
														<div class="ndd-icon-main-element"></div>
														<div class="ndd-icon-border-element"></div>
													</div> </label> <label class="btn btn-default"> <input
													type="radio" name="radio-popup-position"
													id="radio-popup-style-rect-4">
												<div class="icon-in-label ndd-spot-icon icon-style-rect-4">
														<div class="ndd-icon-main-element"></div>
														<div class="ndd-icon-border-element"></div>
													</div> </label> <label class="btn btn-default"> <input
													type="radio" name="radio-popup-position"
													id="radio-popup-style-rect-0">
												<div style="height: 44px; line-height: 44px;">隐藏</div> </label>
											</div>
										</div>
									</div>
									<div style="display: none">
									<!-- Popup Width -->
									<div class="form-group">
										<label class="col-md-3 control-label"> 提示框宽度</label>
										<div class="col-md-7">
											<div class="input-group">
												<input type="text" class="form-control"
													id="input-popup-width"> <span
													class="input-group-addon" id="input-popup-width-addon">px</span>
											</div>
										</div>
										<div class="col-md-2">
											<div class="checkbox">
												<label> <input type="checkbox"
													id="checkbox-popup-width-auto" checked="">Auto </label>
											</div>
										</div>
									</div>
									<!-- Popup Height -->
									<div class="form-group">
										<label class="col-md-3 control-label"> 提示框高度</label>
										<div class="col-md-7">
											<div class="input-group">
												<input type="text" class="form-control"
													id="input-popup-height"> <span
													class="input-group-addon" id="input-popup-height-addon">px</span>
											</div>
										</div>
										<div class="col-md-2">
											<div class="checkbox">
												<label> <input type="checkbox"
													id="checkbox-popup-height-auto" checked="">Auto </label>
											</div>
										</div>
									</div>
									<!-- Popup Position -->
									<div class="form-group">
										<label for="radio-popup-position"
											class="col-md-3 control-label"> 提示框位置</label>
										<div class="col-md-9">
											<div class="btn-group" data-toggle="buttons">
												<label class="btn btn-default active"> <input
													type="radio" name="radio-popup-position"
													id="radio-popup-position-top" >上面 </label> <label
													class="btn btn-default"> <input type="radio"
													name="radio-popup-position"
													id="radio-popup-position-bottom">下面 </label> <label
													class="btn btn-default"> <input type="radio"
													name="radio-popup-position" id="radio-popup-position-left">左面
												</label> <label class="btn btn-default"> <input type="radio"
													name="radio-popup-position" id="radio-popup-position-right" checked="">右面
												</label>
											</div>
										</div>
									</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<!-- Navigation -->
				<div class="col-md-4" style="display: none">
					<div class="panel panel-default">
						<div class="panel-heading">导航</div>
						<div class="panel-body">
							<form role="form" class="form-horizontal">
								<!-- Width -->
								<div class="form-group">
									<label class="col-md-3 control-label"> 宽</label>
									<div class="col-md-7">
										<div class="input-group">
											<input type="text" class="form-control" id="input-width">
											<span class="input-group-addon" id="input-width-addon">px</span>
										</div>
									</div>
									<div class="col-md-2">
										<div class="checkbox">
											<label> <input type="checkbox"
												id="checkbox-width-auto" checked="">Auto </label>
										</div>
									</div>
								</div>
								<!-- Height -->
								<div class="form-group">
									<label class="col-md-3 control-label"> 高</label>
									<div class="col-md-9">
										<div class="input-group">
											<input type="text" class="form-control" id="input-height">
											<span class="input-group-addon" id="input-height-addon">px</span>
										</div>
									</div>
								</div>
								<!-- Max Zoom -->
								<div class="form-group">
									<label class="col-md-3 control-label"> 放大</label>
									<div class="col-md-9">
										<div class="btn-group" data-toggle="buttons">
											<label class="btn btn-default active"> <input
												type="radio" name="radio-max-zoom" id="radio-max-zoom-1-1"
												checked="">1:1 </label> <label class="btn btn-default">
												<input type="radio" name="radio-max-zoom"
												id="radio-max-zoom-1">1x </label> <label
												class="btn btn-default"> <input type="radio"
												name="radio-max-zoom" id="radio-max-zoom-2">2x </label> <label
												class="btn btn-default"> <input type="radio"
												name="radio-max-zoom" id="radio-max-zoom-3">3x </label> <label
												class="btn btn-default"> <input type="radio"
												name="radio-max-zoom" id="radio-max-zoom-4">4x </label> <label
												class="btn btn-default"> <input type="radio"
												name="radio-max-zoom" id="radio-max-zoom-custom">自定义
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-offset-3 col-md-9">
										<input type="text" class="form-control" id="input-max-zoom"
											value="4" disabled="disabled">
									</div>
								</div>
								<!-- Show Navigator -->
								<div class="form-group">
									<label for="checkbox-navigator" class="col-md-3 control-label">
										导航</label>
									<div class="col-md-9">
										<div class="checkbox">
											<label> <input type="checkbox"
												id="checkbox-navigator"> </label>
										</div>
									</div>
								</div>
								<!-- Show Navigator Image Preview -->
								<div class="form-group">
									<label for="checkbox-navigator-image-preview"
										class="col-md-3 control-label"> 导航图片预览</label>
									<div class="col-md-9">
										<div class="checkbox">
											<label> <input type="checkbox"
												id="checkbox-navigator-image-preview"> </label>
										</div>
									</div>
								</div>
								<!-- Enable Fullscreen -->
								<div class="form-group">
									<label for="checkbox-fullscreen" class="col-md-3 control-label">
										宽屏</label>
									<div class="col-md-9">
										<div class="checkbox">
											<label> <input type="checkbox"
												id="checkbox-fullscreen"> </label>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="text-align: center;"><button value="确定" onclick="subPicMap()">确定</button> <button value="关闭" onclick="closePicDiv()">关闭</button></div>
	<!-- Modals -->
	<!-- Modal -->
	<div class="modal fade" id="modal-delete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">确定吗?</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						id="delete-annotation-button">删除</button>
				</div>
			</div>
		</div>
	</div>
	</div>
</div>			
 </body>
 <script type="text/javascript">
 var m_pic_num = "${picNums}";//已经上传的图的张数
 $(document).ready(function(){
	  $('#templatePic_u').uploadify({buttonText:'浏览图片',
         progressData:'speed',
         multi:false,
         height:25,
         queueID:'progress_bar_m',
         overrideEvents:['onDialogClose'],
         fileTypeDesc:'文件格式:',
         fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
         fileSizeLimit:'210KB',
         swf:'plug-in/uploadify/uploadify.swf',
         uploader:'tSceneInfoController.do?uploadPic&sessionId=${pageContext.session.id}',
         auto:true,
         onUploadSuccess : function(file, data, response) {
             var d=$.parseJSON(data);
         	if(d.success){
                 $("#prePic").attr("src",d.msg);
                 $("#coverPic").val(d.msg);
             }
         },
			onSelectError : function(file, errorCode, errorMsg) {
				switch (errorCode) {
				case -110:
					$.messager.alert('错误提示',
							'文件 大小超出系统限制的210KB大小');
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
	  $('#templatePic_u2').uploadify({buttonText:'浏览图片',
         progressData:'speed',
         multi:false,
         height:25,
         queueID:'progress_bar_m2',
         overrideEvents:['onDialogClose'],
         fileTypeDesc:'文件格式:',
         fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
         fileSizeLimit:'210KB',
         swf:'plug-in/uploadify/uploadify.swf',
         uploader:'tSceneInfoController.do?uploadPic&sessionId=${pageContext.session.id}',
         auto:true,
         onUploadSuccess : function(file, data, response) {
             var d=$.parseJSON(data);
         	if(d.success){
                 $("#prePic2").attr("src",d.msg);
                 $("#indexPic").val(d.msg);
             }
         },
			onSelectError : function(file, errorCode, errorMsg) {
				switch (errorCode) {
				case -110:
					$.messager.alert('错误提示',
							'文件 大小超出系统限制的210KB大小');
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
	  
	  
     
     setDivH(m_pic_num);
     
     $('#pic_u').uploadify({buttonText:'浏览图片',
         progressData:'speed',
         multi:true,
         auto:true,
         queueSizeLimit:10,
         height:25,
         queueID:'progress_bar_muti',
         overrideEvents:['onDialogClose'],
         fileTypeDesc:'文件格式:',
         fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
         fileSizeLimit:'210KB',
         swf:'plug-in/uploadify/uploadify.swf',
         uploader:'tSceneInfoController.do?uploadPic&sessionId=${pageContext.session.id}',
         onUploadSuccess : function(file, data, response) {
             var d=$.parseJSON(data);
         	if(d.success){
         		m_pic_num++;
         		setDivH(m_pic_num);
         		var id =getRandomID();
         		var div_imgs = "<div id='"+id+"' class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+d.msg+"' width='240' height='427' onclick='addTags(this,\""+id+"\")'></img>"
         				+"<div><input type='hidden' class='picUrl' value='"+d.msg+"'/><input type='hidden' class='picMapContent' /></div>"
         				+"<input id='"+id+"_cnt' type='hidden'/></div>";
					$("#aaaaaaa").append(div_imgs);
         		if(m_pic_num>=10){
				    	$("#pic_u").hide();
			    }else{
			    	$('#pic_u').uploadify('settings','queueSizeLimit',10-m_pic_num);
			    }
             }
         },
	        onFallback : function() {
				tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
			},
			onSelectError : function(file, errorCode, errorMsg) {
				switch (errorCode) {
				case -100:
					$.messager.alert('错误提示',
							'上传图片总共不能超过10个');
					break;
				case -110:
					$.messager.alert('错误提示',
							'文件 大小超出系统限制的210KB大小');
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
     if(m_pic_num>=10){
	    	$("#pic_u").hide();
	 }
     checkPicMap();
});
//图片热点  
 function checkPicMap(){
	  <c:forEach items="${detailPics}" varStatus="status" var="detail">
		  var picMapContent = '${detail.picMapContent}';
		   var obj = $.parseJSON(picMapContent);
		   if(obj!=null&&obj.length>0){
			    var html='';
		   		for(var i=0;i<obj.length;i++){
		   	   			html += '<div class="ndd-drawable" id="'+obj[i].id+'" style="left:'+obj[i].l+'; top: '+obj[i].t+'; width: 44px; height: 44px;"><div class="ndd-spot-icon icon-style-1"><div class="ndd-icon-main-element"></div><div class="ndd-icon-border-element"></div></div><div class="ndd-drawable-active-area"></div><div class="ndd-annotation-container ndd-annotation-visible" style="left: -4px; top: -2.5px;">'
		   	   			+'<div class="ndd-annotation-box" style="width: 120px; height: 45px;"><div class="del-pic-map" onclick="delPicMap(this)">×</div><a src="'+obj[i].goodsId+'"><div class="ndd-annotation-content"><div class="div_p" onclick="findGoods(this)">'+obj[i].p+'</div></div></a></div></div></div>';
		   	   	}
		   		$("#${detail.id}__cnt").val(html);
//		   		$("#addTagsDiv div.ndd-drawables-container").attr("id","cnt_"+id).empty().html(cnt);
//		   		$("#ndd-drawables-container").html(html);
		   }
	  </c:forEach>
 }
 
 
//初始化annotator-pro-editor.js  中的div
 var div_width = 300;
 var div_height = 533;
 </script>
  <script src = "webpage/com/buss/base/tSceneInfo.js"></script>	
  <script src="plug-in/picMap/js/bootstrap.min.js"></script>
	<script src="plug-in/picMap/js/annotator-pro-editor.js"></script>
	<script src="plug-in/picMap/js/annotator-pro.min.js"></script>	
