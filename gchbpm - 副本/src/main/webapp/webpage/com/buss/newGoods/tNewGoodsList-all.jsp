<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="false" fitColumns="false" extendParams="nowrap:false," title="商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsController.do?datagrid"
   idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="80"  queryMode="single" frozenColumn="true" width="80"></t:dgCol>
<%--     <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100"></t:dgCol> --%>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice" hidden="true"   queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgCol title="活动价"  field="activityPrice"    queryMode="single"  width="80"></t:dgCol> --%>
   <t:dgCol title="最低价折扣"  field="lowestPriceDiscount"    queryMode="single"  width="60" ></t:dgCol>
   <t:dgCol title="最低价"  field="lowestPrice"    queryMode="single"  width="80" ></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="库存"  field="goodsStock"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="人气"  field="goodsCollect"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="上架时间"  field="goodsUpdateTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()"  funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tNewGoodsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tNewGoodsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function goView(){
	 var rowsData = $('#tNewGoodsList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		window.open("tNewGoodsController.do?goView&id="+rowsData[0].id,"new"); 
// 	 window.location="tGoodsController.do?goView&id="+rowsData[0].id+"&goods_status=${goods_status }";
 }
 
 </script>