<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<div class="easyui-layout" fit="true">
	  <div region="center" style="padding:1px;">
	  <t:datagrid name="templateWordsList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="话术模板" actionUrl="templateWordsController.do?datagrid&tp=${tp}" 
	  idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
	   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
	   <t:dgCol title="更新时间"  field="updateDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
	   <t:dgCol title="一级分类"  field="topTypeId" dictionary="t_template_type,id,name,status='A' and level='1' and template_type='1'"  queryMode="single"  width="120"></t:dgCol>
	   <%-- <t:dgCol title="二级分类"  field="subTypeId" dictionary="t_s_category,id,name,status='A' and level='2'and retailer_id='admin' and template_type='1'"  queryMode="single"  width="120"></t:dgCol> --%>
	   <t:dgCol title="二级分类"  field="subName" queryMode="single"  width="120"></t:dgCol>
	   <t:dgCol title="三级分类"  field="thridTypeId" dictionary="t_template_type,id,name,status='A' and level='3' and template_type='1'"  queryMode="single"  width="120"></t:dgCol>
	   <t:dgCol title="关联行业"  field="tradeName" queryMode="single" width="100"></t:dgCol>
	   <t:dgCol title="话术类型"  field="type" replace="图片话术_2,文字话术_1" queryMode="single" query="true" width="100"></t:dgCol>
		<t:dgCol title="商品话术标签"  field="tags" queryMode="single" width="100"></t:dgCol>
	   <t:dgCol title="内容"  field="content"  formatterjs="checkPicShow"  queryMode="single"  query="true" width="500"></t:dgCol>
	   <t:dgCol title="平台类别"  field="platformType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
	   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
	   <c:if test="${userType=='01' }">
	   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	   <t:dgDelOpt title="删除" url="templateWordsController.do?doDel&id={id}" />
	   <t:dgToolBar title="录入" icon="icon-add" url="templateWordsController.do?goAdd" funname="add"></t:dgToolBar>
	   <t:dgToolBar title="编辑" icon="icon-edit" url="templateWordsController.do?goUpdate" funname="update"></t:dgToolBar>
	   <t:dgToolBar title="批量删除"  icon="icon-remove" url="templateWordsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
<%-- 	   <t:dgToolBar title="批量编辑关联行业" icon="icon-edit" url="templateWordsController.do?goBatchUpdate" funname="goBatchUpdate" height="200"></t:dgToolBar> --%>
	   </c:if>
	   <c:if test="${userType!='01' }">
	   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	   <t:dgConfOpt title="复制" url="templateWordsController.do?doCopy&id={id}"   message="确定复制到自有话术？"></t:dgConfOpt>
<%-- 	   <t:dgToolBar title="编辑" icon="icon-edit" url="templateWordsController.do?goUpdateByRet" funname="update"></t:dgToolBar> --%>
	   <t:dgToolBar title="批量复制"  icon="icon-edit" url="templateWordsController.do?doBatchCopy" funname="copyALLSelect"></t:dgToolBar>
	   </c:if>
	   <t:dgToolBar title="查看" icon="icon-search" url="templateWordsController.do?goUpdate" funname="detail"></t:dgToolBar>
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
	            <select id="subTypeId" name="subTypeId" onchange="getSubList(this.value)" WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
				</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="三级分类">
	                	三级分类：
	            </span>
	            <select id="thridTypeId" name="thridTypeId"  WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
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
	
<script src = "webpage/com/buss/template/templateWords.js?v=1.13"></script>
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#templateWordsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
 });
 
//判断是否显示图片
function checkPicShow(value,row,index){
	 var type = row.type;//1:文字，2：图片
	 if("2"==type){
		 if(value!=""){
			 return '<img width="70" height="70" border="0" src="'+value+'">';
		 }else{
			 return '';
		 }
	 }else{
		 return value;
	 }
}

//批量编辑关联行业
function goBatchUpdate(title,addurl,gname,width,height) {
	gridname=gname;
	createwindow(title, addurl,width,height);
}

//批量编辑（复制）
function copyALLSelect(title,url,gname){
	 var rows = $('#'+gname).datagrid('getSelections');
		if (!rows || rows.length==0) {
			tip('请选择一条记录');
			return;
		}
		var ids = [];
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		 $.messager.confirm('提示信息', "确认复制到自有话术？", function(r){
			  if (r) {
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
								reloadTable();
								$("#"+gname).datagrid('unselectAll');
							}
						}
					});
				}
		  })
}
 </script>