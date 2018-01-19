<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSmsSendTemplateList" checkbox="true" fitColumns="false" title="短信模板设置" actionUrl="tSmsSendTemplateController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="短信模板名称"  field="templateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="短信签名名称"  field="autographName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="正文内容"  field="content"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="链接地址"  field="url"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="结尾内容1"  field="contentEnd"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="结尾内容2"  field="contentEnd2"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSmsSendTemplateController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tSmsSendTemplateController.do?goAdd" funname="add" width="615" height="500"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tSmsSendTemplateController.do?goUpdate" funname="update" width="615" height="500"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSmsSendTemplateController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tSmsSendTemplateController.do?goUpdate" funname="detail" width="615" height="500"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 </script>