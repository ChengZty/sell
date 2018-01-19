<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tWeixinUserList" checkbox="true" fitColumns="false" title="微信用户表" actionUrl="tWeixinUserController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="微信openid"  field="openid" hidden="true"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="用户昵称"  field="nickname"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="性别"  field="sex"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="国家"  field="country"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="省份"  field="province"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="城市"  field="city"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="头像"  field="headimgurl"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="特权json组"  field="privilege"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="tWeixinUserController.do?goUpdate" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 </script>