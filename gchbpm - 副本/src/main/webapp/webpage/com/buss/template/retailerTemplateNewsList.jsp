<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="话题列表" 
  sortName="createDate" sortOrder="desc" actionUrl="templateNewsController.do?datagrid&isRet=1" idField="id" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商Id"  field="shopkeeper"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="封面图片"  field="coverPic"    queryMode="single"  width="130" imageSize="130,80"  image="true"></t:dgCol>
   <t:dgCol title="话题作者"  field="author"   query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="话题分类"  field="newsType"   query="true" queryMode="single" dictionary="t_news_type,code,name,status = 'A' and platform_type='2' and retailer_id = '${rId }'"  width="120"></t:dgCol> --%>
   <t:dgCol title="话题分类"  field="newsType" queryMode="single" dictionary="news_type"  width="120"></t:dgCol>
   <t:dgCol title="关联行业"  field="tradeName" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="标签"  field="tags"    queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgCol title="点击数"  field="clickNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="点赞数"  field="goodNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="无感数"  field="noSenseNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="是否已发布"  field="upLoaded"   query="true" queryMode="single" dictionary="sf_yn" width="100"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate" formatter="yyyy-MM-dd"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
   <t:dgDelOpt title="删除" url="tNewsController.do?doDel&id={id}" />
   <t:dgFunOpt funname="copyUrl(id)" title="复制链接"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url=""  onclick="addbytab()" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url=""  onclick="updatebytab()" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="reviewbytab()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
  <div id="tempSearchColums" style="display: none">
	    <div name="searchColums">
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="话题分类">
	                	话题分类：
	            </span>
	            <select id="topTypeId" name="topTypeId" onchange="getSubList(this.value)"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">---所有---</option>
						 <c:forEach var="obj" items="${topTypeList}" >
								<option value="${obj.id}">${obj.name}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	        
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="关联行业">
	                	关联行业：
	            </span>
	            <select id="templateTrade" name="templateTrade"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">---所有---</option>
						 <c:forEach var="obj" items="${templateTrade}" >
								<option value="${obj.id}">${obj.name}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	    </div>
	</div>
 <input id="newsUrl" type="text" value=""/>
 <script type="text/javascript">
 $(document).ready(function(){
		$("#tNewsListtb").find("select[name='newsType']").attr("name","news_Type");
		 $("#tNewsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
 });
 
function addbytab() {
		document.location="tNewsController.do?goAdd";
}

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
	window.open("tNewsController.do?goReview&id="+id,"new");
// 	document.location="tNewsController.do?goReview&id="+id;
}

function updatebytab(){
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
		document.location="tNewsController.do?goUpdate&id="+id;
}

function copyUrl(id){
	var url = "${newsUrl}"+id;
	$("#newsUrl").val(url);
	 var e=document.getElementById("newsUrl");
// 	 alert(e.value);
	e.select(); //选择对象 
    document.execCommand("Copy"); //执行浏览器复制命令
	alert("复制成功!");
}

 </script>