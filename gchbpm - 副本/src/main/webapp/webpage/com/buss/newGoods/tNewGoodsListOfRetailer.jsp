<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tNewGoodsList" checkbox="true" fitColumns="false" extendParams="nowrap:false,"  title="商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tNewGoodsController.do?datagrid&goods_status=${goods_status }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="80"  queryMode="single" frozenColumn="true"  width="80"></t:dgCol>
<%--    <t:dgCol title="短标题"  field="title" query="true" queryMode="single"  width="100" funname="doChangeProperty" url="tNewGoodsController.do?doChangeProperty&id={id}&field=title"></t:dgCol> --%>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="200" funname="doChangeProperty" url="tNewGoodsController.do?doChangeProperty&id={id}&field=goods_name"></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="原价"  field="originalPrice"  queryMode="single"  width="80" funname="doChangePrice" url="tNewGoodsController.do?doChangePrice&id={id}&goodsCode={goodsCode}&field=original_price&prePrice={originalPrice}"></t:dgCol>
<%--    <t:dgCol title="现价"  field="currentPrice" queryMode="single"  width="80" funname="doChangePrice" url="tNewGoodsController.do?doChangePrice&id={id}&goodsCode={goodsCode}&field=current_price&prePrice={currentPrice}"></t:dgCol> --%>
   <t:dgCol title="现价"  field="currentPrice" hidden="true" queryMode="single"  width="80" ></t:dgCol>
<%--    <t:dgCol title="活动价"  field="activityPrice"    queryMode="single"  width="80" funname="doChangePrice" url="tNewGoodsController.do?doChangePrice&id={id}&goodsCode={goodsCode}&field=activity_price&prePrice={activityPrice}"></t:dgCol> --%>
	<t:dgCol title="最低价折扣"  field="lowestPriceDiscount"    queryMode="single"  width="60" ></t:dgCol>
   <t:dgCol title="最低价"  field="lowestPrice"    queryMode="single"  width="80"  funname="doChangePrice" url="tNewGoodsController.do?doChangePrice&id={id}&goodsCode={goodsCode}&field=lowest_price&prePrice={lowestPrice}"></t:dgCol>
   <t:dgCol title="销量"  field="salesVolume"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="库存"  field="goodsStock"     queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作时间"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="排序"  field="sortNum" hidden="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="250" align="center" ></t:dgCol>
   <c:if test="${goods_status =='4' }">
   <t:dgConfOpt title="下架" url="tNewGoodsController.do?doDown&id={id}" message="确认下架该商品？" urlStyle="color:red" exp="goodsStatus#eq#4"/>
   <t:dgFunOpt funname="doToTop(id)"  title="置顶"></t:dgFunOpt>
   <t:dgFunOpt funname="doToUper(id,sortNum)" title="<img src='plug-in/easyui/themes/default/images/accordion_collapse.png'/>" 
   urlStyle="position: relative; top: 4px;" ></t:dgFunOpt>
   <t:dgFunOpt funname="doToDown(id,sortNum)"  title="<img src='plug-in/easyui/themes/default/images/accordion_expand.png'/>" 
	urlStyle="position: relative; top: 4px;" ></t:dgFunOpt>
   <t:dgToolBar title="批量下架" icon="icon-edit" url="tNewGoodsController.do?doBatchDown" funname="doBatchDown"></t:dgToolBar>
   </c:if>
<%--    <t:dgToolBar title="批量修改原价" icon="icon-edit" funname="goBatchChangeOrigPrice"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量修改最低价" icon="icon-edit"  funname="goBatchChangeLowestPrice"></t:dgToolBar> --%>
   <t:dgDefOpt title="编辑" url="tNewGoodsController.do?goUpdate&id={id}"/>
   <t:dgDelOpt title="删除" url="tNewGoodsController.do?doDel&id={id}" exp="goodsStatus#ne#4"/>
   <c:if test="${edition =='1' }">
   <t:dgFunOpt funname="doSend(id)"  title="推送"></t:dgFunOpt>
   </c:if>
   <c:if test="${goods_status =='0' }">
   <t:dgToolBar title="批量上架" icon="icon-edit" url="tNewGoodsController.do?doBatchPublish" funname="doBatchPublish"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <!-- <div id="price_div" style="position: relative;top:200px;display: none;">
 	<div style="width: 320px;z-index: 2000;margin: auto;background: gray;padding: 20px;">
 		<div><span id="price_title">批量修改原价</span><span style="color: red;">(价格或者打折后的价格为四舍五入的整数)</span></div>
 		<input type="hidden" id="priceField" value="1"/>默认修改原价
 		<table style="width: 100%">
 			<tr>
 				<td width="80px">改价方式</td>
 				<td>
 					<input type="radio" name="priceType" value="1" checked="checked"/>统一调价&nbsp;&nbsp;
 					<input type="radio" name="priceType" value="2" />折扣调价
 				</td>
 			</tr>
 			<tr>
 				<td>价格/折扣</td>
 				<td>
 					<input type="text" id="price" placeholder="输入折扣时不能大于10"/>
 				</td>
 			</tr>
 			<tr>
 				<td colspan="2" align="center">
 					<button onclick="confirmDiv()" >确定</button>  
 					<button onclick="closeDiv()" >取消</button>
 				<td>
 			</tr>
 		</table>
 	</div>
 </div> -->
 <script src = "webpage/com/buss/newGoods/tNewGoodsList.js?v=1.06"></script>
 <script type="text/javascript">
 gridname = "tNewGoodsList";
 
 </script>