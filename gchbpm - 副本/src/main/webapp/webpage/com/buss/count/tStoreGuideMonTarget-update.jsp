<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>导购月目标</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tStoreGuideMonTargetController.do?doUpdate" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tStoreGuideMonTargetPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tStoreGuideMonTargetPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tStoreGuideMonTargetPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tStoreGuideMonTargetPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tStoreGuideMonTargetPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tStoreGuideMonTargetPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tStoreGuideMonTargetPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tStoreGuideMonTargetPage.status }">
					<input id="monTargetId" name="monTargetId" type="hidden" value="${tStoreGuideMonTargetPage.monTargetId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tStoreGuideMonTargetPage.retailerId }">
					<input id="storeId" name="storeId" type="hidden" value="${tStoreGuideMonTargetPage.storeId }">
					<input id="guideId" name="guideId" type="hidden" value="${tStoreGuideMonTargetPage.guideId }">
					<input id="onlineMoney" name="onlineMoney" type="hidden" value="${tStoreGuideMonTargetPage.onlineMoney }">
					<input id="offlineMoney" name="offlineMoney" type="hidden" value="${tStoreGuideMonTargetPage.offlineMoney }">
					<input id="reachRate" name="reachRate" type="hidden" value="${tStoreGuideMonTargetPage.reachRate }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="120">
							<label class="Validform_label">
								导购姓名:
							</label>
						</td>
						<td class="value">
						     	${tStoreGuideMonTargetPage.guideName}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								月目标金额(元):
							</label>
						</td>
						<td class="value">
						     	 <input id="targetMoney" name="targetMoney" type="text" style="width: 90px" class="inputxt"  
									               datatype="d"
										       value='${tStoreGuideMonTargetPage.targetMoney}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">目标金额</label>
						</td>
						
			</table>
		</t:formvalid>
 </body>
