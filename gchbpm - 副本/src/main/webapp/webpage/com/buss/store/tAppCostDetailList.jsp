<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tAppCostDetailList" checkbox="true" fitColumns="false" title="端口扣费明细" actionUrl="tAppCostDetailController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="chargeTime" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="主表id"  field="appCostInfoId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="账户明细id"  field="storeAccountDetailId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商id"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="年月"  field="yearMonthStr"  query="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="店铺id"  field="storeId"  hidden="true"  queryMode="single"  width="120"></t:dgCol> --%>
<c:if test="${not empty rId }">
   <t:dgCol title="登记店铺" field="storeId" dictionary="t_store,id,name, status='A' and retailer_id = '${rId}'" query="true" queryMode="single" width="120"></t:dgCol>
</c:if>
   <t:dgCol title="导购id"  field="guideId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购姓名"  field="guideName" query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机号"  field="phone"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="激活时间"  field="activeTime"  query="true" formatter="yyyy-MM-dd" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="扣费期间开始"  field="chargePeriodBegin"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="扣费期间结束"  field="chargePeriodend" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="扣费时间"  field="chargeTime" query="true" formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="扣费金额"  field="appCost"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="扣费状态"  field="chargeStatus"  replace="待扣费_0,扣费成功_1,扣费失败_2" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="失败次数"  field="failTimes"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="tAppCostDetailController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tAppCostDetailController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tAppCostDetailController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tAppCostDetailController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tAppCostDetailController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tAppCostDetailListtb").find("input[name^='activeTime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tAppCostDetailListtb").find("input[name^='chargeTime']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

//导出
function ExportXls() {
	JeecgExcelExport("tAppCostDetailController.do?exportXls","tAppCostDetailList");
}

 </script>