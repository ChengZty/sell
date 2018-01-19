<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker,layer"></t:base> --%>
<!-- <div class="easyui-layout" fit="true"> -->
<!-- <div region="center" style="padding:1px;"> -->
		  <t:datagrid name="tCommissionCloudList" checkbox="true" fitColumns="true" sortName="createDate" sortOrder="desc" title="实收分配定义" actionUrl="tCommissionCloudController.do?datagrid&store_Id=${store_Id }" idField="id" fit="true" queryMode="group">
		   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="云商ID" hidden="true" field="storeId"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="一级分类" hidden="true" field="topCategoryId"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="二级分类" hidden="true" field="subCategoryId"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="三级分类" hidden="true" field="thridCategoryId"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="一级分类"  field="topCategoryName"    queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="二级分类"  field="subCategoryName"    queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="三级分类"  field="thridCategoryName"    queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="品牌" hidden="true" field="brandId"    width="120"></t:dgCol>
		   <t:dgCol title="品牌"  field="brandName"    queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="提成占比"  field="commission"   formatterjs="percent"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   		   <t:dgDelOpt title="删除" url="tCommissionCloudController.do?doDel&id={id}" />
		   <t:dgToolBar title="录入" icon="icon-add" url="tCommissionCloudController.do?goAdd&store_Id=${store_Id }" width="800" height="400"  funname="add"></t:dgToolBar>
<%-- 		   <t:dgToolBar title="录入" icon="icon-add" url=""  onclick="addbytab()" funname="add"></t:dgToolBar> --%>
<%-- 		   <t:dgToolBar title="批量录入" icon="icon-add" url=""  onclick="batchAdd()" funname="batchAdd"></t:dgToolBar> --%>
		   <t:dgToolBar title="编辑" icon="icon-edit" url="tCommissionCloudController.do?goUpdate"  width="800" height="400"  funname="update"></t:dgToolBar>
<%-- 		   <t:dgToolBar title="编辑" icon="icon-edit" url=""  onclick="updatebytab()" funname="update"></t:dgToolBar> --%>
		   <t:dgToolBar title="查看" icon="icon-search" url="tCommissionCloudController.do?goReview"  funname="detail" width="800" height="400"></t:dgToolBar>
<%-- 		   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="reviewbytab()" funname="detail"></t:dgToolBar> --%>
		  </t:datagrid>
<!-- 	</form> -->
<!--   </div> -->
<!--  </div>	 -->
<input type="hidden" id="storeId" value="${store_Id }"/>
	<div id="tempSearchColums" style="display: none">
	    <div name="searchColums">
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;" title="品牌">
	            <input id="brandId" name="brandId" type="hidden" >
	                	品牌：<input type="text" name="brandName" id="brandName" style="width: 100px;height: 26px;">
						<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" id=""><span class="l-btn-text icon-search l-btn-icon-left">选择</span></a>
	            </span>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;" title="一级分类">
	                	一级分类：
	                	<select id="topCategoryId" name="topCategoryId" onchange="getSubList(this.value,'subCategoryId')"  WIDTH="100" style="width: 104px;height: 26px;">
							 <option value="">---所有---</option>
							 <c:forEach var="obj" items="${catList}" >
									<option value="${obj.id}">${obj.name}</option>					 	
				              </c:forEach>
						</select>
	            </span>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;" title="二级分类">
	                	二级分类：<select id="subCategoryId" name="subCategoryId" onchange="getSubList(this.value,'thridCategoryId')"  WIDTH="100" style="width: 104px;height: 26px;">
									<option value="">---所有---</option>
								</select>
	            </span>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;" title="三级分类">
	                	三级分类：<select id="thridCategoryId" name="thridCategoryId"  WIDTH="100" style="width: 104px;height: 26px;">
									<option value="">---所有---</option>
								</select>
	        </span>
	    </div>
	</div>
	<div id="queryBtn">
		<span style="float: right;" >
			<a class="easyui-linkbutton l-btn" onclick="tCommissionCloudListsearch()" href="#" iconCls="icon-search">查询</a>
			<a class="easyui-linkbutton l-btn" onclick="searchReset('tCommissionCloudList')" href="#" iconCls="icon-reload">重置</a>
		</span>
	</div>
	
<script type="text/javascript">
<!--
$(function () {
	$("#tCommissionCloudListtb").append($("#tempSearchColums div[name='searchColums']").html());
	$("#tCommissionCloudListtb div[name='searchColums']").next().append($("#queryBtn").html());
	$("#tempSearchColums").empty();
});

function addbytab() {
	document.location="tCommissionCloudController.do?goAdd";
}

function batchAdd() {
	document.location="tCommissionCloudController.do?goBatchAdd";
}

function reviewbytab() {
	var rows = $("#tCommissionCloudList").datagrid("getSelections");
	if(rows==''){
		layer.msg('请选择一行记录', {icon: 5,offset: 0,shift: 6,time:1500});
		return;
	}
	if (rows.length>1) {
		layer.msg('只能选择一条记录', {icon: 5,offset: 0,shift: 6,time:1500});
		return;
	}
	var id=rows[0].id;
	document.location="tCommissionCloudController.do?goReview&id="+id;
}

function updatebytab(){
	var rows = $("#tCommissionCloudList").datagrid("getSelections");
	if(rows==''){
		layer.msg('请选择一行记录', {icon: 5,offset: 0,shift: 6,time:1500});
		return;
	}
	if (rows.length>1) {
		layer.msg('只能选择一条记录', {icon: 5,offset: 0,shift: 6,time:1500});
		return;
	}
	var id=rows[0].id;
	document.location="tCommissionCloudController.do?goUpdate&id="+id;
}

function percent(value,rec,index){
	return value+'%';
}
//-->
</script>
<script src = "webpage/com/buss/bill/tCommissionCloud.js"></script>		