<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品详情</title>
  <t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript" src="plug-in/gridly/jquery.gridly.js" ></script><!-- 拖动控件 -->
	<link href="plug-in/gridly/css/jquery.gridly.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
    html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, figcaption, figure, footer, header, hgroup, menu, nav, section, summary, time, mark, audio, video {
	    margin: 0;
	    padding: 0;
	    border: 0;
	    font-size: 100%;
	    font: inherit;
	  }
	 img{display:block;} 
	</style>
  <script type="text/javascript">
  //编写自定义JS代码
//   function goback(){
// 	  window.location="tGoodsController.do?${backlist}&goods_status=${goods_status}";
// 	  return false;
//   }
  

	$(function () {
		findParams();
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
				$("#fare_2").html('满${tGoodsPage.goodsFarePreferential}元包邮');
			}else if("2"==type){//递减
				$("#fare_3").html('第一件${tGoodsPage.fare}元，第二件开始每件${tGoodsPage.goodsFarePreferential}元');
			}
		}
		$("#goods_view input,select,a").attr("disabled","disabled");
		
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
					var div_content = "";
					var num_input = 0;//select的个数
						var vals_1="";
						var vals_2="";
					for(i=0;i<d.obj.length;i++){
							var values = d.obj[i].paramValues;
							values = values.replace(/，/g,',');
							var str = values.split(",");
						if(d.obj[i].multiSelect=="1"){//是多选
							vals_1+="<div style='margin:2px'></div><div style='width:100px;float: left;text-align:right;padding-right:3px'>"+d.obj[i].paramName
							+"：</div><div style='float: left'><input type='hidden' name='tGoodsAttrDetails["+i+"].careCode'  value='"+d.obj[i].paramName+"'/>";
							for(n=0;n<str.length;n++){
								vals_1+="<input type='checkbox' name='tGoodsAttrDetails["+i+"].careValue' key='"+d.obj[i].paramName+"' value='"+str[n]+"'/>"+str[n]+"&nbsp;&nbsp;&nbsp;";
								if(n%10==9){
									vals_1+="</br>";
								}
							}
							vals_1+="</div></br>";
						}else{
							if(num_input==0){
								vals_2 ="<div style='height:35px'>";
							}
							vals_2+="<div style='width:90px;float: left;text-align:right;padding-right:3px'>"+d.obj[i].paramName+"：</div><input type='hidden' name='tGoodsAttrDetails["+i+"].careCode' value='"+d.obj[i].paramName+"'/>";
							vals_2 += "<div style='width:130px;float: left'><select name='tGoodsAttrDetails["+i+"].careValue'  key='"+d.obj[i].paramName+"'>";
							for(n=0;n<str.length;n++){
								vals_2+="<option value='"+str[n]+"'>"+str[n]+"</option>";
							}
							vals_2 +="</select></div>";
							if(num_input%5==4){//每行5个
								vals_2+="</div><div style='height:35px'>";
							}
							num_input++;
						}
					}
					div_content+=vals_2+"</div><div>"+vals_1+"</div>";
					$("#params").html(div_content);
					selectAttrs();
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
			var select_obj = $("#params").find("select[key='"+attr_code+"']");
			$(select_obj).val(attr_value);
		}
	</c:forEach>
	</c:if>	
	}
  </script>
  
 
 </head>
 <body>
 <div style="width: 1280px;margin: auto;">
  <t:formvalid formid="goods_view" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
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
										<span>${topCategory} ${subCategory} ${thridCategory}</span>	
										<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true" style="margin-left: 50px" onclick="setVisibleCatgs('${tGoodsPage.id }')" ><span class="icon-search l-btn-icon-left">查看可见类目</span></a>							
								</td>
								<%-- 
								<td align="right">
									<label class="Validform_label">
										场景:
									</label>
								</td>
								<td class="value">
										  <t:dictSelect field="sceneType" type="checkbox"
												typeGroupCode="sceneType" defaultVal="${tGoodsPage.sceneType}" hasLabel="false"  title="场景"></t:dictSelect>     
										<span class="Validform_checktip"></span>
										<label class="Validform_label" style="display: none;">场景</label>
									</td>
									 --%>
								<td align="right">
									<label class="Validform_label">
										品牌:
									</label>
								</td>
								<td class="value">
									<input id="brandName" name="brandName" type="text" style="width: 150px"   value="${tGoodsPage.brandName}" datatype="*">
								</td>	
							</tr>
							<tr>
							<td align="right" rowspan="4">
									<label class="Validform_label">
										商品小图:
									</label>
								</td>
									<td rowspan="4" width="300">
										<div>
			        					</div>
			        					<div  id="main_pics_small" style="min-height: 100px;">
			        					<c:if test="${not empty tGoodsPage.smallPic }">
			        						<img src='${tGoodsPage.smallPic }' width='100' height='100' ></img>
			        					</c:if>
			        					</div>
									</td>
								<td align="right" width="100">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value" width="400">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px" value="${tGoodsPage.goodsCode}" datatype="*">
								</td>
								<td align="right" width="100">
									<label class="Validform_label">
										商品名称:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}" datatype="*">
									</td>
									
								</tr>
							<tr>
							<td align="right">
									<label class="Validform_label">
										原价:
									</label>
									</td>
									<td class="value">
									     	 <input id="originalPrice" name="originalPrice" type="text" style="width: 150px" value="${tGoodsPage.originalPrice}" datatype="n">
										</td>
								<td align="right">
									<label class="Validform_label">
										现价:
									</label>
								</td>
								<td class="value">
								     	 <input id="currentPrice" name="currentPrice" type="text" style="width: 150px" value="${tGoodsPage.currentPrice}" datatype="n">
									</td>
								
							</tr>
							<tr>
							<td align="right">
									<label class="Validform_label">
										最低价:
									</label>
								</td>
								<td class="value">
								     	 <input id="lowestPrice" name="lowestPrice" type="text" style="width: 150px" value="${tGoodsPage.lowestPrice}" >
									</td>
									<td align="right">
									<label class="Validform_label">
										库存:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsStock" name="goodsStock" type="text" style="width: 150px" value="${tGoodsPage.goodsStock}" >
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
									<span id="fare_1" style="display: none">单价：${tGoodsPage.fare}元/件</span>
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
										商品图片:
									</label>
								</td>
								<td class="value" colspan="5">
								<div>
<!-- 								<input type="file" name="templatePic_main" id="templatePic_main" /> -->
								<div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
	        					</div>
	        					<div class="gridly" id="main_pics" style="height: 200px;">
	        					<c:if test="${not empty tGoodsPage.picOne  }">
	        					<div class='brick' style="float: left;margin-right: 5px;">
	        					<img src='${tGoodsPage.picOne }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picTwo  }">
	        					<div class='brick' style="float: left">
	        					<img src='${tGoodsPage.picTwo }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picThree  }">
	        					<div class='brick' style="float: left">
	        					<img src='${tGoodsPage.picThree }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picFour  }">
	        					<div class='brick' style="float: left">
	        					<img src='${tGoodsPage.picFour }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picFive  }">
	        					<div class='brick' style="float: left">
	        					<img src='${tGoodsPage.picFive }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					</div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品描述:
									</label>
								</td>
								<td class="value" colspan="5">
								<div style="min-height:100px;">
								
<!-- 								     	<script id="content"   type="text/plain" style="width:99%;"> -->
						${tGoodsPage.desc.goodsDesc == NULL || tGoodsPage.desc.goodsDesc == '' ? '' : tGoodsPage.desc.goodsDesc}
<!-- 									</script> -->
								</div>
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
							<tr>
								<td align="right">
									<label class="Validform_label">
										库存情况:
									</label>
								</td>
								<td class="value" colspan="5">
								<div style="width: auto;min-height: 100px;">
										<table border="0" width="100%" cellpadding="2" cellspacing="0" >
											<tr bgcolor="#E6E6E6">
												<td align="center" bgcolor="#EEEEEE">序号</td>
														  <td align="left" bgcolor="#EEEEEE">
																${tSpecHeader.headerOne}
														  </td>
														  <td align="left" bgcolor="#EEEEEE">
																${tSpecHeader.headerTwo}
														  </td>
														  <td align="left" bgcolor="#EEEEEE">
																${tSpecHeader.headerThree}
														  </td>
														  <td align="left" bgcolor="#EEEEEE">
																预警库存
														  </td>
														  <td align="left" bgcolor="#EEEEEE">
																库存
														  </td>
											</tr>
											<tbody >	
											<c:if test="${fn:length(tGoodsPage.tGoodsStoreDetails)  > 0 }">
												<c:forEach items="${tGoodsPage.tGoodsStoreDetails}" var="poVal" varStatus="stuts">
													<tr>
														<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
														   <td align="left">
															  	<input name="tGoodsStoreDetails[${stuts.index }].specificationOne" maxlength="50" 
															  		type="text"  readonly="readonly" value="${poVal.specificationOne }">
														   </td>
														   <td align="left">
															  	<input name="tGoodsStoreDetails[${stuts.index }].specificationTwo" maxlength="50" 
															  		type="text"  readonly="readonly" value="${poVal.specificationTwo }">
														   </td>
														   <td align="left">
															  	<input name="tGoodsStoreDetails[${stuts.index }].specificationThree" maxlength="50" 
															  		type="text"  readonly="readonly" value="${poVal.specificationThree }">
														   </td>
														   <td align="left">
															  	<input name="tGoodsStoreDetails[${stuts.index }].alarmGoodsStock" maxlength="12" 
															  		type="text"  style="width:120px;"  value="${poVal.alarmGoodsStock }">
														   </td>
														   <td align="left">
															  	<input name="tGoodsStoreDetails[${stuts.index }].store" maxlength="12" 
															  		type="text"  style="width:120px;"  value="${poVal.store }">
														   </td>
										   			</tr>
												</c:forEach>
											</c:if>	
											</tbody>
										</table>
									</div>
								</td>
							</tr>
				<c:if test="${not empty tGoodsPage.tSysRecmdPics}">
					<tr>
								<td align="right" >
									<label class="Validform_label">
										系统推荐:
									</label>
								</td>
								<td class="value" colspan="5" >
		                            <div class="gridly2" id="sys_recommond_pics" style="height: 160px;margin-top: 5px;margin-bottom: -10px">
		                            <c:forEach items="${tGoodsPage.tSysRecmdPics}" varStatus="status" var="sysRecmdPic"> 
									    	 <div class='brick3' >	
									    	 	<img src='${sysRecmdPic.picUrl }' width='140' height='140' ></img>
												<script>$('#sys_recommond_pics').gridly()</script>
											</div>
									</c:forEach>
	        						</div>
								</td>
							</tr>
				</c:if>
				<c:if test="${not empty tGoodsPage.tGuideRecmdPics}">			
					<tr>
								<td align="right">
									<label class="Validform_label">
										管家秀:
									</label>
								</td>
								<td class="value" colspan="5" >
		                            <div class="gridly2" id="guide_recommond_pics" style="height: 170px;">
		                            	<c:forEach items="${tGoodsPage.tGuideRecmdPics}" varStatus="status" var="guideRecmdPic"> 
									    	 <div class='brick4' >	
												<img src='${guideRecmdPic.picUrl }' width='105' height='140' ></img>
												拍摄人：<input type='text'   style='width:50px;height: 20px;' value="${guideRecmdPic.userName }"/>
												<script>$('#guide_recommond_pics').gridly().css("height","170px")</script>
											</div>
									</c:forEach>
	        						</div>
								</td>
							</tr>
				</c:if>							
				</table>
				
<!-- 		<div style="padding: 3px; height: 25px;width:auto;text-align: center;" > -->
<!-- 		<a href="#" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back" onclick="goback()" >返回</a> -->
<!-- 		</div> -->
		</t:formvalid>
 </div>
 </body>
<script src = "webpage/com/buss/goods/tGoods.js"></script>	