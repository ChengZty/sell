<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tFocusCustomerList" checkbox="true" fitColumns="true" title="顾客列表"  sortName="createDate" sortOrder="desc"
  actionUrl="tFocusCustomerController.do?datagrid&dtype=d" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="添加时间"  field="createDate"  query="true"  queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="130"></t:dgCol>
   <t:dgCol title="姓名"  field="name"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="性别"  field="sex"   query="true" queryMode="single" dictionary="sex" width="50"></t:dgCol>
   <t:dgCol title="手机号"  field="phoneNo"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="生日"  field="birthday" formatter="yyyy-MM-dd"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="年龄段"  field="birthdayRank" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="星座"  field="constellation" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="生肖"  field="zodiac" query="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="登记地区"  field="registerArea" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="登记店铺"  field="phoneRegShop" dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机识别地区"  field="phoneArea" query="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="顾客VIP等级"  field="vipLevel"  query="true" queryMode="single"  width="100"></t:dgCol> --%>
<%--    <t:dgCol title="经济实力"  field="finAbilityName"  query="true" queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="推送次数"  field="pushCount"  query="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="点击次数"  field="clickNumber"  query="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="购买次数"  field="buyCount"  query="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="类型"  field="type" query="true" replace="无效号码_0,无反应顾客_1,点击顾客_2,交易顾客_3" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否退订"  field="unOrder" query="true" replace="否_0,是_1" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="备注" field="remark" query="true" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tFocusCustomerController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tFocusCustomerController.do?goAdd2" funname="add" width="700"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tFocusCustomerController.do?goUpdate"  funname="update" width="700"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" onclick="updatebytab()"  funname="update"></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tFocusCustomerController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tFocusCustomerController.do?goView" funname="detail" height="550"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
<%--    <t:dgToolBar title="更新手机归属地" icon="icon-put" funname="updateEmptyPhoneArea"></t:dgToolBar> --%>
   <t:dgToolBar title="顾客分析"   icon="icon-search"   onclick="reviewByTab()" funname="review"></t:dgToolBar>
   <t:dgToolBar title="删除全部无效顾客"   icon="icon-remove"  url="tFocusCustomerController.do?doBatchDelDeadCust" funname="deleteALLDeadCust"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div id='winPage' class='easyui-window' title=' ' style='width:320px;height:220px;' closed='true' modal='true' collapsible='false' minimizable='false' maximizable='false'>
 
 
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#tFocusCustomerListtb").find("input[name^='createDate']").attr("class","Wdate").attr("style","height:25px;width:130px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
 });
 
 function addbytab() {
		document.location="tFocusCustomerController.do?goAdd";
}
 
function updatebytab(){
	var rowsData = $("#tFocusCustomerList").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var id=rowsData[0].id;
	document.location="tFocusCustomerController.do?goUpdate&id="+id;
}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入(单次不超过100000条)', 'tFocusCustomerController.do?upload&type=2', "tFocusCustomerList");
}

//导出
function ExportXls(){
	var total = $('#tFocusCustomerList').datagrid('getData').total;
// 	var pageNum = 5000;
	var url = "tFocusCustomerController.do?exportXls&exportType=d";
	var datagridId = "tFocusCustomerList";
// 	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
// 	}else{
// 		showWinPage(total,url,datagridId,pageNum);
// 	}
}


//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tFocusCustomerController.do?exportXlsByT","tFocusCustomerList");
}

//导入的错误记录下载
function ExportXlsxByFailList(key) {
	JeecgExcelExport("tFocusCustomerController.do?exportXlsxByFailList&key="+key,"tFocusCustomerList");
}

//更新手机归属地
// function updateEmptyPhoneArea() {
// 	$.ajax({
// 		url : "tFocusCustomerController.do?updateEmptyPhoneArea",
// 		type : 'post',
// 		cache : false,
// 		success : function(data) {
// 			var d = $.parseJSON(data);
// 			if (d.success) {
// 				var msg = d.msg;
// 				tip(msg);
// 				reloadTable();
// 			}
// 		}
// 	})
// }

/**
 * 查看顾客资料分析
 */
function reviewByTab() {
	document.location="tFocusCustomerController.do?goReview&page=listOfRet";
}

//删除全部的无效顾客
function deleteALLDeadCust(title,url,gname) {
	gridname=gname;
    	$.dialog.confirm('你确定删除全部无效顾客吗?', function(r) {
		   if (r) {
				$.ajax({
					url : url,
					type : 'post',
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
// 							$("#"+gname).datagrid('unselectAll');
						}
					}
				});
			}
		});
}
</script>