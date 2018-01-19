<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSmsSendInfoList" checkbox="true" fitColumns="false" title="短信发送 【剩余${surplusNumber }条】" actionUrl="tSmsSendInfoController.do?datagrid" 
  idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" hidden="true"  formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="批次号"  field="batchNo"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="签名名称"  field="autographName"   hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="发送内容"  field="content"    queryMode="group"  width="120"></t:dgCol> 
<%--    <t:dgCol title="结尾内容2"  field="contentEnd2"   hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="提交时间"  field="sendTime"  formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"   width="120"></t:dgCol>
   <t:dgCol title="发送类型"  field="sendTimeType"  replace="即时发送_0,定时发送_1"  query="true"  queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgCol title="零售商ID"  field="retailerId"   hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="发送状态"  field="sendStatus" replace="未发送_0,提交中_1,提交成功_2,提交失败_3,发送成功_4,审核失败_5"   query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="推送类型"  field="pushType" replace="海报_0,商品_1" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="推送总量"  field="pushCount" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="送达量"  field="reach" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="送达率(%)"  field="reachRate" formatterjs="getReachRate" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="点击人数"  field="clickNumber" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="点击率(%)"  field="clickRate" formatterjs="getClickRate" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="购买单量"  field="buySingle" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="购买率(%)"  field="buyRate" queryMode="group"  width="80"></t:dgCol>
<%--    <t:dgCol title="资料可信度"  field="reliability" queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="内容吸引度"  field="attractionDegree" queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="营销成功率(%)"  field="successRate" queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSmsSendInfoController.do?doDel&id={id}" />
   <t:dgFunOpt title="明细" funname="viewDetails(id)" ></t:dgFunOpt>
   <t:dgFunOpt funname="doSend(id)" title="发送" exp="sendStatus#eq#0"></t:dgFunOpt>
<%--    <t:dgFunOpt funname="doSend(id)" title="再次发送" exp="sendStatus#eq#1"></t:dgFunOpt> --%>
   <t:dgToolBar title="发短信" icon="icon-add" url="" onclick="addbytab()" funname="add"></t:dgToolBar>
    <t:dgToolBar title="编辑" icon="icon-edit" url="" onclick="updatebytab()" funname="update"></t:dgToolBar> 
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSmsSendInfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tSmsSendInfoController.do?goUpdate&viewType=hide" funname="detail" width="850" height="750"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tSmsSendInfoListtb").find("input[name='sendTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;")
 			.attr("id","sendTime_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'sendTime_end\')}'});});
 			$("#tSmsSendInfoListtb").find("input[name='sendTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;")
 			.attr("id","sendTime_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sendTime_begin\')}'});});
 });
 
function addbytab() {
		document.location="tSmsSendInfoController.do?goAdd&source=0";
}

function updatebytab(){
	var rowsData = $("#tSmsSendInfoList").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var id=rowsData[0].id;
	if("2"==rowsData[0].sendStatus){
		tip('已发送的短信不能编辑');
		return;
	}
	document.location = "tSmsSendInfoController.do?goUpdate&source=0&id="+id;
}
function doSend(id) {
	var url = "tSmsSendInfoController.do?doAdd&id="+id+"&sendStatus=1";
	gridname = "tSmsSendInfoList";
	createdialog('发送确认 ', '发送后需要短信平台进行审核，</br>可能会出现一定延迟，确认发送？', url,gridname);
}
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