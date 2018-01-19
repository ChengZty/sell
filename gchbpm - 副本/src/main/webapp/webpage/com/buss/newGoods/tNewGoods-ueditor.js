var leipiEditor = UE.getEditor('content0',{
    toolleipi:true,//是否显示，设计器的 toolbars
    textarea: 'descList[0].goodsDesc',   
    toolbars: [[
                'fullscreen', 'source', '|', 'undo', 'redo', '|','date', 'time',
	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 
//	            'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
	            'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	            'customstyle', 'paragraph','|',
//	            'directionalityltr', 'directionalityrtl', 'indent', '|',
	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
//	            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	            'simpleupload', 'insertimage', //'emotion',
    ]],
	wordCount:true,
    maximumWords:5000,
    autoHeightEnabled:false,
    initialFrameHeight:400,
    elementPathEnabled:false
});
var leipiEditor1 = UE.getEditor('content1',{
	toolleipi:true,//是否显示，设计器的 toolbars
	textarea: 'descList[1].goodsDesc',   
	toolbars: [[
	            'fullscreen', 'source', '|', 'undo', 'redo', '|','date', 'time',
	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 
//	            'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
	            'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	            'customstyle', 'paragraph','|',
//	            'directionalityltr', 'directionalityrtl', 'indent', '|',
	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
//	            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	          //'simpleupload', 
	            'insertimage', 
	            ]],
	            wordCount:true,
	            maximumWords:5000,
	            autoHeightEnabled:false,
	            initialFrameHeight:400,
	            elementPathEnabled:false
});
var leipiEditor2 = UE.getEditor('content2',{
	toolleipi:true,//是否显示，设计器的 toolbars
	textarea: 'descList[2].goodsDesc',   
	toolbars: [[
	             'fullscreen', 'source', '|', 'undo', 'redo', '|','date', 'time',
	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 
//	            'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
	            'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	            'customstyle', 'paragraph','|',
//	            'directionalityltr', 'directionalityrtl', 'indent', '|',
	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
//	            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	          //'simpleupload', 
	            'insertimage',  //'emotion',
	            ]],
	            wordCount:true,
	            maximumWords:5000,
	            autoHeightEnabled:false,
	            initialFrameHeight:400,
	            elementPathEnabled:false
});
var leipiEditor3 = UE.getEditor('content3',{
	toolleipi:true,//是否显示，设计器的 toolbars
	textarea: 'descList[3].goodsDesc',   
	toolbars: [[
	            'fullscreen', 'source', '|', 'undo', 'redo', '|','date', 'time',
	            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 
//	            'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
	            'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
	            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
	            'customstyle', 'paragraph','|',
//	            'directionalityltr', 'directionalityrtl', 'indent', '|',
	            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
//	            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
	          //'simpleupload', 
	            'insertimage',  //'emotion',
	            ]],
	            wordCount:true,
	            maximumWords:5000,
	            autoHeightEnabled:false,
	            initialFrameHeight:400,
	            elementPathEnabled:false
});

function setContent(){
	$("#descList0").val(leipiEditor.getContent());
	$("#descList1").val(leipiEditor1.getContent());
	$("#descList2").val(leipiEditor2.getContent());
	$("#descList3").val(leipiEditor3.getContent());
}

//提交
function sub2(){
	  var goodsStock = $("#goodsStock").val();
	  if(goodsStock==""||goodsStock=="0"){
		  $.messager.alert('提示信息',"请填写库存明细");
		  return false;
	  }
	  var nullNum = checkSpec();
	  if(nullNum==0){
		  setContent();
	  	$("#publishStatus").val("1");
		$("#formobj").submit();
	  }
}

//存草稿
function save2(){
	  var nullNum = checkSpec();
	  if(nullNum==0){
		  setContent();
	  	$("#publishStatus").val("2");
		$("#formobj").submit();
	  }
}