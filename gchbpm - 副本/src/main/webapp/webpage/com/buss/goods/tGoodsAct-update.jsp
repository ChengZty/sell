<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品活动管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  setStoreNum() ;
  }) ;
  
  function setStoreNum(){
	  var storeIdsArray =  new Array();
	  var storeIds = $("#storeIds").val() ;
	  
	  if(storeIds == "") return ;
	  
	  storeIdsArray = storeIds.split(",");
	  $("#store_num").html(storeIdsArray.length);
  }
  </script>
   <style type='text/css'>
  	.selected {
  		border:1px solid #D7D7D7;
  		width:50px ;
  		height: 20px ;
 		border-radius: 8px;
 		margin:10px 5px ;
 		padding: 7px 20px ;
 		background-color: #008EE6 ;
 		color: white ;
	}
	.unselected {
		border:1px solid #D7D7D7;
  		width:50px ;
  		height: 20px ;
 		border-radius: 8px;
 		margin:10px 5px ;
 		padding: 7px 20px ;
	}
  </style>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tGoodsActController.do?doUpdate" tiptype="4" callback="backList">
	<div style="display: inline-block;position: relative;width: 100%;">
  		<div style="width: 50%;display: inline-block;">
		<input id="id" name="id" type="hidden" value="${tGoodsActPage.id }">
		<input id="createName" name="createName" type="hidden" value="${tGoodsActPage.createName }">
		<input id="createBy" name="createBy" type="hidden" value="${tGoodsActPage.createBy }">
		<input id="createDate" name="createDate" type="hidden" value="${tGoodsActPage.createDate }">
		<input id="updateName" name="updateName" type="hidden" value="${tGoodsActPage.updateName }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tGoodsActPage.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tGoodsActPage.updateDate }">
		<input id="status" name="status" type="hidden" value="${tGoodsActPage.status }">
		<input id="valid" name="valid" type="hidden" value="${tGoodsActPage.valid }">
		<input id="auditStatus" name="auditStatus" type="hidden" value="${tGoodsActPage.auditStatus }">
		<input id="retailerId" name="retailerId" type="hidden" value="${tGoodsActPage.retailerId }">
		<input id="newsId" name="newsId" type="hidden" value="${tGoodsActPage.newsId }">
		<input id="actType" name="actType" type="hidden" value="MS">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td colspan="2" height="40px" style="padding-left: 10px">
							<h2>商品活动<c:if test="${empty view }">修改</c:if></h2>
						</td>
					</tr>
					
				<tr>
					<td align="right" height="40px" width="120px">
						<label class="Validform_label">
							活动类型:
						</label>
						</td>
						<td>
						<span class="selected" id="actType_MS" onclick="selectActType('MS')" >秒杀</span>
						<span class="unselected" id="actType_XSZK" onclick="selectActType('XSZK')" >限时折扣</span>
						<span class="unselected" id="actType_ZB" onclick="selectActType('ZB')" >直播</span>
					</td>
				</tr>
					
					<tr>
						<td align="right" width="120px">
							<label class="Validform_label">
								活动名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="title" name="title" type="text" style="width: 350px" class="inputxt" datatype="*"
										       value='${tGoodsActPage.title}'>
							<span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">活动名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								开始时间:
							</label>
						</td>
						<td class="value">
									  <input id="beginTime" name="beginTime" type="text" style="width: 150px"  class="Wdate"   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:00'})"
						      						 value='<fmt:formatDate value='${tGoodsActPage.beginTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
							<span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">开始时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结束时间:
							</label>
						</td>
						<td class="value">
							  <input id="endTime" name="endTime" type="text" style="width: 150px"  class="Wdate"   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})"
		      						 value='<fmt:formatDate value='${tGoodsActPage.endTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
							<span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束时间</label>
						</td>
					</tr>
				<tr>
				 <td align="right">
					<label class="Validform_label">
						分配店铺:
					</label>
				</td>
				<td class="value" colspan="3">
					已选择：
					<span id="store_num" style="font-weight: bold;">0</span>个店铺
					<input id="storeIds" type="hidden" name="storeIds" value="${tGoodsActStoreIds}"/>
					<span style="color: red">*</span>
					<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findStores()" ><span class="icon-search l-btn-icon-left">选择店铺</span></a>
				</td>
				</tr>
					<tr>
					<td align="right" width="120px">
						<label class="Validform_label">
							活动规则:
						</label>
					</td>
					<td class="value">
						<textarea id="remark" name="remark" rows="10" style="width: 95%" datatype="*" placeholder="活动规则说明">${tGoodsActPage.remark}</textarea>
					     	 <span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">活动规则</label>
					</td>
				</tr>
				<tr id="zb" style="display: none;">
					<td align="right" width="120px">
						<label  class="Validform_label">
							直播间地址:
						</label>
					</td>
					<td class="value">
				     	<input id="liveUrl" name="liveUrl" type="text" style="width: 400px" maxlength="200" class="inputxt" value='${tGoodsActPage.liveUrl}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">直播间id</label>
					</td>
				</tr>
				<tr height="150">
					<td align="right">
						<label class="Validform_label">
							封面图片:
						</label>
					</td>
					<td class="value">
							<img alt="" src="${tGoodsActPage.coverPic }" id="coverPic" height="150">
							<label class="Validform_label" style="display: none;">封面图片</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="height: 40px;text-align: center;">
					<c:if test="${empty view }">
						<a href="javascript:save()" class="easyui-linkbutton l-btn" id="btn_sub" iconCls="icon-save">保存</a>&nbsp;&nbsp;
					</c:if>
						<a href="javascript:goBack()" class="easyui-linkbutton l-btn"  iconCls="icon-back">返回</a>
					</td>
				</tr>
			</table>
			</div>
			<div region="east" style="width: 50%;min-height:700px;display: inline-block;position: absolute;" split="true">
				<div tools="#tt" class="easyui-panel" title='活动商品' style="padding: 10px;" fit="true" border="false" id="act_goods_list"></div>
			</div>
		</div>	
	</t:formvalid>
 </body>
  <script src = "webpage/com/buss/goods/tGoodsAct.js?v=1.11"></script>	
  <script type="text/javascript">
  	var goodsActId = "${tGoodsActPage.id}";
  	var view = "${view}";
  	var page = "${page}";
  	var add = "";
  </script>	