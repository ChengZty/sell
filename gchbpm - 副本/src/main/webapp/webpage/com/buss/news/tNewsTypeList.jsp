<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsTypeList" checkbox="true" fitColumns="false" sortName="orderNum" sortOrder="asc" title="话题分类" actionUrl="tNewsTypeController.do?datagrid&tp=1" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编码"  field="code" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="名称"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="小图"  field="smallPic" image="true" imageSize="50,30"  queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="图片"  field="coverPic" image="true" imageSize="100,60"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="排序编号"  field="orderNum"    queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgCol title="显示类别"  field="showType"   query="true" queryMode="single" dictionary="newsShowTp" width="120"></t:dgCol> --%>
<%--    <t:dgCol title="是否必须"  field="isNeed"  replace="否_0,是_1"  queryMode="single"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol> --%>
<%--    <c:if test="${userType !='01' }"> --%>
<%--    <t:dgFunOpt funname="doCopy(id)"  title="复制分类"></t:dgFunOpt> --%>
<%--    </c:if> --%>
<%--    <c:if test="${userType =='01' }"> --%>
<%--    <t:dgDelOpt title="删除" url="tNewsTypeController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tNewsTypeController.do?goAdd" funname="add" height="500"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tNewsTypeController.do?goUpdate" funname="update" height="500"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tNewsTypeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    </c:if> --%>
<%--    <c:if test="${userType !='01' }"> --%>
<%--    <t:dgToolBar title="批量复制"  icon="icon-add" url="tNewsTypeController.do?doBatchCopy" funname="doBatchCopy"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="全部复制"  icon="icon-add" url="tNewsTypeController.do?doAllCopy" funname="doAllCopy"></t:dgToolBar> --%>
<%--    </c:if> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tNewsTypeController.do?goUpdate" funname="detail" height="500"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//复制分类
 function doCopy(id){
 	 var url = "tNewsTypeController.do?doCopy&id="+id;
 	 $.ajax({
 			async : false,
 			cache : false,
 			type : 'POST',
 			url : url,// 请求的action路径
 			error : function() {// 请求失败处理函数
 			},
 			success : function(data) {
 				var d = $.parseJSON(data);
 				if (d.success) {
 					tip(d.msg);
//  					reloadTable();
 				}
 			}
 		});
 }
 
 //批量复制
 function doBatchCopy(title,url,gridname){
	 var ids = [];
	 var rows = $("#"+gridname).datagrid('getSelections');
	 if (rows.length > 0) {
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
						$("#"+gridname).datagrid('unselectAll');
						ids='';
					}
				}
			});
		} else {
			tip("请选择需要复制的数据");
		}
 }
 
//复制全部分类
 function doAllCopy(title,url,gridname){
 	 $.ajax({
 			async : false,
 			cache : false,
 			type : 'POST',
 			url : url,// 请求的action路径
 			error : function() {// 请求失败处理函数
 			},
 			success : function(data) {
 				var d = $.parseJSON(data);
 				if (d.success) {
 					tip(d.msg);
 				}
 			}
 		});
 }
 </script>