<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>品牌可售区域</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBrandSellAreaController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tBrandSellAreaPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tBrandSellAreaPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBrandSellAreaPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBrandSellAreaPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBrandSellAreaPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBrandSellAreaPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBrandSellAreaPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tBrandSellAreaPage.status }">
					<input id="brandId" name="brandId" type="hidden" value="${tBrandSellAreaPage.brandId }">
					<input id="isAllProvince" name="isAllProvince" type="hidden" value="${tBrandSellAreaPage.isAllProvince }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tBrandSellAreaPage.retailerId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								省ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="provinceId" name="provinceId" type="text" style="width: 150px" class="inputxt"  
										       value='${tBrandSellAreaPage.provinceId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">省ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								市:
							</label>
						</td>
						<td class="value">
						     	 <input id="cityId" name="cityId" type="text" style="width: 150px" class="inputxt"  
										       value='${tBrandSellAreaPage.cityId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">市ID</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
 <script src = "webpage/com/buss/base/tBrandSellArea.js"></script>	