<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>导购排名话术</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGuideRankWordsController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tGuideRankWordsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tGuideRankWordsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tGuideRankWordsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tGuideRankWordsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tGuideRankWordsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tGuideRankWordsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tGuideRankWordsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tGuideRankWordsPage.status }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tGuideRankWordsPage.retailerId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								起始排名:
							</label>
						</td>
						<td class="value">
						     	 <input id="rankStart" name="rankStart" type="text" datatype="n" style="width: 150px" class="inputxt" value='${tGuideRankWordsPage.rankStart}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起始排名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结束排名:
							</label>
						</td>
						<td class="value">
						     	 <input id="rankEnd" name="rankEnd" type="text" style="width: 150px" class="inputxt"  value='${tGuideRankWordsPage.rankEnd}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束排名</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								话术:
							</label>
						</td>
						<td class="value">
						     	 <input id="words" name="words" type="text" style="width: 350px" class="inputxt" maxlength="30"  datatype="*" value='${tGuideRankWordsPage.words}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">话术</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
