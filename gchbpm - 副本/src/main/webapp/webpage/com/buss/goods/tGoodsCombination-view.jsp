<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>组合详情</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript" src="plug-in/gridly/jquery.gridly.js" ></script><!-- 拖动控件 -->
  <link href="plug-in/gridly/css/jquery.gridly.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  //编写自定义JS代码
  function goback(){
	  window.location="tGoodsCombinationController.do?${backlist}&goods_status=${goods_status}";
	  return false;
  }
// 	var d_pic_no = 0;//已经上传的明细图的张数	
	$(function () {
			$("#formobj input,select,a").attr("disabled","disabled");
			$('#main_pics').gridly();
	})
  </script>
  
 
 </head>
 <body>
 <div style="width: 1280px;margin: auto;">
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tGoodsCombinationController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
							<tr>
								<td colspan="6" height="40px">
									<h2>基础信息</h2>
								</td>
							</tr>
							<tr>
								<td align="right" rowspan="3" width="120px">
									<label class="Validform_label">
										组合小图:
									</label>
								</td>
									<td rowspan="3" width="180px">
			        					<c:if test="${not empty tGoodsPage.smallPic }">
			        						<img src='${tGoodsPage.smallPic }' width='100' height='100' ></img>
			        					</c:if>
			        					</div>
									</td>
								<td align="right" width="120px">
									<label class="Validform_label">
										组合类目:
									</label>
								</td>
								<td class="value" width="350px">
										${category}	${subCategory}	${thridCategory}
								</td>
								<td align="right">
									<label class="Validform_label">
										组合款号:
									</label>
								</td>
								<td class="value">
								     	<input id="goodsCode" name="goodsCode" type="text" style="width: 150px"  value="${tGoodsPage.goodsCode}" maxlength="20"  datatype="z">
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合名称:
									</label>
								</td>
								<td class="value">
								     	 <input id="goodsName" name="goodsName" type="text" style="width: 300px" value="${tGoodsPage.goodsName}" datatype="*">
									</td>
									<td align="right">
									<label class="Validform_label">
										原价/组合价:
									</label>
								</td>
								<td class="value">
								 <input id="originalPrice" name="originalPrice" type="text" style="width: 150px" value="${tGoodsPage.originalPrice}" datatype="d">
								     	 /&nbsp;<input id="groupPrice" name="groupPrice" type="text" style="width: 150px" value="${tGoodsPage.groupPrice}">
									</td>
								</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合类别:
									</label>
								</td>
								<td class="value">
								<t:dictSelect field="subGoodsType" id="subGoodsType" type="list" typeGroupCode="subgdstype" defaultVal="${tGoodsPage.subGoodsType}" hasLabel="false"></t:dictSelect>
								</td>
								<td align="right">
									<c:if test="${tGoodsPage.groupSource =='2' }">
										<label class="Validform_label">
											零售商:
										</label>
									</c:if>
									<c:if test="${tGoodsPage.groupSource =='1' }">
										<label class="Validform_label">
											主品牌:
										</label>
									</c:if>
								</td>
								<td class="value">
									<c:if test="${tGoodsPage.groupSource =='2' }">
										${tGoodsPage.retailerName }
									</c:if>
									<c:if test="${tGoodsPage.groupSource =='1' }">
										<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" value="${tGoodsPage.brandName}">
									</c:if>
								</td>
							</tr>
							<c:if test="${tGoodsPage.groupSource == '2'}">
							<tr>
								<td align="right">
									<label class="Validform_label">
										主品牌:
									</label>
								</td>
								<td class="value" colspan="5">
								     	<input id="brandName" name="brandName" type="text" style="width: 150px" readonly="readonly" value="${tGoodsPage.brandName}">
								</td>
							</tr>
							</c:if>
							<tr>
								<td align="right">
									<label class="Validform_label">
										描述:
									</label>
								</td>
								<td class="value" colspan="5">
								     	<input id="goodsDesc" name="desc.goodsDesc" type="text" maxlength="35" style="width: 500px;" value="${tGoodsPage.desc.goodsDesc}"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合图片:
									</label>
								</td>
								<td class="value" colspan="5">
								<div>
								<div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
	        					</div>
	        					<div class="gridly" id="main_pics" style="height: 200px;">
	        					<c:if test="${not empty tGoodsPage.picOne  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picOne" value='${tGoodsPage.picOne }'/>
	        					<img src='${tGoodsPage.picOne }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picTwo  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picTwo" value='${tGoodsPage.picTwo }'/>
	        					<img src='${tGoodsPage.picTwo }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picThree  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picThree" value='${tGoodsPage.picThree }'/>
	        					<img src='${tGoodsPage.picThree }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picFour  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picFour" value='${tGoodsPage.picFour }'/>
	        					<img src='${tGoodsPage.picFour }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					<c:if test="${not empty tGoodsPage.picFive  }">
	        					<div class='brick' >
	        					<input type='hidden' name="picFive" value='${tGoodsPage.picFive }'/>
	        					<img src='${tGoodsPage.picFive }' width='200' height='200' ></img>
	        					</div>
	        					</c:if>
	        					</div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品组合中单品图片:
									</label>
								</td>
								<td class="value" colspan="5">
		                            <div class="gridly2" id="dtl_pics" style="height: 220px;">
		                            	<c:forEach items="${tGoodsPage.tGoodsPicDetails}" varStatus="status" var="poVal"> 
									    	 <div class='brick2' >	
												<img src='${poVal.picUrl }' width='140' height='140' ></img>
												<div>商品款号：${poVal.goodsCode }</div>
												<div><span>现价：${poVal.currentPrice }</span></div>
												<div>组合价：<input type='text' class='g_p'  datatype='*' style='width:50px;height: 20px' value="${poVal.groupPrice }"/></div>
												<script>$('#dtl_pics').gridly()</script>
											</div>
									</c:forEach>
	        						</div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										关键词:
									</label>
								</td>
								<td class="value" colspan="5">
									<div style="width: auto;min-height: 100px;">
										<table border="0" width="100%" cellpadding="2" cellspacing="0" >
											<tbody >	
											<c:if test="${fn:length(tGoodsPage.tGoodsAttrDetails)  > 0 }">
												<c:forEach items="${tGoodsPage.tGoodsAttrDetails}" var="poVal" varStatus="stuts">
												<c:if test="${stuts.index % 3 == 0 }">
													<tr>
												</c:if>
														   <td align="right">
															  	关键词：
														   </td>
														   <td align="left">
															  	<input name="tGoodsAttrDetails[${stuts.index }].careValue" maxlength="50" 
															  		type="text"   value="${poVal.careValue }">
														   </td>
										   		<c:if test="${stuts.index % 3 == 3 || stuts.index == status.last}">
													</tr>
												</c:if>
												</c:forEach>
											</c:if>	
											</tbody>
										</table>
									</div>
								</td>
							</tr>
					</table>
	
		</t:formvalid>
		</div>
 </body>
  <script src = "webpage/com/buss/goods/tGoodsCombination.js"></script>		