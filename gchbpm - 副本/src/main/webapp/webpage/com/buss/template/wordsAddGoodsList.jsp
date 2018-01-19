<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsDetailList" checkbox="true" fitColumns="true" title="商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsController.do?datagridForGoodsWords&wordsId=${wordsId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"  queryMode="single" frozenColumn="true" ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="200" ></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="是否有话术"  field="hasWords"  replace="是_1,否_0" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 function goView(){
	 var rowsData = $('#tNewGoodsDetailList').datagrid('getSelections');
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