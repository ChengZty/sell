<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
<!-- 话题列表选择关联商品 -->
  <div region="center" style="padding:1px;">
  <t:datagrid name="newsList" checkbox="false" fitColumns="false"  title="话题列表" extendParams="nowrap:false," sortName="createDate" sortOrder="desc" actionUrl="templateNewsController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="封面图片"  field="coverPic"    queryMode="single"  width="150" imageSize="130,80"  image="true"></t:dgCol>
   <t:dgCol title="话题作者"  field="author"   query="true" queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgCol title="话题分类"  field="newsType"   query="true" queryMode="single" dictionary="t_news_type,code,name,status = 'A' and retailer_id = '${retailerId }'"  width="80"></t:dgCol> --%>
   <t:dgCol title="话题分类"  field="newsType" queryMode="single" dictionary="news_type"  width="80"></t:dgCol>
   <t:dgCol title="关联行业"  field="tradeName" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="是否已发布"  field="upLoaded"   query="true" queryMode="single" dictionary="sf_yn" width="80"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate" formatter="yyyy-MM-dd"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="getNewsGoodsList(id)" title="话题商品" exp="retailerId#empty#false"></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="reviewbytab()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div region="east" style="width: 620px;" split="true">
	<div tools="#tt" class="easyui-panel" title='查看话题商品' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
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
<script type="text/javascript">
	$(document).ready(function(){
		$("#newsListtb").find("select[name='newsType']").attr("name","news_Type");
		$("#newsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
	});
 

	function reviewbytab() {
		var rowsData = $("#newsList").datagrid("getSelections");
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
	function getNewsGoodsList(id) {
		$("#function-panel").panel(
			{
				title : '话题' ,
				href:"tNewsGoodsController.do?list&news_Id=" + id
			}
		);
	// 	$('#function-panel').panel("refresh" );
	}
</script>