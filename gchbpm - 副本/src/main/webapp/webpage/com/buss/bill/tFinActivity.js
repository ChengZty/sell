function sub(){
	$("#formobj").submit();
}

function goBack(){
	document.location="tFinActivityController.do?list";
}

//切换类型
function changeActType(val){
	  if(val!="2"){
		  $(".brand_tr").hide();
		  $("#brandId").val("");
		  $("#brandName").val("");
	  }else{//品牌
		  $(".brand_tr").show();
	  }
}


/**选择品牌
 * type 1:零售商，2：平台
 */
function findBrands(type){
	var rId = "";
	if(type==2){
		rId = $("#retailerId").val();
		if(rId==""){
			alert("请选择零售商");
			return false;
		}
	}
	var url = "baseBrandController.do?list&isForChoose=1&retailerId="+rId;
	 $.dialog.setting.zIndex = 999;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择 品牌",
				width:700,
				height: 500,
				cache:false,
			    ok: function(){
//			    	iframe = this.iframe.contentWindow;
//			    	var selected = iframe.getSelectRows();
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
//				    		$.messager.alert('错误提示', '请选择品牌');
			    			alert("请选择品牌");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
//					    		$.messager.alert('错误提示', '只能选择一个品牌');
					    		alert("只能选择一个品牌");
					    		return false;
					    }
						 var brandId = $(sel_tr).find("td[field='id'] div").text();
						 var brandName = $(sel_tr).find("td[field='brandName'] div").text();
						 $("#brandId").val(brandId);
						 $("#brandName").val(brandName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择 品牌",
				width:700,
				height: 500,
				zIndex:1999,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择品牌");
					    		return false;
						 }else{
							 if($(sel_tr).length>1){
						    		alert("只能选择一个品牌");
						    		return false;
						    }
							 var brandId = $(sel_tr).find("td[field='id'] div").text();
							 var brandName = $(sel_tr).find("td[field='brandName'] div").text();
							 $("#brandId").val(brandId);
							 $("#brandName").val(brandName);
						 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
			
		}
}

//选择零售商
function findRetailers(){
	var url = "userController.do?storeUserList";
	 $.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择零售商",
				width:700,
				height: 500,
				cache:false,
			    ok: function(){
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
			    			$.messager.alert("请选择零售商");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
					    		$.messager.alert("只能选择一个零售商");
					    		return false;
					    }
						var retailerId = $(sel_tr).find("td[field='id'] div").text();
						 var retailerName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#retailerId").val(retailerId);
						 $("#retailerName").val(retailerName);
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
	  			    			$.messager.alert("请选择零售商");
	  				    		return false;
	  					 }else{
	  						 if($(sel_tr).length>1){
	  					    		$.messager.alert("只能选择一个零售商");
	  					    		return false;
	  					    }
	  						var retailerId = $(sel_tr).find("td[field='id'] div").text();
							 var retailerName = $(sel_tr).find("td[field='realName'] div").text();
							 $("#retailerId").val(retailerId);
							 $("#retailerName").val(retailerName);
	  					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}

//选中话题
function findNews(){
	var url = "tNewsController.do?newsListForAct";
	 $.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择话题",
				width:1000,
				height: 500,
				cache:false,
			    ok: function(){
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    if ($(sel_tr).length ==0 ){
			    			$.messager.alert("请选择话题");
				    		return false;
					 }else{
						 var newsId = $(sel_tr).find("td[field='id'] div").text();
						 var newsTitle = $(sel_tr).find("td[field='title'] div").text();
						 var coverPic = $(sel_tr).find("td[field='coverPic'] img").attr("src");
						 $("#newsId").val(newsId);
						 $("#newsTitle").val(newsTitle);
						 $("#coverPic").attr("src",coverPic);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择话题",
				width:1000,
				height: 500,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
			    	 var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	 if ($(sel_tr).length ==0 ){
			    			$.messager.alert("请选择话题");
				    		return false;
					 }else{
						 var newsId = $(sel_tr).find("td[field='id'] div").text();
						 var newsTitle = $(sel_tr).find("td[field='title'] div").text();
						 var coverPic = $(sel_tr).find("td[field='coverPic'] img").attr("src");
						 $("#newsId").val(newsId);
						 $("#newsTitle").val(newsTitle);
						 $("#coverPic").attr("src",coverPic);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}