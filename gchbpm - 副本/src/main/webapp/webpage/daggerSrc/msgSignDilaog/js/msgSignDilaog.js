
$(document).ready(function () {
    var DialogBox = (function () {
        function DialogBox(dialogBox){
            if(this instanceof DialogBox){
                this.$dialogBox = $(dialogBox);
                this.$msg_input = this.$dialogBox.find('.msg_input_box .msg_input');//输入框
                this.$msg_hint_box = this.$dialogBox.find('.msg_hint_box');//提示区域
                this.$msg_sign_name = this.$dialogBox.find('.msg_hint_box .msg_sign_name');//签名文本
                this.$btn_affirm = this.$dialogBox.find('.button_box .btn_affirm');//确认按钮对象
                this.$btn_cancel = this.$dialogBox.find('.button_box .btn_cancel');//取消按钮对象
            }else{
                return new DialogBox(dialogBox);
            }
        }
        DialogBox.prototype = {
            constructor: DialogBox,
            //输入框内容变化监听
            inputListener:function () {
                var _this = this;
                this.$msg_input.on('input propertychange',function () {
                    var str = _this.$msg_input.val();
                    if(str.toString().trim().length>0){
                        _this.$msg_sign_name.text(str);
                        _this.$msg_hint_box.css('display','block');
                        _this.$btn_affirm.removeAttr('disabled');
                        mDialogBox.changeColor();
                    }else{
                        _this.$msg_hint_box.css('display','none');
                        _this.$btn_affirm.attr('disabled','disabled');//设置不可点击
                        mDialogBox.recoverColor();
                    }
                })
            },
            //改变确认提交按钮颜色
            changeColor:function () {
                this.$btn_affirm.css('background','#548087');
                this.$btn_affirm.css('color','white');
            },
            //恢复按钮颜色
            recoverColor:function () {
                this.$btn_affirm.css('background','#CCCCCC');
                this.$btn_affirm.css('color','#B3B3B3');
            },
            submit:function () {
                var _this = this;
                this.$btn_affirm.on('click',function () {
                    var str = _this.$msg_input.val();
                    console.log(str);
                })
            },
            cancel:function () {
                this.$btn_cancel.on('click',function () {

                })
            }
        };
        return DialogBox;
    })();
    var mDialogBox = new DialogBox('.xj_box');
    mDialogBox.inputListener();
    mDialogBox.submit();
    mDialogBox.cancel();
});