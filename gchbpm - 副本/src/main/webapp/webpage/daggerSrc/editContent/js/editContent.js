/// <reference path="F:/tianhu/typings/typings/index.d.ts" />
var editor;
$(document).ready(function(){
	
	function initQiniuUploader() {
		//上传海报图片
		    var P = new QiniuJsSDK();
		    var uploaderPic = P.uploader({
		        runtimes: 'html5,flash,html4',      // 上传模式，依次退化
		        browse_button: 'posterPic_main',         // 上传选择的点选按钮，必需
		        // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
		         uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
		        get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
		        domain: domain,     // bucket域名，下载资源时用到，必需
		        container: 'container_pic',             // 上传区域DOM ID，默认是browser_button的父元素
		        // max_file_size: '800kb',             // 最大文件体积限制
		        flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
		        max_retries: 3,                     // 上传失败最大重试次数
		        dragdrop: true,                     // 开启可拖曳上传
		        drop_element: 'container_pic',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
		        chunk_size: '4mb',                  // 分块上传时，每块的体积
                auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
                resize: {
                    quality: 90,  //需要指定这个参数
                    compressSize: 70  //单位KB，不要写单位；预期的压缩尺寸
                },
		        filters : {
		            // max_file_size : '800kb',
		            prevent_duplicates: false,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
		            mime_types: [
		                {title : "Image files", extensions : "jpg,png,jpeg,bmp"} // 限定jpg,jpeg,png等后缀上传
		            ]
		        },
		        init: {
		            'FilesAdded': function(up, files) {
		                plupload.each(files, function(file) {
		                    // 文件添加进队列后，处理相关的事情
		                });
		            },
		            'BeforeUpload': function(up, file) {
		                   // 每个文件上传前，处理相关的事情
		            },
		            'UploadProgress': function(up, file) {
		                   // 每个文件上传时，处理相关的事情
		            },
		            'FileUploaded': function(up, file, info) {
		                   // 查看简单反馈
		                   var domain = up.getOption('domain');
		                   var res = $.parseJSON(info);
		                   var sourceLink = domain + res.key; //获取上传成功后的文件的Url
		                   
		                   var oMyForm = new FormData();
		                   var curSortId = $('#container_pic').parents('li[sort_id]').attr('sort_id');
//		                   var curSortId = $('li .pic_item.add_pic').attr('sort_id');
	                       oMyForm.append("imgPath", sourceLink);
	                       oMyForm.append("classId", curSortId);
	                       $.ajax({
	                           type : "POST",
	                           url : "tSmsPicInfoController.do?uploadClassPic",
	                           data : oMyForm,
	                           async : true,
	                           cache : false,
	                           contentType : false,
	                           processData : false,
	                           dataType : "json",
	                           success : function(data) {
	                               console.log(data);
	                               if(data.success){
	                            	   picSelectVo.picPageNo = 1;  //进入图片页面时，重置页码
	                            	   picSelectVo.myPicMoreData = true;  //进入图片页面时，重置是否有数据
	                            	   picSelectVo.searchSortImg(curSortId);
	                               }
	                           }
	                       });
		                   
		            },
		            'Error': function(up, err, errTip) {
		                   //上传出错时，处理相关的事情
		            	alert(errTip);
		            },
		            'UploadComplete': function() {
		                   //队列文件处理完毕后，处理相关的事情
		            },
		            'Key': function(up, file) {
		                // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
		                // 该配置必须要在unique_names: false，save_key: false时才生效
	 	                var name = (new Date()).Format("MMddhhmmss")+getRandomStr();
	 	                var n = file.name.lastIndexOf(".");
	 	            	name += file.name.substring(n);
		                var time = new Date().Format("yyyyMMdd");
		                var key = "posterPic/"+time+"/"+name;
		                return key
		            }
		        }
		    });
	}

	//获取随机ID
	function getRandomStr(){
		  var str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		  var n = 8, s = "";
		  for(var i = 0; i < n; i++){
		      var rand = Math.floor(Math.random() * str.length);
		      s += str.charAt(rand);
		  }
		  return s;
	}
	
    var tempPicSrc;
    /** 模板数据 */
    var templateObj = {
        /** 图片模板 */
        tempPic: function(){
            return '\
                <div template_type="tempPic" class="template_item">\
                    <div class="mobile_item img_box">\
                        <img class="content_pic" src="'+tempPicSrc+'" alt="">\
                        <button class="btn_mask"></button>\
                    </div>\
                    <div class="menu_bar">\
                        <img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>\
                        <img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>\
                        <div class="click_move">\
                            <img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">\
                            <img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">\
                            <img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">\
                            <img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">\
                        </div>\
                    </div>\
                </div>\
            '
        },
        /** 商品模板 */
        tempGoods: function(){
            return '\
                <div template_type="tempGoods" class="template_item">\
                    <div class="mobile_item goods_box">\
                        <div class="goods_item">\
                            <div class="goods_pic_box">\
                                <img class="goods_pic" src="webpage/daggerSrc/common/img/bg_goods.png" alt="">\
                                <input type="hidden" class="goods_id" >\
                            </div>\
                            <div class="goods_detail_box">\
                                <div class="goods_title">商品名</div>\
                                <div class="goods_brand">品牌</div>\
                                <div class="goods_price_box">\
                                    <div class="goods_curprice">¥69</div><div class="goods_oldprice">¥119</div>\
                                </div>\
                                <img class="hint_buy" src="webpage/daggerSrc/editContent/img/bg_buy_hint.png"/>\
                            </div>\
                        </div>\
                        <button class="btn_mask"></button>\
                    </div>\
                    <div class="menu_bar">\
                        <img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>\
                        <img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>\
                        <div class="click_move">\
                            <img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">\
                            <img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">\
                            <img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">\
                            <img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">\
                        </div>\
                    </div>\
                </div>\
            '
        },
        /** 标题模板 */
        tempTitle: function(){
            return '\
                <div template_type="tempTitle" class="template_item">\
                    <div class="mobile_item title_box">\
                        <div th_editable class="info_title">请输入资讯标题</div>\
                    </div>\
                    <div class="menu_bar">\
                        <img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>\
                        <img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>\
                        <div class="click_move">\
                            <img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">\
                            <img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">\
                            <img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">\
                            <img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">\
                        </div>\
                    </div>\
                </div>\
            '
        },
        /** 作者模板 */
        tempAuthor: function(){
            return '\
                <div template_type="tempAuthor" class="template_item">\
                    <div class="mobile_item author_box">\
                        <div th_editable class="info_author">作者</div>\
                    </div>\
                    <div class="menu_bar">\
                        <img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>\
                        <img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>\
                        <div class="click_move">\
                            <img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">\
                            <img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">\
                            <img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">\
                            <img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">\
                        </div>\
                    </div>\
                </div>\
            '
        },
        /** 内容模板 */
        tempContent: function(){
            return '\
                <div template_type="tempContent" class="template_item">\
                    <div class="mobile_item paragraph_box">\
                        <div th_editable class="info_paragraph">请输入正文内容</div>\
                    </div>\
                    <div class="menu_bar">\
                        <img  class="handle_bar" src="webpage/daggerSrc/editContent/img/handle_bar.png"/>\
                        <img  class="close_template" src="webpage/daggerSrc/editContent/img/bg_close_template.png"/>\
                        <div class="click_move">\
                            <img class="btn btn_topside" src="webpage/daggerSrc/editContent/img/topside.png" alt="">\
                            <img class="btn btn_top" src="webpage/daggerSrc/editContent/img/top.png" alt="">\
                            <img class="btn btn_bottom" src="webpage/daggerSrc/editContent/img/bottom.png" alt="">\
                            <img class="btn btn_bottomside" src="webpage/daggerSrc/editContent/img/bottomside.png" alt="">\
                        </div>\
                    </div>\
                </div>\
            '
        },
        /** 布局模版 */
        tempLayout: function($curTemp){
            return $curTemp.html();
        }
    };

    /** 实例化编辑器 */
    //  editor = UE.getEditor('ueditor_box', {
    //     toolbars: [
    //         ['undo', 'redo', 'fontsize', 'lineheight', 'forecolor', 
    //         'bold', 'italic', 'underline','justifyleft', 'justifycenter', 'justifyright']
    //     ],
    //     // toolbars: [],
    //     //focus时自动清空初始化时的内容  
    //     autoClearinitialContent:true,  
    //     //关闭字数统计  
    //     wordCount:false,  
    //     //关闭elementPath  
    //     elementPathEnabled:false,  
    //     //初始化编辑器宽度，默认1000
    //     initialFrameWidth: '100%', 
    //     //默认的编辑区域高度  
    //     initialFrameHeight:320,
    //     'enterTag':'',
    //     tableDragable: false,
    //     pasteplain: true,
    //     catchremoteimageenable: true

    // });

    /** 编辑器的document的jquery对象 */
    // var $editorDocument = null;
    // var $editorBody = null;
    /** 上下拖动 */
    var sortable = null;

    /** 编辑器 准备 回调 */
    // editor.addListener( 'ready', function(  ) {
    //     // editor.execCommand( 'focus' ); //编辑器家在完成后，让编辑器拿到焦点
    //     $editorDocument = $(editor.document);
    //     $editorBody = $editorDocument.find('body');
    //     /**点击编辑器中的商品和图片 */
    //     // $editorBody
    //     //     .on('click', '.template_item[template_type="tempPic"]', function(){
    //     //     	changeCover($(this));
    //     //     })
    //     //     .on('click', '.template_item[template_type="tempGoods"]', function(){
    //     //         changeGoods($(this));
    //     //     })
    //     //     .on('click', '.close_template', function(e){
    //     //     	var aim;
    //     //     	if(e.srcElement){
	//     //             aim=e.srcElement;
    //     //        }else{
    //     //          aim=e.target;
    //     //         }
    //     //         if(aim == $(this)[0]){
    //     //         	$(this).parents('.mobile_item').remove();
    //     //         }
    //     //         e.stopPropagation(); 
    //     //     });

    //     //添加禁止向ueditor文本框中拖拽内容（不支持IE8）
    //     // editor.document.body.draggable = false;
    //     // editor.document.body.setAttribute("ondragstart","return false");
    //     // editor.document.body.setAttribute("ondragenter","event.dataTransfer.dropEffect='none'; event.stopPropagation(); event.preventDefault();");
    //     editor.document.body.setAttribute("ondragover","event.dataTransfer.dropEffect='none';event.stopPropagation(); event.preventDefault();");
    //     editor.document.body.setAttribute("ondrop","event.dataTransfer.dropEffect='none';event.stopPropagation(); event.preventDefault();");
    //     /** 上下拖动 */
    //     // sortable = Sortable.create($editorBody[0], {
    //     //     draggable: '.template_item',
    //     //     dragClass: '.template_item',
    //     //     handle: '.handle_bar'
    //     // });
        

        
    //     /** 编辑页面加载H5内容*/
    //     // if(typeof contextHtml !== 'undefined' && contextHtml !== ''){
    //     // 	editor.execCommand('inserthtml', contextHtml);//tPoster-update.jsp页面取的全局变量
    //     // }


    // } );
    

    /** 工具对象 */
    var util = (function(){
        /** 获取不重复的guid */
        var getGUID = function() {
            function S4() {
                return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
            }
            return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
        };

        /** rgb --> hex */
        var rgb2hex = function rgb2hex(rgb) {
            rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
            function hex(x) {
            return ("0" + parseInt(x).toString(16)).slice(-2);
            }
            return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
        }

        return {
            getGUID: getGUID,
            rgb2hex: rgb2hex
        };
    })();

    /** TopMenuVo start 顶部按钮*/
    var TopMenuVo = (function(){
        function TopMenuVo(box){
            if(this instanceof TopMenuVo){
                this.$box= $(box);
                this.$btnSave = this.$box.find('.btn_save');
                this.$btnCheck = this.$box.find('.btn_check');
                this.$btnDone = this.$box.find('.btn_done');
                this.$alertBox = this.$box.find('.alert_box');
                this.$btnCancel = this.$box.find('.btn_cancel');
                this.$btnConfirm = this.$box.find('.btn_confirm');

                this.initView();
            }else{
                return new TopMenuVo(box);
            }
        }

        TopMenuVo.prototype = {
            constructor: TopMenuVo,
            initView: function(){
                this.bindClickConfirm();
                this.bindClickCancel();
            },
            /** 关闭 alert_box */
            bindClickCancel: function(){
                var _this = this;
                _this.$btnCancel.click(function(){
                    _this.$alertBox.hide();
                });
            },
            /** 关闭 alert_box */
            bindClickConfirm: function(){
                var _this = this;
                _this.$btnConfirm.click(function(){
                    _this.$alertBox.hide();
                });
            }
        };

        return TopMenuVo;
    })();
    /** TopMenuVo end */

    /** CoverVo start */
    var CoverVo = (function(){
        function CoverVo(box){
            if(this instanceof CoverVo){
                this.$box= $(box);
                this.$imgBox = this.$box.find('.img_box');
                this.$coverPic = this.$box.find('.cover_pic');
                this.$inputTitle = this.$box.find('.cover_title');
                
                this.initView();
            }else{
                return new CoverVo(box);
            }
        }

        CoverVo.prototype = {
            constructor: CoverVo,
            initView: function(){
            },
            /** 点击封面 更换封面 */
            bindClickCover: function(editContentVo, rightMenuVo){
                var _this = this;

                _this.$imgBox.on('click', function(){
                    editContentVo.getAllTempItem().removeClass('active');
                    $('div[th_editable]').removeClass('active');
                    $('div[key_usable]').removeClass('active');
                    $('.change_img_item').removeClass('active');
                    $('.carousel_box').slick('slickPlay');

                    $(this).addClass('active');
                    rightMenuVo.findDom('.btn_pic')
                        .click()
                        .removeClass('active');  //将图片 按钮上的active 去除，作为标记
                });
            },
            /** 获取标题内容 */
            getTitle: function(){
                var tempStr = this.$inputTitle.val();
                return $.trim(tempStr);
            },
            /** 获取封面图路径 */
            getPicUrl: function(){
                return this.$coverPic.attr('src');
            },
            
            setTitle:function(title){
            	this.$inputTitle.val(title);
            },
            setPicUrl:function(picUrl){
            	this.$coverPic.attr('src',picUrl)
            },
            /** 判断封面图的active状态 */
            isActiveCover: function(){
                return $('.th_cover .img_box.active').length > 0;
            },
            /** 修改封面图的src */
            changeActiveCover: function(imgSrc){
                $('.th_cover .img_box.active').find('.cover_pic').attr('src', imgSrc);
            }
        };

        return CoverVo;
    })();
    /** CoverVo end */

    /** RightMenuVo start 右侧菜单 */
    var RightMenuVo = (function(){
        function RightMenuVo(box){
            if(this instanceof RightMenuVo){
                this.$box= $(box);
                this.$leftBtns = this.$box.find('.left_btn');
                this.$btnText = this.$box.find('.btn_text');
                this.$btnPic = this.$box.find('.btn_pic');
                this.$btnGoods = this.$box.find('.btn_goods');
                this.$menuInfo = this.$box.find('.menu_info');

                this.initView();
            }else{
                return new RightMenuVo(box);
            }
        }

        RightMenuVo.prototype = {
            constructor: RightMenuVo,
            initView: function(){
                this.bindSwitchActive();
            },
            /** 查找对应的dom对象 */
            findDom: function(domStr){
                return this.$box.find(domStr);
            },
            /** 切换 按钮状态 */
            bindSwitchActive: function(){
                var _this = this;

                _this.$leftBtns.each(function(index, btn){
                    var curBtn = $(btn);
                    curBtn.click(function(){
                        if(curBtn.hasClass('active')){
                            return;
                        }
                        _this.$leftBtns.removeClass('active');
                        curBtn.addClass('active');

                        var curTempMenu = _this.$menuInfo.find('.' + curBtn.attr('temp_tag'));
                        curTempMenu.show();
                        curTempMenu.siblings().hide();
                    });
                });
            },
            /** 点击图片模板中的图片 */
            bindClickTempPic: function(){
                
            },
            /** 点击模板 */
            bindClickTemplate: function(editContentVo, coverVo){
                var _this = this;

                _this.$box.on('click', '.template', function(){
                    //ueditor 逻辑 start
                    //光标移动到最后
                    // editor.focus(true);
                    //最后的一个p的高度设置为0，即可以隐藏，又可以避免ueditor不能自动增加高度
                    // $editorBody.children('p').height(0);
                    // var $curTemp = $(this);
                    // var tempName = $curTemp.attr('temp_tag');
                    // var tempStr = templateObj[tempName];
                    // var tempGUID = util.getGUID();
                    // tempStr = '<div template_type="'+tempName+'" template_guid="'+tempGUID+'" class="template_item">' + tempStr + '</div>';
                    // editor.execCommand('inserthtml', tempStr);
                    //让编辑器失去焦点
                    // editor.blur();
                    //ueditor 逻辑 end

                    //直接添加，使用原生的contenteditable编辑
                    var $curTemp = $(this);
                    var tempName = $curTemp.attr('temp_tag');
                    var tempStrFn = templateObj[tempName];

                    if(tempName === 'tempPic'){
                        tempPicSrc = $curTemp.find('img').attr('src');
                    }

                    if(_this.$box.find('.tempPic').is(':visible') && !_this.$btnPic.hasClass('active')){  //如果图片选择区显示，但图片按钮不是active状态，则表示封面图或者背景图更换
                        if(coverVo.isActiveCover()){
                            coverVo.changeActiveCover(tempPicSrc);
                        }else if(editContentVo.isActiveBg()){
                            editContentVo.changeActiveBg(tempPicSrc);
                        }else if(editContentVo.isActivePic()){
                            editContentVo.changeActivePic(tempPicSrc);
                        }else if(editContentVo.isActiveChangeImg()){
                            editContentVo.changeImgSrc(tempPicSrc);
                        }   
                        return;
                    }

                    editContentVo.appendDom(tempStrFn($curTemp));
                    //滚动到底部
                    $(document).scrollTop($(document).height()-$(window).height());
                });
            }
        };
        return RightMenuVo;
    })();
    /** RightMenuVo end */

    /** EditContentVo 内容编辑区 start */
    var EditContentVo = (function(){
        function EditContentVo(box){
            if(this instanceof EditContentVo){
                this.$box= $(box);
                this.draggableArr = [];   //div[th_editable]拖动对象数组
                this.tempCopyDom = null;

                this.initView();
            }else{
                return new EditContentVo(box);
            }
        }

        EditContentVo.prototype = {
            constructor: EditContentVo,
            initView: function(){
                this.bindClickBtnDelete();
                this.bindClickBtnSave();
                this.initEditContent();
                this.bindClickMove();
            },
            /** 初始化 回现 */
            initEditContent: function(){
                var _this = this;
                //初始化所有可以移动的div[th_editable]
                var draggableObj = _this.initDraggableDiv(_this.$box);
                _this.draggableArr.push(draggableObj);

                //初始化所有的轮播图
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
            /** 点击 移动 */
            bindClickMove: function(){
                this.bindClickTop();
                this.bindClickTopside();
                this.bindClickBottom();
                this.bindClickBottomside();
            },
            /** 点击 上移一步 */
            bindClickTop: function(){
                var _this = this;

                _this.$box.on('click', '.btn_top', function(){
                    var $curBtn = $(this);
                    var $allTemp = _this.getAllTempItem();
                    var $curTemp = $curBtn.parents('.template_item');
                    var tempPosi = $allTemp.index($curTemp);

                    if(!(tempPosi === 0)){  //如果不是位于第一个位置，则可以上移
                        $curTemp.insertBefore($curTemp.prev());
                    }

                    return false;  //不冒泡
                });
            },
            /** 点击 置顶 */
            bindClickTopside: function(){
                var _this = this;

                _this.$box.on('click', '.btn_topside', function(){
                    var $curBtn = $(this);
                    var $allTemp = _this.getAllTempItem();
                    var $curTemp = $curBtn.parents('.template_item');
                    var tempPosi = $allTemp.index($curTemp);

                    if(!(tempPosi === 0)){  //如果不是位于第一个位置，则可以上移
                        $curTemp.insertBefore($allTemp.first());
                    }
                    return false;  //不冒泡
                });
            },
            /** 点击 下移一步 */
            bindClickBottom: function(){
                var _this = this;

                _this.$box.on('click', '.btn_bottom', function(){
                    var $curBtn = $(this);
                    var $allTemp = _this.getAllTempItem();
                    var $curTemp = $curBtn.parents('.template_item');
                    var tempPosi = $allTemp.index($curTemp);
                    
                    if(!(tempPosi === ($allTemp.length - 1))){  //如果不是位于第一个位置，则可以下移
                        $curTemp.insertAfter($curTemp.next());
                    }

                    return false;  //不冒泡
                });
            },
            /** 点击 置底 */
            bindClickBottomside: function(){
                var _this = this;

                _this.$box.on('click', '.btn_bottomside', function(){
                    var $curBtn = $(this);
                    var $allTemp = _this.getAllTempItem();
                    var $curTemp = $curBtn.parents('.template_item');
                    var tempPosi = $allTemp.index($curTemp);
                    
                    if(!(tempPosi === ($allTemp.length - 1))){  //如果不是位于第一个位置，则可以下移
                        $curTemp.insertAfter($allTemp.last());
                    }

                    return false;  //不冒泡
                });
            },
            getAllTempItem: function(){
                return this.$box.find('.template_item');
            },
            /** 对th_editable进行处理 只能粘贴文本，不能携带标签 */
            dealPast: function($dom){
                $dom.each(function() {
                    // 干掉IE http之类地址自动加链接
                    try {
                        document.execCommand("AutoUrlDetect", false, false);
                    } catch (e) {}
                    
                    $(this).on('paste', function(e) {
                        e.preventDefault();
                        var text = null;
                    
                        if(window.clipboardData && clipboardData.setData) {
                            // IE
                            text = window.clipboardData.getData('text');
                        } else {
                            text = (e.originalEvent || e).clipboardData.getData('text/plain') || prompt('在这里输入文本');
                        }
                        if (document.body.createTextRange) {    
                            if (document.selection) {
                                textRange = document.selection.createRange();
                            } else if (window.getSelection) {
                                sel = window.getSelection();
                                var range = sel.getRangeAt(0);
                                
                                // 创建临时元素，使得TextRange可以移动到正确的位置
                                var tempEl = document.createElement("span");
                                tempEl.innerHTML = "&#FEFF;";
                                range.deleteContents();
                                range.insertNode(tempEl);
                                textRange = document.body.createTextRange();
                                textRange.moveToElementText(tempEl);
                                tempEl.parentNode.removeChild(tempEl);
                            }
                            textRange.text = text;
                            textRange.collapse(false);
                            textRange.select();
                        } else {
                            // Chrome之类浏览器
                            document.execCommand("insertText", false, text);
                        }
                    });
                });
            },
            /** 获取所有的编辑内容 */
            getEditContent: function(){
                return this.$box.html();
            },
            /** 移除选中的布局模版的背景图片 */
            removeActiveLayoutBg: function(){
                $('.template_item[template_type="tempLayout"].active').find('.img_bg').attr('src', 'webpage/daggerSrc/common/img/cover_pic.png');
            },
            /** 插入dom节点 */
            appendDom: function(domEle){
                var _this = this;

                _this.$box.append(domEle);

                var $lastDomEle = _this.$box.find('.template_item').last();
                //判断是否是布局模版
                if($lastDomEle.attr('template_type') === 'tempLayout'){
                    var draggableObj = _this.initDraggableDiv($lastDomEle);
                    _this.draggableArr.push(draggableObj);

                    //判断是否是布局模板中的轮播
                    if($lastDomEle.find('.temp_carousel').length > 0){
                        var $carouselBox = $lastDomEle.find('.carousel_box');

                        _this.replaceOldCarousel($carouselBox);
                        $carouselBox = $lastDomEle.find('.carousel_box');

                        $carouselBox.slick({
                            accessibility: true,
                            autoplay: true,
                            dots: true
                        });
                        $carouselBox.find('.slick-prev, .slick-next').css('display', 'none');
                    }
                }

                //判断是否含有th_editable
                if($lastDomEle.find('div[th_editable]').length > 0){
                    _this.dealPast($lastDomEle.find('div[th_editable]'));
                }
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
                        <div carousel_id="" class="carousel_box">\
                            '+tempLiStr+'\
                        </div>\
                    ';
                    $curCarouselBox.before(tempBoxStr);
                    $curCarouselBox.remove();
                });
            },
            /** 点击需要更换的图片 */
            blindClickChangeImg: function(rightMenuVo){
                var _this = this;

                _this.$box.on('click', '.change_img_item', function(){
                    var $curImgItem = $(this);
                    var $curCarouselBox = $curImgItem.parents('.carousel_box');
                    var $curTempItem = $curImgItem.parents('.template_item');

                    _this.$box.find('.change_img_item').removeClass('active');
                    $curImgItem.addClass('active');
                    if(!$curTempItem.hasClass('active')){
                        $curTempItem.click();
                    }

                    $curCarouselBox.slick('slickPause');  //停止轮播图

                    rightMenuVo.findDom('.btn_pic')
                        .click()
                        .removeClass('active');  //将图片 按钮上的active 去除，作为标记

                    return false;
                });
            },
            /** 点击模版 */
            bindClickTemplateItem: function(rightMenuVo){
                var _this = this;

                _this.$box.on('click', '.template_item', function(){
                    var $curTemplateItem = $(this);
                    var tempType = $curTemplateItem.attr('template_type');

                    $curTemplateItem.siblings().removeClass('active');
                    $('.th_cover .img_box').removeClass('active');
                    $('div[th_editable]').removeClass('active');
                    $('div[key_usable]').removeClass('active');
                    $('.change_img_item').removeClass('active');
                    $('.carousel_box').slick('slickPlay');
                    $curTemplateItem.addClass('active');
                    
                    switch(tempType){
                        case 'tempGoods':
                            changeGoods($(this));
                            break;
                        case 'tempPic':
                            rightMenuVo.findDom('.btn_pic')
                                .click()
                                .removeClass('active');  //将图片 按钮上的active 去除，作为标记
                            break;
                        default:
                    }

                    return false;
                });
            },
            /** 点击 删除模版按钮 */
            bindClickBtnDelete: function(){
                var _this = this;

                _this.$box.on('click', '.template_item .close_template', function(){
                    var $curBtn = $(this);

                    layer.confirm("确定要删除吗？", {
                            btn: ['确定','关闭'], //按钮
                            title :'提示信息'
                        }, function(){
                            $curBtn.parents('.template_item').remove();
                            layer.closeAll('dialog');	//关闭弹出的确认框
                        }, function(){

                        });  

                    return false;
                });
            },
            /** 点击 保存模版按钮 */
            bindClickBtnSave: function(){
                var _this = this;

                _this.$box.on('click', '.template_item .save_pic', function(){
                    var $curBtn = $(this);
                    var saveDom = $curBtn.parents('.template_item').find('.layout_content')[0];
                    var curTime = new Date().getTime();
                    domtoimage
                        .toJpeg(saveDom, { quality: 1 })
                        .then(function (dataUrl) {
                            var link = document.createElement('a');
                            link.download = curTime+'.jpeg';
                            link.href = dataUrl;
                            link.click();
                        });

                    return false;
                });
            },
            /** 复制编辑div及其事件 */
            copyUsableDiv: function(){
                var _this = this;

                var $usableDiv = _this.$box.find('div[key_usable].active');
                var $editableDiv = _this.$box.find('div[th_editable].active');
                if(!$editableDiv.is(':focus')){  //没有处于编辑状态
                    _this.tempCopyDom = $usableDiv.clone();
                }
            },
            /** 粘贴到指定位置 */
            pasteUsableDiv: function(){
                var _this = this;

                var $editableDiv = _this.$box.find('div[th_editable].active');
                if($editableDiv.is(':focus')){  //处于编辑状态
                    return;
                }
                
                var $pasteDom = _this.tempCopyDom.clone();  
                var $usableDiv = _this.$box.find('div[key_usable].active');
                $usableDiv.after($pasteDom);
                var left = $usableDiv.css('left');
                var top = $usableDiv.css('top');
                $pasteDom.css({
                    "left": parseInt(left, 10) + 20,
                    "top": parseInt(top, 10) + 20
                });
                $pasteDom.removeClass('active');
                $pasteDom.find('div[th_editable]').removeClass('active');
                var draggableObj = $pasteDom.draggabilly({
                    containment: true
                });
                _this.draggableArr.push(draggableObj);
            },
            /** 删除编辑div及其事件 */
            deleteUsableDiv: function(){
                var _this = this;

                var $usableDiv = _this.$box.find('div[key_usable].active');
                var $editableDiv = _this.$box.find('div[th_editable].active');
                if(!$editableDiv.is(':focus')){  //没有处于编辑状态
                    $usableDiv.remove();
                }
            },
            /** 为编辑去添加相应的事件 */
            bindDivEditableEvent: function(obj){
                var _this = this;

                _this.$box.on('focus', 'div[th_editable]', function(){
                    var $curDiv = $(this);

                    $curDiv.css({
                        "cursor": "text"
                    });

                    //获取焦点时，禁止移动
                    $.each(_this.draggableArr, function(index, $draggable){
                        $draggable.draggabilly('disable');
                    });
                });  

                _this.$box.on('blur', 'div[th_editable]', function(){
                    var $curDiv = $(this);
                    //因为改变光标位置的时候，会触发一次blur和focus，但最后并没有blur，用定时器可以做区别
                    setTimeout(function() {
                        if(!$curDiv.is(':focus')){
                            // $('div[th_editable]').removeClass('active');
                            // $('div[key_usable]').removeClass('active');
                            $('div[th_editable]').removeAttr('contenteditable');

                            $curDiv.css({
                                "cursor": "move"
                            });

                            //失去焦点时，可以移动
                            $.each(_this.draggableArr, function(index, $draggable){
                                $draggable.draggabilly('enable');
                            });
                        }
                    }, 100);
                });

                _this.$box.on('dblclick', 'div[th_editable]', function(event){  //双击编辑
                    var $curDiv = $(this);

                    //当前编辑区
                    $('div[th_editable]').removeAttr('contenteditable');
                    $curDiv.attr('contenteditable', 'true');
                    $curDiv.focus();

                    return false;
                });

                _this.$box.on('click', 'div[th_editable]', function(event){  //单击选中
                    var $curDiv = $(this);

                    //当前templateItem
                    var $curTemplateItem = $curDiv.parents('.template_item');
                    $curTemplateItem.siblings().removeClass('active');
                    $('.th_cover .img_box').removeClass('active');
                    $curTemplateItem.addClass('active');

                    //当前编辑区
                    $('div[th_editable]').removeClass('active');
                    $('div[key_usable]').removeClass('active');
                    $('.change_img_item').removeClass('active');
                    $('.carousel_box').slick('slickPlay');
                    $curDiv.addClass('active');
                    $curDiv.parents('div[key_usable]').addClass('active');

                    //出现字体菜单
                    $('.th_menu .btn_text').click();

                    //设置字体
                    if(typeof obj.setFontSpec === 'function'){
                        obj.setFontSpec($curDiv);
                    }

                    return false;
                });
            },
            /** 改变字体颜色 */
            changeFontColor: function(hexColor){
                $('div[th_editable].active').css({
                    "color": hexColor
                });
            },
            /** 改变字体大小 */
            changeFontSize: function(fontSize){
                $('div[th_editable].active').css({
                    "font-size": fontSize
                });
            },
            /** 改变字体大小 */
            changeLineHeight: function(lineHeight){
                $('div[th_editable].active').css({
                    "line-height": lineHeight
                });
            },
            /** 点击布局模版背景图 */
            bindClickLayoutImgBg: function(obj){
                var _this = this;

                _this.$box.on('click', '.img_bg', function(){
                    var $curImgBg = $(this);
                    _this.$box.find('.img_bg').removeClass('active');
                    $curImgBg.addClass('active');
                    //显示模版 --> 当前模版
                    $('.th_menu .btn_template').click();
                    $('.th_menu .tab_cur_template').click();

                    if(typeof obj.setBgImg === 'function'){
                        obj.setBgImg($curImgBg.attr('src'));
                    }
                });
            },
            /** 判断背景图的active状态 */
            isActiveBg: function(){
                return this.$box.find('.template_item[template_type="tempLayout"].active .img_bg.active').length > 0;
            },
            /** 改变背景图的src */
            changeActiveBg: function(imgSrc){
                this.$box.find('.template_item[template_type="tempLayout"].active .img_bg.active').attr('src', imgSrc);
            },
            /** 判断 图片的active状态 */
            isActivePic: function(){
                return this.$box.find('.template_item[template_type="tempPic"].active').length > 0;
            },
            /** 改变图片的src */
            changeActivePic: function(imgSrc){
                this.$box.find('.template_item[template_type="tempPic"].active').find('.content_pic').attr('src', imgSrc);
            },
            /** 判断 改变图片的active状态 */
            isActiveChangeImg: function(){
                return this.$box.find('.change_img_item.active').length > 0;
            },
            /** 修改src */
            changeImgSrc: function(imgSrc){
                var $curActiveItem = this.$box.find('.change_img_item.active');
                //判断是否是轮播图更改src
                if($curActiveItem.parents('.carousel_box').length > 0){
                    var $curCarouselBox = $curActiveItem.parents('.carousel_box');
                    var slickCount = $curCarouselBox.find('.change_img_item').length - 2;
                    var curIndex = $curCarouselBox.slick('slickCurrentSlide');
                    if(curIndex === 0){  //轮播图的原来的第一个与 处理后的最后一个的src相同
                        $curCarouselBox.find('.change_img_item').last().find('.change_img').attr('src', imgSrc);
                    }else if(curIndex === slickCount - 1){ //轮播图的原来的最后一个与  处理后的第一个的src相同
                        $curCarouselBox.find('.change_img_item').first().find('.change_img').attr('src', imgSrc);
                    }
                }
                $curActiveItem.find('.change_img').attr('src', imgSrc);
            },
            /** 拖动布局模版中的文字
             * 返回draggable对象
            */
            initDraggableDiv: function($layoutDomEle){
                var $draggableDom = $layoutDomEle.find('div[th_draggable]');
                var $draggableObj = $draggableDom.draggabilly({
                    containment: true
                });
                $draggableDom.each(function(index, domEle){
                    $(domEle).css({
                        "top": $(domEle).css('top'),
                        "left": $(domEle).css('left')
                    });
                });
                return $draggableObj;
            }
        };

        return EditContentVo;
    })();
    /** EditContentVo end */

    /** FontSpecVo 文字处理 start */
    var FontSpecVo = (function(){
        function FontSpecVo(box){
            if(this instanceof FontSpecVo){
                this.$box= $(box);
                this.$colorPicker = this.$box.find('.colorpicker');
                this.editContentVo = null;

                this.initView();
            }else{
                return new FontSpecVo(box);
            }
        }

        FontSpecVo.prototype = {
            constructor: FontSpecVo,
            initView: function(){
                this.initColorPicker();
                this.initTextSpec();
                this.initDropDownMenu();
                this.initFontIcon();
            },
            /** 当点击可编辑的div后，字体设置发生相应的变化 */
            setFontSpec: function($curDiv){
                var _this = this;
                var rgbColor = $curDiv.css('color');
                var fontSize = $curDiv.css('font-size');
                var lineHeight = $curDiv.css('line-height');
                var fontWeight = $curDiv.css('font-weight');
                var fontItalic = $curDiv.css('font-style');
                var fontUnderline = $curDiv.css('text-decoration');
                var textAlign = $curDiv.css('text-align');

                _this.changePickerBgColor(util.rgb2hex(rgbColor));
                _this.setFontSize(fontSize);
                _this.setLineHeight(lineHeight, fontSize);
                _this.setFontWeight(fontWeight);
                _this.setFontItalic(fontItalic);
                _this.setFontUnderline(fontUnderline);
                _this.setTextAlign(textAlign);
                
                _this.hideSpecMask();
            },
            /** 设置字体大小 */
            setFontSize: function(fontSize){
                this.$box.find('div.fontsize input.font_size').val(parseInt(fontSize, 10));
            },
            /** 设置行高 */
            setLineHeight: function(lineHeight, fontSize){
                var temp = parseInt(lineHeight, 10) / parseInt(fontSize, 10);
                this.$box.find('div.lineheight input.line_height').val(temp + '倍');
            },
            /** 设置字体粗细 */
            setFontWeight: function(fontWeight){
                if(fontWeight === 'bold'){
                    this.$box.find('.icon_weight').addClass('active');
                }else{
                    this.$box.find('.icon_weight').removeClass('active');
                }
            },
            /** 设置字体倾斜 */
            setFontItalic: function(fontItalic){
                if(fontItalic === 'italic'){
                    this.$box.find('.icon_italic').addClass('active');
                }else{
                    this.$box.find('.icon_italic').removeClass('active');
                }
            },
            /** 设置下划线 */
            setFontUnderline: function(fontUnderline){
                if(fontUnderline.indexOf('underline') >= 0){
                    this.$box.find('.icon_underline').addClass('active');
                }else{
                    this.$box.find('.icon_underline').removeClass('active');
                }
            },
            /** 设置对齐方式 */
            setTextAlign: function(textAlign){
                this.$box.find('.icon[icon_tag="text-align"]').removeClass('active');
                if(textAlign === 'left'){
                    this.$box.find('.icon_paragraph_left').addClass('active');
                }else if(textAlign === 'right'){
                    this.$box.find('.icon_paragraph_right').addClass('active');
                }else{
                    this.$box.find('.icon_paragraph_center').addClass('active');
                }
            },
            /** 显示spec_mask */
            showSpecMask: function(){
                this.$box.find('.spec_mask').show();
            },
            /** 隐藏spec_mask */
            hideSpecMask: function(){
                this.$box.find('.spec_mask').hide();
            },
            /** 初始化颜色选择器 */
            initColorPicker: function(){
                var _this = this;

                _this.$colorPicker.colpick({
                    layout: 'hex',
                    onShow: function(){
                        var curColor = _this.$colorPicker.css('background-color');
                        var curHexColor = util.rgb2hex(curColor);
                        _this.$colorPicker.colpickSetColor(curHexColor);
                    },
                    onSubmit:function(hsb,hex,rgb,el) {
                        $(el).css('background-color', '#'+hex);
                        $(el).colpickHide();
                        _this.editContentVo.changeFontColor('#'+hex);
                    }
                });

                $('.colpick_hex, .edui-default').click(function(){
                    return false; //不冒泡
                });
            },
            /** 当文字编辑区域发生变化时，修改colorPicker的背景颜色 */
            changePickerBgColor: function(hexColor){
                this.$colorPicker.css('background-color', hexColor);
            },
            /** 初始化text_spec */
            initTextSpec: function(){
                this.$box.click(function(){
                    return false;  //不冒泡
                });
            },
            /** 字体大小和行高的显隐处理 */
            initDropDownMenu: function(){
                var _this = this;

                var $inputValue = _this.$box.find('.input_value');
                $inputValue.click(function(){
                    var $curMenu = $(this).siblings('.dropdown-menu');
                    if(!$curMenu.is(':visible')){
                        $curMenu.show();
                    }else{
                        $curMenu.hide();
                    }
                });

                //当input中的数据发生变化的时候，修改字体和行高
                $inputValue.on('input propertychange', function(){
                    var $curInput = $(this);
                    var floatText = parseFloat($curInput.val());
                    if($curInput.hasClass('font_size')){  //处理字体
                        _this.editContentVo.changeFontSize(floatText);
                    }else if($curInput.hasClass('line_height')){  //处理行高
                        _this.editContentVo.changeLineHeight(floatText);
                    }
                });

                var $dropdownMenu = $inputValue.siblings('.dropdown-menu');
                //点击其中一条数据之后，将数据显示在input中
                $dropdownMenu.find('a').click(function(){
                    var $a = $(this);
                    var $curInput = $a.parents('.dropdown-menu').siblings('input');
                    var text = $a.text();
                    var floatText = parseFloat(text);
                    $curInput.val(text);
                    if($curInput.hasClass('font_size')){  //处理字体
                        _this.editContentVo.changeFontSize(floatText);
                    }else if($curInput.hasClass('line_height')){  //处理行高
                        _this.editContentVo.changeLineHeight(floatText);
                    }
                    $dropdownMenu.hide();
                });
            },
            /** 字体粗细、倾斜、下划线、对齐方式 */
            initFontIcon: function(){
                var _this = this;

                _this.$box.on('click', '.icon[default_value]', function(){
                    var $curIcon = $(this);
                    var $curEditableDiv = $('div[th_editable].active');
                    var attrValue = $curIcon.attr('icon_tag');
                    var textValue = '';
                    
                    $curIcon.toggleClass('active');
                    if($curIcon.hasClass('active')){
                        textValue = $curIcon.attr('active_value');
                    }else{
                        textValue = $curIcon.attr('default_value');
                    }
                    $curEditableDiv.css(attrValue, textValue);
                });

                _this.$box.on('click', '.icon[icon_tag="text-align"]', function(){
                    var $curIcon = $(this);
                    var $curEditableDiv = $('div[th_editable].active');
                    var attrValue = $curIcon.attr('icon_tag');
                    var textValue = '';

                    if($curIcon.hasClass('active')){
                        $curIcon.removeClass('active');
                        textValue = 'center';
                    }else{
                        $curIcon.parent().find('.icon[icon_tag="text-align"]').removeClass('active');
                        $curIcon.addClass('active');
                        textValue = $curIcon.attr('active_value');
                    }
                    $curEditableDiv.css(attrValue, textValue);
                });
            }
        };

        return FontSpecVo;
    })();
    /** FontSpecVo end */

    /** PicSelectVo 图片选择 start */
    var PicSelectVo = (function(){
        function PicSelectVo(box){
            if(this instanceof PicSelectVo){
                this.$box= $(box);
                this.$myPicContent = this.$box.find('.my_pic_content');
                this.$tempPicContent = this.$box.find('.template_pic_content');
                this.weifenleiPicID;  //图片 我的  未分类id
                this.picPageSize = 10; //图片 我的 图片分页尺寸
                this.picPageNo = 1;  //图片 我的 图片分页页码
                this.myPicMoreData = true; //图片 我的 上拉加载是否还有数据
                this.tempPicPageSize = 10; //图片 模版 图片分页尺寸
                this.tempPicPageNo = 1;  //图片 模版 图片分页页码
                this.tempPicMoreData = true; //图片 模版 上拉加载是否还有数据

                this.initView();
            }else{
                return new PicSelectVo(box);
            }
        }

        PicSelectVo.prototype = {
            constructor: PicSelectVo,
            initView: function(){
                this.bindClickTabPic();
                this.bindClickDelete();
            },
            /** 点击tab_pic */
            bindClickTabPic: function(){
                var _this = this;

                _this.$box.on('click', '.tab_pic', function(){
                    var $curTab = $(this);
                    var picTag = $curTab.attr('pic_tag');

                    if($curTab.hasClass('active')){
                        return;
                    }

                    $curTab.siblings().removeClass('active');
                    $curTab.addClass('active');
                    if(picTag === 'my_pic_content'){
                        $('.my_pic_content').show();
                        $('.template_pic_content').hide();
                    }else{
                        $('.my_pic_content').hide();
                        $('.template_pic_content').show();
                    }
                });
            },
            /** 点击删除按钮 */
            bindClickDelete: function(){
                var _this = this;

                _this.$box.on('click', '.btn_delete_pic', function(){
                    var $curBtn = $(this);
                    var $curPicItem = $curBtn.parents('.pic_item');
                    var curPicId = $curPicItem.attr('pic_id');

                    var paramsData = {
                        ids: curPicId + ','
                    };

                    $curPicItem.fadeOut(function(){
                        $curPicItem.remove();
                    });
                    $.ajax({
                        cache: false,
                        type: 'POST',
                        url: 'tSmsPicInfoController.do?doBatchDel',
                        data: paramsData,
                        dataType: 'json',
                        success: function(data){
                            
                        },
                        error: function(xhr, type){
                            
                        }
                    });

                    return false;  //禁止冒泡
                });
            },
            /**图片 我的 */
            initMyPic: function(addSortWindowVo){
                var _this = this;

                _this.searchImgClass();

                //添加分类
                _this.$myPicContent.on('click', '.icon_add_sort', function(){
                    if(typeof addSortWindowVo.show === 'function'){
                        addSortWindowVo.show(_this.concatSortDom.bind(_this));
                    }
                });

                //点击分类按钮
                _this.$myPicContent.on('click', '.pic_sort', function(){
                    //隐藏分类列表，显示图片列表
                    _this.hidePicSort();
                    _this.showPicList();
                    //加载数据
                    _this.$box.find('.my_pic_content .pic_list').children().remove();
                    var sortId = $(this).attr('sort_id');
                    _this.picPageNo = 1;  //进入图片页面时，重置页码
                    _this.myPicMoreData = true;  //进入图片页面时，重置是否有数据
                    _this.searchSortImg(sortId);
                });

                //点击返回按钮
                _this.$myPicContent.on('click', '.btn_back', function(){
                    //显示分类列表，隐藏图片列表
                    _this.showPicSort();
                    _this.hidePicList();
                });

                //点击删除分类按钮
                 _this.$myPicContent.on('click', '.btn_delete_sort', function(){
                    var $curBtnDelete = $(this);
                    var classId = $curBtnDelete.parent().attr('sort_id');
                    
                    layer.confirm("确定要删除这个分组吗？", {
                        btn: ['确定','关闭'] //按钮
                        ,title :'提示信息'
                        }, function(){
                            layer.closeAll('dialog');	//关闭弹出的确认框
                            var oMyForm = new FormData();
                            oMyForm.append("classId", classId);
                            oMyForm.append("unclassified", _this.weifenleiPicID);
                            $.ajax({
                                type : "POST",
                                url : "tSmsPicClassController.do?deletePicClass",
                                data :oMyForm,
                                async : true,
                                cache : false,
                                contentType : false,
                                processData : false,
                                dataType : "json",
                                success : function(data) {
                                    _this.searchImgClass();
                                }
                            });
                        }, function(){});  

                    return false;  //不冒泡
                });

                //点击上传图片
//                _this.$myPicContent.on('click', '.pic_item.add_pic', function(){
//                	$('#posterPic_main').click();
//                });

                //上传图片 input
                /*_this.$myPicContent.on('change', '#files', function(){
                    var curSortId = _this.$myPicContent.find('.pic_item.add_pic').attr('sort_id');
                    var fileselect = $('#files')[0].files;
                    var count = 0;  //计数，用于判断是否上传完毕
                    var loadingIndex = layer.load(1,{
                        shade: [0.6, '#DCE2F1']
                    });  //弹出层 正在加载
                    for(var i = 0; i < fileselect.length; i++){ 
                        var image = fileselect[i];
                        var oMyForm = new FormData();
                        oMyForm.append("fileselect", image);
                        oMyForm.append("classId", curSortId);
                        $.ajax({
                            type : "POST",
                            url : "tSmsPicInfoController.do?uploadClassPic",
                            data : oMyForm,
                            async : true,
                            cache : false,
                            contentType : false,
                            processData : false,
                            dataType : "json",
                            success : function(data) {
                                count++;
                                if(count === fileselect.length){  //当个数等于上传文件数量时，则上传完毕
                                    _this.picPageNo = 1;  //进入图片页面时，重置页码
                                    _this.myPicMoreData = true;  //进入图片页面时，重置是否有数据
                                    _this.searchSortImg(curSortId);
                                    $("#files").val("");
                                    layer.close(loadingIndex);
                                }
                            }
                        });
                    }
                });*/

                //上拉加载
                _this.$myPicContent.on('scroll', function(){
                    var $container = $(this),
                        containerH = $container.innerHeight(),//container内部的高度  
                        $picList = $container.find('.pic_list'),
                        contentH = $picList.outerHeight(true),  //ul的高度
                        scrollTop = $container.scrollTop(),  //滚动的距离
                        curSortId = _this.$myPicContent.find('.pic_item.btn_back').attr('sort_id');
                    
                    if($container.is(':visible') && $picList.is(':visible')){
                        //如果ul的高度 <= container内部的高度 +  滚动的距离 则开始加载
                        if(containerH + scrollTop >= contentH){
                            _this.searchSortImg(curSortId);
                        }
                    }
                });
            },
            /** 图片 模版 */
            initPicTemp: function(){
                var _this = this;

                _this.searchTempClass();

                //点击分类按钮
                _this.$tempPicContent.on('click', '.temp_sort', function(){
                    //隐藏分类列表，显示图片列表
                    _this.hidePicTempSort();
                    _this.showPicTempList();
                    //加载数据
                    _this.$box.find('.template_pic_content .pic_list').children().remove();
                    var sortId = $(this).attr('sort_id');
                    _this.tempPicPageNo = 1;  //进入图片页面时，重置页码
                    _this.tempPicMoreData = true;  //进入图片页面时，重置是否有数据
                    _this.searchSortTempImg(sortId);
                });

                //点击返回按钮
                _this.$tempPicContent.on('click', '.btn_back', function(){
                    //显示分类列表，隐藏图片列表
                    _this.showPicTempSort();
                    _this.hidePicTempList();
                });

                //上拉加载
                _this.$tempPicContent.on('scroll', function(){
                    var $container = $(this),
                        containerH = $container.innerHeight(),//container内部的高度  
                        $picList = $container.find('.pic_list'),
                        contentH = $picList.outerHeight(true),  //ul的高度
                        scrollTop = $container.scrollTop(),  //滚动的距离
                        curSortId = _this.$tempPicContent.find('.pic_item.btn_back').attr('sort_id');
                    
                    if($container.is(':visible') && $picList.is(':visible')){
                        //如果ul的高度 <= container内部的高度 +  滚动的距离 则开始加载
                        if(containerH + scrollTop >= contentH){
                            _this.searchSortTempImg(curSortId);
                        }
                    }
                });
            },
            /** 添加分类dom */
            concatSortDom: function(sortText){
                var _this = this;
                var oMyForm = new FormData();
                oMyForm.append("classname", sortText);
                $.ajax({
                    type : "POST",
                    url : "tSmsPicClassController.do?addPicClass",
                    data :oMyForm,
                    cache : false,
                    contentType : false,
                    processData : false,
                    dataType : "json",
                    success : function(data) {
                        _this.searchImgClass();
                    }
                });
            },
            /** ajax加载图片模版列表 */
            searchSortTempImg: function(sortId){
                var _this = this;
                var $parent = _this.$box.find('.template_pic_content .pic_list');
                var tempStr = '\
                    <li sort_id="'+sortId+'" class="pic_item btn_back">\
                        <div class="sort_title">返回</div>\
                    </li>\
                ';
                //参数
                var paramsData = {
                    isPlatform: '1',  //标识查询平台的分类图片
                    classId: sortId,
                    rows: _this.tempPicPageSize,
                    page: _this.tempPicPageNo
                };
                //如果没有数据，则不再加载
                if(!_this.tempPicMoreData){
                    return;
                }
                $.ajax({
                    url : "tSmsPicInfoController.do?mydatagrid",
                    data: paramsData,
                    type : 'GET',
                    cache : false,
                    dataType : 'json',
                    success : function(data) {
                        if(data.success){
                            var ajaxGetLi = '';
                            $.each(data.results, function(index, imgObj){
                                ajaxGetLi += '\
                                    <li sort_id="'+sortId+'" temp_tag="tempPic" pic_id="'+imgObj.id+'" class="pic_item template">\
                                        <img class="source_pic" src="'+imgObj.pic_url+'" alt="">\
                                    </li>\
                                ';
                            });
                        }
                        if(_this.tempPicPageNo === 1){  //初次进入时，页码为1，先删除，后添加
                            $parent.children().remove();
                            $parent.append(tempStr + ajaxGetLi);
                        }else{  //下滑加载时，在后面添加，不删除之前的dom
                            $parent.append(ajaxGetLi);
                        }
                        _this.tempPicPageNo++;  //页码加1
                        if(data.results.length < _this.tempPicPageSize){ //如果返回数据的个数 < 每页显示的数量，则没有数据了
                            _this.tempPicMoreData = false;
                        }

                        _this.$tempPicContent.trigger('scroll');  //触发滚动事件，判断是否需要加载
                    }
                });
            },
            /** ajax 获取图片模版分类列表 */
            searchTempClass: function(){
                var _this = this;
                var $parent = _this.$box.find('.template_pic_content .sort_list');
                var tempStr = '\
                    <li sort_id="0" class="sort_item temp_sort">\
                        <div class="sort_title">全部</div>\
                    </li>\
                ';

                $.ajax({
                    url : "tSmsPicClassController.do?getPlatformPicClassList",
                    type : 'GET',
                    cache : false,
                    dataType : 'json',
                    success : function(data) {
                        if(data.status == '200'){
                            $.each(data.picClassList, function(index, sortObj){
                                if(sortObj.id == '0'){
                                    return true;  //相当于continue
                                }
                                tempStr += '\
                                    <li sort_id="'+sortObj.id+'" class="sort_item temp_sort">\
                                        <div class="sort_title">'+sortObj.name+'</div>\
                                    </li>\
                                ';
                            });

                            $parent.children().remove();
                            $parent.append(tempStr);
                        }
                        
                    }
                });
            },
            /** 查询图片列表 <div class="icon_add_pic container_pic">\ </div>\ */
            searchSortImg: function(sortId){
                var _this = this;
                var $parent = _this.$box.find('.my_pic_content .pic_list');
                var tempStr = '\
                    <li sort_id="'+sortId+'" class="pic_item add_pic">\
                    	<div id="container_pic" style="width:110px;height:110px;">\
		                    	<div id="posterPic_main" class="icon_add_pic uploadify-button"></div>\
		                </div>\
                    </li>\
                    <li sort_id="'+sortId+'" class="pic_item btn_back">\
                        <div class="sort_title">返回</div>\
                    </li>\
                ';
                //参数
                var paramsData = {
                    classId: sortId,
                    rows: _this.picPageSize,
                    page: _this.picPageNo
                };
                if(!_this.myPicMoreData){
                    return;
                }
                $.ajax({
                    url : "tSmsPicInfoController.do?mydatagrid",
                    data: paramsData,
                    type : 'GET',
                    cache : false,
                    dataType : 'json',
                    success : function(data) {
                        if(data.success){
                            var ajaxGetLi = '';
                            $.each(data.results, function(index, imgObj){
                                ajaxGetLi += '\
                                    <li sort_id="'+sortId+'" temp_tag="tempPic" pic_id="'+imgObj.id+'" class="pic_item template">\
                                        <img class="source_pic" src="'+imgObj.pic_url+'" alt="">\
                                        <div class="btn_delete_pic"></div>\
                                    </li>\
                                ';
                            });
                        }
                        if(_this.picPageNo === 1){  //初次进入时，页码为1，先删除，后添加
                            $parent.children().remove();
                            $parent.append(tempStr + ajaxGetLi);
                            initQiniuUploader();
                        }else{  //下滑加载时，在后面添加，不删除之前的dom
                            $parent.append(ajaxGetLi);
                        }
                        _this.picPageNo++;  //页码加1
                        if(data.results.length < _this.picPageSize){ //如果返回数据的个数 < 每页显示的数量，则没有数据了
                            _this.myPicMoreData = false;
                        }

                        _this.$myPicContent.trigger('scroll');  //触发滚动事件，判断是否需要加载
                    }
                });
            },
            /** ajax获取图片分类列表 */
            searchImgClass: function(){
                var _this = this;
                var $parent = _this.$box.find('.my_pic_content .sort_list');
                var tempStr = '\
                    <li class="sort_item add_sort">\
                        <div class="icon_add_sort sort_title">新增分类</div>\
                    </li>\
                    <li sort_id="0" class="sort_item pic_sort">\
                        <div class="sort_title">全部</div>\
                    </li>\
                ';
                

                $.ajax({
                    url : "tSmsPicClassController.do?getPicClass",
                    type : 'get',
                    cache : false,
                    dataType : 'json',
                    success : function(data) {
                        if(data.success){
                            var undefinedSortStr = '';  //未分组放到最后面

                            $.each(data.obj, function(index, sortObj){
                                if(sortObj.name === '未分组'){
                                    _this.weifenleiPicID = sortObj.id;
                                    undefinedSortStr += '\
                                        <li sort_id="'+sortObj.id+'" class="sort_item pic_sort">\
                                            <div class="sort_title">'+sortObj.name+'</div>\
                                        </li>\
                                    ';
                                }else{
                                    tempStr += '\
                                        <li sort_id="'+sortObj.id+'" class="sort_item pic_sort">\
                                            <img class="btn_delete_sort" src="webpage/daggerSrc/editContent/img/bg_close_template.png">\
                                            <div class="sort_title">'+sortObj.name+'</div>\
                                        </li>\
                                    ';
                                }
                            });

                            tempStr += undefinedSortStr;
                            $parent.children().remove();
                            $parent.append(tempStr);
                        }
                    }
                });
            },
            /** 显示图片分类列表 */
            showPicSort: function(){
                this.$myPicContent.find('.sort_list').show();
            },
            /** 隐藏图片分类列表 */
            hidePicSort: function(){
                this.$myPicContent.find('.sort_list').hide();
            },
            /** 显示图片列表 */
            showPicList: function(){
                this.$myPicContent.find('.pic_list').show();
            },
            /** 隐藏图片列表 */
            hidePicList: function(){
                this.$myPicContent.find('.pic_list').hide();
            },
            /** 显示图片模版分类列表 */
            showPicTempSort: function(){
                this.$tempPicContent.find('.sort_list').show();
            },
            /** 隐藏图片分类列表 */
            hidePicTempSort: function(){
                this.$tempPicContent.find('.sort_list').hide();
            },
            /** 显示图片列表 */
            showPicTempList: function(){
                this.$tempPicContent.find('.pic_list').show();
            },
            /** 隐藏图片列表 */
            hidePicTempList: function(){
                this.$tempPicContent.find('.pic_list').hide();
            }

        };

        return PicSelectVo;
    })();
    /** PicSelectVo end */

    /** TempSelectVo start */
    var TempSelectVo = (function(){
        function TempSelectVo(box){
            if(this instanceof TempSelectVo){
                this.$box= $(box);
                this.$allTemplate = this.$box.find('.all_template_content');

                this.initView();
            }else{
                return new TempSelectVo(box);
            }
        }

        TempSelectVo.prototype = {
            constructor: TempSelectVo,
            initView: function(){
                this.bindClickTabTemp();
                this.initTempContent();
                this.initCarousel();
            },
            /** 点击tab_template */
            bindClickTabTemp: function(){
                var _this = this;

                _this.$box.on('click', '.tab_template', function(){
                    var $curTab = $(this);
                    var temp_tag = $curTab.attr('temp_tag');

                    if($curTab.hasClass('active')){
                        return;
                    }

                    $curTab.siblings().removeClass('active');
                    $curTab.addClass('active');
                    if(temp_tag === 'cur_template_content'){
                        $('.cur_template_content').show();
                        $('.all_template_content').hide();
                    }else{
                        $('.cur_template_content').hide();
                        $('.all_template_content').show();
                    }
                });
            },
            /** 全部模版 */
            initTempContent: function(){
                var _this = this;

                _this.$allTemplate.on('click', '.sort_item', function(){
                    _this.hideLayoutTempSort();
                    _this.showLayoutTempList();
                });

                _this.$allTemplate.on('click', '.btn_back', function(){
                    _this.hideLayoutTempList();
                    _this.showLayoutTempSort();
                });
            },
            /** 初始化 轮播图 */
            initCarousel: function(){
                var $curCarouselBox = this.$allTemplate.find('.carousel_box');
                $curCarouselBox.slick({
                    autoplay: true,
                    dots: true
                });
                $curCarouselBox.find('.slick-prev, .slick-next').css('display', 'none');
            },
            /** 显示布局模版分类 */
            showLayoutTempSort: function(){
                this.$allTemplate.find('.sort_list').show();
            },
            /** 隐藏布局模版分类 */
            hideLayoutTempSort: function(){
                this.$allTemplate.find('.sort_list').hide();
            },
            /** 显示布局模版列表 */
            showLayoutTempList: function(){
                this.$allTemplate.find('.template_list').show();
            },
            /** 隐藏布局模版列表 */
            hideLayoutTempList: function(){
                this.$allTemplate.find('.template_list').hide();
            },
        };

        return TempSelectVo;
    })();
    /** TempSelectVo end */

    /** CurLayoutTempVo start 当前模版背景对象 */
    var CurLayoutTempVo = (function(){
        function CurLayoutTempVo(box){
            if(this instanceof CurLayoutTempVo){
                this.$box= $(box);

            }else{
                return new CurLayoutTempVo(box);
            }
        }

        CurLayoutTempVo.prototype = {
            constructor: CurLayoutTempVo,
            initView: function(){

            },
            /** 设置菜单中的背景图片 */
            setBgImg: function(imgSrc){
                this.$box.find('.cur_bg').attr('src', imgSrc);
            },
            /** 点击更换背景 */
            bindClickChangeBg: function(rightMenuVo){
                var _this = this;

                _this.$box.on('click', '.change_background', function(){
                    rightMenuVo.findDom('.btn_pic')
                        .click()
                        .removeClass('active');  //将图片 按钮上的active 去除，作为标记
                });
            },
            /** 点击移除背景 */
            bindClickRemoveBg: function(editContentVo){
                var _this = this;

                _this.$box.on('click', '.remove_background', function(){
                    _this.$box.find('.cur_bg').attr('src', 'webpage/daggerSrc/common/img/cover_pic.png');
                    editContentVo.removeActiveLayoutBg();
                });
            }
        };

        return CurLayoutTempVo;
    })();
    /** CurLayoutTempVo end */

    /** LayoutTempListVo start */
    var LayoutTempListVo = (function(){
        function LayoutTempListVo(box){
            if(this instanceof LayoutTempListVo){
                this.$box= $(box);

            }else{
                return new LayoutTempListVo(box);
            }
        }

        LayoutTempListVo.prototype = {
            constructor: LayoutTempListVo
        };

        return LayoutTempListVo;
    })();
    /** LayoutTempListVo end */

    /** AddSortWindowVo start */
    var AddSortWindowVo = (function(){
        function AddSortWindowVo(box){
            if(this instanceof AddSortWindowVo){
                this.$box= $(box);
                this.$input = this.$box.find('.input_sort_name');
                this.$btnCancel = this.$box.find('.btn_sort_cancle');
                this.$btnConfirm = this.$box.find('.btn_sort_confirm');

                this.confirmDealFn = null;

                this.initView();
            }else{
                return new AddSortWindowVo(box);
            }
        }

        AddSortWindowVo.prototype = {
            constructor: AddSortWindowVo,
            initView: function(){
                this.bindClickCancle();
                this.bindClickConfirm();
            },
            show: function(confirmDealFn){
                this.confirmDealFn = confirmDealFn;
                this.$box.fadeIn();
            },
            hide: function(){
                this.confirmDealFn = null;
                this.$box.fadeOut();
            },
            bindClickCancle: function(){
                var _this = this;
                _this.$btnCancel.click(function(){
                    _this.$input.val('');
                    _this.hide();
                });
            },
            bindClickConfirm: function(){
                var _this = this;
                _this.$btnConfirm.click(function(){
                    var inputText = _this.$input.val().trim();
                    if(inputText.length === 0){
                        return;
                    }
                    if(_this.confirmDealFn != null){
                        _this.confirmDealFn(inputText);
                    }
                    _this.$input.val('');
                    _this.hide();
                });
            }
        };

        return AddSortWindowVo;
    })();
    /** AddSortWindowVo end */
    

    /** HtmlVo start */
    var HtmlVo = (function(){
        function HtmlVo(box){
            if(this instanceof HtmlVo){
                this.$box= $(box);

                this.initView();
            }else{
                return new HtmlVo(box);
            }
        }

        HtmlVo.prototype = {
            constructor: HtmlVo,
            initView: function(){
                this.bindClick();
            },
            /** 监听键盘按键 */
            addKeyListener: function(editContentVo){
                var _this = this;

                _this.$box.on('keyup', function(event){
                    if(event.ctrlKey && event.keyCode === 67){
                        editContentVo.copyUsableDiv();
                    }
                    if(event.ctrlKey && event.keyCode === 86){
                        editContentVo.pasteUsableDiv();
                    }
                    if(event.keyCode === 46){
                        editContentVo.deleteUsableDiv();
                    }
                });
            },
            bindClick: function(){
                var _this = this;

                _this.$box.on('click', function(){
                    _this.hideDropMenu();
                    _this.removeEditableActive(event);
                    _this.showSpecMask();
                    //菜单重置 点击文本 按钮
                    _this.$box.find('.btn_text').click();
                });

                //阻止冒泡
                _this.$box.on('click','.th_content, .th_menu', function(event){
                    event.stopPropagation();
                });
            },
            /** 隐藏字体和行高的下拉菜单 */
            hideDropMenu: function(){
                var $dropdownMenu = $('.text_spec .dropdown-menu');
                $dropdownMenu.each(function(index, domEle){
                    var $curDomEle = $(domEle);
                    if($curDomEle.is(':visible')){
                        $curDomEle.hide();
                    }
                });
            },
            /** 显示mask 让文字菜单不可选中 */
            showSpecMask: function(){
                var $specMask = $('.text_spec .spec_mask');
                if($specMask.is(':hidden')){
                    $specMask.show();
                }
            },
            removeEditableActive: function(event){
                $('.template_item.active').removeClass('active');
                $('div[th_editable]').removeClass('active');
                $('div[key_usable]').removeClass('active');
                $('.th_cover .img_box').removeClass('active');
                $('.change_img_item').removeClass('active');
                $('.carousel_box').slick('slickPlay');
            }
        };

        return HtmlVo;
    })();
    /** HtmlVo end */

    /** PreviewVo start */
    var PreviewVo = (function(){
        function PreviewVo(box){
            if(this instanceof PreviewVo){
                this.$box= $(box);

                this.initView();
            }else{
                return new PreviewVo(box);
            }
        }

        PreviewVo.prototype = {
            constructor: PreviewVo,
            initView: function(){
                this.bindClickClose();
            },
            show: function(){
                var _this = this;

                _this.fillSrc('tPosterController.do?goView');
                _this.$box.show();
            },
            hide: function(){
                this.$box.hide();
                this.$box.find('.preview_content').attr('src', '');
            },
            bindClickClose: function(){
                var _this = this;

                _this.$box.on('click', '.close_preview', function(){
                    _this.hide();

                    return false;
                });
            },
            fillSrc: function(url){
                var _this = this;

                _this.$box.find('.preview_content').attr('src', url);
            }
        };

        return PreviewVo;
    })();
    /** PreviewVo end */

    /** 点击 保存到草稿箱 处理事件 */
    function clickSaveFn($curBtn){
        var id = $("#id").val();
        var title = coverVo.getTitle();//标题
        var coverPic = coverVo.getPicUrl();//封面图
        // var contextHtml = editor.getContent();//正文
        var contextHtml = editContentVo.getEditContent();//正文
        $.ajax({
    		url : "tPosterController.do?doUpdate",
    		type : 'post',
    		cache : false,
    		data:{coverPic:coverPic,title:title,contextHtml:contextHtml,id:id},
    		success : function(data) {
    			var d = $.parseJSON(data);
    			var obj = d.obj;//是object对象
    			if (d.success) {
    				$("#id").val(obj);
    				alert('保存成功');
    			}else{
    				alert('保存失败');
    			}
    		} ,error:function(){
    			alert('保存失败');
    		}
    	});
        
    }
    /** 点击 预览 处理事件 */
    function clickCheckFn($curBtn){
//	   	 var content = $("#mobile_content").html();
    	// var content = editor.getContent();
    	var content = editContentVo.getEditContent();
		//判断表单是否存在，若不存在则在body中添加form表单  
		 if($("#contentForm").length<=0){  
		     var form = "<form id='contentForm' action='tPosterController.do?goView' method='post' target='contentWin'>" +  
		             "<input type='hidden' id='mobile_content_html' name='contextHtml'/>" +  
		             "</form>";  
		     $("body").append(form);//在body中添加form表单  
		 }  
		 //将用户名和密码保存到form表单的隐藏域中  
		 $("#mobile_content_html").val(content);  
		 //打开新的窗口  
		 window.open("tPosterController.do?goView","contentWin");  
        //打开手机预览
        previewVo.show();

		 //提交表单  
        $("#contentForm").submit(); 
    }
    /** 点击 完成 处理事件 */
    function clickDoneFn($curBtn){
        var title = coverVo.getTitle();
        var picUrl = coverVo.getPicUrl();
        if(title.length === 0 || picUrl.length === 0){
            topMenuVo.$alertBox.show();
            return;
        }
        var id = $("#id").val();
        var title = coverVo.getTitle();//标题
        var coverPic = coverVo.getPicUrl();//封面图
        // var contextHtml = editor.getContent();//正文
        var contextHtml = editContentVo.getEditContent();//正文
        $.ajax({
    		url : "tPosterController.do?doFinish",
    		type : 'post',
    		cache : false,
    		data:{coverPic:coverPic,title:title,contextHtml:contextHtml,id:id},
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
    /** 更换封面图 */
    function changeCover($imgBox){
        
        var width = 900;
		var height = 700;
		var title = "选择图片 ";
	  if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:tPosterController.do?toChoosePicList',
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: height,
			    ok: function(){
		               var iframe = this.iframe.contentWindow;
		               iframe.uploadCallback();//调用iframe中的方法
		               //取iframe框参数的值
		               $imgBox.find(".cover_pic").attr("src",iframe.choosePicUrl);//封面图
		               $imgBox.find(".content_pic").attr("src",iframe.choosePicUrl);//文本图
                       setTimeout(function() {
                           //用于调整ueditor 的高度
                           $('.edit_content').find('#edui31_body').click();
                       }, 100);
		               return true;
		       },
		       cancelVal: '关闭',
		       cancel: function(){
		    	   
		       } 
			}).zindex();
		}else{
			$.dialog({
				content: 'url:tPosterController.do?toChoosePicList',
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: height,
			    ok: function(){
			    	var iframe = this.iframe.contentWindow;
		               iframe.uploadCallback();//调用iframe中的方法
		               //取iframe框参数的值
		               $imgBox.find(".cover_pic").attr("src",iframe.choosePicUrl);//封面图
		               $imgBox.find(".content_pic").attr("src",iframe.choosePicUrl);//文本图
                       setTimeout(function() {
                           //用于调整ueditor 的高度
                           $('.edit_content').find('#edui31_body').click();
                       }, 100);
		               return true;
		       },
		       cancelVal: '关闭',
		       cancel: function(){
		       } 
			}).zindex();
		}
    }
    /** 更换商品 */
    function changeGoods($goodsBox){
    	
        var width = 900;
		var height = 700;
		var title = "选择商品 ";
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
	               //iframe.uploadCallback();//调用iframe中的方法
	               //取iframe框参数的值
			    	$goodsBox.find(".goods_pic").attr("src",iframe.imgUrl);
			    	$goodsBox.find(".goods_title").html(iframe.goodsName);
			    	$goodsBox.find(".goods_brand").html(iframe.brandName);
			    	$goodsBox.find(".goods_id").val(iframe.goodsId);//商品id
			    	$goodsBox.find(".goods_curprice").html(iframe.goodsPrice);
			    	$goodsBox.find(".goods_oldprice").html(iframe.originalPrice);//原价
	               return true;
	       },
	       cancelVal: '关闭',
	       cancel: function(){
	       } 
		}).zindex();
    }
        
    function fuzhi(choosePicUrl){
    }

    /** 初始化 上下拖动 */
    function initSortable(){
        return Sortable.create($('.mobile_content')[0], {
                draggable: '.template_item',
                dragClass: '.template_item',
                handle: '.handle_bar'
            });
    }

    var topMenuVo = new TopMenuVo('.th_top');
    var coverVo = new CoverVo('.th_cover');
    var rightMenuVo = new RightMenuVo('.th_menu');
    var editContentVo = new EditContentVo('.mobile_content');
    var fontSpecVo = new FontSpecVo('.text_spec');
    var picSelectVo = new PicSelectVo('.pic_select');
    var tempSelectVo = new TempSelectVo('.template_select');
    var addSortWindowVo = new AddSortWindowVo('#id_add_sort');
    var curLayoutTempVo = new CurLayoutTempVo('.tempTemplate .cur_template_content');
    var layoutTempListVo = new LayoutTempListVo('.tempTemplate .all_template_content');
    var htmlVo = new HtmlVo('html');
    var previewVo = new PreviewVo('.phone_preview_box');

    htmlVo.addKeyListener(editContentVo);
    /** 上下拖动 */
    var sortable = initSortable();

    fontSpecVo.editContentVo = editContentVo;

    picSelectVo.initMyPic(addSortWindowVo);
    picSelectVo.initPicTemp();

    //点击 保存到草稿箱
    topMenuVo.$btnSave.click(clickSaveFn);
    //点击 预览
    topMenuVo.$btnCheck.click(clickCheckFn);
    //点击 完成
    topMenuVo.$btnDone.click(clickDoneFn);

    //点击 封面
    coverVo.bindClickCover(editContentVo, rightMenuVo);
    //点击 模板
    rightMenuVo.bindClickTemplate(editContentVo, coverVo);
    //点击编辑div
    editContentVo.bindClickTemplateItem(rightMenuVo);
    editContentVo.bindDivEditableEvent(fontSpecVo);
    editContentVo.bindClickLayoutImgBg(curLayoutTempVo);
    editContentVo.blindClickChangeImg(rightMenuVo);
    //点击更换背景
    curLayoutTempVo.bindClickChangeBg(rightMenuVo);
    //点击移除
    curLayoutTempVo.bindClickRemoveBg(editContentVo);
    
    /** 编辑时重新赋值 */
    var id = $("#id").val();
    console.log(title + '----' + coverPic);
    if(id !== ''){
    	coverVo.setTitle(title);//标题
    	coverVo.setPicUrl(coverPic);//封面图
    }

});
