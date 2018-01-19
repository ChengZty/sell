<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tradeCustomerList" checkbox="true" fitColumns="true" title="交易顾客列表"  
  actionUrl="tFocusCustomerController.do?datagridOfG&custType=3" idField="id" fit="true" queryMode="group" >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   	<t:dgCol title="分配导购" field="toGuideId" hidden="true" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="添加时间"  field="createDate"  query="true"  queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="130"></t:dgCol>
   <t:dgCol title="姓名"  field="name"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="性别"  field="sex"   query="true" queryMode="single" dictionary="sex" width="50"></t:dgCol>
   <t:dgCol title="顾客来源"  field="customerSource" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="常用联系方式"  field="commonContact" query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="外形"  field="appearance" query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="来源"  field="source" replace="APP_1,微信_2,QQ_3,后台_4,其他_5" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机号"  field="phoneNo"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机识别地区"  field="phoneArea" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="生日"  field="birthday" formatter="yyyy-MM-dd"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="年龄段"  field="birthdayRank" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="星座"  field="constellation" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="生肖"  field="zodiac" query="true"  queryMode="single"  width="60"></t:dgCol>
<%--    <t:dgCol title="登记地区"  field="registerArea" query="true"  queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="登记店铺"  field="phoneRegShopName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购" field="addGuideName" formatterjs="getGudieName" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="分配导购" field="toGuideName"  queryMode="single" width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
   <t:dgFunOpt title="取消分配" exp="toGuideId#empty#false" funname="doCancelGive(id)" />
<%--    <t:dgCol title="备注" field="remark"  queryMode="single" width="120"></t:dgCol> --%>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgFunOpt funname="goViewDetailAndTags(id)"  title="查看详情"></t:dgFunOpt> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tFocusCustomerController.do?goView" funname="detail" height="550"></t:dgToolBar> --%>
   <t:dgToolBar title="批量分配" icon="icon-edit" url="" onclick="findGuide('tradeCustomerList')" funname="findGuide"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tFocusCustomerController.do?goViewDetailAndTags" funname="goViewDetailAndTags" ></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" url="tFocusCustomerController.do?exportTradeXls" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="顾客分析"   icon="icon-search"   onclick="reviewByTab()" funname="review"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <div id='winPage' class='easyui-window' title=' ' style='width:320px;height:220px;' closed='true' modal='true' collapsible='false' minimizable='false' maximizable='false'>
 </div>
 
 <script src = "webpage/com/buss/order/mergeColums.js"></script>
 <script type="text/javascript">
$(document).ready(function(){
	//给时间控件加上样式
	$("#tradeCustomerListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${createDate_begin}")
		.attr("id","createDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createDate_end\')}'});});
	$("#tradeCustomerListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${createDate_end}")
		.attr("id","createDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createDate_begin\')}'});});
 	var html = "<span><span style='width:90px;text-align: right;display: inline-block;'>是否分配导购：</span><select name='haveGuide'><option value=''>---所有---</option><option value='1'>是</option><option value='2'>否</option></select></span>";
	$("#tradeCustomerListForm").append(html);
});
 
//如果没有添加导购就显示分配的导购 
 function getGudieName(value,row,index){
	 var guideName = value;
	 if(value=""){
		 guideName = row.toGuideName;
	 }
	 return guideName;
 }

//导出
function ExportXls(title,url,gname){
	var total = $('#'+gname).datagrid('getData').total;
// 	var pageNum = 5000;
// 	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,gname,1,total);
		return ;
// 	}else{
// 		showWinPage(total,url,datagridId,pageNum);
// 	}
}


/**
 * 查看顾客资料分析
 */
function reviewByTab() {
	document.location="tFocusCustomerController.do?goReview";
}

//查看详情和标签
function goViewDetailAndTags(title,url,gname){
// 	var url = "tFocusCustomerController.do?goViewDetailAndTags&id="+id;
	var rowsData = $('#'+gname).datagrid('getSelections');
	
	if (!rowsData || rowsData.length == 0) {
		tip('请选择查看项目');
		return;
	}
	if (rowsData.length > 1) {
		tip('请选择一条记录再查看');
		return;
	}
    url += '&id='+rowsData[0].id;
	window.open(url,'new');
}

//合并列
/* function mergeColumsFun(data){
	var arr = data.rows;
	if(arr.length>0){
		var field = "phoneNo";//判断合并的字段（连续并且相同则合并）
		var mergeFields =["phoneNo","phoneArea"];//合并的字段
		gridname='tradeCustomerList';
		mergeColums(arr,field,mergeFields,gridname);//合并列
		//绑定多行选中事件	
		 dealMultiSelect("phoneNo");
	}
} */


//查询导购
function findGuide(gname) {
	gridname = gname;
	var rows = $('#tradeCustomerList').datagrid('getSelections');
	if (!rows || rows.length == 0) {
		tip('请选择一条记录');
		return;
	}
	var ids = [];
	var phoneNos = [];
	for ( var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
		phoneNos.push(rows[i].phoneNo);
	}
/* 	var flag = false;
	flag = checkPhoneNos(phoneNos);
	if (flag) { */
		$.dialog.setting.zIndex = 300;
		if (typeof (windowapi) == 'undefined') {
			$.dialog({
				content : "url:userController.do?findGuideList",
				lock : true,
				title : "选择导购",
				width : 700,
				height : 550,
				cache : false,
				ok : function() {
					//					    	iframe = this.iframe.contentWindow;
					//					    	var selected = iframe.getSelectRows();
					iframe_doc = this.iframe.contentWindow.document;
					var sel_tr = $(iframe_doc)
							.find(
									".datagrid-view2 tr.datagrid-row-selected");
					if ($(sel_tr).length == 0) {
						alert("请选择导购");
						return false;
					} else {
						//批量分配导购
						var guideId = $(sel_tr).find( "td[field='id'] div").text();
						var url = "tFocusCustomerController.do?doBatchGive&guideId=" + guideId;
						$.ajax({
							url : url,
							type : 'post',
							data : {
								ids : ids.join(',')
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									tip(msg);
									reloadTable();
									$("#"+ gridname).datagrid('unselectAll');
									ids = '';
								}
							}
						});
					}
				},
				cancelVal : '关闭',
				cancel : true
			/*为true等价于function(){}*/
			}).zindex();
		} else {
			$.dialog({
				content : "url:" + url,
				lock : true,
				title : "选择导购",
				width : 700,
				height : 550,
				parent : windowapi,
				cache : false,
				ok : function() {
					iframe_doc = this.iframe.contentWindow.document;
					var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					if ($(sel_tr).length == 0) {
						alert("请选择导购");
						return false;
					} else {
						//批量分配导购
						var guideId = $(sel_tr).find("td[field='id'] div").text();
						var url = "tFocusCustomerController.do?doBatchGive&guideId=" + guideId;
						$.ajax({
							url : url,
							type : 'post',
							data : {
								ids : ids.join(',')
							},
							cache : false,
							success : function(data) {
								var d = $.parseJSON(data);
								if (d.success) {
									var msg = d.msg;
									tip(msg);
									reloadTable();
									$("#"+ gridname).datagrid('unselectAll');
									ids = '';
								}
							}
						});
					}
				},
				cancelVal : '关闭',
				cancel : true
			}).zindex();
		}
	/* } */
}

//校验号码是否为公有（导购没有录过）
function checkPhoneNos(phoneNos) {
	var flag = false;
	var url = "tFocusCustomerController.do?checkPhoneNos";
	$.ajax({
		url : url,
		type : 'post',
		async : false,
		data : {
			phoneNos : phoneNos.join(',')
		},
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				flag = true;
			} else {
				tip(d.msg);
				flag = false;
			}
		}
	});
	return flag;
}

//取消分配
function doCancelGive(id) {
	var url = "tFocusCustomerController.do?doCancelGive&id=" + id;
	var name = "tradeCustomerList";
	gridname = name;
	createdialog('取消分配确认 ', '确定取消分配吗 ?', url, name);
}
</script>