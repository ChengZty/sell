function setCatName(obj,descId){
	  var txt = $(obj).find("option:selected").text();
	  if(obj.value==""){
		  txt = "";
	  }
	  $("#"+descId).val(txt);
}

function getSubList(parentId){
	if(parentId != ""){
		$.ajax({
		    type: 'POST',
		    url : 'categoryController.do?getSubList',
		    dataType: 'json',
		    data : {'pid' :parentId},
		    success : function(data){
		    	$("#subCategoryId").empty().append("<option value=''>---所有---</option>");
		    	var htmlStr = "";
		    	var len = data.length;
		    	for(var i=0;i<len;i++){
		    		htmlStr += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
		    	}
		    	$("#subCategoryId").append(htmlStr);
		    	$("#thridCategoryId").empty().append("<option value=''>---所有---</option>");
		    }
		});
	}else{
		$("#subCategoryId").empty().append("<option value=''>---所有---</option>");
		$("#thridCategoryId").empty().append("<option value=''>---所有---</option>");
	}
}

function getThirdList(parentId){
	if(parentId != ""){
		$.ajax({
		    type: 'POST',
		    url : 'categoryController.do?getSubList',
		    dataType: 'json',
		    data : {'pid' :parentId},
		    success : function(data){
		    	$("#thridCategoryId").empty().append("<option value=''>---所有---</option>");
		    	var htmlStr = "";
		    	var len = data.length;
		    	for(var i=0;i<len;i++){
		    		htmlStr += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
		    	}
		    	$("#thridCategoryId").append(htmlStr);
		    }
		});
	}else{
		$("#thridCategoryId").empty().append("<option value=''>---所有---</option>");
	}
}