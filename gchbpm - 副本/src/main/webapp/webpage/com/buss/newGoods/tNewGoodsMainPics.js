$(function () {
	//上传主图
// 	    var M = new QiniuJsSDK();
// 	    var uploaderMainPics = M.uploader({
// 	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
// 	        browse_button: 'templatePic_main',         // 上传选择的点选按钮，必需
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
// 	        container: 'container_main_pics',             // 上传区域DOM ID，默认是browser_button的父元素
// 	        max_file_size: '100kb',             // 最大文件体积限制
// 	        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
// 	        max_retries: 3,                     // 上传失败最大重试次数
// 	        dragdrop: true,                     // 开启可拖曳上传
// 	        drop_element: 'container_main_pics',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
// 	        chunk_size: '4mb',                  // 分块上传时，每块的体积
// 	        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
// 	        filters : {
// 	            max_file_size : '100kb',
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
// 	            	var n = $("#main_pics div.brick").length;
// 	            	if(n+1>5){
// 	            		up.stop();
// 	            		alert("主图不能超过5张");
// 	            	}
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
// 	                   var div_imgs = "<div class='brick' ><input type='hidden' value=''/><a class='delete' onclick='deMainPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='200' ></img></div>"
// 	   					$("#main_pics").append(div_imgs);
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
// 	                var key = "mainPic/"+time+"/"+name;
// 	                return key;
// 	            }
// 	        }
// 		});
		
		//七牛上传
		var paramsJson = {
			cropperWidth: 750, //裁剪宽度
			cropperHeight: 750, //裁剪高度
			moduleName: 'mainPic',  //模块名称
			dealSourceLinkFn: function(sourceLink){  //处理上传图片路径
				var div_imgs = "<div class='brick' ><input type='hidden' value=''/><a class='delete' onclick='deMainPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='200' ></img></div>"
				$("#main_pics").append(div_imgs);
			}
		}
		new BtnSelectFileVo('#templatePic_main', paramsJson);  //按钮点击选择
		new DragDropSelectFileVo('#main_pics', paramsJson);  //拖曳选择
})

//删除主图 
function deMainPic(obj){
   $(obj).parent().remove();
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

//d+初始化图片
/**
 * @param name 图片名称
 * @param tempPic 上传按钮
 * @param picDivContainer 上传容器
 * @param picId id
 */
function initPicUpload(name,tempPic,picDivContainer,picId){
	 var T = new QiniuJsSDK();
	    var uploaderMainPics = T.uploader({
	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
	        browse_button: tempPic,         // 上传选择的点选按钮，必需
	        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
	        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
	        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//	        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
	         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//	         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//	            // do something
//	            return getUptoken();
//	         },
	        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
	        // downtoken_url: '/downtoken',
	        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
	        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
	        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
	        domain: domain,     // bucket域名，下载资源时用到，必需
	        container: picDivContainer,             // 上传区域DOM ID，默认是browser_button的父元素
	        max_file_size: '100kb',             // 最大文件体积限制
	        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
	        max_retries: 3,                     // 上传失败最大重试次数
	        dragdrop: true,                     // 开启可拖曳上传
	        drop_element: picDivContainer,          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
	        chunk_size: '4mb',                  // 分块上传时，每块的体积
	        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
	        filters : {
	            max_file_size : '100kb',
	            prevent_duplicates: false,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
	            // Specify what files to browse for
	            mime_types: [
	                {title : "Image files", extensions : "jpg,png,jpeg,bmp"} // 限定jpg,jpeg,png等后缀上传
	            ]
	        },
	        init: {
	            'FilesAdded': function(up, files) {
	                plupload.each(files, function(file) {
	                    // 文件添加进队列后，处理相关的事情
	                });
	            },
	            'BeforeUpload': function(up, file) {
	                   // 每个文件上传前，处理相关的事情
	            },
	            'UploadProgress': function(up, file) {
	                   // 每个文件上传时，处理相关的事情
	            },
	            'FileUploaded': function(up, file, info) {
	                   // 每个文件上传成功后，处理相关的事情
	                   // 其中info是文件上传成功后，服务端返回的json，形式如：
	                   // {
	                   //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
	                   //    "key": "gogopher.jpg"
	                   //  }
	                   // 查看简单反馈
	                   var domain = up.getOption('domain');
	                   var res = $.parseJSON(info);
	                   var sourceLink = domain + res.key; //获取上传成功后的文件的Url
	                   var div_imgs = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100' ></img></div>"
		   				$("#"+picDivContainer).html(div_imgs);
		   				$("#"+picId).val(sourceLink);
	            },
	            'Error': function(up, err, errTip) {
	                   //上传出错时，处理相关的事情
	            	alert(errTip);
	            },
	            'UploadComplete': function() {
	                   //队列文件处理完毕后，处理相关的事情
	            },
	            'Key': function(up, file) {
	                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
	                // 该配置必须要在unique_names: false，save_key: false时才生效
	                var name = (new Date()).Format("yyyyMMddhhmmss")+getRandomStr();
	                var key = "proInfo/"+name;
	                return key;
	            }
	        }
	    });
	
}


//d+初始化小图，主图图片的name,和src
function initPicsOrder(){
var flag = true;
var pics_small_num = $("#main_pics_small img").length;
if(pics_small_num==0){
//	alert("请上传商品小图");
	$.messager.alert('提示信息',"请上传商品小图");
	return false;
}
var main_pics_num = $("#main_pics img").length;
if(main_pics_num==0){
//	alert("请上传商品主图");
	$.messager.alert('提示信息',"请上传商品主图");
	return false;
}
	//主图排序
 var $pics_main = $("#main_pics input[type='hidden']");
 var order = "";
 var url = "";
 $.each( $pics_main, function(index, domEle){
	 if(0==index){
		 order = "One";
	 }else if(1==index){
		 order = "Two";
	 }else if(2==index){
		 order = "Three";
	 }else if(3==index){
		 order = "Four";
	 }else{
		 order = "Five";
	 }
	 url = $(this).next().next().attr("src");
	 $(this).attr("name","pic"+order);
	 $(this).attr("value",url);
	});
 return flag;
}

