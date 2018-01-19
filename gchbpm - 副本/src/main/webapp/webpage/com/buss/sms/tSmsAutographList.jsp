<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tSmsAutographList" checkbox="true" fitColumns="false" title="短信签名设置" actionUrl="tSmsAutographController.do?datagrid&retailerId=${retailerId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="短信签名名称"  field="autographName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="签名审核状态"  field="autographStatus"  replace="审核中_0,审核通过_1,审核不通过_2"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSmsAutographController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tSmsAutographController.do?goAdd&retailerId=${retailerId }" funname="add" height="100"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tSmsAutographController.do?goUpdate" funname="update" height="100"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSmsAutographController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tSmsAutographController.do?goUpdate" funname="detail" height="100"></t:dgToolBar>
  </t:datagrid>
 