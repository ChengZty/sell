<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tGuideRecommendInfoList" checkbox="true" fitColumns="true" title="导购点评" sortName="updateDate" sortOrder="asc"
  actionUrl="tGuideRecommendInfoController.do?datagrid&goods_Id=${goods_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="导购"  field="guideName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="商品ID"  field="goodsId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="文字描述"  field="description"    queryMode="group"  width="180"></t:dgCol>
   <t:dgCol title="阅读量"  field="readNum"    queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="标记"  field="flag" hidden="true"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tGuideRecommendInfoController.do?doDel&id={id}"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tGuideRecommendInfoController.do?goAdd&goods_Id=${goods_Id }&retailer_Id=${retailer_Id }" funname="add" width="900" height="600"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tGuideRecommendInfoController.do?goUpdate" funname="update" width="900" height="600"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tGuideRecommendInfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tGuideRecommendInfoController.do?goView" funname="detail" width="900" height="600"></t:dgToolBar>
  </t:datagrid>
 <script type="text/javascript">
 
 </script>