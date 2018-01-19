<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>退款退货</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tRefundReturnController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tRefundReturnPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tRefundReturnPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tRefundReturnPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tRefundReturnPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tRefundReturnPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tRefundReturnPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tRefundReturnPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tRefundReturnPage.status }">
					<input id="orderId" name="orderId" type="hidden" value="${tRefundReturnPage.orderId }">
					<input id="orderNo" name="orderNo" type="hidden" value="${tRefundReturnPage.orderNo }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tRefundReturnPage.retailerId }">
					<input id="retailerPhone" name="retailerPhone" type="hidden" value="${tRefundReturnPage.retailerPhone }">
					<input id="userId" name="userId" type="hidden" value="${tRefundReturnPage.userId }">
					<input id="specGoodsId" name="specGoodsId" type="hidden" value="${tRefundReturnPage.specGoodsId }">
					<input id="addTime" name="addTime" type="hidden" value="${tRefundReturnPage.addTime }">
					<input id="adminTime" name="adminTime" type="hidden" value="${tRefundReturnPage.adminTime }">
					<input id="reasonId" name="reasonId" type="hidden" value="${tRefundReturnPage.reasonId }">
					<input id="reasonInfo" name="reasonInfo" type="hidden" value="${tRefundReturnPage.reasonInfo }">
					<input id="picInfo" name="picInfo" type="hidden" value="${tRefundReturnPage.picInfo }">
					<input id="buyerRemark" name="buyerRemark" type="hidden" value="${tRefundReturnPage.buyerRemark }">
					<input id="sellerRemark" name="sellerRemark" type="hidden" value="${tRefundReturnPage.sellerRemark }">
					<input id="adminRemark" name="adminRemark" type="hidden" value="${tRefundReturnPage.adminRemark }">
					<input id="delayDays" name="delayDays" type="hidden" value="${tRefundReturnPage.delayDays }">
					<input id="receiveRemark" name="receiveRemark" type="hidden" value="${tRefundReturnPage.receiveRemark }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							订单号:
						</label>
					</td>
					<td class="value">
					     	 <input id="subOrderNo" name="subOrderNo" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单号</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							退款号:
						</label>
					</td>
					<td class="value">
					     	 <input id="refundNo" name="refundNo" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退款号</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="userName" name="userName" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户姓名</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							用户手机:
						</label>
					</td>
					<td class="value">
					     	 <input id="userPhone" name="userPhone" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户手机</label>
						</td>
					</tr>
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
							商品数量:
						</label>
					</td>
					<td class="value">
					     	 <input id="goodsNum" name="goodsNum" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品数量</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							退款金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="refundAmount" name="refundAmount" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退款金额</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							卖家处理状态:
						</label>
					</td>
					<td class="value">
					     	 <input id="sellerStatus" name="sellerStatus" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">卖家处理状态</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							退款状态:
						</label>
					</td>
					<td class="value">
					     	 <input id="refundStatus" name="refundStatus" type="text" style="width: 150px"  >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退款状态</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							卖家处理时间:
						</label>
					</td>
					<td class="value">
							   <input id="sellerTime" name="sellerTime" type="text" style="width: 150px" 
					      						 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">卖家处理时间</label>
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
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/refund/tRefundReturn.js"></script>		