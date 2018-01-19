<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>导购详情</title>
  <t:base type="jquery,easyui,DatePicker"></t:base>
  <script type="text/javascript">
  $(function () {
	  $("#formobj input,select,a").attr("disabled","disabled");
  })
  </script>
  <style type="text/css">
  .form_class{
  margin: 30px auto;
  width: 800px;
  }
  .layout{
/*   margin: auto; */
   	margin: 10px 20px 50px 20px; 
  }
  .blc{
  	margin: 20px 0px;
  	width: 100%;
  }
  .key{
  	margin: 10px 0px;
  	color: gray;
  	font-size: small;
  }
  .val{
  	margin: 10px 0px;
  	font-size: x-small;
/*   	border-bottom: 1px solid gray; */
  }
  .inputtext{
  	width:95%;
  	border: 0px ;
  	border-bottom: 1px solid #ddd;
  }
  .sub_tn{
  	border: 0px ;
  	width: 100%;
  	color: white;
  	background: gray;
  	font-weight: bold;
  }
  .sub{
/*    	position: fixed;  */
/*    	bottom: 0px;  */
  	text-align: center;
  	vertical-align:middle;
  	padding-top:10px;
  	margin-top:5px;
    width: 100%;
    color: white;
    font-weight: bold;
  	height:30px;
  	background: #333333;
  }
  </style>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" styleClass="form_class" layout="table" action="" tiptype="1">
<!-- <form action="" name="formobj"> -->
					<input id="id" name="id" type="hidden" value="${tPersonPage.id }">
					<input id="toRetailerId" name="toRetailerId" type="hidden" value="${tPersonPage.toRetailerId }">
					<input id="industry" name="industry" type="hidden" value="${tPersonPage.industry}">
					<input id="height" name="height" type="hidden" value="${tPersonPage.height}">
					<input id="weight" name="weight" type="hidden" value="${tPersonPage.weight}">
					<input id="favouriteColor" name="favouriteColor" type="hidden" value="${tPersonPage.favouriteColor}">
					<input id="userId" name="userId" type="hidden" value="${tPersonPage.userId}">
					<input id="hasTags" name="hasTags" type="hidden" value="${tPersonPage.hasTags}">
					<c:set var="detail"  value="${tPersonPage.guideDetails}"></c:set>
		<table style="" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px"    value='${tPersonPage.realName}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								昵称:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px"    value='${tPersonPage.name}'>
						</td>
						
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							手机号码:
						</label>
					</td>
					<td class="value">
					     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px"  value='${tPersonPage.phoneNo}'>
						</td>
					<td align="right">
						<label class="Validform_label">
							生日:
						</label>
					</td>
					<td class="value">
					<input name="birthday" type="text"  class="Wdate"  style="width: 150px" value="<fmt:formatDate value='${tPersonPage.birthday }' type="date" pattern="yyyy-MM-dd"/>"
				 ignore="ignore"> <span class="Validform_checktip"></span>
						</td>
					</tr>
				<tr>
						<td align="right">
						<label class="Validform_label">
							累计收入:
						</label>
					</td>
					<td class="value">
					     	 <input id="idCard" name="idCard" type="text" style="width: 150px"  value='${tPersonPage.money}'>

						</td>
						<td align="right">
						<label class="Validform_label">
							成交单数:
						</label>
					</td>
					<td class="value">
					     	 <input id="idCard" name="idCard" type="text" style="width: 150px"  value='${tPersonPage.quantity}'>

						</td>
					</tr>
				<tr>
						<td align="right">
						<label class="Validform_label">
							身份证号:
						</label>
					</td>
					<td class="value">
					     	 <input id="idCard" name="idCard" type="text" style="width: 150px"  value='${tPersonPage.idCard}'>

						</td>
				<td align="right">
						<label class="Validform_label">
							所在省市:
						</label>
					</td>
					<td class="value" >
						<input id="area" name="area" type="text" style="width: 150px"  value='${tPersonPage.area}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								星座:
							</label>
						</td>
						<td class="value">
							<input id="constellation" name="constellation" type="text" style="width: 150px"  value='${tPersonPage.constellation}'>
						</td>
						<td align="right">
						<label class="Validform_label">
							生肖:
						</label>
					</td>
					<td class="value">
					     	 <input id="zodiac" name="zodiac" type="text" style="width: 150px"  value='${tPersonPage.zodiac}'>
						</td>
					</tr>
					<tr>
						<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="phoneNo" name="phoneNo" type="text" style="width: 550px"  value='${tPersonPage.remark}'>
					</td>
					</tr>
		</table> 
		<div class="layout">
				 <c:forEach var="vo" items="${voList }" varStatus="sts">
					 <div class="blc">
					 	<input type="hidden" name="answerList[${sts.index }].id" value="${vo.answers.id }"/><!-- id -->
					 	<input type="hidden" name="answerList[${sts.index }].userId" value="${vo.answers.userId }"/>
					 	<input type="hidden" name="answerList[${sts.index }].questionValue" value="${vo.questionValue }"/><!-- 问题值 -->
					 	<div class="key">${sts.index+1 }.${vo.questionName }</div>
					 	<div class="val">
					 	<c:choose>
						    <c:when test="${vo.answerType == '1'||vo.answerType == '6'||vo.answerType == ''}"><!-- input输入框，地址，文本框 -->
						       <input class="inputtext" type="text" maxlength="30" name="answerList[${sts.index }].answerValue" value="${vo.answers.answerValue }" />
						    </c:when>
						    <c:when test="${vo.answerType == '2'}"><!-- 单选 -->
						    	<c:forTokens var="rad" items="${vo.answerValues }" delims=",，" varStatus="status">
							        <input class="inputradio" type="radio" name="answerList[${sts.index }].answerValue" value="${rad }"
							        	<c:if test="${not empty vo.answers && vo.answers.answerValue == rad}">checked="checked" </c:if>
							        />${rad }
							         <c:if test="${status.index%4==3 }"></br></c:if>
						    	</c:forTokens>
						    </c:when>
						    <c:when test="${vo.answerType == '3'}"><!-- 多选 -->
						    	<c:forTokens var="ckbox" items="${vo.answerValues }" delims=",，" varStatus="stus">
							        <input class="inputchekcbox" type="checkbox" name="answerList[${sts.index }].answerValue" value="${ckbox }"
						    		<c:if test="${not empty vo.answers}">
							        	<c:forTokens var="ck" items="${vo.answers.answerValue}" delims=",，" >
									        	<c:if test="${ck == ckbox}">checked="checked" </c:if>
								    	</c:forTokens>
						    		</c:if>
							        />${ckbox } 
							        <c:if test="${stus.index%4==3 }"></br></c:if>
						    	</c:forTokens>
						    </c:when>
						     <c:when test="${vo.answerType == '4'}"><!-- 日期 -->
						    	<input class="inputDate" type="text" style="width: 120px" name="answerList[${sts.index }].answerValue" value="${vo.answers.answerValue }"/>
						    </c:when>
						    <c:when test="${vo.answerType == '5'}"><!-- 图片 -->
						    	<img alt="" src="${vo.answers.answerValue }" height="100">
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
					 	</div>
				 	</div>
				 </c:forEach>
			</div>
	</t:formvalid>

 </body>
