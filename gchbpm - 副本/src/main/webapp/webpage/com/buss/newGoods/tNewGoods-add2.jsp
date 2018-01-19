<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品录入</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
	<!-- 七牛上传 start -->
	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	<script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	<script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	<!-- 七牛上传 end -->
<!-- <script src="plug-in/Validform/js/common.js"></script> -->

<style type="text/css">
  .pic_div{
  	width: 100px;
  	line-height:0;
    position: relative;
    margin-left: 20px;
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
  var uptoken = '${uptoken}';//七牛uptoken
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  var ctx = "${webRoot}";

$(function () {
	$("#fare_td_2 input").attr("disabled",true);
	
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
	
})
		
</script>
  
 
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table"  action="tNewGoodsController.do?doAdd" tiptype="4" beforeSubmit="initPicsAndVideos()" callback="goAdd">
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
					<input id="cityId" name="cityId" type="hidden" value="${tGoodsPage.cityId}">
					<input id="provinceId" name="provinceId" type="hidden" value="${tGoodsPage.provinceId}">
					<input id="brandId" name="brandId" type="hidden" value="${tGoodsPage.brandId}">
					<input id="categries" name="categries" type="hidden" value=""><!-- 可见类目c2ID,c1ID循环的 -->
					<input id="brandCode" name="brandCode" type="hidden" value=""><!-- 品牌编码 -->
					<input id="publishStatus" name="publishStatus" type="hidden" value="1"><!--发布状态 默认 为1，1：立即发布，2：存草稿 -->
					<input id="goodsCollect" name="goodsCollect" type="hidden" value="0">
					<input id="noSenseNum" name="noSenseNum" type="hidden" value="0">
					<input id="goodNum" name="goodNum" type="hidden" value="0">
					<input id="goodsStar" name="goodsStar" type="hidden" value="0">
					<input id="scoresNum" name="scoresNum" type="hidden" value="0">
					<input id="salesVolume" name="salesVolume" type="hidden" value="0">
					<input id="isSpecial" name="isSpecial" type="hidden" value="N">
					<input id="goodsType" name="goodsType" type="hidden" value="1"><!-- 单品 -->
					<input id="newGoodsType" name="newGoodsType" type="hidden" value="2"><!-- 新商品模块 -->
					<input id="hasZhen" name="hasZhen" type="hidden" value="1"><!-- 1：有4真 -->
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
							<tr>
								<td colspan="6" height="40px" style="padding-left: 10px">
									<h2>基础资料</h2>
								</td>
							</tr>
							<tr>
								<td align="right" width="100" >
									<label class="Validform_label">
										商品类目:
									</label>
								</td>
								<td class="value" colspan="3">
										<span class="goods-category">请选择类目</span><span class="required">*</span>
										<!-- &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goToChoose()" >编辑</a> -->										
										<a href="#" class="easyui-linkbutton" plain="true" style="margin-left: 50px" onclick="chooseCatgs('')" ><span class="icon-search l-btn-icon-left">选择</span></a>
								</td>
								<td align="right" width="100">
									<label class="Validform_label" width="100">
										品牌:
									</label>
								</td>
								<td class="value">
									<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly"  datatype="*">
									<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" ><span class="icon-search l-btn-icon-left">选择</span></a>
									
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">品牌</label>
								</td>	
							</tr>
							<tr>
								<td align="right" rowspan="5" width="100">
									<label class="Validform_label">
										商品小图:
									</label>
								</td>
									<td rowspan="5" width="300">
										<div>
										<input type="file"  id="templatePic_main_small" />
										<input type="hidden" name="smallPic" id="smallPic">
										<div id="progress_bar_m_small" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
			        					</div>
			        					<div  id="main_pics_small" style="min-height: 100px;line-height: 0;">
			        					</div>
							     		<div><span class="Validform_checktip">商品小图450*450，100KB以内</span></div>
									</td>
								<td align="right" width="100">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value" width="400">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px" maxlength="20"  datatype="z" >
										<span class="Validform_checktip">20位以内字母数字横杠下划线组合</span>
										<label class="Validform_label" style="display: none;">商品款号</label>
								</td>
								<td align="right">
									<label class="Validform_label">
										短标题:
									</label>
								</td>
								<td class="value">
								     	 <input id="title" name="title" type="text" style="width: 200px" maxlength="11">
										<span class="Validform_checktip">选填，11字以内(含)</span>
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
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px"   datatype="*" maxlength="20">
										<span class="Validform_checktip">20字以内(含)</span>
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
									     	 <input id="originalPrice" name="originalPrice" type="text" style="width: 150px" onkeypress="Public.input.numberInput()" onkeyup="fillCurrentPrice(this.value)"  datatype="money">
											<span class="Validform_checktip"></span>
											<label class="Validform_label" style="display: none;">原价</label>
										</td>
								<td align="right">
									<label class="Validform_label">
										现价:
									</label>
								</td>
								<td class="value">
								     	 <input id="currentPrice" name="currentPrice" type="text" style="width: 150px"  datatype="money" onkeypress="Public.input.numberInput()">
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
								     	 <input id="lowestPrice" name="lowestPrice" type="text" style="width: 150px"  datatype="money" onkeypress="Public.input.numberInput()">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">最低价</label>
									</td>
									<td align="right">
									<label class="Validform_label">
										活动价:
									</label>
								</td>
								<td class="value">
								     	 <input id="activityPrice" name="activityPrice" type="text" style="width: 150px;"  onkeypress="Public.input.numberInput()">
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
								<t:dictSelect field="subGoodsType" id="subGoodsType" type="list" typeGroupCode="subgdstype"  hasLabel="false"></t:dictSelect>
<!-- 								     	 <input id="subGoodsType" name="subGoodsType" type="text" style="width: 150px"  > -->
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">商品类别</label>
								</td>
								<td align="right">
									<label class="Validform_label">
										库存:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsStock" name="goodsStock" type="text" style="width: 150px;background: #D7D7D7"  readonly="readonly">
										<span class="Validform_checktip">无需输入，系统自动累加</span>
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
									<label><input type="radio" value="0" name="fareType" checked="checked" onclick="changeFareType(this.value)"/>包邮</label>&nbsp;
									<label><input type="radio" value="1" name="fareType" onclick="changeFareType(this.value)"/>定额</label>
									&nbsp;&nbsp;
									<span id="fare_1" style="display: none">单价：<input type="text" id="fare" name="fare" value="0" style="width: 30px" onkeypress="Public.input.numberInput()"/>元/件</span>
								</td>
								<td align="right">
									<label class="Validform_label">
										运费优惠:
									</label>
								</td>
								<td class="value" id="fare_td_2">
									<label><input type="radio" value="0" name="farePreferentialType"  checked="checked" onclick="changeFarereferentialType(this.value)"/>无</label>&nbsp;
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
								<td colspan="5" class="value">
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
											<c:set var="type1inputType3Num" value="0"></c:set><!--尺码指导下拉框个数 -->
											<label>
											<c:if test="${po.inputType !='4' }">
											<span style="padding:0px 3px">${po.paramName }:</span>
											</c:if>
											<input type="hidden" name="productInfoList[${idx }].paramName" value="${po.paramName }"/>
											<input type="hidden" name="productInfoList[${idx }].paramCode" value="${po.paramCode }"/>
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
														<select name="productInfoList[${idx }].paramValues" class="easyui-combobox">
														<c:forTokens items="${po.paramValues }" delims=",|，" var="val">
														<option value="${val }">${val }</option>
														</c:forTokens>
														</select>
														<c:set var="type1inputType3Num" value="${type1inputType3Num+1 }"></c:set>
													</c:when>
													<c:when test="${po.inputType =='4' }"><!-- 图片 -->
														<div>
														<input type="hidden" name="productInfoList[${idx }].paramValues" id="productInfo${idx }" value=""/>
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
									<c:if test="${type1inputType3Num > 0 }">
									<div style="font-size: 10px;color: gray">下拉框没有的值可自行输入</div>
									</c:if>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										产品信息:
									</label>
								</td>
								<td colspan="5" class="value">
									<c:if test="${not empty productParamslist }">
										<c:forEach var="po" items="${productParamslist }" varStatus="stu">
											<c:if test="${stu.first }"><div>
											<c:set var="row" value="0"></c:set>
											</c:if>
											<c:if test="${!stu.first && po.rowNum!=row}"><!-- 换行 -->
											</div><div>
											</c:if>
											<c:if test="${po.type =='2' }"><!-- 产品信息 -->
											<c:set var="type2inputType3Num" value="0"></c:set><!-- 产品信息下拉框个数 -->
											<label>
											<c:if test="${po.inputType !='4' }">
											<span style="padding:0px 3px">${po.paramName }:</span>
											</c:if>
											<input type="hidden" name="productInfoList[${idx }].paramName" value="${po.paramName }"/>
											<input type="hidden" name="productInfoList[${idx }].paramCode" value="${po.paramCode }"/>
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
														<select name="productInfoList[${idx }].paramValues" class="easyui-combobox">
														<c:forTokens items="${po.paramValues }" delims=",|，" var="val">
														<option value="${val }">${val }</option>
														</c:forTokens>
														</select>
														<c:set var="type2inputType3Num" value="${type2inputType3Num+1 }"></c:set>
													</c:when>
													<c:when test="${po.inputType =='4' }"><!-- 图片 -->
														<div>
														<input type="hidden" name="productInfoList[${idx }].paramValues" id="productInfo${idx }" value=""/>
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
									<c:if test="${type2inputType3Num > 0 }">
									<div style="font-size: 10px;color: gray">下拉框没有的值可自行输入</div>
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
				                            
				                            </div>
				                      </div>
								</td>
							</tr>
							<tr>
								<td colspan="6">
                					<table style="width: 100%;height: 200px" cellpadding="0" cellspacing="1" >
	                					<tr onclick="setContent()">
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
                					</table>
								</td>
							</tr>
					</table>
					<div style="width: auto;min-height: 200px;border:#DDDDDD 1px solid ">
						<%--库存 增加一个div，用于调节页面大小，否则默认太小 --%>
						<div style="width:800px;height:1px;"></div>
						<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
						 <t:tab href="tGoodsController.do?tGoodsStoreDetails&id=${tGoodsPage.id}&topCategoryId=${tGoodsPage.topCategoryId}" icon="icon-search" title="库存情况" id="tGoodsStoreDetails"></t:tab>
						</t:tabs>
					</div>
			
			</br>
				
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="sub2()" iconCls="icon-save" >发布</a>&nbsp;
		<a href="#" class="easyui-linkbutton" onclick="save2()" iconCls="icon-save" >存草稿</a> 
		</div>
		</t:formvalid>
		
		
		
			<!-- 添加 库存明细 模版 -->
	<table style="display:none">
	<tbody id="add_TgoodsStoreDetail_table_template">
		<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div>
			 </td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationOne" maxlength="50" 
					  		type="text"   >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationTwo" maxlength="50" 
					  		type="text"    >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationThree" maxlength="50" 
					  		type="text"    >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].alarmGoodsStock" maxlength="12" onkeypress="Public.input.numberInput()"
					  		type="text"    >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].store" maxlength="12" onblur="calStore()" onkeypress="Public.input.numberInput()" datatype="*"
					  		type="text"    >
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
<script>UEDITOR_HOME_URL='<%=path%>/plug-in/Formdesign/js/ueditor/';</script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?20160607"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?20160607"> </script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/lang/zh-cn/zh-cn.js?20160607"></script>
<script src = "webpage/com/buss/newGoods/tNewGoods.js?v=1.03"></script>
<script src = "webpage/com/buss/newGoods/tNewGoods-ueditor.js"></script>
<script src = "webpage/com/buss/newGoods/tNewGoodsPicUpload.js?v=1.02"></script>