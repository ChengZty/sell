<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="baseActivityList" checkbox="true" fitColumns="true" title="栏目活动" extendParams="nowrap:false," actionUrl="baseActivityController.do?datagrid&content_Id=${content_Id }" 
  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="图片"  field="picUrl"  image="true" imageSize="100,80" queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="活动名称"  field="activityName"  query="true"  queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="子标题"  field="subTitle"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始时间"  field="startTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="零售商"  field="retailerId" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="280"></t:dgCol>
   <t:dgDelOpt title="删除" url="baseActivityController.do?doDel&id={id}" />
   <t:dgFunOpt funname="getRetailerGoodsList(id,activityName,retailerId)" title="活动商品"></t:dgFunOpt>
<%--    <t:dgFunOpt funname="getColudGoodsList(id,activityName,retailerId)" title="云商活动商品"></t:dgFunOpt> --%>
   <t:dgToolBar title="选择活动" icon="icon-add" funname="batchAddActivity"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search"  funname="goView"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div region="east" style="width: 700px;" split="true">
	<div tools="#tt" class="easyui-panel" title='查看活动商品' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<script src = "webpage/com/buss/base/baseActivityList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
// 	 $("#baseActivityListtb").find("input[name='startTime']").attr("class","Wdate").attr("style","height:24px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
// 	 $("#baseActivityListtb").find("input[name='endTime']").attr("class","Wdate").attr("style","height:24px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
 });
 function getRetailerGoodsList(id,activityName,retailerId,row) {
		$("#function-panel").panel(
			{
				title : '活动名称:' + activityName,
				href:"tActivityGoodsController.do?list&activity_Id="+id+"&rId="+retailerId+"&c_Id=${content_Id }"
			}
		);
	}

function getColudGoodsList(id,activityName,retailerId,row) {
		$("#function-panel").panel(
			{
				title : '活动名称:' + activityName,
				href:"tActivityGoodsController.do?listOfCloud&activity_Id="+id+"&rId="+retailerId+"&c_Id=${content_Id }"
			}
		);
	}
	
function batchAddActivity(){
	 gridname="baseActivityList";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:baseActivityController.do?listOfRetailerTobeAdd&rId=${rId}&cId=${content_Id }",
					lock : true,
					title:"选择活动",
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择活动");
					    		return false;
						 }else{
								var activityIds = "";
								$(sel_tr).each(function(){
									var activityId = $(this).find("td[field='id'] div").text();
									activityIds+=activityId+",";
								})
								 var url = "baseActivityController.do?doBatchAdd";
									$.ajax({
										url : url,
										type : 'post',
										data : {
											contentId : ${content_Id },
											activityIds : activityIds
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
				    },
				    cancelVal: '关闭',
				    cancel: true /*为true等价于function(){}*/
				}).zindex();
			} else{
				$.dialog({
					content: "url:"+url,
					lock : true,
					title:"选择活动",
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请选择活动");
						    		return false;
							 }else{
								 var activityIds = "";
									$(sel_tr).each(function(){
										var activityId = $(this).find("td[field='id'] div").text();
										activityIds+=activityId+",";
									})
									alert(activityIds);
									 var url = "baseActivityController.do?doBatchAdd";
										$.ajax({
											url : url,
											type : 'post',
											data : {
												contentId : ${content_Id },
												activityIds : activityIds
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
				    },
				    cancelVal: '关闭',
				    cancel: true 
				}).zindex();
			}
}
 </script>