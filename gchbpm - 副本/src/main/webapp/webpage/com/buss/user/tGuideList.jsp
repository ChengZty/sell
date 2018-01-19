<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPersonList"   checkbox="false" fitColumns="true"  title="导购信息表" actionUrl="tPersonController.do?datagridOfGuides" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帐号Id"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="头像"  field="photo"  image="true" imageSize="80,80" width="80"></t:dgCol>
   <t:dgCol title="姓名"  field="realName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="昵称"  field="name"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="性别"  field="sex"    queryMode="single" dictionary="sex" width="120"></t:dgCol>
   <t:dgCol title="手机号码"  field="phoneNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属零售商" field="toRetailerId" query="true" dictionary="t_s_user,id,realname,user_type='02' and status = 'A' and retailer_type = '1' and user_status = '1'"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
