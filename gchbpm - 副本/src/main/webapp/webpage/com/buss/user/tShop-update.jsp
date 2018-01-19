<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>店铺</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  $("#shopStatus").val("${tShopPage.shopStatus}");
	  $("#isShow").val("${tShopPage.isShow}");
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tShopController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tShopPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tShopPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tShopPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tShopPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tShopPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tShopPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tShopPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tShopPage.status }">
					<input id="shopLevel" name="shopLevel" type="hidden" value="${tShopPage.shopLevel }">
					<input id="code" name="code" type="hidden" value='${tShopPage.code}'>
					<input id="provinceId" name="provinceId" type="hidden" value='${tShopPage.provinceId}'>
					<input id="cityId" name="cityId" type="hidden"  value='${tShopPage.cityId}'>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								帐号:
							</label>
						</td>
						<td class="value">
						     ${tShopPage.code}
						</td>
					</tr>
					<tr>
						<td align="right"  ><label class="Validform_label">版本: </label></td>
						<td class="value" >
			                <select name="retailerEdition" id="retailerEdition">
								<option value="1">标准版</option>
								<option value="2">企业版</option>
							</select>
			            </td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								店铺名称:
							</label>
						</td>
						<td class="value">
						     <input id="shopName" name="shopName" type="text" style="width: 150px" class="inputxt" datatype="*" value='${tShopPage.shopName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">店铺名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								省市:
							</label>
						</td>
						<td class="value">
						     	 <input id="area" name="area" type="text" style="width: 150px" class="inputxt" value='${tShopPage.area}' readonly="readonly">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">省市</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								详细地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="detailAddress" name="detailAddress" type="text" style="width: 150px" class="inputxt" value='${tShopPage.detailAddress}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">详细地址</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								电话:
							</label>
						</td>
						<td class="value">
						     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px" class="inputxt"  datatype="*" value='${tShopPage.phoneNo}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								有效期:
							</label>
						</td>
						<td class="value">
						     <input name="validPeriod" type="text" class="Wdate" onClick="WdatePicker()" style="width: 150px" value="<fmt:formatDate value='${tPersonPage.birthday }' type="date" pattern="yyyy-MM-dd"/>"
						     errormsg="生日格式不正确!" ignore="ignore">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"> 有效期</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							店铺状态:
						</label>
					</td>
					<td class="value">
				     	 <select id="shopStatus" name="shopStatus" style="width: 100px">
							<option value="1" >激活</option>
							<option value="0" >待激活</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否显示:
						</label>
					</td>
					<td class="value">
				     	 <select id="isShow" name="isShow"  style="width: 100px">
							<option value="1" >是</option>
							<option value="0" >否</option>
						</select>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
