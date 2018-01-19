<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="baseQuestionsList" checkbox="true" sortName="createDate" sortOrder="desc" fitColumns="false" title="问题列表" actionUrl="baseQuestionsController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="问题"  field="questionName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="问题值"  field="questionValue" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="答案类型"  field="answerType"   query="true" queryMode="single" dictionary="ans_type" width="120"></t:dgCol>
   <t:dgCol title="答案"  field="answerValues"    queryMode="single"  width="300"></t:dgCol>
<%--    <t:dgCol title="维护类别"  field="maintanceType" query="true" replace="导购_1,顾客_2"  queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="排序"  field="questionSort"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="资料类型"  field="infoType" dictionary="qest_info_type" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="baseQuestionsController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="baseQuestionsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="baseQuestionsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="baseQuestionsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="baseQuestionsController.do?goUpdate" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="查看问题" icon="icon-search" url="" funname="getList" onclick="getList()"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function getList(){
	var url = "tPersonAnswersController.do?list";
	window.open(url);
}
 </script>