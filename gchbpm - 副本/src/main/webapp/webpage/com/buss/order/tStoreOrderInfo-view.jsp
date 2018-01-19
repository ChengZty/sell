<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>线下订单详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/goods/css/member.css" />
<style type="text/css">
.boxbox{
	line-height: 50px;
	height: 50px;
	border-bottom:solid 1px #C4D5E0;
}
.box dd {
	margin: 0;
	width:150px;
}
.view-big-img-box{
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	background: rgba(0, 0, 0, 0.5);
}
.view-big-img-box .big-img{
	position: absolute;
	left:50%;
	top:50%;
	-webkit-transform:translate(-50%,-50%);
	-moz-transform:translate(-50%,-50%);
	transform:translate(-50%,-50%);
}
.view-big-img-box .btn-close-view{
	position: absolute;
	top: 0;
	right: 0;
	width: 89px;
	height: 89px;
	background-image:url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFkAAABZCAYAAABVC4ivAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAZ9JREFUeNrs2+1twjAQgOGEBWCDdgQ2oCN0A8IGHaEjsAFmg47QbsAK3SAbuDY9pBCpH7Rn+S55X+kk8seIR/kRTtDGGBsq2wICkEEmkEEGmUAGmUAGGWQCGWSQCWSQCWSQQSaQQSaQQQaZQAYZZAIZZAIZ5HPrNPtCZ+/l/Lrl3ydXnHWaPn4WlM8Ocm4v71Ptcy4M3MVLeb1NE5TODXJeI+ffz/lOztPF64LSHXypq/0ZLSBrQpsDtoSsAW0S2Bryf6DNAltE/gu0aWCryLdAmwe2jPwbaBfA1pG/g3YDnKd18I/ULs1hcP2e5m5wvVP8ElOk1snffsfQboA9Iede02wG129pHtjC6RVGwI1cB5D1lz254+D11gW08aeLr54itJdKs32E++kxzQ20V2BX0J6B3UB7B3YBPQVg89BTATYNPSVgs9C1gZ8LbdPG0E9zRl6lORVaV16gT/I+s151rmTR81Lg7EdZLPVs4fgtHIEMMoEMMsgEMsggE8ggE8ggg0wgg0wggwwygQwyyAQyyAQyyCATyCCDTMX7EGAAHWZ0X5i9mdMAAAAASUVORK5CYII=');
	background-size: 89px 89px;
	cursor: pointer;
}

</style>

</head>
<body>
	<div class="layout" style="width: 1000px; margin: 10px auto;">
		<div class="wrap-shadow">
			<div class="wrap-all ncu-order-view">
				<h2>线下订单详情</h2>
				<dl class="box boxbox">
					<dt>订&nbsp;单&nbsp;号：</dt>
					<dd>${StoreOrderInfo.orderNo }</dd>
					<dt>成交时间：</dt>
					<dd>${fn:substring(StoreOrderInfo.payTime,0,10) }</dd> 
					<dt>成交件数：</dt>
					<dd>${StoreOrderInfo.quantityAmount}</dd>
					<dt>成交金额：</dt>
					<dd>${StoreOrderInfo.payAmount}</dd>
				</dl>
				<dl class="box boxbox">
					<dt>顾客姓名：</dt>
					<dd>${StoreOrderInfo.userName }</dd>
					<dt>顾客手机：</dt>
					<dd>${StoreOrderInfo.userPhone }</dd> 
				</dl>
				<dl class="box boxbox">
					<dt>导购姓名：</dt>
					<dd>${StoreOrderInfo.guideName }</dd>
					<dt>导购手机：</dt>
					<dd>${StoreOrderInfo.guidePhone }</dd> 
					<dt>导购所在店铺：</dt>
					<dd>${StoreOrderInfo.storeName }</dd> 
				</dl>
				<dl class="box" style="margin-left:20px;margin-top:20px;">
					<img src="${StoreOrderInfo.picUrl }"  width="150px" height="150px" />
				</dl>
				<dl class="box" style="margin-left:20px;">
					<dd>
						<a href="#" class="easyui-linkbutton" onclick="check()" >&nbsp;查看大图&nbsp;</a>&nbsp;&nbsp;
						<a href="${StoreOrderInfo.picUrl }" class="easyui-linkbutton"  download="picUrl.jpg">&nbsp;下载图片&nbsp;</a>
					</dd> 
				</dl>
			</div>
		</div>
	</div>

	<div class="view-big-img-box" style="display: none;">
		<img class="big-img" src="http://img7.guanjiaapp.net/img/20171029/15092486958881OtRVs4slj.png" alt="">
		<div class="btn-close-view"></div>
	</div>

<script type="text/javascript">
	function check(){
		$('.view-big-img-box').show();
		imgPathURL = '${StoreOrderInfo.picUrl }';
		$('.view-big-img-box .big-img').attr('src', imgPathURL);

		var $currentImg = $('.view-big-img-box .big-img');
		var tempImg = new Image();
		tempImg.src = $currentImg.attr('src');
		if(tempImg.complete){
			calcImgSize();
		}else{
			tempImg.onload = function(){
				calcImgSize();
			};
		}

		function calcImgSize(){
			//获取窗口的宽高
			var winHeight = 0;
			var winWidth = 0;
			if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
			{
				winHeight = document.documentElement.clientHeight;
				winWidth = document.documentElement.clientWidth;
			}

			var tempWidth = winWidth;
			var tempHeight = tempWidth * tempImg.height / tempImg.width;
			if(tempHeight > winHeight){
				if(tempImg.height > winHeight-50){
					$currentImg.css('width',Math.floor((winHeight-50) * tempImg.width / tempImg.height));
					$currentImg.css('height',Math.floor(winHeight-50));  //上下留白
				}else{
					$currentImg.css('width',tempImg.width);
					$currentImg.css('height',tempImg.height);
				}
			}else{
				$currentImg.css('width',Math.floor(tempWidth));
				$currentImg.css('height',Math.floor(tempHeight));
			}

			var timer;
			window.onresize = function(){
				clearTimeout(timer);
				timer = setTimeout(function() {
					calcImgSize();
				}, 100);
			}
		}
	}

	$('.view-big-img-box .btn-close-view').on('click', function(){
		$('.view-big-img-box').hide();
	});
</script>
</body>