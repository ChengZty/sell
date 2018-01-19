<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tFinActivityList" checkbox="false" fitColumns="false" title="活动奖励" actionUrl="tGiftRuleController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="规则名称"  field="ruleName"    queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="满赠金额(元)"  field="money"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核时间"  field="auditTime" formatter="yyyy-MM-dd hh:mm:ss"   width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="auditor"   queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="活动状态"  field="ruleStatus" query="true" replace="待审核_1,已审核_2,已下架_3" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgConfOpt url="tGiftRuleController.do?doAudit&id={id}" urlStyle="color:red" message="确定审核" title="审核" exp="ruleStatus#eq#1"></t:dgConfOpt>
   <t:dgConfOpt url="tGiftRuleController.do?doDown&id={id}"  message="确定下架" title="下架" exp="ruleStatus#eq#2"></t:dgConfOpt>
   <t:dgFunOpt funname="getGiftRuleGoodsList(id,ruleName)" title="赠品" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="tGiftRuleController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div data-options="region:'east',
	title:'赠品',
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
 		//给时间控件加上样式
//  			$("#tFinActivityListtb").find("input[name='startTime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tFinActivityListtb").find("input[name='endTime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function getGiftRuleGoodsList(id,ruleName) {
		if(li_east == 0){
			   $('#main_depart_list').layout('expand','east'); 
		}else{
			   $('#main_depart_list').layout('collapse','east'); 
		}
		$("div.layout-panel-east div.panel-title").text(ruleName);
		$('#function-panel').panel("refresh", "tGiftRuleGoodsController.do?list&grId=" + id);
			}

 </script>