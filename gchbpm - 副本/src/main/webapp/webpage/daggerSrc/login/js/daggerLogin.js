/// <reference path="F:/tianhu/typings/typings/index.d.ts" />

$(document).ready(function(){

    /** 工具对象 */
    var util = (function(){
        /** 页面初始化 */
        var initView = function(){

        };
        /** 利用正则去掉前后空格 */
        var spaceTrim = function(){

        };

        return {
            initView: initView
        };
    })();

    /** TabVo start  Tab栏*/
    var TabVo = (function(){
        function TabVo(box){
            if(this instanceof TabVo){
                this.$box = $(box);

            }else{
                return new TabVo(box);
            }
        }

        TabVo.prototype = {
            constructor: TabVo,
            bindClickTab: function(dealFn){
                this.$box.on('click', 'button', function(){
                    var curBtn = $(this);
                    curBtn.parent().children('button').removeClass('active');
                    curBtn.addClass('active');
                    dealFn(curBtn);
                });
            }
        };

        return TabVo;
    })();
    /** TabVo end */

    /** LoginVo start 登录对象*/
    var LoginVo = (function(){
        function LoginVo(box){
            if(this instanceof LoginVo){
                this.$box= $(box);
                this.inputName = this.$box.find('.input_username');
                this.inputPwd = this.$box.find('.input_pwd');
                this.btnLogin = this.$box.find('.btn_login');

                this.init();

            }else{
                return new LoginVo(box);
            }
        }

        LoginVo.prototype = {
            constructor: LoginVo,
            init: function(){
                this.bindClickClear();
            },
            show: function(){
                this.$box.show();
            },
            hide: function(){
                this.$box.hide();
            },
            /** 清空input */
            bindClickClear: function(){
                this.$box.on('click', '.btn_clear', function(){
                    var curBtn = $(this);
                    var curInput = curBtn.parent().children('input');
                    curInput.val('');
                });
            },
            /** 点击"立即登录" */
            bindClickLogin: function(){
                var _this = this;

                _this.btnLogin.click(function(){
                    var userName = _this.inputName.val();
                    
                });
            }
        };

        return LoginVo;
    })();
    /** LoginVo end */

    /** RegisterVo start 注册对象*/
    var RegisterVo = (function(){
        function RegisterVo(box){
            if(this instanceof RegisterVo){
                this.$box= $(box);
                this.agreedCheckBox = this.$box.find('#agreed');  //接受协议的checkbox
                this.btnRegister = this.$box.find('.btn_register');  //提交注册信息按钮

                this.init();

            }else{
                return new RegisterVo(box);
            }
        }

        RegisterVo.prototype = {
            constructor: RegisterVo,
            init: function(){
                this.bindClickClear();
                this.bindCheckBox();
            },
            show: function(){
                this.$box.show();
            },
            hide: function(){
                this.$box.hide();
            },
            /** 清空input */
            bindClickClear: function(){
                this.$box.on('click', '.btn_clear', function(){
                    var curBtn = $(this);
                    var curInput = curBtn.parent().children('input');
                    curInput.val('');
                });
            },
            /** 如果不接受用户协议，不可点击“提交注册信息”按钮 */
            bindCheckBox: function(){
                var _this = this;
                _this.agreedCheckBox.on('click', function(){
                    if(!_this.agreedCheckBox.is(':checked')){
                        _this.btnRegister.attr('disabled', 'disabled');
                    }else{
                        _this.btnRegister.removeAttr('disabled');
                    }
                });

            }
        };

        return RegisterVo;
    })();
    /** RegisterVo end */

    /** 处理Tab栏的点击 */
    function clickTabFn(curBtn){
        var className = curBtn.attr('class_tag');
        if(loginVo.$box.hasClass(className)){
            registerVo.hide();
            loginVo.show();
        }else{
            loginVo.hide();
            registerVo.show();
        }
    }

    util.initView();

    var tabVo = new TabVo('.content_tab');
    var loginVo = new LoginVo('.login_content');
    var registerVo = new RegisterVo('.register_content');

    tabVo.bindClickTab(clickTabFn);
    loginVo.bindClickLogin();
});