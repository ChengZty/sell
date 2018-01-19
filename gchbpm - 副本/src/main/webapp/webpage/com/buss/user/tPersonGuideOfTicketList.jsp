<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPersonTicketList" checkbox="true" fitColumns="true" sortName="createDate" sortOrder="desc" title="导购列表" actionUrl="tPersonController.do?datagrid&user_Type=03&normal=Y" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="帐号Id"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="realName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="昵称"  field="name"   query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="性别"  field="sex"    queryMode="single" dictionary="sex" width="50"></t:dgCol> --%>
   <t:dgCol title="手机号码"  field="phoneNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="180"></t:dgCol>
<%--    <t:dgFunOpt funname="goView(id)" title="查看"></t:dgFunOpt> --%>
   <t:dgFunOpt title="查看优惠券"  funname="goTicketList(userId,realName)"  />
   <t:dgToolBar title="批量分配" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
  <div region="east" style="width: 700px;" split="true">
<div tools="#tt" class="easyui-panel" title='查看优惠券' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
 <script type = "text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tPersonListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tPersonListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tPersonListtb").find("input[name='birthday']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//  function goView(id){
// 	 var url = "tPersonController.do?goViewGuide&id="+id;
// 	 window.open(url,'new');
// }
 
 function goTicketList(userId,realName) {
		$("#function-panel").panel(
			{
				title : '姓名:' + realName,
				href:"tTicketSendController.do?list&user_Id=" + userId
			}
		);
// 		$('#function-panel').panel("refresh" );
	}
 
 //批量分配
 function batchAdd(){
	 gridname="tPersonTicketList";
	 var rows = $('#tPersonTicketList').datagrid('getSelections');
		if (!rows || rows.length==0) {
			tip('请选择一条记录');
			return;
		}
		var user_Ids = [];
		for ( var i = 0; i < rows.length; i++) {
			user_Ids.push(rows[i].userId);
		}

		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tTicketInfoController.do?listToBeSend&userIds="+user_Ids,
					lock : true,
					title:"选择优惠券",
					width:900,
					height: 550,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择优惠券");
					    		return false;
						 }else{
							 //批量分配优惠券
							 var userIds = $(iframe_doc).find("input[name='userIds']").val();
							 var sheetNum = $(iframe_doc).find("input[name='sheetNum']").val();//人均张数
							 if(checkInput(sheetNum)){
								 var flag=true;
								 var userNum = userIds.split(",").length;//导购个数
								 var ticketIds = "";
								$(sel_tr).each(function(){
									var sheetRemain = $(this).find("td[field='sheetRemain'] div").text();
									var ticketId = $(this).find("td[field='id'] div").text();
									var retailerType = $(this).find("td[field='retailerType'] div").text();
									if(sheetNum*userNum>sheetRemain){
										var batchNo = $(this).find("td[field='batchNo'] div").text();
										alert("批次号"+batchNo+"为的优惠券分配张数超过剩余张数"+sheetRemain); 
										flag = false;
										return false; 
									}
									ticketIds+=ticketId+"_"+retailerType+",";
								})
								if(flag){
									 var url = "tTicketSendController.do?doBatchGive";
										$.ajax({
											url : url,
											type : 'post',
											data : {
												userIds : userIds,
												ticketIds : ticketIds,
												sheetNum : sheetNum
											},
											cache : false,
											success : function(data) {
												var d = $.parseJSON(data);
												if (d.success) {
													var msg = d.msg;
													tip(msg);
													reloadTable();
													$("#"+gridname).datagrid('unselectAll');
												}
											}
										});
								}
							 }
						 }
				    },
				    cancelVal: '关闭',
				    cancel: true /*为true等价于function(){}*/
				}).zindex();
			} else{
	  			$.dialog({
					content: "url:"+url,
					lock : true,
					title:"选择优惠券",
					width:900,
					height: 550,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请选择优惠券");
						    		return false;
							 }else{
								 //批量分配优惠券
								 var userIds = $(iframe_doc).find("input[name='userIds']").val();
								 var sheetNum = $(iframe_doc).find("input[name='sheetNum']").val();//人均张数
								 if(checkInput(sheetNum)){
									 var flag=true;
									 var userNum = userIds.split(",").length;//导购个数
									 var ticketIds = "";
									$(sel_tr).each(function(){
										var sheetRemain = $(this).find("td[field='sheetRemain'] div").text();
										var ticketId = $(this).find("td[field='id'] div").text();
										if(sheetNum*userNum>sheetRemain){
											var batchNo = $(this).find("td[field='batchNo'] div").text();
											alert("批次号"+batchNo+"为的优惠券分配张数超过剩余张数"+sheetRemain); 
											flag = false;
											return false; 
										}
										ticketIds+=ticketId+",";
									})
									if(flag){
										 var url = "tTicketSendController.do?doBatchGive";
											$.ajax({
												url : url,
												type : 'post',
												data : {
													userIds : userIds,
													ticketIds : ticketIds,
													sheetNum : sheetNum
												},
												cache : false,
												success : function(data) {
													var d = $.parseJSON(data);
													if (d.success) {
														var msg = d.msg;
														tip(msg);
														reloadTable();
														$("#"+gridname).datagrid('unselectAll');
													}
												}
											});
									}
								 }
							 }
				    },
				    cancelVal: '关闭',
				    cancel: true 
				}).zindex();
	  		}
 }
 
//判断正整数 
 function checkInput(input){ 
 	var re = /^[1-9]+[0-9]*]*$/;
 	if (!re.test(input)){
		 alert("张数必须为整数"); 
		 return false; 
	 }else{
		 return true;
	 }
 }
 </script>
  </script>
 <style type="text/css">
  div.datagrid-cell-rownumber,div.datagrid-header-rownumber{ 
  	min-width: 55px; 
  } 
 </style>