<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsCombinationList" checkbox="false" extendParams="nowrap:false," fitColumns="true" title="商品组合列表" sortName="goodsUpdateTime" sortOrder="desc"
  actionUrl="tGoodsCombinationController.do?datagrid&goods_status=${goods_status }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"    queryMode="single" ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"   query="true" queryMode="single"  width="300"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合价"  field="groupPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="点击量"  field="goodsCollect"  align="center"  queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="更新时间"  field="goodsUpdateTime"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品类型"  field="goodsType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合来源"  field="groupSource"  replace="零售商_1,g+_2" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
   <t:dgConfOpt title="下架" url="tGoodsCombinationController.do?doDown&id={id}" message="确认下架该组合？"/>
   <t:dgToolBar title="查看" icon="icon-search" url="" funname="goView"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<!--  <script src = "webpage/com/buss/goods/tGoodsCombinationList.js"></script>		 -->
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
		window.open("tGoodsCombinationController.do?goView&id="+rowsData[0].id,"new"); 
}
 
 </script>