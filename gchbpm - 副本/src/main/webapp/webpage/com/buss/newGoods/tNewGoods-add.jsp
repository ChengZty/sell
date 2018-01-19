<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品录入</title>
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

</style>
  <script type="text/javascript">
  var domain = '${domain}';//七牛domain
  var directory = '${directory}';//目录
  
  $(function(){
	  var msg = "${msg}";
	  if(msg!=""){
		  tip(msg);
	  }
	//初始化拖拽对象
		var bar = document.getElementById("container_1_div");
		Sortable.create(bar, { group: "omega" ,
			onUpdate: function (/**Event*/evt) {//移动元素后触发的事件
// 		        var itemEl = evt.item;  // dragged HTMLElement
//		        changedSort = true;
				initPicTextShow();//初始化显示正面图和吊牌图
		    }});
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
					<input id="hasZhen" name="hasZhen" type="hidden" value="0"><!-- 0：没有4真 -->
					<input id="fareType" name="fareType" type="hidden" value="0"><!-- 运费类型：包邮 -->
					<input id="farePreferentialType" name="farePreferentialType" type="hidden" value="0"><!-- 运费优惠：无 -->
					<input id="goodsStock" name="goodsStock" type="hidden" ><!-- 库存 -->
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
									<td rowspan="3" width="200" class="value" >
										<div id="container_pic" >
				                            <a  id="templatePic_main_small" href="#" style="width: 200px">
				                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;">选择图片</div>
				                            </a>
				                            <div id="main_pics_small" style="min-height: 100px;">
				                            
				                            </div>
				                      </div>
										<input type="hidden" name="smallPic" id="smallPic">
							     		<div style="padding-top: 10px;"><span class="Validform_checktip">推荐：450*450</span></div>
									</td>
								<td align="right" width="100" >
									<label class="Validform_label">
										商品类目:
									</label>
								</td>
								<td class="value">
										<span class="goods-category">请选择类目</span><span class="required">*</span>
<!-- 										&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goToChoose()" >编辑</a>										 -->
										<a href="#" class="easyui-linkbutton" plain="true" style="margin-left: 50px" onclick="chooseCatgs('')" ><span class="icon-search l-btn-icon-left">选择</span></a>
								</td>
								<td align="right" width="100">
									<label class="Validform_label" width="100">
										品牌:
									</label>
								</td>
								<td class="value">
									<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly"  datatype="*"><span class="required">*</span>
									<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" ><span class="icon-search l-btn-icon-left">选择</span></a>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">品牌</label>
								</td>	
							</tr>
							<tr>
								
								<td align="right" width="100">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value" width="400">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px" maxlength="20"  datatype="z" ><span class="required">*</span>
										<span class="Validform_checktip">20位以内字母数字横杠下划线组合</span>
										<label class="Validform_label" style="display: none;">商品款号</label>
								</td>
								<!-- <td align="right">
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
								<tr> -->
								<td align="right">
									<label class="Validform_label">
										商品名称:
									</label>
								</td>
								<td class="value" colspan="3">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px"  maxlength="20">
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
								     	 <input id="originalPrice" name="originalPrice" type="text" style="width: 150px"  onkeyup="fillLowestPrice(1)"  datatype="money"><span class="required">*</span>
										<span class="Validform_checktip">元</span>
										<label class="Validform_label" style="display: none;">原价</label>
									</td>
								<!-- <td align="right">
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
							<tr> -->
								<td align="right">
									<label class="Validform_label">
										最低折扣/最低价:
									</label>
								</td>
								<td class="value">
							     	 <input id="lowestPriceDiscount" name="lowestPriceDiscount" type="text" style="width: 20px"  datatype="n" maxlength="3" onkeyup="fillLowestPrice(1)"/>%
							     	 /<input id="lowestPrice" name="lowestPrice" type="text" style="width: 100px" datatype="money" onkeyup="fillLowestPrice(2)"/>
									<span class="Validform_checktip">元</span>
									<label class="Validform_label" style="display: none;">最低价</label>
								</td>
							</tr>
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
				                            
				                            </div>
				                      </div>
								</td>
							</tr>
					</table>
					
					<div style="width: auto;min-height: 200px;border:#DDDDDD 1px solid ">
						<%--库存 增加一个div，用于调节页面大小，否则默认太小 --%>
						<div style="width:800px;height:1px;"></div>
						<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
						 <t:tab href="tGoodsController.do?tGoodsStoreDetails&id=${tGoodsPage.id}&topCategoryId=${tGoodsPage.topCategoryId}"  title="库存详情" id="tGoodsStoreDetails"></t:tab>
						</t:tabs>
					</div>
			
					<div style="width: auto;min-height: 150px;border:#DDDDDD 1px solid ">
						<%--商品话术 增加一个div，用于调节页面大小，否则默认太小 --%>
						<div style="width:800px;height:1px;"></div>
						<t:tabs id="tt2" iframe="false" tabPosition="top" fit="false">
						 <t:tab href="tFinActivityWordsController.do?tGoodsWordsDetails&goodsId=${tGoodsPage.id}"  title="商品话术" id="tGoodsWordsDetails"></t:tab>
						</t:tabs>
					</div>
			
				<!-- <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
						<tr>
								<td align="right" width="100">
									<label class="Validform_label">
										商品描述:
									</label>
								</td>
								<td class="value" colspan="5">
								     	<textarea id="content" style="width: 100%"></textarea>
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">描述</label>
								</td>
							</tr>
					</table> -->
			</br>
				
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" title="有图片则直接上架">上架</a>&nbsp;
<!-- 		<a href="#" class="easyui-linkbutton" onclick="preSub()" iconCls="icon-save" title="给导购完善图片">待发布</a>&nbsp; -->
		<a href="#" class="easyui-linkbutton" onclick="save()" iconCls="icon-save" title="存入草稿箱">存草稿</a> 
		</div>
		</t:formvalid>
		
		
		
	<!-- 添加 库存明细 模版 -->
	<table style="display:none">
		<tbody id="add_TgoodsStoreDetail_table_template">
			<tr>
				 <td align="center" width="40px"><div style="width: 25px;" name="xh"></div></td>
				 <td align="center" width="40px"><input style="width:20px;" type="checkbox" name="ck"/></td>
				 <input name="tGoodsStoreDetails[#index#].status" type="hidden" value="A"/>
				 <input name="tGoodsStoreDetails[#index#].changed" type="hidden" value="Y"/>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationOne" maxlength="50" 
					  		type="text"   ><span style="color: red">*</span>
				  </td>
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationTwo" maxlength="50" 
					  		type="text"    >
				  </td>
				 <!--  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].specificationThree" maxlength="50" 
					  		type="text"    >
				  </td>-->
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].alarmGoodsStock" maxlength="12"  onkeypress="Public.input.numberInput()"
					  		type="text"    >
				  </td> 
				  <td align="left">
					  	<input name="tGoodsStoreDetails[#index#].store" maxlength="12" onblur="calStore()" onkeypress="Public.input.numberInput()" 
					  		type="text"    >
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
			  <td align="left" width="60px">
				  	<input name="tGoodsWordsDetails[#index#].sortNum" style="width: 50px;" maxlength="5" onkeypress="Public.input.numberInput()"
				  		type="text"   >
			  </td>
			  <td align="left">
				  	<input name="tGoodsWordsDetails[#index#].words" style="width: 80%"	type="text"    >
			  </td>
			  <td align="center">
				  	<a href="#" class="easyui-linkbutton" onclick="addWords(this)" iconCls="icon-add" >选择已有话术</a>
			  </td>
			</tr>
		 </tbody>
	</table>

 </body>
<script src = "webpage/com/buss/newGoods/tNewGoods.js?v=1.22"></script>
<script src = "webpage/com/buss/newGoods/tNewGoodsPicUpload.js?v=1.16"></script>