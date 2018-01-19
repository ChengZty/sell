<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tVisibleWordsList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="商品话术" 
  actionUrl="tVisibleWordsController.do?datagridOfGoods" idField="id" fit="true" queryMode="group">
   <t:dgCol title="话术ID"  field="id" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="ID"  field="wordsId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="一级分类"  field="topCategoryName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="二级分类"  field="subCategoryName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="三级分类"  field="thridCategoryName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内容"  field="content"  query="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="是否可见"  field="visible"  query="true" replace="是_1,否_0"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="doShow(id)" title="设置可见" exp="visible#eq#0"></t:dgFunOpt>
   <t:dgFunOpt funname="doHide(wordsId)" title="设置不可见" exp="visible#eq#1"></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search"  funname="reviewbytab"></t:dgToolBar>
   <t:dgToolBar title="批量设置可见" icon="icon-add" url="tVisibleWordsController.do?doBatchAdd&wordsType=2" funname="doShowAllSelect"></t:dgToolBar>
   <t:dgToolBar title="批量设置不可见" icon="icon-remove" url="tVisibleWordsController.do?doBatchDel"  funname="doHideAllSelect"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 <script src = "webpage/com/buss/words/tVisibleWordsList.js"></script>
 <script type="text/javascript">
 function reviewbytab() {
		var rowsData = $("#tVisibleWordsList").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].id;
		createdetailwindow("商品话术","tGoodsWordsController.do?goUpdate&load=detail&id="+id,700,400);
	}
 
//新增可见话术(wordsType 1:顾客，2：商品，3：活动)
 function doShow(wordsId){
	 gridname = "tVisibleWordsList";
	 var url = "tVisibleWordsController.do?doAdd";
	 $.ajax({
			type : 'POST',
			url : url,// 请求的action路径
			data : {
				wordsType : 2,
				wordsId : wordsId
			},
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloadTable();
				}
			}
		});
 }
 </script>