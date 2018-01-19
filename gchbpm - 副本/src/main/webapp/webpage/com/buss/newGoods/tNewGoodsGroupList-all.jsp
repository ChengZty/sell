<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsCombinationList" checkbox="false" extendParams="nowrap:false," fitColumns="false" title="商品组合列表" actionUrl="tNewGoodsGroupController.do?datagrid"
   idField="id" fit="true" queryMode="group" sortName="sortNum" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"    queryMode="single"  ></t:dgCol>
<%--    <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100"></t:dgCol> --%>
   <t:dgCol title="商品名称"  field="goodsName"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode" hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="品牌"  field="brandName"    queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="零售商"  field="retailerName"  query="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="组合价"  field="groupPrice"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="活动价"  field="activityPrice"    queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="上架时间"  field="goodsUpdateTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="组合来源"  field="groupSource"  replace="零售商_1,g+_2" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="组合类型"  field="goodsType"  replace="组合_2,搭配_3" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url=""  funname="goView"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tGoodsCombinationListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tGoodsCombinationListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 
 function goView(){
	 var rowsData = $('#tGoodsCombinationList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		window.open("tNewGoodsGroupController.do?goView&id="+rowsData[0].id,"new"); 
}
 

 </script>