<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
	  <t:datagrid name="tPoster" checkbox="true" fitColumns="false" extendParams="nowrap:false,"  title="海报列表" 
	      actionUrl="tPosterController.do?datagrid&post_Status=${postStatus}" idField="id" fit="true" queryMode="group">
		   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   		   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     	   <t:dgCol title="零售商id"  field="retailerId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="海报状态"  field="postStatus" hidden="true"   queryMode="single"  width="120"></t:dgCol>
		   <t:dgCol title="图片"  field="coverPic" image="true" imageSize="130,80"  queryMode="single" frozenColumn="true" ></t:dgCol>
		   <t:dgCol title="标题"  field="title" query="true" queryMode="single"  width="300" funname="goView" url="tPosterController.do?viewPoster&id={id}" ></t:dgCol>
		   <t:dgCol title="作者"  field="author" hidden="true" width="100" ></t:dgCol>
		   <t:dgCol title="排序"  field="sortNum" hidden="true" queryMode="single"  width="100" ></t:dgCol>
		   <t:dgCol title="操作" field="opt" width="400" align="center" ></t:dgCol>
		   <t:dgFunOpt funname="doToTop(id)"  title="置顶"></t:dgFunOpt>
		   <t:dgFunOpt funname="doToUper(id,sortNum)" title="<img src='plug-in/easyui/themes/default/images/accordion_collapse.png'/>" 
		   urlStyle="position: relative; top: 4px;" ></t:dgFunOpt>
		   <t:dgFunOpt funname="doToDown(id,sortNum)"  title="<img src='plug-in/easyui/themes/default/images/accordion_expand.png'/>" 
			urlStyle="position: relative; top: 4px;" ></t:dgFunOpt>
		   <!-- postStatus 1草稿，2已完成，3已上架，4已下架 -->
		   <c:if test="${postStatus eq '2'}">
			   <t:dgConfOpt title="上架" url="tPosterController.do?doUp&id={id}" message="确认上架该海报？" exp="postStatus#eq#2" urlStyle="color:red" />
		   </c:if>
		   <c:if test="${postStatus eq '3'}">
			   <t:dgConfOpt title="下架" url="tPosterController.do?doDown&id={id}" message="确认下架该海报？" exp="postStatus#eq#3" urlStyle="color:red" />
			   <t:dgToolBar title="批量下架" icon="icon-edit" url="tPosterController.do?doBatchDown" funname="doBatchDown"></t:dgToolBar>
<%-- 		   		<t:dgFunOpt title="推送" funname="doSend(id)"></t:dgFunOpt> --%>
		   </c:if>
		   <c:if test="${postStatus ne '3'}">
			   <t:dgDefOpt title="编辑" url="tPosterController.do?goUpdate&id={id}"/>
	   		   <t:dgDelOpt title="删除" url="tPosterController.do?doDel&id={id}" exp="postStatus#ne#3"/>
		   </c:if>
		   
		   <c:if test="${postStatus ne '1'}">
	   		   <t:dgFunOpt funname="copyUrl(id)" title="复制链接"></t:dgFunOpt>
		   </c:if>
		   <t:dgToolBar title="查看" icon="icon-search" url=""  funname="goView1"></t:dgToolBar>
	  </t:datagrid>
  </div>
 </div>
 <input id="url" type="text" value="xx"/>
 
 <script type="text/javascript">
 gridname = "tPoster";
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tNewGoodsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tNewGoodsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function copyUrl(id){
		$("#url").val('${url}'+id);
		 var e=document.getElementById("url");
		alert("复制成功!");
//	 	 alert(e.value);
		e.select(); //选择对象 
	    document.execCommand("Copy"); //执行浏览器复制命令
}
 function goView1(){
	 var rowsData = $('#tPoster').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		window.open("tPosterController.do?viewPoster&id="+rowsData[0].id,"new");
 }
//批量下架
 function doBatchDown(title,url) {
     var ids = [];
     var rows = $("#tPoster").datagrid('getSelections');
     if (rows.length > 0) {
     	$.dialog.confirm('确定批量下架?', function(r) {
 		   if (r) {
 				for ( var i = 0; i < rows.length; i++) {
 					ids.push(rows[i].id);
 				}
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
 							$("#tPoster").datagrid('unselectAll');
 							ids='';
 						}
 					}
 				});
 			}
 		});
 	} else {
 		tip("请选择海报");
 	}
 }
 
//置顶
 function doToTop(id){
 	 var url = "tPosterController.do?doToTop&id="+id;
 	 $.ajax({
 			async : false,
 			cache : false,
 			type : 'POST',
 			url : url,// 请求的action路径
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
 
//上移
 function doToUper(id,sortNum){
 	changeSort(id,sortNum,"U");
 }
 //下移
 function doToDown(id,sortNum){
 	changeSort(id,sortNum,"D");
 }
 //改变排序
 function changeSort(id,sortNum,type){
 	var url = "tPosterController.do?doChangeSort";
 	 $.ajax({
 			async : false,
 			cache : false,
 			type : 'POST',
 			data : {
 				id : id,
 				type : type,
 				sortNum : sortNum,
 				postStatus : "${postStatus}"
 			},
 			url : url,// 请求的action路径
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
 
	//查看详情
	function goView(title,url){//两个参数：标题和url
		window.open(url,"new");
	}
	//推送信息
	function doSend(id){
			window.open("tSmsSendInfoController.do?goAdd&pushId="+id+"&pushType="+0,"new");
	}
 </script>