<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>面额</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
  <script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tRechargeCardController.do?doUpdate"   tiptype="4">
					<input id="id" name="id" type="hidden" value="${tRechargeCardPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tRechargeCardPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tRechargeCardPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tRechargeCardPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tRechargeCardPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tRechargeCardPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tRechargeCardPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tRechargeCardPage.status }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="cardName" name="cardName" type="text" style="width: 150px" class="inputxt" value="${tRechargeCardPage.cardName }"
								               datatype="*" maxlength="20" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value">
					     	 <div>
								<input type="file" name="templatePic_u" id="templatePic_u" />
	        				</div>
					     	 <div id="progress_bar_m" style="position: absolute;right:0px;bottom: 0px;z-index: 1001"></div>
					     	 <img id="prePic" src="${tRechargeCardPage.cardPic }" style="background-color: rgba(68, 111, 128, 0.67)"  width="100px" height="150px"    />
					     	 <input id="cardPic" name="cardPic" type="hidden" datatype="*" value="${tRechargeCardPage.cardPic }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图片</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								面额:
							</label>
						</td>
						<td class="value">
						     	 <input id="faceValue" name="faceValue" type="text" style="width: 150px" class="inputxt"  
									             datatype="d"  value='${tRechargeCardPage.faceValue}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">面额</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							折扣:
						</label>
					</td>
					<td class="value">
					     	 <input id="discount" name="discount" type="text" style="width: 150px" class="inputxt" value="${tRechargeCardPage.discount}"
								               datatype="d" onblur="checkDiscount(this.value)">
							<span class="Validform_checktip">折扣小于1</span>
							<label class="Validform_label" style="display: none;">折扣</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
							<textarea style="width: 98%;height: 100px" id="remark" name="remark" maxlength="300">${tRechargeCardPage.remark}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/balance/tRechargeCard.js"></script>		