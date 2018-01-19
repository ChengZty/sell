<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="cmsCategoryIndex" class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
		<t:datagrid name="cmsCategory" title="内容管理" actionUrl="cmsCustom.do?cmsGrid" idField="id" treegrid="true" pagination="false" openFirstNode="true">
			<t:dgCol title="内容ID" field="id" treefield="id" hidden="true"></t:dgCol>
			<t:dgCol title="分类名称" field="name" treefield="text"></t:dgCol>
			<t:dgCol title="内容分类" field="categoryType" treefield="remark" dictionary="category_type"  hidden="true"></t:dgCol>
			<t:dgCol title="显示顺序" field="sortOrder" treefield="order"></t:dgCol>
			<t:dgCol title="是否显示" field="isShow" treefield="isShow" replace="显示_true,隐藏_false"></t:dgCol>
			<t:dgCol title="子标题" field="subTitle" treefield="subTitle"></t:dgCol>
			<t:dgCol title="操作" field="opt"></t:dgCol>
			<t:dgFunOpt funname="changeShow(id,isShow)"    title="显示"  exp="isShow#eq#false"></t:dgFunOpt>
			<t:dgFunOpt  funname="changeShow(id,isShow)"     title="隐藏"  exp="isShow#eq#true"></t:dgFunOpt>
<%-- 			<t:dgDelOpt url="cmsCategory.do?del&id={id}" title="设置 " ></t:dgDelOpt> --%>
			<t:dgFunOpt funname="goToContentList(id,remark,text)" title="设置内容"  exp="remark#ne#ML&&remark#ne#GNZ"></t:dgFunOpt>
			<t:dgFunOpt funname="updateSubTitleFun(id,remark)" title="设置子标题"  exp="remark#eq#SP"></t:dgFunOpt>
			<t:dgFunOpt funname="goToContentNewsList(id,remark,text)" title="设置话题"  exp="remark#eq#SP"></t:dgFunOpt>
			<t:dgToolBar title="修改顺序"   icon="icon-edit" url="cmsCustom.do?goUpdateOrder" funname="updateFun"  ></t:dgToolBar>
		</t:datagrid>
</div>
</div>
 <div region="south"  split="true" style="height: 300px">
	<div tools="#tt" class="easyui-panel" title='查看设置内容' style="padding: 10px;" fit="true" border="false" id="function-panel-home"></div>
</div>
<!-- 	<div id="formContent" region="south" style="resize:vertical;overflow:auto;height: 400px" > -->
<!-- 		<iframe id="formContenFrame" width="98%" style="resize:vertical;height: 100%" scrolling="no" ></iframe> -->
<!-- 	</div> -->

<script type="text/javascript">
$(function() {
	var li_east = 0;
});

// function goToContentList(id,categoryType,name) {
// 	if(categoryType=="GNZ"){
// 		alertTip("该栏目无需设置");
// 		return;
// 	}
// 	$("#formContenFrame").attr("src","cmsCustom.do?contentList&categoryType="+categoryType+"&contentId="+id);
// }

//设置内容
 function goToContentList(id,categoryType,name) {
	 if(categoryType=="GNZ"){
			alertTip("该栏目无需设置");
			return;
		}
	$("#function-panel-home").panel(
		{
			title : '栏目名称:' + name,
			href:"cmsCustom.do?contentList&categoryType="+categoryType+"&contentId="+id
		}
	);
} 

//设置商品栏目的资讯
 function goToContentNewsList(id,categoryType,name) {
	$("#function-panel-home").panel(
		{
			title : '栏目名称:' + name,
			href:"cmsCustom.do?contentNewsList&contentId="+id
		}
	);
// 	$('#function-panel').panel("refresh" );
} 

/**
 * 修改顺序
 */
function updateFun(title,url, id) {
	var rowData = $('#'+id).datagrid('getSelected');
	if (rowData) {
			url += '&id='+rowData.id;
			add(title,url,'cmsCategory',"300px","80px");
	}else{
		tip("请选择有修改的内容");
		return  false;
	}
}
/**
 * 修改子标题
 */
function updateSubTitleFun(id,categoryType) {
	 if(categoryType!="SP"){
			alertTip("该栏目无需设置");
			return;
	}else{
		var url = 'cmsCustom.do?goUpdateSubTitle';
		url += '&id='+id;
		add("修改子标题",url,'cmsCategory',"500px","100px");
	}
}
/**
 * 是否显示该栏目
 */
function  changeShow(id,isShow){
	var content = "是否确认要";
	//反转是否显示
	if(isShow == 'true'){
		content += "隐藏";
		isShow = false;
	}else{
		content += "显示";
		isShow = true;
	}
	var url = "cmsCustom.do?show&id="+id+"&isShow="+isShow;
	var name = "确认";
	$.dialog.confirm(content, function(){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			data : [],
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				var msg = d.msg;
				if (d.success) {
					tip(msg);
					window.location.reload();
					//$('#cmsCategory').datagrid("reload",{});
				}else{
					tip(msg);
				}
			}
		});
		rowid = '';
	}, function(){
	}).zindex();
}
</script>

