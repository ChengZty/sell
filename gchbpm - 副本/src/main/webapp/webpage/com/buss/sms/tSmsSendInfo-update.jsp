<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>短信发送</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">
  	.divMarginLeft{
  		padding-top: 10px;
  		margin-left: 20px;
  	}
  	.marginLeft{
  		padding-top: 10px;
  		margin-left: 20px;
  		color: #FF6600;
  	}
  	#insertUrl{
  		position: absolute;
  		left: 360px;
  		top: 0px;
  	}
  	.alarmNum{
 		position: absolute;
	    left: 360px;
	    top: 35px;
	    width: 200px;
	    display: block;
  	}
  	.alarmNum span{
  		font-size: 30px;
  		color: red;
  	}
  </style>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" layout="table" action="tSmsSendInfoController.do?doUpdate" callback="backList" tiptype="4">
					<input id="id" name="id" type="hidden" value="${tSmsSendInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSmsSendInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSmsSendInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSmsSendInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSmsSendInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSmsSendInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSmsSendInfoPage.updateDate }">
					<input id="status" name="status" type="hidden" value="${tSmsSendInfoPage.status }">
					<input id="retailerId" name="retailerId" type="hidden" value="${tSmsSendInfoPage.retailerId }">
					<input id="batchNo" name="batchNo" type="hidden" value="${tSmsSendInfoPage.batchNo }">
					<input id="sendStatus" name="sendStatus" type="hidden" value="${tSmsSendInfoPage.sendStatus }">
					<input id="msgNum" name="msgNum" type="hidden" value="${tSmsSendInfoPage.msgNum }">
					<input id="pushCount" name="pushCount" type="hidden" value="${tSmsSendInfoPage.pushCount }">
					<input id="clickNumber" name="clickNumber" type="hidden" value="${tSmsSendInfoPage.clickNumber }">
					<input id="clickRate" name="clickRate" type="hidden" value="${tSmsSendInfoPage.clickRate }">
					<input id="reach" name="reach" type="hidden" value="${tSmsSendInfoPage.reach }">
					<input id="reachRate" name="reachRate" type="hidden" value="${tSmsSendInfoPage.reachRate }">
					<input id="buySingle" name="buySingle" type="hidden" value="${tSmsSendInfoPage.buySingle }">
					<input id="buyRate" name="buyRate" type="hidden" value="${tSmsSendInfoPage.buyRate }">
					<input id="reliability" name="reliability" type="hidden" value="${tSmsSendInfoPage.reliability }">
					<input id="attractionDegree" name="attractionDegree" type="hidden" value="${tSmsSendInfoPage.attractionDegree }">
					<input id="successDegree" name="successDegree" type="hidden" value="${tSmsSendInfoPage.successDegree }">
		<div id="alignLeftOrCenter" align="left">
		<table style="width: 800px;height: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="3" align="center"><span><p>短信发送【剩余条数：<span style="color: red">${surplusNumber }</span>条】</p></span></td>
				</tr>
				<tr>
					<td align="right" width="200">
						<label class="Validform_label">
							标题
						</label>
					</td>
					<td class="value">
					     	<input id="title" name="title" type="text" style="width: 300px" class="inputxt" datatype="*" value="${tSmsSendInfoPage.title }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							短信签名
						</label>
					</td>
					<td class="value">
					     	 <input id="autographName" name="autographName" hidden="true" datatype="*" value="${tSmsSendInfoPage.autographName }">
					     	 <select id="autographId" name="autographId" onchange="setAutographName()" style="width: 300px" datatype="*">
								<option value="">--请选择--</option>
								<c:forEach items="${autographList}" var="autograph">
									<option value="${autograph.id }" <%-- <c:if test="${depart.id==jgDemo.depId}">selected="selected"</c:if> --%>>${autograph.autographName}</option>
								</c:forEach>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信签名</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							推送链接
						</label>
					</td>
					<td class="value">
					     	<input id="pushUrl" name="pushUrl" type="text" style="width: 300px"  class="inputxt" value="${tSmsSendInfoPage.pushUrl }"> 
					     	<label><input id="pushType0" name="pushType" type="radio" checked="checked" value="0" onclick="checkType(this)">海报</label>
					     	<label><input id="pushType1" name="pushType" type="radio"  value="1" onclick="checkType(this)">商品</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">推送链接</label>
						</td>
					</tr>
				<tr>
					<td></td>
					<td class="value">
						<div>
							<B>短信模板参考:</B><div style="width: 100%;word-break: break-all;">【观潮汇生活馆】招牌XXX商品优惠券送给您，
					     	 49元请你享美味，有效期至2017-3-31日。 快戳52gj.cc/1/r8J8E8，先到先得，数量有限! 一起观潮汇!回复TD退订</div>
					     	 </div>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							店铺名称
						</label>
					</td>
					<td class="value">
					     	<input id="contentEnd" name="contentEnd" hidden="true" >
					     	 <select id="storeId" name="storeId" onchange="setStoreName()" style="width: 300px">
								<option value="">----------------------请选择----------------------</option>
								<c:forEach items="${storeList}" var="storeList">
									<option value="${storeList.name }${storeList.address }" <%-- <c:if test="${depart.id==jgDemo.depId}">selected="selected"</c:if> --%>>${storeList.name }${storeList.address }</option>
								</c:forEach>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">店铺名称</label>
						</td>
					</tr>
				<tr style="display: none">
					<td align="right">
						<label class="Validform_label">
							是否使用模板
						</label>
					</td>
					<td class="value">
						<label><input id="templateType0" name="templateType" type="radio" checked="checked" value="0" onclick="templateShowDiv(this.value)">否</label>
					    <label><input id="templateType1" name="templateType" type="radio"  value="1" onclick="templateShowDiv(this.value)">是</label>
					    <br>
					    <div id="templateFlag">
						<input id="templateName" name="templateName"  type="hidden">
					     <select id="templateId" name="templateId" onchange="setTemplateType(this.value)" style="width: 300px">
							<option value="">--请选择模板--</option>
							<c:forEach items="${templateList}" var="template">
								<option value="${template.id }" <%-- <c:if test="${depart.id==jgDemo.depId}">selected="selected"</c:if> --%>>${template.templateName}</option>
							</c:forEach>
						</select>
						<a href="#" class="easyui-linkbutton l-btn" onclick="goSendTemplate()">短信模板设置</a>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">短信模板名称</label>
						</div>
						</td>
						</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							正文内容
						</label>
					</td>
					<td class="value">
					     	 <div style="position: relative">
						     	 <textarea class="inputxt"  id="content" name="content" style="width: 350px;height: 50px;" onkeyup="setPreviewHtml()">${tSmsSendInfoPage.content }</textarea>
						     	 <button type="button" id="insertUrl">插入链接位置</button>
						     	 <span class="alarmNum">
						     	 	共<span id="num1">4</span>个字符,<span id="num2">1</span>条短信
						     	 </span>
						     	  <div style="width:225px;float: right;margin-top: 60px;">
									 <B>内容：</B> <span id ="countNumber" style="color: red">0</span> 字符+签名 <span id ="nameNumber" style="color: red">0</span>个字符=<span id ="number" style="color: red">0</span> 字符,还剩<span id="surplusNumber" style="color: red">494</span> ,
									移动/联通/电信每条<span style="color: red">70</span> ,总字数限制:<span id="cententNumber" style="color: red">500</span>。 
										普通短信<span style="color: red">70</span>字符 ,长短信<span style="color: red">67</span>字符,为一条计费。(包含结尾内容)
								</div>
							</div>
							
							<B>短信预览:</B>
							<div id="previewID" style="width: 100%;word-break: break-all;color: red;font-size: 14px">
							</div>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">正文内容</label>
						</td>
					</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							结尾内容1
						</label>
					</td>
					<td class="value">
					     	 <input id="contentEnd" name="contentEnd" type="text" style="width: 300px" class="inputxt" onkeyup="setPreviewHtml()" value="${tSmsSendInfoPage.contentEnd }">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结尾内容1</label>
						</td>
					</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							结尾内容
						</label>
					</td>
					<td class="value">
					     	<input id="contentEnd2" name="contentEnd2" type="text" style="width: 300px;border:0px solid #9999B2; " class="inputxt"  readonly="readonly" value="退订回T">
							<span class="Validform_checktip">短信运营商要求必填内容</span>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发送时间
						</label>
					</td>
					<td class="value">
					     	  <label><input id="sendTimeType0" name="sendTimeType" type="radio" checked="checked" value="0" onclick="sendTimeShowDiv(this.value)">即时发送</label>
					     	 <label><input id="sendTimeType1" name="sendTimeType" type="radio"  value="1" onclick="sendTimeShowDiv(this.value)">定时发送</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">时间发送方式，0即时发送，1定时发送</label>
						</td>
						<td></td>
					</tr>
				<tr id="sendTimeFlag">
					<td align="right"></td>
					<td class="value" >
							   <input id="sendTime" name="sendTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${tSmsSendInfoPage.sendTime }">    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送时间</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							顾客手机号
						</label>
					</td>
					<td class="value">
<!-- 							<a href="#" class="easyui-linkbutton l-btn" onclick="goCustomerList()">选择顾客</a>  -->
							<input id="flag" type="hidden"  value="1">
							已选中（<span id="selectCount" style="color:red;">${tSmsSendInfoPage.pushCount }</span>）人
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">手机号</label>
							
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<span style="color: red">短信发送问题说明</span>
						</label>
					</td>
					<td class="value">
						<div>
							<div style="width: 100%;word-break: break-all;">
								<B>1.您发的短信为什么出现延时现象？</B><br>
								<span style="color: red">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									由于国家工信部要求所有平台的短信发送必须通过短信运营商（如联通、移动）审批后再进行短信分发到目标客户，由于运营商每天短信发送量巨大，
									您的发送申请可能需要有一定的网络延时或者等待，请耐心等候。如需在既定时间发送的，请提2-3	个小时进行短信发送。
								</span>
								<br>
								<B>2.手机号码接收不到信息的几种原因</B><br>
								<span style="color: red">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									排除运营商进行特殊号码屏蔽外,手机号码状态分为8种状态：正常，关机，停机，空号，限制呼入，无法接通，来电提醒，其他。其中关机，停机，空号，
									无法接通的状态下，短信群发是接收不到的
								</span>
							</div>
					    </div>
						</td>
					</tr>
<!-- 				<tr> -->
<!-- 					<td colspan="3"> -->
<!-- 						<div style="margin-left:30px;"> -->
<!-- 						说明： -->
<!-- 						<br/> -->
<!-- 						您可以选择手动输入号码，也可批量导入手机号码； -->
<!-- 						<br/> -->
<!-- 						手动输入号码：多个号码请使用","将多个号码分隔开来； -->
<!-- 						<br/> -->
<!-- 						批量导入号码：支持（.???）文件的导入 -->
<!-- 						</div> -->
<!-- 					</td> -->
<!-- 				</tr> -->
				<c:if test="${viewType!='hide' }">
				<tr height="40">
					<td class="upload" colspan="2" align="center">
<!-- 						<a href="#" class="easyui-linkbutton l-btn" id="btn_view" onclick="view()" iconCls="icon-msg">预览</a>  -->
						<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="sub()" iconCls="icon-save">存草稿</a> 
						<a href="#" class="easyui-linkbutton l-btn" id="btnAndUp" onclick="setUpload()" iconCls="icon-save">推送</a> 
						<a href="javascript:back()" class="easyui-linkbutton l-btn" id="btn_reset" iconCls="icon-back">返回</a>
					</td>
				</tr>
				</c:if>
			</table>
			</div>
		</t:formvalid>
 </body>
  <script src = "webpage/com/buss/sms/tSmsSendInfo.js?v=1.12"></script>		
  <script type="text/javascript">
	//文本输入框获取光标位置以及指定位置插入内容
	$.extend($.fn,{  
		//获取文本框内光标位置  
		getSelectionStart: function() {  
			var e = this[0];  
			if (e.selectionStart) {  
				return e.selectionStart;  
			} else if (document.selection) {  
				e.focus();  
				var r=document.selection.createRange();  
				var sr = r.duplicate();  
				sr.moveToElementText(e);  
				sr.setEndPoint('EndToEnd', r);  
				return sr.text.length - r.text.length;  
			}  

			return 0;  
		},  
		getSelectionEnd: function() {  
			var e = this[0];  
			if (e.selectionEnd) {  
				return e.selectionEnd;  
			} else if (document.selection) {  
				e.focus();  
				var r=document.selection.createRange();  
				var sr = r.duplicate();  
				sr.moveToElementText(e);  
				sr.setEndPoint('EndToEnd', r);  
				return sr.text.length;  
			}  
			return 0;  
		},  
		//自动插入默认字符串  
		insertString: function(str) {  
			$(this).each(function() {  
				var tb = $(this);  
				tb.focus();  
				if (document.selection){  
					var r = document.selection.createRange();  
					document.selection.empty();  
					r.text = str;  
					r.collapse();  
					r.select();  
				} else {  
					var newstart = tb.get(0).selectionStart+str.length;  
					tb.val(tb.val().substr(0,tb.get(0).selectionStart) +  
							str + tb.val().substring(tb.get(0).selectionEnd));  
					tb.get(0).selectionStart = newstart;  
					tb.get(0).selectionEnd = newstart;  
				}  
			});  

			return this;  
		},  
		setSelection: function(startIndex,len) {  
			$(this).each(function(){  
				if (this.setSelectionRange){  
					this.setSelectionRange(startIndex, startIndex + len);  
				} else if (document.selection) {  
					var range = this.createTextRange();  
					range.collapse(true);  
					range.moveStart('character', startIndex);  
					range.moveEnd('character', len);  
					range.select();  
				} else {  
					this.selectionStart = startIndex;  
					this.selectionEnd = startIndex + len;  
				}  
			});  

			return this;  
		},  
		getSelection: function() {  
			var elem = this[0];  

			var sel = '';  
			if (document.selection){  
				var r = document.selection.createRange();  
				document.selection.empty();  
				sel = r.text;  
			} else {  
				var start = elem.selectionStart;  
				var end = elem.selectionEnd;  
				var content = $(elem).is(':input') ? $(elem).val() : $(elem).text();  
				sel = content.substring(start, end);  
			}  
			return sel;  
		}  
	})  

    var url = "${SHORT_URL}r8J8E8";//短连接demo
	var sourcePushType = $("#sourcePushType").val();//来源推送类型,0海报，1商品
  	$('input[name="pushType"]').attr("disabled","disabled");
	$("#pushUrl").attr("disabled","disabled");
  	$("#autographId").val("${tSmsSendInfoPage.autographId}");
  	var contentEnd = "${tSmsSendInfoPage.contentEnd}";
  	$("#contentEnd").val(contentEnd);
  	if(contentEnd.length>0){
  		contentEnd = contentEnd.substr(0,contentEnd.length-1)
  	}
  	$("#storeId").val(contentEnd);
  	
  	var pushType = "${tSmsSendInfoPage.pushType}";
  	var sendTimeType = "${tSmsSendInfoPage.sendTimeType}";
  	if("0"==pushType){
  		$("#pushType0").attr("checked","checked");
  	}else{
  		$("#pushType1").attr("checked","checked");
  	}
  	if("0"==sendTimeType){
  		$("#sendTimeType0").attr("checked","checked");
  		sendTimeShowDiv("0");
  	}else{
  		$("#sendTimeType1").attr("checked","checked");
  		sendTimeShowDiv("1");
  	}
  	$(function(){
	  	setPreviewHtml();
	  //插入链接位置占位符
		 $("#insertUrl").on("click",function(){
			 var beforeInsertStr = $("#content").val();
			//  $("#content").val($("#content").val()+" {url} ");
			 $("#content").insertString(" {url} ");  //插入到指定位置
			 setPreviewHtml(beforeInsertStr);
		});
		 if(location.href.indexOf("load=detail")!=-1){
				$("#insertUrl").hide();
			}
  	})
  	
  </script>