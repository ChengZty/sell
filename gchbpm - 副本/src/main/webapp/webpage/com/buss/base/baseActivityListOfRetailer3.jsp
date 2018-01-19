<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="baseActivityList" checkbox="true" fitColumns="false" title="活动列表" actionUrl="baseActivityController.do?datagrid2&rId=${rId }&cId=${cId }" 
  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="图片"  field="picUrl"  image="true" imageSize="100,80" queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="活动名称"  field="activityName"  query="true"  queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="子标题"  field="subTitle"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始时间"  field="startTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerId" hidden="true" queryMode="single"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
