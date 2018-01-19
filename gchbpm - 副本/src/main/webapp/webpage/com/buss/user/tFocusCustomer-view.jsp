<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>待发展顾客详情</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tFocusCustomerController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tFocusCustomerPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tFocusCustomerPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tFocusCustomerPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tFocusCustomerPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tFocusCustomerPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tFocusCustomerPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tFocusCustomerPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tFocusCustomerPage.status }">
					<input id="addGuideId" name="addGuideId" type="hidden" value="${tFocusCustomerPage.addGuideId }">
					<input id="addRetailerId" name="addRetailerId" type="hidden" value="${tFocusCustomerPage.addRetailerId }">
					<input id="toRetailerId" name="toRetailerId" type="hidden" value="${tFocusCustomerPage.toRetailerId }">
					<input id="isUseApp" name="isUseApp" type="hidden" value="${tFocusCustomerPage.isUseApp }">
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
					<tr>
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
					</tr> 
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
		</t:formvalid>
 </body>
