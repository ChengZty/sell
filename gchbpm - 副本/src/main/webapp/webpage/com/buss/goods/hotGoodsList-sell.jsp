<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsList" checkbox="true" fitColumns="true" title="商品列表" actionUrl="tGoodsController.do?datagrid3&goods_status=4&content_Id=${content_Id }" 
  extendParams="nowrap:false,"  sortName="createDate" sortOrder="desc" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="场景"  field="sceneType"   queryMode="single" dictionary="sceneType" ></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"  queryMode="single" ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="300"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName" align="center"  query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"  hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice"  align="center"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="最低价"  field="lowestPrice"  hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"  align="center"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="库存"  field="goodsStock"  align="center"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="点击量"  field="goodsCollect"  align="center"  queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="操作时间"  field="goodsUpdateTime" formatter="yyyy-MM-dd"   queryMode="single" width="100"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 		$("#tGoodsListtb").append("<input name='activityId' type='hidden' value='${activityId}'/>");
 });
 
 function goView(){
	 var rowsData = $('#tGoodsList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		window.open("tNewGoodsController.do?goView&id="+rowsData[0].id,"new");
 }
 </script>