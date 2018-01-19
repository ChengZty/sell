<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品组合</title>
  <t:base type="jquery,easyui,tools"></t:base>
 <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
  <!-- 七牛上传 start -->
  	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
  	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	  <script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	  <script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	  <!-- 七牛上传 end -->
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  <script type="text/javascript" src="plug-in/sortable/Sortable.min.js?v=1.0.1"></script><!-- 拖拽 -->
  <script type="text/javascript">
  //编写自定义JS代码
//    	  var uptoken = '${uptoken}';//七牛uptoken
	  var domain = '${domain}';//七牛domain
	  var directory = '${directory}';//目录
	  var ctx = "${webRoot}";
  
function goback(){
	  window.location="tNewGoodsGroupController.do?${listPage}&goods_status=${tGoodsPage.goodsStatus}";
	  return false;
}
  
function backList(data) {
	  if(data.success){
		window.location="tNewGoodsGroupController.do?${listPage}&goods_status=${tGoodsPage.goodsStatus}";
	  }else{
		  $.messager.alert('错误提示', data.msg);
	  }
}
	$(function () {
		$("#topCategoryId option[value='${tGoodsPage.topCategoryId}']").attr("selected",true);
		$("#subCategoryId option[value='${tGoodsPage.subCategoryId}']").attr("selected",true);
		$("#thridCategoryId option[value='${tGoodsPage.thridCategoryId}']").attr("selected",true);
		$("#goodsType").val("${tGoodsPage.goodsType}");
		//初始化拖拽对象
		var bar = document.getElementById("dtl_pics");
		Sortable.create(bar, { group: "omega" });
		var bar2 = document.getElementById("container_1_div");
		Sortable.create(bar2, { group: "omega" });
	});
	
  </script>
  
 
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tNewGoodsGroupController.do?doUpdate" tiptype="4" beforeSubmit="initPicsAndVideos" callback="backList">
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tGoodsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tGoodsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tGoodsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tGoodsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tGoodsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tGoodsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="retailerId" name="retailerId" type="hidden" value="${tGoodsPage.retailerId}">
					<input id="retailerName" name="retailerName" type="hidden" value="${tGoodsPage.retailerName}">
					<input id="goodsStatus" name="goodsStatus" type="hidden" value="${tGoodsPage.goodsStatus}">
					<input id="cityId" name="cityId" type="hidden" value="${tGoodsPage.cityId}">
					<input id="brandId" name="brandId" type="hidden" value="${tGoodsPage.brandId}">
					<input id="brandCode" name="brandCode" type="hidden" value="${tGoodsPage.brandCode}"><!-- 品牌编码 -->
					<input id="goodsCollect" name="goodsCollect" type="hidden" value="${tGoodsPage.goodsCollect}">
					<input id="noSenseNum" name="noSenseNum" type="hidden" value="${tGoodsPage.noSenseNum}">
					<input id="goodNum" name="goodNum" type="hidden" value="${tGoodsPage.goodNum}">
					<input id="goodsStar" name="goodsStar" type="hidden" value="${tGoodsPage.goodsStar}">
					<input id="scoresNum" name="scoresNum" type="hidden" value="${tGoodsPage.scoresNum}">
					<input id="provinceId" name="provinceId" type="hidden" value="${tGoodsPage.provinceId}">
					<input id="currentPrice" name="currentPrice" type="hidden" value="${tGoodsPage.currentPrice}">
					<input  name="prePrice" type="hidden" value="${tGoodsPage.currentPrice}"><!-- 用于记录之前的价格 -->
					<input type="hidden" name="preLowestPrice" value="${tGoodsPage.lowestPrice}" /><!-- 用于记录之前的最低价 -->
					<input type="hidden" name="preActivityPrice" value="${tGoodsPage.activityPrice}" /><!-- 用于记录之前的活动价格 -->
					<input id="publishStatus" name="publishStatus" type="hidden" value="1"><!--发布状态 默认 为1，1：立即发布，2：存草稿 -->
					<input id="groupSource" name="groupSource" type="hidden" value="${tGoodsPage.groupSource}"><!--组合来源   1：零售商录入，2：后台系统录入 -->
					<input id="coverPic" name="coverPic" type="hidden" value="${tGoodsPage.coverPic}"><!--封面图 -->
					<input id="isShow" name="isShow" type="hidden" value="${tGoodsPage.isShow}"><!--是否显示广告位 -->
					<input id="isSpecial" name="isSpecial" type="hidden" value="${tGoodsPage.isSpecial}"><!--是否特殊商品 -->
					<input id="newGoodsType" name="newGoodsType" type="hidden" value="2"><!-- 新商品模块 -->
					<input id="goodsType" name="goodsType" type="hidden" value="2"><!-- 组合 -->
					<input id="goodsCode" name="goodsCode" type="hidden" value="${tGoodsPage.goodsCode}"><!-- 款号 -->
					<input name="lowestPriceDiscount" type="hidden" value="0"><!-- 最低折扣 -->
					<input name="lowestPrice" type="hidden" value="0"><!-- 最低价 -->
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
							<tr>
								<td colspan="4" height="40px" onclick="aa()">
									<h2>基础信息</h2>
								</td>
							</tr>
							<tr>
							<td align="right" rowspan="2" width="120px" >
									<label class="Validform_label">
										组合小图:
									</label>
								</td>
									<td rowspan="2"  width="180px">
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
								<td align="right" width="120px">
									<label class="Validform_label">
										组合类目:
									</label>
								</td>
								<td class="value" >
										<select name="topCategoryId" id="topCategoryId" onchange="getUnderCategories(this.value,'2','subCategoryId')">
											<c:forEach var="category" items="${categoryList}">
											<option value="${category[0] }" >${category[1] }</option>
											</c:forEach>
										</select>
										<select name="subCategoryId" id="subCategoryId"  onchange="getUnderCategories(this.value,'3','thridCategoryId')" >
											<c:forEach var="subCategory" items="${subCategoryList}">
												<option value="${subCategory[0] }" >${subCategory[1] }</option>
												</c:forEach>
										</select>
										<select name="thridCategoryId" id="thridCategoryId" datatype="*" >
											<c:forEach var="thirdCategory" items="${thirdCategoryList}">
												<option value="${thirdCategory[0] }" >${thirdCategory[1] }</option>
												</c:forEach>
										</select>
										<span style="color: red">*</span>										
								</td>
								<!-- <td align="right">
									<label class="Validform_label">
										组合类别:
									</label>
								</td>
								<td class="value">
								     	<select name="goodsType" id="goodsType">
								     		<option value="2">组合</option>
								     		<option value="3">搭配</option>
								     	</select>
								</td> -->
							</tr>
							<%-- <tr>
								<td align="right">
									<label class="Validform_label">
										组合款号:
									</label>
								</td>
								<td class="value">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px"  value="${tGoodsPage.goodsCode}" maxlength="20"  datatype="z" readonly="readonly">
										<span class="Validform_checktip">20位以内字母数字横杠下划线组合</span>
										<label class="Validform_label" style="display: none;">组合款号</label>
								</td>
								<td align="right">
									<label class="Validform_label">
										短标题:
									</label>
								</td>
								<td class="value">
								     	 <input id="title" name="title" type="text" style="width: 200px" value="${tGoodsPage.title}"  maxlength="14">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">短标题</label>
								</td>
							</tr> --%>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合名称:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}"  maxlength="20">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">组合名称</label>
								</td>
							</tr>
							<%-- <tr>
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
								<td align="right">
										<label class="Validform_label">
										原价/组合价:
									</label>
									</td>
									<td class="value">
									<input id="originalPrice" name="originalPrice" type="text" style="width: 150px;background: #D7D7D7" value="${tGoodsPage.originalPrice}" readonly="readonly">
									     	/ &nbsp;<input id="groupPrice" name="groupPrice" type="text" style="width: 150px;background: #D7D7D7" datatype="*" value="${tGoodsPage.groupPrice}" readonly="readonly">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">组合价</label>
										</td>
							</tr> --%>
							<%-- <c:if test="${tGoodsPage.groupSource == '1'}"><!-- 零售商 -->
							<tr>
								<td align="right">
									<label class="Validform_label">
										主品牌:
									</label>
								</td>
								<td class="value" colspan="5">
								     	<input id="brandName" name="brandName" type="text" style="width: 150px" datatype="*" readonly="readonly" value="${tGoodsPage.brandName}">
										<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" ><span class="icon-search l-btn-icon-left">选择</span></a>
										<span class="Validform_checktip">可选择该组合中的一个主要品牌</span>
										<label class="Validform_label" style="display: none;">主品牌</label>
								</td>
							</tr>
							</c:if>
							<c:if test="${tGoodsPage.groupSource == '2'}"><!-- 后台 -->
							<tr>
								<td align="right">
									<label class="Validform_label">
										主品牌:
									</label>
								</td>
								<td class="value" colspan="3">
								     	<input id="brandName" name="brandName" type="text" style="width: 150px" datatype="*" readonly="readonly" value="${tGoodsPage.brandName}">
										<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" ><span class="icon-search l-btn-icon-left">选择</span></a>
										<span class="Validform_checktip">可选择该组合中的一个主要品牌</span>
										<label class="Validform_label" style="display: none;">主品牌</label>
								</td>
								<td align="right">
									<label class="Validform_label">
										零售商:
									</label>
								</td>
								<td class="value">
								     	${tGoodsPage.retailerName }
								</td>
							</tr>
							</c:if> --%>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品组合中单品:
									</label>
								</td>
								<td class="value" colspan="3">
									<div class="pic_list" id="group_pics">
		                               <input type="button" value="添加单品" onclick="findGoods()" class="uploadify-button" style="width: 120px;height: 25px;"/>
		                            </div>	
		                            <div id="dtl_pics" style="min-height: 220px">
		                            	<c:forEach items="${tGoodsPage.tGoodsPicDetails}" varStatus="status" var="poVal"> 
									    	 <div class="dtl_div">	
									    	 	<input type='hidden' class='d_id' value='${poVal.id }'/>
									    	 	<input type='hidden' class='d_g' value='${poVal.detailGoodsId }'/>
									    	 	<input type='hidden' class='g_c' value='${poVal.goodsCode }'/>
									    	 	<a class='delete2' onclick='delDtlPic(this)' href='#'>×</a>
												<img src='${poVal.picUrl }'  height='160' ></img>
												<div class="elips">款号：${poVal.goodsCode }</div>
												<div><span>原价：${poVal.originalPrice }</span></div>
											</div>
									</c:forEach>
	        						</div>
	        						<div><span style="color: red">单品款数必选（最多15款）</span>选择的单品图片可以拖拽调整顺序</div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合相册:
									</label>
								</td>
								<td colspan="3">
									<div id="container_1" >
				                            <a  id="pickfiles_1" href="#" style="width: 200px">
				                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
				                            </a>
				                            <span class="Validform_checktip">红框范围内支持拖拽上传</span>
				                            <div id="container_1_div" style="min-height: 100px;border: 1px dashed red;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='1' }">
														<div class='pic_div'>
															<a class='delete' onclick='delPic(this)' href='#'>×</a>
															<img src='${poVal.url }'  height='100'/>
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
							<%-- <tr>
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
							</tr> --%>
							<tr>
								<td align="right">
									<label class="Validform_label">
										视频:
									</label>
								</td>
								<td colspan="3">
									<div id="container_5" >
				                            <div id="container_5_div" style="min-height: 200px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='5' }">
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
			<%-- <div style="width: auto;min-height: 200px;">
				增加一个div，用于调节页面大小，否则默认太小
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tGoodsController.do?tGoodsAttrDetailsForUpdate&id=${tGoodsPage.id}" icon="icon-search" title="关键词" id="tGoodsAttrDetailsForUpdate"></t:tab>
				</t:tabs>
			</div> --%>
	
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
			<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" title="有图片则直接上架">发布</a>&nbsp;
			<a href="#" class="easyui-linkbutton" onclick="save()" iconCls="icon-save" title="存入草稿箱">存草稿</a>&nbsp;
			<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a>
		</div>
		</t:formvalid>
		
		<!-- 添加 关键词 模版 -->
	<!-- <table style="display:none">
	<tbody id="add_TgoodsAttrDetail_table_template">
		<tr>
		<td align="center">
		<input style="width:20px;" type="checkbox" name="ck"/> </td>
				  <td align="right">关键词：</td>
				  <td align="left">
			<input name="tGoodsAttrDetails[#index#].status" type="hidden" value="A"/>
					  	<input name="tGoodsAttrDetails[#index#].careValue" maxlength="50" 
					  		type="text"   >
				  </td>
				  <td align="right">关键词：</td>
				  <td align="left">
				  			<input name="tGoodsAttrDetails[#index#].status" type="hidden" value="A"/>
					  	<input name="tGoodsAttrDetails[#index#].careValue" maxlength="50" 
					  		type="text"   >
				  </td>
				  <td align="right">关键词：</td>
				  <td align="left">
				  			<input name="tGoodsAttrDetails[#index#].status" type="hidden" value="A"/>
					  	<input name="tGoodsAttrDetails[#index#].careValue" maxlength="50" 
					  		type="text"   >
				  </td>
			</tr>
		 </tbody>
		</table>
		 -->
 </body>
  <script src = "webpage/com/buss/newGoods/tNewGoodsGroup.js?v=1.19"></script>	
  <script src = "webpage/com/buss/newGoods/tNewGoodsPicUpload.js?v=1.02"></script>	