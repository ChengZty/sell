<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>短信模板设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSmsSendTemplateController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tSmsSendTemplatePage.id }">
					<input id="createName" name="createName" type="hidden"  value="${tSmsSendTemplatePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSmsSendTemplatePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSmsSendTemplatePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSmsSendTemplatePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSmsSendTemplatePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSmsSendTemplatePage.updateDate }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tSmsSendTemplatePage.retailerId }">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
						<td colspan="2" align="center"><span><p>短信模板设置</p></span></td>
					</tr>
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							短信模板名称
						</label>
					</td>
					<td class="value">
					     	 <input id="templateName" name="templateName" type="text" style="width: 300px" class="inputxt" datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信模板名称</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							短信签名
						</label>
					</td>
					<td class="value">
					     	 <input id="autographName" name="autographName" hidden="true" datatype="*">
					     	 <select id="autographId" name="autographId" onchange="setAutographName()" style="width: 300px" datatype="*">
								<option value="">--请选择--</option>
								<c:forEach items="${autographList}" var="autograph">
									<option value="${autograph.id }" <%-- <c:if test="${depart.id==jgDemo.depId}">selected="selected"</c:if> --%>>${autograph.autographName}</option>
								</c:forEach>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信签名</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							正文内容
						</label>
					</td>
					<td class="value">
					     	 <textarea id="content" name="content" style="width: 300px" rows="5" maxlength="480" onkeyup="setPreviewHtml()"  datatype="*"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">正文内容</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							链接地址
						</label>
					</td>
					<td class="value">
					     	 <input id="url" name="url" type="text" style="width: 300px" class="inputxt" onkeyup="setPreviewHtml()"  datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">链接地址</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							结尾内容1
						</label>
					</td>
					<td class="value">
					     	 <input id="contentEnd" name="contentEnd" type="text" style="width: 300px" class="inputxt" onkeyup="setPreviewHtml()"  datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结尾内容1</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							结尾内容2
						</label>
					</td>
					<td class="value">
					     	 <input id="contentEnd2" name="contentEnd2" type="text" style="width: 300px" class="inputxt" onkeyup="setPreviewHtml()"  datatype="*" value="回复TD退订">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结尾内容2</label>
						</td>
					</tr>
				<tr>
					<td class="value" colspan="2">
						<div style="margin-left:30px;width: 570px">
							<div>
								内容： <span id ="countNumber" style="color: red">0</span> 字符+签名 <span id ="nameNumber" style="color: red">0</span>个字符+短链接 <span id="shortNumber" style="color: red">6</span>个字符=<span id ="number" style="color: red">6</span> 字符,还剩<span id="surplusNumber" style="color: red">494</span> ,移动/联通/电信每条<span style="color: red">70</span> ,总字数限制:<span id="cententNumber" style="color: red">500</span>。 
									普通短信<span style="color: red">70</span>字符 ,长短信<span style="color: red">67</span>字符,为一条计费。
							</div>
							短信模板预览:<div id = "previewID" style="width: 550px;word-break: break-all;">【观潮汇生活馆】招牌XXX商品优惠券送给您，
					     	 49元请你享美味，有效期至2017-3-31日。
					     	 快戳t.cn/ZX407b，先到先得，数量有限!
					     	 一起观潮汇!回复TD退订短信。</div>
					     	 </div>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/sms/tSmsSendTemplate.js"></script>	