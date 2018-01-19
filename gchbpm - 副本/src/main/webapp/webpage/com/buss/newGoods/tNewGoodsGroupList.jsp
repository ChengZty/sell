<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsGroupList" checkbox="true" extendParams="nowrap:false," fitColumns="false" title="商品组合列表" actionUrl="tNewGoodsGroupController.do?datagrid&goods_status=${goods_status }"
   idField="id" fit="true" queryMode="group" sortName="sortNum" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"    queryMode="single"  ></t:dgCol>
<%--    <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100"></t:dgCol> --%>
   <t:dgCol title="商品名称"  field="goodsName"   query="true" queryMode="single"  width="200"></t:dgCol>
<%--    <t:dgCol title="品牌"  field="brandName"    queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="零售商"  field="retailerName"  query="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="原价"  field="originalPrice"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="组合价"  field="groupPrice"    queryMode="single"  width="60"></t:dgCol> --%>
<%--    <t:dgCol title="活动价"  field="activityPrice"    queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="状态"  field="goodsStatus"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="组合来源"  field="groupSource"  replace="零售商_1,g+_2" queryMode="single"  width="80"></t:dgCol> --%>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="上架时间"  field="goodsUpdateTime"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="组合类型"  field="goodsType"  replace="组合_2,搭配_3" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" align="center" ></t:dgCol>
   <c:if test="${goods_status =='4' }">
   <t:dgFunOpt funname="doToTop(id)"  title="置顶"></t:dgFunOpt>
   <t:dgConfOpt title="下架" url="tNewGoodsGroupController.do?doDown&id={id}" message="确认下架该商品？" exp="goodsStatus#eq#4"/>
   <t:dgToolBar title="批量下架" icon="icon-edit" url="tNewGoodsController.do?doBatchDown" funname="doBatchDown"></t:dgToolBar>
   </c:if>
   <t:dgDefOpt title="编辑" url="tNewGoodsGroupController.do?goUpdate&id={id}" exp="groupSource#eq#2"/>
   <c:if test="${goods_status !='4' }">
   <t:dgDelOpt title="删除" url="tNewGoodsGroupController.do?doDel&id={id}" exp="groupSource#eq#2"/>
   </c:if>
<%--    <t:dgFunOpt funname="goUpdateActivityPrice(id)" title="修改活动价" exp="groupSource#eq#2"></t:dgFunOpt> --%>
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
 
 //修改活动价
 function goUpdateActivityPrice(id) {
 	 var url = "tNewGoodsController.do?goUpdateActivityPrice&id="+id;
 	createwindow("修改活动价", url,400,180);
 }
//置顶
 function doToTop(id){
 	 var url = "tGoodsController.do?doToTop&id="+id;
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
 </script>