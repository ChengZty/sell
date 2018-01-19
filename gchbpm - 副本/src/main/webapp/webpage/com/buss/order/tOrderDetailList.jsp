<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <!-- 待付款1，已发货3,已完成4，已退款8，已取消9 -->
  <t:datagrid name="tOrderDetailList${order_status}" checkbox="true" fitColumns="false" extendParams="nowrap:false," sortName="createDate" sortOrder="desc" title="订单明细" 
  actionUrl="tOrderDetailController.do?datagrid&order_status=${order_status }" idField="id" fit="true" queryMode="group" onLoadSuccess="mergeColumsFun">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="主单ID"  field="orderId"  hidden="true"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  query="true"  queryMode="single" width="110"></t:dgCol>
   <t:dgCol title="子订单号"  field="subOrderNo" hidden="true"  queryMode="single" width="130"></t:dgCol>
   <t:dgCol title="图片"  field="goodsPic"  image="true" imageSize="80,80"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"    queryMode="single" width="120" ></t:dgCol>
   <t:dgCol title="款号"  field="goodsCode"    queryMode="single" width="80" ></t:dgCol>
   <t:dgCol title="规格"  field="specInfo"    queryMode="single" width="80" ></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"  hidden="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="售价"  field="priceNow"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="数量"  field="quantity"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="商品金额"  field="goodsAmount"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="订单总额"  field="orderAmount" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="优惠金额"  field="ticketPreferential"  queryMode="single"  width="60"></t:dgCol>
   <%-- <c:if test="${order_status =='1'}"> --%><%--待付款 --%>
   <%-- <t:dgCol title="应付总额"  field="payAmount"  queryMode="single"  width="60"></t:dgCol> --%>
   <%-- </c:if>
   <c:if test="${order_status !='1'}"> --%><%--不是待付款 --%>
   <t:dgCol title="实付总额"  field="payAmount"  queryMode="single"  width="60"></t:dgCol>
   <%-- </c:if> --%>
   <t:dgCol title="买家手机"  field="userPhone"  query="true"  queryMode="single" width="90" ></t:dgCol>
   <c:if test="${order_status =='1' || order_status =='9'}">
   <t:dgCol title="下单时间"  field="addTime" query="true" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss"  width="130"></t:dgCol>
   </c:if>
   <c:if test="${order_status !='1' && order_status !='9'}"><%--除了待付款和已取消 --%>
   <t:dgCol title="下单时间"  field="addTime" formatter="yyyy-MM-dd hh:mm:ss"  width="130"></t:dgCol>
   <t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="支付方式"  field="payMethod"     queryMode="single"  ></t:dgCol>
   </c:if>
   <c:if test="${order_status =='3'}"><%--已发货 --%>
   <t:dgCol title="物流名称"  field="deliveryName"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="快递单号"  field="deliveryNo"    queryMode="single"  width="110"></t:dgCol>
   <t:dgCol title="收货人姓名"  field="reciverName"     queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="收货人电话"  field="reciverPhone"     queryMode="single" width="90" ></t:dgCol>
   <t:dgCol title="收货人地址"  field="reciverDetailInfo"     queryMode="single"  width="100"></t:dgCol>
   </c:if>
   <c:if test="${order_status =='9'}"><%--已取消 --%>
   <t:dgCol title="取消时间"  field="closeTime" formatter="yyyy-MM-dd hh:mm:ss"   width="130"></t:dgCol>
   </c:if>
   <t:dgCol title="导购"  field="guideName" query="true" queryMode="single" width="60"  ></t:dgCol>
   <t:dgCol title="导购手机"  field="guidePhone" query="true"   queryMode="single" width="90" ></t:dgCol>
   <t:dgCol title="导购店铺"  field="storeName"  queryMode="single" width="90"  ></t:dgCol>
   <c:if test="${order_status =='1'}"><%--待付款 --%>
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
   <t:dgFunOpt funname="cancelOrder(orderId)" title="取消订单"></t:dgFunOpt>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="tOrderInfoController.do?goView" funname="goViewBySelect"></t:dgToolBar>
   <t:dgToolBar title="导出订单" icon="icon-putout" url="tOrderDetailController.do?exportDetailsXls&isTest=0&orderStatus=${order_status }" funname="ExportDetailsXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/order/tOrderDetailList.js?v=1.03"></script>	
 <script src = "webpage/com/buss/order/mergeColums.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 		//支付时间
		$("#tOrderDetailList${order_status}tb").find("input[name='payTime_begin']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${start_time}")
		.attr("id","payTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'payTime_end\')}'});});
		$("#tOrderDetailList${order_status}tb").find("input[name='payTime_end']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${end_time}")
		.attr("id","payTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'payTime_begin\')}'});});
		//下单时间
		$("#tOrderDetailList${order_status}tb").find("input[name='addTime_begin']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${start_time}")
		.attr("id","addTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTime_end\')}'});});
		$("#tOrderDetailList${order_status}tb").find("input[name='addTime_end']").attr("class","Wdate").attr("style","height:24px;width:90px;").val("${end_time}")
		.attr("id","addTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTime_begin\')}'});});
 });
 
 //关闭订单
 function cancelOrder(orderId){
	 $.dialog.confirm('确定关取消订单吗', function(){
		 $.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : 'tOrderInfoController.do?cancelOrder&id='+orderId,// 请求的action路径
				error : function() {// 请求失败处理函数
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
					var msg = d.msg;
						tip(msg);
						reloadTable();
					}
					
				}
			});
		}, function(){
		});
 }
 
//合并列
 function mergeColumsFun(data){
 	var arr = data.rows;
 	if(arr.length>0){
 		var field = "orderId";//判断合并的字段（连续并且相同则合并）
 		var mergeFields =["ck","orderNo","orderAmount","payAmount","ticketPreferential","userPhone","addTime","guideName","guidePhone","storeName"];//合并的字段
 		<c:if test="${order_status =='1'}">
 		mergeFields.push("opt");
 		</c:if>	
 		<c:if test="${order_status =='3'}">
 		mergeFields.push("deliveryName");
 		mergeFields.push("deliveryNo");
 		mergeFields.push("reciverName");
 		mergeFields.push("reciverPhone");
 		mergeFields.push("reciverDetailInfo");
 		</c:if>	
 		<c:if test="${order_status =='9'}">
 		mergeFields.push("colseTime");
 		</c:if>	
 		<c:if test="${order_status !='1' && order_status !='9'}">
 		mergeFields.push("payTime");
 		mergeFields.push("payMethod");
 		</c:if>	
 		gridname='tOrderDetailList${order_status}';
 		mergeColums(arr,field,mergeFields,gridname);//合并列
 		//绑定多行选中事件	
 		 dealMultiSelect("orderNo");
 	}
 }
 </script>