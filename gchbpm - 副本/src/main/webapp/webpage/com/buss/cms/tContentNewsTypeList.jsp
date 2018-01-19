<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tContentNewsTypeList" checkbox="true" fitColumns="true" title="首页资讯分类选择" actionUrl="tContentNewsTypeController.do?datagrid&content_Id=${content_Id}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="栏目ID"  field="contentId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="资讯分类"  field="name" query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
   <t:dgDelOpt title="删除" url="tContentNewsTypeController.do?doDel&id={id}" />
   <t:dgFunOpt funname="getNewsTypeGoodsList(id,retailerId,name)" title="话题分类商品"></t:dgFunOpt>
   <t:dgToolBar title="添加话题分类" icon="icon-add" url="" funname="batchAddNewsType" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tContentNewsTypeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
  <div region="east" style="width: 500px;" split="true">
	<div tools="#tt" class="easyui-panel" title='查看资讯分类' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tContentNewsTypeListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tContentNewsTypeListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function getNewsTypeGoodsList(id,retailerId,name) {
		$("#function-panel").panel(
			{
				title : '资讯分类名称:' + name,
				href: "tActivityGoodsController.do?newsTypeGoodslist&content_Id=${content_Id}&news_type_Id=" + id+"&rId="+retailerId
			}
		);
// 		$('#function-panel').panel("refresh" );
	}
 
 
//新增资讯分类
 function batchAddNewsType(){
	 gridname="tContentNewsTypeList";
		var activityId = "${activity_Id }";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tNewsTypeController.do?tNewsTypelist",
					lock : true,
					title:"选择话题分类",
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择话题分类");
					    		return false;
						 }else{
								var newsTypeIds = "";
								$(sel_tr).each(function(){
									var newsTypeId = $(this).find("td[field='id'] div").text();
									newsTypeIds+=newsTypeId+",";
								})
								 var url = "tContentNewsTypeController.do?doBatchAdd&contentId=${content_Id}";
									$.ajax({
										url : url,
										type : 'post',
										data : {
											contentId : "${content_Id}",
											newsTypeIds : newsTypeIds
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
					title:"选择话题分类",
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请选择话题分类");
						    		return false;
							 }else{
									var newsTypeIds = "";
									$(sel_tr).each(function(){
										var newsTypeId = $(this).find("td[field='id'] div").text();
										newsTypeIds+=newsTypeId+",";
									})
									 var url = "tContentNewsTypeController.do?doBatchAdd&contentId=${content_Id}";
										$.ajax({
											url : url,
											type : 'post',
											data : {
												contentId : "${content_Id}",
												newsTypeIds : newsTypeIds
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