<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsCountList" checkbox="true" fitColumns="false" title="商品导购浏览统计报表" actionUrl="guideGoodsCountController.do?tGoodsCountDatagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="查询时间"  field="searchTime"  hidden="true" query="true"   queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="商品款号"  field="code"  query="true"  width="180"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"   query="true"    width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true"     width="120"></t:dgCol>
<%--    <t:dgCol title="一级类目"  field="topCategoryId"  hidden="true" dictionary="t_s_category,id,name,level = '1' and status = 'A' " queryMode="single"  width="50"></t:dgCol> --%>
<%--    <t:dgCol title="二级类目"  field="subCategoryId"  hidden="true" dictionary="t_s_category,id,name,level = '2' and status = 'A' " queryMode="single"  width="50"></t:dgCol> --%>
<%--    <t:dgCol title="三级类目"  field="thridCategoryId"  hidden="true"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="50"></t:dgCol> --%>
<%--    <t:dgCol title="导购所属店铺"  field="storeId"  replace="${stores}" query="true"  width="120"></t:dgCol> --%>
	<t:dgCol title="导购所属店铺"  field="storeId"  query="true"  dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" width="120"></t:dgCol>
<%--    <t:dgCol title="导购所属店铺"  field="storeName"  query="true" width="120"></t:dgCol> --%>
<%--    <t:dgCol title="导购姓名"  field="personId" replace="${persons}" hidden="true"  query="true"  width="120"></t:dgCol> --%>
   <t:dgCol title="导购姓名"  field="guideName"  query="true"  width="120"></t:dgCol>
   <t:dgCol title="点击次数"  field="totalClickNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="第一次点击时间"  field="minClickTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="最近一次点击时间"  field="maxClickTime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="总停留时间(s)"  field="totalStillTime"  queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  <input type="hidden" name="notInitSearch" value="1"/>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#tGoodsCountListtb").find("input[name='searchTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_begin}")
		.attr("id","searchTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchTime_end\')}'});});
	$("#tGoodsCountListtb").find("input[name='searchTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_end}")
		.attr("id","searchTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchTime_begin\')}'});});
 			
//  			$("#tGoodsCountListtb").find("select[name='storeId']").change(function(){
//  				getPersons(this);
//  			});
 });
 

//导出
function ExportXls() {
	JeecgExcelExport("guideGoodsCountController.do?exportGoodsXls","tGoodsCountList");
}
/* 
function getPersons(obj)
{
	var storeId = $(obj).val();
	$("#tGoodsCountListtb").find("[name='personId']").empty();
	$("#tGoodsCountListtb").find("[name='personId']").append("<option value=''>---所有---</option>");
	var url="tPersonController.do?getPersonsByStoreId&storeId="+ storeId+"&date=" + new Date().getTime();
	jQuery.ajax({
		url:url,
		type:"GET",
		dataType:"JSON",
		async: false,
		success:function(data){
			if(data.success){
				var list = data.obj;
       			if(list.length > 0){
       				console.log(111);
					for(var i=0;i<list.length;i++){
		       			var item2 = " <option  value='" + list[i].id + "'>"+ list[i].name+"</option>";
		       			console.log(item2);
		       			$("#tGoodsCountListtb").find("[name='personId']").append(item2);
					}
       			}	
			}	
		}
     });
}
 */
 </script>