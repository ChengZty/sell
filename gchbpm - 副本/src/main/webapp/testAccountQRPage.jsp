<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 声明文档使用的字符编码 -->
  <meta charset='utf-8'>
 <!-- 为移动设备添加 viewport -->
 <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
 <style type="text/css">
 #form_div{
 	    padding: 5rem 1rem;
 }
 
  #form_div div{
 	    margin: 1rem;
 }
 #container{
 	margin-top: 5rem;
 	text-align: center;
 	display: none;
 }
 #img-buffer{
 	display: none; 
 }
 #saveBtn{
 	margin-top: 2rem; 
 }
 </style>
 
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- <script type="text/javascript" src="plug-in/jquery-plugs/qrcode/jquery.qrcode.min.js"></script> -->
<script type="text/javascript" src="plug-in/jquery-plugs/qrcode/jquery-qrcode-0.14.0.js"></script>
<title>导购测试帐号二维码获取页面</title>
<script type="text/javascript">
	function getQRCode(){
		var token = $("#token").val().trim();
		if(token==""){
			alert("请输入口令");
			return ;
		}
		 var url = "tPersonController.do?getQRCode&token="+token;
	 	 $.ajax({
	 			async : false,
	 			cache : false,
	 			type : 'POST',
	 			url : url,// 请求的action路径
	 			error : function() {// 请求失败处理函数
	 			},
	 			success : function(data) {
	 				var d = $.parseJSON(data);
// 	 				console.log(d);
	 				if (d.success) {
	 					$("#form_div").hide();
	 					$("#container").show();
	 					createQrcode(d.obj);
	 					console.log(d.obj);
// 	 					$('#qrcode_div').qrcode({width: 128,height: 128,text: d.obj});
	 				}else{
	 					$("#qrcode_div").hide();
	 					alert(d.msg);
	 				}
	 			}
	 		});
	}

//生成二维码
function createQrcode(url){
	var options = {
		    // render method: 'canvas', 'image' or 'div'
		    render: 'image',
		    // version range somewhere in 1 .. 40
		    minVersion: 4,
		    maxVersion: 40,
		    // error correction level: 'L', 'M', 'Q' or 'H'
		    ecLevel: 'L',
		    // offset in pixel if drawn onto existing canvas
		    left: 0,
		    top: 0,
		    // size in pixel
		    size: 200,
		    // code color or image element
		    fill: '#000',
		    // background color or image element, null for transparent background
		    background: "#FFF",
		    // content
		    text: url,
		    // corner radius relative to module width: 0.0 .. 0.5
		    radius: 0,
		    // quiet zone in modules
		    quiet: 2,
		    // modes
		    // 0: normal
		    // 1: label strip
		    // 2: label box
		    // 3: image strip
		    // 4: image box
		    mode: 4,
		    mSize: 0.1,
		    mPosX: 0.5,
		    mPosY: 0.5,
		    label: 'no label',
		    fontname: 'sans',
		    fontcolor: '#000',
		    image: $('#img-buffer')[0]
    };
	$('#qrcode_div').qrcode(options);
	var src = $('#qrcode_div img').attr("src").replace("image/png","image/jpeg");
	$('#qrcode_div img').attr("src",src);
}

function saveImg(){
	var imgUrl = $('#qrcode_div img').attr("src");
// 	var img = canvas.toDataURL("image/png");
	location = imgUrl;
}
</script>
</head>
<body>
<div id="form_div">
	<div>输入口令后获取导购测试帐号二维码</div>
	<div><input id="token" value="" placeholder="请输入口令"/></div>
	<div><input type="button" onclick="getQRCode()" value="获取二维码"/></div>
</div>
<div id="container">
	<div id="qrcode_div" ></div>
</div>
<img id="img-buffer" src="data:image/png;base64,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">
</body>
</html>
