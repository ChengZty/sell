<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tShopList" checkbox="true" fitColumns="false" title="店铺" actionUrl="tShopController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帐号"  field="code"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺名称"  field="shopName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="省市"  field="area"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="详细地址"  field="detailAddress"    queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="电话"  field="phoneNo"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="版本"  field="retailerEdition"  replace="标准版_1,企业版_2"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺状态"  field="shopStatus"  replace="激活_1,待激活_0"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否显示"  field="isShow"  replace="是_1,否_0"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺等级"  field="shopLevel"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="有效期"  field="validPeriod"  formatterjs="replaceBlank"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tShopController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tShopController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tShopController.do?goUpdate" funname="update"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tShopController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tShopController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tShopListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tShopListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 //替换空白期限
 function replaceBlank(value,row,index){
	 if(value==""){
		 return "无期限";
	 }
 }
 </script>