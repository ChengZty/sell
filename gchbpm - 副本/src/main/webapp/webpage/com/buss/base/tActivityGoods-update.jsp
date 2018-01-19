<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动商品</title>
  <t:base type="jquery,easyui,tools"></t:base>
  
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tActivityGoodsController.do?doUpdateOrderNum"  tiptype="4">
					<input id="id" name="id" type="hidden" value="${tActivityGoods.id }">
		<table  cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="100">
							<label class="Validform_label">
								商品排序:
							</label>
						</td>
						<td class="value">
						     	 <input id="orderNum" name="orderNum" type="text" style="width: 150px"   datatype="n" value='${tActivityGoods.orderNum}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品排序</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
