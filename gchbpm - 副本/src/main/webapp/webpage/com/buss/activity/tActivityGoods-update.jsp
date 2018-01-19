<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动商品</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tActivityGoodsController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${tActivityGoodsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tActivityGoodsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tActivityGoodsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tActivityGoodsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tActivityGoodsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tActivityGoodsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tActivityGoodsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tActivityGoodsPage.status }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								活动ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="activityId" name="activityId" type="text" style="width: 150px" class="inputxt"  value='${tActivityGoodsPage.activityId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">活动ID</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsId" name="goodsId" type="text" style="width: 150px" class="inputxt"  value='${tActivityGoodsPage.goodsId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品零售商类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsStoreType" name="goodsStoreType" type="text" style="width: 150px" class="inputxt"  value='${tActivityGoodsPage.goodsStoreType}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品零售商类型</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品零售商ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsStoreId" name="goodsStoreId" type="text" style="width: 150px" class="inputxt"  value='${tActivityGoodsPage.goodsStoreId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品零售商ID</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
