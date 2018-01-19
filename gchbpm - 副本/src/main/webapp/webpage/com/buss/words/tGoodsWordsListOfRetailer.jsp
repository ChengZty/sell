<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsWordsList" checkbox="true" fitColumns="false" title="顾客话术" actionUrl="tGoodsWordsController.do?datagrid&tp=2" 
  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="一级分类"  field="topCategoryId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="二级分类"  field="subCategoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="三级分类"  field="thridCategoryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="一级分类"  field="topCategoryName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="二级分类"  field="subCategoryName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="三级分类"  field="thridCategoryName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内容"  field="content"  query="true"   queryMode="single"  width="320"></t:dgCol>
   <t:dgCol title="平台类别"  field="platformType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <c:if test="${userType == '01' }">
   <t:dgCol title="零售商"  field="retailerId"  dictionary="t_s_user,id,realname,status = 'A' and user_type='02'"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tGoodsWordsController.do?doDel&id={id}" />
   <c:if test="${userType != '01' }">
   <t:dgToolBar title="录入" icon="icon-add" url="tGoodsWordsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tGoodsWordsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tGoodsWordsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="tGoodsWordsController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 <div id="tempSearchColums" style="display: none">
	    <div name="searchColums">
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="一级分类">
	                	一级分类：
	            </span>
	            <select id="topCategoryId" name="topCategoryId" onchange="getSubList(this.value)"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">---所有---</option>
						 <c:forEach var="obj" items="${topCategoryList}" >
								<option value="${obj[0]}">${obj[1]}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="二级分类">
	                	二级分类：
	            </span>
	            <select id="subCategoryId" name="subCategoryId" onchange="getThirdList(this.value)"  WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
				</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="二级分类">
	                	三级分类：
	            </span>
	            <select id="thridCategoryId" name="thridCategoryId"  WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
				</select>
	        </span>
	    </div>
	</div>
	<script src = "webpage/com/buss/words/tGoodsWords.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#tGoodsWordsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
 });
 

 </script>