$(document).ready(function(){
    
    /**
     * 获取七牛TOKEN和零售商编码接口
     */
    var URL_GET_QINIU_TOKEN = top.location.origin + '/systemController.do?getQNUptoken&c=1';  //正式 测试 不带项目名
    /**
     * 上传图片到七牛服务器接口
     */
    var URL_UPLOAD_QINIU_PIC = 'http://up.qiniu.com/'; 

    
    //格式化日期
    Date.prototype.format = function (fmt) { //author: meizz 
        var o = {
            "M+": this.getMonth() + 1, //月份 
            "d+": this.getDate(), //日 
            "H+": this.getHours(), //小时 
            "m+": this.getMinutes(), //分 
            "s+": this.getSeconds(), //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
            "S": this.getMilliseconds() //毫秒 
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

    /** 裁剪图片 CropImgVo start */
    var CropImgVo = (function(){
        function CropImgVo(box){
            if(this instanceof CropImgVo){
                this.$box= $(box);
                this.file = null;
                this.tokenKeyJson = {};

                this.initView();
            }else{
                return new CropImgVo(box);
            }
        }

        CropImgVo.prototype = {
            constructor: CropImgVo,
            initView: function(){
                this.initCropper();

                this.bindInputFileChange();
                this.bindClick();
            },
            /**
             * 绑定点击事件
             */
            bindClick: function(){
                var _this = this;

                //点击上传图片按钮
                _this.$box.on('click', '.upload_pic', function(){
                    _this.showLoading(function(){
                        _this.getqiniuTokenAndKey(URL_GET_QINIU_TOKEN);  //同步获取token
                        _this.uploadCropperPic(_this.tokenKeyJson);
                    });

                });
            },
            /**
             * 绑定文件选择变化事件
             */
            bindInputFileChange: function(){
                var _this = this;

                _this.$box.on('change', '.avatar-input', function(e){
                    var $curInput = $(this);
                    var fileVo = e.target.files[0];

                    _this.replaceCropperWithFile(fileVo);
                });
            },
            /**
             * 初始化 裁剪 插件
             */
            initCropper: function(){
                var _this = this;

                $('.crop-img').cropper({
                    dragMode: 'move',
                    aspectRatio: cropperWidth / cropperHeight,//裁剪框比例 1：1
                    viewMode : 1,//显示
                    movable : true,//是否能移动图片
                    cropBoxMovable :true,//是否允许拖动裁剪框
                    cropBoxResizable :true,//是否允许拖动 改变裁剪框大小
                });

                //使用定时器，排在事件队列后面
                setTimeout(function(){
                    if(top.qiniuFileVo){
                        _this.replaceCropperWithFile(top.qiniuFileVo);
                    }
                }, 0);
            },
            /**
             * 在有file对象的情况下，替换cropper中的图片
             */
            replaceCropperWithFile: function(fileVo){
                var _this = this;

                _this.file = fileVo;
                var n = fileVo.name.lastIndexOf(".");
                var suffix = fileVo.name.substring(n);

                if(".jpg,.png,.jpeg,.bmp".indexOf(suffix) === -1){
                    alert('类型不符，请选择jpg,png,jpeg,bmp文件');
                    return;
                }

                var blobURL = URL.createObjectURL(fileVo);
                $('.crop-img').cropper('replace', blobURL);
            },
            /**
             * 上传裁剪图片
             */
            uploadCropperPic: function(tokenKeyJson){
                var _this = this;

                //获取 裁剪范围的宽和高
                var cropperData = $('.crop-img').cropper('getData',{
                    rounded : false
                });
                var sizeParamsJson = _this.getDistSize(cropperData);

                 //获取指定宽高的canvas
                var croppedCanvas = $('.crop-img').cropper('getCroppedCanvas',{ 
                    width: sizeParamsJson.width,
                    height: sizeParamsJson.height,
                    fillColor: '#ffffff'
                });

                //压缩图片
                var quality = 90;
                var dataURL = croppedCanvas.toDataURL('image/jpeg', quality/100);
                var blob = _this.dataURLtoBlob(dataURL);
                while(blob.size / 1024 > 100 && quality > 30){  //图片大于100K，且质量大于30，则继续压缩
                    quality = quality - 10;
                    dataURL = croppedCanvas.toDataURL('image/jpeg', quality/100);
                    blob = _this.dataURLtoBlob(dataURL);
                }

                //FormData 对象，ajax上传用
                var formData = new FormData();
                
                formData.append('token', tokenKeyJson.token);
                formData.append('key', tokenKeyJson.key);
                formData.append('file', blob, _this.file.name.replace('.png', '.jpg'));
                
                $.ajax({
                    url: URL_UPLOAD_QINIU_PIC,
                    type: "POST",
                    data: formData,
                    processData: false, // 告诉jQuery不要去处理发送的数据，否则：Illegal invocation
                    contentType: false, // 告诉jQuery不要去设置Content-Type请求头，否则：400，Bad Request
                    success: function (data) {
                        
                        var sourceLink = qiniuDomain + data.key;  //图片地址，用于获取图片

                        top.qiniuSourceLink = sourceLink;  //七牛图片路径，挂载在top全局对象上
                        
                        var ui_close = window.parent.document.querySelector('.ui_dialog .ui_close'); 
                        ui_close.click(); //关闭dialog
                    },
                    error: function () {
                        console.log('Upload error');

                        _this.hideLoading();
                    }
                });
            },
            /**
             * 获取七牛token和key
             */
            getqiniuTokenAndKey: function(serverPath){
                var _this = this;

                $.ajax({
                    url: serverPath, 
                    type: "POST",
                    async : false,//同步
                    dataType : 'json',
                    cache: false,
                    success: function(data){

                        var time = new Date().format("yyyyMMdd");
                        var name = (new Date()).format("MMddHHmmss") + _this.getRandomStr();
                        var n = _this.file.name.lastIndexOf(".");
                        name += _this.file.name.substring(n);

                        var key = data.code + '/' + moduleName + '/' + time + '/' + name;

                        _this.tokenKeyJson.token = data.uptoken;
                        _this.tokenKeyJson.key = key;
                    },
                    error: function(){

                    }
                });
            },
            /**
             * 获取随机ID
             */
            getRandomStr: function(){
                var str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                var n = 8, s = "";
                for(var i = 0; i < n; i++){
                    var rand = Math.floor(Math.random() * str.length);
                    s += str.charAt(rand);
                }
                return s;
            },
            /**
             * dataURL转换为Blob对象
             */
            dataURLtoBlob: function(dataurl) {
                var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                    bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
                while(n--){
                    u8arr[n] = bstr.charCodeAt(n);
                }
                return new Blob([u8arr], {type:mime});
            },
            /**
             * 获取目标长和宽
             */
            getDistSize: function(cropperData){
                var cropperWidth = cropperData.width;
                var cropperHeight = cropperData.height;
                var params = {
                    width: cropperWidth,
                    height: cropperHeight
                };

                /**
                 * a，图片宽或者高均小于或等于1280时图片尺寸保持不变，但仍然经过图片压缩处理，得到小文件的同尺寸图片

                    b，宽或者高大于1280，但是图片宽度高度比小于或等于2，则将图片宽或者高取大的等比压缩至1280

                    c，宽或者高大于1280，但是图片宽高比大于2时，并且宽以及高均大于1280，则宽或者高取小的等比压缩至1280

                    d，宽或者高大于1280，但是图片宽高比大于2时，并且宽或者高其中一个小于1280，则压缩至同尺寸的小文件图片      
                    */
                //宽高比
                var widthHeightRatio = (cropperWidth / cropperHeight > 1) ? (cropperWidth / cropperHeight) : (cropperHeight / cropperWidth); 
                
                if(cropperWidth <= 1280 && cropperHeight <= 1280 ){  //a方案
                    //不做处理
                }else if((cropperWidth > 1280 || cropperHeight > 1280) && widthHeightRatio <= 2.1){  //b方案
                    if(cropperWidth >= cropperHeight){
                        params.width = 1280;
                        params.height = (params.width / cropperWidth) * cropperHeight;
                    }else{
                        params.height = 1280;
                        params.width = (params.height / cropperHeight) * cropperWidth;
                    }
                    
                }else if(cropperWidth > 1280 && cropperHeight > 1280 && widthHeightRatio > 2.1){ //c方案
                    if(cropperWidth <= cropperHeight){
                        params.width = 1280;
                        params.height = (params.width / cropperWidth) * cropperHeight;
                    }else{
                        params.height = 1280;
                        params.width = (params.height / cropperHeight) * cropperWidth;
                    }
                    
                }else if(((cropperWidth > 1280 && cropperHeight < 1280) || (cropperWidth < 1280 && cropperHeight > 1280)) && widthHeightRatio > 2.1){ //d方案
                    //不做处理
                }

                return params;
            },
            /**
             * 显示loading
             */
            showLoading: function(callback){
                this.$box.find('.ui_loading_box').show(callback);
            },
            /**
             * 隐藏loading
             */
            hideLoading: function(){
                this.$box.find('.ui_loading_box').hide();
            }
        };

        return CropImgVo;
    })();
    /** 裁剪图片 CropImgVo end */

    var cropImgVo = new CropImgVo('.cropper-box');
});
