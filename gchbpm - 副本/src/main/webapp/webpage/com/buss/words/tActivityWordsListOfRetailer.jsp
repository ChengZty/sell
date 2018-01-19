<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tActivityWordsList" checkbox="true" fitColumns="false" title="活动话术" actionUrl="tActivityWordsController.do?datagrid&tp=2" 
  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动分类"  field="activityTypeId" dictionary="t_content_category,id,name,status = 'A' and category_type = 'SPHD'" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内容"  field="content"  query="true"   queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="平台类别"  field="platformType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <c:if test="${userType == '01' }">
   <t:dgCol title="零售商"  field="retailerId"  dictionary="t_s_user,id,realname,status = 'A' and user_type='02'"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tActivityWordsController.do?doDel&id={id}" />
   <c:if test="${userType != '01' }">
   <t:dgToolBar title="录入" icon="icon-add" url="tActivityWordsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tActivityWordsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tActivityWordsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="tActivityWordsController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">

 </script>