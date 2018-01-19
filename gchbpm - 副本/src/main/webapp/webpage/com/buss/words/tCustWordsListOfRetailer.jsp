<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tCustWordsList" checkbox="true" fitColumns="false" title="顾客话术" actionUrl="tCustWordsController.do?datagrid&tp=2" 
  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="一级分类"  field="topTypeId"   dictionary="t_cust_words_type,id,name,status = 'A' and level = '1'"  queryMode="single"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="二级分类"  field="subTypeId"   dictionary="t_cust_words_type,id,name,status = 'A' and level = '2'" queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="一级分类"  field="topTypeId"   dictionary="t_template_type,id,name,status = 'A' and level = '1' and parent_id='100'"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="二级分类"  field="subTypeId"   dictionary="t_template_type,id,name,status = 'A' and level = '2' and parent_id='102'" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="话术类型"  field="type" replace="图片话术_2,文字话术_1" queryMode="single" query="true" width="100"></t:dgCol>
   <t:dgCol title="内容"  field="content"  query="true" formatterjs="checkPicShow"  queryMode="single"  width="500"></t:dgCol>
   <c:if test="${userType != '01' }">
   <t:dgCol title="是否可见"  field="isShow" query="true" replace="是_Y,否_N" queryMode="single"  width="90"></t:dgCol>
   </c:if>
   <t:dgCol title="平台类别"  field="platformType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <c:if test="${userType == '01' }">
   <t:dgCol title="零售商"  field="retailerId"  dictionary="t_s_user,id,realname,status = 'A' and user_type='02'"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <c:if test="${userType != '01' }">
   <t:dgFunOpt funname="doShow(id,subTypeId)" title="设置可见" exp="isShow#eq#N"></t:dgFunOpt>
   <t:dgFunOpt funname="doHide(id)" title="设置不可见" exp="isShow#eq#Y"></t:dgFunOpt>
   </c:if>
   <t:dgDelOpt title="删除" url="tCustWordsController.do?doDel&id={id}" />
   <c:if test="${userType != '01' }">
   <t:dgToolBar title="录入" icon="icon-add" url="tCustWordsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tCustWordsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tCustWordsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="tCustWordsController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 <div id="tempSearchColums" style="display: none">
	    <div name="searchColums">
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="一级分类">
	                	一级分类：
	            </span>
	            <select id="topTypeId" name="topTypeId" onchange="getSubList(this.value)"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">---所有---</option>
						 <c:forEach var="obj" items="${topTypeList}" >
								<option value="${obj.id}">${obj.name}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="二级分类">
	                	二级分类：
	            </span>
	            <select id="subTypeId" name="subTypeId"  WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
				</select>
	        </span>
	    </div>
	</div>
	<script src = "webpage/com/buss/words/tCustWords.js?v=1.01"></script>
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#tCustWordsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
 });
 
 //设置可见
 function doShow(id,subTypeId){
	 var url = "tCustWordsController.do?doShow";
	 $.ajax({
			type : 'POST',
			url : url,// 请求的action路径
			data : {
				id : id,
				subTypeId : subTypeId
			},
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
 
 //设置不可见
 function doHide(id){
	 var url = "tCustWordsController.do?doShow";
	 $.ajax({
			type : 'POST',
			url : url,// 请求的action路径
			data : {
				id : id
			},
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