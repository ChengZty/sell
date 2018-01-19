<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewsListForAct" fitColumns="false" extendParams="nowrap:false," title="话题列表" 
  sortName="createDate" sortOrder="desc" actionUrl="tNewsController.do?datagrid&isRet=1&upLoaded=Y" idField="id" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商Id"  field="shopkeeper"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题标题"  field="title"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="封面图片"  field="coverPic"    queryMode="single"  width="130" imageSize="130,80"  image="true"></t:dgCol>
   <t:dgCol title="话题作者"  field="author"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话题分类"  field="newsType" query="true" queryMode="single" replace="${newsTypeArr }"  width="120"></t:dgCol>
   <t:dgCol title="关联行业"  field="tradeName" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="标签"  field="tags"    queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate" formatter="yyyy-MM-dd"  width="100"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <div id="tempSearchColums" style="display: none">
	    <div name="searchColums">
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
		 $("#tNewsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
 });
 

 </script>