<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tNewsGoodsList" checkbox="true" fitColumns="false" title="话题商品" actionUrl="tNewsGoodsController.do?datagrid&news_Id=${news_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="smallPic"  image="true"  queryMode="single" imageSize="80,80" width="80"></t:dgCol>
   <t:dgCol title="名称"  field="goodsName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="款号"  field="goodsCode"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice" queryMode="single"  width="50"></t:dgCol>
<%--    <t:dgCol title="现价"  field="currentPrice" queryMode="single"  width="50"></t:dgCol> --%>
<%--    <t:dgCol title="活动价"  field="activityPrice" queryMode="single"  width="50"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tNewsGoodsController.do?doDel&id={id}" />
   <c:if test="${user_Type!='01' }">
   <t:dgToolBar title="添加话题商品" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
   </c:if>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tNewsGoodsController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
 <script type="text/javascript">
//批量添加
 function batchAdd(){
	 gridname="tNewsGoodsList";
		var newsId = "${news_Id }";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tNewGoodsController.do?tSingleNewGoodsListOfNews&newsId="+newsId,
					lock : true,
					title:"选择话题商品",
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择话题商品");
					    		return false;
						 }else{
								var goodsIds = "";
								$(sel_tr).each(function(){
									var goodsId = $(this).find("td[field='id'] div").text();
									goodsIds+=goodsId+",";
								})
								 var url = "tNewsGoodsController.do?doBatchAdd";
									$.ajax({
										url : url,
										type : 'post',
										data : {
											newsId : newsId,
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
					title:"选择话题商品",
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请选择话题商品");
						    		return false;
							 }else{
									var goodsIds = "";
									$(sel_tr).each(function(){
										var goodsId = $(this).find("td[field='id'] div").text();
										goodsIds+=goodsId+",";
									})
									 var url = "tNewsGoodsController.do?doBatchAdd";
										$.ajax({
											url : url,
											type : 'post',
											data : {
												newsId : newsId,
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