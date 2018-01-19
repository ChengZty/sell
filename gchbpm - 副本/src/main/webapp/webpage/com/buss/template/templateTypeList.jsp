<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="templateTypeList" title="模板分类管理"  actionUrl="tTemplateTypeController.do?datagrid" idField="id" treegrid="true" pagination="false" fitColumns="false">
			<t:dgCol title="id" field="id" hidden="true" treefield="id"  width="60"></t:dgCol>
			<t:dgCol title="code" field="code" hidden="true" treefield="code"></t:dgCol>
			<t:dgCol title="模板分类名称" field="name" treefield="text" width="300"></t:dgCol>
			<t:dgCol title="模板类型" field="templateType" hidden="true" treefield="remark" width="80"></t:dgCol>
   			<t:dgCol title="层级"  field="level"  treefield="src" hidden="true" width="120"></t:dgCol>
   			<t:dgCol title="父id"  field="parentId"  treefield="parentId" hidden="true" width="120"></t:dgCol>
   			<%-- <t:dgCol title="是否有图片"  field="hasPics" hidden="true" treefield="remark" width="120"></t:dgCol> --%>
<%-- 			<t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%-- 			<t:dgDelOpt title="删除" url="tTemplateTypeController.do?doDel&id={id}" exp="src#eq#3"/> --%>
   			<t:dgToolBar title="录入" icon="icon-add" url="tTemplateTypeController.do?goAdd" funname="addFun" height="300" width="600"></t:dgToolBar>
   			<t:dgToolBar title="编辑" icon="icon-edit" url="tTemplateTypeController.do?goUpdate" funname="updateFun" height="300" width="600"></t:dgToolBar>
   			<t:dgToolBar title="删除" icon="icon-remove" url="tTemplateTypeController.do?doDel" funname="delFun" ></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
function updateFun(title,url, gname,width,height) {
		gridname=gname;
		var rowData = $('#'+gname).datagrid('getSelected');
		if (rowData!=null) {
			if(rowData.src==0){
				tip("暂不支持添加下级分类");
				return;
			}
			if(rowData.src==1){
				if(rowData.parentId=="200"){//-1：话题分类模版
					if(rowData.id=='6001'||rowData.id=='6002'){//管家课堂,管家故事
						tip("暂不支持编辑此级分类");
						return;
					}
				}else{
					tip("暂不支持编辑此级分类");
					return;
				}
			}
			if(rowData.src==2){//101：商品话术
				tip("暂不支持编辑此级分类");
				return;
			}
			if(rowData.src==3&&(rowData.parentId=="-1"||rowData.parentId=="-7")){//-1：售后回访,-7：活动推广话术
				tip("暂不支持编辑此级分类");
				return;
			}
			url += '&id='+rowData.id;
		}else{
			tip("请选择编辑项目");
			return;
		}
// 		url+="&hasPics="+rowData.remark;
		createwindow(title,url,width,height);
	}


function addFun(title,url, gname,width,height) {
	 	gridname=gname;
		var rowData = $('#'+gname).datagrid('getSelected');
		if (rowData!=null) {
			if(rowData.src==0){
				tip("暂不支持添加下级分类");
				return;
			}
			if(rowData.src==1){//101：商品话术
				tip("暂不支持添加下级分类");
				return;
			}
			if(rowData.src==2){
				if(rowData.id=="-1"||rowData.id=="-7"){//-1：售后回访,-7：活动推广话术,101：商品话术
					tip("暂不支持添加下级分类");
					return;
				}
			}
			if(rowData.src==3){
				tip("暂不支持添加四级分类");
				return;
			}
			url += '&pid='+rowData.id;
		}else{
			tip("请选择分类");
			return;
		}
// 		url+="&hasPics="+rowData.remark;
		if(rowData.parentId=="101"){
			url+="&tradeId=101";
		}
		add(title,url,'templateTypeList',width,height);
	}

function delFun(title,url, gname,width,height) {
 	gridname=gname;
	var rowData = $('#'+gname).datagrid('getSelected');
	if (rowData!=null) {
		if(rowData.src<2||(rowData.src==2&&rowData.id.length<10)){//101：商品话术
			tip("暂不支持删除该分类");
			return;
		}
		if(rowData.src==3&&rowData.parentId=="-1"){//售后回访
			tip("暂不支持删除该分类");
			return;
		}
		$.dialog.confirm('你确定删除该分类吗?', function(r) {
		   if (r) {
				$.ajax({
					url : url,
					type : 'post',
					data : {
						id : rowData.id
					},
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
		});
// 		url += '&id='+rowData.id;
	}else{
		tip("请选择分类");
		return;
	}
// 	url+="&hasPics="+rowData.remark;
// 	add(title,url,'templateTypeList',width,height);
}
</script>
