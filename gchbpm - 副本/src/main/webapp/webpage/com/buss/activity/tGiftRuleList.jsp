<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGiftRuleList" checkbox="true" fitColumns="false" title="赠品规则" actionUrl="tGiftRuleController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="规则名称"  field="ruleName"    queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <c:if test="${userType =='01' }">
   <t:dgCol title="零售商"  field="retailerName"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="满赠金额(元)"  field="money"    queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="描述"  field="description"    queryMode="group"  width="300"></t:dgCol>
   <t:dgCol title="规则类别"  field="ruleType"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="规则状态"  field="ruleStatus"  replace="待审核_1,已审核_2,已下架_3" query="true"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgDelOpt title="删除" url="tGiftRuleController.do?doDel&id={id}" exp="ruleStatus#eq#1"/>
   <t:dgFunOpt funname="updatebytab(id)"  title="编辑" exp="ruleStatus#eq#1"></t:dgFunOpt>
   <t:dgFunOpt funname="getGiftRuleGoodsList(id,ruleStatus,ruleName,retailerId)" title="赠品" ></t:dgFunOpt>
   <c:if test="${userType !='01' }">
   <t:dgToolBar title="录入" icon="icon-add" url="tGiftRuleController.do?goAdd" funname="add" height="300"></t:dgToolBar>
   </c:if>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tGiftRuleController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tGiftRuleController.do?goUpdate" funname="detail" height="300"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
  <div data-options="region:'east',
	title:'赠品名称',
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
 });
 
 function updatebytab(id){
	 var title = "编辑";
	 var url = "tGiftRuleController.do?goUpdate&id="+id;
	 var width = 700;
	 var height = 300;
	 createwindow(title,url,width,height);
 }
 
 function getGiftRuleGoodsList(id,ruleStatus,ruleName,retailerId) {
	 if(li_east == 0){
		   $('#main_depart_list').layout('expand','east'); 
	}else{
		   $('#main_depart_list').layout('collapse','east'); 
	}
		$("div.layout-panel-east div.panel-title").text(ruleName);
	$('#function-panel').panel("refresh", "tGiftRuleGoodsController.do?list&grId="+id+"&rSts="+ruleStatus+"&rId="+retailerId);
			}
	
 </script>