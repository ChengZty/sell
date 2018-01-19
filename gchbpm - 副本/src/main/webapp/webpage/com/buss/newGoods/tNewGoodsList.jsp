<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsController.do?datagrid&goods_status=${goods_status }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="80"  queryMode="single" frozenColumn="true" width="80"></t:dgCol>
    <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"   queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="现价"  field="currentPrice" hidden="true"   queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgCol title="活动价"  field="activityPrice"    queryMode="single"  width="80"></t:dgCol> --%>
   <t:dgCol title="最低价"  field="lowestPrice"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="库存"  field="goodsStock"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="排序"  field="sortNum" hidden="true" queryMode="single"  width="100"></t:dgCol>
   <c:if test="${goods_status =='4' }">
   <t:dgCol title="操作" field="opt" width="180" align="center" ></t:dgCol>
   <t:dgFunOpt funname="doToTop(id)"  title="置顶"></t:dgFunOpt>
   <t:dgFunOpt funname="doToUper(id,sortNum)" title="<img src='plug-in/easyui/themes/default/images/accordion_collapse.png'/>" 
   urlStyle="position: relative; top: 4px;"></t:dgFunOpt>
   <t:dgFunOpt funname="doToDown(id,sortNum)"  title="<img src='plug-in/easyui/themes/default/images/accordion_expand.png'/>" 
	urlStyle="position: relative; top: 4px;"></t:dgFunOpt>
   <t:dgConfOpt title="下架" url="tNewGoodsController.do?doDown&id={id}" message="确认下架该商品？" exp="goodsStatus#eq#4" urlStyle="color:red" />
   <t:dgToolBar title="批量下架" icon="icon-edit" url="tNewGoodsController.do?doBatchDown" funname="doBatchDown"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 gridname = "tNewGoodsList";
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tNewGoodsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tNewGoodsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function goView(){
	 var rowsData = $('#tNewGoodsList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		window.open("tNewGoodsController.do?goView&id="+rowsData[0].id,"new");
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
//批量下架
function doBatchDown(title,url) {
    var ids = [];
    var rows = $("#tNewGoodsList").datagrid('getSelections');
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
							$("#tNewGoodsList").datagrid('unselectAll');
							ids='';
						}
					}
				});
			}
		});
	} else {
		tip("请选择商品");
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