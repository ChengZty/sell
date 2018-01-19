<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>订单明细</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOrderDetailController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tOrderDetailPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tOrderDetailPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tOrderDetailPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tOrderDetailPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tOrderDetailPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tOrderDetailPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tOrderDetailPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="orderId" name="orderId" type="hidden" value="${tOrderDetailPage.orderId }">
					<input id="newOrderNo" name="newOrderNo" type="hidden" value="${tOrderDetailPage.newOrderNo }">
					<input id="goodsId" name="goodsId" type="hidden" value="${tOrderDetailPage.goodsId }">
					<input id="returnType" name="returnType" type="hidden" value="${tOrderDetailPage.returnType }">
					<input id="returnReason" name="returnReason" type="hidden" value="${tOrderDetailPage.returnReason }">
					<input id="returnPrice" name="returnPrice" type="hidden" value="${tOrderDetailPage.returnPrice }">
					<input id="returnDelayReason" name="returnDelayReason" type="hidden" value="${tOrderDetailPage.returnDelayReason }">
					<input id="toGuideId" name="toGuideId" type="hidden" value="${tOrderDetailPage.toGuideId }">
					<input id="toRetailerId" name="toRetailerId" type="hidden" value="${tOrderDetailPage.toRetailerId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							商品名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="goodsName" name="goodsName" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品名称</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							件数:
						</label>
					</td>
					<td class="value">
					     	 <input id="quantity" name="quantity" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">件数</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							原价:
						</label>
					</td>
					<td class="value">
					     	 <input id="priceOriginal" name="priceOriginal" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">原价</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							组合价:
						</label>
					</td>
					<td class="value">
					     	 <input id="priceCombination" name="priceCombination" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">组合价</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							现价:
						</label>
					</td>
					<td class="value">
					     	 <input id="priceNow" name="priceNow" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">现价</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							商品描述:
						</label>
					</td>
					<td class="value">
					     	 <input id="goodsDescription" name="goodsDescription" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品描述</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value">
					     	 <input id="goodsPic" name="goodsPic" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图片</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							运费:
						</label>
					</td>
					<td class="value">
					     	 <input id="fare" name="fare" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">运费</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							地址:
						</label>
					</td>
					<td class="value">
					     	 <input id="address" name="address" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">地址</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							物流:
						</label>
					</td>
					<td class="value">
					     	 <input id="delivery" name="delivery" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">物流</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							快递单号:
						</label>
					</td>
					<td class="value">
					     	 <input id="deliveryNo" name="deliveryNo" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">快递单号</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							支付时间:
						</label>
					</td>
					<td class="value">
							   <input id="payTime" name="payTime" type="text" style="width: 150px" 
					      						 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">支付时间</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							买家帐号:
						</label>
					</td>
					<td class="value">
					     	 <input id="buyerAccount" name="buyerAccount" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">买家帐号</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							折扣:
						</label>
					</td>
					<td class="value">
					     	 <input id="discount" name="discount" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">折扣</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							优惠价:
						</label>
					</td>
					<td class="value">
					     	 <input id="pricePreferential" name="pricePreferential" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">优惠价</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							订单状态:
						</label>
					</td>
					<td class="value">
					     	 <input id="orderStatus" name="orderStatus" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单状态</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
