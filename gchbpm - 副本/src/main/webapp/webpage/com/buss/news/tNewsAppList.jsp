<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsList" checkbox="true" fitColumns="true"  title="话题列表" extendParams="nowrap:false," 
  sortName="createDate" sortOrder="desc" actionUrl="tNewsController.do?datagridOfAppNews&content_Id=${content_Id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="话题作者"  field="author"   query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="话题分类"  field="newsType"   query="true" queryMode="single" dictionary="t_news_type,code,name,1=1"  width="320"></t:dgCol> --%>
   <t:dgCol title="话题分类"  field="newsType"   query="true" queryMode="single" replace="${newsTypeArr }"  width="150"></t:dgCol>
   <t:dgCol title="封面图片"  field="coverPic"    queryMode="single"  width="150" imageSize="130,80"  image="true"></t:dgCol>
   <t:dgCol title="标签"  field="tags"    queryMode="single"  width="200" ></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="reviewbytab()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<!--  <script src = "webpage/com/buss/news/tNewsList.js"></script>	 -->
 <script type="text/javascript">
 $(document).ready(function(){
// 	$("#tNewsListtb").find("select[name='newsType']").attr("name","news_Type");
 });
 

function reviewbytab() {
	var rowsData = $("#tNewsList").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var id=rowsData[0].id;
	window.open("tNewsController.do?goReview&id="+rowsData[0].id,"new");
}


 </script>