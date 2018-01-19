var flag = false;
var fileitem = "";
var fileKey = "";
var serverMsg = "";
var m = new Map();
$(function() {
	$('#file_upload').uploadify(
					{
						buttonText:'选择图片',
						auto : true,
						progressData : 'speed',
						multi : false,
						height : 25,
						overrideEvents : [ 'onDialogClose' ],
						fileTypeDesc : '文件格式:',
						fileTypeExts : '*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif',
						queueSizeLimit : '1',
						fileSizeLimit : '200KB',
						swf : 'plug-in/uploadify/uploadify.swf',
						'removeCompleted' : false,
						uploader : 'basePayTypeController.do?uploadPic&time='+new Date().getTime(),
						onUploadStart : function(file) {
							var o = {};
							var _array = $('#formobj').serializeArray();
							$.each(_array,function() {
												if (o[this.name]){
													if (!o[this.name].push) {
														o[this.name] = [ o[this.name] ];
													}
													o[this.name].push(this.value|| '');
												} else {
													o[this.name] = this.value || '';
												}
											});
							$('#file_upload').uploadify("settings", "formData",o);
						},
						onQueueComplete : function(queueData) {
							var win = frameElement.api.opener;
							//win.reloadTable();
							//win.tip(serverMsg);
							//frameElement.api.close();
							//alert("111")
						},
						onUploadSuccess : function(file,data, response) {
							var d = $.parseJSON(data);
							$("#payIcon").val(d.msg);
							$("#prePic").attr("src",d.msg);
						},
						onFallback : function() {
							tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
						},
						onSelectError : function(file,errorCode, errorMsg) {
							switch (errorCode) {
							case -100:
								$.messager.alert('错误','上传的文件数量已经超出系统限制的1个文件');
								break;
							case -110:
								$.messager.alert('错误提示','文件 大小超出系统限制的300kb)大小');
								break;
							case -120:
								$.messager.alert('错误提示','文件大小异常');
								break;
							case -130:
								$.messager.alert('错误提示','文件类型不正确!');
								break;
							}
						},
						onUploadProgress : function(file,bytesUploaded, bytesTotal,totalBytesUploaded,totalBytesTotal){},
						onInit: function (){$("#file_upload-queue").hide();}
					});
});
function upload() {
	$('#file_upload').uploadify('upload', '*');
	return flag;
}
function cancel() {
	$('#file_upload').uploadify('cancel', '*');
}
//function errorImg(obj){
//    obj.src="online/template/default/images/default.jpg";
//}