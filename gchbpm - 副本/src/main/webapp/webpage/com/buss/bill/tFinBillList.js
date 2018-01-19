function clearStoreId(val){
	if(val==''){
		$("#storeId").val("");
	}
}

function clearGuideId(val){
	if(val==''){
		$("#guideId").val("");
	}
}
//选择零售商
function findStores(){
	var url = "userController.do?storeUserList";
	 $.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择零售商",
				width:500,
				height: 530,
				cache:false,
			    ok: function(){
//			    	iframe = this.iframe.contentWindow;
//			    	var selected = iframe.getSelectRows();
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
//				    		$.messager.alert('错误提示', '请选择品牌');
			    			alert("请选择零售商");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
//					    		$.messager.alert('错误提示', '只能选择一个品牌');
					    		alert("只能选择一个零售商");
					    		return false;
					    }
						 var storeId = $(sel_tr).find("td[field='id'] div").text();
						 var storeName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#storeId").val(storeId);
						 $("#storeName").val(storeName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
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
			    			alert("请选择零售商");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
				    		alert("只能选择一个零售商");
				    		return false;
					    }
						 var storeId = $(sel_tr).find("td[field='id'] div").text();
						 var storeName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#storeId").val(storeId);
						 $("#storeName").val(storeName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}



//选择导购
function findGuides(){
	if(rId==""||rId==undefined){
		rId = $("#storeId").val();//平台选择的零售商
	}
	var url = "userController.do?findGuideList&storeId="+rId;
	 $.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择导购",
				width:500,
				height: 530,
				cache:false,
			    ok: function(){
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
//				    		$.messager.alert('错误提示', '请选择品牌');
			    			alert("请选择零导购");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
//					    		$.messager.alert('错误提示', '只能选择一个品牌');
					    		alert("只能选择一个导购");
					    		return false;
					    }
						 var guideId = $(sel_tr).find("td[field='id'] div").text();
						 var guideName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#guideId").val(guideId);
						 $("#guideName").val(guideName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择导购",
				width:700,
				height: 500,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
					    if ($(sel_tr).length ==0 ){
			    			alert("请选择导购");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
				    		alert("只能选择一个导购");
				    		return false;
					    }
						 var guideId = $(sel_tr).find("td[field='id'] div").text();
						 var guideName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#guideId").val(guideId);
						 $("#guideName").val(guideName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}