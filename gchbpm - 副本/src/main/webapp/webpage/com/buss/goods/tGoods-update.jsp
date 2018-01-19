<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
    <script type="text/javascript" src="plug-in/gridly/jquery.gridly.js" ></script><!-- 拖动控件 -->
	<link href="plug-in/gridly/css/jquery.gridly.css" rel="stylesheet" type="text/css" />
	<script src="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script>
	<link href="plug-in/jquery/jquery-autocomplete/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
	<script src="plug-in/Validform/js/common.js"></script>
	<style type="text/css">
	html, body, div, span, iframe, h1, h2, h3, h4, h5, h6, p, img  {
	    margin: 0;
	    padding: 0;
	    border: 0;
	    font-size: 100%;
	    font: inherit;
	  }
	 img{display:block;} 
	 #content_view p{
		margin: 0;
	 }
	</style>
  <script type="text/javascript">
  //编写自定义JS代码
  function goToChoose(){
	  window.location="tGoodsController.do?goStep1";
	  return false;
  }
  function goback(){
	  window.location="tGoodsController.do?retailerList&goods_status=${tGoodsPage.goodsStatus}";
	  return false;
  }
 //校验规格和库存 
 /*  function checkSpec(){
	  var store_trs =  $("#add_TgoodsStoreDetail_table tr:visible");
	  var nullNum = 0;
	  $(store_trs).each(function(){
		  var $num = $(this).find("td").eq(5).find("input[name$='store']");
		  var num = $.trim($num.val());
		  var $text = $(this).find("td").eq(1).find("input[name$='specificationOne']");//规格1不能为空
		  var text = $.trim($text.val());
		  if(text==""){
			  nullNum++;
			  $.messager.alert('提示信息',"请填写规格","",function(){
				  $text.focus();
			  });
			  return false;
		  }
		  if(num==""||num=="0"){
			  nullNum++;
			  $.messager.alert('提示信息',"请填写库存","",function(){
				  $num.focus();
			  });
			  return false;
		  }
	  });
	  return nullNum;
  }
  */
  //提交
  function sub(){
	  var goodsStock = $("#goodsStock").val();
	  if(goodsStock==""||goodsStock=="0"){
		  $.messager.alert('提示信息',"请填写库存明细");
		  return false;
	  }
	  var nullNum = checkSpec();
	  if(nullNum==0){
	  	$("#publishStatus").val("1");
		$("#formobj_gu").submit();
	  }
  }
  
  //存草稿
  function save(){
	  var nullNum = checkSpec();
	  if(nullNum==0){
	  	$("#publishStatus").val("2");
		$("#formobj_gu").submit();
	  }
  }
  
function backList(data) {
		 if(data.success){
			document.location="tGoodsController.do?retailerList&goods_status=${tGoodsPage.goodsStatus}";
		}else{
			$.messager.alert('错误提示', data.msg);
		}
	}

	var m_pic_num = ${mainPicsNum};//已经上传的主图的张数
		
	$(function () {
// 		$("input:text").click(function(){
// 		    $(this).select();
// 		});

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
	                var div_imgs = "<div><img src='"+d.msg+"' width='100' height='100' ></img></div>"
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
		
		//上传主图
	    $('#templatePic_main').uploadify({buttonText:'浏览图片',
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
	        queueSizeLimit:5-m_pic_num,
	        onUploadSuccess : function(file, data, response) {
	                var d=$.parseJSON(data);
	            if(d.success){
	            	m_pic_num++;
	            	
//	                 var n = $("#main_pics").find("div").length;
//	                 var left_ = n==0?0:n*(200+40);//初始化图片位置，用于让图片在一行显示
	                var div_imgs = "<div class='brick' ><input type='hidden' value=''/><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+d.msg+"' width='200' height='200' ></img></div>"
					$("#main_pics").append(div_imgs);
	                $('#main_pics').gridly().css("height","200px");;
				    if(m_pic_num>=5){
				    	$("#templatePic_main").hide();
				    }else{
				    $('#templatePic_main').uploadify('settings','queueSizeLimit',5-m_pic_num);
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
		
	    findParams();
	    
	});
	
	//获取类目参数列表
	function findParams(){
		var url = "tAllGoodsParamsController.do?findParams&category_id=${tGoodsPage.thridCategoryId}";
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
//	 			alert("error");
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var d = $.parseJSON(data);
					if (d.success) {
						var div_content = "";
						var num_select = 0;//select的个数
						var num_input = 0;//input的个数
							var vals_1="";
							var vals_2="";
							var vals_3="";
						for(i=0;i<d.obj.length;i++){
							var values = "";
							if(d.obj[i].multiSelect=="1"||d.obj[i].multiSelect=="0"){
								values= d.obj[i].paramValues.replace(/，/g,',');
								var str = values.split(",");
							}else{
								values= d.obj[i].paramValues;
							}
							if(d.obj[i].multiSelect=="1"){//是多选
//	 							var list_map = new Array();
								vals_1+="<div style='height:35px'><div style='width:100px;float: left;text-align:right;padding-right:3px'>"+d.obj[i].paramName
								+"：</div><div style='float: left'><input type='hidden' name='tGoodsAttrDetails["+i+"].careCode' value='"+d.obj[i].paramName+"'/>";
								for(n=0;n<str.length;n++){
//	 								list_map.push(text=str[n]);
//	 								list_map.push({text:str[n]});
									vals_1+="<input type='checkbox' name='tGoodsAttrDetails["+i+"].careValue' key='"+d.obj[i].paramName+"' value='"+str[n]+"'/>"+str[n]+"&nbsp;&nbsp;&nbsp;";
									if(n%10==9){
										vals_1+="</br>";
									}
								}
								vals_1+="</div></div>";
//	 							var ss = $.parseJSON(list_map);
							}else if(d.obj[i].multiSelect=="0"){//是单选
								if(num_select==0||num_select%5==0){
									vals_2 +="<div style='height:35px'>";
								}
								vals_2+="<div style='width:100px;float: left;text-align:right;padding-right:3px'>"+d.obj[i].paramName+"：</div><input type='hidden' name='tGoodsAttrDetails["+i+"].careCode' value='"+d.obj[i].paramName+"'/>";
								vals_2 += "<div style='width:130px;float: left'><select name='tGoodsAttrDetails["+i+"].careValue' key='"+d.obj[i].paramName+"'>";
								for(n=0;n<str.length;n++){
									vals_2+="<option value='"+str[n]+"'>"+str[n]+"</option>";
								}
								vals_2 +="</select></div>";
								if(num_select%5==4){//每行5个
									vals_2+="</div>";
								}
								num_select++;
							}else if(d.obj[i].multiSelect=="2"){//是输入框
								if(num_input==0||num_input%5==0){
									vals_3 +="<div style='height:35px'>";
								}
								vals_3+="<div style='width:100px;float: left;text-align:right;padding-right:3px'>"+d.obj[i].paramName+"：</div><input type='hidden' name='tGoodsAttrDetails["+i+"].careCode' value='"+d.obj[i].paramName+"'/>";
									vals_3+="<div style='width:130px;float: left'><input type='text' maxlength='20' name='tGoodsAttrDetails["+i+"].careValue' key='"+d.obj[i].paramName+"' /></div>";
									if(num_input%5==4){//每行5个
										vals_3+="</div>";
									}
								num_input++;
							}
						}
						if(vals_2!=""){
							div_content+=vals_2+"</div>";
						}
						if(vals_1!=""){
							div_content+=vals_1;
						}
						if(vals_3!=""){
							div_content+=vals_3+"</div>";
						}
						$("#params").html(div_content);
					selectAttrs();
				}
			}
			}
		});
	}
	
	//选中值
function selectAttrs(){
	<c:if test="${fn:length(tGoodsPage.tGoodsAttrDetails)  > 0 }">
	<c:forEach items="${tGoodsPage.tGoodsAttrDetails}" var="poVal" varStatus="stuts">
		var attr_code = "${poVal.careCode}";
		var attr_value = "${poVal.careValue}";
		var input_obj = $("#params").find("input[key='"+attr_code+"']");
		if($(input_obj).length==0){//下拉框
			var select_obj = $("#params").find("select[key='"+attr_code+"']");
			$(select_obj).val(attr_value);
		}else if($(input_obj).length==1){//输入框
			$(input_obj).val(attr_value);
		}else{//多选的
			attr_value = attr_value.replace(/，/g,',');
			if(attr_value.indexOf(',')>0){//多选的
				var str = attr_value.split(",");
				var checkbox_obj = $("#params").find("input[type='checkbox'][key='"+attr_code+"']");
				$(checkbox_obj).each(function(i,n){
					for(i=0;i<str.length;i++){
						if($(this).val()==str[i]){
							$(this).attr("checked",true);
						}
					}
				})
			}else{
				var checkbox_obj = $("#params").find("input[type='checkbox'][key='"+attr_code+"'][value='"+attr_value+"']");
				$(checkbox_obj).attr("checked",true);
			}
		}
	</c:forEach>
	</c:if>	
	}
	

  </script>
  
 
 </head>
 <body>
  <t:formvalid formid="formobj_gu" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tGoodsController.do?doUpdate" tiptype="4" beforeSubmit="initPicsOrder();" callback="backList">
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
					<input id="categries" name="categries" type="hidden" value="${tGoodsPage.categries}"><!-- 可见类目  c1ID,c2ID,c3ID循环的-->
					<input id="brandCode" name="brandCode" type="hidden" value="${tGoodsPage.brandCode}"><!-- 品牌编码 -->
					<input id="publishStatus" name="publishStatus" type="hidden" value="${tGoodsPage.publishStatus}"><!--1：立即发布，2：存草稿 -->
					<input id="newGoodsType" name="newGoodsType" type="hidden" value="1"><!-- 旧商品模块 -->
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
										<span>${topCategory} ${subCategory} ${thridCategory}</span>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goToChoose()" >编辑</a>										
										<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true" style="margin-left: 50px" onclick="setVisibleCatgs('${tGoodsPage.id }')" ><span class="icon-search l-btn-icon-left">选择可见类目</span></a>
								</td>
								<%-- 
								<td align="right">
									<label class="Validform_label" width="100px">
										场景:
									</label>
								</td>
								<td class="value" width="430px">
										  <t:dictSelect field="sceneType" type="checkbox"
												typeGroupCode="sceneType" defaultVal="${tGoodsPage.sceneType}" hasLabel="false"  title="场景"></t:dictSelect>     
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">场景</label>
									</td>
									 --%>
								<td align="right" width="100">
									<label class="Validform_label">
										品牌:
									</label>
								</td>
								<td class="value">
									<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" value="${tGoodsPage.brandName}" datatype="*">
									<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findBrands()" id=""><span class="icon-search l-btn-icon-left">选择</span></a>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">品牌</label>
								</td>	
							</tr>
							<tr>
							<td align="right" rowspan="4" width="100">
									<label class="Validform_label">
										商品小图:
									</label>
								</td>
									<td rowspan="4" width="300">
										<div>
										<input type="file"  id="templatePic_main_small" />
										<input type="hidden" name="smallPic" id="smallPic" value="${tGoodsPage.smallPic }">
										<div id="progress_bar_m_small" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
			        					</div>
			        					<div  id="main_pics_small" style="min-height: 100px;line-height: 0;">
			        					<c:if test="${not empty tGoodsPage.smallPic }">
			        						<img src='${tGoodsPage.smallPic }' width='100' height='100' ></img>
			        					</c:if>
			        					</div>
							     		<div><span class="Validform_checktip">商品小图450*450，100KB以内</span></div>
									</td>
								<td align="right" width="100">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value" width="400">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px"  value="${tGoodsPage.goodsCode}" maxlength="20"  datatype="z">
										<span class="Validform_checktip">20位以内字母数字组合</span>
										<label class="Validform_label" style="display: none;">商品款号</label>
								</td>
								<td align="right">
									<label class="Validform_label">
										商品名称:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}" datatype="*"  maxlength="14">
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
										库存:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsStock" name="goodsStock" value="${tGoodsPage.goodsStock}" type="text" style="width: 150px;background: #D7D7D7"  readonly="readonly">
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">库存</label>
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
										特殊商品:
									</label>
								</td>
								<td class="value">
									<t:dictSelect field="isSpecial" id="isSpecial" type="list" typeGroupCode="sf_yn"  hasLabel="false" defaultVal="${tGoodsPage.isSpecial}" datatype="*"></t:dictSelect>
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">特殊商品</label>
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
										商品主图:
									</label>
								</td>
								<td class="value" colspan="5">
								<div>
								<input type="file" name="templatePic_main" id="templatePic_main" class="uploadify-button"/>
								<div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
	        					</div>
	        					<div class="gridly" id="main_pics" style="height: 200px;">
	        					<c:if test="${not empty tGoodsPage.picOne  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picOne" value='${tGoodsPage.picOne }'/><a class='delete' onclick='delPic(this)' href='#'>×</a>
	        					<img src='${tGoodsPage.picOne }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picTwo  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picTwo" value='${tGoodsPage.picTwo }'/><a class='delete' onclick='delPic(this)' href='#'>×</a>
	        					<img src='${tGoodsPage.picTwo }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picThree  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picThree" value='${tGoodsPage.picThree }'/><a class='delete' onclick='delPic(this)' href='#'>×</a>
	        					<img src='${tGoodsPage.picThree }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picFour  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picFour" value='${tGoodsPage.picFour }'/><a class='delete' onclick='delPic(this)' href='#'>×</a>
	        					<img src='${tGoodsPage.picFour }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picFive  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picFive" value='${tGoodsPage.picFive }'/><a class='delete' onclick='delPic(this)' href='#'>×</a>
	        					<img src='${tGoodsPage.picFive }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					</div>
					     		<div><span class="Validform_checktip">商品主图要求：750*750，每张210KB以内，1～5张。点击图片并拖动可排序</span></div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品参数:
									</label>
								</td>
								<td class="value" colspan="5">
								<div id="params"></div>
									
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
				<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
						<tr>
								<td align="right">
									<label class="Validform_label">
										商品描述:
									</label>
								</td>
								<td class="value" colspan="5">
								     	<input  name="desc.id" type="hidden" value="${tGoodsPage.desc.id}">
								     	<input  name="desc.goodsId" type="hidden" value="${tGoodsPage.desc.goodsId}">
								     	<input id="goodsDesc" name="desc.goodsDesc" type="hidden" >
								     	<script id="content"   type="text/plain" style="width:99%;">
						${tGoodsPage.desc.goodsDesc == NULL || tGoodsPage.desc.goodsDesc == '' ? '' : tGoodsPage.desc.goodsDesc}
									</script>
									<a href="#" class="easyui-linkbutton l-btn" id="btn_view" onclick="view()" iconCls="icon-msg">预览</a>
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">描述</label>
								</td>
							</tr>
				</table>
		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" >
		<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" >提交</a>&nbsp;
		<a href="#" class="easyui-linkbutton" onclick="save()" iconCls="icon-save" >存草稿</a>&nbsp;
		<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a>
		</div>
		</t:formvalid>
	
<div id="content_view" style="display: none;background: gray" class="modal fade in"  tabindex="-1" >		
	<div   class="modal-dialog" style="width:360px;">
		<div class="modal-content" >
			<div class="modal-header">
                <button type="button" class="close" onclick="closeDiv()" aria-label="Close"><span aria-hidden="true">×</span></button>
                <h4 class="modal-title" id="myModalLabel">图文预览</h4>
            </div>
            <div class="modal-body" id="preview-msg-content" style="overflow-y: auto; overflow-x: hidden; padding: 0px; margin: 10px; height: 400px;word-break:break-all">
			</div>
			<div class="modal-footer">
                <p class="text-center text-danger">此处预览请作为参考，最终效果已手机为准</p>
            </div>
         </div>
	</div>
</div>		
		
		
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
  <script src = "webpage/com/buss/goods/tGoods.js"></script>		