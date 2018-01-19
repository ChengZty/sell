<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPersonList"   checkbox="true" fitColumns="false" extendParams="nowrap:false," sortName="createDate" sortOrder="desc" title="导购信息表" actionUrl="tPersonController.do?datagrid&user_Type=03" idField="id" queryMode="group">
   <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帐号Id"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="头像"  field="photo"  image="true" imageSize="80" width="80"></t:dgCol>
   <t:dgCol title="姓名"  field="realName"   query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="昵称"  field="name"   query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="性别"  field="sex"    queryMode="single" dictionary="sex" width="40"></t:dgCol>
   <t:dgCol title="手机号码"  field="phoneNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="身份证号"  field="idCard"   queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="生日"  field="birthday" formatter="yyyy-MM-dd"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="所在省"  field="provinceId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所在市"  field="cityId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属区域"  field="area"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="件数"  field="quantity"    hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="金额"  field="money"    hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="所属零售商" field="toRetailerId" query="true" dictionary="t_s_user,id,realname,user_type='02' and retailer_type ='1' and status = 'A' and user_status ='1'"></t:dgCol>
   <t:dgCol title="VIP等级"  field="vipLevel"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="帮手大类"  field="helperType" hidden="true"   queryMode="single" dictionary="hp_type" width="120"></t:dgCol> --%>
   <t:dgCol title="用户类别"  field="userType" hidden="true"   queryMode="single" dictionary="user_type" width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否有标签"  field="hasTags"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
   <t:dgFunOpt funname="goView(id)" title="查看"></t:dgFunOpt>
<%--    <t:dgFunOpt title="贴标签" exp="hasTags#eq#0" funname="addTags(userId,realName)"  /> --%>
<%--    <t:dgFunOpt title="改标签" exp="hasTags#eq#1" funname="addTags(userId,realName)"  /> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tPersonController.do?goUpdateGuide" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="换馆" icon="icon-edit" funname="changeArea"></t:dgToolBar> --%>
   <t:dgToolBar title="停用" icon="icon-edit" url="userController.do?lock&lockvalue=0" funname="inactiveAccount"></t:dgToolBar>
   <t:dgToolBar title="导出基础信息" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
    <t:dgToolBar title="导出问题信息" icon="icon-putout" funname="ExportQuestionXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <!-- 
  <div data-options="region:'east',
	title:'标签设置',
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
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="function-panel"></div>
</div> -->
<!--  <div region="east" style="width: 500px;" split="true" > -->
<!-- <div tools="#tt" class="easyui-panel" title='标签设置' style="padding: 10px;" fit="true"  border="false" id="function-panel"></div> -->
<!-- </div> -->
 <div id='winPage' class='easyui-window' title=' ' style='width:320px;height:220px;' closed='true' modal='true' collapsed='true' minimizable='false' maximizable='false'>
 </div>
 <script type="text/javascript">
 gridname = "tPersonList";
 $(document).ready(function(){
 		$("#tPersonListtb .datagrid-toolbar a").eq(0).attr("title","换馆后将只保留管家基础数据");
 });
 
//换馆
function changeArea(){
	var url = "userController.do?retailerList2";
	var rowsData = $("#tPersonList").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var userId=rowsData[0].userId;
	var personId=rowsData[0].id;
	
	$.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择生活馆",
				width:700,
				height: 500,
				cache:false,
			    ok: function(){
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
				    		//$.messager.alert('错误提示', '请选择品牌');
			    			alert("请选择生活馆");
				    		return false;
					 }else{
						 var storeId = $(sel_tr).find("td[field='id'] div").text();
						 doChangeArea(personId,userId,storeId);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择生活馆",
				width:700,
				height: 500,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
					    		//$.messager.alert('错误提示', '请选择品牌');
				    			alert("请选择生活馆");
					    		return false;
						 }else{
							 var storeId = $(sel_tr).find("td[field='id'] div").text();
								doChangeArea(personId,userId,storeId);
						 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
} 
 
//换馆及对应的基础信息
function doChangeArea(personId,userId,storeId){
	var url = "tPersonController.do?doChangeArea"
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data :  {
			personId : personId,
			userId : userId,
			storeId : storeId //目标零售商ID
		},
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			var msg = d.msg;
			if (d.success) {
				tip(msg);
				reloadTable();
			}else{
				tip(msg);
			}
		}
	});
}

function inactiveAccount(title,url,gname){
	var rowsData = $('#'+gname).datagrid('getSelections');
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	url += '&id='+rowsData[0].userId;
	$.dialog.confirm('确定停用用户吗', function(){
		lockuploadify(url);
	}, function(){
	});
	
}

function lockuploadify(url) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			var msg = d.msg;
				tip(msg);
// 				reloadTable();
			}
			
		}
	});
}

function addTags(userId,realName) {
	if(li_east == 0){
		   $('#main_depart_list').layout('expand','east'); 
	}
	$("#function-panel").panel(
		{
			title : '姓名:' + realName,
			href:"tPersonTagsController.do?list&user_Id=" + userId
		}
	);
// 	$('#function-panel').panel("refresh" );
}

function goView(id){
	 var url = "tPersonController.do?goViewGuide&id="+id;
	 window.open(url,'new');
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
</script>
