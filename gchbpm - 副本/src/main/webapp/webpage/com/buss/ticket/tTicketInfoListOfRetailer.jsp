<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tTicketInfoList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="优惠券列表" sortName="updateDate" sortOrder="desc"
   actionUrl="tTicketInfoController.do?datagrid" idField="id" fit="true" queryMode="group" onLoadSuccess="mergeColumsFun">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="批次号"  field="batchNo" queryMode="single" width="110"></t:dgCol>
   <t:dgCol title="券名"  field="ticketName" query="true" queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="类型"  field="type" query="true" dictionary="tkt_type" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="店铺"  field="storeId" query="true"  dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="面值/折扣"  field="faceValue"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="使用条件"  field="leastMoney"  formatterjs="showCondition"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="限制使用张数"  field="sheetLimit"  formatterjs="showLimitSheet"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="使用类型"  field="useType" hidden="true"   queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="起始时间"  field="beginTime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="审核状态"  field="ticketStatus" query="true" replace="待审核_1,已审核_2,已作废_3" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="总张数"  field="sheetTotal"    queryMode="single"  width="60"></t:dgCol>
<%--    <t:dgCol title="分配张数"  field="sheetSent"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="剩余张数"  field="sheetRemain"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="使用张数"  field="sheetUsed"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="适用范围"  field="usingRange"   queryMode="single"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="使用说明"  field="remark"    queryMode="single"  width="150"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>
   <t:dgFunOpt funname="goUpdate(id)"  title="编辑" exp="ticketStatus#eq#1" ></t:dgFunOpt>
   <t:dgDelOpt title="删除" url="tTicketInfoController.do?doDel&id={id}" exp="ticketStatus#eq#1"/>
<%--    <t:dgFunOpt funname="getTicketGoodsList(id,ticketStatus,ticketName,retailerId)" title="券商品" exp="usingRange#eq#3"></t:dgFunOpt> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tTicketInfoController.do?goAdd" funname="add" width="800" height="500"></t:dgToolBar> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="tTicketInfoController.do?goAdd2" funname="addTicket" ></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tTicketInfoController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tTicketInfoController.do?doBatchDel" funname="doBatchDel"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tTicketInfoController.do?goUpdate2&load=detail" funname="goViewBySelect" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<!--    <div region="east" style="width: 400px;" split="true"> -->
<!-- 	<div tools="#tt" class="easyui-panel" title='优惠券商品' style="padding: 10px;" fit="true" border="false" id="function-panel"></div> -->
<!-- </div> -->
 <script src = "webpage/com/buss/ticket/tTicketInfoList.js?v=1.14"></script>		
 <script src = "webpage/com/buss/order/mergeColums.js"></script>
 <script type="text/javascript">
 gridname = "tTicketInfoList";
 $(document).ready(function(){
 		//给时间控件加上样式
	 $("#tTicketInfoListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>起始时间：</span><input name='begin_Time' type='text' class='Wdate' style='width:90px'/>");
		$("#tTicketInfoListForm").append("<span style='width: 80px;text-align: right;display: inline-block;'>结束时间：</span><input name='end_Time' type='text' class='Wdate' style='width:90px'/>");
		$("#tTicketInfoListForm").find("input[name='begin_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tTicketInfoListForm").find("input[name='end_Time']").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

//录入
function addTicket(title,url,gname){
	window.location.href=url;
}

//修改优惠券
 function goUpdate(id) {
 	 var url = "tTicketInfoController.do?goUpdate2&id="+id;
 	document.location=url;
 }

 function mergeColumsFun(data){
		var arr = data.rows;//json数组
		if(arr.length>0){
			var field = "batchNo";//判断合并的字段（连续并且相同则合并）
			var mergeFields =["ck","batchNo","ticketName","type","faceValue","leastMoney","sheetLimit","useType","beginTime","endTime","ticketStatus","sheetTotal"];//合并的字段
			gridname='tTicketInfoList';
			mergeColums(arr,field,mergeFields,gridname);//合并列
		}
		//绑定多行选中事件	
		 dealMultiSelect("batchNo");
}	
</script>