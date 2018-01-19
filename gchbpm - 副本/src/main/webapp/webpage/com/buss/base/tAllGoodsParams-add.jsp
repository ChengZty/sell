<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>产品参数表</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tAllGoodsParamsController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tAllGoodsParamsPage.id }">
					<input id="categoryId" name="categoryId" type="hidden" value="${category_Id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							参数名:
						</label>
					</td>
					<td class="value">
					     	 <input id="paramName" name="paramName" type="text" style="width: 150px"  datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">参数名</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							参数值:
						</label>
					</td>
					<td class="value">
					     	 <input id="paramValues" name="paramValues" type="text" style="width: 400px" >
							<span class="Validform_checktip">多个值用逗号分割</span>
							<label class="Validform_label" style="display: none;">参数值</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型:
						</label>
					</td>
					<td class="value">
					     	 <input name="multiSelect" type="radio"  value="1" >多选
					     	 <input name="multiSelect" type="radio" checked="checked" value="0" >单选
					     	 <input name="multiSelect" type="radio"  value="2" >输入框
							<span class="Validform_checktip"></span>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							序号:
						</label>
					</td>
					<td class="value">
					     	 <input id="sortNum" name="sortNum" type="text" style="width: 400px" maxlength="3" datatype="n">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">序号</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
