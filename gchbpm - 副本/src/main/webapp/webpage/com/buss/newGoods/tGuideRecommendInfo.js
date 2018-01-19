$(function(){
	//视频
	  var QV = new QiniuJsSDK();
	  var uploaderV = QV.uploader({
	      runtimes: 'html5,flash,html4',      // 上传模式，依次退化
	      browse_button: 'pickfiles_4',         // 上传选择的点选按钮，必需
	      // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
	      // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
	      // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
//	      uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
	       uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//	       uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//	          // do something
//	          return getUptoken();
//	       },
	      get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
	      // downtoken_url: '/downtoken',
	      // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
	      // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
	      // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
	      domain: domain,     // bucket域名，下载资源时用到，必需
	      container: 'container_4',             // 上传区域DOM ID，默认是browser_button的父元素
	      max_file_size: '20mb',             // 最大文件体积限制
	      flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
	      max_retries: 3,                     // 上传失败最大重试次数
	      dragdrop: true,                     // 开启可拖曳上传
	      multi_selection: false,// 设置一次只能选择一个文件
	      drop_element: 'container_4',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
	      chunk_size: '4mb',                  // 分块上传时，每块的体积
		  auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
		  resize: {
				quality: 90,  //需要指定这个参数
				compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
			},
	      filters: {
	    	    max_file_size : '20mb',
	    	    prevent_duplicates: true,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
	    	    mime_types: [
	    	        {title : "视频文件", extensions : "mp4"} // 限定mp4等后缀上传
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
	                 $("#percent").text("进度"+file.percent + "%");
//	                   $("#percent").css("width",file.percent + "%")
	          },
	          'UploadComplete': function() {
	              //队列文件处理完毕后，处理相关的事情
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
	                 var video_tag = "<div class='video_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><video controls height=120 width=240 src='"+sourceLink+"'></video>"
	                 +"<input type='hidden' value='"+sourceLink+"' class='url'><input type='hidden' class='type'/><input type='hidden' class='id'/><input type='hidden' class='recommendId'/></div>";
	                 $("#container_4_div").html(video_tag);
	          },
	          'Error': function(up, err, errTip) {
	                 //上传出错时，处理相关的事情
	        	  alert(errTip);
	          },
	          'Key': function(up, file) {
	              // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
	              // 该配置必须要在unique_names: false，save_key: false时才生效
	               var name = (new Date()).Format("yyyyMMddhhmmss")+getRandomStr();
	              var time = new Date().Format("yyyyMMdd");
	              var key = directory+"4/"+time+"/"+name+".mp4";
	              return key
	          }
	      }
	  });
})

//选择零售商
  function findGuides(){
//	var retailerId = $("#retailerId").val();
//  	var url = "tPersonController.do?guideListOfRetailer&retailer_Id="+retailerId;
  	var url = "tPersonController.do?guideListOfRetailer";
  	 $.dialog.setting.zIndex = 9999;
  	 if(typeof(windowapi) == 'undefined'){
  		 $.dialog({
  				content: "url:"+url,
  				lock : true,
  				title:"选择导购",
  				width:700,
  				height: 500,
  				cache:false,
  			    ok: function(){
  			    	iframe_doc = this.iframe.contentWindow.document;
  				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
  			    	if ($(sel_tr).length ==0 ){
  			    			alert("请选择导购");
  				    		return false;
  					 }else{
  						 if($(sel_tr).length>1){
  					    		alert("只能选择一个导购");
  					    		return false;
  					    }
  						var guideId = $(sel_tr).find("td[field='userId'] div").text();
						 var guideName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#guideId").val(guideId);
						 $("#guideName").val(guideName);
  					 }
  			    },
  			    cancelVal: '关闭',
  			    cancel: true /*为true等价于function(){}*/
  			}).zindex();
  		} else{
  			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择导购",
				width:700,
				height: 500,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
	  				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
	  			    	if ($(sel_tr).length ==0 ){
	  			    			alert("请选择导购");
	  				    		return false;
	  					 }else{
	  						 if($(sel_tr).length>1){
	  					    		alert("只能选择一个导购");
	  					    		return false;
	  					    }
	  						var guideId = $(sel_tr).find("td[field='userId'] div").text();
							 var guideName = $(sel_tr).find("td[field='realName'] div").text();
							 $("#guideId").val(guideId);
							 $("#guideName").val(guideName);
	  					 }
			    },
			    cancelVal: '关闭',
			    cancel: true 
			}).zindex();
  		}
}
  
//删除图片
  function delPic(obj){
  	   $(obj).parent().remove();
  }
  
  //因为上传和商品页面共用js，商品页面上传完后需要调用此方法，而此处不需要，因此写个空方法防止报错
  function initPicTextShow(){}