<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tTicketGoodsList" checkbox="true"   title="${title}"  fitColumns="true"
  actionUrl="tTicketGoodsController.do?${type=='1'?'datagrid':'datagridBrand'	 }&ticket_id=${ticket_Id }" idField="id"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
  <c:if test="${type =='1' }">
   <t:dgCol title="商品ID"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="pic" image="true" imageSize="60,60" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"   queryMode="single"  width="120"></t:dgCol>
  </c:if>
  <c:if test="${type =='2' }">
  	<t:dgCol title="品牌ID"  field="goodsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="pic" image="true" imageSize="60,60" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="品牌名称"  field="goodsName"  queryMode="single"  width="120"></t:dgCol>
  </c:if>
   <c:if test="${tktStatus =='1' && isView!='1'}">
   <c:if test="${type =='1' }">
   	<t:dgToolBar title="添加商品" icon="icon-add" url="tNewGoodsController.do?ticketGoodsList&ticketId=${ticket_Id }&rId=${rId}&type=${type}" funname="batchAdd" ></t:dgToolBar>
   </c:if>
   <c:if test="${type =='2' }">
   	<t:dgToolBar title="添加品牌" icon="icon-add" url="baseBrandController.do?ticketBrandList&ticketId=${ticket_Id }&rId=${rId}&type=${type}" funname="batchAdd" ></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tTicketGoodsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   </c:if>
  </t:datagrid>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//批量添加商品
 function batchAdd(title,url,gname){
	var typeName = "${type=='1'?'商品':'品牌'}";
		 $.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:"+url,
					lock : true,
					title:"选择"+typeName,
					width:900,
					height: 600,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
				    	var f = batchAddGoodsBrands(iframe_doc,typeName,gname);
				    	return f;
				    },
				    cancelVal: '关闭',
				    cancel: true /*为true等价于function(){}*/
				}).zindex();
			} else{
				$.dialog({
					content: "url:"+url,
					lock : true,
					title:"选择"+typeName,
					width:900,
					height: 600,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
				    	 var f = batchAddGoodsBrands(iframe_doc,typeName,gname);
					     return f;
				    },
				    cancelVal: '关闭',
				    cancel: true 
				}).zindex();
			}
 }
 
 //添加商品/品牌并更新数量
 function batchAddGoodsBrands(iframe_doc,typeName,gname){
	 var flag = false;
	 gridname=gname;
	 var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
 		if ($(sel_tr).length ==0 ){
 			alert("请选择"+typeName);
	    		return flag;
		 }else{
				var goodsIds = "";
				$(sel_tr).each(function(){
					var goodsId = $(this).find("td[field='id'] div").text();
					goodsIds+=goodsId+",";
				})
				 var url = "tTicketGoodsController.do?doBatchAdd&ticketId=${ticket_Id }&type=${type}";
					$.ajax({
						url : url,
						type : 'post',
						async: false,
						data : {
							goodsIds : goodsIds
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								flag = true;
								tip(msg);
								reloadTable();
								getTotalNum('${ticket_Id }','${type}');//获取总商品/品牌的数量
								$("#"+gridname).datagrid('unselectAll');
							}
						}
					});
		 }
 	return flag;
 }
 
 //删除商品/品牌并更新数量
 function deleteALLSelect(title,url,gname) {
		gridname=gname;
	    var ids = [];
	    var rows = $("#"+gname).datagrid('getSelections');
	    if (rows.length > 0) {
	    	$.dialog.confirm('你确定删除该数据吗?', function(r) {
			   if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : url,
						type : 'post',
						data : {
							ids : ids.join(',')
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								tip(msg);
								reloadTable();
								getTotalNum('${ticket_Id }','${type}');//获取总商品/品牌的数量
								$("#"+gname).datagrid('unselectAll');
								ids='';
							}
						}
					});
				}
			});
		} else {
			tip("请选择需要删除的数据");
		}
	}
 </script>