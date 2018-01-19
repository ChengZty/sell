<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tFocusCustomerList" checkbox="true" fitColumns="true" title="待发展顾客列表" sortName="createDate" sortOrder="desc"
			actionUrl="tFocusCustomerController.do?datagrid" idField="id" fit="true" queryMode="group">
	<t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="添加时间"  field="createDate"  query="true"  queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150"></t:dgCol>
	<t:dgCol title="姓名" field="name" query="true" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="性别" field="sex" query="true" queryMode="single" dictionary="sex" width="50"></t:dgCol>
	<t:dgCol title="手机号" field="phoneNo" query="true" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="添加的导购ID" field="addGuideId" hidden="true" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="添加的导购" field="addGuideName" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="添加的零售商" field="addRetailerId" hidden="true" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="所属零售商" field="toRetailerId" dictionary="t_s_user,id,realname,user_type='02' and status = 'A' and retailer_type = '1' and user_status = '1'"  queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="分配导购" field="toGuideId" hidden="true" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="分配的导购" field="toGuideName" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="生日" field="birthday" formatter="yyyy-MM-dd" queryMode="single" width="130"></t:dgCol>
	<t:dgCol title="年龄段" field="birthdayRank" query="true" queryMode="single" width="80"></t:dgCol>
	<t:dgCol title="星座" field="constellation" query="true" queryMode="single" width="80"></t:dgCol>
	<t:dgCol title="生肖" field="zodiac" query="true" queryMode="single" width="60"></t:dgCol>
	<t:dgCol title="登记地区" field="registerArea" query="true" queryMode="single" width="120"></t:dgCol>
<%-- 	<t:dgCol title="登记店铺" field="phoneRegShop" dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" query="true" queryMode="single" width="120"></t:dgCol> --%>
	<t:dgCol title="手机识别地区" field="phoneArea" query="true" queryMode="single" width="120"></t:dgCol>
<%-- 	<t:dgCol title="顾客VIP等级" field="vipLevel" query="true" queryMode="single" width="100"></t:dgCol> --%>
	<t:dgCol title="推送次数" field="pushCount" query="true" queryMode="group" width="80"></t:dgCol>
	<t:dgCol title="点击次数" field="clickNumber" query="true" queryMode="group" width="80"></t:dgCol>
	<t:dgCol title="购买次数" field="buyCount" query="true" queryMode="group" width="80"></t:dgCol>
	<t:dgCol title="类型" field="type" query="true" replace="无效号码_0,无反应顾客_1,点击顾客_2,交易顾客_3" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="是否退订" field="unOrder" query="true" replace="是_1,否_0" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="备注" field="remark" query="true" queryMode="single" width="120"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="tFocusCustomerController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div id='winPage' class='easyui-window' title=' ' style='width:320px;height:220px;' closed='true' modal='true' collapsible='false' minimizable='false' maximizable='false'>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tFocusCustomerListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tFocusCustomerListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 

//导出
function ExportXls(){
	var total = $('#tFocusCustomerList').datagrid('getData').total;
	var pageNum = 5000;
	var url = "tFocusCustomerController.do?exportXls";
	var datagridId = "tFocusCustomerList";
	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
	}else{
		showWinPage(total,url,datagridId,pageNum);
	}  
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tFocusCustomerController.do?exportXlsByT","tFocusCustomerList");
}
 </script>