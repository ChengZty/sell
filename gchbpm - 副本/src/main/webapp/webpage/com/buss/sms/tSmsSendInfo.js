//提交
function sub(){
	 if(setPreviewHtml()==false){
		 return false;
	 }
	var selectCount = $("#selectCount").html();
	if(selectCount!=""&&parseInt(selectCount)>0){
		$("#sendStatus").val("0");
		$("#formobj").submit();
	}else{
		tip("请选择顾客");
		return false;
	}
	
}

//发布
function setUpload(){
	 if(setPreviewHtml()==false){
		 return false;
	 }
	var sendStatus = $("#sendStatus").val();
	var selectCount = $("#selectCount").html();
	if("1"==sendStatus){
		tip("不能重复推送");
		return false;
	}else{
		if(selectCount!=""&&parseInt(selectCount)>0){
			$.dialog.confirm("发送后需要短信平台进行审核，</br>可能会出现一定延迟，确认发送？", function(){
				$("#sendStatus").val("1");
				$("#formobj").submit();
			}, function(){
			}).zindex();
		}else{
			tip("请选择顾客");
			return false;
		}
	}
}
//返回
function back() {
	document.location="tSmsSendInfoController.do?tSmsSendInfo";
}
//回调状态，是否发送成功
function backList(data) {
//	console.log(data);
		if(data.success){
			tip(data.msg);
			if(""==sourcePushType||null==sourcePushType){//来源于短信列表页面进行新增
				setTimeout(back,1000);//返回列表
			}
		}else{
			$("#sendStatus").val("0");
			tip(data.msg);
		}
}

//短信模板设置
function goSendTemplate() {
	$.dialog.setting.zIndex = 2005;
	$.dialog({
		content : "url:tSmsSendTemplateController.do?goAdd",
		lock : true,
		title : "短信模板设置",
		width : 615,
		height : 450,
		cache : false,
		ok : function() {
			iframe = this.iframe.contentWindow;
			saveObj();
			setTimeout("loadTemplate()", 1000);
			return false;
		},
		cancelVal : '关闭',
		cancel : true
	/*为true等价于function(){}*/
	}).zindex();

}

function setTemplateType(obj) {
	if (0 == obj) {
		$("#content").val("");
		$("#contentEnd1").val("");
		$("#contentEnd2").val("");
		$("#templateName").val("");
	} else {
		var url = "tSmsSendTemplateController.do?getTemplateIdById&templateId="
				+ obj;
		$.ajax({
			url : url,
			type : 'post',
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					var dateSoure = d.obj;
					$("#content").val(dateSoure.content);
					$("#contentEnd").val(dateSoure.contentEnd);
					$("#contentEnd2").val(dateSoure.contentEnd2);
					$("#templateName").val(dateSoure.templateName);
					setPreviewHtml();
				}
			}
		});
	}
}

function checkType(obj){
	$("#pushUrl").val("");
	getDateSource($(obj).parent().parent().find("#pushUrl"));
}

function loadTemplate() {
	var url = "tSmsSendInfoController.do?getTemplateList";
	$.ajax({
		url : url,
		type : 'post',
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				var dateSoure = d.templateList;
				var obj = $("#templateId");
				obj.find("option").remove();
				obj.append("<option value=''>--请选择模板--</option>");
				$.each(dateSoure, function(index, data) {
					obj.append("<option value='" + data.id + "'>"
							+ data.templateName + "</option>");
				});
			}
		}
	});
}
//获取商品或资讯链接
function getDateSource(obj){
	var pushType = $("input[name='pushType']:checked").val();
	var url = "tSmsSendInfoController.do?tPosterList&postStatus=3";
	var title = "海报链接";
	var longUrl = "";
	var width = 810;
	var height = 600;
	if("0"==pushType){
		url = "tSmsSendInfoController.do?tPosterList&postStatus=3";
		title = "海报链接";
	}else{
		url = "tSmsSendInfoController.do?tSmsRetailerList&goods_status=4";
		title="商品链接";
	}
	$.dialog.setting.zIndex = 2053;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:title,
				width:width,
				height:height,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	longUrl= $(iframe.document).find("#longUrl").val();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	$.each( selected, function(i, n){
					    	$(obj).val(longUrl+n.id);
					    	if($("#title").val()==""){
					    		$(obj).parent().parent().parent().find("#title").val(n.title);
					    	}
				    	});
				    	return true;
				    }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:title,
				width:width,
				height:height,
				parent:windowapi,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	longUrl= $(iframe.document).find("#longUrl").val();
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	$.each( selected, function(i, n){
				    		 $(obj).val(longUrl+n.id);
				    		 $(iframe).parent().find("#title").val(n.title);
				    	});
				    	return true;
				    }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}


//选择顾客
function goCustomerList() {
	 if(setPreviewHtml()==false){
		 return false;
	 }
	 var pushUrl = $("#pushUrl").val();
	 var selectCount = $("#selectCount").html();
	 var flag = $("#flag").val();
	 if("1"!=flag&&parseInt(selectCount)>0){
		 tip("已选择顾客，不能再次选择！");
		 return false;
	 }
	 var id = $("#id").val();
	 var batchNo = $("#batchNo").val();
	 var sendTimeType = $("input[name='sendTimeType']:checked").val();
	 var sendTime = $("#sendTime").val();
	 var autographId = $('#autographId option:selected').val();
	 var content = $('#content').val();
	 var contentEnd2 = $('#contentEnd2').val();
	 if(autographId == ""){
		tip("请选择签名");
		return false;
	 }
	 if(pushUrl == ""){
	 	tip("请填写推送链接");
		return false;
	 }
	 if(content == ""){
		tip("请填写内容");
		return false;
	 }
//	 if(contentEnd2 == ""){
//		tip("请填写结尾2内容");
//		return false;
//	 }
	 if(sendTimeType == "1"&&sendTime==""){
	 		tip("请选择发送时间");
		return false;
	 }
	 var params = $("#formobj").serialize();
	 setAttrDisplay();//设置display属性
	 $.dialog.setting.zIndex = 1000;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content : "url:tFocusCustomerMiddleController.do?listOfNewSelect&"+params,
				lock : true,
				title : "选择顾客",
				width : 1100,
				height : 600,
				cache : false,
				ok : function() {
					$("#flag").val("2");
					tip("导入中，请稍等...");
			    	iframe_doc = this.iframe.contentWindow.document;
			    	var types = $(iframe_doc).find("#types").val();
			    	/*$(iframe_doc).find("input[name=selectType]:checked").each(function() { 
			    		types += $(this).val()+",";
			    	}); */
			    	
					var text = $(iframe_doc).find("#tFocusCustomerListForm").serialize();
					var url = "tSmsSendController.do?importSendCustomer&"+text+"&"+params+"&types="+types;
					$.ajax({
						url : url,
						type : 'post',
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								$("#flag").val("0");
								$("#selectCount").html(d.attributes["selectCount"]);
							}else{
								removeAttrDisplay();
							}
							tip(d.msg);
						}
					});
				},
				cancelVal : '关闭',
				cancel : function(){
					removeAttrDisplay();
				}/*为true等价于function(){}*/
			}).zindex();
	 }
	else{
		$.dialog({
			content : "url:tFocusCustomerController.do?listOfSelect",
			lock : true,
			title:"选择顾客",
			width:1000,
			height: 600,
			parent:windowapi,
			cache:false,
		    ok: function(){
				$("#flag").val("2");
				tip("导入中，请稍等...");
		    	iframe_doc = this.iframe.contentWindow.document;
				var text = $(iframe_doc).find("#tFocusCustomerListForm").serialize();
				var url = "tSmsSendController.do?importSendCustomer&"+text+"&"+params;
				$.ajax({
					url : url,
					type : 'post',
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							$("#flag").val("0");
							$("#selectCount").html(d.attributes["selectCount"]);
						}else{
							removeAttrDisplay();
						}
						tip(d.msg);
					}
				});
			},
		    cancelVal: '关闭',
		    cancel:  function(){
		    	removeAttrDisplay();
			}/*为true等价于function(){}*/
		}).zindex();
	}
}


function removeAttrDisplay(){
//	$('#templateId').removeAttr("disabled");
//	$("input[name='sendTimeType']").removeAttr("disabled");
//	$("#sendTime").removeAttr("disabled");
//	$('#autographId').removeAttr("disabled");
//	$('#content').removeAttr("disabled");
//	$('#contentEnd').removeAttr("disabled");
//	$('#title').removeAttr("disabled");
//	$('input[name="templateType"]').removeAttr("disabled");
//	$('input[name="pushType"]').removeAttr("disabled");
//	$("#pushUrl").removeAttr("disabled");
	$('input:visible').removeAttr("disabled");
	$('select').removeAttr("disabled");
	$('textarea').removeAttr("disabled");
}

//选择顾客后页面不可编辑
function setAttrDisplay(){
//	$('#templateId').attr("disabled","disabled");
//	$("input[name='sendTimeType']").attr("disabled","disabled");
//	$("#sendTime").attr("disabled","disabled");
//	$('#autographId').attr("disabled","disabled");
//	$('#content').attr("disabled","disabled");
//	$('#contentEnd').attr("disabled","disabled");
//	$('#title').attr("disabled","disabled");
//	$('input[name="templateType"]').attr("disabled","disabled");
//	$('input[name="pushType"]').attr("disabled","disabled");
//	$("#pushUrl").attr("disabled","disabled");
//	$('#storeId').attr("disabled","disabled");
	$('input:visible').attr("disabled","disabled");
	$('select').attr("disabled","disabled");
	$('textarea').attr("disabled","disabled");
}

//导入
function ImportXls() {
	  var id = $("#id").val();
	  var batchNo = $("#batchNo").val();
	  var templateId = $('#templateId option:selected').val();
	  var sendTimeType = $("input[name='sendTimeType']:checked").val();
	  var sendTime = $("#sendTime").val();
	  if(templateId == ""){
		  tip("请选择模板");
			return false;
	  }
	  if(sendTimeType == "1"&&sendTime==""){
	  		tip("请选择发送时间");
			return false;
	  }else{
		  sendTime = "";
	  }
	  $('#templateId').attr("disabled","disabled");
	  $("input[name='sendTimeType']").attr("disabled","disabled");
	  $("#sendTime").attr("disabled","disabled");
	  openuploadwin('Excel导入', 'tSmsSendController.do?upload&params='+id+','+batchNo+','+templateId+','+sendTimeType+','+sendTime, "tSmsSendList");
}
/**
 * 创建上传页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openuploadwin(title, url,name) {
	$.dialog.setting.zIndex = 2001;
	gridname=name;
	$.dialog({
	    content: 'url:'+url,
	    lock:true,
	    cache:false,
	    button: [
	        {
	            name: "开始上传",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
	            	var shadow_div = iframe.document.getElementById('shadow_div');
	            	$(shadow_div).show();
					iframe.upload();
					return false;
	            },
	            focus: true
	        },
	        {
	            name: "取消上传",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
	            	var shadow_div = iframe.document.getElementById('shadow_div');
	            	//$("img").removeAttr("src");
	            	$('#templateId').removeAttr("disabled");
	        	  	$("input[name='sendTimeType']").removeAttr("disabled");
	        	  	$("#sendTime").removeAttr("disabled");
	            	$(shadow_div).hide();
					iframe.cancel();
	            }
	        }
	    ]
	}).zindex();
}

	function reloadTable(){
	}
	  
	function templateShowDiv(obj){
		if("1"==obj){
			$("#templateFlag").show();
		}else{
			$("#templateFlag").hide();
		}
	}
	
	function sendTimeShowDiv(obj){
		if("1"==obj){
			$("#sendTimeFlag").show();
		}else{
			$("#sendTimeFlag").hide();
		}
	}
	sendTimeShowDiv("0");
	templateShowDiv("0");
	
	
	function setPreviewHtml(beforeInsertStr){
		var templateName = $("#templateName").val();
		var autographName = $("#autographName").val();
		var content = $("#content").val();
		var contentEnd = $("#contentEnd").val();
//		var url = "52gj.cc/1/r8J8E8";
//		var url = "guanjiaapp.net/1/r8J8E8";//临时使用
//		var contentEnd = $("#contentEnd").val();
		var contentEnd2 = $("#contentEnd2").val();
		if(autographName!=""){
			autographName = "【"+autographName+"】"
		}
		if (url!="") {
			content= content.replace(/{url}/g,url);
//			url="快戳"+url+"，";
		}
		var contenttext = autographName+content+contentEnd+contentEnd2;
		var sumContentNumber = content.length+contentEnd.length+contentEnd2.length;
//		var shortNumber = parseInt($("#shortNumber").text());
		var number = sumContentNumber+autographName.length;
		if(number> 500){
			$("#content").val(beforeInsertStr);
			tip("超出最大范围限制，请精简内容");
			return false;
		}
		$("#countNumber").html(sumContentNumber);
		$("#nameNumber").html(autographName.length);
		$("#number").html(number);
		$("#surplusNumber").html(500-number);
		$("#cententNumber").html(500);
		$("#previewID").html(contenttext);
		if(number>70){
			$("#msgNum").val(Math.ceil(number/67));
			$("#num2").text(Math.ceil(number/67));
			
		}else{
			$("#msgNum").val(1);
			$("#num2").text(1);
		}
		$("#num1").text(number);
	}

	function setAutographName(){
		var idva = $("#autographId").find("option:selected");
		if(""!=idva.val()){
			var autographName = idva.text();
			$("#autographName").val(autographName);
		}else{
			$("#autographName").val("");
		}
		setPreviewHtml();
	}
	
	function setStoreName(){
		var idva = $("#storeId").find("option:selected");
		if(""!=idva.val()){
			var contentEnd = idva.text();
			$("#contentEnd").val(contentEnd+"，");
		}else{
			$("#contentEnd").val("");
		}
		setPreviewHtml();
	}
	
	

	
	
	
	

	
	
	
	
	
	
	
	
	
