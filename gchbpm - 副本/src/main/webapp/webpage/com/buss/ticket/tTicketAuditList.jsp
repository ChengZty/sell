<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tTicketAuditList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="优惠券审核列表" sortName="updateDate" sortOrder="desc"
   actionUrl="tTicketInfoController.do?datagrid" idField="id" fit="true"  queryMode="group" onLoadSuccess="mergeColumsFun">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="批次号"  field="batchNo" queryMode="single"  width="110"></t:dgCol>
   <t:dgCol title="券名"  field="ticketName" query="true" queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="类型"  field="type" query="true" dictionary="tkt_type" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="店铺"  field="storeId" query="true"  dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="面值/折扣"  field="faceValue"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="使用条件"  field="leastMoney"  formatterjs="showCondition"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="限制使用张数"  field="sheetLimit"  formatterjs="showLimitSheet"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="使用类型"  field="useType" hidden="true"   queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="起始时间"  field="beginTime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="审核状态"  field="ticketStatus" query="true" replace="待审核_1,已审核_2,已作废_3"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作人"  field="auditor"  width="80"></t:dgCol>
   <t:dgCol title="总张数"  field="sheetTotal"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="已领取张数"  field="sheetSent"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="剩余领取张数"  field="sheetRemain"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="使用张数"  field="sheetUsed"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="适用范围"  field="usingRange" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
   <t:dgDelOpt title="删除" url="tTicketInfoController.do?doDel&id={id}" exp="ticketStatus#eq#1"/>
<%--    <t:dgFunOpt title="停用"  funname="doStop(id)" exp="ticketStatus#eq#2"/> --%>
   <t:dgFunOpt title="详情"  funname="viewDetail(id)" exp="ticketStatus#ne#1"/>
   <t:dgToolBar title="批量审核" icon="icon-edit" url="tTicketInfoController.do?doBatchAudit" funname="doBatchAudit"></t:dgToolBar>
   <t:dgToolBar title="批量作废" icon="icon-edit" url="tTicketInfoController.do?doBatchStop" funname="doBatchStop"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tTicketInfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tTicketInfoController.do?doBatchDel" funname="doBatchDel"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tTicketInfoController.do?goUpdate2&load=detail" funname="goViewBySelect" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
<div data-options="region:'east',
	title:'券使用详情',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 420px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="subListpanel"></div>
</div>
 
 <script src = "webpage/com/buss/ticket/tTicketInfoList.js?v=1.14"></script>
 <script src = "webpage/com/buss/order/mergeColums.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
	    $("#tTicketAuditListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>起始时间：</span><input name='begin_Time' type='text' class='Wdate' style='width:90px'/>");
		$("#tTicketAuditListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>结束时间：</span><input name='end_Time' type='text' class='Wdate' style='width:90px'/>");
		$("#tTicketAuditListForm").find("input[name='begin_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tTicketAuditListForm").find("input[name='end_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tTicketAuditListtb a[iconcls='icon-reload']").attr("onclick","searchMainReset('tTicketAuditList')");//防止和右边的列表的重置冲突
 });

 //查看详情
 function viewDetail(id){
	 if(li_east == 0){
		   $('#main_depart_list').layout('expand','east'); 
	}
// 	$("div.layout-panel-east div.panel-title").text("券使用详情");
	$('#subListpanel').panel("refresh", 'tTicketInfoController.do?viewDetail&id='+id);
 }
 
 //重置
 function searchMainReset(name) {
		$("#" + name + "tb").find(":input").val("");
		var queryParams = $('#'+name).datagrid('options').queryParams;
		$('#'+name+"tb").find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#'+name)
				.datagrid(
						{
							url : 'tTicketInfoController.do?datagrid&field=id,updateDate,batchNo,ticketName,type,storeId,faceValue,leastMoney,sheetLimit,useType,beginTime,endTime,ticketStatus,auditor,sheetTotal,sheetSent,sheetRemain,sheetUsed,usingRange,',
							pageNumber : 1
						});
	}

 function mergeColumsFun(data){
		var arr = data.rows;//json数组
		if(arr.length>0){
			var field = "batchNo";//判断合并的字段（连续并且相同则合并）
			var mergeFields =["ck","batchNo","ticketName","type","faceValue","leastMoney","sheetLimit","useType","beginTime","endTime","ticketStatus","auditor","sheetTotal"];//合并的字段
			gridname='tTicketAuditList';
			mergeColums(arr,field,mergeFields,gridname);//合并列
		}
		//绑定多行选中事件	
		 dealMultiSelect("batchNo");
}
</script>