<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>产品参数表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(document).ready(function(){
		var val = '${tAllGoodsParamsPage.multiSelect}';
		$("#multiSelect_"+val).attr("checked","checked");
});
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tAllGoodsParamsController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tAllGoodsParamsPage.id }">
					<input id="categoryId" name="categoryId" type="hidden" value="${tAllGoodsParamsPage.categoryId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								参数名:
							</label>
						</td>
						<td class="value">
						     	 <input id="paramName" name="paramName" type="text" style="width: 150px"   datatype="*" value='${tAllGoodsParamsPage.paramName}'>
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
						     	 <input id="paramValues" name="paramValues" type="text" style="width: 400px"    value='${tAllGoodsParamsPage.paramValues}'>
							<span class="Validform_checktip"></span>
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
						     	 <input id="multiSelect_1" name="multiSelect" type="radio"  value="1" >多选
					     	 	 <input id="multiSelect_0" name="multiSelect" type="radio"  value="0" >单选
					     	 	 <input id="multiSelect_2" name="multiSelect" type="radio"  value="2" >输入框
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">多选</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							序号:
						</label>
					</td>
					<td class="value">
					     	 <input id="sortNum" name="sortNum" type="text"  style="width: 150px" maxlength="3" datatype="n" value='${tAllGoodsParamsPage.sortNum}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">序号</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
