<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
	<!--  <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
		<title>g+后台管理系统</title>
		<link rel="stylesheet" href="plug-in/ace/assets/css/bootstrap.min.css">
		<link rel="stylesheet" href="plug-in/ace/assets/css/ace.min.css" />
		<script src="plug-in/jquery/jquery-1.8.0.min.js"></script>
		<script src="plug-in/ace/assets/js/bootstrap.min.js"></script>
		<style>
			.report-form .report-title{
				padding: 5px 0;
				background-color: #6fb3e0;
				font-size: 24px;
				color: #ffffff;
			}
			.infobox{
				width: 188px;
				height: 90px;
			}
			.widget-body{
				min-height: 203px;
			}
			.widget-box{
				position: relative;
			}
			.loading{
				display: block;
				position: absolute;
				z-index: 100;
				top: 0;
				right: 0;
				bottom: 0;
				left: 0;
				background: rgba(255, 255, 255, 0.5);
			}
			.loading .loading-img{
				position: absolute;
				left:50%;
				top:50%;
				-webkit-transform:translate(-50%,-50%);
				-moz-transform:translate(-50%,-50%);
				transform:translate(-50%,-50%);
				width: 42px;
				height: 40px;
				background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACsAAAApCAYAAACsldDLAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAqhJREFUeNrsWTFu2zAUVQQPGQpIPUGUE8Rjl8Dq1s0emrn21i3qCaKeoM4Jaq9xB2fLVhe9gLsHiHIDG8jQzX0/eCwIQlQk0ZE0+AMfNElRfPz8/32KPtrtdp6L/Fgs+ijuP15cPBntb1B8gJ5Df0PvzGeqylFdsAATo1hCA+gWmgDMTOtfozjThvyBxnhmUxes77BQBdRj+R0AQwIdGUA91kculu05jA1y2sQlVtDIMibSLJ8QvFh6CouvnMHipREnyfDCTOva5gBW/baJV3yn7MpQax+ibaK7UWU3wAvGKB6gP6Xk9ipJCFjJRC0GpfjrF+N112I9BuQwZ7rU1WenRj1RP2gFsfh76FvTKqjL2FP2n6KuxoaWuU5c3SCw+RwBbQq23KOlM6PZxgaPrpa9Nuozz1HoIrd13OBFnqXfqgBzBpvDBhmNsKbbjbijt+TuzDkp7FsAXtxpYDRLAEcqkfSMAbIVMVeZumSbGil7YImZsQr0nrEtV6wOGAhpQ4YNy/TpARYbD8UNesGmTJ9v5HqvoP5qQob4ldO11RnIN0h+Av3KbDRtOMaEBeZaVhQ26Otx0xk2MAIuJHihzDUALzsJlsywMrLn81m4U2Bp0cxy/Jz72oNTrqpN6VuAinzyCVSC61LM3wHA9oMMwB1rnxsBV9eWFPHto49I+8sEsCVlzdpCSr6dW7qT/wEmzt3UWaBEoKU86AdkAjmnLDvJs6/xKX4AWxqs+C2Pit0Gy+whae4beffgBk4ibKB0cXMTQlO9rWnF/CNolNd3oK4D2DJg5ZKjK+xQ6LO87nxgtdVDTpXro752C7jPrwL5vopqUVcLNBVVed6Jumh1+VNjXGC90Lgxb40Nnm8C6dt5Ij6+3pfr9BzHv4MeFxzaP79wj1VJ/gkwAGfsDG/4FJ2vAAAAAElFTkSuQmCC") no-repeat center center;
				background-size: 42px 40px;
				animation: spin 2000ms infinite linear;
			}
			@keyframes spin {
				0%   { transform: rotate(0deg); }
				100% { transform: rotate(360deg); }
			}
		</style>
	</head>
	<body>
		<div style="margin-right: 14px;">
			<div class="row">
				<div class="col-xs-12">
					<div class="row">
						<div class="space-6"></div>
						<div class="col-sm-5 col-sm-offset-1">
							<div class="widget-box guide-num-box">
								<div class="loading">
									<div class="loading-img"></div>
								</div>
								<div class="widget-header widget-header-flat widget-header-small">
									<h5 class="widget-title">
										<i class="ace-icon fa fa-signal"></i>
										导购报表
									</h5>
								</div>
	
								<div class="widget-body">
									<div class="widget-main">
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number total-guide-num">0</span>
												<div class="infobox-content">总导购数</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number active-guide-num">0</span>
												<div class="infobox-content">现有激活的导购数</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number deactivated-guide-num">0</span>
												<div class="infobox-content">退出停用的导购数</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-5">
							<div class="widget-box order-num-box">
								<div class="loading">
									<div class="loading-img"></div>
								</div>
								<div class="widget-header widget-header-flat widget-header-small">
									<h5 class="widget-title">
										<i class="ace-icon fa fa-signal"></i>
										订单
									</h5>
								</div>
	
								<div class="widget-body">
									<div class="widget-main">
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number trading-suc-guide-num">0</span>
												<div class="infobox-content">今日成交导购数</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number online-order-num">0</span>
												<div class="infobox-content">今日线上订单数</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number offline-order-num">0</span>
												<div class="infobox-content">今日线下订单数</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="row">
						<div class="space-6"></div>
						<div class="col-sm-5 col-sm-offset-1">
							<div class="widget-box customer-num-box">
								<div class="loading">
									<div class="loading-img"></div>
								</div>
								<div class="widget-header widget-header-flat widget-header-small">
									<h5 class="widget-title">
										<i class="ace-icon fa fa-signal"></i>
										顾客
									</h5>
								</div>
	
								<div class="widget-body">
									<div class="widget-main">
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number total-num">0</span>
												<div class="infobox-content">顾客总数量</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number trading-num">0</span>
												<div class="infobox-content">交易顾客数量</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number non-trading-num">0</span>
												<div class="infobox-content">非交易顾客数量</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-5">
							<div class="widget-box goods-num-box">
								<div class="loading">
									<div class="loading-img"></div>
								</div>
								<div class="widget-header widget-header-flat widget-header-small">
									<h5 class="widget-title">
										<i class="ace-icon fa fa-signal"></i>
										商品
									</h5>
								</div>
	
								<div class="widget-body">
									<div class="widget-main">
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number total-num">0</span>
												<div class="infobox-content">总商品数量</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number draft-num">0</span>
												<div class="infobox-content">草稿箱商品数量</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number pending-shelf-num">0</span>
												<div class="infobox-content">待上架商品数量</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number on-sale-num">0</span>
												<div class="infobox-content">销售中商品数量</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number off-shelf-num">0</span>
												<div class="infobox-content">已下架商品数量</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<div class="row">
						<div class="space-6"></div>
						<div class="col-sm-5 col-sm-offset-1">
							<div class="widget-box topic-module-num">
								<div class="loading">
									<div class="loading-img"></div>
								</div>
								<div class="widget-header widget-header-flat widget-header-small">
									<h5 class="widget-title">
										<i class="ace-icon fa fa-signal"></i>
										话题
									</h5>
								</div>
	
								<div class="widget-body">
									<div class="widget-main">
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number online-topic-num">0</span>
												<div class="infobox-content">线上话题数</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number click-topic-num">0</span>
												<div class="infobox-content">有点击话题数</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-5">
							<div class="widget-box words-num-box">
								<div class="loading">
									<div class="loading-img"></div>
								</div>
								<div class="widget-header widget-header-flat widget-header-small">
									<h5 class="widget-title">
										<i class="ace-icon fa fa-signal"></i>
										话术
									</h5>
								</div>
	
								<div class="widget-body">
									<div class="widget-main">
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number words-total-num">0</span>
												<div class="infobox-content">话术总量</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number sale-words-num">0</span>
												<div class="infobox-content">销售话术总量</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number goods-words-num">0</span>
												<div class="infobox-content">商品话术总量</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="display: none;">
				<div class="col-xs-12">
					<div class="row">
						<div class="space-6"></div>
						<div class="col-sm-5 col-sm-offset-1">
							<div class="widget-box">
								<div class="loading">
									<div class="loading-img"></div>
								</div>
								<div class="widget-header widget-header-flat widget-header-small">
									<h5 class="widget-title">
										<i class="ace-icon fa fa-signal"></i>
										任务
									</h5>
								</div>
	
								<div class="widget-body">
									<div class="widget-main">
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number">0</span>
												<div class="infobox-content">任务数及导购完成比例</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number">0</span>
												<div class="infobox-content">任务激励及获奖比例</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number">0</span>
												<div class="infobox-content">任务处罚及处罚比例</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-5">
							<div class="widget-box">
								<div class="loading">
									<div class="loading-img"></div>
								</div>
								<div class="widget-header widget-header-flat widget-header-small">
									<h5 class="widget-title">
										<i class="ace-icon fa fa-signal"></i>
										活动
									</h5>
								</div>
	
								<div class="widget-body">
									<div class="widget-main">
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number">0</span>
												<div class="infobox-content">导购销售激励及获奖比例</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number">0</span>
												<div class="infobox-content">导购销售处罚及处罚比例</div>
											</div>
										</div>
										<div class="infobox infobox-green">
											<div class="infobox-data">
												<span class="infobox-data-number">0</span>
												<div class="infobox-content">有无顾客促销活动</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script>
		//获取商品状态数量
		function getGoodsStatusNum(){
			$.ajax({
				cache: false,
				type: 'GET',
				url: 'mainPageCountController.do?getGoodsStatusNum',
				dataType: 'json',
				success: function(data){
					if(data.success){
						var numObj = data.obj;
						$('.goods-num-box .draft-num').text(numObj.goods0Num);
						$('.goods-num-box .pending-shelf-num').text(numObj.goods3Num);
						$('.goods-num-box .on-sale-num').text(numObj.goods4Num);
						$('.goods-num-box .off-shelf-num').text(numObj.goods5Num);
						$('.goods-num-box .total-num').text(numObj.allGoodsNum);
					}
					$('.goods-num-box .loading').fadeOut();
				},
				error: function(xhr, type){
					
				}
			});
		}

		//获取待发展顾客，交易顾客 资料数
		function getCustInfoNum(){
			$.ajax({
				cache: false,
				type: 'GET',
				url: 'mainPageCountController.do?getCustInfoNum',
				dataType: 'json',
				success: function(data){
					if(data.success){
						var numObj = data.obj;
						$('.customer-num-box .trading-num').text(numObj.tradeNum);
						$('.customer-num-box .non-trading-num').text(numObj.otherNum);
						$('.customer-num-box .total-num').text(numObj.allNum);
					}
					$('.customer-num-box .loading').fadeOut();
				},
				error: function(xhr, type){
					
				}
			});
		}

		//获取话题数据
		function getTopicModuleNum(){
			$.ajax({
				cache: false,
				type: 'GET',
				url: 'mainPageCountController.do?getTopicModuleNum',
				dataType: 'json',
				success: function(data){
					if(data.success){
						var clickNewsNumJ = data.obj.clickNewsNumJ;
						if(clickNewsNumJ && clickNewsNumJ.success){
							$('.topic-module-num .online-topic-num').text(clickNewsNumJ.obj);
						}

						var onLineNewsNumJ = data.obj.onLineNewsNumJ;
						if(onLineNewsNumJ && onLineNewsNumJ.success){
							$('.topic-module-num .click-topic-num').text(onLineNewsNumJ.obj);
						}
					}
					$('.topic-module-num .loading').fadeOut();
				},
				error: function(xhr, type){
					
				}
			});
		}

		//获取话术数据
		function getWordsModuleNum(){
			$.ajax({
				cache: false,
				type: 'GET',
				url: 'mainPageCountController.do?getWordsModuleNum',
				dataType: 'json',
				success: function(data){
					if(data.success){
						var wordsTotalJ = data.obj.wordsTotalJ;
						if(wordsTotalJ && wordsTotalJ.success){
							$('.words-num-box .words-total-num').text(wordsTotalJ.obj);
						}

						var hasMarketWordsGoodsNumJ = data.obj.hasMarketWordsGoodsNumJ;
						if(hasMarketWordsGoodsNumJ && hasMarketWordsGoodsNumJ.success){
							$('.words-num-box .sale-words-num').text(hasMarketWordsGoodsNumJ.obj);
						}

						var hasWordsGoodsNumJ = data.obj.hasWordsGoodsNumJ;
						if(hasWordsGoodsNumJ && hasWordsGoodsNumJ.success){
							$('.words-num-box .goods-words-num').text(hasWordsGoodsNumJ.obj);
						}
					}
					$('.words-num-box .loading').fadeOut();
				},
				error: function(xhr, type){
					
				}
			});
		}

		//获取订单数据
		function getOrderModuleNum(){
			$.ajax({
				cache: false,
				type: 'GET',
				url: 'mainPageCountController.do?getOrderModuleNum',
				dataType: 'json',
				success: function(data){
					if(data.success){
						var countGuaidSaledJ = data.obj.countGuaidSaledJ;
						if(countGuaidSaledJ && countGuaidSaledJ.success){
							$('.order-num-box .trading-suc-guide-num').text(countGuaidSaledJ.obj);
						}

						var countOrderOnlineJ = data.obj.countOrderOnlineJ;
						if(countOrderOnlineJ && countOrderOnlineJ.success){
							$('.order-num-box .online-order-num').text(countOrderOnlineJ.obj);
						}

						var countOrderUnderLineJ = data.obj.countOrderUnderLineJ;
						if(countOrderUnderLineJ && countOrderUnderLineJ.success){
							$('.order-num-box .offline-order-num').text(countOrderUnderLineJ.obj);
						}
					}
					$('.order-num-box .loading').fadeOut();
				},
				error: function(xhr, type){
					
				}
			});
		}

		//获取导购数据
		function getGuideModuleNum(){
			$.ajax({
				cache: false,
				type: 'GET',
				url: 'mainPageCountController.do?getGuideModuleNum',
				dataType: 'json',
				success: function(data){
					if(data.success){
						var hasGuideCountJ = data.obj.hasGuideCountJ;
						if(hasGuideCountJ && hasGuideCountJ.success){
							$('.guide-num-box .total-guide-num').text(hasGuideCountJ.obj);
						}

						var hasActiveGuideCountJ = data.obj.hasActiveGuideCountJ;
						if(hasActiveGuideCountJ && hasActiveGuideCountJ.success){
							$('.guide-num-box .active-guide-num').text(hasActiveGuideCountJ.obj);
						}

						var hasInactiveGuideCountJ = data.obj.hasInactiveGuideCountJ;
						if(hasInactiveGuideCountJ && hasInactiveGuideCountJ.success){
							$('.guide-num-box .deactivated-guide-num').text(hasInactiveGuideCountJ.obj);
						}
					}
					$('.guide-num-box .loading').fadeOut();
				},
				error: function(xhr, type){
					
				}
			});
		}

		var funArr = [];
		
		//加入方法对象
		funArr.push(getGuideModuleNum);
		funArr.push(getOrderModuleNum);
		funArr.push(getCustInfoNum);
		funArr.push(getGoodsStatusNum);
		funArr.push(getTopicModuleNum);
		funArr.push(getWordsModuleNum);

		for(var i = 0; i < funArr.length; i++){
			setTimeout(funArr[i], i * 100);  //100ms后，发一次请求
		}
	</script>
</html>