<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>品牌可售区域</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tBrandSellAreaController.do?doAdd" tiptype="4">
					<input id="brandId" name="brandId" type="hidden" value="${brandId }">
					<input id="isAllProvince" name="isAllProvince" type="hidden" value="0">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							省:
						</label>
					</td>
					<td class="value">
					     <select id="provinceId" name="provinceId" datatype="*" onchange="getCitys(this.value)" style="width: 100px">
							<c:forEach items="${areaList}" var="area">
								<option value="${area.areaId }" >${area.areaName}</option>
							</c:forEach>
						</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">省</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							市:
						</label>
					</td>
					<td class="value">
					     <select id="cityId" name="cityId"  style="width: 100px" >
							<option value="">---请选择---</option>
						</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">市ID</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
 <script src = "webpage/com/buss/base/tBrandSellArea.js"></script>