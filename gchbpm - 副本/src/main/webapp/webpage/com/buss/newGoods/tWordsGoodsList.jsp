<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsDetailList" checkbox="false" fitColumns="true" title="商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsController.do?datagridForGoodsWords" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"  queryMode="single" frozenColumn="true" ></t:dgCol>
<%--    <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100" ></t:dgCol> --%>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="200" ></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <c:if test="${userType =='01' }">
   <t:dgCol title="零售商"  field="retailerName"  queryMode="single"  width="90"></t:dgCol>
   </c:if>
   <t:dgCol title="排序"  field="sortNum" hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="是否有话术"  field="hasWords"  replace="是_1,否_0" query="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="120" align="center" ></t:dgCol>
   <t:dgFunOpt funname="getFinActivityWordsList(id,goodsCode)" title="商品话术" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<div region="east" style="width: 400px;" split="true">
	<div tools="#tt" class="easyui-panel" title='商品话术' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
 <script type="text/javascript">
 gridname = "tNewGoodsList";

 
//话术
 function getFinActivityWordsList(id,goodsCode) {
		$("#function-panel").panel(
			{
				title : '商品款号:' + goodsCode,
				href:"tFinActivityWordsController.do?goodsWordsList&fin_act_Id="+id
			}
		);
	}
 function goView(){
	 var rowsData = $('#tNewGoodsDetailList').datagrid('getSelections');
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
 
 </script>