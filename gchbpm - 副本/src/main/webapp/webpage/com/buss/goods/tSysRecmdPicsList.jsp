<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tSysRecmdPicsList" checkbox="true" fitColumns="false" title="t_sys_recmd_pics" actionUrl="tSysRecmdPicsController.do?datagrid&goods_Id=${goods_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="商品ID"  field="goodsId"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="图片地址"  field="smallPic"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="推荐商品名称"  field="goodsName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="商品类型"  field="goodsType"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSysRecmdPicsController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tSysRecmdPicsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tSysRecmdPicsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSysRecmdPicsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tSysRecmdPicsController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 </script>