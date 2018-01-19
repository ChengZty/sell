<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tSmsCountInfoList" checkbox="false" fitColumns="false" title="短信使用情况" actionUrl="tSmsCountInfoController.do?datagrid" 
		idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
			<t:dgCol title="id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="批次号" field="batchNo" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="操作人" field="createName" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作时间" field="createDate" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="140"></t:dgCol>
			<t:dgCol title="更新人名称" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="类型" field="type" replace="充值_1,扣除_2,锁定_3,消费_4" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="短信数量" field="number"  queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="备注" field="remark" hidden="true" queryMode="single" width="140"></t:dgCol>
		</t:datagrid>
	</div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
	 	$("#tSmsCountInfoListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","createDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createDate_end\')}'});});
		$("#tSmsCountInfoListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","createDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createDate_begin\')}'});});
 });
 </script>