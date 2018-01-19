<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tHiddenNewsList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="不可见话题" 
  actionUrl="tHiddenNewsController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题ID"  field="newsId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"  query="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="封面图片"  field="coverPic"    queryMode="single"  width="130" imageSize="130,80"  image="true"></t:dgCol>
   <t:dgCol title="话题作者"  field="author"   width="120"></t:dgCol>
   <t:dgCol title="话题分类"  field="newsType"   query="true" queryMode="single" dictionary="t_news_type,code,name,platform_type='1'"  width="150"></t:dgCol>
   <t:dgCol title="是否可见"  field="visible"  query="true" replace="是_1,否_0"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="doHide(newsId)" title="设置不可见" exp="visible#eq#1"></t:dgFunOpt>
   <t:dgFunOpt funname="doShow(id)" title="设置可见" exp="visible#eq#0"></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" onclick="reviewbytab()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 gridname = "tHiddenNewsList";
 function reviewbytab() {
		var rowsData = $("#tHiddenNewsList").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].newsId;
		window.open("tNewsController.do?goReview&id="+id,"new");
	}
 
 //新增不可见话题
 function doHide(newsId){
	 var url = "tHiddenNewsController.do?doAdd&newsId="+newsId;
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
					reloadTable();
				}
			}
		});
 }
 
//删除不可见话题
 function doShow(id){
	 var url = "tHiddenNewsController.do?doDel&id="+id;
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
					reloadTable();
				}
			}
		});
 }
 </script>