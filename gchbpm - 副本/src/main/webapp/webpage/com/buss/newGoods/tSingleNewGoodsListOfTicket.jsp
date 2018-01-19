<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="true" fitColumns="true" title="商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsController.do?datagridOfTicket&ticketId=${ticketId }&rId=${rId }"
  extendParams="nowrap:false,"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"  queryMode="single" ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode" query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice" queryMode="single"  width="50"></t:dgCol>
<%--    <t:dgCol title="活动价"  field="activityPrice" queryMode="single"  width="50"></t:dgCol> --%>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="100"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
