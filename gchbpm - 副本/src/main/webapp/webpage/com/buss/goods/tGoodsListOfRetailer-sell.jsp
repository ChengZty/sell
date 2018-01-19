<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsList" checkbox="false" fitColumns="true" title="商品列表" actionUrl="tGoodsController.do?datagrid&goods_status=${goods_status }" 
  extendParams="nowrap:false,"  sortName="goodsUpdateTime" sortOrder="desc" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="场景"  field="sceneType"  hidden="true"  queryMode="single" dictionary="sceneType" ></t:dgCol>
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
   <t:dgCol title="操作时间"  field="goodsUpdateTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single" width="100"></t:dgCol>
<%--    <t:dgCol title="商品状态"  field="goodsStatus"    queryMode="single"  ></t:dgCol> --%>
   <t:dgCol title="操作" field="opt"  align="center" width="100"></t:dgCol>
<%--    <t:dgFunOpt title="下架" funname="doDown(id)" /> --%>
   <t:dgConfOpt title="下架" url="tGoodsController.do?doDown&id={id}" message="确认下架该商品？"/>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/buss/goods/tGoodsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tGoodsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tGoodsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
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
		window.open("tGoodsController.do?goView&id="+rowsData[0].id,"new");
// 	 window.location="tGoodsController.do?goView&id="+rowsData[0].id+"&goods_status=${goods_status }";
 }
 
//导出
 function ExportXls() {
 	JeecgExcelExport("tGoodsController.do?exportXls","tGoodsList");
 }
 </script>