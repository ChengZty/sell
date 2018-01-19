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
   <t:dgCol title="搜索关键字"  field="seoCare"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属零售商"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品类型"  field="goodsType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合来源"  field="groupSource"  replace="零售商_1,g+_2" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作时间"  field="goodsUpdateTime"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200" align="center"></t:dgCol>
   <t:dgConfOpt title="审核并上架" url="tGoodsCombinationController.do?doUp&id={id}" message="确认上架该商品？"/>
   <t:dgConfOpt title="审核不通过" url="tGoodsCombinationController.do?doNotAudit&id={id}" message="确认该商品审核不通过？"/>
   <t:dgDefOpt title="编辑" url="tGoodsCombinationController.do?goUpdate&id={id}" exp="groupSource#eq#2"/>
   <t:dgDelOpt title="删除" url="tGoodsCombinationController.do?doDel&id={id}" exp="groupSource#eq#2"/>
   <t:dgToolBar title="查看" icon="icon-search" url="" funname="goView"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<!--  <script src = "webpage/com/buss/goods/tGoodsCombinationList.js"></script>		 -->
 <script type="text/javascript">
 
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
 
 function goUpdate(){
	 var rowsData = $('#tGoodsCombinationList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
	 window.location="tGoodsCombinationController.do?goUpdate&id="+rowsData[0].id;
 }
 
 </script>