<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tGuideRankWordsList" checkbox="true" fitColumns="false" title="导购排名话术" actionUrl="tGuideRankWordsController.do?datagrid&retailer_Id=${retailer_Id }"
   idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="起始排名"  field="rankStart"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="结束排名"  field="rankEnd"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="话术"  field="words"    queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="80"></t:dgCol>
   <t:dgDelOpt title="删除" url="tGuideRankWordsController.do?doDel&id={id}" />
   <t:dgToolBar title="编辑" icon="icon-edit" url="tGuideRankWordsController.do?goUpdate" funname="update" height="200"></t:dgToolBar>
  </t:datagrid>
 </script>