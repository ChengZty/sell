<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="话题列表" sortName="createDate" sortOrder="desc" actionUrl="templateNewsController.do?datagrid&isRet=A" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="封面图片"  field="coverPic"    queryMode="single"  width="130" imageSize="130,80"  image="true"></t:dgCol>
   <t:dgCol title="话题作者"  field="author"   query="true" queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="话题分类"  field="newsType"   queryMode="single" formatterjs="replaceCode"  width="150"></t:dgCol> --%>
   <t:dgCol title="话题分类"  field="newsType"   queryMode="single" dictionary="news_type"  width="80"></t:dgCol>
   <t:dgCol title="关联行业"  field="tradeName" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="标签"  field="tags"    queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgCol title="点击数"  field="clickNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="点赞数"  field="goodNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="无感数"  field="noSenseNum"    queryMode="single"  width="50" ></t:dgCol>
   <t:dgCol title="是否已发布"  field="upLoaded"   query="true" queryMode="single" dictionary="sf_yn" width="90"></t:dgCol>
   <t:dgCol title="零售商"  field="shopkeeper" query="true" dictionary="t_s_user,id,realname,user_type='02' and status = 'A' and user_status = '1' " queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate" formatter="yyyy-MM-dd"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tNewsController.do?doDel&id={id}" />
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
 <script src = "webpage/com/buss/news/newsTypeReplace.js?v=1.01"></script>
 <script type="text/javascript">
// 	 var value = "${vo.id}";//所有话题分类code_retailerId,用逗号连接
// 	 var text = "${vo.name}";//对应所有话题分类name,用逗号连接
 $(document).ready(function(){
	 var codeSelect = getCodeSelectHtml();
	 $("#tNewsListForm").append(codeSelect);
	 $("#tNewsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
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
	window.open("tNewsController.do?goReview&id="+id,"new");
}

 </script>