<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>主页元素</title>
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
		var domain = '${domain}';//七牛domain
		var directory = '${directory}';//目录
	</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tGuideMainElementController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tGuideMainElement.id }">
		<input id="createName" name="createName" type="hidden" value="${ tGuideMainElement.createName }">
		<input id="createBy" name="createBy" type="hidden" value="${ tGuideMainElement.createBy }">
		<input id="createDate" name="createDate" type="hidden" value="${ tGuideMainElement.createDate }">
		<input id="updateName" name="updateName" type="hidden" value="${ tGuideMainElement.updateName }">
		<input id="updateBy" name="updateBy" type="hidden" value="${ tGuideMainElement.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden" value="${ tGuideMainElement.updateDate }">
		<input id="status" name="status" type="hidden" class="inputxt" value="${tGuideMainElement.status}">
		<input id="retailerId" name="retailerId" type="hidden" class="inputxt" value="${tGuideMainElement.retailerId}">
		<input id="groupCode" name="groupCode" type="hidden" class="inputxt" value="${tGuideMainElement.groupCode}">
		<input id="elementCode" name="elementCode" type="hidden" class="inputxt" value="${tGuideMainElement.elementCode}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						元素标题:
					</label>
				</td>
				<td class="value">
		     	 <input id="elementTitle" name="elementTitle" type="text" style="width: 150px"   datatype="*" value='${tGuideMainElement.elementTitle}' readonly="readonly">
				</td>
			</tr>
			
			<tr>
				<td align="right"  >
					<label class="Validform_label">
						封面图片:
					</label>
				</td>
				<td class="value" >
			     	<div id="container_2" >
                          <a  id="pickfiles_2" href="#" style="width: 200px">
                              <div class="uploadify-button" style="height: 25px;line-height: 25px; width: 120px;display: inline-block;">选择图片</div>
                          </a>
							<c:choose>
								<c:when test="${tGuideMainElement.groupCode == 'SHANGPIN'}">  
                          			<span class="Validform_checktip" style="color:red;">红框范围内支持拖拽上传，规格比例为326*180(限一张)</span>
								</c:when>
								<c:when test="${tGuideMainElement.groupCode == 'HUATI'}">  
                          			<span class="Validform_checktip" style="color:red;">红框范围内支持拖拽上传，图片大小规格为678*204(限一张)</span>
								</c:when>
							</c:choose>
                          <div id="container_div_2" style="min-height: 100px;border: 1px dashed red;">
                          	<div class='pic_div'>
							<a class='delete' onclick='delPic(this)' href='#'>×</a>
							<img src='${tGuideMainElement.pic }'  height='150' />
						</div>
                          </div>
                     	</div>
					<input id="pic" name="pic" type="hidden" value="${tGuideMainElement.pic}" datatype="*" >
				    <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">封面图片</label>
				</td>
			</tr>	
		</table>
	</t:formvalid>
 </body>
 
<script type="text/javascript">
$(function(){
	var groupCode = '${tGuideMainElement.groupCode}';
  //品牌图片
    // var P2 = new QiniuJsSDK();
    // var uploaderPic2 = P2.uploader({
    //     runtimes: 'html5,flash,html4',      // 上传模式，依次退化
    //     browse_button: 'pickfiles_2',         // 上传选择的点选按钮，必需
    //      uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
    //     get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
    //     domain: domain,     // bucket域名，下载资源时用到，必需
    //     container: 'container_2',             // 上传区域DOM ID，默认是browser_button的父元素
    //     max_file_size: '200kb',             // 最大文件体积限制
    //     flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
    //     max_retries: 3,                     // 上传失败最大重试次数
    //     dragdrop: true,                     // 开启可拖曳上传
    //     drop_element: 'container_2',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
    //     chunk_size: '4mb',                  // 分块上传时，每块的体积
    //     auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
    //     filters : {
    //         max_file_size : '200kb',
    //         prevent_duplicates: true,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
    //         // Specify what files to browse for
    //         mime_types: [
    //             {title : "Image files", extensions : "jpg,png,jpeg,bmp"} // 限定jpg,jpeg,png等后缀上传
    //         ]
    //     },
    //     init: {
    //         'FilesAdded': function(up, files) {
    //             plupload.each(files, function(file) {
    //                 // 文件添加进队列后，处理相关的事情
    //             });
    //         },
    //         'BeforeUpload': function(up, file) {
    //                // 每个文件上传前，处理相关的事情
    //         },
    //         'UploadProgress': function(up, file) {
    //                // 每个文件上传时，处理相关的事情
    //         },
    //         'FileUploaded': function(up, file, info) {
    //                // 每个文件上传成功后，处理相关的事情
    //                // 其中info是文件上传成功后，服务端返回的json，形式如：
    //                // {
    //                //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
    //                //    "key": "gogopher.jpg"
    //                //  }
    //                // 查看简单反馈
    //                var domain = up.getOption('domain');
    //                var res = $.parseJSON(info);
    //                var sourceLink = domain + res.key; //获取上传成功后的文件的Url
    //                var div_imgs = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='150' width='252'></img></div>"
	//    				$("#container_div_2").html(div_imgs);
	//    				$("#pic").val(sourceLink);
    //         },
    //         'Error': function(up, err, errTip) {
    //                //上传出错时，处理相关的事情
    //         	alert(errTip);
    //         },
    //         'UploadComplete': function() {
    //                //队列文件处理完毕后，处理相关的事情
    //         },
    //         'Key': function(up, file) {
    //             // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
    //             // 该配置必须要在unique_names: false，save_key: false时才生效
	//                 var name = (new Date()).Format("MMddhhmmss")+getRandomStr();
	//                 var n = file.name.lastIndexOf(".");
	//             	name += file.name.substring(n);
    //             var time = new Date().Format("yyyyMMdd");
    //             var key = directory+"newsType/"+time+"/"+name;
    //             return key
    //         }
    //     }
	// });
	
	var cropperWidth = 100;
	var cropperHeight = 100;
	if(groupCode == 'SHANGPIN'){
		cropperWidth = 330;
		cropperHeight = 200;
	}else if(groupCode == 'HUATI'){
		cropperWidth = 691;
		cropperHeight = 255;
	}
	//七牛上传
    var paramsJson = {
        cropperWidth: cropperWidth, //裁剪宽度
        cropperHeight: cropperHeight, //裁剪高度
        moduleName: 'newsType',  //模块名称
        dealSourceLinkFn: function(sourceLink){  //处理上传图片路径
            var div_imgs = "<div class='pic_div'><a class='delete' onclick='delPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='150' width='252'></img></div>"
			$("#container_div_2").html(div_imgs);
			$("#pic").val(sourceLink);
        }
    }
    new BtnSelectFileVo('#pickfiles_2', paramsJson);  //按钮点击选择
    new DragDropSelectFileVo('#container_div_2', paramsJson);  //拖曳选择
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
</script>