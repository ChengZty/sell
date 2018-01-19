<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tSmsSingleList" checkbox="false" fitColumns="false" title="短信使用情况" actionUrl="tSmsCountInfoController.do?datagrid2" 
		idField="id" fit="true" queryMode="group" sortName="sendDate" sortOrder="desc">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="发送时间" field="sendDate" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="140"></t:dgCol>
			<t:dgCol title="手机号码" field="userName" query="true" queryMode="single" width="90"></t:dgCol>
			<t:dgCol title="发送内容" field="content"   width="450"></t:dgCol>
			<t:dgCol title="类型" field="msgType" replace="验证码_1,确认订单_2,支付_3,发货_4,申请退款_5,退货成功_6" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="短信数量" field="number" formatterjs="getFixedValue" queryMode="group" width="80"></t:dgCol>
		</t:datagrid>
	</div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
	 	$("#tSmsSingleListtb").find("input[name='sendDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","sendDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'sendDate_end\')}'});});
		$("#tSmsSingleListtb").find("input[name='sendDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","sendDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sendDate_begin\')}'});});
 });
 
 function getFixedValue(value,row,index){
	 return -1;
 }
 </script>