<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPersonList" checkbox="false" fitColumns="false" extendParams="nowrap:false," sortName="createDate" sortOrder="desc" title="导购信息表" actionUrl="tPersonController.do?datagrid&user_Type=03" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帐号Id"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="头像"  field="photo"  image="true" imageSize="80" width="80"></t:dgCol>
   <t:dgCol title="姓名"  field="realName"   query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="昵称"  field="name"   query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="性别"  field="sex"  replace="女_1,男_0"  queryMode="single" dictionary="sex" width="40"></t:dgCol>
   <t:dgCol title="手机号码"  field="phoneNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="身份证号"  field="idCard"   queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="生日"  field="birthday" formatter="yyyy-MM-dd"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="所在省"  field="provinceId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所在市"  field="cityId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属区域"  field="area"   queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="件数"  field="quantity"    hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="金额"  field="money"    hidden="true"  width="120"></t:dgCol>
<%--    <t:dgCol title="所属导购"  field="toGuideId"  hidden="true"  queryMode="single"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="有标签"  field="hasTags" query="true" replace="是_1,否_0"  queryMode="single"  width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="顾客能否绑定"  field="canBind"  replace="能_1,否_0"  queryMode="single"  width="90"></t:dgCol> --%>
   <t:dgCol title="登记店铺"  field="storeId" query="true" dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="220"></t:dgCol>
   <t:dgFunOpt funname="goView(id)" title="查看"></t:dgFunOpt>
<%--    <t:dgConfOpt title="允许绑定" exp="canBind#eq#0" url="tPersonController.do?doCanBind&id={id}" message="确定让顾客绑？"/> --%>
<%--    <t:dgConfOpt title="暂缓顾客绑定" exp="canBind#eq#1" url="tPersonController.do?doCanNotBind&id={id}" message="确定暂缓顾客绑定？"/> --%>
   <t:dgFunOpt title="查看服务顾客"  funname="viewMyCustomers(userId,realName)"  />
   
   <t:dgFunOpt title="查看金币"  funname="viewGoldDetail(userId,realName)" exp="retailerId#empty#false" />
<%--    <t:dgFunOpt title="查看标签" exp="hasTags#eq#1" funname="addTags(userId,realName)"  /> --%>
<%--    <t:dgDelOpt title="删除" url="tPersonController.do?doDel&id={id}" /> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="tPersonController.do?goAddGuide" funname="add" width="800"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tPersonController.do?goUpdateGuide" funname="update" width="800"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPersonController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tPersonController.do?goUpdateGuide" funname="detail"></t:dgToolBar> --%>
   <t:dgToolBar title="导入导购" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导购模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
   <t:dgToolBar title="导出基础信息" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="导出问题信息" icon="icon-putout" funname="ExportQuestionXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
  <div data-options="region:'east',
	title:'查看金币记录',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 500px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="subListpanel"></div>
</div>

  <!-- <div data-options="region:'east',
	title:'查看标签',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 250px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="subListpanel"></div>
</div> -->
 
<!--   <div region="east" style="width: 250px;" split="true"> -->
<!-- <div tools="#tt" class="easyui-panel" title='查看标签' style="padding: 10px;" fit="true" border="false" id="function-panel"></div> -->
<!-- </div> -->
 <div id='winPage' class='easyui-window' title=' ' style='width:320px;height:220px;' closed='true' modal='true' collapsible='false' minimizable='false' maximizable='false'>
 </div>
  <link rel="stylesheet" href="plug-in/html5uploader/html5uploader.css">
 <script src="plug-in/html5uploader/jquery.html5uploader.js"></script>
 <script type = "text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 function goView(id){
	 var url = "tPersonController.do?goViewGuide&id="+id;
	 window.open(url,'new');
}
 
//导购点击报表详情
 function addTags(userId,name){
		if(li_east == 0){
			   $('#main_depart_list').layout('expand','east'); 
		}
 		$('#subListpanel').panel("refresh", "tPersonTagsController.do?listOfRetailer&user_Id=" + userId);
 } 
 
//  function addTags(userId,name) {
// 		$("#function-panel").panel(
// 			{
// 				title : '姓名:' + name,
// 				href:"tPersonTagsController.do?listOfRetailer&user_Id=" + userId
// 			}
// 		);
// 		$('#function-panel').panel("refresh" );
// 	}
 
 //查看导购的顾客列表
 function viewMyCustomers(userId,realName){
	 var title = realName+"服务的顾客列表";
	 var addurl = "tFocusCustomerController.do?goViewCustomersOfGuide&guideId="+userId;
	 createdetailwindow(title, addurl,1100,600) ;
 }

 
//导入
// function ImportXls() {
// 	openuploadwin('Excel导入', 'tPersonController.do?upload', "tPersonList");
// }

function ImportXls() {
  openuploadwinH5('导购导入', 'tPersonController.do?importExcel', "tPersonList");
}

//导出
function ExportXls() {
	var total = $('#tPersonList').datagrid('getData').total;
	var pageNum = 5000;
	var url = "tPersonController.do?exportGuideXls";
	var datagridId = "tPersonList";
	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
	}else{
		showWinPage(total,url,datagridId,pageNum);
	}
}

function ExportQuestionXls(){
	var total = $('#tPersonList').datagrid('getData').total;
	var pageNum = 5000;
	var url = "tPersonController.do?exportGuideQuestionXls";
	var datagridId = "tPersonList";
	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
	}else{
		showWinPage(total,url,datagridId,pageNum);
	}
}

//查看金币记录
function viewGoldDetail(userId,realName){
	if(li_east == 0){
		   $('#main_depart_list').layout('expand','east'); 
	}
	$("div.layout-panel-east div.panel-title").text(realName+"的金币记录");
	$('#subListpanel').panel("refresh", "tPersonController.do?viewGoldDetail&guideId="+userId);
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPersonController.do?exportXlsByT","tPersonList");
}
</script>
  <style type="text/css">
  div.datagrid-cell-rownumber,div.datagrid-header-rownumber{ 
  	min-width: 15px; 
  } 
 </style>
