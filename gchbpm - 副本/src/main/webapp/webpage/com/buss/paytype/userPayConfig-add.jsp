<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户信息</title>
	<t:base type="jquery,easyui,tools"></t:base>
	<script src="plug-in/Validform/js/common.js"></script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="userPayConfigController.do?doAdd" tiptype="4">
		<input id="id" name="id" type="hidden" value="${userPayConfigPage.id }">
		<input id="createName" name="createName" type="hidden" value="${userPayConfigPage.createName }">
		<input id="createBy" name="createBy" type="hidden" value="${userPayConfigPage.createBy }">
		<input id="createDate" name="createDate" type="hidden" value="${userPayConfigPage.createDate }">
		<input id="updateName" name="updateName" type="hidden" value="${userPayConfigPage.updateName }">
		<input id="updateBy" name="updateBy" type="hidden" value="${userPayConfigPage.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden" value="${userPayConfigPage.updateDate }">
		<input id="status" name="status" type="hidden" value="A">
		<input id="status" name="version" type="hidden" value="${userPayConfigPage.version }">
		<input id="status" name="remark" type="hidden" value="${userPayConfigPage.remark }">
		
		<table style="width: 900px;margin:20px 40px; background-color:#fff;" cellspacing="0" class="formtable">
			<tr><td colspan="6"><label class="Validform_label">用户支付配置</label></td></tr>
			<tr>
				<td style="width: 90px;">商户名：</td>
				<td style="width: 220px;">
					<input id="sid" name="sid" type="hidden" value="${userPayConfigPage.sid}" />
					<input id="storeName" name="storeName" type="text" value="${userPayConfigPage.storeName }" readonly="readonly" onchange="checkStore()"/>
             		<a href="#" class="easyui-linkbutton l-btn l-btn-plain" plain="true"  onclick="findStores()" ><span class="icon-search l-btn-icon-left">选择</span></a>
				</td>
				<td style="width: 75px;">收款方式：</td>
				<td style="width: 220px;">
					<select name="paymentType" id="paymentType" >
                        <option value="1" selected="selected">商户收款</option>
                        <option value="0">平台收款</option>
                   	</select>
				</td>
				<td id="autoclearTd" style="width: 220px; visibility:hidden;">平台收款是否自动结算：
					 <input type="radio" name="autoclear" value="0" />是&nbsp;&nbsp;
					 <input type="radio" name="autoclear" value="1" />否
				</td>
			</tr>
		</table>
		<div id = "paymentDiv" >
			<table style="width: 900px;margin:20px 40px" cellspacing="0" class="formtable">
				<tr><td colspan="6"><label class="Validform_label">微信设置</label></td></tr>
				<tr>
					<td>商户号：</td>
					<td colspan="5"><input type="text" name="wxMerchantId" id="wxMerchantId" maxlength="150" value="${userPayConfigPage.wxMerchantId }" />&nbsp;&nbsp;<span style="color: red;">*</span>微信支付开通成功的通知邮件里，复制“微信支付商户号”，例如：100****321</td>
				</tr>
				<tr>
					<td>APPID：</td>
					<td colspan="5"><input type="text" name="wxAppId" id="wxAppId" maxlength="128" value="${userPayConfigPage.wxAppId }" />&nbsp;&nbsp;<span style="color: red;">*</span>请到“<a href="https://mp.weixin.qq.com/" target="_blank" style="color: blue;">微信公众平台</a>”上点击“开发者中心”，复制AppID，例如：wx0fe958******3135</span></td>
				</tr>
				<tr>
					<td>开发者密码：</td>
					<td colspan="5"><input type="text" name="wxAppSectet" id="wxAppSectet" maxlength="150" value="${userPayConfigPage.wxAppSectet }" />&nbsp;&nbsp;<span style="color: red;">*</span>请到“<a href="https://mp.weixin.qq.com/" target="_blank" style="color: blue;">微信公众平台</a>”上点击“基本配置”，配置开发者密码，例如：7252******1d72</span></td>
				</tr>
				<tr>
					<td>API秘钥：</td>
					<td colspan="5"><input type="text" name="wxPartnerKey" id="wxPartnerKey" maxlength="500" value="${userPayConfigPage.wxPartnerKey }" />&nbsp;&nbsp;<span style="color: red;">*</span>请到“<a href="https://pay.weixin.qq.com/" target="_blank" style="color: blue;">微信支付商户平台</a>”点击 帐户设置→API安全→设置密钥，32位秘钥</td>
				</tr>
				<tr><td colspan="6">&nbsp;</td></tr>
				<tr><td colspan="6"><label class="Validform_label">支付宝设置</label></td></tr>
				<tr>
					<td style="width: 90px;">合作者身份ID：</td>
					<td colspan="5"><input type="text" name="pid" id="pid" maxlength="128" value="${userPayConfigPage.pid }"  />&nbsp;&nbsp;<span style="color: red;">*</span>卖家支付宝用户ID 以2088开头 例如：208810******5135</td>
				</tr>
				<%-- <tr>
					<td>MD5_KEY：</td>
					<td colspan="5"><input type="text" name="md5Key" id="md5Key" maxlength="150" value="${userPayConfigPage.md5Key }" />&nbsp;&nbsp;支付宝账户的MD5_KEY 例如：b1kwb*******f4f</td>
				</tr>
				<tr>
					<td>收款账号：</td>
					<td colspan="5"><input type="text" name="paymentNo" id="paymentNo" maxlength="200" value="${userPayConfigPage.paymentNo }" />&nbsp;&nbsp;卖家支付宝用户ID 以2088开头 例如：2088102146225135</td>
				</tr> --%>
				<tr>
					<td>APPID：</td>
					<td colspan="5"><input type="text" name="appid" id="appid" maxlength="200" value="${userPayConfigPage.appid }" />&nbsp;&nbsp;<span style="color: red;">*</span>支付宝分配给开发者的应用ID 例如：201407******7148</td>
				</tr>
				<tr>
					<td>RSA加密方式：</td>
					<td colspan="5">
						<%-- <input type="text" name="signType" id="signType" maxlength="200" value="${userPayConfigPage.signType }" /> --%>
						<select name="signType" id="signType" style="width: 147px; height:26px;">
		                    <option value="0" selected="selected">RSA</option>
		                    <option value="1" >RSA2</option>
		               	</select>
		               	&nbsp;&nbsp;<span style="color: red;">*</span>商户签名算法类型，目前支持RSA（默认）和RSA2
					</td>
				</tr>
				<tr>
					<td>商户公钥：</td>
					<td colspan="5"><textarea rows="3" cols="100" name="rsaPublicKey" id="rsaPublicKey" maxlength="2050" value="${userPayConfigPage.rsaPublicKey }" >${userPayConfigPage.rsaPublicKey }</textarea><span style="color: red;">*</span></td>
				</tr>
				<tr>
					<td>商户私钥：</td>
					<td colspan="5"><textarea rows="10" cols="100" name="rsaPrivateKey" id="rsaPrivateKey" maxlength="2050" value="${userPayConfigPage.rsaPrivateKey }" >${userPayConfigPage.rsaPrivateKey }</textarea><span style="color: red;">*</span></td>
				</tr>
			</table>
		</div>
		<table style="width: 900px;margin:20px 40px" cellspacing="0" class="formtable">
			<tr height="40">
				<td class="upload" colspan="6" align="center">
					<a href="#" class="easyui-linkbutton" id="btn" onclick="submit()" iconCls="icon-save">保存</a>
					<a href="javascript:back()" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回</a>
				</td>
			</tr>
		</table>
		
		
	</t:formvalid>
</body>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script src = "webpage/com/buss/paytype/basePayType.js"></script> 
<script type="text/javascript">
$(document).ready(function(){  
	/* 设置自动结算默认为否 */
   var ac = ${userPayConfigPage.autoclear } +"";
   if(ac == ""){
	   ac = 1;
   }
   $("#paymentType").val(${userPayConfigPage.paymentType });

   $("#signType").val(${userPayConfigPage.signType });
   
   document.getElementsByName("autoclear")[ac].checked="checked"; 
   $('#paymentType').change(function (){
		if("0" == $("#paymentType").val()){
			$("#autoclearTd").css('visibility','visible');
			$("#paymentDiv").css('visibility','hidden');
		}else {
			$("#autoclearTd").css('visibility','hidden');
			$("#paymentDiv").css('visibility','visible');
		}
	});
});
//提交表单
function submit(){
	if($("#storeName").val() == ""){
		$.messager.alert('提示信息',"请选择零售商");
		return false;
	}
	
	//通过支付方式判断所填项目是否为空
	if("0" == $("#paymentType").val()){
		clearInput();
	}else{
		if(checkInput()){
			return false;	
		}
	}
	$("#formobj").submit();
	back();
}
//验证选择的零售商是否已存在
function checkStore(){
	var sid = $("#sid").val();
	$.ajax({
	    url:'userPayConfigController.do?checkStore&sid='+ sid,
	    type:'GET', //GET
	    async:true,    //或false,是否异步
	    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
	    success:function(data){
        	alert(data.msg);
	        if(data.obj == "500"){
	        	alert(data.msg);
	        	$("#sid").val("");
	        	$("#storeName").val("");
	        }
	    }
	})
}
//商户收款，支付配置不能为空
function checkInput(){
	if($("#wxAppId").val()==""){
		$.messager.alert('提示信息',"微信APPID，不能为空");
		return true;
	}
	if($("#wxPartnerKey").val()==""){
		$.messager.alert('提示信息',"微信商户密钥，不能为空");
		return true;
	}
	if($("#wxMerchantId").val()==""){
		$.messager.alert('提示信息',"微信商户号，不能为空");
		return true;
	}
	if($("#wxAppSectet").val()==""){
		$.messager.alert('提示信息',"微信开发者密码，不能为空");
		return true;
	}
	if($("#pid").val()==""){
		$.messager.alert('提示信息',"支付宝合作者身份ID，不能为空");
		return true;
	}
	if($("#appid").val()==""){
		$.messager.alert('提示信息',"支付宝APPID，不能为空");
		return true;
	}
	if($("#rsaPrivateKey").val()==""){
		$.messager.alert('提示信息',"支付宝私钥，不能为空");
		return true;
	}
	if($("#rsaPublicKey").val()==""){
		$.messager.alert('提示信息',"支付宝公钥，不能为空");
		return true;
	}
}
//平台收款，将所有的支付配置置空
function clearInput(){
	$("#wxAppId").val("");
	$("#wxPartnerKey").val("");
	$("#wxMerchantId").val("");
	$("#wxAppSectet").val("");
	$("#pid").val("");
	$("#appid").val("");
	$("#paymentNo").val("");
	$("#md5Key").val("");
	$("#rsaPrivateKey").val("");
	$("#rsaPublicKey").val("");
}

//返回商户支付配置列表
function back() {
  document.location="userPayConfigController.do?list";
}
//选择零售商
function findStores(){
	var url = "userController.do?storeUserList";
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择零售商",
				width:500,
				height: 530,
				cache:false,
			    ok: function(){
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
			    			$.messager.alert('提示信息',"请选择零售商");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
					    		$.messager.alert('提示信息',"只能选择一个零售商");
					    		return false;
					    }
						 var storeId = $(sel_tr).find("td[field='id'] div").text();
						 var storeName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#sid").val(storeId);
						 $("#storeName").val(storeName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex(3000);
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择零售商",
				width:700,
				height: 500,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    if ($(sel_tr).length ==0 ){
			    			$.messager.alert('提示信息',"请选择零售商");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
				    		$.messager.alert('提示信息',"只能选择一个零售商");
				    		return false;
					    }
						 var storeId = $(sel_tr).find("td[field='id'] div").text();
						 var storeName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#sid").val(storeId);
						 $("#storeName").val(storeName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex(3000);
		}
}

</script>