<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsList" checkbox="false" fitColumns="true" title="商品列表" sortName="goodsUpdateTime" sortOrder="desc" actionUrl="tGoodsController.do?datagrid&goods_status=${goods_status }"
  extendParams="nowrap:false,"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single" ></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="场景"  field="sceneType"  hidden="true" query="true" queryMode="single" dictionary="sceneType" ></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"  queryMode="single" ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single" width="300"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"   query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"  query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerName"  query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"  hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="最低价"  field="lowestPrice"  hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="库存"  field="goodsStock"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="操作时间"  field="goodsUpdateTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200" align="center"></t:dgCol>
   <t:dgConfOpt title="审核并上架" url="tGoodsController.do?doUp&id={id}" message="确认上架该商品？"/>
   <t:dgConfOpt title="审核不通过" url="tGoodsController.do?doNotAudit&id={id}" message="确认该商品审核不通过？"/>
<%--    <t:dgFunOpt funname="goChangeActivity(id,retailerId)"  title="修改活动"></t:dgFunOpt> --%>
<%--    <t:dgDelOpt title="删除" url="tGoodsController.do?doDel&id={id}" /> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()"  funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="录入" icon="icon-add" url=""  onclick="addbytab()" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url=""  onclick="updatebytab()" funname="update"></t:dgToolBar> --%>
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

function updatebytab(){
	var rows = $("#tGoodsList").datagrid("getSelections");
	if(rows==''){
		alert('请选择一行记录');
		return;
	}
	var id=rows[0].id;
	document.location="tGoodsController.do?goUpdate&id="+id;
}

//修改活动
 function goChangeActivity(id,retailerId) {
 	 var url = "tGoodsController.do?goChangeActivity&id="+id+"&retailerId="+retailerId;
 	 gridname="tGoodsList";
 	createwindow("修改活动", url,400,100);
 }
 </script>