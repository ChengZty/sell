<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分类管理</title>
<t:base type="jquery,easyui,tools"></t:base>
  <link rel="stylesheet" href="webpage/com/buss/newGoods/newGoods.css?v=1.0" type="text/css"></link>
  <script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
  <!-- 七牛上传 start -->
  	<!-- 这个js由plupload.dev.customed.js和moxie.customed.js合并而来，moxie.customed.js在前，plupload.dev.customed.js在后-->
  	<script type="text/javascript" src="plug-in/plupload/js/plupload.full.customed.min.js?v=1.0.1"></script>
	  <script type="text/javascript" src="plug-in/qiniu/qiniu.min.js"></script>
	  <script src="plug-in/cropper/js/cropper-dom-select-file.js?v=1.0.1"></script>
	  <!-- 七牛上传 end -->
   <script type="text/javascript">
//  	var domain = '${domain}';//七牛domain
//  	var directory = '${directory}';//目录
 </script>
<script type="text/javascript">
	/* $(function() {
		$('#categoryTree').combotree({
			url : 'categoryController.do?combotree',
			panelHeight : 200,
			width : 157,
			onClick : function(node) {
				$("#pId").val(node.id);
			}
		});
		if ($('#id').val()) {
			$('#categoryTree').combotree('disable');
		}
	}); */
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" layout="div" dialog="true" tiptype="4" action="categoryController.do?save">
		<input name="id" id="id" type="hidden" value="${categoryPage.id}">
		<input name="status" id="status" type="hidden" value="A">
		<fieldset class="step">
			<div class="form">
				<label class="Validform_label"> 类型名称: </label> 
					<input name="name" type="text" datatype="*" maxlength="10" id="name" value="${categoryPage.name}">
				 <span class="Validform_checktip">10个字以内</span>
			</div>
			<div class="form">
				<label class="Validform_label"> <t:mutiLang
						langKey="图标" />:</label> <select name="icon.id">
					<c:forEach items="${iconlist}" var="icon">
						<option value="${icon.id}"
							<c:if test="${icon.id==categoryPage.icon.id}">selected="selected"</c:if>>
							<t:mutiLang langKey="${icon.iconName}" />
						</option>
					</c:forEach>
				</select>
			</div>
			<div class="form">
				<label class="Validform_label"> 所属上级:</label> 
				<input id="pId" name="parent.id" type="hidden" value="${categoryPage.parent.id}">
				<input id="categoryTree" type="text" value="${categoryPage.parent.name}" readonly="readonly">
				<span class="Validform_checktip"></span>
			</div>
			<%-- <div class="form">
				<label class="Validform_label"> 排序:</label> <input type="text"
					id="sort" name="sort" datatype="n" errormsg="必须数字"
					checktip="序列号必须为数字" value="${categoryPage.sort}"> <span
					class="Validform_checktip"></span>
			</div>
			<div class="form">
				<table style="width: 100%">
					<tr>
						<td>分类图片</td>
						<td>品牌分类图片</td>
					</tr>
					<tr>
						<td>
							<div id="container_1" >
	                            <a  id="pickfiles_1" href="#" style="width: 200px">
	                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
	                            </a>
	                            <span class="Validform_checktip">红框范围内支持拖拽上传，图片不超过200kb(限一张)</span>
	                            <div id="container_div_1" style="min-height: 150px;border: 1px dashed red;">
	                            	<c:if test="${not empty categoryPage.imgUrl  }">
		                            	<div class='pic_div'>
											<a class='delete' onclick='delPic(this)' href='#'>×</a>
											<img src='${categoryPage.imgUrl }'   width="200px" height="150px"/>
										</div>
	                            	</c:if>
	                            </div>
	                      	</div>
							<input type="hidden" id="imgUrl" name="imgUrl" value="${categoryPage.imgUrl}">
						</td>
						<td>
							<div id="container_2" >
	                            <a  id="pickfiles_2" href="#" style="width: 200px">
	                                <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
	                            </a>
	                            <span class="Validform_checktip">红框范围内支持拖拽上传，图片不超过200kb(限一张)</span>
	                            <div id="container_div_2" style="min-height: 150px;border: 1px dashed red;">
	                            	<c:if test="${not empty categoryPage.imgBrandUrl  }">
		                            	<div class='pic_div'>
											<a class='delete' onclick='delPic(this)' href='#'>×</a>
											<img src='${categoryPage.imgBrandUrl }'   width="200px" height="150px"/>
										</div>
	                            	</c:if>
	                            </div>
                            </div>
							<input type="hidden" id="imgBrandUrl" name="imgBrandUrl" value="${categoryPage.imgBrandUrl}">
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<div id="progress_bar_muti" style="position: absolute;right:0px;bottom: 0px;display: none"></div>
							<span class="Validform_checktip">二级分类图片尺寸516*206，三级分类图片尺寸150*150</span>
							</br>
							<span class="Validform_checktip">品牌分类图片尺寸516*206(二级分类的时候需要录入)</span>
						</td>
					</tr>
				</table>
			</div> --%>
		</fieldset>
	</t:formvalid>
</body>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript">
	var flag = false;
	var fileitem = "";
	var fileKey = "";
	var serverMsg = "";
	var m = new Map();
	/* $(function() {
		//图片
	    var P = new QiniuJsSDK();
	    var uploaderPic = P.uploader({
	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
	        browse_button: 'pickfiles_1',         // 上传选择的点选按钮，必需
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
	        container: 'container_1',             // 上传区域DOM ID，默认是browser_button的父元素
	        max_file_size: '200kb',             // 最大文件体积限制
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
	            max_file_size : '200kb',
	            prevent_duplicates: true,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
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
	                   var div_imgs = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='150' width='200'></img></div>"
		   				$("#container_div_1").html(div_imgs);
		   				$("#imgUrl").val(sourceLink);
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
	                var key = "category/"+time+"/"+name;
	                return key
	            }
	        }
	    });
	    
	  //图片
	    var P2 = new QiniuJsSDK();
	    var uploaderPic2 = P2.uploader({
	        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
	        browse_button: 'pickfiles_2',         // 上传选择的点选按钮，必需
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
	        container: 'container_2',             // 上传区域DOM ID，默认是browser_button的父元素
	        max_file_size: '200kb',             // 最大文件体积限制
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
	            max_file_size : '200kb',
	            prevent_duplicates: true,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
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
	                   var div_imgs = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='150' width='200'></img></div>"
		   				$("#container_div_2").html(div_imgs);
		   				$("#imgBrandUrl").val(sourceLink);
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
	                var key = "category/"+time+"/"+name;
	                return key
	            }
	        }
	    });

	});


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
	}  */

</script>
