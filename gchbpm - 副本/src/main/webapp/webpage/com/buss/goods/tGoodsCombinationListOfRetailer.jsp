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
   <t:dgCol title="品牌"  field="brandName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合价"  field="groupPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属零售商"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作时间"  field="goodsUpdateTime"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>
   <t:dgDelOpt title="删除" url="tGoodsCombinationController.do?doDel&id={id}" />
   <t:dgDefOpt title="编辑" url="tGoodsCombinationController.do?goUpdate&id={id}" />
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
 
 function goAdd(){
	 document.location="tGoodsCombinationController.do?goAdd";
 }
 
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
// 	 window.location="tGoodsCombinationController.do?goView&id="+rowsData[0].id+"&goods_status=${goods_status }";
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
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tGoodsCombinationController.do?upload', "tGoodsCombinationList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tGoodsCombinationController.do?exportXls","tGoodsCombinationList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tGoodsCombinationController.do?exportXlsByT","tGoodsCombinationList");
}
 </script>