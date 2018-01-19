<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tCardRateList" checkbox="false" fitColumns="false" title="G+卡提成比例" sortName="createDate" sortOrder="desc"
  actionUrl="tCardRateController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerName" query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商比例"  field="retailerRate"  formatterjs="percent"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="管家比例"  field="guideRate"  formatterjs="percent"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="tCardRateController.do?doDel&id={id}" /> --%>
   <t:dgToolBar title="批量录入或编辑" icon="icon-add" url="tCardRateController.do?goBatchUpdate" funname="goBatchUpdate"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tCardRateController.do?goUpdate" funname="update"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tCardRateListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tCardRateListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
  function goBatchUpdate(title,url){
	  window.location.href=url;
  }
  
  function percent(value,rec,index){
		return value+'%';
	}
 </script>