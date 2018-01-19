<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>店铺月目标</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tStoreMonTargetController.do?goUpdate" tiptype="4">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								店铺名称:
							</label>
						</td>
						<td class="value">
							<select name="storeId" id="storeId" datatype="*"  >
								<option value="">---请选择---</option>
								<c:forEach items="${storeList }" var="store">
									<option value="${store.id }">${store.name }</option>
								</c:forEach>
							</select>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">店铺名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								月份:
							</label>
						</td>
						<td class="value">
									  <input id="targetMonth" name="targetMonth" type="text" style="width: 120px" readonly="readonly"  datatype="*" class="Wdate" 
									  value="${targetMonth }" onClick="WdatePicker({dateFmt:'yyyy-MM'})">    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">月份</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
