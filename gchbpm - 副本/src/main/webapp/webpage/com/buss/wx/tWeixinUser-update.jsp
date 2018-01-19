<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>微信用户表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tWeixinUserController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tWeixinUserPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tWeixinUserPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tWeixinUserPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tWeixinUserPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tWeixinUserPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tWeixinUserPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tWeixinUserPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tWeixinUserPage.status }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								零售商ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="retailerId" name="retailerId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserPage.retailerId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">零售商ID</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								微信openid:
							</label>
						</td>
						<td class="value">
						     	 <input id="openid" name="openid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserPage.openid}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">微信openid</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								用户昵称:
							</label>
						</td>
						<td class="value">
						     	 <input id="nickname" name="nickname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserPage.nickname}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户昵称</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								性别:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="sex" type="list"
										typeGroupCode="" defaultVal="${tWeixinUserPage.sex}" hasLabel="false"  title="性别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">性别</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								国家:
							</label>
						</td>
						<td class="value">
						     	 <input id="country" name="country" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserPage.country}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">国家</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								省份:
							</label>
						</td>
						<td class="value">
						     	 <input id="province" name="province" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserPage.province}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">省份</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								城市:
							</label>
						</td>
						<td class="value">
						     	 <input id="city" name="city" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserPage.city}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">城市</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								头像:
							</label>
						</td>
						<td class="value">
						     	 <input id="headimgurl" name="headimgurl" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserPage.headimgurl}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">头像</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								特权json组:
							</label>
						</td>
						<td class="value">
						     	 <input id="privilege" name="privilege" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tWeixinUserPage.privilege}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">特权json组</label>
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
  <script src = "webpage/com/buss/wx/tWeixinUser.js"></script>		