<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>活动话术</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tFinActivityWordsController.do?doAdd" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tFinActivityWordsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tFinActivityWordsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tFinActivityWordsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tFinActivityWordsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tFinActivityWordsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tFinActivityWordsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tFinActivityWordsPage.updateDate }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="finActId" name="finActId" type="hidden" value="${finActId }">
					<input id="wordsType" name="wordsType" type="hidden" value="${wordsType }">
					
					<input id="goodsWords.topTypeId" name="goodsWords.topTypeId" type="hidden" value="101">
					<input id="goodsWords.type" name="goodsWords.type" type="hidden" value="1">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							二级分类:
						</label>
					</td>
					<td class="value">
				     	 <select id="goodsWords.subTypeId" name="goodsWords.subTypeId" onchange="getSubList(this.value)" datatype="*" >
							 <option value="">-请选择-</option>
							 <c:forEach var="obj" items="${subTypeList}" >
									<option value="${obj.id}">${obj.name}</option>					 	
				              </c:forEach>
						</select>
						<label class="Validform_label" style="display: none;">二级分类</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							三级分类:
						</label>
					</td>
					<td class="value">
				     	 <select id="goodsWords.thridTypeId" name="goodsWords.thridTypeId" onchange="changeTypeDisplay(this.value)">
							 <option value="">-请选择-</option>
						</select>
						<label class="Validform_label" style="display: none;">三级分类</label>
					</td>
				</tr>
				
				<tr>
					<td align="right"><label class="Validform_label"> 关联行业: </label></td>
					<td class="value" >
		                <input name="tradeId" type="hidden" value="${tradeId}" id="tradeId">
		                <input name="tradeName" type="text" value="${tradeName }" id="tradeName" readonly="readonly" datatype="*" />
		                <t:choose hiddenName="tradeId" hiddenid="id" url="userController.do?goTradeList" name="tradeList"
		                          icon="icon-search" title="行业列表" textname="tradeName" isclear="true" isInit="true"></t:choose>
		            </td>
				</tr>
		
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							话术:
						</label>
					</td>
					<td class="value">
						<textarea rows="5" name="words" style="width: 98%" maxlength="300" datatype="*"></textarea>
							<span class="Validform_checktip">300字以内</span>
							<label class="Validform_label" style="display: none;">话术</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排序:
						</label>
					</td>
					<td class="value">
					     	 <input id="sortNum" name="sortNum" type="text" style="width: 150px" class="inputxt" datatype="n" maxlength="3">
							<span class="Validform_checktip">最多三位数字</span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							同时加入话术库:
						</label>
					</td>
					<td class="value">
					     	<select name="addToWordsStore">
					     		<option value="0">否</option>
					     		<option value="1">是</option>
					     	</select>
							<span class="Validform_checktip">最多三位数字</span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
