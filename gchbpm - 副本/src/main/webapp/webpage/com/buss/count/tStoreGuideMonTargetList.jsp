<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tStoreGuideMonTargetList" checkbox="true" fitColumns="false" title="导购月目标" sortName="targetMonth" sortOrder="desc"
  actionUrl="tStoreGuideMonTargetController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="月目标ID"  field="monTargetId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="月份"  field="targetMonth"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺名称"  field="storeId"  dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购userId"  field="guideId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="导购姓名"  field="guideName"  query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="目标金额"  field="targetMoney"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="线上销售金额"  field="onlineMoney"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="实体销售金额"  field="offlineMoney" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="达成率"  field="reachRate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt title="编辑" funname="goUpdate(id)" />
   <t:dgDelOpt title="删除" url="tStoreGuideMonTargetController.do?doDel&id={id}" />
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tStoreMonTargetController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="设置目标" icon="icon-edit" url="tStoreMonTargetController.do?goUpdate" funname="update"></t:dgToolBar> --%>
   <t:dgToolBar title="批量设置月目标" icon="icon-edit" funname="setTarget"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tStoreGuideMonTargetController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tStoreGuideMonTargetController.do?goUpdate" funname="detail" width="400" height="200"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  		var target_Month_query = '<span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">月份：</span>'
//  				+'<input type="text" name="target_Month" class="Wdate" style="height:24px;width:90px;" onclick="WdatePicker({dateFmt:\'yyyy-MM\'})"/>';
//  		$("#tStoreGuideMonTargetListForm").prepend(target_Month_query);
//  			$("#tStoreGuideMonTargetListtb").find("input[name='target_Month']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
 			$("#tStoreGuideMonTargetListtb").find("input[name='targetMonth']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
 });
/*  
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tStoreGuideMonTargetController.do?upload', "tStoreGuideMonTargetList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tStoreGuideMonTargetController.do?exportXls","tStoreGuideMonTargetList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tStoreGuideMonTargetController.do?exportXlsByT","tStoreGuideMonTargetList");
}
 */
 
//选择店铺
 function setTarget() {
 	 	var url = 'tStoreMonTargetController.do?goChooseStore';
 		var title = "选择店铺";
 		var width = 500;
 		var height = 300;
 		$.dialog.setting.zIndex = 300;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content:"url:"+url,
					lock : true,
					title:title,
					width:width,
					height: height,
					cache:false,
				    ok: function(){
				    	iframe_doc = this.iframe.contentWindow.document;
				    	var addurl = "tStoreMonTargetController.do?goUpdate";//设置导购月目标
				    	var storeId = $(iframe_doc).find("#storeId").val();
				      	var targetMonth = $(iframe_doc).find("#targetMonth").val();
				      	if(storeId==""){
				      		$(iframe_doc).find("#storeId").focus();
				    		return false;
				      	}
				      	if(targetMonth==""){
				      		$(iframe_doc).find("#targetMonth").focus();
				    		return false;
				      	}
				      	addurl+="&storeId="+storeId+"&targetMonth="+targetMonth;
				      	setGuideTargetList("设置月目标", addurl,500,600);
// 				      	createwindow("设置月目标", addurl,500,600);
				    },
				    cancelVal: '关闭',
				    cancel: true /*为true等价于function(){}*/
				}).zindex();
			} else{
				$.dialog({
					content:"url:"+url,
					lock : true,
					title:title,
					width:width,
					height: height,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe_doc = this.iframe.contentWindow.document;
				    	var addurl = "tStoreMonTargetController.do?goUpdate";//设置导购月目标
				    	var storeId = $(iframe_doc).find("#storeId").val();
				      	var targetMonth = $(iframe_doc).find("#targetMonth").val();
				      	if(storeId==""){
				      		$(iframe_doc).find("#storeId").focus();
				    		return false;
				      	}
				      	if(targetMonth==""){
				      		$(iframe_doc).find("#targetMonth").focus();
				    		return false;
				      	}
				      	addurl+="&storeId="+storeId+"&targetMonth="+targetMonth;
				      	setGuideTargetList("设置月目标", addurl,500,600);
// 					      	createwindow("设置月目标", addurl,500,600);
				    },
				    cancelVal: '关闭',
				    cancel: true 
				}).zindex();
			}
 		
 }
 
function setGuideTargetList(title,url,width,height){
	$.dialog.setting.zIndex = 600;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content:"url:"+url,
				lock : true,
				title:title,
				width:width,
				height: height,
				cache:false,
			    ok: function(){
			    	iframe_doc = this.iframe.contentWindow.document;
			    	var $form = $(iframe_doc).find("#formobj");
			    	var flag = true;//为true才能提交
			    	$form.find("tbody tr input[name$='targetMoney']").each(function(){
			    		var val = $(this).val();
			    		if(val==""){
			    			$(this).focus();
			    			flag = false;
			    			return false;
			    		}
			    	})
			    	if(flag){//列表全部导购填写月目标
// 				    	var l =$form.find("tbody tr input[name$='targetMoney']").length;
				    	var data = $(iframe_doc).find("#formobj").serialize();
// 				    	console.log(data);
				    	submitForm(data);//提交
			    	}else{
			    		return false;
			    	}
//			      	createwindow("设置月目标", addurl,500,600);
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content:"url:"+url,
				lock : true,
				title:title,
				width:width,
				height: height,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
				    	var $form = $(iframe_doc).find("#formobj");
				    	var flag = true;//为true才能提交
				    	$form.find("tbody tr input[name$='targetMoney']").each(function(){
				    		var val = $(this).val();
				    		if(val==""){
				    			$(this).focus();
				    			flag = false;
				    			return false;
				    		}
				    	})
				    	if(flag){//列表全部导购填写月目标
					    	var data = $(iframe_doc).find("#formobj").serialize();
					    	submitForm(data);//提交
				    	}else{
				    		return false;
				    	}
			    },
			    cancelVal: '关闭',
			    cancel: true 
			}).zindex();
		}
}
 
 //编辑导购月目标
 function goUpdate(id,idx){
	 var $selectTr = $("tr[datagrid-row-index='"+idx+"']");
	 var storeName = $selectTr.find("td[field='storeId'] div").text();//店铺名称
	 var targetMonth = $selectTr.find("td[field='targetMonth'] div").text();//月份
	 var addurl = "tStoreGuideMonTargetController.do?goUpdate&id="+id;//设置导购月目标
	 createwindow("设置月目标（"+storeName+"，"+targetMonth+"）", addurl,400,200);
 }
 
//提交导购月目标设置 
 function submitForm(data){
	 gridname="tStoreGuideMonTargetList";
	 var url = "tStoreMonTargetController.do?doUpdate";
		$.ajax({
			url : url,
			type : 'post',
			data : data,
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
					reloadTable();
				}
			}
		});
 }
 </script>