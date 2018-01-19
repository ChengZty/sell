//选择签约品牌
function findBrands(){
	var storeId = $("#otherRetailerId").val();
	if(storeId==""){
		alert("请选择品牌");
		return false;
	}
	var url = "tBrandShowController.do?findSelfBrandList&isSelfBrand=1&storeId="+storeId;
	 $.dialog.setting.zIndex = 1020;
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
						 var brandId = $(sel_tr).find("td[field='brandId'] div").text();
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
							 var brandId = $(sel_tr).find("td[field='brandId'] div").text();
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

/*
parentId:上级ID,lvl:类目级别，sel_id:展示列表的下拉框的ID
*/
function getUnderCategories(id,parentId,lvl,sel_id){
	  if(parentId!=""){
		//获取城市
		  	var url = "categoryController.do?getUnderCategories&parentId="+parentId;
		  	$.ajax({
		  		async : false,
		  		cache : false,
		  		type : 'POST',
		  		url : url,// 请求的action路径
		  		error : function() {// 请求失败处理函数
		  		
		  		},
		  		success : function(data) {
		  			var d = $.parseJSON(data);
		  			if (d.success) {
		  				$("#"+sel_id).empty();
		  				var ops = "<option value=''>---请选择---</option>";
		  				for(i=0;i<d.obj.length;i++){
		  					if(lvl=="3"){
		  						ops+="<option value='"+d.obj[i].areaId+"'>"+d.obj[i].areaName+"</option>"
		  					}else{
		  						ops+="<option value='"+d.obj[i].areaId+"'>"+d.obj[i].areaName+"</option>"
		  					}
		  				}
		  				$("#"+sel_id).append(ops).show();
		  				var name = $("#"+id).find("option:selected").text();
		  				if(lvl=="2"){
		  					$("#thridCategoryId").empty().hide();
		  					$("#topCategoryName").val(name);
		  					$("#subCategoryName").val("");
		  					$("#thridCategoryName").val("");
		  				}else if(lvl=="3"){
		  					$("#subCategoryName").val(name);
		  					$("#thridCategoryName").val("");
		  				}
		  				
		  			}
		  			
		  		}
		  	});
	  }else{
		  if(lvl=="3"){
			  $("#thridCategoryId").empty().hide();
			  $("#subCategoryName").val("");
			  $("#thridCategoryName").val("");
		  }else if(lvl=="2"){
			  $("#subCategoryId").empty().hide();
			  $("#thridCategoryId").empty().hide();
			  $("#topCategoryName").val("");
			  $("#subCategoryName").val("");
			  $("#thridCategoryName").val("");
		  }
	  }
}