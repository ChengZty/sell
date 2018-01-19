var addTabs = function (options) {
    //var rand = Math.random().toString();
    //var id = rand.substring(rand.indexOf('.') + 1);
    //var url = window.location.protocol + '//' + window.location.host;
    //options.url = url + options.url;
	
    var id = "tab_" + options.id;
    $(".active").removeClass("active");
    //如果TAB不存在，创建一个新的TAB
    if (!$("#" + id)[0]) {
        //固定TAB中IFRAME高度
        //mainHeight = $(top.document.body).height()-150;
    	mainHeight = $(window).height()-120;
        //mainHeight = 683;//Ace 右侧高度默认
        //创建新TAB的title
        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
        //是否允许关闭
        if (options.close) {
            title += ' <i class="icon-remove" tabclose="' + id + '"></i>';
        }
        title += '</a></li>';
        //是否指定TAB内容
        if (options.content) {
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + options.url + '" width="100%" height="' + mainHeight +
                    '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
        }
        //加入TABS
        $(".nav-tabs").append(title);
        $(".tab-content").append(content);

        calcTabsWidth();
        checkShowControl();
        viewAllTabsVo.addIframe(options);
    }
    //激活TAB
    $("#tab_" + id).addClass('active');
    $("#" + id).addClass("active");

    var last = $("#tabs .nav-tabs>li:last");
    /*$(".contextMenuPlugin").mouseout(function(){
     $(".contextMenuPlugin").remove();
     })
     $(".contextMenuPlugin").mouseup(function(){
     alert("aaa");
     })*/
    last.contextPopup({
        title: '菜单',
        items: [
            {
                label:'刷新缓存',icon:'plug-in/diy/icons/shopping-basket.png',action:function(){
                //last就是当前选中的元素
                var tab = last.children("a").attr("aria-controls").toString();
                //$("#tabs").find("li[aria-controls='"+tab+"']").remove();
                var div = $("#tabs").find("div[id='"+tab+"']");
                div.find("iframe").attr("src",options.url);
                //tabs.tabs("refresh");
            }
            },
            {
                label:'关闭',icon:'plug-in/diy/icons/shopping-basket.png',action:function(){
                //last就是当前选中的元素
                var closeText = last.children("a").text().trim();
                var nowText = $("#tabs").find("li[class='active']").children("a").text().trim();
                if(closeText==nowText){
                    //关闭的是当前页的时候，显示前一页，如果没有前一页了，就提示
                    var prevCount = last.prevAll().size();
                    if(prevCount==0){
                        var tab = last.children("a").attr("aria-controls").toString();
                        last.remove();
                        $("#tabs").find("div[id='"+tab+"']").remove();
                    }else{
                        //显示前一个tab
                        var tab = last.children("a").attr("aria-controls").toString();
                        var prev = last.prevAll().first();
                        last.remove();
                        $("#tabs").find("div[id='"+tab+"']").remove();
                        prev.addClass("active");
                        var id = prev.children("a").attr("aria-controls").toString();
                        $("#tabs").find("div[id='"+id+"']").addClass("active");
                    }
                }else{
                    //关闭的不是当前页，关闭就好了╮(╯_╰)╭
                    var tab = last.children("a").attr("aria-controls").toString();
                    last.remove();
                    $("#tabs").find("div[id='"+tab+"']").remove();
                }
            }
            },
            {
                label:'全部关闭',icon:'plug-in/diy/icons/shopping-basket.png',action:function(){
                $("#tabs .th-nav-tabs .nav-tabs li").remove();
                $("#tabs .tab-content div").remove();
                //tabs.tabs("refresh");
            }
            },
            {
                label:'除此之外全部关闭',icon:'plug-in/diy/icons/shopping-basket.png',action:function(){
                var closeText = last.children("a").text().trim();
                var nowText = $("#tabs").find("li[class='active']").children("a").text().trim();
                //此是当前页则关闭，如果不是当前页面，要激活选择页面
                if(closeText==nowText){
                    //此是当前页面
                    var tab = last.children("a").attr("aria-controls").toString();
                    $("#tabs .th-nav-tabs .nav-tabs li").not(last).remove();
                    $("#tabs .tab-content div").not($("#tabs").find("div[id='"+tab+"']")).remove();
                }else{
                    var tab = last.children("a").attr("aria-controls").toString();
                    $("#tabs .th-nav-tabs .nav-tabs li").not(last).remove();
                    $("#tabs .tab-content div").not($("#tabs").find("div[id='"+tab+"']")).remove();
                    last.addClass("active");
                    var id = last.children("a").attr("aria-controls").toString();
                    $("#tabs").find("div[id='"+id+"']").addClass("active");
                }
                //tabs.tabs("refresh");
            }
            },
            null,
            {
                label:'当前页右侧全部关闭',icon:'plug-in/diy/icons/shopping-basket.png',action:function(){
                var closeText = last.children("a").text().trim();
                var nowText = $("#tabs").find("li[class='active']").children("a").text().trim();
                if(closeText==nowText){
                    //当前页面
                    var nextAll = last.nextAll();
                    if(nextAll.length!=0){
                        nextAll.remove();
                        var tab = last.children("a").attr("aria-controls").toString();
                        //$("#tabs>ul>li").not(shouye).remove();
                        $("#tabs>div").find("div[id='"+tab+"']").nextAll().remove();
                        //tabs.tabs("refresh");
                    }else{
                        layer.msg('<b>右侧没有啦</b>');
                    }
                }else{
                    //不是当前页，当前页的active去掉
                    var now = $("#tabs").find("li[class='active']");
                    var nowid = now.children("a").attr("aria-controls").toString();
                    now.removeClass("active");
                    $("#tabs").find("div[id='"+nowid+"']").removeClass("active");
                    var nextAll = last.nextAll();
                    if(nextAll.length!=0){
                        nextAll.remove();
                        var tab = last.children("a").attr("aria-controls").toString();
                        //$("#tabs>ul>li").not(shouye).remove();
                        $("#tabs>div").find("div[id='"+tab+"']").nextAll().remove();
                        last.addClass("active");
                        var id = last.children("a").attr("aria-controls").toString();
                        $("#tabs").find("div[id='"+id+"']").addClass("active");
                        //tabs.tabs("refresh");
                    }else{
                        layer.msg('<b>右侧没有啦</b>');
                    }
                }
            }
            },
            {
                label:'当前页左侧全部关闭',icon:'plug-in/diy/icons/shopping-basket.png',action:function(){
                var closeText = last.children("a").text().trim();
                var nowText = $("#tabs").find("li[class='active']").children("a").text().trim();
                if(closeText==nowText){
                    //当前页面
                    var prevAll = last.prevAll();
                    if(prevAll.length!=0){
                        prevAll.remove();
                        var tab = last.children("a").attr("aria-controls").toString();
                        //$("#tabs>ul>li").not(shouye).remove();
                        $("#tabs>div").find("div[id='"+tab+"']").prevAll().remove();
                        //tabs.tabs("refresh");
                    }else{
                        layer.msg('<b>左侧没有啦</b>');
                    }
                }else{
                    //不是当前页，当前页的active去掉
                    var now = $("#tabs").find("li[class='active']");
                    var nowid = now.children("a").attr("aria-controls").toString();
                    now.removeClass("active");
                    $("#tabs").find("div[id='"+nowid+"']").removeClass("active");
                    var prevAll = last.prevAll();
                    if(prevAll.length!=0){
                        prevAll.remove();
                        var tab = last.children("a").attr("aria-controls").toString();
                        //$("#tabs>ul>li").not(shouye).remove();
                        $("#tabs>div").find("div[id='"+tab+"']").prevAll().remove();
                        last.addClass("active");
                        var id = last.children("a").attr("aria-controls").toString();
                        $("#tabs").find("div[id='"+id+"']").addClass("active");
                        //tabs.tabs("refresh");
                    }else{
                        layer.msg('<b>左侧没有啦</b>');
                    }
                }
                /*var prevAll = last.prevAll();
                 if(prevAll.length!=0){
                 prevAll.remove();
                 }else{
                 layer.msg('<b>左侧没有啦</b>');
                 }
                 var tab = last.attr("aria-controls").toString();
                 //$("#tabs>ul>li").not(shouye).remove();
                 $("#tabs>div").find("div[id='"+tab+"']").prevAll().remove();*/
                //tabs.tabs("refresh");
            }
            }
        ]
    });
};
var closeTab = function (id) {
    //如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($("li.active").attr('id') == "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
    }
    //关闭TAB
    $("#tab_" + id).remove();
    $("#" + id).remove();

    calcTabsWidth();
    checkShowControl();
    viewAllTabsVo.removeIframe(id.replace('tab_', ''));
};

/** 计算ul的宽度；增加tab时，计算；减少tab时，计算 */
function calcTabsWidth(){
    $('.nav-tabs').css({
        'width': function(){
            var tempWidth = 0;
            var $liArr = $('.nav-tabs').children('li');
            $liArr.each(function(index, domEle){
                tempWidth += $(domEle).outerWidth(true) + 1;
            });
            return tempWidth;
        }
    });
}

/**
 * 判断长度，是否显示控制按钮；
 * 三个地方需要检测，添加；减少；窗口宽度变化
 */
function checkShowControl(){
    var divWidth = $('.th-nav-tabs').width();
    var ulWidth = $('.th-tab-list .nav-tabs').width();
    if(ulWidth > divWidth){
        $('.th-nav-tabs').addClass('show-control');
        $('.th-nav-tabs .btn-right-last').click();  //滚动到最后
    }else{
        $('.th-nav-tabs').removeClass('show-control');
        $('.th-nav-tabs .btn-left-first').click();  //滚动到开始位置
    }
}

//函数节流
function throttle(method, context) { 
    clearTimeout(method.tId); 
    method.tId= setTimeout(function(){ 
        method.call(context); 
    }, 100); 
}

//获取移动样式
function getStyle (x, time){
    time = time || 100;
    return {
        '-ms-transition':'-ms-transform '+time+'ms',
        '-ms-transform':'translate3d('+x+'px,0,0)',
        '-mo-transition':'-mo-transform '+time+'ms',
        '-mo-transform':'translate3d('+x+'px,0,0)',
        '-webkit-transition':'-webkit-transform '+time+'ms',
        '-webkit-transform':'translate3d('+x+'px,0,0)',
        'transition':'transform '+time+'ms',
        'transform':'translate3d('+x+'px,0,0)'
    };
}


//窗口发生变化
window.onresize = function(){ 
    throttle(checkShowControl); 
};

/** NavScrollVo start */
var NavScrollVo = (function(){
    function NavScrollVo(box){
        if(this instanceof NavScrollVo){
            this.$box= $(box);
            this.$navTabs = this.$box.find('.nav-tabs');
            
            this.translateX = 0; //translate的偏移量

            this.initView();
        }else{
            return new NavScrollVo(box);
        }
    }

    NavScrollVo.prototype = {
        constructor: NavScrollVo,
        initView: function(){
            this.bindClickBtn();
        },
        /** 移动到指定的tab处 */
        scrollToTab: function(index){
            var $navTabsLi = this.$box.find('.nav-tabs li');
            //容器长度
            var navBoxWidth = this.$box.find('.th-tab-list').width();
            //index 左侧的长度
            var indexLeftWidth = 0;
            //index 右侧的长度（包括其自身）
            var indexRightWidth = 0;
            //总长度
            var totalWidth = 0;
            $navTabsLi.each(function(i, domEle){
                if(i < index){
                    indexLeftWidth += $(domEle).outerWidth(true);
                }else{
                    indexRightWidth += $(domEle).outerWidth(true);
                }
            });
            totalWidth = indexLeftWidth + indexRightWidth;
            
            if(totalWidth <= navBoxWidth){
            	return;
            }
            if(indexRightWidth > navBoxWidth){
                this.translateX = -indexLeftWidth;
            }else{
                this.translateX = -(totalWidth - navBoxWidth);
            }
            this.$navTabs.css(getStyle(this.translateX));
        },
        bindClickBtn: function(){
            var _this = this;
            //点击 最左
            _this.$box.on('click', '.btn-left-first', function(){
                //移动到ul中的第一个li
                _this.translateX = 0;
                _this.$navTabs.css(getStyle(_this.translateX));
            });
            //点击 上一个
            _this.$box.on('click', '.btn-left-pre', function(){
                var $navTabsLi = _this.$box.find('.nav-tabs li');
                var absTranslateX = Math.abs(_this.translateX);
                var beforeWidth = 0;
                var afterWidth = 0;

                if(_this.translateX >= 0){
                    return;
                }
                
                //找出卡在左侧的li
                $navTabsLi.each(function(index, domEle){
                    var $curLi = $(domEle);

                    afterWidth += $curLi.outerWidth(true);

                    if(absTranslateX > beforeWidth && absTranslateX <= afterWidth){  //位置卡在中间
                        return false;
                    }

                    beforeWidth += $curLi.outerWidth(true);
                });

                _this.translateX = -beforeWidth;
                _this.$navTabs.css(getStyle(_this.translateX));
            });
            //点击 下一个
            _this.$box.on('click', '.btn-right-next', function(){
                var $navTabsLi = _this.$box.find('.nav-tabs li');
                var navBoxWidth = _this.$box.find('.th-tab-list').width();
                var absTranslateX = Math.abs(_this.translateX);
                var needCompareWidth = absTranslateX + navBoxWidth;
                var beforeWidth = 0;
                var afterWidth = 0;
                var theBiggestDistance = -(_this.$navTabs.width() - navBoxWidth);

                if(_this.translateX <= theBiggestDistance){
                    return;
                }
                
                //找出卡在右侧的li
                $navTabsLi.each(function(index, domEle){
                    var $curLi = $(domEle);

                    afterWidth += $curLi.outerWidth(true);

                    if(needCompareWidth >= beforeWidth && needCompareWidth < afterWidth){  //位置卡在中间
                        return false;
                    }

                    beforeWidth += $curLi.outerWidth(true);
                });

                _this.translateX = -(afterWidth - navBoxWidth);
                _this.$navTabs.css(getStyle(_this.translateX));
            });
            //点击 最右
            _this.$box.on('click', '.btn-right-last', function(){
                var $navTabsLi = _this.$box.find('.nav-tabs li');

                //移动到ul中的最后一个li
                _this.translateX = -(_this.$navTabs.width() - _this.$box.find('.th-tab-list').width());
                _this.$navTabs.css(getStyle(_this.translateX));
            });
            //点击 查看全部
            _this.$box.on('click', '.btn-view-all', function(){
                viewAllTabsVo.show();
            });
        }
    };

    return NavScrollVo;
})();
var navScrollVo  = new NavScrollVo('.th-nav-tabs');
/** NavScrollVo end */
    
/** ViewAllTabsVo start */
var ViewAllTabsVo = (function(){
    function ViewAllTabsVo(box){
        if(this instanceof ViewAllTabsVo){
            this.$box= $(box);

            this.initView();
        }else{
            return new ViewAllTabsVo(box);
        }
    }

    ViewAllTabsVo.prototype = {
        constructor: ViewAllTabsVo,
        initView: function(){
            this.bindClickPage();
            this.bindClickTab();
            this.bindClickCloseAll();
            this.bindClickCloseTab();
        },
        addIframe: function(options){
            var _this = this;

            //先判断对应的tab-item是否存在
            var existFlag = false;
            _this.$box.find('.all-tabs-box .tab-item').each(function(index, domEle){
                var domEleId = $(domEle).attr('tab-item-id');
                if(options.id === domEleId){
                    existFlag = true; //存在
                    return false;
                }
            });
            if(existFlag){  //如果存在，则不添加iframe
                return;
            }

            var tempStr = '\
                <div class="tab-item" tab-item-id="'+options.id+'">\
                    <div class="tab-iframe-box">\
                        <iframe class="tab-iframe" frameborder="0" border="0" marginwidth="0" marginheight="0"></iframe>\
                    </div>\
                    <div class="tab-mask"></div>\
                    <span class="status">当前页面</span>\
                    <div class="tab-title">'+options.title+'</div>\
                    <div class="tab-close"></div>\
                </div>\
            ';
            _this.$box.find('.all-tabs-box').append(tempStr);
        },
        removeIframe: function(tabItemId){
            this.$box.find('.all-tabs-box .tab-item[tab-item-id='+tabItemId+']').remove();
        },
        fillData: function(){
            var _this = this;
            var $tabPaneList = $('.tab-content .tab-pane:not(#Index, #tab_home)');

            $tabPaneList.each(function(index, domEle){
                var $domEle = $(domEle);
                var tempId = $domEle.attr('id').replace('tab_', '');
                //取出其中的dom结构
                var $_document = $domEle.children('iframe').eq(0).contents();
                var $_noJsDoc = $_document.find('html').clone();
                $_noJsDoc.find("script").remove();

                //找到iframe中的html
                var $tabItem = _this.$box.find('.tab-item[tab-item-id='+tempId+']');
                var $iframe = $tabItem.find('.tab-iframe');
                var $thisIframeCon = $iframe.contents();

                //向iframe的html中填充dom结构
                $thisIframeCon.find('html').html($_noJsDoc.html());
            });
        },
        show: function(){
            this.fillData();
            this.$box.show();
        },
        hide: function(){
            this.$box.hide();
        },
        /** 点击 除了tab外的页面 消失 */
        bindClickPage: function(){
            var _this = this;

            _this.$box.on('click', function(){
                _this.hide();
            });
        },
        /** 点击 tab-item */
        bindClickTab: function(){
            var _this = this;

            _this.$box.on('click','.tab-item', function(){
                var $tabItem = $(this);
                var tabItemId = $tabItem.attr('tab-item-id');
                var index = $tabItem.parent().find('.tab-item').index($tabItem);

                $('.th-nav-tabs li[id*='+tabItemId+'] a').click();
                //移动 nav 到点击的 tab处
                navScrollVo.scrollToTab(index);

                _this.hide();
                return false;  //不冒泡
            });
        },
        /** 点击 关闭全部窗口 */
        bindClickCloseAll: function(){
            var _this = this;

            _this.$box.on('click','.btn_close_all', function(){
                $('.th-tab-list .icon-remove').click();
                _this.hide();
                _this.$box.find('.tab-item:not(.tab-item[tab-item-id=home])').remove();
                return false;  //不冒泡
            });
        },
        /** 点击 关闭tab */
        bindClickCloseTab: function(){
            var _this = this;

            _this.$box.on('click','.tab-close', function(){
                var $tabClose = $(this);
                var $tabItem = $tabClose.parents('.tab-item');
                var tabItemId = $tabItem.attr('tab-item-id');

                $('.th-nav-tabs li[id=tab_tab_'+tabItemId+'] .icon-remove').click();
                _this.hide();
                $tabItem.remove();

                return false;  //不冒泡
            });
        }
    };

    return ViewAllTabsVo;
})();
var viewAllTabsVo = new ViewAllTabsVo('.view-all-tabs');
/** ViewAllTabsVo end */
        
$(function () {
    mainHeight = $(document.body).height();
    $('.main-left,.main-right').height(mainHeight);
    $("[addtabs]").click(function () {
        addTabs({ id: $(this).attr("id"), title: $(this).attr('title'), close: true });
    });

    $(".nav-tabs").on("click", "[tabclose]", function (e) {
        id = $(this).attr("tabclose");
        closeTab(id);
    });
});
