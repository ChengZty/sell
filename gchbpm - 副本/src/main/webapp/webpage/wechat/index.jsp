<%@ page language="java"   import="java.util.*"  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<html lang="zh-cmn-Hans">
<head>
	<meta charset="UTF-8">
	<title>微信登录页面</title>
    <meta name="format-detection" content="telephone=no,email=no,adress=no">
    <meta name="description" content="观潮汇管家系统，短信制作，商品SHOW" />
    <meta name="Keywords" content="观潮汇管家系统，短信制作，商品资讯，图文制作，内容排版，大数据处理" />
    <link rel="stylesheet" href="plug-in/wechat/css/reset.css" />
</head>
<body>
    <div class="wechat_login">
        <div class="login_box">
            <div class="qrcode ">
                <img class="img" src="plug-in/wechat/img/wechat_qrcode.jpg" alt="">
                <div class="login_hint ">
                    <p class="sub_title">使用手机微信扫码登录</p>
                    <p class="sub_desc">网页版微信需要配合手机使用</p>
                </div>
                <div class="refresh_hint  hide">
                    <div class="refresh_qrcode_mask">
                        <i class="icon-refresh"></i>
                    </div>
                    <p class="refresh_tips">二维码失效，点击刷新</p>
                </div>
            </div>
            <div class="avatar hide">
                <img class="img" alt="" src="plug-in/wechat/img/avatar.png"   id="userAvatar">
                <h4 class="sub_title">扫描成功</h4>
                <p class="tips">请在手机上点击确认以登录</p>
                <a href="javascript:;" class="action"   onclick="reflushCode()">切换帐号</a>
            </div>
            <div class="association hide">
                <img class="img" alt="" src="plug-in/wechat/img/avatar.png">
                <p class="waiting_confirm ng-hide">请在手机点击确认登录</p>
                <a href="javascript:;" class="button button_primary">登录</a>
                <a href="javascript:;" class="button button_default"    onclick="reflushCode()">切换帐号</a>
            </div>
            <div class="broken_network hide">
                <div class="icon-broken-logo"></div>
                <h4 class="sub_title">网络连接已断开</h4>
                <p class="sub_desc">请检查你的网络设置</p>
            </div>
        </div>
    </div>
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
</body>
</html>
<script type="text/javascript">
	var  timer;
	var uuid;
	var tip = 1;
	var count = 0;
	$(function () {
		//获取二维码
		getQRCode();
		//定时回调
		timer =  window.setInterval("waitForLogin()",3000);
		$('.icon-refresh').on('click', function(){
	            $(this).css({
	                'animation':'circle 1s infinite ease',
	                '-webkit-animation':'circle 1s infinite ease'
	            });
	            reflushCode();
	    }); 
	})
	
	//重新获取二维码
	function reflushCode(){
		$(".qrcode").show();
		$(".avatar").hide();
		$(".broken_network").hide();
		$(".login_hint").show();
		$(".refresh_hint").hide();
		getQRCode();
		tip = 1;
	}
	//获取二维码
	function getQRCode(){
		$.ajax({
            type: "GET",
            url: "wx.do?getQRCode",
            dataType: "json",
            success: function(data){
           	 	uuid = data.uuid;
          	 	 	$(".img").attr("src",data.imgPath);
            }
        });
	}
	/********************************************验证登录状态码说明***********************************************
	200:确认登陆,请刷新页面
	201:扫码成功,请刷新页面
	400:二维码过期
	401:被微信强制退出
	402:手机端退出登陆
	403:该微信号人为禁止登陆
	404:可能被拉黑,如果多次出现这个情况，被服务器拉黑，等几个小时在登陆吧，或者换个微信
	408:等待扫码
	444:web页面退出登陆
	500:无人道，又被拉黑！等几个小时在登陆把，或者换个微信吧
	********************************************验证登录状态码说明***********************************************/
	function waitForLogin(){
         count +=1;
//          console.log(count);
         //大于100次 则强制失效二维码
         if(count  < 100)
         {
        	 $.ajax({
                 type: "GET",
                 url: "wx.do?waitForLogin",
                 data: {"uuid":uuid,"tip":tip},
                 dataType: "json",
                 success: function(data){
               	 	    var code = data.code;
//                	 	    console.log(code);
               	 	    tip = data.tip;
           				if(code == "201")
           				{
           					$(".qrcode").hide();
           					$(".avatar").show();
           					$("#userAvatar").attr("src",data.userAvatar);
           				} else if(code==  "200"){
           					window.clearInterval(timer);
           					window.location.href="wx.do?login"
           				} else if(code == "400"){
           					$(".login_hint").hide();
           					$(".refresh_hint").show();
           				} 
                 },
                error: function(XMLHttpRequest, textStatus, errorThrown) 
                {
                	$(".qrcode").hide();
                	$(".broken_network").show();
            	}
             });
         }else{
        	 $(".login_hint").hide();
			 $(".refresh_hint").show();
         }
	} 
</script>
</html>