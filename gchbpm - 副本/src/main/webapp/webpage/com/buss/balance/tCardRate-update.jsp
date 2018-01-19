<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>G卡提成比例</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tCardRateController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tCardRatePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tCardRatePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tCardRatePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tCardRatePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tCardRatePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tCardRatePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tCardRatePage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tCardRatePage.status }">
					<input type="hidden" name="retailerId" value="${tCardRatePage.retailerId}"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
							<td align="right" width="100">
								<label class="Validform_label">
									零售商:
								</label>
							</td>
							<td class="value">
							     <input  name="retailerName" type="text" style="width: 150px" class="inputxt"
							      value="${tCardRatePage.retailerName}" readonly="readonly">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">零售商</label>
							</td>
						</tr>
						<tr>
							<td align="right" width="120">
								<label class="Validform_label">
									零售商比例:
								</label>
							</td>
							<td class="value">
						     	 <input  name="retailerRate" type="text" style="width: 100px" class="inputxt" 
						     	 datatype="d" value="${tCardRatePage.retailerRate}" >
						     	 <span>%</span>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">零售商比例</label>
							</td>
						</tr>
						<tr>
							<td align="right" width="120">
								<label class="Validform_label">
									管家比例:
								</label>
							</td>
							<td class="value">
							     	 <input  name="guideRate" type="text" style="width: 100px" class="inputxt" 
							     	 datatype="d" value="${tCardRatePage.guideRate}" >
							     	 <span>%</span>
									<span class="Validform_checktip"></span>
									<label class="Validform_label" style="display: none;">管家比例</label>
								</td>
							</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/balance/tCardRate.js"></script>		