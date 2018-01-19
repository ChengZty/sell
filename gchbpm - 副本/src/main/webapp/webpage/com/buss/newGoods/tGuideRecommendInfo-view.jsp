<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>管家点评</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <style type="text/css">
  .pic_div{
  	width: 100px;
  	line-height:0;
    position: relative;
    margin-left: 20px;
    float: left;
  }
</style>
  <script type="text/javascript">
  $(function(){
	  $("#formobj input,select,a").attr("disabled","disabled");
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGuideRecommendInfoController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tGuideRecommendInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tGuideRecommendInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tGuideRecommendInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tGuideRecommendInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tGuideRecommendInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tGuideRecommendInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tGuideRecommendInfoPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tGuideRecommendInfoPage.status }">
					<input id="guideId" name="guideId" type="hidden" value="${tGuideRecommendInfoPage.guideId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tGuideRecommendInfoPage.retailerId }">
					<input id="goodsId" name="goodsId" type="hidden" value="${tGuideRecommendInfoPage.goodsId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							管家:
						</label>
					</td>
					<td class="value">
					     	 <input id="guideName"  type="text" style="width: 150px" readonly="readonly" value="${guideName }"/>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							文字描述:
						</label>
					</td>
					<td class="value">
							<textarea rows="5" style="width: 100%" name="description">${tGuideRecommendInfoPage.description}</textarea>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							阅读量:
						</label>
					</td>
					<td class="value">
					     	 <input id="readNum" name="readNum" type="text" style="width: 150px" class="inputxt" datatype="n" value="${tGuideRecommendInfoPage.readNum }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">阅读量</label>
					</td>
				</tr>
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								正面图:
							</label>
						</td>
						<td>
              					<div id="container_1" >
		                            <div id="container_1_div" style="min-height: 100px;">
		                            	<c:forEach var="poVal" items="${recommendDetailsList }">
												<c:if test="${poVal.type=='1' }">
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
								搭配图:
							</label>
						</td>
						<td>
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
						<td>
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
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								视频:
							</label>
						</td>
						<td>
							<div id="container_4" >
		                            <div id="container_4_div" style="min-height: 120px;">
		                            	<c:forEach var="poVal" items="${recommendDetailsList }">
												<c:if test="${poVal.type=='4' }">
													<div class='video_div'>
														<video controls height=120 width=240 src='${poVal.url }' ></video>
													</div>
												</c:if>
											</c:forEach>
		                            </div>
		                      </div>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
