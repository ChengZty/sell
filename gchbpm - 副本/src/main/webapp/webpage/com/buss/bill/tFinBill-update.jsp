<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>财务结算表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tFinBillController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tFinBillPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								流水时间:
							</label>
						</td>
						<td class="value">
									  <input id="addTime" name="addTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value='<fmt:formatDate value='${tFinBillPage.addTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">流水时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								订单编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderSn" name="orderSn" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.orderSn}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								业务日期:
							</label>
						</td>
						<td class="value">
									  <input id="businessDate" name="businessDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tFinBillPage.businessDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								业务类型:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="businessType" type="list"
										typeGroupCode="bill_bus_type" defaultVal="${tFinBillPage.businessType}" hasLabel="false"  title="业务类型"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">业务类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								订单金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderAmount" name="orderAmount" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.orderAmount}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								系统冻结:
							</label>
						</td>
						<td class="value">
						     	 <input id="frozenAmount" name="frozenAmount" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.frozenAmount}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">系统冻结</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								顾客应收:
							</label>
						</td>
						<td class="value">
						     	 <input id="custAmount" name="custAmount" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.custAmount}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">顾客应收</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								零售商应收:
							</label>
						</td>
						<td class="value">
						     	 <input id="storeAmount" name="storeAmount" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.storeAmount}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">零售商应收</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								导购应收:
							</label>
						</td>
						<td class="value">
						     	 <input id="guideAmount" name="guideAmount" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.guideAmount}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">导购应收</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								系统应收:
							</label>
						</td>
						<td class="value">
						     	 <input id="systemAmount" name="systemAmount" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.systemAmount}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">系统应收</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								帮手应收:
							</label>
						</td>
						<td class="value">
						     	 <input id="helperAmount" name="helperAmount" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.helperAmount}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">帮手应收</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								顾客ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="custId" name="custId" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.custId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">顾客ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								顾客姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="custName" name="custName" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.custName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">顾客姓名</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								导购ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="guideId" name="guideId" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.guideId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">导购ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								导购姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="guideName" name="guideName" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.guideName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">导购姓名</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								导购所属零售商ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="storeId" name="storeId" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.storeId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">导购所属零售商ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								导购所属零售商姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="storeName" name="storeName" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.storeName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">导购所属零售商姓名</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								货品所属零售商ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsStoreId" name="goodsStoreId" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.goodsStoreId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">货品所属零售商ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								货品所属零售商名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsStoreName" name="goodsStoreName" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.goodsStoreName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">货品所属零售商名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="tbStatus" name="tbStatus" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.tbStatus}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">状态</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								账单编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="billSn" name="billSn" type="text" style="width: 150px" class="inputxt"  value='${tFinBillPage.billSn}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">账单编号</label>
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
  <script src = "webpage/com/buss/bill/tFinBill.js"></script>		