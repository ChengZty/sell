<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>退款详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/goods/css/member.css" />
<style type="text/css">
dd {
	margin: 0
}
</style>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
	<div class="layout" style="width: 1000px; margin: 10px auto;">
		<div class="wrap-shadow">
			<div class="wrap-all ncu-order-view">
				<h2>退款详情</h2>
				<dl class="box">
					<dt>订单状态：</dt>
					<dd>
						<strong><span style="color: #36C">
						<c:choose>
							<c:when test="${tRefundReturnPage.refundStatus == '1'}">待处理</c:when>
							<c:when test="${tRefundReturnPage.refundStatus == '2'}">等待卖家处理</c:when>
							<c:when test="${tRefundReturnPage.refundStatus == '3'}">商家同意退款</c:when>
							<c:when test="${tRefundReturnPage.refundStatus == '4'}">商家不同意退款</c:when>
							<c:when test="${tRefundReturnPage.refundStatus == '5'}">退款完成</c:when>
						</c:choose>
						</span>
						</strong>
					</dd>
					<dt>退款编号：</dt>
					<dd>${tRefundReturnPage.refundNo }</dd>
					<dt>申请退款金额：</dt>
					<dd>${tRefundReturnPage.refundAmount }</dd>
					<dt>申请时间：</dt>
					<dd>${fn:substring(tRefundReturnPage.addTime,0,19)}</dd>
				</dl>
				<c:if test="${tRefundReturnPage.refundStatus == '5'}">
				<dl class="box">
					<dt>退款方式：</dt>
					<dd>${tRefundReturnPage.payMethodName }</dd>
					<dt>交易号：</dt>
					<dd>${tRefundReturnPage.payNo }</dd>
					<dt>实际退款金额：</dt>
					<dd>${tRefundReturnPage.refundAmountReal }</dd>
					<dt>退款时间：</dt>
					<dd>${fn:substring(tRefundReturnPage.adminTime,0,19)}</dd>
				</dl>
				</c:if>
				<dl>
				<dt style="clear: both">退款原因：</dt>
					<dd style="width: 90%;">
						<span>${tRefundReturnPage.reasonInfo}</span>
					</dd>
				</dl>
				<%-- <dl>
				<dt style="clear: both">买家留言：</dt>
					<dd style="width: 90%;">
						<span>${tRefundReturnPage.buyerRemark}</span>
					</dd>
				</dl>
				<dl>
				<dt style="clear: both">卖家留言：</dt>
					<dd style="width: 90%;">
						<span>${tRefundReturnPage.sellerRemark}</span>
					</dd>
				</dl> --%>
				<dl>
				<dt style="clear: both">退款备注：</dt>
					<dd style="width: 90%;">
						<span>${tRefundReturnPage.adminRemark}</span>
					</dd>
				</dl>
				<h3>订单信息</h3>
				<table class="ncu-table-style">
					<thead>
						<tr>
							<th colspan="2" align="	left">商品名称</th>
							<th>款号</th>
							<th>规格</th>
							<th>单价</th>
							<th>数量</th>
							<th>商品总价</th>
<!-- 							<th>运费</th> -->
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tOrderDetail" items="${tOrderDetailList }">
							<tr>
								<td width="70px"><img src="${tOrderDetail.goodsPic }" width="60px" height="60px" /></td>
								<td class="goods-name" style="text-align: left;">${tOrderDetail.goodsName }</td>
								<td>${tOrderDetail.goodsCode }</td>
								<td>${tOrderDetail.specInfo }</td>
								<td>${tOrderDetail.priceNow }</td>
								<c:if test="${empty tRefundReturnPage.subOrderNo }">
								<td>${tOrderDetail.quantity }</td>
								<td>${tOrderDetail.goodsAmount}</td>
								</c:if>
								<c:if test="${not empty tRefundReturnPage.subOrderNo }">
								<td>${tRefundReturnPage.goodsNum}</td>
								<td>${tRefundReturnPage.refundAmount}</td>
								</c:if>
<%-- 								<td>${tOrderDetail.fare }</td> --%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<h3>操作历史</h3>
				<ul class="log-list">
					<c:forEach items="${logList }" var="orderlog">
						<li>
						<span class="log-time">${fn:substring(orderlog.logTime,0,19)}</span> 订单<span	class="order-status">
							<c:choose>
								<c:when test="${orderlog.logOrderStatus == '2'}">待商家处理</c:when>
								<c:when test="${orderlog.logOrderStatus == '3'}">商家同意</c:when>
								<c:when test="${orderlog.logOrderStatus == '4'}">商家拒绝</c:when>
								<c:when test="${orderlog.logOrderStatus == '5'}">退款完成</c:when>
							</c:choose>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

</body>