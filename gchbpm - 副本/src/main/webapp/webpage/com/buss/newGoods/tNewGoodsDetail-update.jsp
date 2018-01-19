<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品详情</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  
  function goback(){
	  window.location="tNewGoodsController.do?detailList";
	  return false;
  }

function backList(data) {
	 if(data.success){
		document.location="tNewGoodsController.do?detailList";
	}else{
		$.messager.alert('错误提示', data.msg);
	}
}

	
$(function () {
  //初始化运费
    initFare();
    $("#tb input,select,a").attr("disabled","disabled");
    
});

//初始化运费
function initFare(){
	//初始化运费
	var fareType = "${tGoodsPage.fareType}";//邮费类型
	var type = "${tGoodsPage.farePreferentialType}";//运费优惠类型
	$("#fare_td_1 input[type='radio'][name='fareType'][value="+fareType+"]").attr("checked",true);
	$("#fare_td_2 input[type='radio'][name='farePreferentialType'][value="+type+"]").attr("checked",true);
	if("0"==fareType){//包邮
		$("#fare_td_2 input").attr("disabled",true);
		$("#fare").val("");
		$("#fare_1").hide();
	}else if("1"==fareType){//定额
		$("#fare_td_2 input").attr("disabled",false);
		$("#fare_1").show();
		if("1"==type){//满免
			$("#fare_2").html('满<input type="text" name="goodsFarePreferential" style="width: 30px" value="${tGoodsPage.goodsFarePreferential}"/>元包邮');
		}else if("2"==type){//递减
			$("#fare_3").html('第一件<input type="text" id="fare_pft" style="width: 30px" value="${tGoodsPage.fare}" readonly="readonly"/>元，第二件开始每件<input type="text" name="goodsFarePreferential" style="width: 30px" value="${tGoodsPage.goodsFarePreferential}"/>元');
		}
	}
}

function save(){
	$("#formobj").submit();
}
  </script>
  
 
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tNewGoodsController.do?doUpdateDetail" tiptype="4" beforeSubmit="setContent" callback="backList">
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tGoodsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tGoodsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tGoodsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tGoodsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tGoodsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tGoodsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="topCategoryId" name="topCategoryId" type="hidden" value="${tGoodsPage.topCategoryId}">
					<input id="subCategoryId" name="subCategoryId" type="hidden" value="${tGoodsPage.subCategoryId}">
					<input id="thridCategoryId" name="thridCategoryId" type="hidden" value="${tGoodsPage.thridCategoryId}">
					<input id="retailerId" name="retailerId" type="hidden" value="${tGoodsPage.retailerId}">
					<input id="retailerCode" name="retailerCode" type="hidden" value="${tGoodsPage.retailerCode}">
					<input id="retailerName" name="retailerName" type="hidden" value="${tGoodsPage.retailerName}">
					<input id="retailerType" name="retailerType" type="hidden" value="${tGoodsPage.retailerType}">
					<input id="goodsType" name="goodsType" type="hidden" value="${tGoodsPage.goodsType}">
					<input id="goodsStatus" name="goodsStatus" type="hidden" value="${tGoodsPage.goodsStatus}">
					<input id="cityId" name="cityId" type="hidden" value="${tGoodsPage.cityId}">
					<input id="provinceId" name="provinceId" type="hidden" value="${tGoodsPage.provinceId}">
					<input id="brandId" name="brandId" type="hidden" value="${tGoodsPage.brandId}">
					<input id="goodsCollect" name="goodsCollect" type="hidden" value="${tGoodsPage.goodsCollect}">
					<input id="noSenseNum" name="noSenseNum" type="hidden" value="${tGoodsPage.noSenseNum}">
					<input id="goodNum" name="goodNum" type="hidden" value="${tGoodsPage.goodNum}">
					<input id="goodsStar" name="goodsStar" type="hidden" value="${tGoodsPage.goodsStar}">
					<input id="scoresNum" name="scoresNum" type="hidden" value="${tGoodsPage.scoresNum}">
					<input id="salesVolume" name="salesVolume" type="hidden" value="${tGoodsPage.salesVolume}">
					<input type="hidden" name="prePrice" value="${tGoodsPage.currentPrice}" /><!-- 用于记录之前的价格 -->
					<input type="hidden" name="preLowestPrice" value="${tGoodsPage.lowestPrice}" /><!-- 用于记录之前的最低价 -->
					<input type="hidden" name="preActivityPrice" value="${tGoodsPage.activityPrice}" /><!-- 用于记录之前的活动价格 -->
					<input id="categries" name="categries" type="hidden" value="${tGoodsPage.categries}"><!-- 可见类目 c2ID， c1ID循环的-->
					<input id="brandCode" name="brandCode" type="hidden" value="${tGoodsPage.brandCode}"><!-- 品牌编码 -->
					<input id="publishStatus" name="publishStatus" type="hidden" value="${tGoodsPage.publishStatus}"><!--1：立即发布，2：存草稿 -->
					<input id="isSpecial" name="isSpecial" type="hidden" value="N">
					<input id="newGoodsType" name="newGoodsType" type="hidden" value="2"><!-- 新商品模块 -->
					<input id="hasZhen" name="hasZhen" type="hidden" value="${tGoodsPage.hasZhen}"><!-- 是否有4真 -->
					<table id="tb" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
							<tr>
								<td colspan="6" height="40px" style="padding-left: 10px">
									<h2>基础资料</h2>
								</td>
							</tr>
							<tr>
								<td align="right" width="100">
									<label class="Validform_label">
										商品类目:
									</label>
								</td>
								<td class="value" colspan="3">
										<span>${topCategory} ${subCategory} ${thridCategory}</span>										
								</td>
								<td align="right" width="100">
									<label class="Validform_label">
										品牌:
									</label>
								</td>
								<td class="value">
									<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" value="${tGoodsPage.brandName}" datatype="*">
<!-- 									<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" id=""><span class="icon-search l-btn-icon-left">选择</span></a> -->
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">品牌</label>
								</td>	
							</tr>
							<tr>
							<td align="right" rowspan="5">
									<label class="Validform_label">
										商品小图:
									</label>
								</td>
									<td rowspan="5">
										<div>
										<div id="progress_bar_m_small" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
			        					</div>
			        					<div  id="main_pics_small" style="min-height: 100px;line-height: 0;">
			        					<c:if test="${not empty tGoodsPage.smallPic }">
			        						<div><img src='${tGoodsPage.smallPic }' width='100' height='100' ></img></div>
			        					</c:if>
			        					</div>
							     		<div><span class="Validform_checktip">商品小图450*450，100KB以内</span></div>
									</td>
								<td align="right">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value" width="400">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px"  value="${tGoodsPage.goodsCode}" maxlength="20"  datatype="z"  readonly="readonly">
										<span class="Validform_checktip">20位以内字母数字横杠下划线组合</span>
										<label class="Validform_label" style="display: none;">商品款号</label>
								</td>
								<td align="right">
									<label class="Validform_label">
										短标题:
									</label>
								</td>
								<td class="value">
								     	 <input id="title" name="title" type="text" style="width: 200px" value="${tGoodsPage.title}"  maxlength="11">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">短标题</label>
									</td>
								</tr>
								<tr>
								<td align="right">
									<label class="Validform_label">
										商品名称:
									</label>
								</td>
								<td class="value" colspan="3">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}" datatype="*"  maxlength="20">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">商品名称</label>
									</td>
									
								</tr>
							<tr>
							<td align="right">
									<label class="Validform_label">
										原价:
									</label>
									</td>
									<td class="value">
									     	 <input id="originalPrice" name="originalPrice" type="text" style="width: 150px" value="${tGoodsPage.originalPrice}"  onkeypress="Public.input.numberInput()"
									     	 onkeyup="fillCurrentPrice(this.value)" datatype="money">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">原价</label>
										</td>
								<td align="right">
									<label class="Validform_label">
										现价:
									</label>
								</td>
								<td class="value">
								     	 <input id="currentPrice" name="currentPrice" type="text" style="width: 150px" value="${tGoodsPage.currentPrice}" datatype="money" onkeypress="Public.input.numberInput()">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">现价</label>
									</td>
								
							</tr>
							<tr>
							<td align="right">
									<label class="Validform_label">
										最低价:
									</label>
								</td>
								<td class="value">
								     	 <input id="lowestPrice" name="lowestPrice" type="text" style="width: 150px" value="${tGoodsPage.lowestPrice}" datatype="money" onkeypress="Public.input.numberInput()">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">最低价</label>
									</td>
									<td align="right">
									<label class="Validform_label">
										活动价:
									</label>
								</td>
								<td class="value">
								     	 <input id="activityPrice" name="activityPrice" type="text" style="width: 150px" value="${tGoodsPage.activityPrice}" datatype="money" onkeypress="Public.input.numberInput()">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">活动价</label>
									</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品类别:
									</label>
								</td>
								<td class="value">
								<t:dictSelect field="subGoodsType" id="subGoodsType" type="list" typeGroupCode="subgdstype" defaultVal="${tGoodsPage.subGoodsType}" hasLabel="false"></t:dictSelect>
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">商品类别</label>
								</td>
								<td align="right">
									<label class="Validform_label">
										库存:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsStock" name="goodsStock" value="${tGoodsPage.goodsStock}" type="text" >
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">库存</label>
								</td>
							</tr>
							<tr height="36px">
								<td align="right">
									<label class="Validform_label">
										运费:
									</label>
								</td>
								<td class="value" id="fare_td_1">
									<label><input type="radio" value="0" name="fareType" onclick="changeFareType(this.value)"/>包邮</label>&nbsp;
									<label><input type="radio" value="1" name="fareType" onclick="changeFareType(this.value)"/>定额</label>
									&nbsp;&nbsp;
									<span id="fare_1" style="display: none">单价：<input type="text" id="fare" name="fare" value="${tGoodsPage.fare}" style="width: 30px" onkeypress="Public.input.numberInput()"/>元/件</span>
								</td>
								<td align="right">
									<label class="Validform_label">
										运费优惠:
									</label>
								</td>
								<td class="value" id="fare_td_2">
									<label><input type="radio" value="0" name="farePreferentialType" onclick="changeFarereferentialType(this.value)"/>无</label>&nbsp;
									<label><input type="radio" value="1" name="farePreferentialType" onclick="changeFarereferentialType(this.value)"/>满免</label>&nbsp;
									<label><input type="radio" value="2" name="farePreferentialType" onclick="changeFarereferentialType(this.value)"/>递减</label>
									&nbsp;&nbsp;
									<span id="fare_2" ></span>
									<span id="fare_3" ></span>
								</td>
								<td class="value" colspan="2">
								</td>
							</tr>
					</table>
					<table style="width: 99%;height: 200px" cellpadding="0" cellspacing="1" >
	                					<tr height="40px">
	                						<td align="center">
	                							<label class="Validform_label">
		                						真特色
		                						</label>
	                						</td>
	                						<td align="center">
	                							<label class="Validform_label">
		                						真用途
		                						</label>
	                						</td>
	                						<td align="center">
	                							<label class="Validform_label">
		                						真权威
		                						</label>
	                						</td>
	                						<td align="center">
	                							<label class="Validform_label">
		                						真服务
		                						</label>
	                						</td>
	                					</tr>
	                					<c:if test="${not empty descList }">
	                						<tr>
	                						<td>
		                						<input  name="descList[0].id" type="hidden" value='${descList[0].id }'>
		                						<input id="descList0" name="descList[0].goodsDesc" type="hidden" value='${descList[0].goodsDesc }'>
		                						<input  name="descList[0].type" type="hidden" value="1">
									     		<textarea name="descList0" id="content0" style="width: 100%">${descList[0].goodsDesc }</textarea>
	                						</td>
	                						<td>
	                							<input  name="descList[1].id" type="hidden" value='${descList[1].id }'>
		                						<input id="descList1" name="descList[1].goodsDesc" type="hidden" value='${descList[1].goodsDesc }'>
		                						<input  name="descList[1].type" type="hidden" value="2">
		                						<textarea name="descList1" id="content1" style="width: 100%">${descList[1].goodsDesc }</textarea>
	                						</td>
	                						<td>
	                							<input  name="descList[2].id" type="hidden" value='${descList[2].id }'>
		                						<input id="descList2" name="descList[2].goodsDesc" type="hidden" value='${descList[2].goodsDesc }'>
		                						<input  name="descList[2].type" type="hidden" value="3">
		                						<textarea name="descList2" id="content2" style="width: 100%">${descList[2].goodsDesc }</textarea>
	                						</td>
	                						<td>
	                							<input  name="descList[3].id" type="hidden" value='${descList[3].id }'>
		                						<input id="descList3" name="descList[3].goodsDesc" type="hidden" value='${descList[3].goodsDesc }'>
		                						<input  name="descList[3].type" type="hidden" value="4">
		                						<textarea name="descList3" id="content3" style="width: 100%">${descList[3].goodsDesc }</textarea>
	                						</td>
	                					</tr>
	                					</c:if>
	                					<c:if test="${empty descList}">
	                						<tr>
	                						<td>
		                						<input id="descList0" name="descList[0].goodsDesc" type="hidden" >
		                						<input  name="descList[0].type" type="hidden" value="1">
									     		<textarea name="descList0" id="content0" style="width: 100%"></textarea>
	                						</td>
	                						<td>
		                						<input id="descList1" name="descList[1].goodsDesc" type="hidden" >
		                						<input  name="descList[1].type" type="hidden" value="2">
		                						<textarea name="descList1" id="content1" style="width: 100%"></textarea>
	                						</td>
	                						<td>
		                						<input id="descList2" name="descList[2].goodsDesc" type="hidden" >
		                						<input  name="descList[2].type" type="hidden" value="3">
		                						<textarea name="descList2" id="content2" style="width: 100%"></textarea>
	                						</td>
	                						<td>
		                						<input id="descList3" name="descList[3].goodsDesc" type="hidden" >
		                						<input  name="descList[3].type" type="hidden" value="4">
		                						<textarea name="descList3" id="content3" style="width: 100%"></textarea>
	                						</td>
	                					</tr>
	                					</c:if>
                					</table>
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="save()" iconCls="icon-save" >保存</a>&nbsp;
		<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a>
		</div>
		</t:formvalid>
	
		
 </body>
<script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?20160607"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?20160607"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/lang/zh-cn/zh-cn.js?20160607"></script>
<script src = "webpage/com/buss/newGoods/tNewGoods-ueditor.js?v=1.0"></script>
