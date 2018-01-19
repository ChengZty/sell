<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>优惠券</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<!-- 七牛上传 start -->
	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	<script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	<script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	<!-- 七牛上传 end -->
  <style type="text/css">
  	.uploadify-button {
		background-color: #505050;
		border-radius: 30px;
		color: #FFF;
		text-align: center;
		font: bold 12px Arial, Helvetica, sans-serif;
	}
	.hide_td{
		display: none;
	}
  </style>
  <script type="text/javascript">
	  var domain = '${domain}';//七牛domain
	  var directory = '${directory}';//目录
	  var isView = "0";
	//是否改动过品牌和商品
	  var hasChanged = false;
	  var pageAction = "add";
  </script>
 </head>
 <body>
  <t:formvalid formid="ticketInfo" dialog="false" layout="table" action="tTicketInfoController.do?doAdd" tiptype="4" beforeSubmit="checkData()" callback="back">
  <div style="display: inline-block;position: relative;width: 100%;">
  	<div style="width: 60%;display: inline-block;">
					<input id="id" name="id" type="hidden" value="${tTicketInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tTicketInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tTicketInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tTicketInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tTicketInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tTicketInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tTicketInfoPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="batchNo" name="batchNo" type="hidden" value="${tTicketInfoPage.batchNo }">
<%-- 					<input id="auditorId" name="auditorId" type="hidden" value="${tTicketInfoPage.auditorId }"> --%>
					<input id="retailerId" name="retailerId" type="hidden" value="${retailerId }">
					<input id="ticketStatus" name="ticketStatus" type="hidden" value="1"><!-- 待审核 -->
					<input id="usingRange" name="usingRange" type="hidden" value="1"><!-- 全馆 -->
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" >
				<tr>
					<td align="right" width="120px">
						<label class="Validform_label">
							券名:
						</label>
					</td>
					<td class="value" colspan="3">
					     	 <input id="ticketName" name="ticketName" placeholder="不超过20字" type="text" style="width: 350px" class="inputxt" maxlength="20" datatype="*">
					     	 <span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">券名</label>
						</td>
				</tr>
				<tr>
					<td align="right" width="130px">
						<label class="Validform_label">
							缩略图:
						</label>
					</td>
					<td class="value" colspan="3">
				     	 <div id="container_pic" >
                            <a  id="pic_btn_div" href="#" style="width: 200px">
                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
                            </a>
                            <div id="pic_div" style="min-height: 100px;">
                            	<img src='${tTicketInfoPage.picUrl}'  height='100' ></img>
                            </div>
	                      </div>
				     	 <input id="picUrl" name="picUrl" type="hidden" value="${tTicketInfoPage.picUrl}">
						<input type="hidden" name="smallPic" id="smallPic">
			     		<div><span class="Validform_checktip">图片会出现在导购分享的微信消息中，尺寸推荐：180*180</span></div>
						</td>
				</tr>
				<tr height="40px">
					<td align="right">
						<label class="Validform_label">
							券类型:
						</label>
					</td>
					<td class="value" id="type_td" colspan="3">
						<input type="radio" name="type" checked="checked" value="1">红包&nbsp;&nbsp;
						<input type="radio" name="type" value="2">满减券
<%-- 						<t:dictSelect field="type" defaultVal="1" hasLabel="false" typeGroupCode="tkt_type" type="radio"></t:dictSelect> --%>
						<span style="color: red">*</span>
<!-- 						<span class="Validform_checktip"></span> -->
<!-- 						<label class="Validform_label" style="display: none;">类型</label> -->
					</td>
					<td align="right" class="hide_td" width="120px">
						<label class="Validform_label">
							最多使用数量:
						</label>
					</td>
					<td class="value hide_td">
						<input id="sheetLimit" name="sheetLimit" value="1" type="text" style="width: 50px" maxlength="3" class="inputxt" >
						<span style="color: red">*</span>
						<span class="Validform_checktip">仅限满减券(0则表示不限制)</span>
						<label class="Validform_label" style="display: none;">最多使用数量</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							面额:
						</label>
					</td>
					<td class="value" width="300px">
					     	 <input id="faceValue" name="faceValue" type="text" style="width: 50px" class="inputxt" datatype="d">
					     	 <span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">面额</label>
					</td>
					<td align="right" width="120px">
						<label class="Validform_label">
							使用条件:
						</label>
					</td>
					<td class="value">
						<span id="span1">无条件使用</span>
						<span id="span2" style="display: none">
					     	 <input id="leastMoney" name="leastMoney" type="text" placeholder="用券最低金额/件数" style="width: 150px" class="inputxt">
					     	 <span style="color: red">*</span>&nbsp;&nbsp;
					     	 <!-- 用券类型 -->
					     	 <input type="radio" name="useType"  value="1"  >金额
					     	 <input type="radio" name="useType"  value="2" >件数
						</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">使用条件</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							起始时间:
						</label>
					</td>
					<td class="value">
							<input id="beginTime" name="beginTime" type="text" style="width: 150px" datatype="*"
					      						class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:00'})" >    
					      	<span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起始时间</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							结束时间:
						</label>
					</td>
					<td class="value">
							<input id="endTime" name="endTime" type="text" style="width: 150px" datatype="*"
					      						class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})" >    
					      	<span style="color: red">*</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束时间</label>
						</td>
					</tr>
				<tr>
					<td align="right" >
						<label class="Validform_label">
							总张数:
						</label>
					</td>
					<td class="value" colspan="3">
					     	<input id="sheetTotal" name="sheetTotal" placeholder="不填写则不限量" type="text" style="width: 150px" maxlength="5" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">总张数</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							可用品牌/商品:
						</label>
					</td>
					<td class="value" colspan="3">
						已选择：
						<span id="brands_num" style="font-weight: bold;">0</span>个品牌
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="getSelectedBrandsGoods('2','${retailerId }')" ><span class="icon-search l-btn-icon-left">选择品牌</span></a>
						&nbsp;&nbsp;&nbsp;
						<span id="goods_num" style="font-weight: bold;">0</span>件商品
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="getSelectedBrandsGoods('1','${retailerId }')" ><span class="icon-search l-btn-icon-left">选择商品</span></a>
						<span class="Validform_checktip"></span>
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
						<input id="storeIds" type="hidden" name="storeIds"/>
						<span style="color: red">*</span>
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findStores()" ><span class="icon-search l-btn-icon-left">选择店铺</span></a>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							第三方推送主标题:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="pushTitle" name="pushTitle" type="text" placeholder="10个字以内" style="width: 350px" class="inputxt" maxlength="10">
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							第三方推送副标题:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="pushSubtitle" name="pushSubtitle" type="text" placeholder="10个字以内" style="width: 350px" class="inputxt" maxlength="10">
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							使用规则:
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea style="width: 95%;height: 100px" name="remark"  maxlength="500"></textarea>
						<span style="color: red">*</span>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">使用说明</label>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="height: 40px;text-align: center;">
						<a href="javascript:sub()" class="easyui-linkbutton l-btn" id="btn_sub" iconCls="icon-save">保存</a>
						&nbsp;&nbsp;
						<a href="javascript:back()" class="easyui-linkbutton l-btn"  iconCls="icon-back">返回</a>
					</td>
				</tr>
			</table>
		</div>
		<div region="east" style="width: 40%;min-height:100%;display: inline-block;position: absolute;" split="true">
			<div tools="#tt" class="easyui-panel" title='已选品牌/商品' style="padding: 10px;" fit="true" border="false" id="brand_goods"></div>
		</div>
	</div>
</t:formvalid>
 </body>
  <script src = "webpage/com/buss/ticket/tTicketInfo.js?v=1.17"></script>		
