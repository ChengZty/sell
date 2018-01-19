//返回话题列表
function back() {
	var list = "platformNewsList";
	if(userType!="01"){
		list = "retailerNewsList";
	}
  document.location="tNewsController.do?"+list;
}

//返回管家课堂或者管家故事
function back2() {
	var isRet = "1";
	if(userType=="01"){
		isRet = "0";
	}
	document.location="tNewsController.do?tNewsOfType&newsType="+newsType+"&isRet="+isRet;
}
//返回话题列表
function backList(data) {
	if(data.success){
			 back();
	}
}

//返回管家课堂或者管家故事
function backList2(data) {
	if(data.success){
		back2();
	}
}

//保存
 function sub(){
//		var newsTypeY = $("#news_type input[class='isNeed']");
//		 if($(newsTypeY).length>0){
//			var checked_news_type = $("#news_type input[class='isNeed']:checked");
//			if($(checked_news_type).length==0){
//				$.messager.alert('提示信息',"请选择必选分类");
//				return false;
//			}
//		 }
//		var checked_news_type_non = $("#news_type input[class='nonNeed']:checked");
//		 if($(checked_news_type_non).length==0){
//			$.messager.alert('提示信息',"请选择资讯分类");
//				return false;
//		 }else if($(checked_news_type_non).length>2){
//			 $.messager.alert('提示信息',"资讯分类不能超过2个");
//			 return false;
//		 }
//		  $.messager.confirm('提示信息', "确定？", function(r){
//			  if (r) {
				  	$("#formobj").submit();
//				}
//		  })
	  }

var leipiEditor = UE.getEditor('content',{
    toolleipi:true,//是否显示，设计器的 toolbars
    textarea: 'zzzz',   
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
    initialFrameHeight:300,
    elementPathEnabled:false
});

//发布
function setUpload(){
	$("#upLoaded").val("Y");
	$("#btn").click();
}
//下架
function doDown(){
	$("#upLoaded").val("N");
	$("#btn").click();
}

//图片加商品tags(锚点图片)
function addTags(){
	 var src = $("#prePic2").attr("src");
	 if(src!=""){
		 $("#pic_add_tags").attr("src",src);
   	 $("#addTagsDiv").show();
	 }
}

//查询单品(锚点图片)
/*function findGoods(obj) {
  var p_elmt = $(obj);//当前p标签
	  var url = "tGoodsController.do?singleList";
		 $.dialog.setting.zIndex = 2001;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:"+url,
					lock : true,
					title:"选择商品",
					width:1000,
					height: 700,
					cache:false,
				    ok: function(){
				    	iframe = this.iframe.contentWindow;
				    	var selected = iframe.getSelectRows();
				    	if (selected == '' || selected == null ){
				    		alert('请选择商品');
// 				    		$.messager.alert('错误提示', '请选择商品');
				    		return false;
					    }else {
					    	if(selected.length>1){
					    		alert('只能选择一个商品');
// 					    		$.messager.alert('错误提示', '只能选择一个商品');
					    		return false;
					    	}
					    	var goodsName = selected[0].goodsName;
					    	var brandName = selected[0].brandName;
					    	var currentPrice = selected[0].currentPrice;
					    	var shortName = prompt("请修改为8字以内商品名称:",goodsName);
					    	if(shortName!=""&&shortName!=null){
					    		shortName = $.trim(shortName).substring(0,8);
					    		$(p_elmt).html("<p>"+shortName+"</p><p>"+brandName+"</p>");
//					    	$(p_elmt).html(goodsName+"...</br>"+brandName+"</br>￥"+currentPrice+"RMB");
					    		$(p_elmt).parent().parent().attr("src",""+selected[0].id);
					    	}
					    }
				    },
				    cancelVal: '关闭',
				    cancel: true 为true等价于function(){}
				}).zindex();
			} 
	}*/
//确定(锚点图片)
/*function subPicMap(){
  closePicDiv();
  //TODO 传值html
  var pic_maps = $("div.ndd-drawables-container").find("div.ndd-drawable");
  if($(pic_maps).length>0){
	   var content = '[';
	   $(pic_maps).each(function(){
		   var goodsId = $(this).find("a").attr("src");
		   if(goodsId!=""){
			   content+="{";
			   var id = $(this).attr("id");
			   var p = $(this).find("div.div_p").html();
			   var l = $(this).css("left");
			   var t = $(this).css("top");
			   if(l.indexOf("px")>0){
				   l=l.slice(0,-2);
				   t=t.slice(0,-2);
				   content+='"id":"'+id+'","l":"'+(l*100/div_width).toFixed(2)+'%","t":"'+(t*100/div_height).toFixed(2)+'%","goodsId":"'+goodsId+'","p":"'+p+'"';
			   }else if(l.indexOf("%")>0){
				   content+='"id":"'+id+'","l":"'+l+'","t":"'+t+'","goodsId":"'+goodsId+'","p":"'+p+'"';
			   }
		   		content+="},";
			 }
		   });
	   if(content.length>1){
		   content = content.slice(0,-1);
	   }
	   content+=']';
	   $("#picMapContent").val(content);
  }
  else{
	  $("#picMapContent").val("");
  }
}*/

//关闭div(锚点图片)
function closePicDiv(){
  $("#addTagsDiv").hide();
}

//删除锚点图片
function delPicMap(obj){
	   $(obj).parent().parent().parent().remove();
}

//预览     
function view(){
	var html = "";
	var titlePic = $("#titlePic").val();
	var title = $("#title").val();
	var picMapContent = $("#picMapContent").val();
	var newsTypeNames = getNewsTypeNames();
//	var createDate = $("#createDate").val();
	var author = $("#author").val();
	var obj = $.parseJSON(picMapContent);
//	alert(obj[0].id);
	if(titlePic!=""){
		html +="<div style='position: relative;left:0px;top:0px;'><img src='"+titlePic+"'>";
		if(obj!=null&&obj.length>0){
			for(var i=0;i<obj.length;i++){
				html+="<div id="+obj[i].id+" style='position: absolute;left:"+obj[i].l+";top:"+obj[i].t+";'>"
				+"<div style='width: 90px;height: 40px;background: rgba(0, 0, 0, 0.2);'><div style='width: 8px;height: 8px;background: yellow; z-index: 9999;border-radius: 100px;position: absolute;top: 2px;left: 2px;'></div>"
				+"<a src='"+obj[i].goodsId+"'><div style='width:90px;height:40px;padding: 10px;position: absolute;left:-2px;top:-2px;'><p style='color: white;margin:0px;font:12px sans-serif;'>"+obj[i].p+"</p></div></a></div></div>";
			}
		}
		html+="</img></div>";
	}
	html +="<div style='text-align: center;padding:2px 10px;'><h2>"+title+"</h2></div>";
	html +="<div><div style='color:rgb(162, 162, 162);text-align: center;'>"
		+"<table style='margin:auto;'><tr><td style='border-bottom: 1px solid rgb(162, 162, 162);'><span style='padding:5px 10px; display:block;'>"+newsTypeNames+"</span></td></tr>";
//	html +="<div style='color:rgb(162, 162, 162);text-align: center;'>";
//	if(createDate!=""){
//		createDate = createDate.substring(0,10);
//	}else{
//		var d = new Date();
//		var createDate = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
//	}
//	html +=createDate+"&nbsp;&nbsp;"
	html +="<tr><td><span>by&nbsp;"+author+"</span></tr></td></table></div></div>";
	html +=leipiEditor.getContent();
	$("#preview-msg-content").html(html);
	$("#content_view").show();
}
   
function getNewsTypeNames(){
	var names = ""
	var checked_news_type_non = $("#news_type input[class='nonNeed']:checked");
		 if($(checked_news_type_non).length>2){
			$.messager.alert('提示信息',"资讯分类不能超过2个");
			return false;
		 }else{
			 $(checked_news_type_non).each(function(idx){
				 names += $(this).next("span").text()+" | ";
			 });
			 names = names.substring(0, names.length-3)
		 }
		 return names;
}

     
//关闭预览
function closeDiv(){
	$("#content_view").hide();
}

function setContent(){
    if(leipiEditor.queryCommandState( 'source' ))
    {
    	leipiEditor.execCommand('source');
    }
            
    if(leipiEditor.hasContents()){
        leipiEditor.sync();
	    $("#newsContext").val(leipiEditor.getContent());
	}
}


//function errorImg(obj){
//    obj.src="online/template/default/images/default.jpg";
//}

function randomColor() {	//16进制方式表示颜色0-F	
	var arrHex = ["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"];	
	var strHex = "#";	
	var index;	
	for(var i = 0; i < 6; i++) {	//取得0-15之间的随机整数	
		index = Math.round(Math.random() * 15);		
		strHex += arrHex[index];
	}	
	return strHex;
}
function changeColor(){
	var links = $("#tag_div a");
	for(var i=0;i<links.length;i++){
		var linkClass = $(links[i]).attr("class");
		if(linkClass != 'easyui-linkbutton l-btn')
		{
			var bgColor = randomColor();//改变背景色的代码，根据修去修改。
			links[i].style.backgroundColor = bgColor;	
		}
	}
}
function sayHi(){
	var scolor,links = document.getElementsByTagName("a");
	for(var i=0;i<links.length;i++){
		links[i].onmouseover = function(){
			var linkClass = $(this).attr("class");
			if(linkClass != 'easyui-linkbutton l-btn')
			{
				scolor = this.style.backgroundColor;
				this.style.color = scolor;
				this.style.borderColor = scolor;
				this.style.backgroundColor = "white";
			}
		}
		links[i].onmouseout = function(){
			var linkClass = $(this).attr("class");
			if(linkClass != 'easyui-linkbutton l-btn')
			{
				this.style.color = "white";
				this.style.borderColor = "";
				this.style.backgroundColor = scolor;
			}	
		}
		links[i].ondblclick = function(){
			var linkClass = $(this).attr("class");
			if(linkClass != 'easyui-linkbutton l-btn')
			{
				var tagVal = $("#tags").val();
				var arr = new Array();
				var newArr = new Array();
				arr = tagVal.split(",");
				var flag = false;
				var count = 0;
				for (i=0;i<arr.length ;i++ )   
				{   
					if(this.childNodes[0].nodeValue != arr[i])
					{
						newArr[count] = arr[i];
						count++;
					} 
				}   
				tagVal = newArr.join(",");
				$("#tags").val(tagVal);
				$(this).parent().remove();
			}
		}
	}
}
function addEvaluation(){
	var txt = document.getElementById("input-txt");
	var btn = document.getElementById("input-btn");
	var divs = document.getElementById("tag_div");
	if(!txt) return false;
	if(!btn) return false;
	var texts,links,spans;
	btn.onclick = function(){
		if(txt.value == "") {
			$.messager.alert("提示","请输入一个标签");
			return false
		};
		var tagVal = $("#tags").val();
		var arr = new Array();  
		arr = tagVal.split(",");
		var flag = false;
		for (i=0;i<arr.length ;i++ )   
		{   
			if(txt.value == arr[i])
			{
				flag =  true;
				break;
			} 
		}   
		
		if(flag){
			$.messager.alert("提示","已经存在标签"+txt.value);
			return false;
		}
		
		$("#tags").val(tagVal+txt.value+",");
		texts = document.createTextNode(txt.value);
		links = document.createElement("a");
		spans = document.createElement("span");
		links.appendChild(texts);
		links.style.backgroundColor = randomColor();
		spans.appendChild(links);
		divs.appendChild(spans);
		sayHi();
	}
}

//获取随机ID
function getRandomStr(){
var str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
var n = 8, s = "";
for(var i = 0; i < n; i++){
  var rand = Math.floor(Math.random() * str.length);
  s += str.charAt(rand);
}
return s;
}

$(function () {
	//标题图片
// 	    var P = new QiniuJsSDK();
// 	    var uploaderPic = P.uploader({
// 	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
// 	        browse_button: 'pickfiles_1',         // 上传选择的点选按钮，必需
// 	        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
// 	        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
// 	        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
// //	        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
// 	         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
// //	         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
// //	            // do something
// //	            return getUptoken();
// //	         },
// 	        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
// 	        // downtoken_url: '/downtoken',
// 	        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
// 	        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
// 	        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
// 	        domain: domain,     // bucket域名，下载资源时用到，必需
// 	        container: 'container_div_1',             // 上传区域DOM ID，默认是browser_button的父元素
// 	        max_file_size: '200kb',             // 最大文件体积限制
// 	        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
// 	        max_retries: 3,                     // 上传失败最大重试次数
// 	        dragdrop: true,                     // 开启可拖曳上传
// 	        drop_element: 'container_div_1',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
// 	        chunk_size: '4mb',                  // 分块上传时，每块的体积
// 	        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
// 	        //x_vars : {
// 	        //    查看自定义变量
// 	        //    'time' : function(up,file) {
// 	        //        var time = (new Date()).getTime();
// 	                  // do something with 'time'
// 	        //        return time;
// 	        //    },
// 	        //    'size' : function(up,file) {
// 	        //        var size = file.size;
// 	                  // do something with 'size'
// 	        //        return size;
// 	        //    }
// 	        //},
// 	        filters : {
// 	            max_file_size : '200kb',
// 	            prevent_duplicates: false,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
// 	            // Specify what files to browse for
// 	            mime_types: [
// 	                {title : "Image files", extensions : "jpg,png,jpeg,bmp"} // 限定jpg,jpeg,png等后缀上传
// 	            ]
// 	        },
// 	        init: {
// 	            'FilesAdded': function(up, files) {
// 	                plupload.each(files, function(file) {
// 	                    // 文件添加进队列后，处理相关的事情
// 	                });
// 	            },
// 	            'BeforeUpload': function(up, file) {
// 	                   // 每个文件上传前，处理相关的事情
// 	            },
// 	            'UploadProgress': function(up, file) {
// 	                   // 每个文件上传时，处理相关的事情
// 	            },
// 	            'FileUploaded': function(up, file, info) {
// 	                   // 每个文件上传成功后，处理相关的事情
// 	                   // 其中info是文件上传成功后，服务端返回的json，形式如：
// 	                   // {
// 	                   //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
// 	                   //    "key": "gogopher.jpg"
// 	                   //  }
// 	                   // 查看简单反馈
// 	                   var domain = up.getOption('domain');
// 	                   var res = $.parseJSON(info);
// 	                   var sourceLink = domain + res.key; //获取上传成功后的文件的Url
// 	                   var div_imgs = "<div class='pic_div'><a class='delete' onclick='delTitlePic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='150' ></img></div>"
// 		   				$("#titlePicDiv").html(div_imgs);
// 		   				$("#titlePic").val(sourceLink);
// 	            },
// 	            'Error': function(up, err, errTip) {
// 	                   //上传出错时，处理相关的事情
// 	            	alert(errTip);
// 	            },
// 	            'UploadComplete': function() {
// 	                   //队列文件处理完毕后，处理相关的事情
// 	            },
// 	            'Key': function(up, file) {
// 	                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
// 	                // 该配置必须要在unique_names: false，save_key: false时才生效
//  	                var name = (new Date()).Format("MMddhhmmss")+getRandomStr();
//  	                var n = file.name.lastIndexOf(".");
//  	            	name += file.name.substring(n);
// 	                var time = new Date().Format("yyyyMMdd");
// 	                var key = directory+"newsPic/"+time+"/"+name;
// 	                return key
// 	            }
// 	        }
// 		});
		
		
	    
	  //封面图片
// 	    var P2 = new QiniuJsSDK();
// 	    var uploaderPic = P2.uploader({
// 	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
// 	        browse_button: 'pickfiles_2',         // 上传选择的点选按钮，必需
// 	        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
// 	        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
// 	        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
// //	        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
// 	         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
// //	         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
// //	            // do something
// //	            return getUptoken();
// //	         },
// 	        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
// 	        // downtoken_url: '/downtoken',
// 	        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
// 	        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
// 	        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
// 	        domain: domain,     // bucket域名，下载资源时用到，必需
// 	        container: 'container_div_2',             // 上传区域DOM ID，默认是browser_button的父元素
// 	        max_file_size: '200kb',             // 最大文件体积限制
// 	        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
// 	        max_retries: 3,                     // 上传失败最大重试次数
// 	        dragdrop: true,                     // 开启可拖曳上传
// 	        drop_element: 'container_div_1',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
// 	        chunk_size: '4mb',                  // 分块上传时，每块的体积
// 	        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
// 	        //x_vars : {
// 	        //    查看自定义变量
// 	        //    'time' : function(up,file) {
// 	        //        var time = (new Date()).getTime();
// 	                  // do something with 'time'
// 	        //        return time;
// 	        //    },
// 	        //    'size' : function(up,file) {
// 	        //        var size = file.size;
// 	                  // do something with 'size'
// 	        //        return size;
// 	        //    }
// 	        //},
// 	        filters : {
// 	            max_file_size : '200kb',
// 	            prevent_duplicates: false,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
// 	            // Specify what files to browse for
// 	            mime_types: [
// 	                {title : "Image files", extensions : "jpg,png,jpeg,bmp"} // 限定jpg,jpeg,png等后缀上传
// 	            ]
// 	        },
// 	        init: {
// 	            'FilesAdded': function(up, files) {
// 	                plupload.each(files, function(file) {
// 	                    // 文件添加进队列后，处理相关的事情
// 	                });
// 	            },
// 	            'BeforeUpload': function(up, file) {
// 	                   // 每个文件上传前，处理相关的事情
// 	            },
// 	            'UploadProgress': function(up, file) {
// 	                   // 每个文件上传时，处理相关的事情
// 	            },
// 	            'FileUploaded': function(up, file, info) {
// 	                   // 每个文件上传成功后，处理相关的事情
// 	                   // 其中info是文件上传成功后，服务端返回的json，形式如：
// 	                   // {
// 	                   //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
// 	                   //    "key": "gogopher.jpg"
// 	                   //  }
// 	                   // 查看简单反馈
// 	                   var domain = up.getOption('domain');
// 	                   var res = $.parseJSON(info);
// 	                   var sourceLink = domain + res.key; //获取上传成功后的文件的Url
// 	                   var div_imgs = "<div class='pic_div'><a class='delete' onclick='delCoverPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='150' ></img></div>"
// 		   				$("#coverPicDiv").html(div_imgs);
// 		   				$("#coverPic").val(sourceLink);
// 	            },
// 	            'Error': function(up, err, errTip) {
// 	                   //上传出错时，处理相关的事情
// 	            	alert(errTip);
// 	            },
// 	            'UploadComplete': function() {
// 	                   //队列文件处理完毕后，处理相关的事情
// 	            },
// 	            'Key': function(up, file) {
// 	                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
// 	                // 该配置必须要在unique_names: false，save_key: false时才生效
//  	                var name = (new Date()).Format("MMddhhmmss")+getRandomStr();
// 	                var time = new Date().Format("yyyyMMdd");
// 	                var key = directory+"newsPic/"+time+"/"+name;
// 	                return key
// 	            }
// 	        }
// 		});
		
		//七牛上传
		var paramsJson_01 = {
			cropperWidth: 750, //裁剪宽度
			cropperHeight: 446, //裁剪高度
			moduleName: 'newsPic',  //模块名称
			dealSourceLinkFn: function(sourceLink){  //处理上传图片路径
				var div_imgs = "<div class='pic_div'><a class='delete' onclick='delTitlePic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='130' ></img></div>"
				$("#titlePicDiv").html(div_imgs);
				$("#titlePic").val(sourceLink);
			}
		}
		new BtnSelectFileVo('#pickfiles_1', paramsJson_01);  //按钮点击选择

		//七牛上传
		var paramsJson_02 = {
			cropperWidth: 750, //裁剪宽度
			cropperHeight: 446, //裁剪高度
			moduleName: 'newsPic',  //模块名称
			dealSourceLinkFn: function(sourceLink){  //处理上传图片路径
				var div_imgs = "<div class='pic_div'><a class='delete' onclick='delCoverPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='130' ></img></div>"
				$("#coverPicDiv").html(div_imgs);
				$("#coverPic").val(sourceLink);
			}
		}
		new BtnSelectFileVo('#pickfiles_2', paramsJson_02);  //按钮点击选择
})

//删除图片
function delTitlePic(obj){
	$(obj).parent().remove();
	$("#titlePic").val("");
}
function delCoverPic(obj){
	$(obj).parent().remove();
	$("#coverPic").val("");
}