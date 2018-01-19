<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tNewsGoodsList" checkbox="true" fitColumns="false" title="活动推广话术" actionUrl="tGoodsActController.do?actWordsGrid&actId=${actId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内容"  field="content"  query="true"  queryMode="single"  width="350"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="70"></t:dgCol>
   <t:dgDelOpt title="删除" url="tGoodsActController.do?doBatchActWordsDel&id={id}" />
   <t:dgToolBar title="添加商品活动话术" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tNewsGoodsController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
 <script type="text/javascript">
//批量添加
 function batchAdd(){
	 gridname="tNewsGoodsList";
		var actId = "${actId }";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tGoodsActController.do?tSingleNewGoodsListOfWords&&actId=${actId }",
					lock : true,
					title:"选择活动话术",
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择活动话术");
					    		return false;
						 }else{
								var goodsIds = "";
								$(sel_tr).each(function(){
									var goodsId = $(this).find("td[field='id'] div").text();
									goodsIds+=goodsId+",";
								})
								 var url = "tGoodsActController.do?doBatchActAdd";
									$.ajax({
										url : url,
										type : 'post',
										data : {
											actId : actId,
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
					title:"选择活动话术",
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请选择活动话术");
						    		return false;
							 }else{
									var goodsIds = "";
									$(sel_tr).each(function(){
										var goodsId = $(this).find("td[field='id'] div").text();
										goodsIds+=goodsId+",";
									})
									 var url = "tGoodsActController.do?doBatchActAdd";
										$.ajax({
											url : url,
											type : 'post',
											data : {
												actId : actId,
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