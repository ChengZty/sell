<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>顾客详情</title>
  <t:base type="jquery,easyui,DatePicker"></t:base>
  <script type="text/javascript">
  $(function () {
	  $("#formobj input,select,a").attr("disabled","disabled");
	  checkAllSelecteTags();
  })
  
  
  	//回显所有已选中的标签
	function checkAllSelecteTags(){
		$("div.val").each(function(){
			var keyCode = $(this).attr("keyCode");
			var tagValues = $(this).parent().find("input[name$='tagValues']").val();
			if(tagValues!=""){
				var arr = tagValues.split(",");
				for(var i in arr){
					var tag = '<span class="selectedTag" keyCode="'+keyCode+'" keyValue="'+arr[i]+'">'+arr[i]+'<div class="x_close"></div></span>';
					$("#content").append(tag);//显示选中的标签
					var hasLabel = $(this).find("label[keyValue='"+arr[i]+"']");
					if($(hasLabel).length>0){//非自定义的标签
						$(hasLabel).addClass("selected");
					}else{//自定义的标签
						var tag = '';
						var lastLabeTag = $(this).find("label.TAG:last");
						if($(lastLabeTag).hasClass("singleselect")){
							tag = '<label class="singleselect selected" keyvalue="'+arr[i]+'">'+arr[i]+'</label>';
						}else if($(lastLabeTag).hasClass("mutiselect")){
							tag = '<label class="mutiselect selected" keyvalue="'+arr[i]+'">'+arr[i]+'</label>';
						}
						$(lastLabeTag).after(tag);//添加自定义的标签
					}
				}
			}
		})
	}
  </script>
  <style type="text/css">
  .form_class{
  margin: 30px auto;
  width: 800px;
  }
  .layout{
/*   margin: auto; */
/*    	margin: 10px 20px 50px 20px;  */
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
  
  
.blc{
 	margin-top: 0.16rem;
 	background-color: #f1f1f1;
 	border-bottom: 1px solid #ccc;
 }

.blc .tag_name{
	padding: 0.4rem 0 0 0.4rem;
	font-size: 1.083em;
	color: #666;
}
.blc .tag_value_list{
	margin: 0 0 0.4rem 0.64rem;
}
.blc .tag_value_list label{
	float: left;
	display: inline-block;
	margin-top: 5px;
	margin-right: 0.266667rem;
	padding: 0 5px;
	border: 1px solid #afafaf;
	border-radius: 3px;
	line-height: 24px;
	color: #333;
}
.blc .tag_value_list label.selected{
	color: #fff;
	background-color: #c69c6d;
}
.clearfix{
	height: 30px
}
  
  </style>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" styleClass="form_class" layout="table" action="tPersonController.do?doUpdateGuide" tiptype="1">
<!-- <form action="" name="formobj"> -->
					<input id="id" name="id" type="hidden" value="${tPersonPage.id }">
					<input id="toRetailerId" name="toRetailerId" type="hidden" value="${tPersonPage.toRetailerId }">
					<input id="industry" name="industry" type="hidden" value="${tPersonPage.industry}">
					<input id="height" name="height" type="hidden" value="${tPersonPage.height}">
					<input id="weight" name="weight" type="hidden" value="${tPersonPage.weight}">
					<input id="favouriteColor" name="favouriteColor" type="hidden" value="${tPersonPage.favouriteColor}">
					<input id="userId" name="userId" type="hidden" value="${tPersonPage.userId}">
					<input id="hasTags" name="hasTags" type="hidden" value="${tPersonPage.hasTags}">
					<c:set var="detail"  value="${tPersonPage.customerDetails}"></c:set>
		<table style="" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="realName" type="text" style="width: 150px"    value='${tPersonPage.realName}'>
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
<!-- 					<td align="right"> -->
<!-- 						<label class="Validform_label"> -->
<!-- 							性别: -->
<!-- 						</label> -->
<!-- 					</td> -->
<!-- 					<td class="value"> -->
<%-- 							  <t:dictSelect field="sex" type="list"  --%>
<%-- 									typeGroupCode="sex" defaultVal="${tPersonPage.sex}" hasLabel="false"  title="性别"></t:dictSelect>      --%>
<!-- 						</td> -->
					</tr>
				<tr>
						<td align="right">
						<label class="Validform_label">
							购买金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="idCard" name="idCard" type="text" style="width: 150px"  value='${tPersonPage.money}'>

						</td>
						<td align="right">
						<label class="Validform_label">
							购买件数:
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
					<td class="value">
						<input id="area" name="area" type="text" style="width: 150px"  value='${tPersonPage.area}'>
						</td>
					</tr>
					<tr>
						<td align="right">
						<label class="Validform_label">
							职业:
						</label>
					</td>
					<td class="value">
					     	 <input id="profession" name="profession" type="text" style="width: 150px"  value='${tPersonPage.profession}'>

						</td>
					<td align="right">
						<label class="Validform_label">
							行业:
						</label>
					</td>
					<td class="value">
						<input id="industry" name="industry" type="text" style="width: 150px"  value='${tPersonPage.industry}'>
						</td>
					</tr>
				<tr>
						<td align="right">
						<label class="Validform_label">
							身高:
						</label>
					</td>
					<td class="value">
					     	 <input id="height" name="height" type="text" style="width: 150px"  value='${tPersonPage.height}'>
						</td>
					<td align="right">
						<label class="Validform_label">
							体重:
						</label>
					</td>
					<td class="value">
						<input id="weight" name="weight" type="text" style="width: 150px"  value='${tPersonPage.weight}'>
						</td>
					</tr>
				<tr>
						<td align="right">
						<label class="Validform_label">
							喜欢的颜色:
						</label>
					</td>
					<td class="value">
					     	 <input id="favouriteColor" name="favouriteColor" type="text" style="width: 150px"  value='${tPersonPage.favouriteColor}'>
						</td>
					<td align="right">
						<label class="Validform_label">
							星座:
						</label>
					</td>
					<td class="value">
						<input id="constellation" name="constellation" type="text" style="width: 150px"  value='${tPersonPage.constellation}'>
						</td>
					</tr>
				<tr>
						<td align="right">
						<label class="Validform_label">
							生肖:
						</label>
					</td>
					<td class="value">
					     	 <input id="zodiac" name="zodiac" type="text" style="width: 150px"  value='${tPersonPage.zodiac}'>
						</td>
					<td align="right">
						<label class="Validform_label">
						</label>
					</td>
					<td class="value">
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
				 	<input type="hidden" name="tagStoreList[${sts.index }].id" value="${vo.tagStore.id }"/><!-- id -->
				 	<input type="hidden" name="tagStoreList[${sts.index }].tagCode" value="${vo.tagCode }"/><!--标签编码 -->
				 	<input type="hidden" name="tagStoreList[${sts.index }].tagValues" value="${vo.tagStore.tagValues }"/><!-- 标签值 -->
				 	<div class="key tag_name">${sts.index+1 }.${vo.tagTitle }
				 		<c:if test="${vo.tagType == '2'}">(可多选)</c:if>
				 	</div>
				 	<div class="val tag_value_list" keyCode="${vo.tagCode }">
				 	
				 	<c:choose>
					    <c:when test="${vo.tagType == '1'}"><!-- 单选 -->
					    	<div class="single_div clearfix">
					    	<c:forTokens var="rad" items="${vo.tagValues }" delims=",，" varStatus="status">
					    	<label class="singleselect TAG" keyValue="${rad }">${rad }</label>
					    	</c:forTokens>
					    	</div>
					    </c:when>
					    <c:when test="${vo.tagType == '2'}"><!-- 多选 -->
					    	<div class="muti_div clearfix">
					    	<c:forTokens var="ckbox" items="${vo.tagValues }" delims=",，" varStatus="stus">
					    	<label class="mutiselect TAG" keyValue="${ckbox }">${ckbox }</label>
					    	</c:forTokens>
					    	</div>
					    </c:when>
					    
					    <c:otherwise>
					    </c:otherwise>
					</c:choose>
				 	</div>
			 	</div>
			 </c:forEach>
				<%-- 
				 <c:forEach var="vo" items="${voList }" varStatus="sts">
					 <div class="blc">
					 	<input type="hidden" name="answerList[${sts.index }].id" value="${vo.answers.id }"/><!-- id -->
					 	<input type="hidden" name="answerList[${sts.index }].userId" value="${vo.answers.userId }"/>
					 	<input type="hidden" name="answerList[${sts.index }].questionValue" value="${vo.questionValue }"/><!-- 问题值 -->
					 	<div class="key">${sts.index+1 }.${vo.questionName }</div>
					 	<div class="val">
					 	<c:choose>
						    <c:when test="${vo.answerType == '1'}"><!-- input输入框 -->
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
						    	<input class="inputDate" type="text" style="width: 120px" name="answerList[${sts.index }].answerValue"value="${vo.answers.answerValue }"	
						    	 	onClick="WdatePicker()" />
						    </c:when>
						    <c:otherwise>
						    </c:otherwise>
						</c:choose>
					 	</div>
				 	</div>
				 </c:forEach>
				  --%>
				</div>
		</t:formvalid>

 </body>
