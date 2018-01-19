<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>品牌</title>
  <t:base type="jquery,easyui,tools"></t:base>
  
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBrandShowController.do?doUpdate" tiptype="4" >
					<input id="id" name="id" type="hidden" value="${tBrandShowPage.id }">
					<input id="brandId" name="brandId" type="hidden" value="${tBrandShowPage.brandId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tBrandShowPage.retailerId }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td colspan="4" height="40px">
							<h2>品牌满邮管理</h2>
						</td>
					</tr>
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								品牌编码:
							</label>
						</td>
						<td class="value">
						     	 ${baseBrand.brandCode}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								品牌名称:
							</label>
						</td>
						<td class="value">
							${baseBrand.brandName}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								品牌排序:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderNum" name="orderNum" type="text" style="width: 150px"  maxlength="5" datatype="n" value='${tBrandShowPage.orderNum}'>
							<span class="Validform_checktip">数值小的排在前面</span>
							<label class="Validform_label" style="display: none;">品牌排序</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								满邮金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="freeAmount" name="freeAmount" type="text" style="width: 150px"   datatype="d" value='${tBrandShowPage.freeAmount}'>
							<span class="Validform_checktip">品牌满邮金额</span>
							<label class="Validform_label" style="display: none;">满邮金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								基础运费:
							</label>
						</td>
						<td class="value">
						     	 <input id="fare" name="fare" type="text" style="width: 150px"   datatype="d" value='${tBrandShowPage.fare}'>
							<span class="Validform_checktip">该品牌如果买了多件商品，则有一件按该运费算（非包邮的），其他的都是0</span>
							<label class="Validform_label" style="display: none;">满邮金额</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							品牌图片:
						</label>
					</td>
					<td class="value">
					    <img id="prePic"  src="${baseBrand.brandPic }"   width="100px" height="100px"    />
					</td>
					</tr>
			</table>
		</t:formvalid>
		
		
 </body>
