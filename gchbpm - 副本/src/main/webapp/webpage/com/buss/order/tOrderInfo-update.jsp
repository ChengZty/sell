<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>订单信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tOrderInfoController.do?doUpdate">
					<input id="id" name="id" type="hidden" value="${tOrderInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tOrderInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tOrderInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tOrderInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tOrderInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tOrderInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tOrderInfoPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tOrderInfoPage.status }">
					<input id="orderStatus" name="orderStatus" type="hidden" value="${tOrderInfoPage.orderStatus }">
					<input id="toGuideId" name="toGuideId" type="hidden" value="${tOrderInfoPage.toGuideId }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">订单号:</label>
			</td>
			<td class="value">
		     	 <input id="orderNo" name="orderNo" type="text" style="width:150px" readonly="readonly"  value='${tOrderInfoPage.orderNo}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单号</label>
			</td>
			<td align="right">
				<label class="Validform_label">下单时间:</label>
			</td>
			<td class="value">
					  <input id="orderTime" name="orderTime" type="text" style="width: 150px" readonly="readonly"
		      						 class="Wdate"  value='<fmt:formatDate value='${tOrderInfoPage.orderTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">下单时间</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">件数:</label>
			</td>
			<td class="value">
		     	 <input id="quantityAmount" name="quantityAmount" type="text" style="width: 150px" readonly="readonly"  value='${tOrderInfoPage.quantityAmount}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">件数</label>
			</td>
			<td align="right">
				<label class="Validform_label">支付帐号:</label>
			</td>
			<td class="value">
		     	 <input id="payAccount" name="payAccount" type="text" style="width: 150px" readonly="readonly"  value='${tOrderInfoPage.payAccount}'>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">运费:</label>
			</td>
			<td class="value">
		     	 <input id="fareAmount" name="fareAmount" type="text" style="width: 150px" readonly="readonly"  value='${tOrderInfoPage.fareAmount}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">运费</label>
			</td>
			<td align="right">
				<label class="Validform_label">商品总额:</label>
			</td>
			<td class="value">
		     	 <input id="goodsAmount" name="goodsAmount" type="text" style="width: 150px" readonly="readonly"  value='${tOrderInfoPage.goodsAmount}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">商品总额</label>
			</td>
		</tr>
			</table>
			<div style="width: auto;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tOrderInfoController.do?tOrderDetails&id=${tOrderInfoPage.id}&orderStatus=${tOrderInfoPage.orderStatus}" icon="icon-search" title="订单明细" id="tOrderDetail"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
		<table style="display:none">
		<tbody id="add_tOrderDetail_table_template">
			<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left">
					  	<input name="tOrderDetails[#index#].goodsName" maxlength="50" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">商品名称</label>
				  </td>
				  <td align="left">
					  	<input name="tOrderDetails[#index#].quantity" maxlength="12" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">件数</label>
				  </td>
				  <td align="left">
					  	<input name="tOrderDetails[#index#].priceOriginal" maxlength="12" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">原价</label>
				  </td>
				  <td align="left">
					  	<input name="tOrderDetails[#index#].priceNow" maxlength="12" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">现价</label>
				  </td>
				  <!--  
				  <td align="left">
					  	<input name="tOrderDetails[#index#].goodsDescription" maxlength="200" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">商品描述</label>
				  </td>
				  <td align="left">
					  	<input name="tOrderDetails[#index#].goodsPic" maxlength="100" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">图片</label>
				  </td>
				  <td align="left">
					  	<input name="tOrderDetails[#index#].address" maxlength="100" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">地址</label>
				  </td>
				  -->
				  <td align="left">
					  	<input name="tOrderDetails[#index#].fare" maxlength="12" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">运费</label>
				  </td>
				  
				  <td align="left">
					  	<input name="tOrderDetails[#index#].discount" maxlength="5" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">折扣</label>
				  </td>
				  <td align="left">
					  	<input name="tOrderDetails[#index#].pricePreferential" maxlength="12" 
					  		type="text"   style="width:120px;"
					  		>
					  <label class="Validform_label" style="display: none;">优惠价</label>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/com/buss/order/tOrderInfo.js"></script>	