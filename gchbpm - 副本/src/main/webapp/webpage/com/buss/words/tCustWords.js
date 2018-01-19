//通过pid获取下级分类
function getSubList(parentId){
	if(parentId != ""){
		$.ajax({
		    type: 'POST',
		    url : 'tCustWordsTypeController.do?getSubList',
		    dataType: 'json',
		    data : {'pid' :parentId},
		    success : function(data){
//		    	console.log(data);
		    	$("#subTypeId").empty().append("<option value=''>-请选择-</option>");
		    	var optionsStr = "";
		    	 var temStr="";
		    	var len = data.length;
		    	for(var i=0;i<len;i++){
		    		optionsStr += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
//		    		temStr +="<input type='hidden' id='"+data[i].id+"ID' value='"+data[i].type+"'/>";
		    	}
		    	$("#subTypeId").append(optionsStr);
//		    	$("#temStr").html(temStr);//保存分类是否可录图片的信息
		    	$("#tb tbody tr.t").remove();
				$("#type").val("");
		    }
		});
	}else{
		$("#subTypeId").empty().append("<option value=''>-请选择-</option>");
		$("#temStr").empty();
		$("#tb tbody tr.t").remove();
		$("#type").val("");
	}
}

/**显示文字话术或者图片话术下拉框*/
function changeTypeDisplay(subTypeId){
	if(subTypeId!=""){//默认显示文字话术
		$("#type").show();
		$("#tb tbody tr.t").remove();
		var trStr = $("#div_tb tr:eq(0)").clone();
		$("#tb tbody").append(trStr);
	}else{
		$("#type").hide();
		$("#tb tbody tr.t").remove();
	}
}

/**显示内容，文字话术或者图片话术*/
function changeCotentDisplay(type){
//	var type = $("#"+subId+"ID").val();
//	if(type){
		$("#tb tbody tr.t").remove();
		if(type=="2"){//图片
			var trStr = $("#div_tb tr:eq(1)").clone();
			$(trStr).find("a").attr("id","pickfiles_1");
			$(trStr).find("div#container_").attr("id","container_1");
			$(trStr).find("div#container_div_").attr("id","container_div_1");
			$("#tb tbody").append(trStr);
			//初始化图片上传七牛
			initPicsUpload();
			//初始化拖拽对象
			initSortablePics();
		}else{//文字
			var trStr = $("#div_tb tr:eq(0)").clone();
			$("#tb tbody").append(trStr);
		}
		$("#type").val(type);
//	}else{
//		$("#tb tbody tr.t").remove();
//		$("#type").val("");
//	}
}

/**初始化拖拽对象*/
function initSortablePics(){
	var bar = document.getElementById("container_div_1");
	Sortable.create(bar, { group: "omega" });
}

/**初始化图片上传七牛*/
function initPicsUpload(){
	var uploader = Qiniu.uploader({
        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
        browse_button: 'pickfiles_1',         // 上传选择的点选按钮，必需
        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
        // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
        // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//            return uptoken;
//         },
        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
        // downtoken_url: '/downtoken',
        // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
        // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
        // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
        domain: domain,     // bucket域名，下载资源时用到，必需
        container: 'container_1',             // 上传区域DOM ID，默认是browser_button的父元素
        // max_file_size: '1m',             // 最大文件体积限制
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
            // max_file_size : '1m',
            prevent_duplicates: false,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
            // Specify what files to browse for
            mime_types: [
                {title : "Image files", extensions : "jpg,png,jpeg,bmp,gif"} // 限定jpg,jpeg,png等后缀上传
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
                   var div_imgs = "<div class='brick' ><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100' ></img>"
                   +"<input type='hidden' name='content' value='"+sourceLink+"' class='picUrl'><input type='hidden' class='id'/><input type='hidden' class='custWordsId'/></div>"
//  					$("#container_div_1").append(div_imgs);
                   $("#container_div_1").html(div_imgs);
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
//            	if(file.type=="image/gif"){
//					name +=".gif";
//                }
            	var n = file.name.lastIndexOf(".");
            	name += file.name.substring(n);
                var time = new Date().Format("yyyyMMdd");
                var key = directory+"wordsPics/"+time+"/"+name;
                return key;
            }
        }
    });
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

//删除主图 
function delPic(obj){
   $(obj).parent().remove();
} 

//判断并初始化图片下标
function checkAndInitPics(){
	var type = $("#type").val();
	if(type=="2"){//图片话术
//		var idx = 0;
		var container_div = $("#container_div_1").find("div");
		if($(container_div).length>0){
//			$(container_div).each(function(){
//				$(this).find("input[class='id']").attr("name","picList["+idx+"].id");
//				$(this).find("input[class='picUrl']").attr("name","picList["+idx+"].picUrl");
//				$(this).find("input[class='custWordsId']").attr("name","picList["+idx+"].custWordsId");
//				idx++;
//			})
		}else{
			alert("请上传图片");
			return false;
		}
	}
	return true;
}

//判断是否显示图片
function checkPicShow(value,row,index){
	 var type = row.type;//1:文字，2：图片
	 if("2"==type){
		 if(value!=""){
			 return '<img width="70" height="70" border="0" src="'+value+'">';
		 }else{
			 return '';
		 }
	 }else{
		 return value;
	 }
}