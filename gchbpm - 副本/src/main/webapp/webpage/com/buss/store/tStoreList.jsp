<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tStoreList" checkbox="true" fitColumns="false" title="实体店" actionUrl="tStoreController.do?datagrid" idField="id" 
  fit="true" queryMode="group" sortName="sortNum" sortOrder="asc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺名称"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺编号"  field="storeCode"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="地址"  field="address"    queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="电话"  field="phoneNo"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="排序"  field="sortNum"    queryMode="single"  width="120"></t:dgCol>
   <c:if test="${userType!='01' }">
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tStoreController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tStoreController.do?goAdd" funname="add" width="900" height="600"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tStoreController.do?goUpdate" funname="update" width="900" height="600"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tStoreController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="批量导入实体店" icon="icon-put" funname="importXls"></t:dgToolBar>
   <t:dgToolBar title="实体店模板下载" icon="icon-putout" funname="downloadTemp"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="tStoreController.do?goUpdate" funname="detail" width="900" height="600"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <link rel="stylesheet" href="plug-in/html5uploader/html5uploader.css">
 <script src="plug-in/html5uploader/jquery.html5uploader.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
 //库存导入
 function importXls() {
	  openuploadwinH5('实体店导入', 'tStoreController.do?imporExcel', "tStoreList", checkFailList);
}

//库存模板下载
 function downloadTemp() {
 	JeecgExcelExport("tStoreController.do?downloadTemp","tStoreList");
 } 
 
 //校验错误
 function checkFailList(data) {
 	var key = data.obj;
 	if(key!=null){
 		//下载错误的记录
 		window.location.href=key;//直接从七牛下载
 	}
 }
 </script>