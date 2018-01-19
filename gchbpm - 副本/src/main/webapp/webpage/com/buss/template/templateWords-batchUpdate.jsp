<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加话术模板</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body>
 <div id="temStr" style="display: none;"></div>
  	<t:formvalid formid="formobj" dialog="true"  layout="table" action="templateWordsController.do?doBatchUpdate" tiptype="4" >
		<table id="tb" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							二级分类:
						</label>
					</td>
					<td class="value">
						<select id="subTypeId" name="subTypeId" datatype="*">
							 <option value="">-请选择-</option>
							 <c:forEach var="obj" items="${subTypeList}" >
									<option value="${obj.id}">${obj.name}</option>					 	
				              </c:forEach>
						</select>
						<label class="Validform_label" style="display: none;">二级分类</label>
					</td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> 关联行业: </label></td>
					<td class="value" >
		                <input name="tradeIds" type="hidden"  id="tradeId">
		                <input name="tradeNames" type="text"  id="tradeName" readonly="readonly" datatype="*" style="width: 95%"/>
		                <label class="Validform_label" style="display: none;"></label>
		                <div>
		                <t:choose hiddenName="tradeId" hiddenid="id" url="userController.do?goTradeList" name="tradeList" left="50%"
		                          icon="icon-search" title="行业列表" textname="tradeName" isclear="true" isInit="true"></t:choose>
		                </div>
		            </td>
				</tr>
		</table>
	</t:formvalid>
 </body>
