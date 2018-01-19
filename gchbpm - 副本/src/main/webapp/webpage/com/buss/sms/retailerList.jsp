<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>零售商列表</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
<t:datagrid  pagination="true"  name="retailerList" title="零售商短信设置(g+剩余${smsNum}条短信)"  actionUrl="tSmsSubAccountController.do?retailerListDatagrid" idField="id" checkbox="true" showRefresh="false"  fit="true"  queryMode="group" onLoadSuccess="initCheck">
	<t:dgCol title="零售商ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" field="username" width="100" query="true"></t:dgCol>
	<t:dgCol title="零售商编码" field="userCode" width="100" query="true" ></t:dgCol>
	<t:dgCol title="零售商名称" field="realName" width="120" query="true" ></t:dgCol>
<%-- 	<t:dgCol title="短信账号" field="smsName" width="120" query="false" ></t:dgCol> --%>
	<t:dgCol title="短信总条数" field="smsNumber" width="120" query="false" ></t:dgCol>
	<t:dgCol title="锁定条数" field="lockingNumber" width="120" query="false" ></t:dgCol>
<%-- 	<t:dgCol title="产品ID"  field="productId"    queryMode="group"  width="120"></t:dgCol> --%>
	<t:dgCol title="公司签名"  field="companyAutographName"    queryMode="group"  width="120"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
	<t:dgFunOpt funname="getInvisibleAutographList(id,realName)" title="签名设置" ></t:dgFunOpt>
<%-- 	<t:dgCol title="角色" field="userKey" ></t:dgCol> --%>
<t:dgToolBar title="设置短信账号" icon="icon-edit"  url=""  funname="setMessageAccount"></t:dgToolBar>
<%-- <t:dgToolBar title="刷新账号短信条数" icon="icon-refresh" url="" funname="doUpdateSurplusNumber"></t:dgToolBar> --%>
</t:datagrid>
  </div>
 </div>
  <div region="east" style="width: 600px;" split="true">
	<div tools="#tt" class="easyui-panel" title='短信签名设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
	var smsNum = "${smsNum}"-0;
	if(smsNum<5000){
		tip("短信剩余不足5000条，请注意充值哦");
	}
})
function getInvisibleAutographList(id,realName) {
	$("#function-panel").panel(
		{
			title : '短信签名设置:' + realName,
			href:"tSmsAutographController.do?tSmsAutographList&retailerId="+id
		}
	);
}

function initCheck(data){
	var ids = "${ids}";
	var idArr = ids.split(",");
	for(var i=0;i<idArr.length;i++){
		if(idArr[i]!=""){
			$("#retailerList").datagrid("selectRecord",idArr[i]);
		}
	}
}
//新增
function addbytab() {
		document.location="tSmsSubAccountController.do?goAdd";
}

//修改
function updatebytab(){
	var rowsData = $("#retailerList").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].id;
		document.location="tSmsSubAccountController.do?goAdd&retailerId="+id;
}


//设置短信账号
function setMessageAccount() {
	var rowsData = $('#retailerList').datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择零售商');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
/* 	var ids = "";
//			  d是要遍历的集合
//			  index就是索引值
//			  domEle 表示获取遍历每一个dom对
	$.each(rowsData,function (index,domEle){
		ids += domEle.id + ",";
		});
// 	if(isRestful!='undefined'&&isRestful){
// 		url += '/'+rowsData[0].id;
// 	}else{
		url += '&ids='+ids;
// 	}
// 	createwindow('批量发货',url,360,150); */
 		var url = 'tSmsSubAccountController.do?goAdd&retailerId='+rowsData[0].id;
		var title = "零售商短信账号设置";
		var width = 600;
		var height = 200;
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				lock : true,
				zIndex: 300,
				width:width,
				height:height,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					//setTimeout("updateToBeFahuoCount()",800);
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
		}else{
			W.$.dialog({
				content: 'url:'+url,
				lock : true,
				width:width,
				zIndex:300,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					//setTimeout("updateToBeFahuoCount()",800);
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
		}
}
/* function doUpdateSurplusNumber(){
	 $.ajax({
			url : "tSmsSubAccountController.do?doUpdateSurplusNumber",
			type : 'post',
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
// 					tip("短信账号剩余条数更新成功");
					location.reload();
				}else{
					tip("短信账号剩余条数更新失败");
				}
			}
		});
} */
</script>