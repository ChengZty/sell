<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tStoreAccountList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="客户账户总表" actionUrl="tStoreAccountController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" hidden="true" formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户账号"  field="retailerName" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户名称"  field="retailerRealname" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="激活日期"  field="activeDate" formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="余额"  field="remainMoney"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="本月端口费用"  field="monthCharge"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="年度工具购买费"  field="yearCharge"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="账户状态"  field="accountStatus" replace="正常_1,余额不足_0" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否提醒"  field="remind" queryMode="single" replace="正常_1,待提醒_2,已提醒_3" query="true" width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   	<t:dgFunOpt title="账户余额明细"  funname="viewDetails(retailerId)" ></t:dgFunOpt>

   
   <t:dgToolBar title="充值" icon="icon-add" url="tStoreAccountController.do?goUpdateInfo&type=1" funname="update" width="1000" height="350"></t:dgToolBar>
   <t:dgToolBar title="扣除" icon="icon-edit" url="tStoreAccountController.do?goUpdateInfo&type=0" funname="update" width="1000" height="350"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" url="tStoreAccountController.do?exportInfoXls" height="500" funname="ExportInfoXls"></t:dgToolBar>
   
   </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 //账户详情弹出框
 function viewDetails(retailerId) {
		var addurl = "tStoreAccountController.do?tStoreAccountDetail&rid="+retailerId;
		var title = "账户余额明细";
		var width = 1200;
		var height = 800;
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+addurl,
				lock : true,
				//zIndex:1990,
				width:width,
				height:height,
				title:title,
				opacity : 0.3,
				cache:false,
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			W.$.dialog({
				content: 'url:'+addurl,
				lock : true,
				width:width,
				//zIndex:1990,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
	}
 
//导出
 function ExportInfoXls() {
 	var total = $('#tStoreAccountList').datagrid('getData').total;
 	var pageNum = 5000;
 	var url = "tStoreAccountController.do?exportInfoXls";
 	var datagridId = "tStoreAccountList";
 	if(total - pageNum < 0){
 		JeecgExcelExportWithPars(url,datagridId,1,total);
 		return ;
 	}else{
 		showWinPage(total,url,datagridId,pageNum);
 	}
 }
 </script>