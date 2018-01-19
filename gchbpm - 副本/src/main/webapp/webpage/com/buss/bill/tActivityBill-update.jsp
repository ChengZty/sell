<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动奖励明细</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tActivityBillController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tActivityBillPage.id }">
					<input id="finBillId" name="finBillId" type="hidden" value="${tActivityBillPage.finBillId }">
					<input id="addTime" name="addTime" type="hidden" value="${tActivityBillPage.addTime }">
					<input id="orderTime" name="orderTime" type="hidden" value="${tActivityBillPage.orderTime }">
					<input id="orderNo" name="orderNo" type="hidden" value="${tActivityBillPage.orderNo }">
					<input id="goodsId" name="goodsId" type="hidden" value="${tActivityBillPage.goodsId }">
					<input id="storeType" name="storeType" type="hidden" value="${tActivityBillPage.storeType }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tActivityBillPage.retailerId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								业务日期:
							</label>
						</td>
						<td class="value">
							  <input id="businessDate" name="businessDate" type="text" style="width: 150px" 
				      			class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tActivityBillPage.businessDate}' type="date" pattern="yyyy-MM-dd"/>'>    
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								业务类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="businessType" name="businessType" type="text" style="width: 150px" class="inputxt"  
								value='${tActivityBillPage.businessType}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								子单号:
							</label>
						</td>
						<td class="value">
						     	 <input id="subOrderNo" name="subOrderNo" type="text" style="width: 150px" class="inputxt"  
									 value='${tActivityBillPage.subOrderNo}'>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品所属零售商:
							</label>
						</td>
						<td class="value">
						     	 <input id="toStoreGoodsId" name="toStoreGoodsId" type="text" style="width: 150px" class="inputxt"  
									   value='${tActivityBillPage.toStoreGoodsId}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								导购ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="guideId" name="guideId" type="text" style="width: 150px" class="inputxt"  
									  value='${tActivityBillPage.guideId}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								活动ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="finActId" name="finActId" type="text" style="width: 150px" class="inputxt"  
									   value='${tActivityBillPage.finActId}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								活动奖励:
							</label>
						</td>
						<td class="value">
						     	 <input id="money" name="money" type="text" style="width: 150px" class="inputxt"  
									   value='${tActivityBillPage.money}'>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
