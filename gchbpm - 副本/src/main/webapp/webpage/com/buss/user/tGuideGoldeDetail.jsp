<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
	  <t:datagrid name="tGuideGoldDetail" checkbox="false" fitColumns="true" title="导购金币明细" actionUrl="tPersonController.do?datagridOfGuideGoldDetail&guideId=${guideId}" idField="id" fit="true" queryMode="group">
	   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
	   <t:dgCol title="时间"  field="createDate"   queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" query="true"></t:dgCol>
	   <t:dgCol title="标题"  field="title"   queryMode="single" width="200"></t:dgCol>
	   <t:dgCol title="金币数量"  field="goldNum"    queryMode="single"  width="100"></t:dgCol>
	   <c:if test="${user_Type!='01' }"> 
			<t:dgToolBar title="操作金币" icon="icon-add" url="tPersonController.do?doGuideGold&guideId=${guideId}" funname="add" height="250"></t:dgToolBar>
	   </c:if> 
	  </t:datagrid>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tGuideGoldDetailtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:140px;")
 			.attr("id","createDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createDate_end\')}'});});
 			$("#tGuideGoldDetailtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:140px;")
 			.attr("id","createDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createDate_begin\')}'});});
 });
 </script>