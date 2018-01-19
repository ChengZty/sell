<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tRetailerBrandCategoryList" checkbox="true" fitColumns="false" title="零售商品牌分类选择" 
  actionUrl="tRetailerBrandCategoryController.do?datagrid&otherRetailerId=${otherRetailerId }&retailerId=${retailerId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="其他零售商ID"  field="otherRetailerId"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="品牌ID"  field="brandId" dictionary="base_brand,id,brand_name, status = 'A' "   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="一级分类"  field="topCategoryId"  dictionary="t_s_category,id,name,level = '1' and status = 'A' "  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="二级分类"  field="subCategoryId"  dictionary="t_s_category,id,name,level = '2' and status = 'A' "  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="三级分类"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' "  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="tRetailerBrandCategoryController.do?doDel&id={id}" /> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="tRetailerBrandCategoryController.do?goAdd&otherRetailerId=${otherRetailerId }&retailerId=${retailerId }" funname="goAdd" ></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tRetailerBrandCategoryController.do?goUpdate" funname="update"></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tRetailerBrandCategoryController.do?doBatchDel&retailerId=${retailerId }" funname="deleteALLSelect"></t:dgToolBar>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tRetailerBrandCategoryController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 
 function goAdd(title,url,gname){
	 gridname = gname;
	 var width = 500;
	 var height = 250;
// 	 $.dialog.setting.zIndex = 800;
// 	 alert($.dialog.setting.zIndex);
	 if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				lock : true,
				zIndex:800,
				width:width,
				height:height,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					reloadTable();
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			W.$.dialog({
				content: 'url:'+url,
				lock : true,
				width:width,
				zIndex:800,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					reloadTable();
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
// 	 alert($.dialog.setting.zIndex);
 } 
 </script>