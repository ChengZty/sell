<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品详情</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <style type="text/css">
  .pic_div{
/*   	width: 100px; */
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
 
	$(function () {
	    
	  //初始化运费
// 	    initFare();
	  //初始化产品参数信息
// 		initProductInfo();
		
		$("#goods_view input,select,a").attr("disabled","disabled");
	    
	});
	
	//初始化运费
	/* function initFare(){
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
	} */
	
	//初始化产品信息
	/* function initProductInfo(){
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
	} */
	

  </script>
  
 
 </head>
 <body>
 <div style="width: 1280px;margin: auto;">
  <t:formvalid formid="goods_view"  dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tNewGoodsController.do?doUpdate" tiptype="4" >
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
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
									<td rowspan="3">
		        					<div  id="main_pics_small" style="min-height: 100px;line-height: 0;min-width: 100px;">
		        					<c:if test="${not empty tGoodsPage.smallPic }">
		        						<img src='${tGoodsPage.smallPic }'  height='100' ></img>
		        					</c:if>
		        					</div>
								</td>
								<td align="right" width="100">
									<label class="Validform_label">
										商品类目:
									</label>
								</td>
								<td class="value">
										<span>${topCategory} ${subCategory} ${thridCategory}</span>									
<%-- 										<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true" style="margin-left: 50px" onclick="setVisibleCatgs('${tGoodsPage.id }')" ><span class="icon-search l-btn-icon-left">查看可见类目</span></a> --%>
								</td>
								<td align="right" width="100">
									<label class="Validform_label">
										品牌:
									</label>
								</td>
								<td class="value">
									<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" value="${tGoodsPage.brandName}" datatype="*">
								</td>	
							</tr>
							<tr>
								
								<td align="right">
									<label class="Validform_label">
										商品款号:
									</label>
								</td>
								<td class="value" width="400">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px"  value="${tGoodsPage.goodsCode}" maxlength="20"  datatype="z">
								</td>
								<%-- <td align="right">
									<label class="Validform_label">
										短标题:
									</label>
								</td>
								<td class="value">
							     	 <input id="title" name="title" type="text" style="width: 200px" value="${tGoodsPage.title}" datatype="*"  maxlength="14">
								</td>
								</tr>
								<tr> --%>
								<td align="right">
									<label class="Validform_label">
										商品名称:
									</label>
								</td>
								<td class="value" colspan="3">
							     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}" datatype="*"  maxlength="14">
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
								     	  datatype="money">
									</td>
								<td align="right">
									<label class="Validform_label">
										最低折扣/最低价:
									</label>
								</td>
								<td class="value">
							     	 <input id="lowestPriceDiscount" name="lowestPriceDiscount" type="text" style="width: 20px"
							     	 value='<fmt:formatNumber maxFractionDigits="0" value="${tGoodsPage.lowestPriceDiscount*100}"/>'
							     	    datatype="n" maxlength="2" />%
							     	 /<input id="lowestPrice" name="lowestPrice" type="text" value="${tGoodsPage.lowestPrice}" style="width: 100px" datatype="money" />
									<span class="Validform_checktip">元</span>
									<label class="Validform_label" style="display: none;">最低价</label>
								</td>
								<%-- <td align="right">
									<label class="Validform_label">
										活动价:
									</label>
								</td>
								<td class="value">
								     	 <input id="activityPrice" name="activityPrice" type="text" style="width: 150px" value="${tGoodsPage.activityPrice}" >
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
								<t:dictSelect field="subGoodsType" id="subGoodsType" type="list" typeGroupCode="subgdstype" defaultVal="${tGoodsPage.subGoodsType}" hasLabel="false"></t:dictSelect>
								<t:dictSelect field="isSpecial" id="isSpecial" type="list" typeGroupCode="sf_yn"  hasLabel="false" defaultVal="${tGoodsPage.isSpecial}" ></t:dictSelect>
								</td>
								<td align="right">
									<label class="Validform_label">
										库存:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsStock" name="goodsStock" value="${tGoodsPage.goodsStock}" type="text" style="width: 150px;background: #D7D7D7"  readonly="readonly">
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
											<span style="padding:0px 3px">${po.paramName }:</span>
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
														<input type="text" name="productInfoList[${idx }].paramValues" value="" style="width: 90px"/>
													</c:when>
													<c:when test="${po.inputType =='3' }"><!-- 下拉框 -->
														<input type="text" name="productInfoList[${idx }].paramValues" value="${po.paramValues }"/>
														<input type="hidden"  value="${po.paramValues }"/>
													</c:when>
													<c:when test="${po.inputType =='4' }"><!-- 图片 -->
														<div>
														<div id="progress_bar_${idx }" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
							        					</div>
							        					<div  id="picDivContainer${idx }" style="min-height: 100px;line-height: 0;">
							        					</div>
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
													<c:when test="${po.inputType =='1' }"><!-- 多选 -->
														<c:forTokens items="${po.paramValues }" delims=",|，" var="val">
														<label style="padding: 0px 5px 0px 0px"><input type="checkbox" name="productInfoList[${idx }].paramValues" value="${val }"/>${val }</label>
														</c:forTokens>
													</c:when>
													<c:when test="${po.inputType =='2' }"><!-- 输入框 -->
														<input type="text" name="productInfoList[${idx }].paramValues" value="" style="width: 90px"/>
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
													</c:when>
													<c:when test="${po.inputType =='5' }"><!-- 多选图片 -->
														<c:forTokens items="${po.paramValues }" delims=",|，" var="val">
														<label style="padding: 0px 5px 0px 0px"><input type="checkbox" name="productInfoList[${idx }].paramValues" value="${val }"/>${val }</label>
														</c:forTokens>
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
										商品图片:
									</label>
								</td>
								<td colspan="5">
									<div id="container_1" >
				                            <div id="container_1_div" style="min-height: 100px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='1' }">
														<div class='pic_div'>
															<img src='${poVal.url }'  height='100'/>
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
															<video src='${poVal.url }' width='320' height='180' controls="controls"></video>
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
				 <t:tab href="tGoodsController.do?tGoodsStoreDetailsForUpdate&isView=1&id=${tGoodsPage.id}" icon="icon-search" title="库存情况" id="tGoodsStoreDetailsForUpdate"></t:tab>
				</t:tabs>
			</div>
			<div style="width: auto;min-height: 150px;border:#DDDDDD 1px solid ">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:200px;height:1px;"></div>
				<t:tabs id="t_words" iframe="false" tabPosition="top" fit="false">
					<t:tab href="tFinActivityWordsController.do?tGoodsWordsDetails&goodsId=${tGoodsPage.id}&isView=1" icon="icon-search" title="商品话术" id="tGoodsWordsDetailsForUpdate"></t:tab>
				</t:tabs>
			</div>
			<div style="width: auto;min-height: 150px;border:#DDDDDD 1px solid ">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<span id="tagstitle">商品标签</span><hr>
				<c:forEach var="tag" items="${tagsList }">
					<span class="goodsTags">${tag.tag }</span>
				</c:forEach>
			</div>
		</t:formvalid>
	
		</div>
 </body>
