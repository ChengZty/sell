$(function(){

	var util = (function(){
		var px2rem  = function(d, rem) {
			if(typeof d === 'string' && d === 'auto'){
				return d;
			}
			var val = parseFloat(d) / rem;
			if (typeof d === 'string' && d.match(/px$/)) {
				val += 'rem';
			}
			return val;
		}

		return {
			px2rem: px2rem
		};
	})();

	//绑定点击商品的事件
// 	$("body div.goods_box").on("click",function(){
// 		var goodsId = $(this).find("input.goods_id").val();
// 		window.location.href="tGoodsController.do?goodsDetailPage&isShare=1&dtl=1&goodsId="+goodsId+"&rId="+rId;
// //		window.location.href=domain+"tGoodsController.do?goodsViewDetail&goodsId="+goodsId+"&retailerId="+rId+"&isShare=1&dtl=1";
// //		alert(goodsId);
// 	});
	//记录顾客点击短信链接
  	doMarkSmsClick();

	$('.mobile_item.goods_box').each(function(index, domEle){
		var $curBox = $(domEle);
		if($curBox.find('.hint_buy').length === 0){
			$curBox.find('.goods_detail_box').append('<div class="hint_buy"></div>');
		}
	});

	

	/** MobileVo start */
    var MobileVo = (function(){
        function MobileVo(box){
            if(this instanceof MobileVo){
                this.$box= $(box);

				this.initView();
            }else{
                return new MobileVo(box);
            }
        }

        MobileVo.prototype = {
            constructor: MobileVo,
			initView: function(){
				this.innerStyleToRem();
				this.initCarousel();
				this.removeAllActive();
				this.initScrollBar();
			},
			/** 初始化轮播图 */
			initCarousel: function(){
				var _this = this;
				
				var $carouselBox = _this.$box.find('.carousel_box');
				_this.replaceOldCarousel($carouselBox);
				$carouselBox = _this.$box.find('.carousel_box');

				$carouselBox.slick({
					accessibility: true,
					autoplay: true,
					dots: true
				});
				$carouselBox.find('.slick-prev, .slick-next').css('display', 'none');
			},
            /** 创建一个新的轮播图节点，替换原来的，之后插入到原来的前面，删除原来的节点 */
            replaceOldCarousel: function($carouselBox){
                $carouselBox.each(function(index, domEle){
                    var $curCarouselBox = $(domEle);
                    var $items = $curCarouselBox.find('.carousel_item');
                    var tempBoxStr = '';
                    var tempLiStr = '';

                    $items.each(function(i, dom){
                        if(!(i === 0 || i === $items.length - 1)){
                            tempLiStr += '\
                                <div class="carousel_item change_img_item">\
                                    '+$(dom).html()+'\
                                </div>\
                            ';
                        }
                        
                    });
                    tempBoxStr += '\
                        <div carousel_id="111" class="carousel_box">\
                            '+tempLiStr+'\
                        </div>\
                    ';
                    $curCarouselBox.before(tempBoxStr);
                    $curCarouselBox.remove();
                });
            },
			/** 把所有的内置样式中的定位px转成rem  标准是: 37.5 */
			innerStyleToRem: function(){
				var _this = this;
				
				var $allDom = _this.$box.find('div[th_draggable]');
				$allDom.each(function(index, domEle){
					var $curDom = $(domEle);
					var poStr = $curDom.css('position');
					var top, left;

					if(poStr === 'absolute'){
						top = $curDom.css('top');
						left = $curDom.css('left');
						$curDom.css({
							"top": util.px2rem(top, 37.5),
							"left": util.px2rem(left, 37.5)
						});
					}
				});
			},
			/** 去除div[th_editable]中的active */
			removeAllActive: function(){
				this.$box.find('div[th_editable]').removeClass('active');
			},
			/** 初始化滚动条 */
			initScrollBar: function(){
				var _this = this;
				
                _this.$box.mCustomScrollbar({
					axis:"y", // vertical and horizontal scrollbar
					theme:"minimal-dark"
				});
			}
        };

        return MobileVo;
    })();
    /** MobileVo end */

	var mobileVo = new MobileVo('.mobile_content');
});


function doMarkSmsClick(){
//	alert(domain);
	if(""!=sid&&"null"!=sid){//短信ID
		$.ajax({
	          type: "post",
//     	          url: "http://192.168.1.19:8080/guideApp/tSmsSendController.do?doMarkSmsClick",
	          url: domain+"tSmsSendController.do?doMarkSmsClick",
	          data : {id:sid},
	          success: function(data) {
	        	  console.log(data);
	          }
	      });
		  return false;
	}
}