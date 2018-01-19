<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>话题信息表</title>
    <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="plug-in/login/css/tags.css" type="text/css"></link>
  <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css" type="text/css"></link>
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
  var newsType = "${newsType}";
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  /* 	function checkSelected(){
  		var type_codes = "${tNewsPage.newsType}";
  		var code = type_codes.split(",");
  		for(var i=0;i<code.length;i++){
  			$("#news_type input[type='checkbox'][value='"+code[i]+"']").attr("checked",true);
  		}
  	} */
  </script>
 </head>
 <body>
   <t:formvalid formid="formobj" dialog="false" beforeSubmit="setContent()"  layout="table" action="tNewsController.do?doUpdate" callback="backList2" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tNewsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tNewsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tNewsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tNewsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tNewsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tNewsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tNewsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="shopkeeper" name="shopkeeper" type="hidden" value="${tNewsPage.shopkeeper}">
					<input id="upLoaded" name="upLoaded" type="hidden" value="${tNewsPage.upLoaded}" >
					<input id="tags" name="tags" type="hidden"  value="${tNewsPage.tags}">
					<input id="picMapContent" name="picMapContent" type="hidden"  value='${tNewsPage.picMapContent}'>
					<input id="goodNum" name="goodNum" type="hidden" value="${tNewsPage.goodNum}" >
					<input id="noSenseNum" name="noSenseNum" type="hidden" value="${tNewsPage.noSenseNum}" >
					<input id="clickNum" name="clickNum" type="hidden" value="${tNewsPage.clickNum}" >
					<input id="newsType" name="newsType" type="hidden" value="${newsType }" ><!-- 1：管家课堂 2：管家故事-->
		<table style="width: 95%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="6"  height="40px">
						<h2>
							<c:if test="${newsType =='1' }">管家课堂</c:if>
							<c:if test="${newsType =='2' }">管家故事</c:if>
						</h2>
					</td>
				</tr>
				<tr>
					<td align="right" width="120px;">
						<label class="Validform_label">
							话题标题:
						</label>
					</td>
					<td class="value" width="450px;">
					     	 <input id="title" name="title" type="text" value="${tNewsPage.title}"  style="width: 320px" maxlength="30" datatype="*2-60"  >
					     	 <span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">话题标题</label>
					</td>
					<td align="right" rowspan="3" width="120px;">
						<label class="Validform_label">
							顶图:
						</label>
					</td>
					<td class="value" rowspan="3">
							<div id="container_div_1" >
                            <a  id="pickfiles_1" href="#" style="width: 200px">
                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
                            </a>
                            <div id="titlePicDiv" style="height: 150px;">
                            	<c:if test="${not empty tNewsPage.titlePic }">
                            	<div class='pic_div'><a class='delete' onclick='delTitlePic(this)' href='#'>×</a><img src='${tNewsPage.titlePic}'  height='150' ></img></div>
                            	</c:if>
                            </div>
                        </div>
							<input id="titlePic" name="titlePic" type="hidden" value="${tNewsPage.titlePic}" >
					</td>
					<td align="right" rowspan="3" width="120px;">
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value" rowspan="3">
							<div id="container_div_2" >
	                            <a  id="pickfiles_2" href="#" style="width: 200px">
	                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
	                            </a>
	                            <div id="coverPicDiv" style="height: 150px;">
	                            	<div class='pic_div'><a class='delete' onclick='delCoverPic(this)' href='#'>×</a><img src='${tNewsPage.coverPic}'  height='150' ></img></div>
	                            </div>
	                        </div>
							<input id="coverPic" name="coverPic" type="hidden" value="${tNewsPage.coverPic}" datatype="*" errormsg="请上传封面图片" checktip="请上传封面图片">
						    <span class="Validform_checktip"></span>
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
				     	 <input id="author" name="author" type="text" style="width: 120px"  value="${tNewsPage.author}"  errormsg="请录入话题作者" checktip="请录入话题作者">
				     	 <input id="userId" name="userId" type="hidden" value="${tNewsPage.userId}"/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">话题作者</label>
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
							<input type="text"  id="input-txt" />
							<a href="#" class="easyui-linkbutton l-btn" id="input-btn" iconCls="icon-edit">评价</a>
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
					<td class="value" colspan="5"><input id="newsContext" name="newsContext" type="hidden"><script id="content"   type="text/plain"  style="width:99%;">
						${tNewsPage.newsContext == NULL || tNewsPage.newsContext == '' ? '' : tNewsPage.newsContext}
					</script>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">话题正文</label></td>
				</tr>
				<tr height="40">
					<td class="upload" colspan="6" align="center">
					<a href="#" class="easyui-linkbutton l-btn" id="btn_view" onclick="view()" iconCls="icon-msg">预览</a> 
						<a href="#" class="easyui-linkbutton" id="btn" onclick="sub()" iconCls="icon-save">保存</a>
						<a href="#" class="easyui-linkbutton" onclick="doDown()" iconCls="icon-save">下架</a>
						<a href="#" class="easyui-linkbutton" id="btnAndUp" onclick="setUpload()" iconCls="icon-save">保存并发布</a> 
						<a href="javascript:back2()" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回</a>
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
    
    var tags = $("#tags").val();
    if(tags != ""){
    	var tagVal = $("#tags").val();
		var arr = new Array();  
		arr = tagVal.split(",");
		var flag = false;
		var divs = document.getElementById("tag_div");
		for (i=0;i<arr.length ;i++ )   
		{   
			var str = arr[i];
			if(str != ""){
				var texts = document.createTextNode(str);
    			var links = document.createElement("a");
    			var spans = document.createElement("span");
    			links.appendChild(texts);
    			links.style.backgroundColor = randomColor();
    			spans.appendChild(links);
    			divs.appendChild(spans);	
			}
		}
    }
//     checkSelected();
    changeColor();
    sayHi();
    addEvaluation();
//     checkPicMap();
});
    
</script>	
<script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?v=1.02"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?v=1.02"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/lang/zh-cn/zh-cn.js?v=1.02"></script>
<script src = "webpage/com/buss/news/tNews.js?v=1.06"></script>
