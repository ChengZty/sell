<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>G+卡提成设置</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tCardRateController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tCardRatePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tCardRatePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tCardRatePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tCardRatePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tCardRatePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tCardRatePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tCardRatePage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							零售商:
						</label>
					</td>
					<td class="value">
					     	 <input id="title" name="title" type="text" style="width: 150px" class="inputxt" datetype="*" maxlength="20">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							零售商比例:
						</label>
					</td>
					<td class="value">
					     	 <input id="retailerRate" name="retailerRate" type="text" style="width: 100px" class="inputxt" datetype="d">
					     	 <span>%</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">零售商比例</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							管家比例:
						</label>
					</td>
					<td class="value">
					     	 <input id="guideRate" name="guideRate" type="text" style="width: 100px" class="inputxt" datetype="d">
					     	 <span>%</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">管家比例</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
