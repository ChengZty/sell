$(document).ready(function(){
    //横屏
    if($(window).width() > $(window).height()){
        $('.banner_div .img01_div').css({'margin-top':0});
    }else{
        $('.banner_div .img01_div').css({'margin-top':'3.733333rem'});
    }
    var windowWidth = $(window).width();
    var bodyWidth = $('body').width();
    var boxWidth = $('.left_slide_div').width();
    
    $('.left_slide_div').css('height',$(window).height());//动态设置高度
    $('.banner_div').css('height',$(window).height());//动态设置高度
    var bannerTop = $('.scree_box').offset().top;
    
    $('.style_box').on('click',function () {
        var $curIcon = $(this);
        var myPosition = $curIcon.attr('myPosition');
        if(!$curIcon.hasClass('active')){
            $curIcon.addClass('active');
            if(!$curIcon.hasClass('guide_help')){
                $('html,body').animate({scrollTop:$('.'+myPosition).offset().top}, 200);
            }else{
                window.location.href = 'helpDoc/daggerCase/daggerCase.html';
            }
        }
        $('.style_box').removeClass('active');
        $('.btn_menu').removeClass('active');
        $('.left_slide_div').animate({
            'margin-left':-(bodyWidth*1/2+boxWidth),
            'opacity':0
        },500);
    });
    $('.btn_menu').on('click',function () {
	    var $curIcon = $(this);
        if(!$curIcon.hasClass('active')){
            $curIcon.addClass('active');
            $('.left_slide_div').animate({'margin-left':-bodyWidth*1/2, 'opacity':1},500);
        }else{
            $curIcon.removeClass('active');
            $('.left_slide_div').animate({'margin-left':-(bodyWidth*1/2+boxWidth), 'opacity':0},500);
        }
    });
    $('.btn2').on('click',function () {
        $('.introduce_box').removeClass('active')
    });
    $('.btn1').on('click',function () {
        $('.introduce_box').addClass('active')
    });
    
    setTimeout(function () {
        $(window).scroll(function () {
            var scrollTop = $(window).scrollTop();
            console.log(bannerTop+'/'+scrollTop);
            if(scrollTop >= bannerTop-89){
                $('.title_div').addClass('active')
            }else{
                $('.title_div').removeClass('active')
            }
        });
    }, 100);

    $(window).on('resize', function(){
        setTimeout(function() {
            //横屏
            if($(window).width() > $(window).height()){
                $('.banner_div .img01_div').css({'margin-top':0});
            }else{
                $('.banner_div .img01_div').css({'margin-top':'3.733333rem'});
            }

            windowWidth = $(window).width();
            bodyWidth = $('body').width();
            boxWidth = $('.left_slide_div').width();
            
            $('.left_slide_div').css('height',$(window).height());//动态设置高度
            $('.banner_div').css('height',$(window).height());//动态设置高度
            bannerTop = $('.scree_box').offset().top;
            $('.left_slide_div').animate({'margin-left':-bodyWidth*1/2, 'opacity':1},500);
        }, 1000);
    });
});