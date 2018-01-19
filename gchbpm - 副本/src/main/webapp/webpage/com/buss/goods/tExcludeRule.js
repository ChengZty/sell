 function goback(){
	  window.location="tExcludeRuleController.do?list";
	  return false;
  }
  
  function findCategorys() {
 	  var url = "tGoodsController.do?getCategorys";
 		 $.dialog.setting.zIndex = 2001;
 		 if(typeof(windowapi) == 'undefined'){
 			 $.dialog({
 					content: "url:"+url,
 					lock : true,
 					title:"选择商品类别",
 					width:900,
 					height: 400,
 					cache:false,
 				    ok: function(){
 				    	iframe_doc = this.iframe.contentWindow.document;
 				    	var sel_a_1 = $(iframe_doc).find("#class_div_1 a[class='classDivClick']");
 				    	var sel_a_2 = $(iframe_doc).find("#class_div_2 a[class='classDivClick']");
 				    	var sel_a_3 = $(iframe_doc).find("#class_div_3 a[class='classDivClick']");
 				    	if($(sel_a_1).length>0){
 				    		var cat_id =  $(sel_a_1).parent().attr("id");
 				    		var cat_name = $(sel_a_1).find("span").text();
 				    		var valarray = cat_id.split('|');
 				    		$('#topCategoryId').val(valarray[0]);
				    		$('#topCategoryName').val(cat_name);
 				    	}else{
 				    		$('#topCategoryId').val("");
				    		$('#topCategoryName').val("");
 				    	}
 				    	if($(sel_a_2).length>0){
 				    		var cat_id =  $(sel_a_2).parent().attr("id");
 				    		var cat_name = $(sel_a_2).find("span").text();
 				    		var valarray = cat_id.split('|');
 				    		$('#subCategoryId').val(valarray[0]);
				    		$('#subCategoryName').val(cat_name);
 				    	}else{
 				    		$('#subCategoryId').val("");
				    		$('#subCategoryName').val("");
 				    	}
 				    	if($(sel_a_3).length>0){
 				    		var cat_id =  $(sel_a_3).parent().attr("id");
 				    		var cat_name = $(sel_a_3).find("span").text();
 				    		var valarray = cat_id.split('|');
 				    		$('#thridCategoryId').val(valarray[0]);
				    		$('#thridCategoryName').val(cat_name);
 				    	}else{
 				    		$('#thridCategoryId').val("");
				    		$('#thridCategoryName').val("");
 				    	}
 				    	 
 						 
 				    },
 				    cancelVal: '关闭',
 				    cancel: true /*为true等价于function(){}*/
 				}).zindex();
 			} else{
 				$.dialog({
 					content: "url:"+url,
 					lock : true,
 					title:"选择商品类别",
 					width:900,
 					height: 400,
 					parent:windowapi,
 					cache:false,
 				     ok: function(){
 				    	iframe_doc = this.iframe.contentWindow.document;
 				    	var sel_a_1 = $(iframe_doc).find("#class_div_1 a[class='classDivClick']");
 				    	var sel_a_2 = $(iframe_doc).find("#class_div_2 a[class='classDivClick']");
 				    	var sel_a_3 = $(iframe_doc).find("#class_div_3 a[class='classDivClick']");
 				    	if($(sel_a_1).length>0){
 				    		var cat_id =  $(sel_a_1).parent().attr("id");
 				    		var cat_name = $(sel_a_1).find("span").text();
 				    		var valarray = cat_id.split('|');
 				    		$('#topCategoryId').val(valarray[0]);
				    		$('#topCategoryName').val(cat_name);
 				    	}else{
 				    		$('#topCategoryId').val("");
				    		$('#topCategoryName').val("");
 				    	}
 				    	if($(sel_a_2).length>0){
 				    		var cat_id =  $(sel_a_2).parent().attr("id");
 				    		var cat_name = $(sel_a_2).find("span").text();
 				    		var valarray = cat_id.split('|');
 				    		$('#subCategoryId').val(valarray[0]);
				    		$('#subCategoryName').val(cat_name);
 				    	}else{
 				    		$('#subCategoryId').val("");
				    		$('#subCategoryName').val("");
 				    	}
 				    	if($(sel_a_3).length>0){
 				    		var cat_id =  $(sel_a_3).parent().attr("id");
 				    		var cat_name = $(sel_a_3).find("span").text();
 				    		var valarray = cat_id.split('|');
 				    		$('#thridCategoryId').val(valarray[0]);
				    		$('#thridCategoryName').val(cat_name);
 				    	}else{
 				    		$('#thridCategoryId').val("");
				    		$('#thridCategoryName').val("");
 				    	}
 				    },
 				    cancelVal: '关闭',
 				    cancel: true /*为true等价于function(){}*/
 				}).zindex();
 			}
 	}

function chooseBrands(){
	var url = 'tExcludeRuleController.do?findBrands';
	var initValue = $('#brandId').val();
	var brandNames = $('#brandName').val();
	url += '&ids='+initValue+"&brandNames="+brandNames;
	$.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择商品",
				width:400,
				height: 600,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var brandIds = iframe.document.getElementById("checkedIds").value;
			    	var brandNames = iframe.document.getElementById("checkedNames").value;
			    	if(brandIds!=""){
			    		brandIds = brandIds.slice(1);
			    		brandNames = brandNames.slice(1);
			    	}
			    	$('#brandId').val(brandIds);
			    	$('#brandName').val(brandNames);
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择商品类别",
				width:400,
				height: 600,
				zIndex:1999,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe = this.iframe.contentWindow;
				    	var brandIds = iframe.document.getElementById("checkedIds").value;
				    	var brandNames = iframe.document.getElementById("checkedNames").value;
				    	if(brandIds!=""){
				    		brandIds = brandIds.slice(1);
				    		brandNames = brandNames.slice(1);
				    	}
				    	$('#brandId').val(brandIds);
				    	$('#brandName').val(brandNames);
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
	}
