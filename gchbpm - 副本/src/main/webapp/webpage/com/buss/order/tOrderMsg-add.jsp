<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>编辑参数</title>
<t:base type="jquery,easyui,tools"></t:base>

</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="tOrderMsgController.do?doAdd">
	<fieldset class="step">
		<div class="form"><label class="Validform_label"> 客服电话: </label> <input class="change_template" name="phone" type="text" id="phone" value="${phone }" 
			datatype="s2-20"  > <span class="Validform_checktip">客服电话范围在2~15位字符</span>
		</div>	
		<div class="form" ><label class="Validform_label"> 退货电话: </label> <input class="change_template" name="returnPhone" type="text" id="returnPhone"  value="${returnPhone }" 
			datatype="s2-20"  > <span class="Validform_checktip">退货电话范围在2~15位字符</span>
		</div>	
		<div class="form" ><label class="Validform_label"> 退货地址: </label> 
		<textarea class="change_template" name="returnAddress" style="width: 350px;height: 50px" rows="5" maxlength="100" id="returnAddress"   datatype="s2-100">${returnAddress }</textarea>
			 <span class="Validform_checktip">范围在2~100位字符</span>
		</div>	
	<div class="form"><label class="Validform_label">短信自动回复类型:</label> 
		<select id="provinceId" name="provinceId" datatype="*" onchange="getOrderMsgTemp(this.value)" style="width: 150px" >
			<option value="0" >==请查看==</option>
			<c:forEach items="${paraTypeList}" var="paramType">
				<option value="${paramType.typecode }" <c:if test="${paramType.typecode eq tSysParameterPage.paraCode}">selected='selected'</c:if> >${paramType.typename}</option>
			</c:forEach>
		</select>
	</div>
	<div style="display:none;"><label class="Validform_label"> 参数编码: </label> <input name="paraCode" type="text" id="paraCode"> 
	</div>	
	<div class="form">
		<label class="Validform_label">短信内容:</label>
		<p class="paraValue" id="paraValue" style="width: 400px;height: 80px; margin-left: 115px; background-color:#fff; border-radius: 3px; border: 1px solid #d7d7d7; padding: 0 2px 0 4px; line-height: 14px; font-size: 12px;" ></p>
		<input class="input_paraValue" type="hidden" name="paraValue" />
	</div>

	</fieldset>
</t:formvalid>
<script src = "webpage/com/buss/order/tOrderMsg.js?v=1.0.6"></script>
</body>
</html>