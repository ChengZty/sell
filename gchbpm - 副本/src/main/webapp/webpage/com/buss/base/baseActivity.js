$(function () {
    	//封面图片
        $('#templatePic_u').uploadify({buttonText:'浏览',
            progressData:'speed',
            multi:false,
            height:25,
            queueID:'progress_bar_m',
            overrideEvents:['onDialogClose'],
            fileTypeDesc:'文件格式:',
            fileTypeExts:'*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
            fileSizeLimit:'210KB',
            swf:'plug-in/uploadify/uploadify.swf',
            uploader:'tFinActivityController.do?uploadPic&sessionId=${pageContext.session.id}',
            auto:true,
            onUploadSuccess : function(file, data, response) {
                if(data){
                    var d=$.parseJSON(data);
                    $("#prePic").attr("src",d.msg);
                    $("#coverPic").val(d.msg);
                }
            }
        });
    });

var leipiEditor = UE.getEditor('content',{
    toolleipi:true,//是否显示，设计器的 toolbars
    textarea: 'newsContext',   //属性名称
    toolbars: [[
    'fullscreen', 'source', '|', 'undo', 'redo', '|','date', 'time',
    'fontfamily', 'fontsize', '|', 'indent', '|',
    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 
    'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor',
    'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
    'customstyle', 'paragraph','|',
    'directionalityltr', 'directionalityrtl', 'indent', '|',
    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
    'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
  //'simpleupload', 
    'insertimage', 
//    'emotion',
//    'map',  'insertframe',  'pagebreak',  'background', '|',
//    'horizontal',  'spechars',  '|',
//    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', '|',
//    'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', '|','charts', '|',
//    'print', 
    'insertvideo',
    'preview',
//    'searchreplace', //'help', 'drafts'
    ]],
    wordCount:true,
    maximumWords:5000,
    autoHeightEnabled:false,
//    initialFrameWidth:1000,
    initialFrameHeight:350,
    elementPathEnabled:false
});


function sub(){
	$("#formobj").submit();
}

function goBack(){
	if(source==1){//平台
		document.location="baseActivityController.do?list";
	}else if(source==2){//零售商
		document.location="baseActivityController.do?listOfRetailer";
	}
}