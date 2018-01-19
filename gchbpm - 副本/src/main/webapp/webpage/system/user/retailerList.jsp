<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
</script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="userList" title="零售商品牌显示列表" actionUrl="userController.do?datagridOfRetailList" fitColumns="false" extendParams="nowrap:false,"
   idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
	<t:dgCol title="所属区域" field="area" ></t:dgCol>
	<t:dgCol title="零售商类型" field="retailerType" replace="零售商_1,云商_2" query="true"></t:dgCol>
	<t:dgCol title="用户状态"  field="userStatus" replace="已激活_1,已停用_0,已锁定_2,待激活_3" ></t:dgCol>
	<t:dgCol title="操作" field="opt" width="150" ></t:dgCol>
	<t:dgFunOpt funname="setShowBrand(id,realName)" exp="userStatus#eq#1" title="设置可见品牌"></t:dgFunOpt>
</t:datagrid>
</div>
</div>
<div region="east" style="width: 750px;" split="true">
<div tools="#tt" class="easyui-panel" title='可见品牌列表' style="padding: 10px;width: 500px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
<script type="text/javascript">
function setShowBrand(id,realName) {
	$("#function-panel").panel(
		{
			title :realName+ ':' + '可见品牌列表',
			href:"tBrandShowController.do?list&retailer_id=" + id
		}
	);
// 	$('#function-panel').panel("refresh" );
}
</script>
