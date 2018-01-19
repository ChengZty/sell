<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tFinActivityGoodsList" checkbox="true" fitColumns="true" title="活动奖励商品" actionUrl="tFinActivityGoodsController.do?datagrid&fin_Act_Id=${finActId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="活动奖励ID"  field="finActId"    queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="商品ID"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="pic" image="true" imageSize="80,80" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice"   queryMode="single"  width="80"></t:dgCol>
   <c:if test="${activityStatus =='1' && isSame == '1'}">
   <t:dgCol title="操作" field="opt" width="60"></t:dgCol>
   <t:dgDelOpt title="删除" url="tFinActivityGoodsController.do?doDel&id={id}" />
   <t:dgToolBar title="添加活动商品" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tFinActivityGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   </c:if>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tFinActivityGoodsController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
 <script type="text/javascript">
//批量添加活动商品
 function batchAdd(){
	 gridname="tFinActivityGoodsList";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tNewGoodsController.do?finActGoodsList&finActId=${finActId }&pType=${platformType}&rId=${retailerId}",
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
								var goodsIds = "";
								$(sel_tr).each(function(){
									var goodsId = $(this).find("td[field='id'] div").text();
									goodsIds+=goodsId+",";
								})
								 var url = "tFinActivityGoodsController.do?doBatchAdd&finActId=${finActId }";
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
									var goodsIds = "";
									$(sel_tr).each(function(){
										var goodsId = $(this).find("td[field='id'] div").text();
										goodsIds+=goodsId+",";
									})
									 var url = "tFinActivityGoodsController.do?doBatchAdd&finActId=${finActId }";
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