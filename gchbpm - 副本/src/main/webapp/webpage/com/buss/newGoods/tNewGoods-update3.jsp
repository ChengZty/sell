<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品表</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
  	<!-- 七牛上传 start -->
	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	<script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	<script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	<!-- 七牛上传 end -->
  <script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script><!-- 拖拽 -->
  <script type="text/javascript">
  //编写自定义JS代码
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  
  
  function goback(){
	  window.location="tNewGoodsController.do?retailerList&goods_status=${tGoodsPage.goodsStatus}";
	  return false;
  }

function backList(data) {
		 if(data.success){
			document.location="tNewGoodsController.do?retailerList&goods_status=${tGoodsPage.goodsStatus}";
		}else{
			$.messager.alert('错误提示', data.msg);
		}
	}

		
	$(function () {
	    
	  //初始化运费
// 	    initFare();
	  //初始化产品参数信息
// 		initProductInfo();
		//初始化修改后的分类
		initCategory();
	  //初始化拖拽对象
		var bar = document.getElementById("main_pics");
		Sortable.create(bar, { group: "omega" });
	});
	
	//初始化修改后的分类
	function initCategory(){
		var topCategoryId = "${topCategoryId}";
		var subCategoryId = "${subCategoryId}";
		var thridCategoryId = "${thridCategoryId}";
		if(topCategoryId!=""){
			$("#topCategoryId").val(topCategoryId);
		}
		if(subCategoryId!=""){
			$("#subCategoryId").val(subCategoryId);
		}
		if(thridCategoryId!=""){
			$("#thridCategoryId").val(thridCategoryId);
		}
	}
	
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
	
	//初始化产品信息
	function initProductInfo(){
		<c:forEach items="${tGoodsPage.productInfoList}" var="poVal" varStatus="stuts">
			var id = "${poVal.id}";
			var paramCode = "${poVal.paramCode}";//编码
			var paramValues = "${poVal.paramValues}";//值
			var paramCodeInput = $("td.product_td input[value='"+paramCode+"']");
			var inputType = $(paramCodeInput).next().val();//类型
			$(paramCodeInput).prev().prev().val(id);
			if(paramValues!=""&&paramValues!="null"){
				if("0"==inputType){//单选
					$(paramCodeInput).parent().find("input[type='radio'][value='"+paramValues+"']").attr("checked",true);
				}else if("1"==inputType||"5"==inputType){//多选
// 					var strs= new Array(); 
					var strs=paramValues.split(","); //字符分割 
					for(var i in strs){
						$(paramCodeInput).parent().find("input[type='checkbox'][value='"+strs[i]+"']").attr("checked",true);
					}
				}else if("2"==inputType){//输入框
					$(paramCodeInput).next().next().val(paramValues);
				}else if("3"==inputType){//下拉框
					var selectInput = $(paramCodeInput).parent().find("input[type='text'][name$='.paramValues']");
					$(selectInput).val(paramValues);
					var values = $(paramCodeInput).next().next().next().val();//参数值
					values= values.replace(/，/g,',').split(",");
					var n=0;//值在参数值中的个数
					var jsonString = "["
					for(var i in values){
						if(paramValues==values[i]){
							n++;
						}
						jsonString+="{\"name\":\""+values[i]+"\"},";
					}
					if(n==0){
						jsonString+="{\"name\":\""+paramValues+"\"},";
					}
					jsonString=jsonString.slice(0,-1)+"]";
					var obj = JSON.parse(jsonString);
					$(selectInput).combobox({
						data:obj,
					    valueField:'name',
					    textField:'name'
					});
				}else if("4"==inputType){//图片
					$(paramCodeInput).next().next().find("input[name$='paramValues']").val(paramValues);
					var img = "<img src='"+paramValues+"'  height='100' ></img>";
					$(paramCodeInput).parent().find("div[id^='picDivContainer']").html(img);
				}
			}
		</c:forEach>
	}
	
  </script>
  
 
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false"  layout="table" btnsub="btn" action="tNewGoodsController.do?doUpdate" beforeSubmit="initPicsOrder()" tiptype="4" callback="backList">
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
					<input id="fareType" name="fareType" type="hidden" value="${tGoodsPage.fareType}"><!-- 运费类型：包邮 -->
					<input id="farePreferentialType" name="farePreferentialType" type="hidden" value="${tGoodsPage.farePreferentialType}"><!-- 运费优惠：无 -->
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
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
									<span class="goods-category">${topCategory} ${subCategory} ${thridCategory}</span><span class="required">*</span>
									<%-- &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goToChange('${tGoodsPage.id }')" >编辑</a> --%>										
									<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true" style="margin-left: 50px" onclick="chooseCatgs('${tGoodsPage.id }')" ><span class="icon-search l-btn-icon-left">选择</span></a>
								</td>
								<td align="right" width="100">
									<label class="Validform_label">
										品牌:
									</label>
								</td>
								<td class="value">
									<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" value="${tGoodsPage.brandName}" datatype="*"><span style="color: red">*</span>
<!-- 									<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" id=""><span class="icon-search l-btn-icon-left">选择</span></a> -->
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">品牌</label>
								</td>	
							</tr>
							<tr>
							<td align="right" rowspan="3">
									<label class="Validform_label">
										商品小图:
									</label>
								</td>
									<td rowspan="3">
										<div id="container_pic" >
					                            <a  id="templatePic_main_small" href="#" style="width: 200px">
					                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
					                            </a>
					                            <div id="main_pics_small" style="min-height: 100px;line-height: 0;">
					                            <c:if test="${not empty tGoodsPage.smallPic }">
					        						<div class='pic_div'><a class='delete' onclick='delSmallPic(this)' href='#'>×</a><img src='${tGoodsPage.smallPic }'  height='100' ></img></div>
					        					</c:if>
					                            </div>
					                      </div>
										<input type="hidden" name="smallPic" id="smallPic" value="${tGoodsPage.smallPic }">
							     		<div><span class="Validform_checktip">商品小图450*450，100KB以内</span></div>
									</td>
								<td align="right">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value" width="400">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px"  value="${tGoodsPage.goodsCode}" maxlength="20"  datatype="z"  readonly="readonly"><span style="color: red">*</span>
										<span class="Validform_checktip">20位以内字母数字横杠下划线组合</span>
										<label class="Validform_label" style="display: none;">商品款号</label>
								</td>
								<%-- <td align="right">
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
								<tr> --%>
								<td align="right">
									<label class="Validform_label">
										商品名称:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}" datatype="*"  maxlength="20"><span style="color: red">*</span>
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
									     	 <input id="originalPrice" name="originalPrice" type="text" style="width: 150px" value="${tGoodsPage.originalPrice}"  onkeyup="fillLowestPrice(1)"
									     	 onkeyup="fillCurrentPrice(this.value)" datatype="money"><span class="required">*</span>
											<span class="Validform_checktip">元</span>
											<label class="Validform_label" style="display: none;">原价</label>
										</td>
								<td align="right">
									<label class="Validform_label">
										最低折扣/最低价:
									</label>
								</td>
								<td class="value">
							     	 <input id="lowestPriceDiscount" name="lowestPriceDiscount" type="text" style="width: 20px"
							     	 value='<fmt:formatNumber maxFractionDigits="0" value="${tGoodsPage.lowestPriceDiscount*100}"/>'
							     	    datatype="n" maxlength="2" onkeyup="fillLowestPrice(1)"/>%
							     	 /<input id="lowestPrice" name="lowestPrice" type="text" value="${tGoodsPage.lowestPrice}" style="width: 100px" datatype="money" onkeyup="fillLowestPrice(2)"/>
									<span class="Validform_checktip">元</span>
									<label class="Validform_label" style="display: none;">最低价</label>
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
								     	 <input id="goodsStock" name="goodsStock" value="${tGoodsPage.goodsStock}" type="text" style="width: 150px;background: #D7D7D7"  readonly="readonly">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">库存</label>
								</td>
							</tr>
							<%-- <tr height="36px">
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
							</tr> --%>
							<%-- <tr>
								<td align="right">
									<label class="Validform_label">
										尺码指导:
									</label>
								</td>
								<td colspan="5" class="value product_td">
									<c:if test="${not empty productParamslist }">
										<c:set var="idx" value="0"></c:set><!-- 明细表下标 -->
										<c:forEach var="po" items="${productParamslist }" varStatus="stu">
											<c:if test="${stu.first }"><div>
											<c:set var="row" value="0"></c:set>
											</c:if>
											<c:if test="${!stu.first && po.rowNum!=row}"><!-- 换行 -->
											</div><div>
											</c:if>
											<c:if test="${po.type =='1' }"><!-- 尺码指导 -->
											<label>
											<c:if test="${po.inputType !='4' }">
											<span style="padding:0px 3px">${po.paramName }:</span>
											</c:if>
											<input type="hidden" name="productInfoList[${idx }].id" />
											<input type="hidden" name="productInfoList[${idx }].paramName" value="${po.paramName }"/>
											<input type="hidden" name="productInfoList[${idx }].paramCode" id="paramCode_${idx }" value="${po.paramCode }"/>
											<input type="hidden" id="inputType_${idx }" value="${po.inputType }"/>
												<c:choose>
													<c:when test="${po.inputType =='0' }"><!-- 单选 -->
														<c:forTokens items="${po.paramValues }" delims=",|，" var="val">
														<label style="padding: 0px 5px 0px 0px"><input type="radio" name="productInfoList[${idx }].paramValues" value="${val }"/>${val }</label>
														</c:forTokens>
													</c:when>
													<c:when test="${po.inputType =='1'||po.inputType =='5' }"><!-- 多选 ，或者多选图片-->
														<c:forTokens items="${po.paramValues }" delims=",|，" var="val">
														<label style="padding: 0px 5px 0px 0px"><input type="checkbox" name="productInfoList[${idx }].paramValues" value="${val }"/>${val }</label>
														</c:forTokens>
													</c:when>
													<c:when test="${po.inputType =='2' }"><!-- 输入框 -->
														<input type="text" name="productInfoList[${idx }].paramValues" value="" onkeydown="this.onkeyup();" onkeyup="this.size=(this.value.length>5?this.value.length*2+10:10);" size="10"/>
													</c:when>
													<c:when test="${po.inputType =='3' }"><!-- 下拉框 -->
														<input type="text" name="productInfoList[${idx }].paramValues" value="${po.paramValues }"/>
														<input type="hidden"  value="${po.paramValues }"/>
													</c:when>
													<c:when test="${po.inputType =='4' }"><!-- 图片 -->
														<div>
															<input type="hidden" name="productInfoList[${idx }].paramValues" id="productInfo${idx }" value=""/>
								        					<a  id="tempPic${idx }" href="#" style="width: 200px">
								                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
								                            </a>
								        					<div id="picDivContainer${idx }" style="min-height: 100px;line-height: 0;">
								        					</div>
							        					</div>
							        					<script type="text/javascript">
							        						$(function(){
							        							initPicUpload('${po.paramName }','tempPic${idx }','picDivContainer${idx }','productInfo${idx }');
							        						})
							        					</script>
													</c:when>
												</c:choose>
												<c:set var="idx" value="${idx+1 }"></c:set>
												</label>
											</c:if>
											<c:if test="${stu.last}"><!-- 行全部结束 -->
											</div>
											</c:if>
											<c:set var="row" value="${po.rowNum }"></c:set>
										</c:forEach>
									</c:if>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										产品信息:
									</label>
								</td>
								<td colspan="5" class="value product_td">
									<c:if test="${not empty productParamslist }">
										<c:forEach var="po" items="${productParamslist }" varStatus="stu">
											<c:if test="${stu.first }"><div>
											<c:set var="row" value="0"></c:set>
											</c:if>
											<c:if test="${!stu.first && po.rowNum!=row}"><!-- 换行 -->
											</div><div>
											</c:if>
											<c:if test="${po.type =='2' }"><!-- 产品信息 -->
											<label>
											<c:if test="${po.inputType !='4' }">
											<span style="padding:0px 3px">${po.paramName }:</span>
											</c:if>
											<input type="hidden" name="productInfoList[${idx }].id" />
											<input type="hidden" name="productInfoList[${idx }].paramName" value="${po.paramName }"/>
											<input type="hidden" name="productInfoList[${idx }].paramCode" id="paramCode_${idx }" value="${po.paramCode }"/>
											<input type="hidden" id="inputType_${idx }" value="${po.inputType }"/>
												<c:choose>
													<c:when test="${po.inputType =='0' }"><!-- 单选 -->
														<c:forTokens items="${po.paramValues }" delims=",|，" var="val">
														<label style="padding: 0px 5px 0px 0px"><input type="radio" name="productInfoList[${idx }].paramValues" value="${val }"/>${val }</label>
														</c:forTokens>
													</c:when>
													<c:when test="${po.inputType =='1'||po.inputType =='5' }"><!-- 多选 ，或者多选图片-->
														<c:forTokens items="${po.paramValues }" delims=",|，" var="val">
														<label style="padding: 0px 5px 0px 0px"><input type="checkbox" name="productInfoList[${idx }].paramValues" value="${val }"/>${val }</label>
														</c:forTokens>
													</c:when>
													<c:when test="${po.inputType =='2' }"><!-- 输入框 -->
														<input type="text" name="productInfoList[${idx }].paramValues" value="" onkeydown="this.onkeyup();" onkeyup="this.size=(this.value.length>5?this.value.length*2+10:10);" size="10"/>
														${po.inputUnit }
													</c:when>
													<c:when test="${po.inputType =='3' }"><!-- 下拉框 -->
													<input type="text" name="productInfoList[${idx }].paramValues" id="productInfoList[${idx }].paramValues" value=""/>
													<input type="hidden"  value="${po.paramValues }"/>
													</c:when>
													<c:when test="${po.inputType =='4' }"><!-- 图片 -->
														<div>
														<input type="hidden" name="productInfoList[${idx }].paramValues" id="productInfo${idx }" value="${po.paramValues }"/>
														<input type="file"  id="tempPic${idx }" />
														<div id="progress_bar_${idx }" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
							        					</div>
							        					<div  id="picDivContainer${idx }" style="min-height: 100px;line-height: 0;">
							        					</div>
							        					<script type="text/javascript">
							        						$(function(){
							        							initPicUpload('${po.paramName }','tempPic${idx }','progress_bar_${idx }','picDivContainer${idx }','productInfo${idx }');
							        						})
							        					</script>
													</c:when>
												</c:choose>
												<c:set var="idx" value="${idx+1 }"></c:set>
												</label>
											</c:if>
											<c:if test="${stu.last}"><!-- 行全部结束 -->
											</div>
											</c:if>
											<c:set var="row" value="${po.rowNum }"></c:set>
										</c:forEach>
									</c:if>
								</td>
							</tr> --%>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品主图:
									</label>
								</td>
								<td class="value" colspan="5">
								<div id="container_main_pics" >
		                            <a  id="templatePic_main" href="#" style="width: 200px">
		                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
		                            </a>
		                            <span class="Validform_checktip">红框范围内支持拖拽上传</span>
		        					<div  id="main_pics" style="height: 200px;border: 1px dashed red;">
			        					<c:if test="${not empty tGoodsPage.picOne  }">
			        					<div class='brick' >
			        					<input type='hidden' name="picOne" value='${tGoodsPage.picOne }'/><a class='delete' onclick='deMainPic(this)' href='#'>×</a>
			        					<img src='${tGoodsPage.picOne }'  height='200' ></img>
			        					</div>
			        					</c:if>
			        					<c:if test="${not empty tGoodsPage.picTwo  }">
			        					<div class='brick' >
			        					<input type='hidden' name="picTwo" value='${tGoodsPage.picTwo }'/><a class='delete' onclick='deMainPic(this)' href='#'>×</a>
			        					<img src='${tGoodsPage.picTwo }'  height='200' ></img>
			        					</div>
			        					</c:if>
			        					<c:if test="${not empty tGoodsPage.picThree  }">
			        					<div class='brick' >
			        					<input type='hidden' name="picThree" value='${tGoodsPage.picThree }'/><a class='delete' onclick='deMainPic(this)' href='#'>×</a>
			        					<img src='${tGoodsPage.picThree }'  height='200' ></img>
			        					</div>
			        					</c:if>
			        					<c:if test="${not empty tGoodsPage.picFour  }">
			        					<div class='brick' >
			        					<input type='hidden' name="picFour" value='${tGoodsPage.picFour }'/><a class='delete' onclick='deMainPic(this)' href='#'>×</a>
			        					<img src='${tGoodsPage.picFour }'  height='200' ></img>
			        					</div>
			        					</c:if>
			        					<c:if test="${not empty tGoodsPage.picFive  }">
			        					<div class='brick' >
			        					<input type='hidden' name="picFive" value='${tGoodsPage.picFive }'/><a class='delete' onclick='deMainPic(this)' href='#'>×</a>
			        					<img src='${tGoodsPage.picFive }'  height='200' ></img>
			        					</div>
			        					</c:if>
		        					</div>
		        				</div>
					     		<div><span class="Validform_checktip">商品主图要求：750*750，每张210KB以内，1～5张。点击图片并拖动可排序</span></div>
								</td>
							</tr>
					</table>
			<c:if test="${tGoodsPage.goodsStatus !='4' }">	
				<div style="width: auto;min-height: 200px;border:#DDDDDD 1px solid ">
					<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
					<div style="width:200px;height:1px;"></div>
					<t:tabs id="t_store" iframe="false" tabPosition="top" fit="false">
					 <t:tab href="tGoodsController.do?tGoodsStoreDetailsForUpdate&id=${tGoodsPage.id}" icon="icon-search" title="库存情况" id="tGoodsStoreDetailsForUpdate"></t:tab>
					</t:tabs>
				</div>
			</c:if>
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
						<tr>
								<td align="right" width="100">
									<label class="Validform_label">
										商品描述:
									</label>
								</td>
								<td class="value" colspan="5">
										<input  name="desc.id" type="hidden" value='${desc.id }'>
										<input  name="desc.type" type="hidden" value='0'>
										<input  name="desc.goodsId" type="hidden" value='${tGoodsPage.id }'>
<%-- 								     	<input id="goodsDesc" name="desc.goodsDesc" type="hidden" value='${desc.goodsDesc}'> --%>
								     	<textarea  id="content" style="width: 100%">${desc.goodsDesc }</textarea>
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">描述</label>
								</td>
							</tr>
					</table>
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" >发布</a>&nbsp;
		<a href="#" class="easyui-linkbutton" onclick="save()" iconCls="icon-save" >存草稿</a>&nbsp;
		<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a>
		</div>
		</t:formvalid>
	
		
		
			<!-- 添加 库存明细 模版 -->
	<table style="display:none">
	<tbody id="add_TgoodsStoreDetail_table_template">
		<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div>
			 </td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
			 <input name="tGoodsStoreDetails[#index#].status" type="hidden" value="A"/>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationOne" maxlength="50" 
					  		type="text"   ><span style="color: red">*</span>
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationTwo" maxlength="50" 
					  		type="text"   >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationThree" maxlength="50" 
					  		type="text"   >
				  </td>
				 <!--  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].alarmGoodsStock" maxlength="12" onkeypress="Public.input.numberInput()"		type="text"   >
				  </td> -->
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].store" maxlength="12"  onkeypress="Public.input.numberInput()"	type="text" >
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
<script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?20160607"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?20160607"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/lang/zh-cn/zh-cn.js?20160607"></script>
<script src = "webpage/com/buss/newGoods/tNewGoods.js?v=1.19"></script>
<script src = "webpage/com/buss/newGoods/tNewGoodsMainPics.js?v=1.04"></script>
<script type="text/javascript">
var leipiEditor = UE.getEditor('content',{
    toolleipi:true,//是否显示，设计器的 toolbars
    textarea: 'desc.goodsDesc',   
    toolbars: [[
                'fullscreen', 'source', '|', 'undo', 'redo', '|','date', 'time',
	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 
//	            'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
	            'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	            'customstyle', 'paragraph','|',
//	            'directionalityltr', 'directionalityrtl', 'indent', '|',
	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
//	            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	            //'simpleupload',
	            'insertimage', //'emotion',
    ]],
	wordCount:true,
    maximumWords:5000,
    autoHeightEnabled:false,
    initialFrameHeight:400,
    elementPathEnabled:false
});



//删除主图 
/* function deMainPic(obj){
   $(obj).parent().remove();
   	$("#templatePic_main").show();
   	m_pic_num--;
   	$('#templatePic_main').uploadify('settings','queueSizeLimit',5-m_pic_num);
    $('#main_pics').css("height","200px");
}  */


</script>