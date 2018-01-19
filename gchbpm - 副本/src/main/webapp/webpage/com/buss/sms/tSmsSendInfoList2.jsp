<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSmsSendInfoList" checkbox="false" fitColumns="false" title="短信推送列表" actionUrl="tSmsSendInfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"    width="80"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" hidden="true" formatter="yyyy-MM-dd"     width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"    width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"   hidden="true"    width="80"></t:dgCol>
   <t:dgCol title="批次号"  field="batchNo"      width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="签名名称"  field="autographName"   hidden="true"    width="120"></t:dgCol>
   <t:dgCol title="正文内容"  field="content"      width="120"></t:dgCol>
   <t:dgCol title="提交时间"  field="sendTime"  formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"   width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"   hidden="true"    width="80"></t:dgCol>
   <t:dgCol title="发送状态"  field="sendStatus" replace="未发送_0,提交中_1,提交成功_2,提交失败_3,发送成功_4,审核失败_5"  query="true"   width="80"></t:dgCol>
   <t:dgCol title="推送总量"  field="pushCount"      width="80"></t:dgCol>
   <t:dgCol title="送达量"  field="reach"  width="80"></t:dgCol>
   <t:dgCol title="送达率(%)"  field="reachRate" formatterjs="getReachRate" width="80"></t:dgCol>
   <t:dgCol title="点击人数"  field="clickNumber" width="80"></t:dgCol>
   <t:dgCol title="点击率(%)"  field="clickRate" formatterjs="getClickRate" width="80"></t:dgCol>
   <t:dgCol title="购买单量"  field="buySingle" width="80"></t:dgCol>
   <t:dgCol title="购买率(%)"  field="buyRate" width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="130"></t:dgCol>
   <t:dgFunOpt funname="viewReport(id)" title="查看报表" ></t:dgFunOpt>
   <t:dgFunOpt title="明细" funname="viewDetails(id)" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="tSmsSendInfoController.do?goUpdate&viewType=hide" funname="detail" width="800" height="700"></t:dgToolBar>
   <t:dgToolBar title="查看综合报表" icon="icon-search" url="" funname="viewReportSummary"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
	 	$("#tSmsSendInfoListtb").find("input[name='sendTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","sendTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'sendTime_end\')}'});});
		$("#tSmsSendInfoListtb").find("input[name='sendTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
		.attr("id","sendTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sendTime_begin\')}'});});
 });
 

function viewReport(id) {
	var addurl = "reportController.do?smsInfoTabs&smsId="+id;
	var title = "推送报表";
	var width = 700;
	var height = 600;
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			//zIndex:1990,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
// 		    ok: function(){
// 		    	iframe = this.iframe.contentWindow;
// 				saveObj();
// 				return false;
// 		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			//zIndex:1990,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
// 		    ok: function(){
// 		    	iframe = this.iframe.contentWindow;
// 				saveObj();
// 				return false;
// 		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
}

function viewReportSummary() {
	var params = $("#tSmsSendInfoListForm").serialize();
	var addurl = "reportController.do?smsReportSummaryPage&"+params;
	var title = "推送综合报表";
	var width = 700;
	var height = 600;
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			//zIndex:1990,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			//zIndex:1990,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
}

//查看明细
function viewDetails(id){
	$.dialog.setting.zIndex = 1000;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content : "url:tSmsSendController.do?tSmsSend&sendInfoId="+id,
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
			content : "url:tSmsSendController.do?tSmsSend&id="+id,
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

//计算送达率
function getReachRate(value,row,index){
	if(row.pushCount==0){
		return 0.00;
	}
	return (row.reach/row.pushCount*100).toFixed(2);
}

//计算点击率
function getClickRate(value,row,index){
	if(row.pushCount==0){
		return 0.00;
	}
	return (row.clickNumber/row.pushCount*100).toFixed(2);
}

 </script>