<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>组合价调价历史</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGroupPriceChangeController.do?doUpdate" tiptype="4" >
					<input id="id" name="id" type="hidden" value="${tGroupPriceChangePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tGroupPriceChangePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tGroupPriceChangePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tGroupPriceChangePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tGroupPriceChangePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tGroupPriceChangePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tGroupPriceChangePage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tGroupPriceChangePage.status }">
					<input id="prePrice" name="prePrice" type="hidden" value="${tGroupPriceChangePage.prePrice }">
					<input id="currentPrice" name="currentPrice" type="hidden" value="${tGroupPriceChangePage.currentPrice }">
					<input id="goodsId" name="goodsId" type="hidden" value="${tGroupPriceChangePage.goodsId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tGroupPriceChangePage.retailerId }">
					<input id="groupSource" name="groupSource" type="hidden" value="${tGroupPriceChangePage.groupSource }">
					<input id="goodsName" name="goodsName" type="hidden" value="${tGroupPriceChangePage.goodsName }">
					<input id="detailJson" name="detailJson" type="hidden" value="">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
						<tr>
								<td align="right" width="100px">
									<label class="Validform_label">
										商品类目:
									</label>
								</td>
								<td class="value">
										<textarea style="width: 98%;height: 100px;">${tGroupPriceChangePage.detailJson }</textarea>
								</td>
							</tr>
			</table>
		</t:formvalid>
 </body>
