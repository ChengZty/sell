<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<!-- <div class="easyui-layout" fit="true"> -->
<!--   <div region="center" style="padding:1px;"> -->
  <t:datagrid name="tActivityGoodsList" checkbox="true" fitColumns="false" title="商品列表" actionUrl="tActivityGoodsController.do?datagrid3&content_Id=${content_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动ID"  field="activityId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"   queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="排序"  field="orderNum"  queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgFunOpt funname="goUpdateOrderNum(id)"  title="修改排序"></t:dgFunOpt>
   <t:dgDelOpt title="删除" url="tActivityGoodsController.do?doDel&id={id}" />
   <t:dgToolBar title="添加商品" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tActivityGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
<!--   </div> -->
<!-- </div> -->
 <script type="text/javascript">
 //修改排序
 function goUpdateOrderNum(id){
	 var url = 'tActivityGoodsController.do?goUpdateOrderNum&id='+id;
		var title = "修改排序";
		var width = 360;
		var height = 100;
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				lock : true,
				zIndex: 300,
				width:width,
				height:height,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
		}else{
			W.$.dialog({
				content: 'url:'+url,
				lock : true,
				width:width,
				zIndex:300,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
		}
 }
 
//批量添加商品
 function batchAdd(){
	 gridname="tActivityGoodsList";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tGoodsController.do?hotGoodsList&content_Id=${content_Id }",
					lock : true,
					title:"选择商品",
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择商品");
					    		return false;
						 }else{
								var goodsIds = "";
								$(sel_tr).each(function(){
									var goodsId = $(this).find("td[field='id'] div").text();
									goodsIds+=goodsId+",";
								})
								 var url = "tActivityGoodsController.do?doBatchAddByHotGoods&content_Id=${content_Id }";
									$.ajax({
										url : url,
										type : 'post',
										data : {
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
					title:"选择商品",
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请选择商品");
						    		return false;
							 }else{
									var goodsIds = "";
									$(sel_tr).each(function(){
										var goodsId = $(this).find("td[field='id'] div").text();
										goodsIds+=goodsId+",";
									})
									 var url = "tActivityGoodsController.do?doBatchAddByHotGoods&content_Id=${content_Id }";
										$.ajax({
											url : url,
											type : 'post',
											data : {
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