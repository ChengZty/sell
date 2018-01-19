<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="guideActivityTotal" checkbox="true" fitColumns="false" title="直播分享活动点击明细" actionUrl="guideActivityCountController.do?tActivityClickDatagrid&actId=${actId }&searchTime_begin=${searchTime_begin }&searchTime_end=${searchTime_end }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分享人"  field="guideName"  query="true"    width="120"></t:dgCol>
   <t:dgCol title="点击次数"  field="clickNum"  width="120"></t:dgCol>
  </t:datagrid>
  <input type="hidden" name="notInitSearch" value="1"/><!-- 不是初始化查询 -->
  </div>
 </div>