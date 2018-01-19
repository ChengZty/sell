<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<!-- <div class="easyui-layout" fit="true"> -->
<!--   <div region="center" style="padding:1px;"> -->
  <t:datagrid name="tGoodsActDetailList" checkbox="true" fitColumns="true" title="商品列表" actionUrl="tGoodsActDetailController.do?datagrid&actId=${goodsActId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="主表id"  field="goodsActId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品id"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品图片"  field="smallPic" image="true" imageSize="60,60"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="最低价折扣"  field="lowestPriceDiscount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="日常最低价"  field="lowestPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动价折扣"  field="discount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动最低价"  field="actPrice"    queryMode="single"  width="120"></t:dgCol>
   <c:if test="${view !='1'|| auditStatus=='2'}">
   <t:dgToolBar title="导入商品活动价" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   </c:if>
<c:if test="${view !='1' }">
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tGoodsActDetailController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
<%--    <t:dgToolBar title="选择已有商品" icon="icon-edit" funname="getSaleGoods"></t:dgToolBar> --%>
   <c:if test="${add !='1' }">
   <t:dgToolBar title="导出商品活动价" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
</c:if>
<%--    <t:dgToolBar title="导出错误记录" icon="icon-putout" funname="ExportXlsxByFailList"></t:dgToolBar> --%>
  </t:datagrid>
<!--   </div> -->
<!--  </div> -->

 <link rel="stylesheet" href="plug-in/html5uploader/html5uploader.css">
 <script src="plug-in/html5uploader/jquery.html5uploader.js"></script>
 <script type="text/javascript">
 

function ImportXls() {
  	openuploadwinH5('商品活动价导入', 'tGoodsActDetailController.do?importExcel&goodsActId=${goodsActId }', "tGoodsActDetailList", ExportXlsxByFailList);
 }

//导入的错误记录下载
function ExportXlsxByFailList(data) {
	var key = data.obj;
	if(key!=null){
		//下载错误的记录
		window.location.href=key;//直接从七牛下载
	}
}

//导出
function ExportXls() {
	JeecgExcelExport("tGoodsActDetailController.do?exportXls&goodsActId=${goodsActId }","tGoodsActDetailList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tGoodsActDetailController.do?exportXlsByT","tGoodsActDetailList");
}
 </script>