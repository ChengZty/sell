<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="dbSourceList" title="多数据源管理" actionUrl="dynamicDataSourceController.do?datagrid" idField="id" fit="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="数据库名称" field="dbName" hidden="false"></t:dgCol>
	<t:dgCol title="多数据源主键" field="dbKey" ></t:dgCol>
	<t:dgCol title="描述" field="description" ></t:dgCol>
	<t:dgCol title="驱动类" field="driverClass" ></t:dgCol>
	<t:dgCol title="数据源地址" field="url" ></t:dgCol>
	<t:dgCol title="数据库用户名" field="dbUser" ></t:dgCol>
	<t:dgCol title="数据库密码" field="dbPassword" ></t:dgCol>
	<t:dgCol title="数据库类型" field="dbType" ></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt title="删除" url="dynamicDataSourceController.do?del&id={id}" />
	<t:dgToolBar title="录入" icon="icon-add" url="dynamicDataSourceController.do?addorupdate" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="dynamicDataSourceController.do?addorupdate" funname="update"></t:dgToolBar>
	<t:dgToolBar title="查看" icon="icon-search" url="dynamicDataSourceController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
