<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="baseBrandList" checkbox="true" fitColumns="false" sortName="createDate" sortOrder="desc" title="品牌" actionUrl="baseBrandController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌图片"  field="brandPic"  image="true"  queryMode="single"  imageSize="80" width="80"></t:dgCol>
   <t:dgCol title="背景图片"  field="bigPic"  image="true"  queryMode="single"  imageSize="130,80" width="130"></t:dgCol>
   <t:dgCol title="品牌编码"  field="brandCode"   query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="品牌名称"  field="brandName"   query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="品牌介绍"  field="brandSummary"    width="200"></t:dgCol>
   <c:if test="${userType == '01' }">
   <t:dgCol title="零售商"  field="retailerId"  dictionary="t_s_user,id,realname,status = 'A' and user_type='02'"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="排序"  field="sortNo"    queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
   <t:dgDelOpt title="删除" url="baseBrandController.do?doDel&id={id}" />
   <t:dgFunOpt funname="setBaseBrabd(id,brandName)" title="品牌分类" exp="retailerId#empty#false"></t:dgFunOpt>
<%--    <t:dgFunOpt funname="setBaseBrabdSellArea(id)" title="可售区域" exp="retailerId#empty#false"></t:dgFunOpt> --%>
   <c:if test="${userType == '02' || userType == '05'}">
   <t:dgToolBar title="录入" icon="icon-add"  funname="addbytab"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit"  funname="updatebytab"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="baseBrandController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search"  funname="reviewbytab"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 
 <div data-options="region:'east',
	title:'查看话题商品',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 500px; overflow: hidden;">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="function-panel"></div>
</div>


 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#baseBrandListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#baseBrandListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function addbytab() {
		document.location="baseBrandController.do?goAdd";
}
 
 function reviewbytab() {
		var rowsData = $("#baseBrandList").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].id;
		document.location="baseBrandController.do?goReview&id="+id;
	}

	function updatebytab(){
		var rowsData = $("#baseBrandList").datagrid("getSelections");
			if(rowsData==''){
				tip('请选择一行记录');
				return;
			}
			if (rowsData.length>1) {
				tip('只能选择一条记录');
				return;
			}
			var id=rowsData[0].id;
			document.location="baseBrandController.do?goUpdate&id="+id;
	}
 

function setBaseBrabd(id,brandName) {
	if(li_east == 0){
		   $('#main_depart_list').layout('expand','east'); 
	}else{
		   $('#main_depart_list').layout('collapse','east'); 
	}
	$("div.layout-panel-east div.panel-title").text(brandName);
	$('#function-panel').panel("refresh", "baseBrandController.do?typeList&brand_Id=" + id);
		}

function setBaseBrabdSellArea(id) {
	$("#function-panel").panel(
		{
			title : '可售区域设置(默认是全国)' ,
			href:"tBrandSellAreaController.do?list&brandId=" + id
		}
	);
}

 </script>