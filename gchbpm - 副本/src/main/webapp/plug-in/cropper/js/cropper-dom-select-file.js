//主要用于裁剪图片上传前，按钮点击选择文件，或者拖曳上传
//有尺寸规定，采用裁剪方式上传到七牛
//top顶层对象上挂载两个参数， qiniuFileVo(文件对象),qiniuSourceLink(图片路径)
//需要修改 paramsJson 和 dealSourceLink

/** 按钮点击选择图片 BtnSelectFileVo start */
var BtnSelectFileVo = (function(){
    function BtnSelectFileVo(box, paramsJson){
        if(this instanceof BtnSelectFileVo){
            this.$box= $(box);
            this.paramsJson = paramsJson ? paramsJson : {};

            this.initData();
            this.initView();
        }else{
            return new BtnSelectFileVo(box, paramsJson);
        }
    }

    BtnSelectFileVo.prototype = {
        constructor: BtnSelectFileVo,
        /** 处理数据 */
        initData: function(){
            var _this = this;
            var tempCropperWidth = _this.paramsJson.cropperWidth;
            var tempCropperHeight = _this.paramsJson.cropperHeight;
            var tempModuleName = _this.paramsJson.moduleName;
            var tempDealSourceLinkFn =  _this.paramsJson.dealSourceLinkFn;

            _this.paramsJson = {
                cropperWidth: tempCropperWidth ? tempCropperWidth : 100,  //默认100
                cropperHeight: tempCropperHeight ? tempCropperHeight : 100,  //默认100
                moduleName: tempModuleName ? tempModuleName : 'module',  //默认'module'
                dealSourceLinkFn: tempDealSourceLinkFn ? tempDealSourceLinkFn : function(){},  //默认空方法
            };
        },
        initView: function(){
            this.bindClick();
        },
        bindClick: function(){
            var _this = this;

            _this.$box.on('click', function(){
                
                if($('.trigger-select-pic').length > 0){  //已经存在
                    $('.trigger-select-pic').eq(0).click();
                    return;
                }

                $('body').append('<input style="display: none;" class="trigger-select-pic" type="file" />');
                $('.trigger-select-pic').on('change', function(e){
                    var $curInput = $(this);
                    var fileVo = e.target.files[0];
                    top.qiniuFileVo = fileVo;  //挂载对象
    
                    _this.openDialog(_this.paramsJson);
                });
    
                $('.trigger-select-pic').click();
            });
        },
        /**
         * 打开dialog
         */
        openDialog: function(paramsJson){
            //路径
            var contentStr = 'url:systemController.do?getCropperPage&cropperWidth='+paramsJson.cropperWidth+'&cropperHeight='+paramsJson.cropperHeight+'&moduleName='+paramsJson.moduleName;
            $.dialog({
                content: contentStr,
                lock : true,
                width:1000,
                height:600,
                zIndex:2000,
                title:'图片选择',
                opacity : 0.5,
                cache:false,
                close: function(){  //关闭dialog回调
                    var sourceLink = top.qiniuSourceLink ? top.qiniuSourceLink : '';

                    top.qiniuSourceLink = ''; //设为空字符串
                    top.qiniuFileVo = null;  //释放内存
                    $('.trigger-select-pic').remove();  //清除dom

                    if(sourceLink == ''){
                        return;
                    }

                    //处理上传图片路径的回调
                    paramsJson.dealSourceLinkFn(sourceLink);
                }
            });
        }
    };

    return BtnSelectFileVo;
})();
/** 按钮点击选择图片 BtnSelectFileVo end */

/** 拖曳选择图片 DragDropSelectFileVo start */
var DragDropSelectFileVo = (function(){
    function DragDropSelectFileVo(box, paramsJson){
        if(this instanceof DragDropSelectFileVo){
            this.$box= $(box);
            this.paramsJson = paramsJson ? paramsJson : {};

            this.dragDrop = this.$box[0];

            this.initData();
            this.initView();
        }else{
            return new DragDropSelectFileVo(box, paramsJson);
        }
    }

    DragDropSelectFileVo.prototype = {
        constructor: DragDropSelectFileVo,
        /** 处理数据 */
        initData: function(){
            var _this = this;
            var tempCropperWidth = _this.paramsJson.cropperWidth;
            var tempCropperHeight = _this.paramsJson.cropperHeight;
            var tempModuleName = _this.paramsJson.moduleName;
            var tempDealSourceLinkFn =  _this.paramsJson.dealSourceLinkFn;

            _this.paramsJson = {
                cropperWidth: tempCropperWidth ? tempCropperWidth : 100,  //默认100
                cropperHeight: tempCropperHeight ? tempCropperHeight : 100,  //默认100
                moduleName: tempModuleName ? tempModuleName : 'module',  //默认'module'
                dealSourceLinkFn: tempDealSourceLinkFn ? tempDealSourceLinkFn : function(){},  //默认空方法
            };
        },
        initView: function(){
            this.bindEvt();
        },
        bindEvt: function(){
            var _this = this;

            if (this.dragDrop) {
                this.dragDrop.addEventListener("dragover", function(e) { _this.funDragHover(e); }, false);
                this.dragDrop.addEventListener("dragleave", function(e) { _this.funDragHover(e); }, false);
                this.dragDrop.addEventListener("drop", function(e) { _this.funGetFiles(e); }, false);
            }
        },
        //文件拖放
        funDragHover: function(e) {
            e.stopPropagation();
            e.preventDefault();
            return this;
        },
        //获取选择文件，file控件或拖放
        funGetFiles: function(e) {
            var _this = this;

            // 取消鼠标经过样式
            this.funDragHover(e);
                    
            // 获取文件列表对象
            var files = e.target.files || e.dataTransfer.files;
            var fileVo = files[0];
            top.qiniuFileVo = fileVo;  //挂载对象

            this.openDialog(_this.paramsJson);
            
            return this;
        },
        /**
         * 打开dialog
         */
        openDialog: function(paramsJson){
            //路径
            var contentStr = 'url:systemController.do?getCropperPage&cropperWidth='+paramsJson.cropperWidth+'&cropperHeight='+paramsJson.cropperHeight+'&moduleName='+paramsJson.moduleName;
            $.dialog({
                content: contentStr,
                lock : true,
                width:1000,
                height:600,
                zIndex:2000,
                title:'图片选择',
                opacity : 0.5,
                cache:false,
                close: function(){  //关闭dialog回调
                    top.qiniuFileVo = null;  //释放内存
                    $('.trigger-select-pic').remove();  //清除dom

                    var sourceLink = top.qiniuSourceLink ? top.qiniuSourceLink : '';
                    if(sourceLink == ''){
                        return;
                    }

                    //处理上传图片路径的回调
                    paramsJson.dealSourceLinkFn(sourceLink);
                }
            });
        }
    };

    return DragDropSelectFileVo;
})();
/** 拖曳选择图片 DragDropSelectFileVo end */