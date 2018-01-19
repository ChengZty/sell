<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="guideMainEmelmentList" checkbox="true" fitColumns="false" title="主页分类图片" actionUrl="tGuideMainElementController.do?datagrid" 
			idField="id" fit="true" queryMode="group" sortName="updateDate" sortOrder="desc" pagination="false">
		 	<t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="组编码" field="groupCode" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="分类名称" field="elementTitle" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="元素编码" field="elementCode" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="用户ID" field="retailerId" hidden="true" queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="默认图片" field="pic" image="true" align="center" queryMode="single"  imageSize="130,80"  width="130"></t:dgCol>
			<c:if test="${userType =='02' }">
				<t:dgCol title="自定义图片" field="customPic" image="true" align="center" queryMode="single" imageSize="130,80" width="130"></t:dgCol>
			</c:if>
			<t:dgCol title="排序" field="orderNum"  queryMode="single" width="50" funname="doChangeOrderNum" url="tGuideMainElementController.do?doChangeOrderNum&id={id}"></t:dgCol>
			
		   	<t:dgCol title="操作" field="opt" width="160"></t:dgCol>
		  	<t:dgFunOpt title="上传图片" funname="goUpdate(id)" />
		   	<c:if test="${userType =='02' }">
				<t:dgDelOpt title="删除图片" url="tGuideMainElementController.do?doDel&id={id}" />
			</c:if>
	   		
   			<%-- <t:dgToolBar title="编辑" icon="icon-edit" url="tGuideMainElementController.do?goUpdate" funname="updateFun" height="500" width="600"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>


<script type="text/javascript">
function updateFun(title,url, gname,width,height) {
	gridname=gname;
	var rowData = $('#'+gname).datagrid('getSelected');
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	if (rowData!=null) {
		url+="&id="+rowData.id;
	}else{
		tip("请选择编辑项目");
		return;
	}
	createwindow(title,url,width,height);
}
//上传自定义图片
function goUpdate(id,idx){
	 var addurl = "tGuideMainElementController.do?goUpdate&id="+id;//上传自定义图片
	 createwindow("上传自定义图片", addurl,600,400);
}

function doChangeOrderNum(title,url){
	var val = prompt("请谨慎输入顺序,会在导购端的主页同步顺序！");
	if(val!=null){
		val = $.trim(val);
	 if(val!=""&&!isNaN(val)){
		 url +="&orderNum="+val;
		 $.ajax({
				url : url,
				type : 'post',
				data : {},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						reloadTable();
						$("#guideMainEmelmentList").datagrid('selectAll');
					}
				}
			});
	 }else{
		 tip("请填写正确的顺序");
		 return false;
	 }
	}
}

</script>