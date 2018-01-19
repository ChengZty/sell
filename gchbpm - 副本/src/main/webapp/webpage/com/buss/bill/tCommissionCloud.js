function back(){
	 document.location="tCommissionCloudController.do?list";
}

function backList(data) {
	 /*$.messager.confirm('提示信息', data.msg, function(r){
			document.location="tCommissionCloudController.do?list";
	  });*/
	  if(data.success){
		  layer.confirm(data.msg, {
			  btn: ['确定','关闭'] //按钮
		  	  ,title :'提示信息'
			}, function(){document.location="tCommissionCloudController.do?list";}, function(){});  
	  }else{
		  layer.alert(data.msg); 
	  }
}


function setCatName(obj,descId){
	  var txt = $(obj).find("option:selected").text()
	  $("#"+descId).val(txt);
}

function searchList(){
var topCategoryId = $("#topCategoryId").val();
var subCategoryId = $("#subCategoryId").val();
var thridCategoryId = $('#thridCategoryId').val();
var brandId = $('#brandId').val();
//$('#mutiLangList').datagrid({url:'mutiLangController.do?datagrid&field=id,langKey,langContext,langCode,',pageNumber:1});
$("#mutiLangList").datagrid('load', {
	topCategoryId:topCategoryId,
	subCategoryId:subCategoryId,
	thridCategoryId:thridCategoryId,
	brandId:brandId,
	 page:1
});
}

//云商列表
function findCloudStores(){
	var url = "tCommissionCloudController.do?cloudStoreList";
	$.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择云商",
				width:700,
				height: 500,
				cache:false,
			    ok: function(){
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
				    		//$.messager.alert('错误提示', '请选择品牌');
			    			alert("请选择云商");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
					    		//$.messager.alert('错误提示', '只能选择一个品牌');
					    		alert("只能选择一个云商");
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
				title:"选择云商",
				width:700,
				height: 500,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
					    		//$.messager.alert('错误提示', '请选择品牌');
				    			alert("请选择云商");
					    		return false;
						 }else{
							 if($(sel_tr).length>1){
						    		//$.messager.alert('错误提示', '只能选择一个品牌');
						    		alert("只能选择一个云商");
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

//查询云商自有品牌
function findBrands(){
	var storeId = $("#storeId").val();
	var url = "tBrandShowController.do?findSelfBrandList&isSelfBrand=1&storeId="+storeId;
	$.dialog.setting.zIndex = 9900;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"请选择品牌",
				width:700,
				height: 500,
				cache:false,
			    ok: function(){
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
				    		//$.messager.alert('错误提示', '请选择品牌');
			    			alert("请选择品牌");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
					    		//$.messager.alert('错误提示', '只能选择一个品牌');
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
				title:"请选择品牌",
				width:700,
				height: 500,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
					    		//$.messager.alert('错误提示', '请选择品牌');
				    			alert("请选择品牌");
					    		return false;
						 }else{
							 if($(sel_tr).length>1){
						    		//$.messager.alert('错误提示', '只能选择一个品牌');
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

function getSubList(objId,descId){
	if(objId != ""){
		$.ajax({
		    type: 'POST',
		    url : 'categoryController.do?getSubList',
		    dataType: 'json',
		    data : {'pid' :objId},
		    success : function(data){
		    	if(descId == 'subCategoryId'){
		    		$("#subCategoryId").empty().append("<option value=''>-请选择-</option>");
		    	}
		    	$("#thridCategoryId").empty().append("<option value=''>-请选择-</option>");
		    	$("#thridCategoryId_div").empty();
		    	var htmlStr = "";
		    	var len = data.length;
		    	for(var i=0;i<len;i++){
		    		htmlStr += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
		    	}
		    	$("#"+descId).append(htmlStr);
		    }
		});
	}else{
		if(descId == 'subCategoryId'){
    		$("#subCategoryId").empty().append("<option value=''>-请选择-</option>");
    	}
    	$("#thridCategoryId").empty().append("<option value=''>-请选择-</option>");
    	$("#thridCategoryId_div").empty();
	}
}

function getThirdCheckboxList(objId){
	if(objId != ""){
		$.ajax({
		    type: 'POST',
		    url : 'categoryController.do?getSubList',
		    dataType: 'json',
		    data : {'pid' :objId},
		    success : function(data){
		    	$("#thridCategoryId_div").empty();
//		    	var htmlStr = "<span class="l-btn-left">保存</span></span>&nbsp;&nbsp;<input type='button' onclick='clearAll()' value='全选'/></br>";
		    	var htmlStr = '<a href="javascript:checkAll()" class="l-btn" ><span class="l-btn-left">全选</span></a>&nbsp;&nbsp;<a href="javascript:clearAll()" class="l-btn" ><span class="l-btn-left">取消</span></a></br>';
		    	var len = data.length;
		    	for(var i=0;i<len;i++){
		    		htmlStr += "<label style='padding: 0px 15px 0px 2px;'><input type='checkbox' name='thridCategoryId' value='"+data[i].id+"'/>"+data[i].name+"</label>";
		    		if(i%10==9){
		    			htmlStr +="</br>";
		    		}
		    	}
		    	$("#thridCategoryId_div").append(htmlStr);
		    }
		});
	}else{
    	$("#thridCategoryId_div").empty();
	}
}

//校验比例
function checkAccounting(){
	var flag = true;
	var n=0;
	var total = $("#commission").val()-0;
//	if(total==""){
//		alert("请输入提成占比");
//		$("#commission").focus();
//	}else{
		$("#tb_body tr").each(function(){
			var a1 = $(this).find("td").eq(1).find("input").val();
			var a2 = $(this).find("td").eq(2).find("input").val();
			var a3 = $(this).find("td").eq(3).find("input").val();
			var h = $(this).find("td").eq(4).find("input").val();
			var sum = a1-0+(a2-0)+(a3-0);
			if(sum!=total||h==""){
				n++;
			}
		})
//	}
	if(n>0){
		flag = false;
		alert("请检查占比设置");
	}
	return flag;
}