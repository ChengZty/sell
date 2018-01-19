/// <reference path="F:/tianhu/typings/typings/index.d.ts" />

$(document).ready(function(){
    //模板数据
    var templateData = {
        /** 单篇 */
        singleInfo: '\
            <div temp_type="singleInfo" class="content_item info_item single">\
                <div class="handle_bar"></div>\
                <div class="btn_delelte_item"></div>\
                <div class="single_box">\
                    <img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/cover_pic.png" alt="">\
                </div>\
                <div class="btn_check_all check_all_info">查看全部资讯</div>\
            </div>\
        ',
        /** 轮播 */
        carouselInfo: '\
            <div temp_type="carouselInfo" class="content_item info_item carousel">\
                <div class="handle_bar"></div>\
                <div class="btn_delelte_item"></div>\
                <div obj_id="" class="carousel_box">\
                    <div class="carousel_item"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/cover_pic.png" alt=""></div>\
                    <div class="carousel_item"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/cover_pic.png" alt=""></div>\
                    <div class="carousel_item"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/cover_pic.png" alt=""></div>\
                    <div class="carousel_item"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/cover_pic.png" alt=""></div>\
                    <div class="carousel_item"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/cover_pic.png" alt=""></div>\
                    <div class="carousel_item"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/cover_pic.png" alt=""></div>\
                </div>\
                <div class="btn_check_all check_all_info">查看全部资讯</div>\
            </div>\
        ',
        /** 双列 */
        twoGoods: '\
            <div temp_type="twoGoods" class="content_item goods_item two_column">\
                <div class="handle_bar"></div>\
                <div class="btn_delelte_item"></div>\
                <h2 class="h_title">当红人气王</h2>\
                <div class="two_box">\
                        <ul class="two_list clearfix">\
                            <li class="two_item fl"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>\
                            <li class="two_item fl"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>\
                        </ul>\
                </div>\
                <div class="btn_check_all check_all_goods">查看全部商品</div>\
            </div>\
        ',
        /** 水平滚动 */
        scrollGoods: '\
            <div temp_type="scrollGoods" class="content_item goods_item horizontal">\
                <div class="handle_bar"></div>\
                <div class="btn_delelte_item"></div>\
                <h2 class="h_title">当红人气王</h2>\
                <div obj_id="" class="scroll_box">\
                        <ul class="scroll_list clearfix">\
                            <li class="scroll_item fl"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>\
                            <li class="scroll_item fl"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>\
                            <li class="scroll_item fl"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>\
                            <li class="scroll_item fl"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>\
                            <li class="scroll_item fl"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>\
                            <li class="scroll_item fl"><img class="link_info" temp_link="link" src="webpage/daggerSrc/common/img/bg_goods.png" alt=""></li>\
                        </ul>\
                </div>\
                <div class="btn_check_all check_all_goods">查看全部商品</div>\
            </div>\
        '
    };

    /** 工具对象 */
    var util = (function(){
        var getGUID = function() {
            function S4() {
                return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
            }
            return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
        };

        return {
            getGUID: getGUID
        };
    })();

    /** TopMenuVo start 顶部按钮*/
    var TopMenuVo = (function(){
        function TopMenuVo(box){
            if(this instanceof TopMenuVo){
                this.$box= $(box);

            }else{
                return new TopMenuVo(box);
            }
        }

        TopMenuVo.prototype = {
            constructor: TopMenuVo,
            bindClickSave: function(){//保存
                this.$box.on('click', '.btn_save', function(){
                    console.log(1);
                    doUpdate();
                });
            },
            bindClickCheck: function(){//预览
                this.$box.on('click', '.btn_check', function(){
                    console.log(1);
                    doView();
                });
            },
            bindClickCancel: function(){//完成
                this.$box.on('click', '.btn_done', function(){
                    console.log(555555555);
                    doFinish();
                });
            }
        };

        return TopMenuVo;
    })();
    /** TopMenuVo end */

    /** RightMenuVo start 右侧菜单*/
    var RightMenuVo = (function(){
        function RightMenuVo(box){
            if(this instanceof RightMenuVo){
                this.$box= $(box);
                this.$infoModule = this.$box.find('.info_menu_box');
                this.$goodsModule = this.$box.find('.goods_menu_box');

                this.initView();
            }else{
                return new RightMenuVo(box);
            }
        }

        RightMenuVo.prototype = {
            constructor: RightMenuVo,
            initView: function(){
                this.bindClickInfo();
                this.bindClickGoods();
            },
            /** 点击资讯按钮 显示资讯模块 隐藏商品模块*/
            bindClickInfo: function(){
                var _this = this;
                _this.$box.on('click', '.btn_info', function(){
                    $(this).parent().children().removeClass('active');
                    $(this).addClass('active');
                    _this.$infoModule.show();
                    _this.$goodsModule.hide();
                });
            },
            /** 点击商品按钮 隐藏资讯模块 显示商品模块**/
            bindClickGoods: function(){
                var _this = this;
                _this.$box.on('click', '.btn_goods', function(){
                    $(this).parent().children().removeClass('active');
                    $(this).addClass('active');
                    _this.$infoModule.hide();
                    _this.$goodsModule.show();
                });
            },
            /** 点击模块按钮 */
            bindStyleItem: function(dealFn){
                var _this = this;

                _this.$box.on('click', '.style_item', function(){
                    var $curItem = $(this);
                    if(!$curItem.hasClass('active')){
                        $curItem.parent().children('.style_item').removeClass('active');
                        $curItem.addClass('active');
                    }

                    //根据相应的item做相应的处理
                    dealFn($curItem);
                });
            },
            /** 点击删除连接 */
            bindClickClose: function(mobileVo){
                var _this = this;

                function removeClick(){
                    if($curTemplate.next().length>0){
                        $curTemplate.next().click();
                    }else if($curTemplate.prev().length>0){
                        $curTemplate.prev().click();
                    }
                }

                _this.$box.on('click', '.btn_close', function(){
                    var $curBtnClose = $(this);
                    var $curTemplate = mobileVo.getActiveTemplate();
                    var tempType = $curTemplate.attr('temp_type');
                    var curIndex = $curBtnClose.parents('.link_list').find('.btn_close').index($curBtnClose);
                    var btnCloseNum = $curBtnClose.parents('.link_list').find('.btn_close').length;

                    if(tempType === 'singleInfo'){
                        $curTemplate.remove();
                    }else if(tempType === 'carouselInfo'){
                        $curTemplate.find('.carousel_box').slick('slickRemove',curIndex);
                        if(btnCloseNum === 1){
                            $curTemplate.remove();
                        }
                    }else if(tempType === 'twoGoods'){
                        $curTemplate.remove();
                    }else if(tempType === 'scrollGoods'){
                        var scrollId = $curTemplate.find('.scroll_box').attr('obj_id');
                        var curScroll = mobileVo.objMap[scrollId];

                        $curTemplate.find('.scroll_box').find('.scroll_item').eq(curIndex).remove();
                        resetScrollSize();
                        setTimeout(function() {
                            curScroll.refresh();
                        }, 100);

                        if(btnCloseNum === 1){
                            $curTemplate.remove();
                        }
                    }
                    
                    $curBtnClose.parents('.info_link_item').remove();
                });
            },
            /** 根据左侧的模块的选中情况，确定右侧菜单的显示 */
            showMenuContent: function($curTemplate){
                var _this = this;

                var tempType = $curTemplate.attr('temp_type');

                if(tempType === 'singleInfo'){//单个资讯图
                    var $curListBox = _this.$box.find('.info_menu_box .link_list');
                    var $curLinkInfo = $curTemplate.find('.link_info').eq(0);
                    var tempStr = '\
                        <li class="info_link_item">\
                            <div class="btn_move_box">\
                                <div class="link_title">链接</div>\
                                <div class="btn_move">\
                                    <button class="btn_up"></button>\
                                    <button class="btn_down"></button>\
                                    <button class="btn_close"></button>\
                                </div>\
                            </div>\
                            <div class="link_url">\
                                <p class="url_content">'+$curLinkInfo.attr('src')+'</p>\
                                <button class="btn_changelink"></button>\
                            </div>\
                            <div class="info_pic_box">\
                                <img class="info_pic" src="'+$curLinkInfo.attr('src')+'" alt="">\
                                <p class="pic_note">建议选择750x446的图片，类型：jpg.png</p>\
                            </div>\
                        </li>\
                    ';
                    _this.$box.find('.link_list').children().remove();
                    $curListBox.append(tempStr);
                }else if(tempType === 'carouselInfo'){//资讯轮播图
                    var $curListBox = _this.$box.find('.info_menu_box .link_list');
                    var $linkInfoList = $curTemplate.find('.link_info');
                    var tempStr = '';
                    $linkInfoList.each(function(index, domEle){
                        var $curLinkInfo = $(domEle);

                        if(index !== 0 && index !== $linkInfoList.length-1){  //去头去尾
                            tempStr += '\
                                <li class="info_link_item">\
                                    <div class="btn_move_box">\
                                        <div class="link_title">链接</div>\
                                        <div class="btn_move">\
                                            <button class="btn_up"></button>\
                                            <button class="btn_down"></button>\
                                            <button class="btn_close"></button>\
                                        </div>\
                                    </div>\
                                    <div class="link_url">\
                                        <p class="url_content">'+$curLinkInfo.attr('src')+'</p>\
                                        <button class="btn_changelink"></button>\
                                    </div>\
                                    <div class="info_pic_box">\
                                        <img class="info_pic" src="'+$curLinkInfo.attr('src')+'" alt="">\
                                        <p class="pic_note">建议选择750x446的图片，类型：jpg.png</p>\
                                    </div>\
                                </li>\
                            ';
                        }
                    });
                    _this.$box.find('.link_list').children().remove();
                    $curListBox.append(tempStr);
                }else if(tempType === 'twoGoods' || tempType === 'scrollGoods'){//商品排列图
                    var $curListBox = _this.$box.find('.goods_menu_box .link_list');
                    var $linkInfoList = $curTemplate.find('.link_info');//temp_link
                    var tempStr = '';
                    $linkInfoList.each(function(index, domEle){
                        var $curLinkInfo = $(domEle);
                        tempStr += '\
                                <li class="info_link_item">\
                                    <div class="btn_move_box">\
                                        <div class="link_title">链接</div>\
                                        <div class="btn_move">\
                                            <button class="btn_up"></button>\
                                            <button class="btn_down"></button>\
                                            <button class="btn_close"></button>\
                                        </div>\
                                    </div>\
                                    <div class="link_url">\
                                        <p class="url_content">'+$curLinkInfo.attr('src')+'</p>\
                                        <button class="btn_changelink"></button>\
                                    </div>\
                                    <div class="info_pic_box">\
                                        <img class="info_pic" src="'+$curLinkInfo.attr('src')+'" alt="">\
                                        <p class="pic_note">建议选择340x340的图片，类型：jpg.png</p>\
                                    </div>\
                                </li>\
                            ';
                    });
                    _this.$box.find('.link_list').children().remove();
                    $curListBox.append(tempStr);

                    var tempTitle = $curTemplate.find('.h_title').text();
                    _this.$box.find('.input_title').val(tempTitle);
                }
            },
            /** 监听输入框 */
            listenInputTitle: function(mobileVo){
                this.$box.on('input propertychange', '.input_title', function(){
                    var inputValue = $(this).val();
                    console.log($curTemplate);
                    var $curTemplate = mobileVo.getActiveTemplate();
                    $curTemplate.find('.h_title').html(inputValue);
                });
            },
            /** 删除全部连接 */
            deleteAllLink: function(){
                this.$box.find('.link_list').children().remove();
            }

        };

        return RightMenuVo;
    })();
    /** RightMenuVo end */

    /** MobileVo start 手机显示内容*/
    var MobileVo = (function(){
        function MobileVo(box, templateData){
            if(this instanceof MobileVo){
                this.$box= $(box);
                this.templateData = templateData;
                this.objMap = {};  //轮播图和左右滑动的对象集合
                this.$curBtnDelete = null;  //保存当前的删除按钮对象
                
                this.initView();
            }else{
                return new MobileVo(box);
            }
        }

        MobileVo.prototype = {
            constructor: MobileVo,
            initView: function(){
                this.initIScroll();
                this.initCarousel();
                this.initDeleteItem();
            },
            initDeleteItem: function(){
                var _this = this;
                _this.$box.find('.content_item').each(function(index, domEle){
                    var $curItem = $(this);
                    if($curItem.find('.btn_delelte_item').length === 0){
                        $curItem.append('<div class="btn_delelte_item"></div>');
                    }
                });
            },
            initIScroll: function(){
                var _this = this;
                _this.$box.find('.scroll_box').each(function(index, domEle){
                    var $curScrollBox = $(this);
                    var guid = util.getGUID();
                    $curScrollBox.attr('obj_id', guid);
                    resetScrollSize();
                    var myScroll = new IScroll(domEle, { eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false });
                    _this.objMap[guid] = myScroll;
                });
            },
            initCarousel: function(){
                var _this = this;
                //如果第一次进入，则显示默认模板
                if(_this.$box.find('.default_temp').length > 0){
                    _this.$box.find('.carousel_box').each(function(index, domEle){
                        var $curCarouselBox = $(this);
                        var guid = util.getGUID();
                        $curCarouselBox.attr('obj_id', guid);
                        var mySlide  = $curCarouselBox.slick({
                            accessibility: true,
                            autoplay: true,
                            dots: true
                        });
                        _this.objMap[guid] = mySlide;
                    });
                }else{
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
                }
                
            },
            /** 插入模板 */
            insertTemplate: function(templateName){
                this.$box.append(templateData[templateName]);
                var $lastDom = this.$box.children('.content_item').last();

                $lastDom.click();

                if('carouselInfo' === templateName){
                    var $curCarouselBox = $lastDom.find('.carousel_box');
                    var guid = util.getGUID();
                    $curCarouselBox.attr('obj_id', guid);
                    var mySlide  = $curCarouselBox.slick({
                        accessibility: true,
                        autoplay: true,
                        dots: true
                    });
                    this.objMap[guid] = mySlide;
                }else if('scrollGoods' === templateName){
                    var $curScrollBox = $lastDom.find('.scroll_box');
                    var guid = util.getGUID();
                    $curScrollBox.attr('obj_id', guid);
                    resetScrollSize();
                    var myScroll = new IScroll($curScrollBox[0], { eventPassthrough: true, scrollX: true, scrollY: false, preventDefault: false });
                    this.objMap[guid] = myScroll;
                }
                
            },
            /** 让最后一个商品模块或者资讯模块中的 查看全部 显示 */
            showCheckAll: function(){
                this.$box.find('.btn_check_all').hide();
                this.$box.find('.content_item.info_item').last().find('.btn_check_all').show();
                this.$box.find('.content_item.goods_item').last().find('.btn_check_all').show();
            },
            /** 获取选中的模板 */
            getActiveTemplate: function(){
                return this.$box.find('.content_item.selected');
            },
            /** 滚动到底部 */
            scrollToBottom: function(){
                $(window).scrollTop(this.$box.find('.content_item').last().offset().top);
            },
            /** 点击模板 */
            bindClickTemplate: function(rightMenuVo){
                var _this = this;
                _this.$box.on('click', '.content_item', function(){
                    var $curItem = $(this);
                    $curItem.parent().children().removeClass('selected');
                    $curItem.addClass('selected');

                    if($curItem.hasClass('info_item')){
                        rightMenuVo.$box.find('.btn_info').click();
                    }else{
                        rightMenuVo.$box.find('.btn_goods').click();
                    }

                    setTimeout(function() {
                        rightMenuVo.showMenuContent(_this.getActiveTemplate());
                    }, 300);
                });
            },
            /** 点击删除全部 */
            // bindClickDelete: function(rightMenuVo, popWindowVo){
            //     var _this = this;
            //     _this.$box.on('click', '.btn_delelte_item', function(){

            //         popWindowVo.show();

            //         var $curBtnDelete = $(this);
            //         var $curTemplate = $curBtnDelete.parents('.content_item');
            //         rightMenuVo.deleteAllLink();

            //         setTimeout(function() {
            //             if($curTemplate.next().length>0){
            //                 $curTemplate.next().click();
            //             }else if($curTemplate.prev().length>0){
            //                 $curTemplate.prev().click();
            //             }
                        
            //             $curTemplate.remove();
            //         }, 100);
            //     });
            // },
            /** 点击删除全部 */
            bindClickDelete: function(dealFn){
                var _this = this;
                _this.$box.on('click', '.btn_delelte_item', function(){
                    _this.$curBtnDelete = $(this);
                    if(typeof dealFn === 'function'){
                        dealFn();
                    }
                });
            }
        };

        return MobileVo;
    })();
    /** MobileVo end */

    /** PopWindowVo start 弹出窗*/
    var PopWindowVo = (function(){
        function PopWindowVo(box){
            if(this instanceof PopWindowVo){
                this.$box= $(box);

                this.initView();
            }else{
                return new PopWindowVo(box);
            }
        }

        PopWindowVo.prototype = {
            constructor: PopWindowVo,
            initView: function(){
                this.bindClickNo();
            },
            show: function(){
                this.$box.show();
            },
            hide: function(){
                this.$box.hide();
            },
            bindClickNo: function(){
                 var _this = this;
                _this.$box.on('click', '.simple_popwindow_no', function(){
                    _this.hide();
                });
                
            },
            bindClickYes: function(dealFn){
                var _this = this;
                _this.$box.on('click', '.simple_popwindow_yes', function(){
                    if(typeof dealFn === 'function'){
                        dealFn($(this));
                    }
                });
            }
        };

        return PopWindowVo;
    })();
    /** PopWindowVo end */


    /** 添加模块 */
    function addTemplate($curItem){
        mobileVo.insertTemplate($curItem.attr('style_name'));
        mobileVo.showCheckAll();
        mobileVo.scrollToBottom();
    }

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

    //点击弹窗中的‘是’按钮
    function popWindowYes(){
        var $curBtnDelete = mobileVo.$curBtnDelete;
        var $curTemplate = $curBtnDelete.parents('.content_item');
        rightMenuVo.deleteAllLink();

        setTimeout(function() {
            if($curTemplate.next().length>0){
                $curTemplate.next().click();
            }else if($curTemplate.prev().length>0){
                $curTemplate.prev().click();
            }
            
            $curTemplate.remove();

            popWindowVo.hide();
        }, 100);
    }

    var topMenuVo = new TopMenuVo('.th_top');
    var rightMenuVo = new RightMenuVo('.th_menu');
    var mobileVo = new MobileVo('.mobile_content', templateData);
    var popWindowVo = new PopWindowVo('.simple_popwindow');

    //点击 保存
    topMenuVo.bindClickSave();
    //点击 查看
    topMenuVo.bindClickCheck();
    //点击 取消
    topMenuVo.bindClickCancel();

    rightMenuVo.bindStyleItem(addTemplate);
    rightMenuVo.listenInputTitle(mobileVo);
    rightMenuVo.showMenuContent(mobileVo.getActiveTemplate());
    rightMenuVo.bindClickClose(mobileVo);

    mobileVo.showCheckAll();
    mobileVo.bindClickTemplate(rightMenuVo);
    mobileVo.bindClickDelete(function(){
        popWindowVo.show();
    });
    popWindowVo.bindClickYes(popWindowYes);

    //初始化上下移动
    var sortable = Sortable.create($('.edit_content .mobile_content')[0], {
        handle: '.handle_bar',
        onSort: function(){
            mobileVo.showCheckAll();
        }
    });

    //禁止图片拖动
    $('img.link_info').removeAttr('ondragstart');  //移除原有的
    $('body').on('dragstart', 'img.link_info', function(){
        return false;
    });

	//获取海报列表和商品列表
	$('.right_content').on('click', '.btn_changelink', function(){
		var $curTemplate = mobileVo.getActiveTemplate();
        var tempType = $curTemplate.attr('temp_type');
		console.log(tempType);
		 if(tempType === 'singleInfo' || tempType === 'carouselInfo'){//单个资讯图和资讯轮播图
			 getPosterList(this, mobileVo);
		 }else if(tempType === 'twoGoods' || tempType === 'scrollGoods'){//商品排列图
			 getGoodsList(this, mobileVo);
		 }
	});
	
	$('.right_content').on('click', '.info_pic', function(){
		var $curTemplate = mobileVo.getActiveTemplate();
        var tempType = $curTemplate.attr('temp_type');
		console.log(tempType);
		 if(tempType === 'singleInfo' || tempType === 'carouselInfo'){//单个资讯图和资讯轮播图
			 getPosterList(this, mobileVo);
		 }else if(tempType === 'twoGoods' || tempType === 'scrollGoods'){//商品排列图
			 getGoodsList(this, mobileVo);
		 }
	});
		
});

//获取海报列表  
function getPosterList(obj, mobileVo){
	var $curBtnLink = $(obj);
	  var width = 900;
		var height = 700;
		var title = "海报列表 ";
	  if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:tShopHomeTempController.do?tPosterLinkList',
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: height,
			    ok: function(){
		               var iframe = this.iframe.contentWindow;
		               iframe.uploadCallback();//调用iframe中的方法
		               var $curLi = $curBtnLink.parents('.info_link_item');
		               var $curLinkList = $curLi.parents('.link_list');//ul
		               var curIndex = $curLinkList.find('.info_link_item').index($curLi);//li
		               $curLi.find(".info_pic").attr("src",iframe.coverPic);//海报封面图
		               //$curLi.find(".info_pic").attr("posterId",iframe.posterId);//海报封面图
		               $curLi.find(".url_content").html(iframe.coverPic);//链接地址
		               
		               var $curTemplate = mobileVo.getActiveTemplate();
		               var tempType = $curTemplate.attr('temp_type');
		               if(tempType === 'carouselInfo'){
		            	   $curTemplate.find('.link_info').eq(curIndex+1).attr("src",iframe.coverPic);//海报封面图
		            	   $curTemplate.find('.link_info').eq(curIndex+1).attr("posterId",iframe.posterId);//海报封面图
		               }else{
		            	   $curTemplate.find('.link_info').eq(curIndex).attr("src",iframe.coverPic);//海报封面图
		            	   $curTemplate.find('.link_info').eq(curIndex).attr("posterId",iframe.posterId);//海报封面图
		               }
		               
		               return true;
		       },
		       cancelVal: '关闭',
		       cancel: function(){
		    	   
		       } 
			}).zindex();
		}else{
			$.dialog({
				content: 'url:tShopHomeTempController.do?tPosterLinkList',
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: height,
			    ok: function(){
		               var iframe = this.iframe.contentWindow;
		               iframe.uploadCallback();//调用iframe中的方法
		               var $curLi = $curBtnLink.parents('.info_link_item');
		               var $curLinkList = $curLi.parents('.link_list');//ul
		               var curIndex = $curLinkList.find('.info_link_item').index($curLi);//li
		               $curLi.find(".info_pic").attr("src",iframe.coverPic);//海报封面图
//		               $curLi.find(".info_pic").attr("posterid",iframe.posterId);//海报封面图
		               $curLi.find(".url_content").html(iframe.coverPic);//链接地址
		               
		               var $curTemplate = mobileVo.getActiveTemplate();
		               var tempType = $curTemplate.attr('temp_type');
		               if(tempType === 'carouselInfo'){
		            	   $curTemplate.find('.link_info').eq(curIndex+1).attr("src",iframe.coverPic);//海报封面图
		            	   $curTemplate.find('.link_info').eq(curIndex+1).attr("posterid",iframe.posterId);//海报封面图
		               }else{
		            	   $curTemplate.find('.link_info').eq(curIndex).attr("src",iframe.coverPic);//海报封面图
		            	   $curTemplate.find('.link_info').eq(curIndex).attr("posterid",iframe.posterId);//海报封面图
		               }
		               return true;
		       },
		       cancelVal: '关闭',
		       cancel: function(){
		    	   
		       } 
			}).zindex();
		}
  }

//获取商品列表  
function getGoodsList(obj, mobileVo){
	var $curBtnLink = $(obj);
	  var width = 900;
		var height = 700;
		var title = "商品列表 ";
	  if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:tShopHomeTempController.do?tGoodsLinkList',
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: height,
			    ok: function(){
		               var iframe = this.iframe.contentWindow;
		               var $curLi = $curBtnLink.parents('.info_link_item');
		               var $curLinkList = $curLi.parents('.link_list');//ul
		               var curIndex = $curLinkList.find('.info_link_item').index($curLi);//li 从0开始
		               $curLi.find(".info_pic").attr("src",iframe.imgUrl);//商品图片
		               $curLi.find(".url_content").html(iframe.imgUrl);//链接地址
//		               $curLi.find(".info_pic").attr("goodsid",iframe.goodsId);//商品id
		               
		               var $curTemplate = mobileVo.getActiveTemplate();
	            	   
	            	   
	            	   var $curTemplate = mobileVo.getActiveTemplate();
		               var tempType = $curTemplate.attr('temp_type');
		               if(tempType === 'carouselInfo'){
		            	   $curTemplate.find('.link_info').eq(curIndex+1).attr("src",iframe.imgUrl);//商品图片
		            	   $curTemplate.find('.link_info').eq(curIndex+1).attr("goodsid",iframe.goodsId);//商品id
		               }else{
		            	   $curTemplate.find('.link_info').eq(curIndex).attr("src",iframe.imgUrl);//商品图片
		            	   $curTemplate.find('.link_info').eq(curIndex).attr("goodsid",iframe.goodsId);//商品id
		               }
		               
		               
		               return true;
		       },
		       cancelVal: '关闭',
		       cancel: function(){
		    	   
		       } 
			}).zindex();
		}else{
			$.dialog({
				content: 'url:tShopHomeTempController.do?tGoodsLinkList',
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: height,
			    ok: function(){
		               var iframe = this.iframe.contentWindow;
		               var $curLi = $curBtnLink.parents('.info_link_item');
		               var $curLinkList = $curLi.parents('.link_list');//ul
		               var curIndex = $curLinkList.find('.info_link_item').index($curLi);//li
		               $curLi.find(".info_pic").attr("src",iframe.imgUrl);//商品图片
		               $curLi.find(".url_content").html(iframe.imgUrl);//链接地址
//		               $curLi.find(".info_pic").attr("goodsid",iframe.goodsId);//商品id
		               
		               var $curTemplate = mobileVo.getActiveTemplate();
	            	   $curTemplate.find('.link_info').eq(curIndex+1).attr("src",iframe.imgUrl);//商品图片
	            	   $curTemplate.find('.link_info').eq(curIndex+1).attr("goodsid",iframe.goodsId);//商品id
		               return true;
		       },
		       cancelVal: '关闭',
		       cancel: function(){
		    	   
		       } 
			}).zindex();
		}
  }
//保存
 function doUpdate(){
	 var content = $("#mobile_content").html();
     var id = $("#id").val();
     $.ajax({
 		url : "tShopHomeTempController.do?doUpdate",
 		type : 'post',
 		cache : false,
 		data:{id:id,content:content},
 		success : function(data) {
 			var d = $.parseJSON(data);
 			var obj = d.obj;//是object对象
 			if (d.success) {
 				$("#id").val(obj);
 				alert('保存成功');
 			}else{
 				alert('保存失败');
 			}
 		},error:function(){
 			alert('保存失败');
 		}
 	});
 }
//预览
 function doView(){
	 var content = $("#mobile_content").html();
	//判断表单是否存在，若不存在则在body中添加form表单  
	 if($("#contentForm").length<=0){  
	     var form = "<form id='contentForm' action='tShopHomeTempController.do?goView' method='post' target='contentWin'>" +  
	             "<input type='hidden' id='mobile_content_html' name='content'/>" +  
	             "</form>";  
	     $("body").append(form);//在body中添加form表单  
	 }  
	 //将用户名和密码保存到form表单的隐藏域中  
	 $("#mobile_content_html").val(content);  
	 //打开新的窗口  
	 window.open("tShopHomeTempController.do?goView","contentWin");  
	 //提交表单  
	 $("#contentForm").submit(); 
 }
//完成
 function doFinish(){
	 var content = $("#mobile_content").html();
     var id = $("#id").val();
     $.ajax({
 		url : "tShopHomeTempController.do?doFinish",
 		type : 'post',
 		cache : false,
 		data:{id:id,content:content},
 		success : function(data) {
 			var d = $.parseJSON(data);
 			var obj = d.obj;//是object对象
 			if (d.success) {
 				$("#id").val(obj);
 				alert('保存成功');
 			}else{
 				alert('保存失败');
 			}
 		},error:function(){
 			alert('保存失败');
 		}
 	});
 }



 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 






