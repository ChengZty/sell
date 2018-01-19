<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSmsSendList" checkbox="false" fitColumns="true" title="短信明细" actionUrl="tSmsSendController.do?datagrid&send_Info_Id=${smsSendInfo.id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"   hidden="true"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="批次号"  field="batchNo"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="手机号码"  field="phone"  query="true"  queryMode="single"  width="90" align="center"></t:dgCol>
   <t:dgCol title="姓名"  field="name"    queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="长连接"  field="longUrl"    hidden="true" width="120"></t:dgCol>
   <t:dgCol title="短连接"  field="shortUrl"    hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="发送状态"  field="sendStatus"  replace="是_1,否_0"  queryMode="single" query="true" width="60"  align="center"></t:dgCol>
   <t:dgCol title="接收状态"  field="receiveStatus"  replace="是_1,否_0"    queryMode="single" query="true" width="60" align="center"></t:dgCol>
   <t:dgCol title="是否点击"  field="isClick"   replace="是_1,否_0"   queryMode="single" query="true" width="60" align="center"></t:dgCol>
   <t:dgCol title="点击次数"  field="clickNumber" queryMode="single" query="true" width="60" align="center"></t:dgCol>
   <t:dgCol title="类型"  field="type" query="true" replace="无效号码_0,无反应顾客_1,点击顾客_2,交易顾客_3" queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgCol title="发送时间"  field="sendTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120" align="center"></t:dgCol> --%>
<%--    <t:dgCol title="时间发送类型"  field="sendTimeType"  replace="即时发送_0,定时发送_1"  queryMode="group"  width="80" align="center"></t:dgCol> --%>
   <t:dgCol title="发送内容"  field="sendContent" formatterjs="showFullContent"   width="350"  align="center"></t:dgCol>
   <c:if test="${smsSendInfo.sendStatus =='0' }">
	   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
	   <t:dgFunOpt funname="doDel(id)" title="删除"></t:dgFunOpt>
<%-- 	   <t:dgDelOpt title="删除" url="tSmsSendController.do?doDel&id={id}" /> --%>
   </c:if>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tSmsSendController.do?goAdd" funname="add" width="615" height="450"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tSmsSendController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSmsSendController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tSmsSendController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
	<t:dgToolBar title="按查询条件导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
   </div>
   <div id='winPage' class='easyui-window' title=' ' style='width:320px;height:220px;' closed='true' modal='true' collapsible='false' minimizable='false' maximizable='false'>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tSmsSendListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tSmsSendListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tSmsSendListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tSmsSendListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tSmsSendListtb").find("input[name='sendTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tSmsSendListtb").find("input[name='sendTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

/*  //导出
function ExportXls(){
	var total = $('#tSmsSendList').datagrid('getData').total;
	var pageNum = 5000;
	var url = "tSmsSendController.do?exportXls";
	var datagridId = "tSmsSendList";
	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
	}else{
		showWinPage(total,url,datagridId,pageNum);
	}
} */
 
function ExportXls() {
	var sendInfoId = "${smsSendInfo.id }";
// 	JeecgExcelExport("tSmsSendController.do?exportXls&sendInfoId="+sendInfoId,"tSmsSendList");
	var total = $('#tSmsSendList').datagrid('getData').total;
	var pageNum = 5000;
	var url = "tSmsSendController.do?exportXls&sendInfoId="+sendInfoId;
	var datagridId = "tSmsSendList";
	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
	}else{
		showWinPage(total,url,datagridId,pageNum);
	}
}

//显示内容

function showFullContent(value,row,index){
	var shortUrl = row.shortUrl;
	var content = "【${smsSendInfo.autographName}】${smsSendInfo.content}${smsSendInfo.contentEnd}${smsSendInfo.contentEnd2}";
 		return content.replace(/{url}/g,shortUrl);
 }
 
function doDel(id) {
	var url = "tSmsSendController.do?doDel&id="+id;
	gridname = "tSmsSendList";
// 	createdialog('确认 ', '确认删除该记录？', url,gridname);
	$.dialog.confirm('确认删除该记录？', function(){
		doSubmit(url,gridname);
		rowid = '';
	}, function(){
	}).zindex(1999);
}
 </script>