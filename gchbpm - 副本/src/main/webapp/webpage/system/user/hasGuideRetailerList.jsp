<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
		<t:datagrid name="userList" title="零售商列表" actionUrl="userController.do?hasGuideRetailerDatagrid" 
		    fitColumns="false" extendParams="nowrap:false," idField="id" queryMode="group" >
			<t:dgCol title="ID" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
			<t:dgCol title="零售商类型" field="retailerType" replace="零售商 人货_1,零售商 人_3"></t:dgCol>
			<t:dgCol title="所属区域" field="area" query="true"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="120"></t:dgCol>
   			<t:dgFunOpt funname="getGuideWordsList(id,realName)" title="导购排名话术" ></t:dgFunOpt>
		</t:datagrid>
 </div>
 </div>
 <div region="east" style="width: 400px;" split="true">
	<div tools="#tt" class="easyui-panel" title='导购排名话术' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<script type="text/javascript">
function getGuideWordsList(id,realName) {
	$("#function-panel").panel(
		{
			title : '零售商名称:' + realName,
			href:"tGuideRankWordsController.do?allList&retailer_Id=" + id
		}
	);
// 	$('#function-panel').panel("refresh" );
}
</script>