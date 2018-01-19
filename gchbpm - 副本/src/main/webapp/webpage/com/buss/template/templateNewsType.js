$(function(){
	//品牌图片
    var P = new QiniuJsSDK();
    var uploaderPic = P.uploader({
        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
        browse_button: 'pickfiles_1',         // 上传选择的点选按钮，必需
        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//            // do something
//            return getUptoken();
//         },
        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
        // downtoken_url: '/downtoken',
        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
        domain: domain,     // bucket域名，下载资源时用到，必需
        container: 'container_1',             // 上传区域DOM ID，默认是browser_button的父元素
        // max_file_size: '100kb',             // 最大文件体积限制
        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
        max_retries: 3,                     // 上传失败最大重试次数
        dragdrop: true,                     // 开启可拖曳上传
        drop_element: 'container_1',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
        chunk_size: '4mb',                  // 分块上传时，每块的体积
        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
        resize: {
            quality: 90,  //需要指定这个参数
            compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
        },
        filters : {
            // max_file_size : '100kb',
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
//                	console.log(file);
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
                   var div_imgs = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='90' width='150'></img></div>"
	   				$("#container_div_1").html(div_imgs);
	   				$("#smallPic").val(sourceLink);
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
	                var name = (new Date()).Format("MMddhhmmss")+getRandomStr();
	                var n = file.name.lastIndexOf(".");
	            	name += file.name.substring(n);
                var time = new Date().Format("yyyyMMdd");
                var key = directory+"newsType/"+time+"/"+name;
                return key
            }
        }
    });
    
  //品牌图片
    var P2 = new QiniuJsSDK();
    var uploaderPic2 = P2.uploader({
        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
        browse_button: 'pickfiles_2',         // 上传选择的点选按钮，必需
        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//            // do something
//            return getUptoken();
//         },
        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
        // downtoken_url: '/downtoken',
        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
        domain: domain,     // bucket域名，下载资源时用到，必需
        container: 'container_2',             // 上传区域DOM ID，默认是browser_button的父元素
        // max_file_size: '200kb',             // 最大文件体积限制
        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
        max_retries: 3,                     // 上传失败最大重试次数
        dragdrop: true,                     // 开启可拖曳上传
        drop_element: 'container_2',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
        chunk_size: '4mb',                  // 分块上传时，每块的体积
        auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
        resize: {
            quality: 90,  //需要指定这个参数
            compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
        },
        filters : {
            // max_file_size : '200kb',
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
                   var div_imgs = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='150' width='252'></img></div>"
	   				$("#container_div_2").html(div_imgs);
	   				$("#coverPic").val(sourceLink);
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
	                var name = (new Date()).Format("MMddhhmmss")+getRandomStr();
	                var n = file.name.lastIndexOf(".");
	            	name += file.name.substring(n);
                var time = new Date().Format("yyyyMMdd");
                var key = "newsType/"+time+"/"+name;
                return key
            }
        }
    });
})

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

//删除图片 和字段值
function delPic(obj){
	$(obj). closest("td").find("input:last").val("");
	$(obj).parent().remove();
} 