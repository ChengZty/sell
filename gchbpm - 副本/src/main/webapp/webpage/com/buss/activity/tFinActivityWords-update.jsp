<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动话术</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tFinActivityWordsController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tFinActivityWordsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tFinActivityWordsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tFinActivityWordsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tFinActivityWordsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tFinActivityWordsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tFinActivityWordsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tFinActivityWordsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tFinActivityWordsPage.status }">
					<input id="finActId" name="finActId" type="hidden" value="${tFinActivityWordsPage.finActId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								话术:
							</label>
						</td>
						<td class="value">
							<textarea rows="5" name="words" style="width: 98%" maxlength="300" datatype="*">${tFinActivityWordsPage.words}</textarea>
							<span class="Validform_checktip">300字以内</span>
							<label class="Validform_label" style="display: none;">话术</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								排序:
							</label>
						</td>
						<td class="value">
							<input id="sortNum" name="sortNum" type="text" style="width: 150px" class="inputxt" datetype="n" maxlength="3" value='${tFinActivityWordsPage.sortNum}'>
							<span class="Validform_checktip">最多三位数字</span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
