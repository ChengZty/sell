<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tStoreList" checkbox="false" fitColumns="true" title="实体店" actionUrl="tStoreController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="名称"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺编号"  field="storeCode"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="地址"  field="address"    queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="电话"  field="phoneNo"    queryMode="single"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
	
<script type="text/javascript">
  	function getId(){
  		var rowsData = $('#tStoreList').datagrid('getSelections');
  		if (!rowsData || rowsData.length == 0) {
  			tip('请选择店铺');
  			return;
  		}
  		return rowsData[0].id;
  	}
  </script>