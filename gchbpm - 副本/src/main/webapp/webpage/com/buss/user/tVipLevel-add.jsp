<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>顾客分类</title>
  <t:base type="jquery,easyui,tools,layer"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tVipLevelController.do?doAdd" tiptype="5">
					<input id="id" name="id" type="hidden" value="${tVipLevelPage.id }">
					<input id="status" name="status" type="hidden" value="A">
					<input id="provinceId" name="provinceId" type="hidden" value="${tVipLevelPage.provinceId }">
					<input id="cityId" name="cityId" type="hidden" value="${tVipLevelPage.cityId }">
					<input id="retailerId" name="retailerId" type="hidden" value="${retailerId }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							分类编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="vipCode" name="vipCode" type="text" validType="t_vip_level,status = 'A' and retailer_Id = '${retailerId }' and vip_code" style="width: 150px"   datatype="n">
							<span class="Validform_checktip">编码为数字</span>
							<label class="Validform_label" style="display: none;">分类编码</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							分类名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="vipName" name="vipName" type="text" style="width: 150px"   datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分类名称</label>
						</td>
					</tr>
				<!-- <tr>
					<td align="right">
						<label class="Validform_label">
							折扣:
						</label>
					</td>
					<td class="value">
					     	 <input id="discount" name="discount" type="text" style="width: 150px"   datatype="d">
							<span class="Validform_checktip">折扣小于10</span>
							<label class="Validform_label" style="display: none;">折扣</label>
						</td>
					</tr> -->
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/user/tVipLevel.js"></script>		