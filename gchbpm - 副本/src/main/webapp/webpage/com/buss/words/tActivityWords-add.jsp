<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>顾客话术</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tActivityWordsController.do?doAdd" tiptype="4">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							活动分类:
						</label>
					</td>
					<td class="value">
							<select id="activityTypeId" name="activityTypeId" datatype="*">
								 <option value="">-请选择-</option>
								 <c:forEach var="obj" items="${activityTypeList}" >
										<option value="${obj.id}">${obj.name}</option>					 	
					              </c:forEach>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">活动分类</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							内容:
						</label>
					</td>
					<td class="value">
						<textarea rows="5" style="width: 98%" name="content" datatype="*" maxlength="300"></textarea>
							<span class="Validform_checktip">最多输入300字</span>
							<label class="Validform_label" style="display: none;">内容</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
