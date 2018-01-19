<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
	<t:datagrid name="timeTaskList" title="定时任务管理" actionUrl="timeTaskController.do?datagrid" 
	    idField="id" fit="true" sortName="createDate" sortOrder="desc">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="任务ID" field="taskId"></t:dgCol>
	<t:dgCol title="任务描述" field="taskDescribe"></t:dgCol>
	<t:dgCol title="cron表达式" field="cronExpression"></t:dgCol>
	<t:dgCol title="是否生效" field="isEffect" replace="未生效_0,已生效_1"></t:dgCol>
	<t:dgCol title="运行状态" field="isStart" replace="停止_0,运行_1"></t:dgCol>
	<t:dgCol title="创建人" field="createBy" hidden="true"></t:dgCol>
	<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="修改人" field="updateBy" hidden="true"></t:dgCol>
	<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgConfOpt title="启动" url="timeTaskController.do?startOrStopTask&id={id}&isStart=1" message="确认运行任务" exp="isStart#eq#0"/>
	<t:dgConfOpt title="停止" url="timeTaskController.do?startOrStopTask&id={id}&isStart=0" message="确认停止" exp="isStart#eq#1"/>
	<t:dgConfOpt title="立即生效" url="timeTaskController.do?updateTime&id={id}" message="确认更新任务时间" exp="isEffect#eq#0"/>
	<t:dgDelOpt title="删除" url="timeTaskController.do?del&id={id}" />
	<t:dgToolBar title="录入" icon="icon-add" url="timeTaskController.do?addorupdate" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="timeTaskController.do?addorupdate" funname="update"></t:dgToolBar>
	<t:dgToolBar title="查看" icon="icon-search" url="timeTaskController.do?addorupdate" funname="detail"></t:dgToolBar>
</t:datagrid></div>
</div>
