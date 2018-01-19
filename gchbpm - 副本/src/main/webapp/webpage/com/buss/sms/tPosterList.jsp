<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
	  <t:datagrid name="tPoster" checkbox="false" fitColumns="false" extendParams="nowrap:false,"  title="列表" 
	      actionUrl="tPosterController.do?datagridByStatus" idField="id" fit="true" queryMode="group">
		   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     	   <t:dgCol title="零售商id"  field="retailerId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="海报状态"  field="postStatus" hidden="true"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="图片"  field="coverPic" image="true" imageSize="100,80"  queryMode="single" frozenColumn="true" ></t:dgCol>
		   <t:dgCol title="标题"  field="title" query="true" queryMode="single"  width="300" ></t:dgCol>
		   <t:dgCol title="作者"  field="author" query="true" queryMode="single"  width="100" ></t:dgCol>
	  </t:datagrid>
  </div>
 </div>
 <input id="url" type="text" value=""/>
 <input id="longUrl" type="text" hidden="true" value="${longUrl }">
 <script type="text/javascript">
 gridname = "tPoster";
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tNewGoodsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tNewGoodsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 </script>