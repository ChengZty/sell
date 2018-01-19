/**
 * 上新 图片上传七牛
 */
$(function () {
	//正面图
	    var uploader = Qiniu.uploader({
	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
	        browse_button: 'pickfiles_1',         // 上传选择的点选按钮，必需
	        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
	        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
	        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//	        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
	         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//	         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//	            return uptoken;
//	         },
	        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
	        // downtoken_url: '/downtoken',
	        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
	        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
	        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
	        domain: domain,     // bucket域名，下载资源时用到，必需
	        container: 'container_1',             // 上传区域DOM ID，默认是browser_button的父元素
	        // max_file_size: '500kb',             // 最大文件体积限制
	        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
	        max_retries: 3,                     // 上传失败最大重试次数
	        dragdrop: true,                     // 开启可拖曳上传
	        drop_element: 'container_1',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
	        chunk_size: '4mb',                  // 分块上传时，每块的体积
	        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
	        //x_vars : {
	        //    查看自定义变量
	        //    'time' : function(up,file) {
	        //        var time = (new Date()).getTime();
	                  // do something with 'time'
	        //        return time;
	        //    },
	        //    'size' : function(up,file) {
	        //        var size = file.size;
	                  // do something with 'size'
	        //        return size;
	        //    }
			//},
			resize: {
				quality: 90,  //需要指定这个参数
				compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
	        },
	        filters : {
	            // max_file_size : '500kb',
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
	                   var img_tag = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100'/>"
	                   +"<input type='hidden' value='"+sourceLink+"' class='url'><input type='hidden' class='idx'/><input type='hidden' class='type'/><input type='hidden' class='id'/><input type='hidden' class='recommendId'/></div>";
	                   $("#container_1_div").append(img_tag);
	                   initPicTextShow();//商品图片需要显示 [正面图][吊牌图]
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
					//如果是png，则将png转成jpg上传，此处修改后缀名
					var tempFileName = file.name;
					if(tempFileName.indexOf('.png') > -1){
						tempFileName = tempFileName.replace('.png', '.jpg');
					}
 	                var name = (new Date()).Format("yyyyMMddhhmmss")+getRandomStr();
 	                var n = tempFileName.lastIndexOf(".");
	            	name += tempFileName.substring(n);
	                var time = new Date().Format("yyyyMMdd");
	                var key = directory+"1/"+time+"/"+name;
	                return key;
	            }
	        }
	    });
/*	    
	  //搭配图
	    var Q2 = new QiniuJsSDK();
	    var uploader2 = Q2.uploader({
	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
	        browse_button: 'pickfiles_2',         // 上传选择的点选按钮，必需
	        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
	        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
	        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//	        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
	         uptoken_url: ctx+'/systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
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
	        container: 'container_2',             // 上传区域DOM ID，默认是browser_button的父元素
	        max_file_size: '500kb',             // 最大文件体积限制
	        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
	        max_retries: 3,                     // 上传失败最大重试次数
	        dragdrop: true,                     // 开启可拖曳上传
	        drop_element: 'container_1',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
	        chunk_size: '4mb',                  // 分块上传时，每块的体积
	        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
	        //x_vars : {
	        //    查看自定义变量
	        //    'time' : function(up,file) {
	        //        var time = (new Date()).getTime();
	                  // do something with 'time'
	        //        return time;
	        //    },
	        //    'size' : function(up,file) {
	        //        var size = file.size;
	                  // do something with 'size'
	        //        return size;
	        //    }
			//},
			resize: {
				quality: 90,  //需要指定这个参数
				compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
	        },
	        filters : {
	            max_file_size : '500kb',
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
	                   var img_tag = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100'/>"
	                   +"<input type='hidden' value='"+sourceLink+"' class='url'><input type='hidden' class='type'/><input type='hidden' class='id'/><input type='hidden' class='recommendId'/></div>";
	                   $("#container_2_div").append(img_tag);
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
	                var time = new Date().Format("yyyyMMdd");
	                var key = directory+"2/"+time+"/"+name;
	                return key
	            }
	        }
	    });
	    //细节图
	    var Q3 = new QiniuJsSDK();
	    var uploader3 = Q3.uploader({
	    	runtimes: 'html5,flash,html4',      // 上传模式，依次退化
	    	browse_button: 'pickfiles_3',         // 上传选择的点选按钮，必需
	    	// 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
	    	// 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
	    	// 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//	    	uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
	    	 uptoken_url: ctx+'/systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
	    	// uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
	    	//    // do something
	    	//    return uptoken;
	    	// },
	    	get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
	    	// downtoken_url: '/downtoken',
	    	// Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
	    	// unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
	    	// save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
	    	domain: domain,     // bucket域名，下载资源时用到，必需
	    	container: 'container_3',             // 上传区域DOM ID，默认是browser_button的父元素
	    	max_file_size: '500kb',             // 最大文件体积限制
	    	flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
	    	max_retries: 3,                     // 上传失败最大重试次数
	    	dragdrop: true,                     // 开启可拖曳上传
	    	drop_element: 'container_1',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
	    	chunk_size: '4mb',                  // 分块上传时，每块的体积
	    	auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
	    	//x_vars : {
	    	//    查看自定义变量
	    	//    'time' : function(up,file) {
	    	//        var time = (new Date()).getTime();
	    	// do something with 'time'
	    	//        return time;
	    	//    },
	    	//    'size' : function(up,file) {
	    	//        var size = file.size;
	    	// do something with 'size'
	    	//        return size;
	    	//    }
			//},
			resize: {
				quality: 90,  //需要指定这个参数
				compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
	        },
	    	filters : {
	            max_file_size : '500kb',
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
	    			var img_tag = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100'/>"
	                   +"<input type='hidden' value='"+sourceLink+"' class='url'><input type='hidden' class='type'/><input type='hidden' class='id'/><input type='hidden' class='recommendId'/></div>";
	    			$("#container_3_div").append(img_tag);
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
	                var time = new Date().Format("yyyyMMdd");
	                var key = directory+"3/"+time+"/"+name;
	    			return key
	    		}
	    	}
	    });
	    */
})

//获取七牛uptoken和失效时间
/*
function getUptoken(){
	var token = "";
	var url = ctx+'/systemController.do?getQNUptoken';
	$.ajax({
		url : url,
		type : 'post',
		cache : false,
		async: false,
		success : function(data) {
			var d = $.parseJSON(data);
			token = d.uptoken;
		}
	});
	return token;
}*/

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

//初始化图片和视频下标
function initPicsAndVideos(){
	var idx = 0;
	for(var n=1;n<5;n++){
		var container_div = $("#container_"+n+"_div").find("div");
		if($(container_div).length>0){
			$(container_div).each(function(){
				$(this).find("input[class='idx']").attr("name","recommendDetailsList["+idx+"].idx").val(idx);
				$(this).find("input[class='url']").attr("name","recommendDetailsList["+idx+"].url");
				$(this).find("input[class='type']").attr("name","recommendDetailsList["+idx+"].type").val(n);
				$(this).find("input[class='id']").attr("name","recommendDetailsList["+idx+"].id");
				$(this).find("input[class='recommendId']").attr("name","recommendDetailsList["+idx+"].recommendId");
				idx++;
			})
		}
	}
}