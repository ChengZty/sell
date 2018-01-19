<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
</script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="userList" title="零售商列表" actionUrl="userController.do?storeDatagrid&retailer_Type=1" fitColumns="false" extendParams="nowrap:false,"
   idField="id" queryMode="group" sortName="createDate" sortOrder="desc">
	<t:dgCol title="ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true" width="100"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true" width="100"></t:dgCol>
	<t:dgCol title="所属区域" field="area" width="200"></t:dgCol>
	<t:dgCol title="省" field="provinceId" hidden="true"></t:dgCol>
	<t:dgCol title="馆" field="cityId" hidden="true"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="300"></t:dgCol>
	<t:dgFunOpt funname="getVisibleRetailerGoodsList(id,realName)" title="零售商商品设置" ></t:dgFunOpt>
	<t:dgFunOpt funname="getInvisibleCloudGoodsList(id,realName)" title="云商商品设置" ></t:dgFunOpt>
</t:datagrid>
</div>
</div>
 <div region="east" style="width: 600px;" split="true">
	<div tools="#tt" class="easyui-panel" title='零售商商品设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<script type="text/javascript">
function getVisibleRetailerGoodsList(id,realName) {
	$("#function-panel").panel(
		{
			title : '零售商商品设置:' + realName,
			href:"tRetailerRelationController.do?tRetailerList&rId="+id
		}
	);
}

function getInvisibleCloudGoodsList(id,realName) {
	$("#function-panel").panel(
		{
			title : '云商商品设置:' + realName,
			href:"tRetailerRelationController.do?tCloudList&rId="+id
		}
	);
}

</script>
