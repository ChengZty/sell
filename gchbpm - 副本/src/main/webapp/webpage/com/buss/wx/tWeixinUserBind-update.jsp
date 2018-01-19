<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>微信卡片绑定表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tWeixinUserBindController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tWeixinUserBindPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tWeixinUserBindPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tWeixinUserBindPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tWeixinUserBindPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tWeixinUserBindPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tWeixinUserBindPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tWeixinUserBindPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tWeixinUserBindPage.status }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								零售商ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="retailerId" name="retailerId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserBindPage.retailerId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">零售商ID</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								导购ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="guideId" name="guideId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserBindPage.guideId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">导购ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								微信用户id:
							</label>
						</td>
						<td class="value">
						     	 <input id="wxUserId" name="wxUserId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserBindPage.wxUserId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">微信用户id</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								顾客ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="custId" name="custId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserBindPage.custId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">顾客ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								绑定日期:
							</label>
						</td>
						<td class="value">
						     	 <input id="bindDate" name="bindDate" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserBindPage.bindDate}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">绑定日期</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/wx/tWeixinUserBind.js"></script>		