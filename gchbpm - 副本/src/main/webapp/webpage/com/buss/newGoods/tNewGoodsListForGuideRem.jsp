<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="false" fitColumns="false" title="商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsController.do?datagridForGuideRem"
  extendParams="nowrap:false,"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="80,80"  width="80"></t:dgCol>
<%--     <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100" ></t:dgCol> --%>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="零售商Id"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <c:if test="${userType =='01' }">
   <t:dgCol title="零售商"  field="retailerName"  queryMode="single"  width="90"></t:dgCol>
   </c:if>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="是否有导购点评"  field="hasRecmmend"  replace="是_1,否_0" query="true"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="getGuideRecommendList(id,retailerId,goodsCode)" title="查看导购点评" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()"  funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>

<div data-options="region:'east',
	title:'查看导购点评',
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
 <script type="text/javascript">
 
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
// 	 window.location="tGoodsController.do?goView&id="+rowsData[0].id+"&goods_status=${goods_status }";
 }
 
 function getGuideRecommendList(id,retailerId,goodsCode) {
	 if(li_east == 0){
	 			   $('#main_depart_list').layout('expand','east'); 
 		}
 		$("div.layout-panel-east div.panel-title").text(goodsCode);
 		$('#function-panel').panel("refresh","tGuideRecommendInfoController.do?list&goods_Id="+id+"&retailer_Id="+retailerId);
	}
 </script>