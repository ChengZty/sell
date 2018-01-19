<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"/>
  <t:datagrid name="mutiLangList" title="语言信息维护" actionUrl="mutiLangController.do?datagrid" 
      idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="编码" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="语言key" field="langKey" query="true" extend="{style:'width:200px'}"></t:dgCol>
   <t:dgCol title="内容" field="langContext" query="true" extend="{style:'width:200px'}"></t:dgCol>
   <t:dgCol title="语言" field="langCode" ></t:dgCol>
   <t:dgCol title="创建人" field="createBy" hidden="true"></t:dgCol>
	<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="修改人" field="updateBy" hidden="true"></t:dgCol>
	<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="mutiLangController.do?del&id={id}" />
   <t:dgToolBar title="录入" langArg="语言" icon="icon-add" url="mutiLangController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" langArg="语言" icon="icon-edit" url="mutiLangController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="mutiLangController.do?addorupdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="刷新缓存" icon="icon-search" url="mutiLangController.do?refreshCach" funname="doSubmit"></t:dgToolBar>
  </t:datagrid>
