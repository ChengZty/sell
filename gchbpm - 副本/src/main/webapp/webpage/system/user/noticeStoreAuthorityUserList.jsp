<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="noticeAuthorityUserList" checkbox="false" fitColumns="false" title="导购授权" actionUrl="noticeStoreAuthorityUserController.do?datagrid&notice_Id=${noticeId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="通告ID"  field="noticeId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="用户名"  field="user.userName"  hidden="false"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="真实姓名"  field="user.realName"  hidden="false"  queryMode="group"  width="100"></t:dgCol>
   <c:if test="${isSend !='Y' }">
	   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	   <t:dgDelOpt title="删除" url="noticeStoreAuthorityUserController.do?doDel&id={id}" />
	   <t:dgToolBar title="添加指定导购" icon="icon-add" url="noticeStoreAuthorityUserController.do?doSave" funname="addAuthorityUser" ></t:dgToolBar>
	   <t:dgToolBar title="按店铺添加导购" icon="icon-add" url="noticeStoreAuthorityUserController.do?doSave" funname="addAuthorityUserByStore"></t:dgToolBar>
   </c:if>
  </t:datagrid>
   <input type="hidden" id="pNoticeId" value="${noticeId}" />
<script>


//添加指定导购
function addAuthorityUser(title,url, id){
		$.dialog({
			width:600,
			height:600,
	        id: 'LHG1980D',
	        title: "选择消息授权导购",
	        max: false,
	        min: false,
	        resize: false,
	        content: 'url:noticeStoreAuthorityUserController.do?selectUser&noticeId=${noticeId}',
	        lock:true,
	        ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	var userIds = iframe.getUserIds();
		    	var noticeId = $("#pNoticeId").val();
		    	if(userIds==""){
		    		return false;
		    	}else{
					url += '&userIds='+userIds;
					url += '&noticeId='+noticeId;
					doAjax(url);
		    	}
		    },
	        close: function(){
	        }
	    });
}

//保存授权用户
function doAjax(url) {
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
				$('#noticeAuthorityUserList').datagrid('reload',{});
			}		
		}
	});
}

//按店铺添加导购 选择店铺
function addAuthorityUserByStore(title, url) {
	//选择店铺
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:tStoreController.do?storeList',
			lock : true,
			//zIndex:1990,
			width:700,
			height:500,
			title:"实体店列表",
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	var tStoreId = iframe.getId();
		    	if(tStoreId!=""&&tStoreId!=undefined){
		    		return getSelectStoreGuides(url,tStoreId);
		    	}
		    	return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:tStoreController.do?storeList',
			lock : true,
			width:700,
			height:500,
			parent:windowapi,
			title:"实体店列表",
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	var tStoreId = iframe.getId();
		    	if(tStoreId!=""&&tStoreId!=undefined){
		    		return getSelectStoreGuides(url,tStoreId);
		    	}
		    	return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
	
	
}

//保存选中店铺对应的所有导购
function getSelectStoreGuides(url,tStoreId){
	if(tStoreId!=""&&tStoreId!=undefined){
			url += '&tStoreId='+tStoreId;
			url += '&noticeId=${noticeId}';
			doAjax(url);
		}
}
</script>

