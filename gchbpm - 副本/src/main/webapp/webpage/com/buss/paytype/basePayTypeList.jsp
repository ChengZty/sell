<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="basePayTypeList" checkbox="true" fitColumns="false" title="支付方式" actionUrl="basePayTypeController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付方式"  field="payType"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付编码"  field="payCode"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付费率(‰)"  field="rate"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否启用"  field="isOpen"   query="true" queryMode="single" dictionary="sf_yn" width="120"></t:dgCol>
   <t:dgCol title="支付图标"  field="payIcon"    queryMode="single"  width="150" imageSize="130,80" image="true"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="basePayTypeController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="basePayTypeController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="basePayTypeController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="basePayTypeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="basePayTypeController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#basePayTypeListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#basePayTypeListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'basePayTypeController.do?upload', "basePayTypeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("basePayTypeController.do?exportXls","basePayTypeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("basePayTypeController.do?exportXlsByT","basePayTypeList");
}
 </script>