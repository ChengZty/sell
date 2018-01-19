<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<!-- <div class="easyui-layout" fit="true"> -->
<!--   <div region="center" style="padding:1px;"> -->
  <t:datagrid name="tContentSceneList" checkbox="true" fitColumns="false" title="首页场景图" actionUrl="tContentSceneController.do?datagrid"
   idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内容ID"  field="contentId"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="场景"  field="title"    queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tContentSceneController.do?doDel&id={id}" />
   <t:dgToolBar title="添加场景" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tContentSceneController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
<!--   </div> -->
<!--  </div> -->
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tContentSceneListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tContentSceneListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//批量添加商品
 function batchAdd(){
	 gridname="tContentSceneList";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tSceneInfoController.do?tSceneAppList",
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
								var sceneIds = "";
								$(sel_tr).each(function(){
									var sceneId = $(this).find("td[field='id'] div").text();
									sceneIds+=sceneId+",";
								})
								 var url = "tContentSceneController.do?doBatchAdd&content_Id=${content_Id }";
									$.ajax({
										url : url,
										type : 'post',
										data : {
											sceneIds : sceneIds
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
									var sceneIds = "";
									$(sel_tr).each(function(){
										var sceneId = $(this).find("td[field='id'] div").text();
										sceneIds+=sceneId+",";
									})
									 var url = "tContentSceneController.do?doBatchAdd&content_Id=${content_Id }";
										$.ajax({
											url : url,
											type : 'post',
											data : {
												sceneIds : sceneIds
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