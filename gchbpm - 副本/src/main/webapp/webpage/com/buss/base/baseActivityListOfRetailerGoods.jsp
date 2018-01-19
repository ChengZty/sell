<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="baseActivityList" checkbox="false" fitColumns="false" title="活动" actionUrl="baseActivityController.do?datagrid&content_Id=${content_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="图片"  field="picUrl"  image="true" imageSize="100,80" queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="活动名称"  field="activityName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt title="查看活动商品"  funname="goActivityGoodsList(id,activityName)"  />
   <t:dgToolBar title="添加活动商品" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<div region="east" style="width: 900px;" split="true">
<div tools="#tt" class="easyui-panel" title='查看活动商品' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 function goActivityGoodsList(activityId,activityName) {
		$("#function-panel").panel(
			{
				title : '活动名称:' + activityName,
				href:"tActivityGoodsController.do?list&content_Id=${content_Id }&activity_Id=" + activityId
			}
		);
// 		$('#function-panel').panel("refresh" );
	}
 
 
//批量分配
 function batchAdd(){
	 gridname="baseActivityList";
	 var rows = $('#baseActivityList').datagrid('getSelections');
		if (!rows || rows.length!=1) {
			tip('请选择一条记录');
			return;
		}
		var activityId = rows[0].id;
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tGoodsController.do?goodsList&retailer_Type=1&activityId="+activityId,
					lock : true,
					title:"选择活动商品",
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择活动商品");
					    		return false;
						 }else{
							 	var activityId = $(iframe_doc).find("input[name='activityId']").val();
								var goodsIds = "";
								$(sel_tr).each(function(){
									var goodsId = $(this).find("td[field='id'] div").text();
									goodsIds+=goodsId+",";
								})
								 var url = "tActivityGoodsController.do?doBatchAdd&content_Id=${content_Id }";
									$.ajax({
										url : url,
										type : 'post',
										data : {
											activityId : activityId,
											goodsIds : goodsIds
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
					title:"选择活动商品",
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请选择活动商品");
						    		return false;
							 }else{
								 	var activityId = $(iframe_doc).find("input[name='activityId']").val();
									var goodsIds = "";
									$(sel_tr).each(function(){
										var goodsId = $(this).find("td[field='id'] div").text();
										goodsIds+=goodsId+",";
									})
									 var url = "tActivityGoodsController.do?doBatchAdd&content_Id=${content_Id }";
										$.ajax({
											url : url,
											type : 'post',
											data : {
												activityId : activityId,
												goodsIds : goodsIds
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