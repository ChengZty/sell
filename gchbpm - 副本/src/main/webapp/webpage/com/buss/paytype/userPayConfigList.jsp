<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="userPayConfigList" checkbox="true" fitColumns="false" title="商户支付配置" actionUrl="userPayConfigController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商户id"  field="sid"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商户名"  field="storeName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收款方式"  field="paymentType" replace="商户收款_1,平台收款_0" query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="自动结算"  field="autoclear" replace="否_1,是_0" query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="微信appid"  field="wxAppId"   queryMode="single" width="130"></t:dgCol>
   <t:dgCol title="微信商户号"  field="wxMerchantId"  queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="支付宝身份id"  field="pid"   queryMode="single" width="150"></t:dgCol>
   <t:dgCol title="支付宝appid"  field="appid"    queryMode="single"  width="150" ></t:dgCol>
   <t:dgCol title="签名类型"  field="signType" replace="RSA_0,RSA2_1"  queryMode="single" width="100"></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" exp="id#ne#1" url="userPayConfigController.do?doDel&id={id}" />
<%--    <t:dgToolBar title="录入" icon="icon-add" url="" onclick="addPayConfig()" funname="add"></t:dgToolBar> --%>
   <t:dgToolBar title="录入" icon="icon-add" url=""  funname="addPayConfig"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="" funname="updatePayConfig"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 		
 <script type="text/javascript">

	function addPayConfig(){
		document.location="userPayConfigController.do?goAdd";
	}
	function updatePayConfig(){
		var rowsData = $("#userPayConfigList").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].id;
		document.location="userPayConfigController.do?goUpdate&id="+ id;
	}
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'userPayConfigController.do?upload', "userPayConfigList");
}

//导出
function ExportXls() {
	JeecgExcelExport("userPayConfigController.do?exportXls","userPayConfigList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("userPayConfigController.do?exportXlsByT","userPayConfigList");
}
 </script>