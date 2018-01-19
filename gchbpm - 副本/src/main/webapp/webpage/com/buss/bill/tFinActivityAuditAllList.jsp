<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tFinActivityAuditAllList" checkbox="false" fitColumns="false" title="导购活动审核" actionUrl="tFinActivityController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动名称"  field="activityName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动来源"  field="platformType" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动类别"  field="activityType" replace="单品_1,品牌_2,全馆_3"  query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="品牌名称"  field="brandName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始时间"  field="startTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核时间"  field="auditTime" formatter="yyyy-MM-dd hh:mm:ss"   width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="auditor"   queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="活动状态"  field="activityStatus" query="true" replace="待审核_1,已审核_2,已下架_3" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="活动对象类型"  field="toUserType" replace="导购_1,顾客_2" hidden="true"  width="90"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgConfOpt url="tFinActivityController.do?doAudit&id={id}" urlStyle="color:red" message="确定审核" title="审核" exp="activityStatus#eq#1&&platformType#eq#${platformType }"></t:dgConfOpt>
   <t:dgConfOpt url="tFinActivityController.do?doDown&id={id}"  message="确定下架" title="下架" exp="activityStatus#eq#2"></t:dgConfOpt>
   <t:dgFunOpt funname="getFinActivityGoodsList(id)" title="活动奖励商品" exp="activityType#eq#1"></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search"  funname="goView" ></t:dgToolBar>
  </t:datagrid>
  </div>
  </div>
  <div data-options="region:'east',
	title:'活动奖励商品',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 500px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="function-panel"></div>
</div>
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#tFinActivityAuditAllListtb a[iconcls='icon-reload']").attr("onclick","searchMainReset('tFinActivityAuditAllList')");//防止和右边的列表的重置冲突
 });
 
 function goView() {
	 var rowsData = $("#tFinActivityAuditAllList").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var id=rowsData[0].id;
	window.open("tFinActivityController.do?goView&id="+id,"new");
}
 
 function getFinActivityGoodsList(id,nticeLevel) {
		if(li_east == 0){
			   $('#main_depart_list').layout('expand','east'); 
		}else{
			   $('#main_depart_list').layout('collapse','east'); 
		}
		$("div.layout-panel-east div.panel-title").text("关联话术");
		$('#function-panel').panel("refresh", "tFinActivityGoodsController.do?list&fin_act_Id=" + id);
}

 //重置
 function searchMainReset(name) {
		$("#" + name + "tb").find(":input").val("");
		var queryParams = $('#'+name).datagrid('options').queryParams;
		$('#'+name+"tb").find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#'+name).datagrid(
			{
				url : 'tFinActivityController.do?datagrid&field=id,updateDate,activityName,platformType,activityType,brandName,startTime,startTime_begin,startTime_end,endTime,endTime_begin,endTime_end,retailerId,auditTime,auditor,activityStatus,actStatus,toUserType,status,',
				pageNumber : 1
			});
	} 
 </script>