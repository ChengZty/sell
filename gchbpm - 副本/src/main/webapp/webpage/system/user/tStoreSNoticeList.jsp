<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSNoticeList" checkbox="true" fitColumns="false" title="消息列表" actionUrl="noticeStore.do?datagrid2" sortOrder="desc" sortName="createTime" idField="id" fit="true" queryMode="group">
   <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="noticeTitle"  queryMode="group"  width="250"></t:dgCol>
<%--    <t:dgCol title="通知公告内容"  field="noticeContent"  queryMode="group"  width="120"></t:dgCol> --%>
   <%--<t:dgCol title="类型名称"  field="noticeType"  hidden="false"  queryMode="group"  width="60" replace="通知_1,公告_2"></t:dgCol>--%>
   <t:dgCol title="授权级别"  field="noticeLevel"  hidden="false" query="true" queryMode="single"  width="100" replace="全体导购_1,指定导购_2"></t:dgCol>
   <t:dgCol title="是否已推送"  field="isSend"  hidden="false" query="true" queryMode="single"  width="100" replace="已推送_Y,待推送_N"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
   <t:dgDelOpt title="删除" url="noticeStore.do?doDel&id={id}" />
   <t:dgFunOpt exp="noticeLevel#eq#2" funname="queryUsers(id,isSend)" title="导购列表"></t:dgFunOpt>
   <t:dgFunOpt exp="isSend#eq#N" funname="doSend(id)" title="推送"></t:dgFunOpt>
   <t:dgFunOpt funname="doViewDetails(id,noticeLevel,noticeTitle)"  title="点击详情" exp="isSend#eq#Y"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="noticeStore.do?goAdd" funname="add" width="1000" height="600"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="noticeStore.do?goUpdate" funname="update" width="1000" height="600"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="noticeStore.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div data-options="region:'east',
	title:'导购点击报表详情',
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
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="subListpanel"></div>
</div>

 <script type="text/javascript">
 //导购列表
 function queryUsers(id,isSend){
		if(li_east == 0){
			   $('#main_depart_list').layout('expand','east'); 
		}
		$("div.layout-panel-east div.panel-title").text("导购消息授权管理");
		$('#subListpanel').panel("refresh", "noticeStoreAuthorityUserController.do?noticeAuthorityUser&noticeId=" + id+"&isSend="+isSend);
 }
 
 //导购点击报表详情
 function doViewDetails(id,noticeLevel,noticeTitle	){
		if(li_east == 0){
			   $('#main_depart_list').layout('expand','east'); 
		}
		$("div.layout-panel-east div.panel-title").text(noticeTitle);
		//alert(noticeLevel);
 		$('#subListpanel').panel("refresh", "guideCountController.do?noticeStoreList&noticeId=" + id+"&noticeLevel="+noticeLevel);
 }
 
 function doSend(id)
 {
	 $.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "noticeStore.do?doSend&id="+id,// 请求的action路径
			error : function() {
			},
			success : function(data) {
				var d = $.parseJSON(data);
				tip(d.msg);
				reloadTable();
			}
		});
 } 
 </script>
