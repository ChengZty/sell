<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>订单明细</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/goods/css/member.css" />
<style type="text/css">
dd {
	margin: 0
}
</style>
<script type="text/javascript">
	//编写自定义JS代码
	$(document).ready(function(){
		var com="${tOrderInfo.deliveryCode }";
		var num="${tOrderInfo.deliveryNo }";//快递号
		if(num!=""&&num!="null"){
			var to="${tOrderInfo.reciverProvince}${tOrderInfo.reciverCity }${tOrderInfo.reciverRegion	}";
			var params = "&com="+com+"&num="+num+"&to="+to;
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : "postOrderController.do?queryOrder"+params,// 请求的action路径
				error : function() {// 请求失败处理函数
				
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if(d.success){
						var dat = d.msg;
						var d_info = $.parseJSON(dat);
						var state  = d_info.state;//物流单签收状态0：在途，1：揽件，2：疑难，3：签收，4：退签，5：派件，6：退回
						var arr  = d_info.data;//物流记录
						var html = "";
						for(var i=0;i<arr.length;i++){
							html+=""+arr[i].time+"&nbsp;&nbsp;"+arr[i].context+"</br>";
						}
	// 					alert(state);
						$("#wuliu").html(html);
					}else{
// 						alert(d.msg);
					}
				}
			});
		}
 			
 });
</script>
</head>
<body>
	<div class="layout" style="width: 1000px; margin: 10px auto;">
		<div class="wrap-shadow">
			<div class="wrap-all ncu-order-view">
				<h2>订单详情</h2>
				<dl class="box">
					<dt>订单状态：</dt>
					<dd>
						<strong><span style="color: #36C">
						<c:choose>
							<c:when test="${tOrderInfo.orderStatus == '1'}">待付款</c:when>
							<c:when test="${tOrderInfo.orderStatus == '2'}">待发货</c:when>
							<c:when test="${tOrderInfo.orderStatus == '3'}">已发货</c:when>
							<c:when test="${tOrderInfo.orderStatus == '4'}">已完成</c:when>
							<c:when test="${tOrderInfo.orderStatus == '8'}">已退款</c:when>
							<c:when test="${tOrderInfo.orderStatus == '9'}">已取消</c:when>
						</c:choose>
						</span>
						</strong>
					</dd>
					<dt>订单号：</dt>
					<dd>${tOrderInfo.orderNo }</dd> 
<%-- 					<dd>${fn:substring(tOrderDetailPage.subOrderNo,6,17) }</dd>--%>
					<dt>下单时间：</dt>
					<dd>${fn:substring(tOrderInfo.orderTime,0,19)}</dd>
				</dl>
				<dl>
				<dt style="clear: both">订单留言：</dt>
					<dd style="width: 90%;">
						<span style="color: red">${tOrderInfo.orderMessage}</span>
					</dd>
				</dl>
				<dl>
				<dt style="clear: both">导购备注：</dt>
					<dd style="width: 90%;">
						<span style="color: red">${tOrderInfo.guideRemark}</span>
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
							<th>运费</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="tOrderDetail" items="${tOrderDetailList }">
							<tr>
								<%-- <td width="70px">
									<div class="goods-pic-small">
										<span class="thumb size60"> <img src="${tOrderDetail.goodsPic }" width="60px" height="60px" />
										</span>
									</div></td>
								<td>
									<dl class="goods-name">
										<dt>${tOrderDetail.goodsName }</dt>
									</dl></td> --%>
								<td width="70px"><img src="${tOrderDetail.goodsPic }" width="60px" height="60px" /></td>
								<td class="goods-name" style="text-align: left;">${tOrderDetail.goodsName }</td>
								<td>${tOrderDetail.goodsCode }</td>
								<td>${tOrderDetail.specInfo }</td>
								<td>${tOrderDetail.priceNow }</td>
								<td>${tOrderDetail.quantity }</td>
								<td>${tOrderDetail.goodsAmount}</td>
								<td>${tOrderDetail.fare }</td>
							</tr>
						</c:forEach>
					</tbody>
					<c:if test="${tOrderInfo.orderStatus !='1' && tOrderInfo.orderStatus !='9'}"><!-- 除待支付和已取消状态 -->
					<tfoot>
						<%--<tr>
							<td colspan="20" class="transportation">
								支付运费金额： <b>¥${tOrderInfo.fareAmount } </b>
							</td>
						</tr>
						 <tr>
							<td colspan="20" class="transportation">
								第三方支付商品金额： <b>¥${tOrderInfo.salesPrice } </b>
							</td>
						</tr>
						<tr>
							<td colspan="20" class="transportation">
								G+卡支付商品金额： <b>¥${tOrderInfo.vipMoneyPay } </b>
							</td>
						</tr> --%>
						<tr>
							<td colspan="20" class="transportation">
								总支付金额： <b>¥${tOrderInfo.payAmount} </b>
							</td>
						</tr>
					</tfoot>
					</c:if>
				</table>
				<c:if test="${tOrderInfo.orderStatus !='1' && tOrderInfo.orderStatus !='9'}"><!-- 除待支付和已取消状态 -->
				<dl class="logistics">
					<dt>支付方式：</dt>
					<dd>
						<span>${tOrderInfo.payMethod }</span>&nbsp;
					</dd>
					<dt>支付时间：</dt>
					<dd>
						<span>${fn:substring(tOrderInfo.payTime,0,19)}</span>&nbsp;
					</dd>
				</dl>
				</c:if>
				<h3>物流信息</h3>
				<dl class="logistics">
					<dt>收 货 人：</dt>
					<dd>
						<span>${tOrderInfo.reciverName }</span>&nbsp;
					</dd>
					<dt>手机号码：</dt>
					<dd>
						<span>${tOrderInfo.reciverPhone }</span>&nbsp;
					</dd>
					<dt style="clear: both">收货地址：</dt>
					<dd style="width: 90%;">
						<span>${tOrderInfo.reciverProvince}${tOrderInfo.reciverCity }${tOrderInfo.reciverRegion	}${tOrderInfo.reciverDetailInfo }</span>&nbsp;
					</dd>
				</dl>
				<c:if test="${tOrderInfo.orderStatus !='1' && tOrderInfo.orderStatus !='9'}"><!-- 除待支付和已取消状态 -->
					<dl>
						<c:if test="${tOrderInfo.deliveryType!='0' }">
							<dt>发货方式：</dt>
							<dd>
								<span>
								<c:if test="${tOrderInfo.deliveryType=='1' }">后台发货</c:if>
								<c:if test="${tOrderInfo.deliveryType=='2' }">店铺发货</c:if>
								</span>&nbsp;
							</dd>
							<dt>物流公司：</dt>
							<dd>
								<span>${tOrderInfo.deliveryName }</span>&nbsp;
							</dd>
							<dt>物流单号：</dt>
							<dd><span>${tOrderInfo.deliveryNo }</span>&nbsp;
							</dd>
						</c:if>
						<c:if test="${tOrderInfo.deliveryType=='0' }">
							<dt>发货方式：</dt>
							<dd>
								<span>顾客自提</span>&nbsp;
							</dd>
						</c:if>
					</dl>
				</c:if>
				<dl class="logistics" style="padding-left:5%;" id="wuliu">
				</dl>
				<h3>操作历史</h3>
				<ul class="log-list">
					<c:forEach items="${logList }" var="orderlog">
						<li>
<%-- 						<span class="operator">${orderlog.logUserName }</span> 于 --%>
						<span class="log-time">${fn:substring(orderlog.logTime,0,19)}</span> 订单<span	class="order-status">
							<c:choose>
								<c:when test="${orderlog.logOrderStatus == '1'}">已下单</c:when>
								<c:when test="${orderlog.logOrderStatus == '2'}">已付款</c:when>
								<c:when test="${orderlog.logOrderStatus == '3'}">已发货</c:when>
								<c:when test="${orderlog.logOrderStatus == '4'}">已完成</c:when>
								<c:when test="${orderlog.logOrderStatus == '8'}">已退款</c:when>
								<c:when test="${orderlog.logOrderStatus == '9'}">已取消</c:when>
							</c:choose>
<!-- 							</span> 下一状态：<span	class="order-status"></span> -->
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

</body>