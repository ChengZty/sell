<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>零售商子标题</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="cmsCustom.do?doUpdateSubTitle" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tContentCustomIndexPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tContentCustomIndexPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tContentCustomIndexPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tContentCustomIndexPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tContentCustomIndexPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tContentCustomIndexPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tContentCustomIndexPage.updateDate }">
					<input id="retailerId" name="retailerId" type="hidden"  class="inputxt"   value='${tContentCustomIndexPage.retailerId}'>
					<input id="contentId" name="contentId" type="hidden"  class="inputxt"    value='${tContentCustomIndexPage.contentId}'>
					<input id="isShow" name="isShow" type="hidden" class="inputxt"   value='${tContentCustomIndexPage.isShow}'>
					<input id="sortOrder" name="sortOrder" type="hidden" class="inputxt"   value='${tContentCustomIndexPage.sortOrder}'>
		<table style="width: 500px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="80">
							<label class="Validform_label">
								子标题:
							</label>
						</td>
						<td class="value">
						     <input id="subTitle" name="subTitle" type="text" style="width: 300px" class="inputxt" maxlength="20" datatype="*" value='${tContentCustomIndexPage.subTitle}'>
							<span class="Validform_checktip">不超过20个字</span>
							<label class="Validform_label" style="display: none;">子标题</label>
						</td>
			</table>
		</t:formvalid>
 </body>