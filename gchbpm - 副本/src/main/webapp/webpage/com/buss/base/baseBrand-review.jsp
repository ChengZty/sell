<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>品牌</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj_u" dialog="false" usePlugin="password" layout="table" action="baseBrandController.do?doUpdate" callback="backList" beforeSubmit="setContent()" tiptype="4">
					<input id="id" name="id" type="hidden" value="${baseBrandPage.id }">
					<input id="brandCode" name="brandCode" type="hidden" value="${baseBrandPage.brandCode }">
					<input id="createName" name="createName" type="hidden" value="${baseBrandPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${baseBrandPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${baseBrandPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${baseBrandPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${baseBrandPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${baseBrandPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${baseBrandPage.status }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								品牌编码:
							</label>
						</td>
						<td class="value">
						     	 ${baseBrandPage.brandCode}
						</td>
					<tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								品牌名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="brandName" name="brandName" type="text" style="width: 150px"   datatype="*" value='${baseBrandPage.brandName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">品牌名称</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								排序:
							</label>
						</td>
						<td class="value">
						     	 <input id="sortNo" name="sortNo" type="text" style="width: 150px"   datatype="n" value='${baseBrandPage.sortNo}' maxlength="5"> 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
					</tr>
					<tr id="c_pic"  >
					<td align="right">
						<label class="Validform_label">
							品牌图片:
						</label>
					</td>
					<td class="value">
					    <img id="prePic"  src="${baseBrandPage.brandPic }"   width="100px" height="100px"    />
					    <input id="brandPic" name="brandPic" type="hidden" value="${baseBrandPage.brandPic }"  >
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							背景图片:
						</label>
					</td>
					<td class="value">
					    <img id="prePic_2" src="${baseBrandPage.bigPic }"  width="360px" height="125px"    />
					    <input id="bigPic" name="bigPic" type="hidden" value="${baseBrandPage.bigPic }"  >
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								品牌故事:
							</label>
						</td>
						<td class="value">
						${brandDesc.content == NULL || brandDesc.content == '' ? '' : brandDesc.content}
						</td>
					</tr>
					<tr height="40">
						<td class="upload" colspan="2" align="center">
							<a href="javascript:back()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
<script src = "webpage/com/buss/base/baseBrand.js"></script>