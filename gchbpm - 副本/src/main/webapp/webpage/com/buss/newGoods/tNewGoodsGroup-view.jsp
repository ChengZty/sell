<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品组合</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
	<script src="plug-in/Validform/js/common.js"></script>
<style type="text/css">
  .pic_div{
/*   	width: 100px; */
  	line-height:0;
    position: relative;
    margin-left: 20px;
    margin-bottom: 10px;
    float: left;
  }
  .dtl_div {
	display: inline-block;
	position: relative;
	margin-right: 10px;
}
.elips{
	white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
}
</style>
  <script type="text/javascript">
  //编写自定义JS代码
	$(function () {
		$("#topCategoryId option[value='${tGoodsPage.topCategoryId}']").attr("selected",true);
		$("#subCategoryId option[value='${tGoodsPage.subCategoryId}']").attr("selected",true);
		$("#thridCategoryId option[value='${tGoodsPage.thridCategoryId}']").attr("selected",true);
		$("#goodsType").val("${tGoodsPage.goodsType}");
		$("#formobj input,select,a").attr("disabled","disabled");
	});
	
	
  </script>
  
 
 </head>
 <body>
  <div style="width: 1280px;margin: auto;">
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" btnsub="btn" action="tGoodsCombinationController.do?doUpdate" tiptype="4" beforeSubmit="initAndOrder();" callback="backList">
					<input id="id" name="id" type="hidden" value="${tGoodsPage.id }">
					<input id="goodsCode" name="goodsCode" type="hidden" value="${tGoodsPage.goodsCode }">
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
							<tr>
								<td colspan="6" height="40px">
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
			        					<div  id="main_pics_small" style="min-height: 100px;">
			        					<c:if test="${not empty tGoodsPage.smallPic }">
			        						<img src='${tGoodsPage.smallPic }'  height='100' ></img>
			        					</c:if>
			        					</div>
									</td>
								<td align="right" width="120px">
									<label class="Validform_label">
										组合类目:
									</label>
								</td>
								<td class="value" >
									${category}	${subCategory}	${thridCategory}
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
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合名称:
									</label>
								</td>
								<td class="value" >
									${tGoodsPage.goodsName}
								</td>
								</tr>
							<%-- <tr>
								<td align="right">
									<label class="Validform_label">
										活动价:
									</label>
								</td>
								<td class="value">
								     	 <input id="activityPrice" name="activityPrice" type="text" style="width: 150px" value="${tGoodsPage.activityPrice}">
									</td>
								<td align="right">
										<label class="Validform_label">
										原价/组合价:
									</label>
									</td>
									<td class="value">
									<input id="originalPrice" name="originalPrice" type="text" style="width: 150px;background: #D7D7D7" value="${tGoodsPage.originalPrice}" readonly="readonly">
									     	/ &nbsp;<input id="groupPrice" name="groupPrice" type="text" style="width: 150px;background: #D7D7D7"  value="${tGoodsPage.groupPrice}" readonly="readonly">
										</td>
							</tr> --%>
							<tr>
								<td align="right">
									<label class="Validform_label">
										商品组合中单品:
									</label>
								</td>
								<td class="value" colspan="3">
		                            <div id="dtl_pics" style="min-height: 220px;">
		                            	<c:forEach items="${tGoodsPage.tGoodsPicDetails}" varStatus="status" var="poVal"> 
									    	 <div class='dtl_div' >	
												<img src='${poVal.picUrl }'  height='140' ></img>
												<div class="elips">款号：${poVal.goodsCode }</div>
												<div><span>原价：${poVal.originalPrice }</span></div>
<%-- 												<div>组合价：<input type='text' class='g_p'  style='width:70px;' value="${poVal.groupPrice }"/></div> --%>
											</div>
									</c:forEach>
	        						</div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<label class="Validform_label">
										组合相册:
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
							<%-- <tr>
								<td align="right">
									<label class="Validform_label">
										搭配图:
									</label>
								</td>
								<td colspan="5">
									<div id="container_2" >
				                            <div id="container_2_div" style="min-height: 100px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='2' }">
														<div class='pic_div'>
															<img src='${poVal.url }' width='100' height='100'/>
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
				                            <div id="container_3_div" style="min-height: 100px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='3' }">
														<div class='pic_div'>
															<img src='${poVal.url }' width='100' height='100'/>
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
								<td colspan="5">
									<div id="container_5" >
				                            <div id="container_5_div" style="min-height: 200px;">
												<c:forEach var="poVal" items="${recommendDetailsList }">
													<c:if test="${poVal.type=='5' }">
														<div class='video_div'>
															<video src='${poVal.url }' width='320' height='180' controls="controls"></video>
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
							</tr> --%>
					</table>
	
		</t:formvalid>
		
	</div>	
 </body>
  <script src = "webpage/com/buss/newGoods/tNewGoodsGroup.js"></script>	
