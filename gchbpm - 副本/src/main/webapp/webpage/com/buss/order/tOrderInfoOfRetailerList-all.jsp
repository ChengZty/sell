<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%-- 
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOrderInfoList" checkbox="false" fitColumns="false" extendParams="nowrap:false," title="订单明细" actionUrl="tOrderInfoController.do?datagrid&order_status=${order_status }" 
  idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  ></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  query="true"  queryMode="single" width="130"></t:dgCol>
   <t:dgCol title="下单时间"  field="orderTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="数量"  field="quantityAmount"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="商品总价"  field="goodsAmount"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="运费"  field="fareAmount"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="订单总价"  field="orderAmount"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="买家手机"  field="userPhone"  query="true"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="买家姓名"  field="userName"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="支付时间"  field="payTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group"  width="130"></t:dgCol>
   <t:dgCol title="支付方式"  field="payMethod"     queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="导购"  field="toGuideId"     queryMode="single"  width="60"  ></t:dgCol>
   <t:dgCol title="零售商"  field="toRetailerId"     queryMode="single"  width="100"   ></t:dgCol>
   <t:dgCol title="订单状态"  field="orderStatus" replace="待给优惠_0,待付款_1,待发货_2,已发货_3,已完成_4,已取消_9,已关闭_9" query="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="tOrderInfoController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出明细" icon="icon-putout" funname="ExportDetailsXls(0)"></t:dgToolBar>
   <t:dgToolBar title="导出主单" icon="icon-putout" funname="ExportInfoXls(0)"></t:dgToolBar>
   <c:if test="${isGCH == 'Y' }">
   <t:dgToolBar title="导出测试明细" icon="icon-putout" funname="ExportDetailsXls(1)"></t:dgToolBar>
   <t:dgToolBar title="导出测试主单" icon="icon-putout" funname="ExportInfoXls(1)"></t:dgToolBar>
   </c:if>
  </t:datagrid>
  </div>
 </div>
  --%>
	
<table width="100%" id="tOrderInfoList" toolbar="#tOrderInfoListtb"></table>
<div id="tOrderInfoListtb" style="padding: 3px; height: auto">
	<div name="searchColums">
		<input id="_sqlbuilder" name="sqlbuilder" type="hidden" />
		<form id='tOrderInfoListForm'>
			<link rel="stylesheet" href="plug-in/Validform/css/style.css"
				type="text/css">
			<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css"
				type="text/css">
			<script type="text/javascript"
				src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
			<script type="text/javascript"
				src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
			<script type="text/javascript"
				src="plug-in/Validform/js/datatype_zh-cn.js"></script>
			<span style="display: -moz-inline-box; display: inline-block;"><span
				style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
				title="订单号">订单号：</span><input onkeypress="EnterPress(event)"
				onkeydown="EnterPress()" type="text" name="orderNo" class="inuptxt"
				style="width: 100px" /></span><span
				style="display: -moz-inline-box; display: inline-block;"><span
				style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
				title="下单时间">下单时间：</span><input type="text" name="orderTime_begin"
				style="width: 94px" class="inuptxt" /><span
				style="display: -moz-inline-box; display: inline-block; width: 8px; text-align: right;">~</span><input
				type="text" name="orderTime_end" style="width: 94px" class="inuptxt" /></span><span
				style="display: -moz-inline-box; display: inline-block;"><span
				style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
				title="买家手机">买家手机：</span><input onkeypress="EnterPress(event)"
				onkeydown="EnterPress()" type="text" name="userPhone"
				class="inuptxt" style="width: 100px" /></span><span
				style="display: -moz-inline-box; display: inline-block;"><span
				style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
				title="支付时间">支付时间：</span><input type="text" name="payTime_begin"
				style="width: 94px" class="inuptxt" /><span
				style="display: -moz-inline-box; display: inline-block; width: 8px; text-align: right;">~</span><input
				type="text" name="payTime_end" style="width: 94px" class="inuptxt" /></span><span
				style="display: -moz-inline-box; display: inline-block;"><span
				style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
				title="订单状态">订单状态：</span><select name="orderStatus" WIDTH="100"
				style="width: 104px">
					<option value="">---所有---</option>
					<option value="0">待给优惠</option>
					<option value="1">待付款</option>
					<option value="2">待发货</option>
					<option value="3">已发货</option>
					<option value="4">已完成</option>
					<option value="8">已退款</option>
					<option value="9">已取消</option>
			</select></span>
		</form>
	</div>
	<div style="height: 30px;" class="datagrid-toolbar">
		<span style="float: left;">
		<a href="#"
			class="easyui-linkbutton" plain="true" icon="icon-search"
			onclick="detail('查看','tOrderInfoController.do?goUpdate','tOrderInfoList',null,null)">查看</a><a
			href="#" class="easyui-linkbutton" plain="true" icon="icon-putout"
			onclick="ExportDetailsXls(0)('导出明细','null','tOrderInfoList',null,null)">导出明细</a><a
			href="#" class="easyui-linkbutton" plain="true" icon="icon-putout"
			onclick="ExportInfoXls(0)('导出主单','null','tOrderInfoList',null,null)">导出主单</a><a
			href="#" class="easyui-linkbutton" plain="true" icon="icon-putout"
			onclick="ExportDetailsXls(1)('导出测试明细','null','tOrderInfoList',null,null)">导出测试明细</a><a
			href="#" class="easyui-linkbutton" plain="true" icon="icon-putout"
			onclick="ExportInfoXls(1)('导出测试主单','null','tOrderInfoList',null,null)">导出测试主单</a>
		</span>
		<span style="float: right"><a href="#"
			class="easyui-linkbutton" iconCls="icon-search"
			onclick="tOrderInfoListsearch()">查询</a><a href="#"
			class="easyui-linkbutton" iconCls="icon-reload"
			onclick="searchReset('tOrderInfoList')">重置</a>
		</span>
	</div>
	<script type="text/javascript" src="webpage/com/buss/order/tOrderInfo.js?v=1.00"></script>
	<script type="text/javascript">
/* 
 $(function(){
		$('#tOrderInfoList').datagrid({
			title:'DataGrid - DetailView',
			width:1200,
			height:700,
			remoteSort:false,
			singleSelect:true,
			nowrap:false,
//			fit: true,
//			fitColumns:true,
			rownumbers:true,
			toolbar:'#tb',
			autoRowHeight:false,
			pagination:true,
// 			url:'webpage/com/buss/words/datagrid_data.json',
			url:'tOrderInfoController.do?getGridJsonData&field=id,orderNo,orderTime',
			columns:[[
				{field:'id',title:'Item ID',width:80,hidden:true},
				{field:'orderNo',title:'orderNo',width:100,sortable:true},
				{field:'orderTime',title:'orderTime',width:100,sortable:true}
// 				{field:'itemid',title:'Item ID',width:80,hidden:true},
// 				{field:'productid',title:'Product ID',width:100,sortable:true},
// 				{field:'listprice',title:'List Price',width:80,align:'right',sortable:true},
// 				{field:'unitcost',title:'Unit Cost',width:80,align:'right',sortable:true},
// 				{field:'attr1',title:'Attribute',width:150,sortable:true},
// 				{field:'status',title:'Status',width:60,align:'center'}
			]],
			view: detailview,
			 detailFormatter:function(rowIndex, rowData){    
			        return '<div id="ddv-' + rowIndex + '" style="padding:5px 0;min-height:20px;"></div>';    
			 },
			 onClickRow: function(index,row){ 
				 $('#tOrderInfoList').datagrid('fixDetailRowHeight',index); 
			 },
		    onBeforeLoad:function(){},  
	        onLoadSuccess:function(){
//		    	$("div.datagrid-view1 td[field='_expander'] span.datagrid-row-expand").on("click",function(){
//		    		alert();
//		    	})
		    },
		    onExpandRow: function(index,row){
		    	var html = "<div style='height:200px'></div>";
		    	$('#ddv-'+index).html(html);
		    	
		    	$('#ddv-'+index).panel({    
		            border:false,    
		            cache:false,    
		            href:'tChatWordsController.do?goAdd&itemid='+row.itemid,    
		            onload:function(){    
//		                $('#tOrderInfoList').datagrid('fixdetailrowheight',index);  
						setTimeout($('#tOrderInfoList').datagrid('fixDetailRowHeight',index),1500);
//		                $('#tOrderInfoList').datagrid('fixDetailRowHeight',index);//在加载爷爷列表明细（即：父列表）成功时，获取此时整个列表的高度，使其适应变化后的高度，此时的索引  
                     $('#tOrderInfoList').datagrid('fixRowHeight',index);//防止出现滑动条  
		            }    
		        }); 
		    	setTimeout($('#tOrderInfoList').datagrid('fixDetailRowHeight',index),1500);
	        }  
		});
		
	});
 
		function tOrderDetailListsearch() {
			var queryParams = $('#tOrderInfoList').datagrid('options').queryParams;
			$('#tOrderInfoList').find('*').each(function() {
				queryParams[$(this).attr('name')] = $(this).val();
			});
			$('#tOrderInfoList')
					.datagrid(
							{
								url : 'tOrderInfoController.do?getGridJsonData&field=id,orderNo,orderTime',
								pageNumber : 1
							});
		}

 
 
  */
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 function goCancelOrder(id){
	 var url = "tOrderInfoController.do?goCancelOrder&id="+id;
	 createwindow('取消订单',url,360,150);
 }
 
//导出明细
 function ExportDetailsXls(flag) {
 	JeecgExcelExport("tOrderDetailController.do?exportDetailsXls&order_status=${order_status }&isTest="+flag,"tOrderInfoList");
 }
//导出主单
 function ExportInfoXls(flag) {
 	JeecgExcelExport("tOrderInfoController.do?exportInfoXls&order_status=${order_status }&isTest="+flag,"tOrderInfoList");
 }
 

 </script>