<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsActList" checkbox="false" fitColumns="false" title="商品活动管理" actionUrl="tGoodsActController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动名称"  field="title"   query="true" queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="开始时间"  field="beginTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否有效"  field="valid"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="auditor"    queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="审核时间"  field="auditTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核状态"  field="auditStatus" hidden="true" replace="待审核_1,已审核_2"   queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="活动状态"  field="actStatus" replace="待审核_1,待开始_2,进行中_3,已结束_4" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="260"></t:dgCol>
   <t:dgFunOpt funname="goView(id,title)"  title="查看"></t:dgFunOpt>
   <t:dgDefOpt title="编辑" url="tGoodsActController.do?goUpdate&id={id}" exp="auditStatus#eq#1"/>
   <t:dgDelOpt title="删除" url="tGoodsActController.do?doDel&id={id}" exp="auditStatus#eq#1"/>
<%--    <t:dgFunOpt funname="relateTopic(id,title)" title="关联话题" exp="retailerId#empty#false"></t:dgFunOpt> --%>
<%--    <t:dgFunOpt  funname="relateWords(id,title)"  title="关联话术"  exp="retailerId#empty#false"/> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="" funname="goAdd"></t:dgToolBar>
   <t:dgToolBar title="商品活动价查询" icon="icon-search" url="tGoodsActController.do?allActGoodsList" funname="viewAllActGoodsList" width="1150" height="600"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <!-- 
 <div data-options="region:'east',
	title:'商品活动管理详情',
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
 -->	
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="function-panel"></div>
</div>
 <script type="text/javascript">
/*  function tGoodsActListsearch() {
	var queryParams = $('#tGoodsActList').datagrid('options').queryParams;
	$('#tGoodsActListtb').find('*').each(function() {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	alert();
	$('#tGoodsActList').datagrid(
		{
			url : 'tGoodsActController.do?datagrid&field=id,createName,createBy,createDate,updateName,updateBy,updateDate,status,title,beginTime,endTime,valid,auditor,auditTime,auditStatus,actStatus,',
			pageNumber : 1
		});
} */
 $(document).ready(function(){
 		//给时间控件加上样式
	var msg = "${msg}";//提示信息（新增或者更新后会有）
	if(msg!=""){
		tip(msg);
	}
	var downloadKey = "${downloadKey}";//保存后的错误记录的key
	if(downloadKey!=""&&downloadKey!="null"){
		//下载错误的记录
		window.location.href=downloadKey;//直接从七牛下载
// 		ExportXlsxByFailList(downloadKey);
	}
 });
//新增
 function goAdd(){
	 document.location="tGoodsActController.do?goAdd";
 }
 //修改
 function goUpdate(){
	 document.location="tGoodsActController.do?goUpdate";
 }
 //查看
 function goView(id){
// 	var rowsData = $('#tGoodsActList').datagrid('getSelections');
// 	if (!rowsData || rowsData.length==0) {
// 		tip('请选择活动');
// 		return;
// 	}rowsData[0].id
	document.location="tGoodsActController.do?goUpdate&view=1&id="+id;
 }
 //商品活动价查询
 function viewAllActGoodsList(title,addurl,gname,width,height){
	 gridname=gname;
	 createwindow(title,addurl,width,height);
 }
 
//导入的错误记录下载
 function ExportXlsxByFailList(key) {
 	JeecgExcelExport("tGoodsActDetailController.do?exportXlsxByFailList&key="+key,"tGoodsActList");
 }
 //关联话题
	function relateTopic(id,title){
		if(li_east == 0){
			   $('#main_depart_list').layout('expand','east'); 
		}
		$("div.layout-panel-east div.panel-title").text(title);
		$('#function-panel').panel("refresh", "tGoodsActController.do?relateTopic&act_id=" + id);
	}
//关联话术
 function relateWords(id,title) {
		if(li_east == 0){
			   $('#main_depart_list').layout('expand','east'); 
		}
		$("div.layout-panel-east div.panel-title").text(title);
		$('#function-panel').panel("refresh", "tGoodsActController.do?goodsConWordsList&actId=" + id);
	}
 </script>