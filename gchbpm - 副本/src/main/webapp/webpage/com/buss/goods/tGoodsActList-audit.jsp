<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tGoodsActList" checkbox="false" fitColumns="false" title="商品活动审核" actionUrl="tGoodsActController.do?datagrid&actType=${actType }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="活动名称"  field="title"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="开始时间"  field="beginTime" formatter="yyyy-MM-dd hh:mm:ss" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="结束时间"  field="endTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否有效"  field="valid"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="auditor"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核时间"  field="auditTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <c:if test="${actType !='3' }">
       <t:dgCol title="审核状态"  field="auditStatus" replace="待审核_1,已审核_2,已作废_3"   queryMode="single"  width="120"></t:dgCol>
   	   <t:dgCol title="活动状态"  field="actStatus" replace="待审核_1,待开始_2,进行中_3,已结束_4" query="true"  queryMode="single"  width="120"></t:dgCol>
   </c:if>
   <c:if test="${actType =='3' }">
       <t:dgCol title="活动状态"  field="auditStatus" replace="待审核_1,已审核_2,已作废_3"   queryMode="single"  width="120"></t:dgCol>
       <t:dgCol title="活动状态"  hidden="true"  field="actStatus" replace="待审核_1,待开始_2,进行中_3,已结束_4"  width="120"></t:dgCol>
   </c:if>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgFunOpt funname="goView(id,actStatus)"  title="查看"></t:dgFunOpt>
   <t:dgConfOpt title="审核" url="tGoodsActController.do?doAudit&id={id}" exp="auditStatus#eq#1" message="确定审核？" urlStyle="color:red" ></t:dgConfOpt>
   <t:dgConfOpt title="下架" url="tGoodsActController.do?doDown&id={id}" exp="auditStatus#eq#2&&valid#eq#Y&&actStatus#ne#4" message="确定下架？"></t:dgConfOpt>
   <t:dgConfOpt url="tGoodsActController.do?doInvalid&id={id}"  message="确定作废" title="作废" exp="actStatus#eq#4&&auditStatus#ne#3"></t:dgConfOpt>
   <t:dgToolBar title="商品活动价查询" icon="icon-search" url="tGoodsActController.do?allActGoodsList" funname="viewAllActGoodsList" width="1150" height="600"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
$(function(){
	$("#tGoodsActListtb").find("input[name='beginTime']").attr("class","Wdate").attr("style","height:20px;width:90px;")
	.attr("id","beginTime").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'});});
	$("#tGoodsActListtb").find("input[name='endTime']").attr("class","Wdate").attr("style","height:20px;width:90px;")
	.attr("id","endTime").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTime\')}'});});
})
 //查看
 function goView(id,actStatus){
	document.location="tGoodsActController.do?goUpdate&page=audit&view=1&id="+id+"&actStatus="+actStatus;
 }
 //商品活动价查询
 function viewAllActGoodsList(title,addurl,gname,width,height){
	 gridname=gname;
	 createwindow(title,addurl,width,height);
 }
 </script>