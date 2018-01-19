<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tVipLevelList" checkbox="true" fitColumns="false" title="顾客分类" actionUrl="tVipLevelController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分类编码"  field="vipCode"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分类名称"  field="vipName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属省"  field="provinceId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属市"  field="cityId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属零售商"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="折扣"  field="discount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tVipLevelController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tVipLevelController.do?goAdd" funname="add" width="500" height="200"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tVipLevelController.do?goUpdate" funname="update" width="500" height="200"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tVipLevelController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tVipLevelController.do?goUpdate" funname="detail" width="500" height="200"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tVipLevelListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tVipLevelListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tVipLevelController.do?upload', "tVipLevelList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tVipLevelController.do?exportXls","tVipLevelList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tVipLevelController.do?exportXlsByT","tVipLevelList");
}
 </script>