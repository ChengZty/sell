/// <reference path="F:/tianhu/typings/typings/index.d.ts" />
var editor;
$(document).ready(function(){
    /** 模板数据 */
    var templateObj = {
        tempPic: '\
            <div class="mobile_item img_box">\
                <img class="content_pic" src="webpage/daggerSrc/common/img/cover_pic.png" alt=""><button class="btn_mask"></button>\
                <img  class="handle_bar" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/handle_bar_03.png"/><img  class="close_template" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/bg_close_template.png"/></div>\
        ',
        tempGoods: '\
            <div class="mobile_item goods_box">\
                <div class="goods_item">\
                    <div class="goods_pic_box">\
                        <img class="goods_pic" src="webpage/daggerSrc/common/img/bg_goods.png" alt="">\
        				<input type="hidden" class="goods_id" >\
                    </div>\
                    <div class="goods_detail_box">\
                        <div class="goods_title">Concrete请输入标题标题标题Concrete请输入标题标题标题</div>\
                        <div class="goods_brand">品牌品牌品牌</div>\
                        <div class="goods_price_box">\
                            <div class="goods_curprice">¥69</div><div class="goods_oldprice">¥119</div>\
                        </div>\
                        <img class="hint_buy" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/bg_buy_hint.png"/>\
                    </div>\
                </div>\
                <button class="btn_mask"></button><img  class="handle_bar" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/handle_bar_03.png"/><img  class="close_template" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/bg_close_template.png"/></div>\
        ',
        tempTitle: '\
            <div class="mobile_item title_box">\
                <div class="info_title">请输入资讯标题</div>\
                <img  class="handle_bar" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/handle_bar_03.png"/><img  class="close_template" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/bg_close_template.png"/></div>\
        ',
        tempAuthor: '\
            <div class="mobile_item author_box">\
                <div class="info_author">作者</div><img  class="handle_bar" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/handle_bar_03.png"/><img  class="close_template" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/bg_close_template.png"/></div>\
        ',
        tempContent: '\
            <div class="mobile_item paragraph_box">\
                <div class="info_paragraph">请输入正文内容</div><img  class="handle_bar" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/handle_bar_03.png"/><img  class="close_template" src="webpage/daggerSrc/Formdesign/js/ueditor/themes/default/images/bg_close_template.png"/></div>\
        '
    };
    /** 实例化编辑器 */
     editor = UE.getEditor('mobile_content', {
        toolbars: [
            ['undo', 'redo', 'fontsize', 'lineheight', 'forecolor', 
            'bold', 'italic', 'underline','justifyleft', 'justifycenter', 'justifyright']
        ],
        //focus时自动清空初始化时的内容  
        autoClearinitialContent:true,  
        //关闭字数统计  
        wordCount:false,  
        //关闭elementPath  
        elementPathEnabled:false,  
        //默认的编辑区域高度  
        initialFrameHeight:500,
        'enterTag':'',
        tableDragable: false,
        pasteplain: true,
        catchremoteimageenable: true

    });

    /** 编辑器的document的jquery对象 */
    var $editorDocument = null;
    var $editorBody = null;
    /** 上下拖动 */
    var sortable = null;

    /** 编辑器 准备 回调 */
    editor.addListener( 'ready', function(  ) {
        // editor.execCommand( 'focus' ); //编辑器家在完成后，让编辑器拿到焦点
        $editorDocument = $(editor.document);
        $editorBody = $editorDocument.find('body');
        /**点击编辑器中的商品和图片 */
        $editorBody
            .on('click', '.template_item[template_type="tempPic"]', function(){
            	changeCover($(this));
            })
            .on('click', '.template_item[template_type="tempGoods"]', function(){
                changeGoods($(this));
            })
            .on('click', '.close_template', function(e){
            	var aim;
            	if(e.srcElement){
	                aim=e.srcElement;
               }else{
                 aim=e.target;
                }
                if(aim == $(this)[0]){
                	console.log(e);
                	$(this).parents('.mobile_item').remove();
                }
                e.stopPropagation(); 
            });

        //添加禁止向ueditor文本框中拖拽内容（不支持IE8）
        // editor.document.body.draggable = false;
        // editor.document.body.setAttribute("ondragstart","return false");
        // editor.document.body.setAttribute("ondragenter","event.dataTransfer.dropEffect='none'; event.stopPropagation(); event.preventDefault();");
        editor.document.body.setAttribute("ondragover","event.dataTransfer.dropEffect='none';event.stopPropagation(); event.preventDefault();");
        editor.document.body.setAttribute("ondrop","event.dataTransfer.dropEffect='none';event.stopPropagation(); event.preventDefault();");
        /** 上下拖动 */
        sortable = Sortable.create($editorBody[0], {
            draggable: '.template_item',
            dragClass: '.template_item',
            handle: '.handle_bar'
        });
        

        
        /** 编辑页面加载H5内容*/
        if(typeof contextHtml !== 'undefined' && contextHtml !== ''){
        	editor.execCommand('inserthtml', contextHtml);//tPoster-update.jsp页面取的全局变量
        }


    } );
    

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
                
            }else{
                return new CoverVo(box);
            }
        }

        CoverVo.prototype = {
            constructor: CoverVo,
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
                this.$tempTitle = this.$box.find('.temp_title');
                this.$tempAuthor = this.$box.find('.temp_author');
                this.$tempContent = this.$box.find('.temp_content');
                //模板
                this.$template = this.$box.find('.template');

                this.initView();
            }else{
                return new RightMenuVo(box);
            }
        }

        RightMenuVo.prototype = {
            constructor: RightMenuVo,
            initView: function(){
                this.bindSwichActive();
            },
            /** 切换 按钮状态 */
            bindSwichActive: function(){
                var _this = this;

                _this.$leftBtns.each(function(index, btn){
                    var curBtn = $(btn);
                    curBtn.click(function(){
                        if(curBtn.hasClass('active')){
                            return;
                        }
                        _this.$leftBtns.removeClass('active');
                        curBtn.addClass('active');

                        if(curBtn.hasClass('btn_text')){
                            _this.$menuInfo.show();
                        }else{
                            _this.$menuInfo.hide();
                        }
                    });
                });
            },
            /** 点击模板 */
            bindClickTemplate: function(editor){
                var _this = this;

                _this.$box.on('click', '.template', function(){
                    //光标移动到最后
                    editor.focus(true);
                    //最后的一个p的高度设置为0，即可以隐藏，又可以避免ueditor不能自动增加高度
                    $editorBody.children('p').height(0);

                    var $curTemp = $(this);

                    var tempName = $curTemp.attr('temp_tag');
                    var tempStr = templateObj[tempName];
                    var tempGUID = util.getGUID();
                    tempStr = '<div template_type="'+tempName+'" template_guid="'+tempGUID+'" class="template_item">' + tempStr + '</div>';
                    
                    editor.execCommand('inserthtml', tempStr);
                    //让编辑器失去焦点
                    editor.blur();
                });
            }
        };
        return RightMenuVo;
    })();
    /** RightMenuVo end */

    /** 点击 保存到草稿箱 处理事件 */
    function clickSaveFn($curBtn){
        var id = $("#id").val();
        var title = coverVo.getTitle();//标题
        var coverPic = coverVo.getPicUrl();//封面图
        var contextHtml = editor.getContent();//正文
        if(coverPic==""){
        	alert('请插入封面图');
        	return false;
        }
        if(title==""){
        	alert('请输入标题');
        	return false;
        }
        if(contextHtml==""){
        	alert('请输入内容');
        	return false;
        }
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
    	var content = editor.getContent();
	   	 console.log(content);
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
        var contextHtml = editor.getContent();//正文
        if(coverPic==""){
        	alert('请插入封面图');
        	return false;
        }
        if(title==""){
        	alert('请输入标题');
        	return false;
        }
        if(contextHtml==""){
        	alert('请输入内容');
        	return false;
        }
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
    /** 点击 图片 上传图片 */
    function clickPicFn(){
        console.log('上传图片');
    }
    /** 点击 商品 上传商品 */
    function clickGoodsFn(){
        console.log('更换商品');
    }
    /** 更换封面图 */
    function changeCover($imgBox){
        console.log($imgBox);
        
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
    	console.log($goodsBox);
    	
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
			    	console.log(iframe.goodsId);
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
    	console.log(choosePicUrl);
    }

    var topMenuVo = new TopMenuVo('.th_top');
    var coverVo = new CoverVo('.th_cover');
    var rightMenuVo = new RightMenuVo('.th_menu');
    

    /** 点击 保存到草稿箱 */
    topMenuVo.$btnSave.click(clickSaveFn);
    /** 点击 预览 */
    topMenuVo.$btnCheck.click(clickCheckFn);
    /** 点击 完成 */
    topMenuVo.$btnDone.click(clickDoneFn);

    /** 点击 封面 */
    coverVo.$imgBox.click(function(){
    	changeCover($(this));
    });
    /** 点击 模板 */
    rightMenuVo.bindClickTemplate(editor);
    /** 点击 图片 */
    rightMenuVo.$btnPic.click(clickPicFn);
    /** 点击 商品 */
    rightMenuVo.$btnGoods.click(clickGoodsFn);
    
    /** 编辑时重新赋值 */
    var id = $("#id").val();
    console.log("id:"+id);
    if(id !== ''){
    	coverVo.setTitle(title);//标题
    	coverVo.setPicUrl(coverPic);//封面图
    }
    
});
