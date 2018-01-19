<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsGroupList" checkbox="true" extendParams="nowrap:false," fitColumns="false" title="商品组合列表" sortName="sortNum" sortOrder="desc" 
  actionUrl="tNewGoodsGroupController.do?datagrid&goods_status=${goods_status }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"    queryMode="single" ></t:dgCol>
<%--    <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100" funname="doChangeProperty" url="tNewGoodsController.do?doChangeProperty&id={id}&field=title"></t:dgCol> --%>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="200" funname="doChangeProperty" url="tNewGoodsController.do?doChangeProperty&id={id}&field=goods_name"></t:dgCol>
   <t:dgCol title="款号"  field="goodsCode"   hidden="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="二级类目"  field="subCategoryId"  dictionary="t_s_category,id,name,level = '2' and status = 'A' " queryMode="single"  width="100"></t:dgCol>
<%--    <t:dgCol title="品牌"  field="brandName"    queryMode="single"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="组合价"  field="groupPrice"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="活动价"  field="activityPrice"    queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="所属零售商"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="排序"  field="sortNum" hidden="true"  width="100"></t:dgCol>
<%--    <t:dgCol title="组合类型"  field="goodsType"  replace="组合_2,搭配_3" query="true" queryMode="single"  width="80"></t:dgCol> --%>
   <t:dgCol title="状态"  field="goodsStatus"  hidden="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>
   <c:if test="${goods_status =='4' }">
   <t:dgFunOpt funname="doToTop(id)"  title="置顶"></t:dgFunOpt>
   <t:dgConfOpt title="下架" url="tNewGoodsGroupController.do?doDown&id={id}" message="确认下架该商品？" exp="goodsStatus#eq#4"/>
   <t:dgToolBar title="批量下架" icon="icon-edit" url="tNewGoodsController.do?doBatchDown" funname="doBatchDown"></t:dgToolBar>
   </c:if>
   <t:dgDefOpt title="编辑" url="tNewGoodsGroupController.do?goUpdate&id={id}"/>
   <t:dgDelOpt title="删除" url="tNewGoodsGroupController.do?doDel&id={id}" exp="goodsStatus#ne#4"/>
   <c:if test="${goods_status =='0' }">
   <t:dgToolBar title="批量发布" icon="icon-edit" url="tNewGoodsController.do?doBatchPublish" funname="doBatchPublish"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url=""  funname="goView"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 gridname = "tNewGoodsGroupList";
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tNewGoodsGroupListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tNewGoodsGroupListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 
 function goView(){
	 var rowsData = $('#tNewGoodsGroupList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		window.open("tNewGoodsGroupController.do?goView&id="+rowsData[0].id,"new"); 
// 	 window.location="tNewGoodsGroupController.do?goView&id="+rowsData[0].id+"&goods_status=${goods_status }";
}
 
 function goUpdate(){
	 var rowsData = $('#tNewGoodsGroupList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
	 window.location="tNewGoodsGroupController.do?goUpdate&id="+rowsData[0].id;
 }
 
//置顶
 function doToTop(id){
 	 var url = "tNewGoodsController.do?doToTop&id="+id;
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
 
 function doBatchDown(title,url) {
	    var ids = [];
	    var rows = $("#tNewGoodsGroupList").datagrid('getSelections');
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
								$("#tNewGoodsGroupList").datagrid('unselectAll');
								ids='';
							}
						}
					});
				}
			});
		} else {
			tip("请选择组合");
		}
	}

	function doBatchPublish(title,url) {
	    var ids = [];
	    var rows = $("#tNewGoodsGroupList").datagrid('getSelections');
	    if (rows.length > 0) {
	    	$.dialog.confirm('确定批量发布?', function(r) {
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
								$("#tNewGoodsGroupList").datagrid('unselectAll');
								ids='';
							}
						}
					});
				}
			});
		} else {
			tip("请选择组合");
		}
	}
	
	//修改属性
	function doChangeProperty(title,url){
		var tipcontent = "请输入"+title;
		var limitLength = 14;
		if("商品名称"==title){
			limitLength = 20;
			tipcontent += "，20字以内(含)";
		}else if("短标题"==title){
			tipcontent += "，14字以内(含)";
		}
		var val = prompt(tipcontent,"");
		if(val!=null){
			val = $.trim(val);
			 if(val==""){
				 tip(title+"不能为空");
				 return false;
				 
			 }else if(val.length>limitLength){
				 tip(title+"长度不能超过"+limitLength+"字");
				 return false;
			 }else{
				 url +="&val="+val;
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
								$("#tNewGoodsList").datagrid('unselectAll');
							}
						}
					});
			 }
		}
	}	
	
	//上移
	function doToUper(id,sortNum){
		changeSort(id,sortNum,"U");
	}
	//下移
	function doToDown(id,sortNum){
		changeSort(id,sortNum,"D");
		 
	}
	function changeSort(id,sortNum,type){
		var url = "tNewGoodsController.do?doChangeSort&type="+type+"&id="+id+"&sortNum="+sortNum;
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
 </script>