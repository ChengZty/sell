<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tNewsGoodsList" checkbox="false" fitColumns="false" title="话题商品" actionUrl="tGoodsActController.do?newsDatagrid&act_id=${act_id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题封面图片"  field="coverPic"  image="true"  queryMode="single" imageSize="80,80" width="80"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布时间"  field="createDate"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tGoodsActController.do?deleteNews&id={id}&actId=${act_id }" />
   <c:if test="${user_Type!='01' }">
   <t:dgToolBar title="添加活动话题" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
   </c:if>
  </t:datagrid>
 <script type="text/javascript">
//批量添加
 function batchAdd(){
	 gridname="tGoodsRelateTopic";
		var actId = "${act_id }";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:tGoodsActController.do?actNewsList&actId="+actId,
					lock : true,
					title:"选择话题",
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择活动话题");
					    		return false;
						 }else{
								var newsIds = "";
								$(sel_tr).each(function(){
									var newsId = $(this).find("td[field='id'] div").text();
									newsIds+=newsId+",";
								})
								 var url = "tGoodsActController.do?doBatchAdd";
									$.ajax({
										url : url,
										type : 'post',
										data : {
											actId : actId,
											newsIds : newsIds
										},
										cache : false,
										success : function(data) {
											var d = $.parseJSON(data);
											
											if (d.success) {
												var msg = d.msg;
												tip(msg);
												//reloadTable();
												$("#tNewsGoodsList").datagrid('reload');
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
					title:"选择活动话题",
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
						    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    	if ($(sel_tr).length ==0 ){
					    			alert("请选择活动话题");
						    		return false;
							 }else{
									var newsIds = "";
									$(sel_tr).each(function(){
										var newsId = $(this).find("td[field='id'] div").text();
										newsIds+=newsId+",";
									})
									 var url = "tGoodsActController.do?doBatchAdd";
										$.ajax({
											url : url,
											type : 'post',
											data : {
												actId : actId,
												newsId : newsIds
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