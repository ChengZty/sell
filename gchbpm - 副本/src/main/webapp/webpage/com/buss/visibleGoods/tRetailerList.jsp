<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tRetailerRelationList" checkbox="true" fitColumns="false" title="零售商关系表" actionUrl="tRetailerRelationController.do?datagrid&retailerId=${retailerId }&otherRetailerType=${otherRetailerType }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="其他零售商名字"  field="otherRetailerId"    dictionary="t_s_user,id,realname,user_type='02' and retailer_type='1' and status = 'A' "  width="120"></t:dgCol>
<%--    <t:dgCol title="其他零售商名字"  field="otherRetailerName"    queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="其他零售商类型"  field="otherRetailerType"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgFunOpt funname="getRetailerBrandCategoryList(otherRetailerId)" title="品牌分类设置" ></t:dgFunOpt>
   <t:dgDelOpt title="删除" url="tRetailerRelationController.do?doDel&id={id}" />
   <t:dgToolBar title="添加零售商" icon="icon-add" url="" funname="batchAdd" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tRetailerRelationController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tRetailerRelationController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
 <script type="text/javascript">
 $(document).ready(function(){
		//给时间控件加上样式
});
 
 function getRetailerBrandCategoryList(otherRetailerId){
	 var title = "品牌分类设置";
	 var url = "tRetailerBrandCategoryController.do?list&retailerId=${retailerId }&otherRetailerId="+otherRetailerId;
	 var width = 800;
	 var height = 600;
	 if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				lock : true,
				zIndex:500,
				width:width,
				height:height,
				title:title,
				opacity : 0.3,
				cache:false,
// 			    ok: function(){
// 			    	iframe = this.iframe.contentWindow;
// 					saveObj();
// 					return false;
// 			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			W.$.dialog({
				content: 'url:'+url,
				lock : true,
				width:width,
				zIndex:500,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
// 			    ok: function(){
// 			    	iframe = this.iframe.contentWindow;
// 					saveObj();
// 					return false;
// 			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
 } 
 
//批量添加零售商
 function batchAdd(){
	 gridname="tRetailerRelationList";
 		 $.dialog.setting.zIndex = 300;
 		 if(typeof(windowapi) == 'undefined'){
 			 $.dialog({
 					content: "url:userController.do?retailerListForRetailer&isOther=1&rId=${retailerId}",
 					lock : true,
 					title:"选择零售商",
 					width:500,
 					height: 500,
 					cache:false,
 				    ok: function(){
 				    	iframe_doc = this.iframe.contentWindow.document;
 					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
 				    	if ($(sel_tr).length ==0 ){
 				    			alert("请选择零售商");
 					    		return false;
 						 }else{
							var otherRetailerIds = "";
							$(sel_tr).each(function(){
								var retailerId = $(this).find("td[field='id'] div").text();
								otherRetailerIds+=retailerId+",";
							})
							 var url = "tRetailerRelationController.do?doBatchAdd";
								$.ajax({
									url : url,
									type : 'post',
									data : {
										retailerId : "${retailerId }",
										otherRetailerType : "1",
										otherRetailerIds : otherRetailerIds
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
 					title:"选择零售商",
 					width:500,
 					height: 500,
 					parent:windowapi,
 					cache:false,
 				     ok: function(){
 				    	iframe_doc = this.iframe.contentWindow.document;
 					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
 				    	if ($(sel_tr).length ==0 ){
 				    			alert("请选择零售商");
 					    		return false;
 						 }else{
							var otherRetailerIds = "";
							$(sel_tr).each(function(){
								var retailerId = $(this).find("td[field='id'] div").text();
								otherRetailerIds+=retailerId+",";
							})
							 var url = "tRetailerRelationController.do?doBatchAdd";
								$.ajax({
									url : url,
									type : 'post',
									data : {
										retailerId : "${retailerId }",
										otherRetailerType : "1",
										otherRetailerIds : otherRetailerIds
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