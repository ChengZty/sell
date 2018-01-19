<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>赠品规则</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGiftRuleController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tGiftRulePage.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								规则名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="ruleName" name="ruleName" type="text" style="width: 300px" class="inputxt"  
						     	 value='${tGiftRulePage.ruleName}' datatype="*" maxlength="30">
							<span class="Validform_checktip">不超过30字</span>
							<label class="Validform_label" style="display: none;">规则名称</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								满赠金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="money" name="money" type="text" style="width: 60px" class="inputxt" datatype="n" value='${tGiftRulePage.money}'>
							<span class="Validform_checktip">一次购买达到(含)该金额则赠送赠品</span>
							<label class="Validform_label" style="display: none;">满赠金额</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							描述:
						</label>
					</td>
					<td class="value">
						<textarea rows="6" name="description" style="width: 99%" datatype="*" maxlength="300">${tGiftRulePage.description}</textarea>
						<span class="Validform_checktip">描述不超过300字</span>
						<label class="Validform_label" style="display: none;">描述</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
