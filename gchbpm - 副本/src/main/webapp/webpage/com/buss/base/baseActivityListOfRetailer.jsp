<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="baseActivityList" checkbox="true" fitColumns="false" title="活动列表" actionUrl="baseActivityController.do?datagrid&content_Id=${content_Id }" 
  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="图片"  field="picUrl"  image="true" imageSize="100,80" queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="活动名称"  field="activityName"  query="true"  queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="子标题"  field="subTitle"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始时间"  field="startTime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerId" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="280"></t:dgCol>
   <t:dgDelOpt title="删除" url="baseActivityController.do?doDel&id={id}" />
   <t:dgFunOpt funname="getRetailerGoodsList(id,activityName,retailerId)" title="查看活动商品"></t:dgFunOpt>
<%--    <t:dgFunOpt funname="getColudGoodsList(id,activityName,retailerId)" title="云商活动商品"></t:dgFunOpt> --%>
   <t:dgToolBar title="录入" icon="icon-add" funname="addbytab"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" funname="updatebytab"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="baseActivityController.do?goUpdate" funname="update"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search"  funname="goView"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div region="east" style="width: 700px;" split="true">
	<div tools="#tt" class="easyui-panel" title='查看活动商品' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
 <script src = "webpage/com/buss/base/baseActivityList.js?v=1.1"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
	$("#baseActivityListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>起始时间：</span><input name='start_Time' type='text' class='Wdate' style='width:90px'/>");
	$("#baseActivityListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>结束时间：</span><input name='end_Time' type='text' class='Wdate' style='width:90px'/>");
	$("#baseActivityListForm").find("input[name='start_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#baseActivityListForm").find("input[name='end_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 function getRetailerGoodsList(id,activityName,retailerId) {
		$("#function-panel").panel(
			{
				title : '活动名称:' + activityName,
				href:"tActivityGoodsController.do?list&activity_Id="+id+"&rId="+retailerId
			}
		);
	}

function getColudGoodsList(id,activityName,retailerId) {
		$("#function-panel").panel(
			{
				title : '活动名称:' + activityName,
				href:"tActivityGoodsController.do?listOfCloud&activity_Id="+id+"&rId="+retailerId
			}
		);
	}
 </script>