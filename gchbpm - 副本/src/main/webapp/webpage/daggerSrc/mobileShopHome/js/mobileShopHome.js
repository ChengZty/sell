/// <reference path="F:/tianhu/typings/typings/index.d.ts" />

$(document).ready(function(){
	//fastclick处理300ms延迟
    FastClick.attach(document.body);

    //初始化iscroll
    function resetScrollSize(){
        $('.scroll_list').each(function(index, domEle){
            var $curScrollList = $(domEle);
            var tempWidth = 0;
            $curScrollList.find('.scroll_item').each(function(i, dom){
                var $curLi = $(dom);
                tempWidth += $curLi.outerWidth(true);
            });
            $curScrollList.width(tempWidth);
        });
    }

    resetScrollSize();
    //初始化所有的iscroll
    $('.scroll_box').each(function(index, domEle){
        var myScroll = new IScroll(domEle, { eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false });
    });

    //创建一个新的轮播图节点，替换原来的，之后插入到原来的前面，删除原来的节点
    $('.carousel_box').each(function(index, domEle){
        var $curCarouselBox = $(domEle);
        var $items = $curCarouselBox.find('.carousel_item');
        var tempBoxStr = '';
        var tempLiStr = '';

        $items.each(function(i, dom){
            if(!(i === 0 || i === $items.length - 1)){
                tempLiStr += '\
                    <div class="carousel_item">\
                        '+$(dom).html()+'\
                    </div>\
                ';
            }
            
        });
        tempBoxStr += '\
            <div obj_id="" class="carousel_box">\
                '+tempLiStr+'\
            </div>\
        ';
        $curCarouselBox.before(tempBoxStr);
        $curCarouselBox.remove();
    });

    //初始化所有的轮播图
    $('.carousel_box').each(function(index, domEle){
        $(domEle).slick({
            accessibility: true,
            autoplay: true,
            dots: true
        });
    });
//    //绑定点击资讯的事件
//    $("#content").on("click","div.info_item img.link_info",function(){
//    	var id = $(this).attr("posterid");
//    	window.location.href="tPosterController.do?viewPoster&id="+id+"&rId="+rId;
//    })
//    
//    //绑定点击商品的事件
//    $("#content").on("click","div.goods_item img.link_info",function(){
//    	var id = $(this).attr("goodsid");
//    	window.location.href="tGoodsController.do?goodsDetailPage&goodsId="+id+"&rId="+rId;
//    })
//    
//    //绑定点击查看全部海报的事件
//    $("#content").on("click","div.check_all_info",function(){
//    	window.location.href="tShopHomeTempController.do?viewAllPosterPage&rId="+rId;
//    })
//    //绑定点击查看全部商品的事件
//    $("#content").on("click","div.check_all_goods",function(){
//    	window.location.href="tShopHomeTempController.do?viewAllGoodsPage&rId="+rId;
//    })
    
});