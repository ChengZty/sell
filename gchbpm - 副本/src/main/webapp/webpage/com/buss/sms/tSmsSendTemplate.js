function setPreviewHtml(){
	var templateName = $("#templateName").val();
	var autographName = $("#autographName").val();
	var content = $("#content").val();
	var url = $("#url").val();
	var contentEnd = $("#contentEnd").val();
	var contentEnd2 = $("#contentEnd2").val();
	
	if(autographName!=""){
		autographName = "【"+autographName+"】"
	}
	if (url!="") {
		url="快戳 "+url;
	}
	if(contentEnd2!=""){
		contentEnd2 = "，"+contentEnd2; 
	}
	var contenttext = autographName+content+contentEnd+url+contentEnd2;
	var sumContentNumber = content.length+contentEnd.length+contentEnd2.length;
	var shortNumber = parseInt($("#shortNumber").text());
	$("#countNumber").html(sumContentNumber);
	$("#nameNumber").html(autographName.length);
	$("#number").html(sumContentNumber+autographName.length+shortNumber);
	$("#surplusNumber").html(500-autographName.length-sumContentNumber-shortNumber);
	/*var cententNumber = 500-name.length-shortNumber;
	$("#cententNumber").html(cententNumber);
	$("#content").attr("maxlength",cententNumber)*/
	$("#cententNumber").html(500);
	$("#previewID").html(contenttext);
}

function setAutographName(){
	var idva = $("#autographId").find("option:selected").val();
	if(""!=idva){
		var autographName = $("#autographId").find("option:selected").text();
		$("#autographName").val(autographName);
	}else{
		$("#autographName").val("");
	}
	setPreviewHtml();
}