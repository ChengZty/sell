<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tExcludeRuleList" checkbox="true" fitColumns="true" title="互斥规则" actionUrl="tExcludeRuleController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="一级分类ID"  field="topCategoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="二级分类ID"  field="subCategoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="三级分类ID"  field="thridCategoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="一级分类名称"  field="topCategoryName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="二级分类名称"  field="subCategoryName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="三级分类名称"  field="thridCategoryName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌ID"  field="brandId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌名称"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tExcludeRuleController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="" onclick="goAdd()" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="" onclick="goUpdate()" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tExcludeRuleController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tExcludeRuleController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/goods/tExcludeRuleList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tExcludeRuleListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tExcludeRuleListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
function goAdd(){
		var url ="tExcludeRuleController.do?goAdd";
		window.location.href=url;
}

function goUpdate(){
	 var rowsData = $('#tExcludeRuleList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
	var url ="tExcludeRuleController.do?goUpdate&id="+rowsData[0].id;
	window.location.href=url;
}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tExcludeRuleController.do?upload', "tExcludeRuleList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tExcludeRuleController.do?exportXls","tExcludeRuleList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tExcludeRuleController.do?exportXlsByT","tExcludeRuleList");
}
 </script>