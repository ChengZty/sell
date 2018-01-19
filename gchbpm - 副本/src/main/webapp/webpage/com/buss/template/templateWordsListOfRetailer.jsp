<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
		<div region="center" style="padding:1px;">
			<t:datagrid name="templateWordsList" checkbox="true" extendParams="nowrap:false," fitColumns="false" title="零售商话术模板" actionUrl="templateWordsController.do?datagrid&tp=2" 
			idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc">
			<t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="更新时间"  field="updateDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="一级分类"  field="topTypeId" dictionary="t_template_type,id,name,status='A' and level='1' and template_type='1'"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="二级分类"  field="subName"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="三级分类"  field="thridTypeId" dictionary="t_template_type,id,name,status='A' and level='3' and template_type='1'"  queryMode="single"  width="120"></t:dgCol>
	   		<t:dgCol title="关联行业"  field="tradeName" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="话术类型"  field="type" replace="图片话术_2,文字话术_1" queryMode="single" query="true" width="100"></t:dgCol>
			<t:dgCol title="内容"  field="content"  query="true" formatterjs="checkPicShow"  queryMode="single"  width="300"></t:dgCol>
			<t:dgCol title="平台类别"  field="platformType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
			<c:if test="${userType == '01' }">
				<t:dgCol title="零售商"  field="retailerId"  dictionary="t_s_user,id,realname,status = 'A' and user_type='02'"  queryMode="single"  width="120"></t:dgCol>
			</c:if>
			
			<t:dgCol title="操作" field="opt" width="180"></t:dgCol>
			<t:dgDelOpt title="删除" url="tCustWordsController.do?doDel&id={id}" />
			<%-- <c:if test="${userType != '01' }">
				<t:dgFunOpt funname="doShow(id,subTypeId)" title="设置可见" exp="isShow#eq#N"></t:dgFunOpt>
				<t:dgFunOpt funname="doHide(id)" title="设置不可见" exp="isShow#eq#Y"></t:dgFunOpt>
			</c:if> --%>
			
			<c:if test="${userType!='01' }">
				<t:dgFunOpt exp="topTypeId#eq#101" funname="getGoodsWordsList(id)" title="关联商品" ></t:dgFunOpt>
			</c:if>
   			
			<c:if test="${userType != '01' }">
				<t:dgToolBar title="录入" icon="icon-add" url="templateWordsController.do?goAdd" funname="add"></t:dgToolBar>
				<t:dgToolBar title="编辑" icon="icon-edit" url="templateWordsController.do?goUpdate" funname="update"></t:dgToolBar>
				<t:dgToolBar title="批量删除"  icon="icon-remove" url="templateWordsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
				<t:dgToolBar title="一键复制g+顾客话术" icon="icon-add" url="templateWordsController.do?doCopyPlatformWords" funname="doCopyPlatformWords" ></t:dgToolBar>
				<t:dgToolBar title="一键删除g+顾客话术" icon="icon-remove" url="templateWordsController.do?doDelPlatformWords" funname="doDelPlatformWords" ></t:dgToolBar>
			</c:if>
			<t:dgToolBar title="查看" icon="icon-search" url="templateWordsController.do?goUpdate" funname="detail"></t:dgToolBar>
			</t:datagrid>
		</div>
	</div>

	<div id="tempSearchColums" style="display: none">
	    <div name="searchColums">
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="一级分类">
	                	一级分类：
	            </span>
	            <select id="topTypeId" name="topTypeId" onchange="getSubList(this.value)"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">---所有---</option>
						 <c:forEach var="obj" items="${topTypeList}" >
								<option value="${obj.id}">${obj.name}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="二级分类">
	                	二级分类：
	            </span>
	            <select id="subTypeId" name="subTypeId" onchange="getSubList(this.value)" WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
				</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="三级分类">
	                	三级分类：
	            </span>
	            <select id="thridTypeId" name="thridTypeId"  WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
				</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="关联行业">
	                	关联行业：
	            </span>
	            <select id="templateTrade" name="templateTrade"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">---所有---</option>
						 <c:forEach var="obj" items="${templateTrade}" >
								<option value="${obj.id}">${obj.name}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	    </div>
	</div>
<c:if test="${userType!='01' }"> 
<div data-options="region:'east',
	title:'关联商品',
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
</c:if>
<script src = "webpage/com/buss/template/templateWords.js?v=1.13"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#templateWordsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
});
 
//话术
function getGoodsWordsList(id) {
	if(li_east == 0){
		   $('#main_depart_list').layout('expand','east'); 
	}
	$("div.layout-panel-east div.panel-title").text("关联的商品");
	$('#function-panel').panel("refresh","templateWordsController.do?goodsWordsList&wordsId="+id);
	}


//一键复制g+顾客话术
function doCopyPlatformWords(title,url,gname){
	gridname = gname;
	$.messager.confirm('提示信息', "确认复制全部g+顾客话术？", function(r){
		  if (r) {
				$.ajax({
					url : url,
					type : 'post',
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
						}
					}
				});
			}
	  })
}

//一键删除g+顾客话术
function doDelPlatformWords(title,url,gname){
	gridname = gname;
	$.messager.confirm('提示信息', "确认删除全部g+顾客话术？", function(r){
		  if (r) {
				$.ajax({
					url : url,
					type : 'post',
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
						}
					}
				});
			}
	  })
}

/*  
//设置可见
function doShow(id,subTypeId){
 var url = "tCustWordsController.do?doShow";
 $.ajax({
		type : 'POST',
		url : url,// 请求的action路径
		data : {
			id : id,
			subTypeId : subTypeId
		},
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

//设置不可见
function doHide(id){
 var url = "tCustWordsController.do?doShow";
 $.ajax({
		type : 'POST',
		url : url,// 请求的action路径
		data : {
			id : id
		},
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
*/
</script>