<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动奖励商品</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tFinActivityGoodsController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tFinActivityGoodsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tFinActivityGoodsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tFinActivityGoodsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tFinActivityGoodsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tFinActivityGoodsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tFinActivityGoodsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tFinActivityGoodsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tFinActivityGoodsPage.status }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								活动奖励ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="tFinActId" name="tFinActId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tFinActivityGoodsPage.tFinActId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">活动奖励ID</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsId" name="goodsId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tFinActivityGoodsPage.goodsId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品零售商ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsStoreId" name="goodsStoreId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tFinActivityGoodsPage.goodsStoreId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品零售商ID</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品零售商类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsStoreType" name="goodsStoreType" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tFinActivityGoodsPage.goodsStoreType}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">商品零售商类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								零售商ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="retailerId" name="retailerId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tFinActivityGoodsPage.retailerId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">零售商ID</label>
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
  <script src = "webpage/com/buss/activity/tFinActivityGoods.js"></script>		