<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBaseTagsList" checkbox="true" fitColumns="false" title="标签" actionUrl="tBaseTagsController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编码"  field="tagCode"  hidden="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="标题"  field="tagTitle"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="对象类型"  field="toUserType"  replace="导购_1,顾客_2" hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <c:if test="${userType =='01' }">
   <t:dgCol title="标签类别"  field="tagStage" dictionary="qst_stage" query="true"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="标签类型"  field="tagType"  dictionary="tag_type" query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="标签值"  field="tagValues"    queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="排序"  field="tagSort"    queryMode="single"  width="80"></t:dgCol>
   <c:if test="${userType =='01' }">
   <t:dgCol title="零售商"  field="retailerId"  dictionary="t_s_user,id,realname,user_type='02' and status = 'A'" query="true" queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="是否可用"  field="valid"  replace="可用_Y,停用_N"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgConfOpt title="停用" url="tBaseTagsController.do?doStop&id={id}" exp="valid#eq#Y" message="确定停用？"></t:dgConfOpt>
   <t:dgDelOpt title="删除" url="tBaseTagsController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBaseTagsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBaseTagsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBaseTagsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBaseTagsController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBaseTagsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBaseTagsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 

 </script>