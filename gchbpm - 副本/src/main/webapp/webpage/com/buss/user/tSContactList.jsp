<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
		<t:datagrid name="tSContactList" checkbox="true" fitColumns="false"
			extendParams="nowrap:false," title="导购回访列表(默认显示当天数据)"
			actionUrl="tSContactController.do?datagrid" idField="id" fit="true"
			queryMode="group">
			<%--  <t:dgCol title="主键"  field="id"   queryMode="single"  width="120"></t:dgCol> --%>
			<t:dgCol title="导购ID" field="id" hidden="true" width="120"></t:dgCol>
			<%-- <t:dgCol title="导购所属店铺"  field="storeName" width="120"></t:dgCol> --%>
			<t:dgCol title="导购姓名" field="guideName" query="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="导购所属店铺" field="storeId" replace="${stores}"
				query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="QQ维护次数" field="qqNumber" width="100"></t:dgCol>
			<t:dgCol title="QQ维护人数" field="qqCount" width="100"></t:dgCol>
			<t:dgCol title="微信维护次数" field="weChartNumber" width="100"></t:dgCol>
			<t:dgCol title="微信维护人数" field="weChartCount" width="100"></t:dgCol>
			<t:dgCol title="短信维护次数" field="msgNumber" width="100"></t:dgCol>
			<t:dgCol title="短信维护人数" field="msgCount" width="100"></t:dgCol>
			<t:dgCol title="电话维护次数" field="phoneNumber" width="100"></t:dgCol>
			<t:dgCol title="电话维护人数" field="phoneCount" width="100"></t:dgCol>
			<t:dgCol title="维护次数合计" field="number" width="100"></t:dgCol>
			<t:dgCol title="维护人数合计" field="userCount" width="100"></t:dgCol>
			<t:dgCol title="联系日期" field="concatTime" hidden="true"
				formatter="yyyy-MM-dd" query="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgFunOpt funname="viewDetails(id)" title="回访详细"></t:dgFunOpt>
			
   			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
	</div>
 </div>  


 <script type="text/javascript">
 $(document).ready(function(){
	 	$("#tSContactListtb").find("input[name='concatTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","concatTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'concatTime_end\')}'});});
		$("#tSContactListtb").find("input[name='concatTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","concatTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'concatTime_begin\')}'});});
 
 });
 
 
//导出
 function ExportXls() {
 	JeecgExcelExport("tSContactController.do?exportGoodsXls","tSContactList");
 }
 
//查看明细
function viewDetails(id){
	$.dialog.setting.zIndex = 1000;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content : "url:tSContactController.do?goContactList&guideId="+id,
				lock : true,
				title : "查看明细",
				width : 1000,
				height : 570,
				cache : false,
				cancelVal : '关闭',
				cancel : true/*为true等价于function(){}*/
			}).zindex();
	 }
	else{
		$.dialog({
			content : "url:tSContactController.do?goContactList&guideId="+id,
			lock : true,
			title:"查看明细",
			width:1000,
			height: 570,
			parent:windowapi,
			cache:false,
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
}

 </script>