<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品表</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
	<!-- 七牛上传 start -->
	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	<script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	<script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	<!-- 七牛上传 end -->
  <script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script><!-- 拖拽 -->
<style type="text/css">
.bgd_txt{
	position: absolute;
	top: 0;
	left: 0;
	height: 22px;
	line-height: 22px;
    width: 100%;
    text-align: center;
    background: white;
    font-weight: bold;
    opacity: 0.8;
}
#tagstitle{
	color: #8f0911;
	font-size: 12px;
    font-family: Tahoma,Verdana,微软雅黑,新宋体, Helvetica, Arial, sans-serif;
    line-height: 25px;
    text-align: center;
    white-space: nowrap;
    margin:10px 5px;
}
.goodsTags{
	display: inline-block;
	margin:5px 10px;
	font-size: 12px;
	padding: 0px 10px;
	color: #fff;
	background: #169bd5;
	border-radius: 4px;
}
</style>
  <script type="text/javascript">
  //编写自定义JS代码
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
//  var changedSort = false;//是否修改排序
  
 function goback() {
	window.location = "tNewGoodsController.do?retailerList&goods_status=${tGoodsPage.goodsStatus}";
	return false;
}

function backList(data) {
	if (data.success) {
		document.location = "tNewGoodsController.do?retailerList&goods_status=${tGoodsPage.goodsStatus}";
	} else {
		$.messager.alert('错误提示', data.msg);
	}
}

$(function(){
	//初始化拖拽对象
		var bar = document.getElementById("container_1_div");
		Sortable.create(bar, { group: "omega" ,
			onUpdate: function (/**Event*/evt) {//移动元素后触发的事件
// 		        var itemEl = evt.item;  // dragged HTMLElement
//		        changedSort = true;
				initPicTextShow();//初始化显示正面图和吊牌图
		    }	
		});
		//初始化显示正面图和吊牌图
		initPicTextShow();
  })
  

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
					<input id="fareType" name="fareType" type="hidden" value="${tGoodsPage.fareType}"><!-- 运费类型：包邮 -->
					<input id="farePreferentialType" name="farePreferentialType" type="hidden" value="${tGoodsPage.farePreferentialType}"><!-- 运费优惠：无 -->
					<input id="goodsStock" name="goodsStock" type="hidden" value="${tGoodsPage.goodsStock}"><!-- 库存 -->
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
							<tr>
								<td colspan="6" height="40px" style="padding-left: 10px">
									<h2>基础资料</h2>
								</td>
							</tr>
							<tr>
								<td align="right" rowspan="3" width="100">
									<label class="Validform_label">
										商品小图:
									</label>
								</td>
									<td rowspan="3" width="200" class="value">
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
							     		<div><span class="Validform_checktip">商品小图450*450，100KB以内</br>(便于导购发布商品)</span></div>
									</td>
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
									<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" value="${tGoodsPage.brandName}" datatype="*"><span class="required">*</span>
									<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" id=""><span class="icon-search l-btn-icon-left">选择</span></a>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">品牌</label>
								</td>	
							</tr>
							<tr>
							
								<td align="right">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value" width="400">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px"  value="${tGoodsPage.goodsCode}" maxlength="20"   readonly="readonly"><span class="required">*</span>
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">商品款号</label>
								</td>
								<%-- <td align="right">
									<label class="Validform_label">
										短标题:
									</label>
								</td>
								<td class="value">
								     	 <input id="title" name="title" type="text" style="width: 200px" value="${tGoodsPage.title}" maxlength="11">
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
								<td class="value" colspan="3">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}"  maxlength="20">
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
							     	    datatype="n" maxlength="3" onkeyup="fillLowestPrice(1)"/>%
							     	 /<input id="lowestPrice" name="lowestPrice" type="text" value="${tGoodsPage.lowestPrice}" style="width: 100px" datatype="money" onkeyup="fillLowestPrice(2)"/>
									<span class="Validform_checktip">元</span>
									<label class="Validform_label" style="display: none;">最低价</label>
								</td>
								<%-- <td align="right">
									<label class="Validform_label">
										活动价:
									</label>
								</td>
								<td class="value">
								     	 <input id="activityPrice" name="activityPrice" type="text" style="width: 150px" value="${tGoodsPage.activityPrice}" datatype="money" onkeypress="Public.input.numberInput()">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">活动价</label>
									</td> --%>
							</tr>
							<%-- <tr>
								<td align="right">
									<label class="Validform_label">
										是否虚拟商品:
									</label>
								</td>
								<td class="value">
								<t:dictSelect field="isSpecial" id="isSpecial" type="list" typeGroupCode="sf_yn"  hasLabel="false" defaultVal="${tGoodsPage.isSpecial}" datatype="*"></t:dictSelect>
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">是否虚拟商品</label>
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
							</tr> --%>
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
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品图片:
									</label>
								</td>
								<td colspan="5" class="value">
									<div id="container_1" >
				                            <a  id="pickfiles_1" href="#" style="width: 200px">
				                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
				                            </a>
				                            <span class="Validform_checktip">红框范围内支持拖拽上传(注意：第一张图为正面图，第五张为吊牌图)</span>
				                            <div id="container_1_div" style="min-height: 100px;border: 1px dashed red;">
												<c:forEach var="poVal" items="${recommendDetailsList }" varStatus="sts">
													<c:if test="${poVal.type=='1' }">
														<div class='pic_div'>
															<a class='delete' onclick='delPic(this)' href='#'>×</a>
															<img src='${poVal.url }'  height='100'/>
															
															<input type='hidden' value='${poVal.idx }' class='idx'>
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
								<td colspan="5" class="value">
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
					</table>
					
			<div style="width: auto;min-height: 200px;border:#DDDDDD 1px solid ">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:200px;height:1px;"></div>
				<t:tabs id="t_store" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tGoodsController.do?tGoodsStoreDetailsForUpdate&id=${tGoodsPage.id}" icon="icon-search" title="库存情况" id="tGoodsStoreDetailsForUpdate"></t:tab>
				</t:tabs>
			</div>
			<div style="width: auto;min-height: 150px;border:#DDDDDD 1px solid ">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:200px;height:1px;"></div>
				<t:tabs id="t_words" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tFinActivityWordsController.do?tGoodsWordsDetails&goodsId=${tGoodsPage.id}" icon="icon-search" title="商品话术" id="tGoodsWordsDetailsForUpdate"></t:tab>
				</t:tabs>
			</div>
			<div style="width: auto;min-height: 150px;border:#DDDDDD 1px solid ">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<span id="tagstitle">商品标签</span><hr>
				<c:forEach var="tag" items="${tagsList }">
					<span class="goodsTags">${tag.tag }</span>
				</c:forEach>
			</div>
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" title="有图片则直接上架">上架</a>&nbsp;
<!-- 		<a href="#" class="easyui-linkbutton" onclick="preSub()" iconCls="icon-save" title="给导购完善图片">待发布</a>&nbsp; -->
		<a href="#" class="easyui-linkbutton" onclick="save()" iconCls="icon-save" title="存入草稿箱">存草稿</a>
		<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a>
		</div>
		</t:formvalid>
	
		
		
	<!-- 添加 库存明细 模版 -->
	<table style="display:none">
		<tbody id="add_TgoodsStoreDetail_table_template">
			<tr>
				 <td align="center" width="40px"><div style="width: 25px;" name="xh"></div>
				 </td>
				 <td align="center" width="40px"><input style="width:20px;" type="checkbox" name="ck"/></td>
				 <input name="tGoodsStoreDetails[#index#].status" type="hidden" value="A"/>
				 <input name="tGoodsStoreDetails[#index#].changed" type="hidden" value="Y"/>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationOne" maxlength="50" 
					  		type="text"   ><span style="color: red">*</span>
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationTwo" maxlength="50" 
					  		type="text"   >
				  </td>
				  <!-- <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationThree" maxlength="50" 
					  		type="text"   >
				  </td>-->
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].alarmGoodsStock" maxlength="12" onchange="changeFlag(this)" onkeypress="Public.input.numberInput()"		type="text"   >
				  </td> 
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].store" maxlength="12" onblur="calStore()" onchange="changeFlag(this)" onkeypress="Public.input.numberInput()"	type="text" >
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].barCode" maxlength="20" type="text" >
				  </td>
			</tr>
	 	</tbody>
	</table>
		<!-- 添加 话术 模版 -->
	<table style="display:none">
		<tbody id="add_TgoodsWordsDetail_table_template">
			<tr>
			 <td align="center" width="40px"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center" width="40px"><input style="width:20px;" type="checkbox" name="ck"/></td>
			 <input name="tGoodsWordsDetails[#index#].status" type="hidden" value="A"/>
			 <input name="tGoodsWordsDetails[#index#].wordsType" type="hidden" value="2"/>
			 <input name="tGoodsWordsDetails[#index#].changed"  type="hidden" value="N"/>
			  <td align="left" width="60px">
				  	<input name="tGoodsWordsDetails[#index#].sortNum" style="width: 50px;" maxlength="5" onkeypress="Public.input.numberInput()"  type="text"   >
			  </td>
			  <td align="left">
				  	<input name="tGoodsWordsDetails[#index#].words" style="width: 80%"	type="text"  >
			  </td>
			   <td align="center">
				  	<a href="#" class="easyui-linkbutton" onclick="addWords(this)" iconCls="icon-add" >选择已有话术</a>
			  </td>
			</tr>
		 </tbody>
	</table>
 </body>
  <script src = "webpage/com/buss/newGoods/tNewGoods.js?v=1.21"></script>		
  <script src = "webpage/com/buss/newGoods/tNewGoodsPicUpload.js?v=1.16"></script>