<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>退款退货</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  $(function(){
	  $("#activityPrice").focus();
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tNewGoodsController.do?doChangeActivityPrice" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tGoods.id }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							商品名称:
						</label>
					</td>
					<td class="value">
				     	 ${tGoods.goodsName }
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							活动价:
						</label>
					</td>
					<td class="value">
				     	 <input id="activityPrice" name="activityPrice" type="text" value="${tGoods.activityPrice }" style="width: 150px" datatype="money">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">活动价</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
	