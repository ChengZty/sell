<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品表</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <!-- 七牛上传 start -->
  	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
  	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	  <script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	  <script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	  <!-- 七牛上传 end -->
  <style type="text/css">
  .pic_div{
  	width: 100px;
  	line-height:0;
    position: relative;
    margin-left: 20px;
    float: left;
  }
  .video_div{
  	width: 320px;
  	line-height:0;
    position: relative;
    margin-left: 20px;
    margin-bottom:10px;
    float: left;
  }
  .delete {
    display: block;
    color: white;
    background: rgba(255, 255, 255, 0.2);
    width: 20px;
    height: 20px;
    line-height: 20px;
    top: 0;
    right: 0;
    position: absolute;
    text-align: center;
    z-index: 100;
}
</style>
  <script type="text/javascript">
  //编写自定义JS代码
//   var uptoken = '${uptoken}';//七牛uptoken
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  var ctx = "${webRoot}";
  
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
			
	//上传小图
	    $('#templatePic_main_small').uploadify({buttonText:'浏览小图',
	    	queueID:'progress_bar_m_small',
	        progressData:'speed',
	        height:25,
	        overrideEvents:['onDialogClose'],
	        fileTypeDesc:'文件格式:',
	        fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
	        fileSizeLimit:'100KB',
	        swf:'plug-in/uploadify/uploadify.swf',
	        uploader:'tGoodsController.do?uploadSmallPic&sessionId=${pageContext.session.id}',
	        auto:true,
	        multi:true,
	        queueSizeLimit:5,
	        onUploadSuccess : function(file, data, response) {
	                var d=$.parseJSON(data);
	            if(d.success){
	                var div_imgs = "<div class='pic_div'><a class='delete' onclick='delSmallPic(this)' href='#'>×</a><img src='"+d.msg+"' width='100' height='100' ></img></div>"
					$("#main_pics_small").html(div_imgs);
					$("#smallPic").val(d.msg);
				    };
	            },
	        onFallback : function() {
				tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
			},
			onSelectError : function(file, errorCode, errorMsg) {
				switch (errorCode) {
				case -110:
					$.messager.alert('错误提示',
							'文件 大小超出系统限制的100KB大小');
					break;
				case -120:
					$.messager.alert('错误提示', '文件大小异常');
					break;
				case -130:
					$.messager.alert('错误提示', '文件类型不正确!');
					break;
				}
			}
	    });
	    
	  //初始化运费
	    initFare();
	  //初始化产品参数信息
		initProductInfo();
		//初始化修改后的分类
		initCategory();
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
					var img = "<img src='"+paramValues+"' width='100' height='100' ></img>";
					$(paramCodeInput).parent().find("div[id^='picDivContainer']").html(img);
				}
			}
		</c:forEach>
	}
	
  </script>
  
 
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tNewGoodsController.do?doUpdate" tiptype="4" beforeSubmit="initPicsAndVideos();" callback="backList">
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
								<td class="value">
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
										<input type="file"  id="templatePic_main_small" />
										<input type="hidden" name="smallPic" id="smallPic" value="${tGoodsPage.smallPic }">
										<div id="progress_bar_m_small" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
			        					</div>
			        					<div  id="main_pics_small" style="min-height: 100px;line-height: 0;">
			        					<c:if test="${not empty tGoodsPage.smallPic }">
			        						<div class='pic_div'><a class='delete' onclick='delSmallPic(this)' href='#'>×</a><img src='${tGoodsPage.smallPic }' width='100' height='100' ></img></div>
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
								     	 <input id="goodsStock" name="goodsStock" value="${tGoodsPage.goodsStock}" type="text" style="width: 150px;background: #D7D7D7"  readonly="readonly">
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
							<tr>
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
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										正面图:
									</label>
								</td>
								<td colspan="5">
									<div id="container_1" >
				                            <a  id="pickfiles_1" href="#" style="width: 200px">
				                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
				                            </a>
				                            <div id="container_1_div" style="min-height: 100px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='1' }">
														<div class='pic_div'>
															<a class='delete' onclick='delPic(this)' href='#'>×</a>
															<img src='${poVal.url }' width='100' height='100'/>
															<input type='hidden' value='${poVal.url }' class='url'>
															<input type='hidden' class='type' value="${poVal.type }"/>
															<input type='hidden' class='id' value="${poVal.id }"/>
															<input type='hidden' class='recommendId' value="${poVal.recommendId }"/>
														</div>
													</c:if>
												</c:forEach>
				                            </div>
				                      </div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										搭配图:
									</label>
								</td>
								<td colspan="5">
									<div id="container_2" >
				                            <a  id="pickfiles_2" href="#" style="width: 200px">
				                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
				                            </a>
				                            <div id="container_2_div" style="min-height: 100px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='2' }">
														<div class='pic_div'>
															<a class='delete' onclick='delPic(this)' href='#'>×</a>
															<img src='${poVal.url }' width='100' height='100'/>
															<input type='hidden' value='${poVal.url }' class='url'>
															<input type='hidden' class='type' value="${poVal.type }"/>
															<input type='hidden' class='id' value="${poVal.id }"/>
															<input type='hidden' class='recommendId' value="${poVal.recommendId }"/>
														</div>
													</c:if>
												</c:forEach>
				                            </div>
				                      </div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										细节图:
									</label>
								</td>
								<td colspan="5">
									<div id="container_3" >
				                            <a  id="pickfiles_3" href="#" style="width: 200px">
				                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
				                            </a>
				                            <div id="container_3_div" style="min-height: 100px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='3' }">
														<div class='pic_div'>
															<a class='delete' onclick='delPic(this)' href='#'>×</a>
															<img src='${poVal.url }' width='100' height='100'/>
															<input type='hidden' value='${poVal.url }' class='url'>
															<input type='hidden' class='type' value="${poVal.type }"/>
															<input type='hidden' class='id' value="${poVal.id }"/>
															<input type='hidden' class='recommendId' value="${poVal.recommendId }"/>
														</div>
													</c:if>
												</c:forEach>
				                            </div>
				                      </div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										视频:
									</label>
								</td>
								<td colspan="5">
									<div id="container_4" >
				                            <div id="container_4_div" style="min-height: 200px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='4' }">
														<div class='video_div'>
															<a class='delete' onclick='delPic(this)' href='#'>×</a>
															<video src='${poVal.url }' width='320' height='180' controls="controls"></video>
															<input type='hidden' value='${poVal.url }' class='url'>
															<input type='hidden' class='type' value="${poVal.type }"/>
															<input type='hidden' class='id' value="${poVal.id }"/>
															<input type='hidden' class='recommendId' value="${poVal.recommendId }"/>
														</div>
													</c:if>
												</c:forEach>
				                            </div>
				                      </div>
								</td>
							</tr>
							<tr>
								<td colspan="6">
                					<table style="width: 100%;height: 200px" cellpadding="0" cellspacing="1" >
	                					<tr>
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
                					</table>
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
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="sub2()" iconCls="icon-save" >发布</a>&nbsp;
		<a href="#" class="easyui-linkbutton" onclick="save2()" iconCls="icon-save" >存草稿</a>&nbsp;
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
					  		type="text"   >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationTwo" maxlength="50" 
					  		type="text"   >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationThree" maxlength="50" 
					  		type="text"   >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].alarmGoodsStock" maxlength="12" onkeypress="Public.input.numberInput()"		type="text"   >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].store" maxlength="12" onblur="calStore()" onkeypress="Public.input.numberInput()"	type="text"   datatype="*">
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
<script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?20160607"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?20160607"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/lang/zh-cn/zh-cn.js?20160607"></script>
<script src = "webpage/com/buss/newGoods/tNewGoods.js?v=1.05"></script>
<script src = "webpage/com/buss/newGoods/tNewGoods-ueditor.js"></script>
<script src = "webpage/com/buss/newGoods/tNewGoodsPicUpload.js?v=1.03"></script>