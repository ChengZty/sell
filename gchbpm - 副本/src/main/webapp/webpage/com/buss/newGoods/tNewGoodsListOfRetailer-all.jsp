<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="false" fitColumns="false"  title="商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsController.do?datagrid"
   idField="id" fit="true" queryMode="group"  extendParams="nowrap:false,">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
    <t:dgCol title="图片" style="text-align:center;" field="smallPic"  image="true" imageSize="80"  queryMode="single"  frozenColumn="true" width="80"></t:dgCol>
<%--     <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100"></t:dgCol> --%>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode" query="true" queryMode="single"  ></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName" align="center"  query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice"  hidden="true"  queryMode="single"  width="60"></t:dgCol>
<%--    <t:dgCol title="活动价"  field="activityPrice"    queryMode="single"  width="60"></t:dgCol> --%>
   <t:dgCol title="最低价折扣"  field="lowestPriceDiscount"    queryMode="single"  width="60" ></t:dgCol>
   <t:dgCol title="最低价"  field="lowestPrice"    queryMode="single"  width="60" ></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="库存"  field="goodsStock"   queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="人气"  field="goodsCollect"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="上架时间"  field="goodsUpdateTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus" align="center" replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true"  queryMode="single" width="80" ></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="120"></t:dgCol> --%>
<%--    <t:dgFunOpt funname="goUpdateActivityPrice(id)" title="修改活动价"></t:dgFunOpt> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()"  funname="detail"></t:dgToolBar>
   <c:if test="${retailerEdition == '2' }"><!-- 企业版 -->
   <t:dgToolBar title="批量导入商品" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="商品模板下载" icon="icon-putout" funname="downloadTemp"></t:dgToolBar>
   <t:dgToolBar title="批量导入最低价" icon="icon-put" funname="ImportLowesetPriceXls"></t:dgToolBar>
   <t:dgToolBar title="最低价模板下载" icon="icon-putout" funname="downloadLowestPirceTemp"></t:dgToolBar>
   <t:dgToolBar title="批量导入话术" icon="icon-put" funname="ImportWordsXls"></t:dgToolBar>
   <t:dgToolBar title="话术模板下载" icon="icon-putout" funname="downloadWordsTemp"></t:dgToolBar>
   <t:dgToolBar title="批量导入库存" icon="icon-put" funname="ImportStockXls"></t:dgToolBar>
   <t:dgToolBar title="库存模板下载" icon="icon-putout" funname="downloadStockTemp"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="商品导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>

 <link rel="stylesheet" href="plug-in/html5uploader/html5uploader.css">
 <script src="plug-in/html5uploader/jquery.html5uploader.js"></script>
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
 }

 //校验错误
function checkFailList(data) {
	var key = data.obj;
	if(key!=null){
		//下载错误的记录
		window.location.href=key;//直接从七牛下载
	}
}
 
//导入商品 20171108修改
 function ImportXls() {
  openuploadwinH5('商品导入', 'tNewGoodsController.do?importGoodsExcel', "tNewGoodsList", checkFailList);
 }
 
//导入最低价
 function ImportLowesetPriceXls() {
	 openuploadwinH5('最低价导入', 'tNewGoodsController.do?importExcel&extraParam=lowestPirce', "tNewGoodsList");
 }
 
//导入话术
 function ImportWordsXls() {
	 openuploadwinH5('话术导入', 'tNewGoodsController.do?importExcel&extraParam=words', "tNewGoodsList");
 }

 //商品模板下载
 function downloadTemp() {
 	JeecgExcelExport("tGoodsController.do?downloadTemp","tNewGoodsList");
 }
 
 //最低价模板下载
 function downloadLowestPirceTemp() {
 	JeecgExcelExport("tNewGoodsController.do?downloadLowestPirceTemp","tNewGoodsList");
 }
 
 //话术模板下载
 function downloadWordsTemp() {
 	JeecgExcelExport("tNewGoodsController.do?downloadWordsTemp","tNewGoodsList");
 }
 
//商品导出
 function ExportXls() {
 	JeecgExcelExport("tNewGoodsController.do?exportXls","tNewGoodsList");
 }
 
 //库存导入
 function ImportStockXls() {
	  openuploadwinH5('商品库存导入', 'tNewGoodsController.do?imporStockExcel', "tNewGoodsList", checkFailList);
}

//库存模板下载
 function downloadStockTemp() {
 	JeecgExcelExport("tNewGoodsController.do?downloadStockTemp","tNewGoodsList");
 } 
 
 //修改活动价
 function goUpdateActivityPrice(id) {
 	 var url = "tNewGoodsController.do?goUpdateActivityPrice&id="+id;
 	 gridname="tNewGoodsList";
 	createwindow("修改活动价", url,400,180);
 }
 </script>