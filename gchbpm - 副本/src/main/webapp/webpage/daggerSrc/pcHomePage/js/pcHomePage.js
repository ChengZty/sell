$(document).ready(function () {

    var heightArr = [0];
    $('.icon_box01').on('click',function () {
        $(window).off('scroll');
        var $curIcon = $(this);
        var myPosition = $curIcon.attr('myPosition');
        if(!$curIcon.hasClass('active')){
            $('.icon_box01, .icon_box, .icon_box06').removeClass('active');
            $curIcon.addClass('active');
            $('html,body').stop(true);
            $('html,body').animate({scrollTop:$('.'+myPosition).offset().top}, 200, function () {
                setTimeout(function () {
                    $(window).on('scroll',selectByScroll);
                }, 10);
            });
        }
    });
    $('.icon_box06').on('click',function () {
        $(window).off('scroll');
        var $curIcon = $(this);
        var myPosition = $curIcon.attr('myPosition');
        if(!$curIcon.hasClass('active')){
            $('.icon_box01, .icon_box, .icon_box06').removeClass('active');
            $curIcon.addClass('active');
            $('html,body').stop(true);
            $('html,body').animate({
                scrollTop:$('.'+myPosition).offset().top}, 200, function () {
                setTimeout(function () {
                    $(window).on('scroll',selectByScroll);
                }, 10);
            });
        }
    });
    $('.icon_box').on('click',function () {
        $(window).off('scroll');
        var $curIcon = $(this);
        var myPosition = $curIcon.attr('myPosition');
        if(!$curIcon.hasClass('active')){
            $('.icon_box01, .icon_box, .icon_box06').removeClass('active');
            $curIcon.addClass('active');
            $('html,body').stop(true);
            $('html,body').animate({scrollTop:$('.'+myPosition).offset().top}, 200, function () {
                setTimeout(function () {
                    $(window).on('scroll',selectByScroll);
                }, 10);
            });
        }
    });
    $('.btn_standard').click(function () {
        // $('.meal_content').css('display','block');
        // $('.meal_content1').css('display','none');
        // $('.btn_standard').removeClass('active');
        // $('.btn_specialty').removeClass('active');
        // $('.btn_standard').addClass('active');
        $('.product_introduce_div').removeClass('specialty');
        $('.product_introduce_div').addClass('standard');
    });
    $('.btn_specialty').click(function () {
        // $('.meal_content').css('display','none');
        // $('.meal_content1').css('display','block');
        // $('.btn_standard').removeClass('active');
        // $('.btn_specialty').removeClass('active');
        // $('.btn_specialty').addClass('active');
        $('.product_introduce_div').removeClass('standard');
        $('.product_introduce_div').addClass('specialty');
    });

    var $btnList = $('.scroll_select');
    $('.measure_top').each(function (index, domEle) {
        var $curDiv = $(domEle);
        heightArr.push($curDiv.offset().top);
    });

    function  selectByScroll() {
        setTimeout(function() {
            var scrollTop = $(window).scrollTop();
            $.each(heightArr, function (i, arrValue) {
                if(i<heightArr.length-1){
                    if(scrollTop > arrValue && scrollTop < heightArr[i+1]){
                        $btnList.removeClass('active');
                        $btnList.eq(i).addClass('active');
                    }
                }else if(i===heightArr.length-1){
                    if(scrollTop > heightArr[i]){
                        $btnList.removeClass('active');
                        $btnList.eq(i).addClass('active');
                    }
                }
            });
            if((scrollTop +$(window).height()) >= $('body').height() ){
                $btnList.removeClass('active');
                $btnList.last().addClass('active');
            }
        }, 10);
    }
    $(window).on('scroll',selectByScroll);
    $(window).trigger('scroll');

    /** 函数节流 */
    function throttle(method, context, event) { 
        clearTimeout(method.tId); 
        method.tId= setTimeout(function(){ 
            method.call(context, event); 
        }, 100); 
    }

    /** 滚动到相应的位置 */
    function scrollToPosition(event){
        var $btnActive = $('.icon_div div[myPosition].active'); //获取当前状态为active的按钮
        var deltaY = event.deltaY;
        if(deltaY > 0){ //向上
            if($btnActive.prev().length > 0){
                $btnActive.prev().click();
            }
        }else if(deltaY < 0){ //向下
            if($btnActive.next().length > 0){
                $btnActive.next().click();
            }
        }
    }
    

    /** 监听滚轮 */
    $(window).on('mousewheel', function(event){
        event.preventDefault();  //阻止滚动事件的默认处理
        throttle(scrollToPosition, window, event);
    });

    /** 监听 窗口缩放 */
    $(window).on('resize', function(){
        setTimeout(function() {
            console.log('resize');
        }, 1000);
    });
});

