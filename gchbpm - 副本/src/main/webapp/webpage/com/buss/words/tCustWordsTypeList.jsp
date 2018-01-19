<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tCustWordsTypeList" pagination="false" openFirstNode="false" treegrid="true" fitColumns="false" title="顾客话术类别" 
  actionUrl="tCustWordsTypeController.do?treeGrid" idField="id" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true" treefield="id"  width="120"></t:dgCol>
   <t:dgCol title="类型名称"  field="name" treefield="text"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="层级"  field="level"  hidden="true" treefield="src"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否有图片"  field="hasPics" hidden="true"  treefield="remark"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tCustWordsTypeController.do?doDel&id={id}" exp="src#ne#1"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tCustWordsTypeController.do?goAdd" funname="addFun" height="200" width="400"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tCustWordsTypeController.do?goUpdate" funname="update" height="200" width="400"></t:dgToolBar>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tCustWordsTypeController.do?goUpdate" funname="detail" height="200" width="400"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 function update(title,url, gname,width,height) {
		gridname=gname;
		var rowData = $('#'+gname).datagrid('getSelected');
		if (rowData!=null) {
			if(rowData.src==1){
				tip("暂不支持编辑一级分类");
				return;
			}
			url += '&id='+rowData.id;
		}else{
			tip("请选择编辑项目");
			return;
		}
		url+="&hasPics="+rowData.remark;
		createwindow(title,url,width,height);
	}
 
 
 function addFun(title,url, gname,width,height) {
	 	gridname=gname;
		var rowData = $('#'+gname).datagrid('getSelected');
		if (rowData!=null) {
			if(rowData.src==2){
				tip("暂不支持添加三级分类");
				return;
			}
			url += '&pid='+rowData.id;
		}else{
			tip("请选择一级分类");
			return;
		}
		url+="&hasPics="+rowData.remark;
		add(title,url,'tCustWordsTypeList',width,height);
	}
 </script>