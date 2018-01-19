<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tMqErrorList" checkbox="true" fitColumns="false" title="MQ通知" actionUrl="tMqErrorController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="addTime" sortOrder="desc">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="addTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="订单ID"  field="orderId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="订单类型"  field="orderType"  replace="充值单_1,订单_2,退款单_3"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="队列名称"  field="queueName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="方法名"  field="method"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="mq消息"  field="mqMsg"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="错误信息"  field="errorMsg"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="dealTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="处理状态"  field="dealStatus" replace="待处理_0,处理成功_1,处理失败_2" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="doDeal(id)" title="发送消息" exp="dealStatus#eq#0"></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="tMqErrorController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
//发送消息
 function doDeal(id){
 	 var url = "tMqErrorController.do?doDeal&id="+id;
 	 $.ajax({
 			async : false,
 			cache : false,
 			type : 'POST',
 			url : url,// 请求的action路径
 			error : function() {// 请求失败处理函数
 			},
 			success : function(data) {
 				var d = $.parseJSON(data);
 				if (d.success) {
 					tip(d.msg);
 					reloadTable();
 				}
 			}
 		});
 }
 </script>