<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tActivityGoodsList" checkbox="true" fitColumns="false" title="话题分类商品" actionUrl="tActivityGoodsController.do?datagrid2&activity_Id=${news_type_Id }&content_Id=${content_Id }&rId=${rId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动ID"  field="activityId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"   queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
   <t:dgDelOpt title="删除" url="tActivityGoodsController.do?doDel&id={id}" />
   <t:dgToolBar title="添加话题分类商品" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tActivityGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tActivityGoodsController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
 <script type="text/javascript">
//批量分配
 function batchAdd(){
	 gridname="tActivityGoodsList";
		var activityId = "${news_type_Id }";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tGoodsController.do?goodsList&activityId="+activityId,
					lock : true,
					title:"选择话题分类商品",
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请话题分类商品");
					    		return false;
						 }else{
								var goodsIds = "";
								$(sel_tr).each(function(){
									var goodsId = $(this).find("td[field='id'] div").text();
									goodsIds+=goodsId+",";
								})
								 var url = "tActivityGoodsController.do?doBatchAddByNewsType&content_Id=${content_Id }";
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
			}else{
				$.dialog({
					content: "url:"+url,
					lock : true,
					title:"选择话题分类商品",
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请话题分类商品");
						    		return false;
							 }else{
									var goodsIds = "";
									$(sel_tr).each(function(){
										var goodsId = $(this).find("td[field='id'] div").text();
										goodsIds+=goodsId+",";
									})
									 var url = "tActivityGoodsController.do?doBatchAddByNewsType&content_Id=${content_Id }";
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