<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>话题信息表</title>
  <t:base type="jquery,easyui,tools"></t:base>
<!--   <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link> -->
  <link rel="stylesheet" href="plug-in/login/css/tags.css" type="text/css"></link>
  <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css" type="text/css"></link>
<!--   <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script> -->
  	<script src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script>
	<link href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
	<link href="plug-in/divView/css/bootstrap.min2.css" rel="stylesheet" type="text/css" />
	<!-- 七牛上传 start -->
	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	<script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	<script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	<!-- 七牛上传 end -->
	<style>
		#content_view p{
			margin: 0;
		}
	</style>
  
  <script type="text/javascript">
  var userType = "${userType}";
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  </script>

 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false"  layout="table" action="tNewsController.do?doAdd" callback="backList" beforeSubmit="setContent()" tiptype="4">
		<input id="id" name="id" type="hidden" value="${tNewsPage.id }">
		<input id="createName" name="createName" type="hidden" value="${tNewsPage.createName }">
		<input id="createBy" name="createBy" type="hidden" value="${tNewsPage.createBy }">
		<input id="createDate" name="createDate" type="hidden" value="${tNewsPage.createDate }">
		<input id="updateName" name="updateName" type="hidden" value="${tNewsPage.updateName }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tNewsPage.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tNewsPage.updateDate }">
		<input id="status" name="status" type="hidden" value="A">
		<input id="upLoaded" name="upLoaded" type="hidden" value="N" >
		<input id="tags" name="tags" type="hidden"  >
		<input id="picMapContent" name="picMapContent" type="hidden"  >
		<input id="goodNum" name="goodNum" type="hidden" value="0" >
		<input id="noSenseNum" name="noSenseNum" type="hidden" value="0" >
		<input id="clickNum" name="clickNum" type="hidden" value="0" >
		<table style="width: 95%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="6" height="40px">
						<h2>话题管理</h2>
					</td>
				</tr>
				<tr>
					<td align="right" width="120px;" >
						<label class="Validform_label">
							话题标题:
						</label>
					</td>
					<td class="value"  width="450px;" >
					     	 <input id="title" name="title" type="text" style="width: 320px" maxlength="16"  datatype="*2-60" >
					     	 <span style="color: red">*</span>
							 <span class="Validform_checktip">建议不超过16个汉字</span>
							<label class="Validform_label" style="display: none;">话题标题</label>
					</td>
					<td align="right" rowspan="4" width="120px;" >
						<label class="Validform_label">
							顶图:
						</label>
					</td>
					<td class="value" rowspan="4" width="350px;">
					    <div id="container_div_1" >
                            <a  id="pickfiles_1" href="#" style="width: 200px">
                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
                            </a>
                            <div id="titlePicDiv" style="height: 150px;">
                            
                            </div>
                        </div>
					    <input id="titlePic" name="titlePic" type="hidden" value="${tNewsPage.titlePic}" />
					    <div class="Validform_checktip">尺寸推荐：750*446</div>
						<label class="Validform_label" style="display: none;">标题图片</label>
					</td>
					<td align="right" rowspan="4" width="120px;" >
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value" rowspan="4" >
						<div id="container_div_2" >
                            <a  id="pickfiles_2" href="#" style="width: 200px">
                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
                            </a>
                            <div id="coverPicDiv" style="height: 150px;">
                            
                            </div>
                        </div>
					    <input id="coverPic" name="coverPic" type="hidden" value="${tNewsPage.coverPic}" datatype="*" >
					    <div class="Validform_checktip">尺寸推荐：750*446</div>
						<label class="Validform_label" style="display: none;">封面图片</label>
					</td>
				</tr>	
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题作者:
						</label>
					</td>
					<td class="value">
					     	<input id="author" name="author" type="text" style="width: 150px" autocomplete='off'  value="${tNewsPage.author}" errormsg="请录入话题作者" checktip="请录入话题作者">
					     	<input id="userId" name="userId" type="hidden" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">话题作者</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题分类:
						</label>
					</td>
					<td class="value" id="news_type">
							<t:dictSelect field="newsType"  type="checkbox" dictField="id" dictText="name" dictTable="t_template_type"  dictCondition=" WHERE status='A' and level='1' and platform_Type='1' and template_Type='2' and id not in('6001','6002') " defaultVal="${tNewsPage.newsType}" hasLabel="false"  title="话题分类"  datatype="*" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">话题分类</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							关联行业:
						</label>
					</td>
					<td class="value">
					    <input name="tradeId" type="hidden" value="${tradeId}" id="tradeId">
		                <input name="tradeName" type="text" value="${tradeName }" id="tradeName" readonly="readonly" datatype="*" />
		                <t:choose hiddenName="tradeId" hiddenid="id" url="userController.do?goTradeList" name="tradeList"
		                    icon="icon-search" title="行业列表" textname="tradeName" isclear="true" isInit="true"></t:choose>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题标签:
						</label>
					</td>
					<td class="value" colspan="5">
							<input type="text"  id="input-txt"  />
							<a href="#" class="easyui-linkbutton l-btn" id="input-btn" iconCls="icon-edit">添加标签</a> 
							 <span style="color: red;">提示:双击删除标签</span> 
							<fieldset title="话题标签" style="min-height: 50px;">
								<div class="box" id="tag_div">
									
								</div>
							</fieldset>
					</td>
				</tr>		
				<tr>
					<td align="right">
						<label class="Validform_label">
							话题正文:
						</label>
					</td>
					<td class="value" colspan="5"><input id="newsContext" name="newsContext" type="hidden" ><script id="content"   type="text/plain" style="width:99%;"></script>
							<label class="Validform_label" style="display: none;">话题正文</label></td>
				</tr>
				<tr height="40">
					<td class="upload" colspan="6" align="center">
						<a href="#" class="easyui-linkbutton l-btn" id="btn_view" onclick="view()" iconCls="icon-msg">预览</a> 
						<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="sub()" iconCls="icon-save">保存</a> 
						<a href="#" class="easyui-linkbutton l-btn" id="btnAndUp" onclick="setUpload()" iconCls="icon-save">保存并发布</a> 
						<a href="javascript:back()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
					</td>
				</tr>
			</table>
		</div>
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
 </body>
  <script>
    $(function () {
        changeColor();
        sayHi();
        addEvaluation();
    });
   

    
    /**自动完成插件start*/
  //自动完成绑定
//   	$("#author").autocomplete(
//   			"tPersonController.do?getHelperAutoList",
//   			{
//   				max : 6,
//   				minChars : 1,
//   				width : 200,
//   				scrollHeight : 100,
//   				matchContains : true,
//   				autoFill : false,
//   				extraParams : {
//   					featureClass : "P",
//   					style : "full",
//   					maxRows : 10,
//   					labelField : "name,phoneNo",
//   					valueField : "userId",
//   					searchField : "name,phoneNo",
//   					entityName : "TPersonEntity"
// //  						trem : getTremValueuser(id)
//   				},
//   				parse : function(data) {
//   					$("#userId").val("");
//   					return parse.call(this, data);
//   				},
//   				formatItem : function(row, i, max) {
//   					return formatItem.call(this, row,i, max);
//   				}
//   			}).result(function(event, row, formatted) {
//   		callBack.call(this, row);
//   	})

//      function parse(data){
//          	var parsed = [];
//   	        	$.each(data.rows,function(index,row){
//   	        		parsed.push({data:row,result:row,value:row.id});
//   	        	});
//      				return parsed;
//      }
     /**
      * 选择后回调 
      * @param {Object} data
      */
//      function callBack(data) {
//      	$("#author").val(data.name);
//      	$("#userId").val(data.userId);
//      }
     
      /**
       * 每一个选择项显示的信息
       * 
       * @param {Object} data
       */
//      function formatItem(data) {
//      	return data.name + "-->" + " " + data.phoneNo;
//      }
     /**自动完成插件end*/
//初始化annotator-pro-editor.js  中的div
//   var div_width = 600;
//   var div_height = 400;  
</script>
<script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?v=1.02"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?v=1.10"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/lang/zh-cn/zh-cn.js?v=1.02"></script>	
<script src = "webpage/com/buss/news/tNews.js?v=1.22"></script>
