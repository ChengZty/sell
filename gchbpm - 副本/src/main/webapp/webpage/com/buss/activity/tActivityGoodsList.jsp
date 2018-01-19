<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tActivityGoodsList" checkbox="true" fitColumns="false" title="活动商品列表" actionUrl="tActivityGoodsController.do?datagrid&type=1&activity_Id=${activity_Id}&content_Id=${content_Id}&rId=${rId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品ID"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动ID"  field="activityId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"   queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="活动价"  field="activityPrice"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="130"></t:dgCol>
   <t:dgDelOpt title="删除" url="tActivityGoodsController.do?doDel&id={id}" />
   <t:dgFunOpt funname="goUpdateActivityPrice(goodsId)" title="修改活动价"></t:dgFunOpt>
   <c:if test="${not empty content_Id }">
   <t:dgToolBar title="添加活动商品" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tActivityGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
 <script type="text/javascript">
//批量添加活动商品
 function batchAdd(){
	 gridname="tActivityGoodsList";
		var activityId = "${activity_Id }";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tGoodsController.do?goodsList&retailer_Type=1&activityId="+activityId,
// 					content: "url:tGoodsController.do?goodsList&retailer_Type=1&contentId=${content_Id }&activityId="+activityId,
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
 
 //修改活动价
function goUpdateActivityPrice(id) {
	 var url = "tNewGoodsController.do?goUpdateActivityPrice&id="+id;
	 gridname="tActivityGoodsList";
	createwindow("修改活动价", url,400,180);
}
 </script>