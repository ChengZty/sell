<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsList${newsType}${isRet}" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="${title}" 
  sortName="createDate" sortOrder="desc" actionUrl="tNewsController.do?guideNewsDatagrid&newsType=${newsType}&isRet=${isRet}" idField="id" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="封面图片"  field="coverPic"    queryMode="single"  width="130" imageSize="130,80"  image="true"></t:dgCol>
   <t:dgCol title="话题作者"  field="author"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="标签"  field="tags"    queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgCol title="点击数"  field="clickNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="点赞数"  field="goodNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="无感数"  field="noSenseNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="是否已发布"  field="upLoaded"   query="true" queryMode="single" dictionary="sf_yn" width="100"></t:dgCol>
   <c:if test="${isRet =='A' }">
   <t:dgCol title="零售商"  field="shopkeeper" query="true" dictionary="t_s_user,id,realname,user_type='02' and status = 'A' and user_status = '1' " queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="操作时间"  field="updateDate" formatter="yyyy-MM-dd"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
   <t:dgDelOpt title="删除" url="tNewsController.do?doDel&id={id}" />
   <c:if test="${userType !='01'}">
   <t:dgToolBar title="录入" icon="icon-add" url=""  funname="addbytab"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url=""  funname="updatebytab"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="" funname="reviewbytab"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
function addbytab() {
		document.location="tNewsController.do?goAdd&newsType=${newsType}";
}

function reviewbytab() {
	var rowsData = $("#tNewsList${newsType}${isRet}").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var id=rowsData[0].id;
	window.open("tNewsController.do?goReview&newsType=${newsType}&id="+id,"new");
// 	document.location="tNewsController.do?goReview&id="+id;
}

function updatebytab(){
	var rowsData = $("#tNewsList${newsType}${isRet}").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].id;
		document.location="tNewsController.do?goUpdate&newsType=${newsType}&id="+id;
}


 </script>