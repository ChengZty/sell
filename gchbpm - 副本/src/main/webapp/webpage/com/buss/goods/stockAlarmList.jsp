<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="storeList" checkbox="false" fitColumns="true" title="预警库存列表" sortName="createDate" sortOrder="desc" actionUrl="tGoodsController.do?storeDatagrid&type=${type }"
  extendParams="nowrap:false,"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="80,80"  queryMode="single" ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="300"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="现价"  field="currentPrice"    queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="最低价"  field="lowestPrice"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="库存"  field="goodsStock"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="goodsUpdateTime"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDefOpt title="修改库存" url="tGoodsController.do?goUpdateStore&type=${type }&id={id}" />
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()"  funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
	//修改库存后刷新顶部库存
	 var stockAlarmNow= "${stockAlarmNow}";
	 if(stockAlarmNow!=""){
		 window.top.jQuery('#noticeCount-2').text(stockAlarmNow);
	 }
 		//给时间控件加上样式
//  			$("#tGoodsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tGoodsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function goView(){
	 var rowsData = $('#storeList').datagrid('getSelections');
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