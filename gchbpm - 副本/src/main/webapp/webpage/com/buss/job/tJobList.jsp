<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tJobList" checkbox="true" fitColumns="false" title="任务维护" actionUrl="tJobController.do?datagrid&jobType=3" idField="id" 
  fit="true" queryMode="group" sortName="jobSceneCode" sortOrder="asc">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="任务场景 "  field="jobSceneCode"  replace="售前任务_1,售中任务_2,售后任务_3" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="任务名称"  field="title"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="任务描述"  field="description"    queryMode="group"  width="320"></t:dgCol>
   <%-- <t:dgCol title="倍率"  field="rate"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="目标数量"  field="targetNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="金币数"  field="goldNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tJobController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tJobController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tJobController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tJobController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>