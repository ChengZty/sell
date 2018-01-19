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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGiftRuleController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tGiftRulePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tGiftRulePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tGiftRulePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tGiftRulePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tGiftRulePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tGiftRulePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tGiftRulePage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="ruleType" name="ruleType" type="hidden" value="1"><!-- 默认为一次买满赠 -->
					<input id="ruleStatus" name="ruleStatus" type="hidden" value="1"><!-- 待审核 -->
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							规则名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="ruleName" name="ruleName" type="text" style="width: 300px" class="inputxt" datatype="*" maxlength="30">
							<span class="Validform_checktip">不超过30字</span>
							<label class="Validform_label" style="display: none;">规则名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							满赠金额:
						</label>
					</td>
					<td class="value">
				     	 <input id="money" name="money" type="text" style="width: 60px" class="inputxt" datatype="n">
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
						<textarea name="description" rows="6"  style="width: 99%" datatype="*" maxlength="300"></textarea>
						<span class="Validform_checktip">描述不超过300字</span>
						<label class="Validform_label" style="display: none;">描述</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
