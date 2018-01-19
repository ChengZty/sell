<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsTypeList" checkbox="true" fitColumns="false" sortName="orderNum" sortOrder="asc" title="话题分类" actionUrl="tNewsTypeController.do?datagrid&tp=2" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编码"  field="code"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="名称"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="小图"  field="smallPic" image="true" imageSize="50,30"  queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="图片"  field="coverPic" image="true" imageSize="100,60"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="排序编号"  field="orderNum"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="显示类别"  field="showType"   query="true" queryMode="single" dictionary="newsShowTp" width="120"></t:dgCol>
<%--    <t:dgCol title="是否必须"  field="isNeed"  replace="否_0,是_1"  queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tNewsTypeController.do?doDel&id={id}" />
   <c:if test="${userType !='01' }">
   	<t:dgToolBar title="录入" icon="icon-add" url="tNewsTypeController.do?goAdd" funname="add" height="500"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tNewsTypeController.do?goUpdate" funname="update" height="500"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tNewsTypeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tNewsTypeController.do?goUpdate" funname="detail" height="500"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 </script>