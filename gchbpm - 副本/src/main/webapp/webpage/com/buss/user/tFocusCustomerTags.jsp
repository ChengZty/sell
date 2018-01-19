<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>顾客详情</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">
  .container_div{
  	max-width: 960px;
  	margin: auto;
  	padding-bottom: 20px;
  }
  .stg_title{
  	font-size: small;
    font-weight: bold;
    margin: 10px 0 -10px 5px;
  }
  .form_class{
  margin: 30px auto;
  width: 800px;
  }
  .layout{
/*   margin: auto; */
/*    	margin: 10px 20px 50px 20px;  */
  }
  .blc{
/*   	margin: 5px 0px 20px 0px; */
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
/*  	margin-top: 0.16rem; */
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
// 					var tag = '<span class="selectedTag" keyCode="'+keyCode+'" keyValue="'+arr[i]+'">'+arr[i]+'<div class="x_close"></div></span>';
					var tag = '<span class="selectedTag" keyCode="'+keyCode+'" keyValue="'+arr[i]+'">'+arr[i]+'</span>';
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
// 						$(lastLabeTag).after(tag);//添加自定义的标签
					}
				}
			}
		})
	}
  </script>
 </head>
 <body>
 <div class="container_div">
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tFocusCustomerController.do?doUpdate" tiptype="4">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px"    value='${tFocusCustomerPage.name}'>
						</td>
						<td align="right" width="100">
							<label class="Validform_label">
								性别:
							</label>
						</td>
						<td class="value">
								<input id="name" name="name" type="text" style="width: 150px"    value='${tFocusCustomerPage.sex}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								手机号:
							</label>
						</td>
						<td class="value">
						     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px"    value='${tFocusCustomerPage.phoneNo}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								生日:
							</label>
						</td>
						<td class="value">
						     	 <input name="birthday" class="Wdate" onClick="WdatePicker()" style="width: 150px" value="<fmt:formatDate value='${tFocusCustomerPage.birthday }' type="date" pattern="yyyy-MM-dd"/>"	 ignore="ignore"> 
				 			 <span class="Validform_checktip"></span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								顾客来源:
							</label>
						</td>
						<td class="value">
						     	 <input id="customerSource" name="customerSource" type="text" style="width: 150px"    value='${tFocusCustomerPage.customerSource}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								常用联系方式:
							</label>
						</td>
						<td class="value">
						     	 <input id="commonContact" name="commonContact" type="text" style="width: 150px"    value='${tFocusCustomerPage.commonContact}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								外形:
							</label>
						</td>
						<td class="value">
						     	 <input id="appearance" name="appearance" type="text" style="width: 150px"    value='${tFocusCustomerPage.appearance}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								顾客尺码:
							</label>
						</td>
						<td class="value">
						     	 <input id="customerSize" name="customerSize" type="text" style="width: 150px"    value='${tFocusCustomerPage.customerSize}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								登记地区:
							</label>
						</td>
						<td class="value">
							<input id="registerArea" name="registerArea" type="text" style="width: 150px"   value='${tFocusCustomerPage.registerArea}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								手机归属地:
							</label>
						</td>
						<td class="value">
							<input id="phoneArea" name="phoneArea" type="text" style="width: 150px"   value='${tFocusCustomerPage.phoneArea}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								星座:
							</label>
						</td>
						<td class="value">
							<input id="constellation" name="constellation" type="text" style="width: 150px"   value='${tFocusCustomerPage.constellation}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								生肖:
							</label>
						</td>
						<td class="value">
						     	 <input id="zodiac" name="zodiac" type="text" style="width: 150px"   value='${tFocusCustomerPage.zodiac}'>
						</td>
					</tr> 
					<tr>
						<td align="right">
							<label class="Validform_label">
								微信联系:
							</label>
						</td>
						<td class="value">
							<input id="isWxContact" name="isWxContact" type="text" style="width: 150px" value="${tFocusCustomerPage.isWxContact}">
						</td>
						 <td align="right">
							<label class="Validform_label">
								实体店消费:
							</label>
						</td>
						<td class="value">
							<input id="isStoreConsum" name="isStoreConsum" type="text"style="width: 150px"	value="${tFocusCustomerPage.isStoreConsum}">
						</td> 
					</tr>
					<%-- <tr>
						<td align="right">
							<label class="Validform_label">
								顾客VIP等级:
							</label>
						</td>
						<td class="value">
							<input id="vipLevel" name="vipLevel" type="text" style="width: 150px" value="${tFocusCustomerPage.vipLevel}">
						</td>
						 <td align="right">
							<label class="Validform_label">
								经济实力:
							</label>
						</td>
						<td class="value">
							<input id="finAbilityName" name="finAbilityName" type="text"style="width: 150px"	value="${tFocusCustomerPage.finAbilityName}">
						</td> 
					</tr> --%>
					<%-- <tr>
						<td align="right">
							<label class="Validform_label">
								推送总量:
							</label>
						</td>
						<td class="value">
						     	 <input id="pushCount" name="pushCount" type="text" style="width: 150px"   value='${tFocusCustomerPage.pushCount}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								点击次数:
							</label>
						</td>
						<td class="value">
						     	 <input id="clickNumber" name="clickNumber" type="text" style="width: 150px"   value='${tFocusCustomerPage.clickNumber}'>
						</td>
					</tr> 
					<tr>
						<td align="right">
							<label class="Validform_label">
								购买次数:
							</label>
						</td>
						<td class="value">
						     	 <input id="buyCount" name="pushCount" type="text" style="width: 150px"   value='${tFocusCustomerPage.buyCount}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="type" name="type" type="text" style="width: 150px"   value='${tFocusCustomerPage.type}'>
						</td>
					</tr>  --%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								职业:
							</label>
						</td>
						<td class="value">
						     	 <input id="profession" name="profession" type="text" style="width: 150px"   value='${tFocusCustomerPage.profession}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								职位:
							</label>
						</td>
						<td class="value">
						     	 <input id="position" name="position" type="text" style="width: 150px"   value='${tFocusCustomerPage.position}'>
						</td>
					</tr> 
					<tr>
						<td align="right">
							<label class="Validform_label">
								身高:
							</label>
						</td>
						<td class="value">
						     	 <input id="height" name="height" type="text" style="width: 150px"   value='${tFocusCustomerPage.height}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								体重:
							</label>
						</td>
						<td class="value">
						     	 <input id="weight" name="weight" type="text" style="width: 150px"   value='${tFocusCustomerPage.weight}'>
						</td>
					</tr> 
					<%-- <tr>
						<td align="right">
							<label class="Validform_label">
								喜欢的颜色:
							</label>
						</td>
						<td class="value">
						     	 <input id="color" name="color" type="text" style="width: 150px"   value='${tFocusCustomerPage.color}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								是否在使用app:
							</label>
						</td>
						<td class="value">
						     	 <input id="isUseApp" name="isUseApp" type="text" style="width: 150px"   value='${tFocusCustomerPage.isUseApp}'>
						</td>
					</tr>  --%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								来源:
							</label>
						</td>
						<td class="value">
						     	 <input id="source" name="source" type="text" style="width: 150px"   value='${tFocusCustomerPage.source}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								登记店铺:
							</label>
						</td>
						<td class="value" >
							<input id="phoneRegShopName" name="phoneRegShopName" type="text" style="width: 150px"  value="${tFocusCustomerPage.phoneRegShopName}">
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value" colspan="3">
						     	 <textarea id="remark" name="remark"  style="width: 98%;"  >${tFocusCustomerPage.remark}</textarea>
						</td>
					</tr>
			</table>
			
			<div>
				<!-- 循环显示标签 -->
				<c:forEach var="item" items="${baseTagMap}"> 
					<div>
						<div class="stg_title">${item.key}</div>
						<c:forEach var="vo" items="${item.value }" varStatus="sts">
						 <div class="blc">
						 	<input type="hidden" name="tagStoreList[${sts.index }].tagCode" value="${vo.tagCode }"/><!--标签编码 -->
						 	<input type="hidden" name="tagStoreList[${sts.index }].tagValues" value="${vo.tagStore.tagValues }"/><!-- 顾客标签值 -->
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
				 </div>
			 </c:forEach>
			
			</div>
		</t:formvalid>
 </div>
 </body>
