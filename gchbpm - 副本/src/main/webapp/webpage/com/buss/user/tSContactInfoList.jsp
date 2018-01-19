<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSContactInfoList" checkbox="false" fitColumns="true" title="导购回访详细列表" actionUrl="tSContactController.do?tSContactList&guideId=${tSContact.userId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id" hidden="true" field="id" width="80"></t:dgCol>
   <t:dgCol title="创建人名称"  field="create_name"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="create_by"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="create_date" hidden="true" formatter="yyyy-MM-dd"     width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="update_name"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="update_by"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="update_date" hidden="true" formatter="yyyy-MM-dd"    width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true" width="80"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailer_id" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="导购ID"  field="user_id" hidden="true" width="120"></t:dgCol>
   <t:dgCol title="导购"  field="guide_name"  width="120"></t:dgCol>
	<t:dgCol title="维护顾客id" field="to_user_id" hidden="true" width="80"></t:dgCol>
	<t:dgCol title="顾客"  field="customer_name" query="true"  queryMode="single"  width="80"></t:dgCol>
	<t:dgCol title="顾客id"  field="customer_id" hidden="true" width="80"></t:dgCol>
   <t:dgCol title="回访类型"  field="type" replace="QQ_qq,微信_weChart,短信_msg,电话_phone" query="true" queryMode="single" width="80"></t:dgCol>
   <t:dgCol title="回访内容"  field="content" width="80"></t:dgCol>
   <t:dgCol title="最新联系时间"  field="concat_time" formatter="yyyy-MM-dd" query="true" queryMode="group"   width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
		$("#tSContactInfoListtb").find("input[name='concat_time_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","payTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'payTime_end\')}'});});
		$("#tSContactInfoListtb").find("input[name='concat_time_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","payTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'payTime_begin\')}'});});
 });
 
 </script>
 
