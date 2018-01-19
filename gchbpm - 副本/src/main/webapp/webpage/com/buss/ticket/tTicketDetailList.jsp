<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="tTicketDetailList" title="" actionUrl="tTicketInfoController.do?ticketDetailDatagrid&id=${id }" fitColumns="true" extendParams="nowrap:false,"   idField="id" queryMode="group" >
	<t:dgCol title="导购ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="导购姓名"  field="guideName" query="true" width="100"></t:dgCol>
	<t:dgCol title="推送张数" field="total"  width="100"></t:dgCol>
	<t:dgCol title="顾客使用张数" field="usedNum"  width="100"></t:dgCol>
</t:datagrid>
</div>
</div>
 <script type="text/javascript">

 
 </script>
