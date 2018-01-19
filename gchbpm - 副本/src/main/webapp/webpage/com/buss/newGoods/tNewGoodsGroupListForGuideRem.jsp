<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="false" fitColumns="false" title="组合列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsGroupController.do?datagrid"
  extendParams="nowrap:false,"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"  queryMode="single" ></t:dgCol>
   <t:dgCol title="组合名称"  field="goodsName" query="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="组合款号"  field="goodsCode" hidden="true" queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="80"></t:dgCol> --%>
   <t:dgCol title="二级类目"  field="subCategoryId"  dictionary="t_s_category,id,name,level = '2' and status = 'A' " queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="零售商Id"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <c:if test="${userType =='01' }">
   <t:dgCol title="零售商"  field="retailerName"  queryMode="single"  width="80"></t:dgCol>
   </c:if>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="getGuideRecommendList(id,retailerId,goodsCode)" title="查看管家点评" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()"  funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div region="east" style="width: 400px;" split="true">
	<div tools="#tt" class="easyui-panel" title='查看管家点评' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
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
		window.open("tNewGoodsGroupController.do?goView&id="+rowsData[0].id,"new"); 
 }
 
 function getGuideRecommendList(id,retailerId,goodsCode) {
		$("#function-panel").panel(
			{
				title : '组合款号:' + goodsCode,
				href:"tGuideRecommendInfoController.do?list&goods_Id="+id+"&retailer_Id="+retailerId
			}
		);
// 		$('#function-panel').panel("refresh" );
	}
 </script>