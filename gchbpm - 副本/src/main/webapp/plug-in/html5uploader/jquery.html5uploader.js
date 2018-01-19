/*
html5uploader V1.0
author:吕大豹
date:2013.2.21
*/
(function($){
$.fn.html5uploader = function(opts){
	
	var defaults = {
		fileTypeExts:'',//允许上传的文件类型，填写后缀名
		url:'',//文件提交的地址
		auto:false,//自动上传
		multi:true,//默认允许选择多个文件
		buttonText:'选择文件',//上传按钮上的文字
		removeTimeout: 1000,//上传完成后进度条的消失时间
		itemTemplate:'<li id="${fileID}file"><div class="progress"><div class="progressbar"></div></div><div><span class="filename">${fileName}</span><span class="progressnum">0/${fileSize}</span><a class="uploadbtn">上传</a><a class="delfilebtn">删除</a></div></li>',//上传队列显示的模板,最外层标签使用<li>
		onUploadStart:function(){},//上传开始时的动作
		onUploadSuccess:function(){},//上传成功的动作
		onUploadComplete:function(){},//上传完成的动作
		onUploadError:function(){}, //上传失败的动作
		onInit:function(){},//初始化时的动作
	}
		
	var option = $.extend(defaults,opts);
	
	//将文件的单位由bytes转换为KB或MB
	var formatFileSize = function(size){
		if (size> 1024 * 1024){
			size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
		}
		else{
			size = (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
		}
		return size;
	}
	//根据文件序号获取文件
	var getFile = function(index,files){
		for(var i=0;i<files.length;i++){	   
		  if(files[i].index == index){
			  return files[i];
			}
		}
		return false;
	}
	//将文件类型格式化为数组
	var formatFileType = function(str){
		if(str){
			return str.split(",");	
		}
		return false;
	}
	
	this.each(function(){
		var _this = $(this);
		//先添加上file按钮和上传列表
		var inputstr = '<input class="uploadfile" style="display: none;" type="file" name="fileselect[]"';
		if(option.multi){
			inputstr += 'multiple';
		}
		inputstr += '/>';
		inputstr += '<a href="javascript:void(0)" class="uploadfilebtn">';
		inputstr += option.buttonText;
		inputstr += '</a>';
		var fileInputButton = $(inputstr);
		var uploadFileList = $('<ul class="filelist"></ul>');
		_this.append(fileInputButton,uploadFileList);
		//创建文件对象
		var ZXXFILE = {
			  fileInput: fileInputButton.get(0),				//html file控件
			  upButton: null,					//提交按钮
			  url: option.url,						//ajax地址
			  fileFilter: [],					//过滤后的文件数组
			  filter: function(files) {		//选择文件组的过滤方法
				  var arr = [];
				  var typeArray = formatFileType(option.fileTypeExts);
				  if(!typeArray){
					  for(var i in files){
							  if(files[i].constructor==File){
								arr.push(files[i]);
							  }
						  }
					  }
				  else{
					  for(var i in files){
						  if(files[i].constructor==File){
							var fileExt = "." + files[i].name.replace(/.+\./, "");
							if($.inArray(fileExt,typeArray)>=0){
								arr.push(files[i]);	
								}
							else{
								alert('文件类型不允许！');
								fileInputButton.val('');
								}  	
							} 
						}	
					  }
				  return arr;  	
			  },
			  //文件选择后
			  onSelect: option.onSelect||function(files){
				
				_this.find('.filelist li').remove(); //去除所有的DOM

				 for(var i=0;i<files.length;i++){
					
					var file = files[i];
					
					var html = option.itemTemplate;
					//处理模板中使用的变量
					html = html.replace(/\${fileID}/g,file.index).replace(/\${fileName}/g,file.name).replace(/\${fileSize}/g,formatFileSize(file.size));
					uploadFileList.append(html);
					//判断是否是自动上传
					 if(option.auto){
						 ZXXFILE.funUploadFile(file);
						 }
				 }
				 
				 //如果配置非自动上传，绑定上传事件
				 if(!option.auto){
					_this.find('.uploadbtn').off().on('click',function(){
						var index = parseInt($(this).parents('li').attr('id'));
						ZXXFILE.funUploadFile(getFile(index,files));
						});
				 }
				 //为删除文件按钮绑定删除文件事件
				 _this.find('.delfilebtn').off().on('click',function(){
					 var index = parseInt($(this).parents('li').attr('id'));
					 ZXXFILE.funDeleteFile(index);
					 });
				 
				},		
			  //文件删除后
			  onDelete: function(index) {
				//   _this.find('#'+index+'file').fadeOut();
				  _this.find('#'+index+'file').remove();
				  },		
			  onProgress: function(file, loaded, total) {
				var eleProgress = _this.find('#'+file.index+'file .progress'), percent = (loaded / total * 100).toFixed(2) + '%';
				eleProgress.find('.progressbar').css('width',percent);
				if(total-loaded<500000){loaded = total;}//解决四舍五入误差
				eleProgress.parents('li').find('.progressnum').html(formatFileSize(loaded)+'/'+formatFileSize(total));
			  },		//文件上传进度
			  onUploadSuccess: option.onUploadSuccess,		//文件上传成功时
			  onUploadError: option.onUploadError,		//文件上传失败时,
			  onUploadComplete: option.onUploadComplete,		//文件全部上传完毕时
			  
			  /* 开发参数和内置方法分界线 */
			  
			  //获取选择文件，file控件或拖放
			  funGetFiles: function(e) {
						  
				  // 获取文件列表对象
				  var files = e.target.files || e.dataTransfer.files;
				  //继续添加文件
				  files = this.filter(files)
				  this.fileFilter.push(files);
				  this.funDealFiles(files);
				  return this;
			  },
			  
			  //选中文件的处理与回调
			  funDealFiles: function(files) {
				  var fileCount = _this.find('.filelist li').length;//队列中已经有的文件个数
				  for (var i = 0; i<this.fileFilter.length; i++) {
					  for(var j=0;j<this.fileFilter[i].length;j++){	
						 var file = this.fileFilter[i][j];	  
						 //增加唯一索引值
					  	 file.index = ++fileCount;
					  }
				  }
				  //执行选择回调
				  this.onSelect(files);
				  
				  return this;
			  },
			  
			  //删除对应的文件
			  funDeleteFile: function(index) {

				  for (var i = 0; i<this.fileFilter.length; i++) {
					  for(var j=0; j<this.fileFilter[i].length; j++){
						  var file = this.fileFilter[i][j];
						  if (file.index == index) {
							  this.fileFilter[i].splice(j,1);
							  this.onDelete(index);	
						  }
					  }
				  }
				  return this;
			  },
			  
			  //文件上传
			  funUploadFile: function(file) {
				  var self = this;	
				  (function(file) {
					  var xhr = new XMLHttpRequest();
					  if (xhr.upload) {
						  // 上传中
						  xhr.upload.addEventListener("progress", function(e) {
							  self.onProgress(file, e.loaded, e.total);
						  }, false);
			  
						  // 文件上传成功或是失败
						  xhr.onreadystatechange = function(e) {
							  if (xhr.readyState == 4) {
								  if (xhr.status == 200) {
									  self.onUploadSuccess(file, xhr.responseText);
									  setTimeout(function(){ZXXFILE.onDelete(file.index);},option.removeTimeout);
									 
									  self.onUploadComplete(xhr.responseText);	
									  
								  } else {
									  self.onUploadError(file, xhr.responseText);		
								  }
							  }
						  };
			  
			  			  option.onUploadStart();	
						  // 开始上传
						  xhr.open("POST", self.url, true);
							//   xhr.setRequestHeader("X_FILENAME", file.name);  //不注释，会出现中文字符问题
							var formData = new FormData(); // FormData 对象
							formData.append("file", file); // 文件对象
							xhr.send(formData);
					  }	
				  })(file);	
				  
					  
			  },
			  
			  init: function() {
				  var self = this;
				  
				  //文件选择控件选择
				  if (this.fileInput) {
					  this.fileInput.addEventListener("change", function(e) { self.funGetFiles(e); }, false);	
				  }
				  
				  //点击上传按钮时触发file的click事件
				  _this.find('.uploadfilebtn').on('click',function(){
					  _this.find('.uploadfile').trigger('click');
					  });
				  
				  option.onInit();
			  }
		  };
		  //初始化文件对象
		  ZXXFILE.init();
		
		
		}); 
	}	
	
})(jQuery);

/** H5UploadWinVo 窗口对象 start */
var H5UploadWinVo = (function(){
	function H5UploadWinVo(box){
		if(this instanceof H5UploadWinVo){
			this.$box= $(box);

			this.initView();
		}else{
			return new H5UploadWinVo(box);
		}
	}

	H5UploadWinVo.prototype = {
		constructor: H5UploadWinVo,
		initView: function(){
			this.bindClick();
			this.$box.show();
		},
		bindClick: function(){
			var _this = this;

			//点击关闭
			_this.$box.on('click', '.btn-close', function(){
				_this.destroywin();
			});
			
			//点击取消上传
			_this.$box.on('click', '.btn-cancle-upload', function(){
				_this.destroywin();
			});

			//点击开始上传
			_this.$box.on('click', '.btn-start-upload', function(){
				_this.$box.find('.uploadbtn').click();  //触发上传操作
			});
		},
		destroywin: function(){
			var _this = this;

			_this.$box.hide();
			_this.$box.remove();
		}
	};

	return H5UploadWinVo;
})();
/** H5UploadWinVo 窗口对象 end */

/**
 * 创建上传页面窗口  HTML5
 * 
 * @param title  窗口名称
 * @param url    文件上传路径
 * @param gname  需要刷新的列表的名称
 * @param callbackFn  回调处理方法
 */
function openuploadwinH5(title, url,gname, callbackFn) {
	if(gname){  //如果存在，则赋值
		gridname = gname;
	}

	var uploadWinStr = '';

	uploadWinStr += '\
		<div class="h5-upload-window">\
			<div class="h5-upload-box">\
			<div class="upload-title-box">\
				<div class="upload-title">'+title+'</div>\
				<div class="btn-close"></div>\
			</div>\
			<div class="btn-menu-box">\
				<div class="btn-item btn-cancle-upload">取消上传</div>\
				<div class="btn-item btn-start-upload active">开始上传</div>\
			</div>\
			<div class="h5-uploader"></div>\
			</div>\
		</div>\
	';

	$('.h5-upload-window').remove();  //先移除所有的DOM
	$('body').append(uploadWinStr);  //再添加dom

	var h5UploadWinVo = new H5UploadWinVo('.h5-upload-window');

	//测试
	$('.h5-upload-window .h5-uploader').html5uploader({
		fileTypeExts: '.xls,.xlsx',
		removeTimeout: 1000 * 60 * 60 * 24,
		url:url,
		multi:false,
		onUploadStart:function(){  //开始上传
		},
		onInit:function(){  //初始化
		},
		onUploadComplete:function(data){  //上传完成
			if(typeof data === 'string'){
				data = JSON.parse(data);
			}
//			if(data.success){
//				reloadTable();  //刷新列表
//			}else{
//			}
			if(callbackFn && typeof callbackFn == 'function'){  //如果存在，且是方法
				callbackFn(data);
			}
			h5UploadWinVo.destroywin();  //关闭窗口
			reloadTable();  //刷新列表
			tip(data.msg);//提示操作结果
		},
		onUploadError: function(file, data){  //上传失败
		}
	});
}


