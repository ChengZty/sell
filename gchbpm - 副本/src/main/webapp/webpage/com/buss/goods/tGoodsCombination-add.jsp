<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品表</title>
  <t:base type="jquery,easyui,tools,DatePicker,layer"></t:base>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
    <script type="text/javascript" src="plug-in/gridly/jquery.gridly.js" ></script><!-- 拖动控件 -->
	<link href="plug-in/gridly/css/jquery.gridly.css" rel="stylesheet" type="text/css" />
	<link href="plug-in/divView/css/bootstrap.min2.css" rel="stylesheet" type="text/css" />
	<script src="plug-in/Validform/js/common.js"></script>
	<script type="text/javascript">
	function goAdd(data) {
		  if(data.success){
			document.location="tGoodsCombinationController.do?goAdd";
		  }else{
				$.messager.alert('错误提示', data.msg);
		  }
	}

	
	var m_pic_no = 0;//已经上传的主图的张数
	$(function () {
	//上传小图
	    $('#templatePic_main_small').uploadify({buttonText:'浏览小图',
	    	queueID:'progress_bar_m_samll',
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
	                var div_imgs = "<img src='"+d.msg+"' width='100' height='100' ></img>"
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
	  
		//上传主图
	    $('#templatePic_main').uploadify({buttonText:'浏览主图',
	    	queueID:'progress_bar_m',
	        progressData:'speed',
	        height:25,
	        overrideEvents:['onDialogClose'],
	        fileTypeDesc:'文件格式:',
	        fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
	        fileSizeLimit:'210KB',
	        swf:'plug-in/uploadify/uploadify.swf',
	        uploader:'tGoodsController.do?uploadMainPic&sessionId=${pageContext.session.id}',
	        auto:true,
	        multi:true,
	        queueSizeLimit:5,
	        onUploadSuccess : function(file, data, response) {
	                var d=$.parseJSON(data);
	            if(d.success){
	            	m_pic_no++;
	            	
//	                 var n = $("#main_pics").find("div").length;
//	                 var left_ = n==0?0:n*(200+40);//初始化图片位置，用于让图片在一行显示
	                var div_imgs = "<div class='brick' ><input type='hidden' value=''/><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+d.msg+"' width='200' height='200' ></img></div>"
					$("#main_pics").append(div_imgs);
	                $('#main_pics').gridly();
				    if(m_pic_no>=5){
				    	$("#templatePic_main").hide();
				    }else{
				    $('#templatePic_main').uploadify('settings','queueSizeLimit',5-m_pic_no);
				    }
				    };
	            },
	        onFallback : function() {
				tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
			},
			onSelectError : function(file, errorCode, errorMsg) {
				switch (errorCode) {
				case -100:
					$.messager.alert('错误提示',
							'上传图片总共不能超过5个');
					break;
				case -110:
					$.messager.alert('错误提示',
							'文件 大小超出系统限制的210KB大小');
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
	});  
	
	</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password"  layout="table" action="tGoodsCombinationController.do?doAdd" tiptype="4"  callback="goAdd">
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tGoodsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tGoodsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tGoodsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tGoodsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tGoodsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tGoodsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="cityId" name="cityId" type="hidden" value="${tGoodsPage.cityId}">
					<input id="provinceId" name="provinceId" type="hidden" value="${tGoodsPage.provinceId}">
					<input id="publishStatus" name="publishStatus" type="hidden" value="1"><!--发布状态 默认 为1，1：立即发布，2：存草稿 -->
					<input id="retailerId" name="retailerId" type="hidden" value=""><!-- -->
					<input id="brandId" name="brandId" type="hidden" value="${tGoodsPage.brandId}">
					<input id="brandCode" name="brandCode" type="hidden" value="${tGoodsPage.brandCode}"><!-- 品牌编码 -->
					<input id="groupSource" name="groupSource" type="hidden" value="${groupSource }"><!--组合来源   1：零售商录入，2：后台系统录入 -->
					<input id="goodsCollect" name="goodsCollect" type="hidden" value="0">
					<input id="noSenseNum" name="noSenseNum" type="hidden" value="0">
					<input id="goodNum" name="goodNum" type="hidden" value="0">
					<input id="goodsStar" name="goodsStar" type="hidden" value="0">
					<input id="scoresNum" name="scoresNum" type="hidden" value="0">
					<input id="isShow" name="isShow" type="hidden" value="N"><!--是否显示广告位 -->
					<input id="isSpecial" name="isSpecial" type="hidden" value="N"><!--是否特殊商品 -->
					<input id="goodsType" name="goodsType" type="hidden" value="2"><!-- 组合 -->
					<input id="newGoodsType" name="newGoodsType" type="hidden" value="1"><!-- 旧商品模块 -->
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
							<tr>
								<td colspan="6" height="40px">
									<h2>基础信息</h2>
								</td>
							</tr>
							<tr>
								<td rowspan="3" width="120px" align="right">
									<label class="Validform_label">
										组合小图:
									</label>
								</td>
								<td rowspan="3" width="180px">
									<div>
										<input type="file"  id="templatePic_main_small" />
										<input type="hidden" name="smallPic" id="smallPic">
										<div id="progress_bar_m_small" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
			        					</div>
			        					<div  id="main_pics_small" style="min-height: 100px;">
			        					</div>
							     		<div><span>商品小图450*450，100KB以内</span></div>
								</td>
								<td align="right" width="120px">
									<label class="Validform_label">
										组合类目:
									</label>
								</td>
								<td class="value" width="550px">
										<select name="topCategoryId"  onchange="getUnderCategories(this.value,'2','subCategoryId')" datatype="*">
											<option value="">---请选择---</option>
											<c:forEach var="category" items="${categoryList}">
											<option value="${category[0] }">${category[1]}</option>
											</c:forEach>
										</select>
										<select name="subCategoryId" id="subCategoryId"  datatype="*" onchange="getUnderCategories(this.value,'3','thridCategoryId')" style="display: none;">
										</select>
										<select name="thridCategoryId" id="thridCategoryId" datatype="*" style="display: none;">
										</select>
										<label class="Validform_label" style="display: none;">组合类目</label>
								</td>
								<td align="right">
									<label class="Validform_label">
										组合款号:
									</label>
								</td>
								<td class="value">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px" maxlength="20"  datatype="z" >
										<span class="Validform_checktip">20位以内字母数字组合</span>
										<label class="Validform_label" style="display: none;">组合款号</label>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合名称:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px"  datatype="*"  maxlength="14">
										<span class="Validform_checktip">14字以内(含)</span>
										<label class="Validform_label" style="display: none;">组合名称</label>
									</td>
									<td align="right">
										<label class="Validform_label">
										原价/组合价:
									</label>
									</td>
									<td class="value">
									<input id="originalPrice" name="originalPrice" type="text" style="width: 100px;background: #D7D7D7"  readonly="readonly">
									     	 	/ &nbsp;<input id="groupPrice" name="groupPrice" type="text" style="width: 100px;background: #D7D7D7" datatype="*" readonly="readonly">
										<span class="Validform_checktip">通过添加单品后合计</span>
										<label class="Validform_label" style="display: none;">组合价</label>
										</td>
								</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合类别:
									</label>
								</td>
								<td class="value">
								<t:dictSelect field="subGoodsType" id="subGoodsType" type="list" typeGroupCode="subgdstype"  hasLabel="false"></t:dictSelect>
<!-- 								     	 <input id="subGoodsType" name="subGoodsType" type="text" style="width: 150px"  > -->
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">组合类别</label>
								</td>
								<td align="right">
									<c:if test="${groupSource == '2'}">
										<label class="Validform_label">
											零售商:
										</label>
									</c:if>
									<c:if test="${groupSource == '1'}">
										<label class="Validform_label">
											主品牌:
										</label>
									</c:if>
								</td>
								<td class="value">
									<c:if test="${groupSource == '2'}">
									<input id="retailerName" name="retailerName" type="text" style="width: 150px" readonly="readonly" />
										<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findRetailers()" ><span class="icon-search l-btn-icon-left">选择零售商</span></a>
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">零售商</label>
									</c:if>
									<c:if test="${groupSource == '1'}">
										<input id="brandName" name="brandName" type="text" style="width: 150px" datatype="*" readonly="readonly" >
										<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" ><span class="icon-search l-btn-icon-left">选择</span></a>
										<span class="Validform_checktip">可选择该组合中的一个主要品牌</span>
										<label class="Validform_label" style="display: none;">主品牌</label>
									</c:if>
								</td>
							</tr>
							<c:if test="${groupSource == '2'}">
							<tr>
								<td align="right">
									<label class="Validform_label">
										主品牌:
									</label>
								</td>
								<td class="value" colspan="5">
								     	<input id="brandName" name="brandName" type="text" style="width: 150px" datatype="*" readonly="readonly" >
									<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" ><span class="icon-search l-btn-icon-left">选择</span></a>
										<span class="Validform_checktip">可选择该组合中的一个主要品牌</span>
										<label class="Validform_label" style="display: none;">主品牌</label>
								</td>
							</tr>
							</c:if>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合描述:
									</label>
								</td>
								<td class="value" colspan="5">
<!-- 								<textarea rows="10" cols="200" style="width: 98%" id="goodsDesc" name="desc.goodsDesc"></textarea> -->
								     	<input id="goodsDesc" name="desc.goodsDesc" type="text" maxlength="35" style="width: 500px;"/>
<!-- 								     	<script id="content"   type="text/plain" style="width:99%;"></script> -->
										<span class="Validform_checktip">不超过35字</span>
										<label class="Validform_label" style="display: none;">描述</label>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品组合中单品:
									</label>
								</td>
								<td class="value" colspan="5">
									<div class="pic_list" id="group_pics">
		                               <input type="button" value="添加单品" onclick="findGoods()" class="uploadify-button " style="width: 120px;height: 25px;"/>
		                            </div>	
		                            <div class="gridly2" id="dtl_pics" style="height: 220px;">
	        						</div>
	        						<div><span>单品款数（最多15款）选择的单品图片可以任意拖拽，拖拽后的显示效果可以在商品组合详情页相册查看</span></div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品组合主图:
									</label>
								</td>
								<td class="value" colspan="5">
									<div>
								<input type="file" name="templatePic_main" id="templatePic_main" class="uploadify-button "/>
								<div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
	        					</div>
	        					<div class="gridly" id="main_pics" style="height: 200px;">
	        					</div>
					     		<div><span>组合主图（750*750，1至5张，每张210KB内）上传的组合图片可以任意拖拽，拖拽后的显示效果可以在商品组合详情页相册查看</span></div>
								</td>
							</tr>
							
					</table>
			<div style="width: auto;min-height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tGoodsCombinationController.do?tGoodsAttrDetails&id=${tGoodsPage.id}" icon="icon-search" title="组合关键词" id="tGoodsAttrDetails"></t:tab>
				</t:tabs>
			</div>
	
			
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
			<a href="#" class="easyui-linkbutton"  iconCls="icon-save" onclick="sub()">提交</a> 
			<a href="#" class="easyui-linkbutton"  iconCls="icon-save" onclick="save()">存草稿</a> 
<!-- 			<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a> -->
		</div>
		</t:formvalid>
		
		
						<!-- 添加 关键词 模版 -->
	<table style="display:none">
	<tbody id="add_TgoodsAttrDetail_table_template">
		<tr>
		<td align="center"><div style="width: 25px;" name="xh"></div><input style="width:20px;" type="checkbox" name="ck"/> </td>
				  <td align="right">关键词：</td>
				  <td align="left">
					  	<input name="tGoodsAttrDetails[#index#].careValue" maxlength="50" 
					  		type="text"   style="width:120px;" >
				  </td>
				  <td align="right">关键词：</td>
				  <td align="left">
					  	<input name="tGoodsAttrDetails[#index#].careValue" maxlength="50" 
					  		type="text"   style="width:120px;" >
				  </td>
				  <td align="right">关键词：</td>
				  <td align="left">
					  	<input name="tGoodsAttrDetails[#index#].careValue" maxlength="50" 
					  		type="text"   style="width:120px;" >
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
  <script src = "webpage/com/buss/goods/tGoodsCombination.js?v=20161101"></script>	
  