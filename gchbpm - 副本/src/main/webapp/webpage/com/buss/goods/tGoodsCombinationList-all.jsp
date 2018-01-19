<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsCombinationList" checkbox="false" extendParams="nowrap:false," fitColumns="true" title="商品组合列表" actionUrl="tGoodsCombinationController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="goodsUpdateTime" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"    queryMode="single" ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"   query="true" queryMode="single"  width="300"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合价"  field="groupPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="goodsUpdateTime"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="搜索关键字"  field="seoCare"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="所属零售商"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="商品类型"  field="goodsType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  dictionary="g_status" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="组合来源"  field="groupSource"  replace="零售商_1,g+_2" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="显示广告位"  field="isShow"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>
   <t:dgFunOpt funname="goSetAd(id,coverPic)"  title="设置广告位" exp="isShow#eq#N"></t:dgFunOpt>
   <t:dgFunOpt funname="goSetAd(id,coverPic)"  title="修改广告位" exp="isShow#eq#Y"></t:dgFunOpt>
   <t:dgConfOpt title="取消广告位" url="tGoodsCombinationController.do?goCancelAd&id={id}" message="确认取消广告位？" exp="isShow#eq#Y"/>
   <t:dgToolBar title="查看" icon="icon-search" url="" funname="goView"></t:dgToolBar>
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

//设置广告位
function goSetAd(id,coverPic) {
	 var url = "tGoodsCombinationController.do?goSetAd&id="+id+"&coverPic="+coverPic;
	 gridname="tGoodsCombinationList";
	createwindow("设置广告位", url,420,320);
}

//模板下载
function downloadTemp() {
	JeecgExcelExport("tGoodsCombinationController.do?downloadTemp","tGoodsCombinationList");
}
 </script>