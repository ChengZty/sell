var Public = Public || {};
Public.isIE6 = !window.XMLHttpRequest;	//ie6


/*获取URL参数值*/
Public.getRequest = Public.urlParam = function() {
   var param, url = location.search, theRequest = {};
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0, len = strs.length; i < len; i ++) {
		 param = strs[i].split("=");
         theRequest[param[0]]=decodeURIComponent(param[1]);
      }
   }
   return theRequest;
};

/*
  通用post请求，返回json
  url:请求地址， params：传递的参数{...}， callback：请求成功回调
*/ 
Public.ajaxPost = function(url, params,async,callback){    
	$.ajax({  
	   type: "POST",
	   url: url,
	   data: params, 
	   async: async,
	   dataType: "json",  
	   success: function(data, status){  
		   callback(data);  
	   },  
	   error: function(err){  
			parent.Public.tips({type: 'error', content : '操作失败了哦，请检查您的网络链接！'});
			
	   }  
	});  
};  

Public.ajaxGet = function(url, params, callback){    
	$.ajax({  
	   type: "GET",
	   url: url,
	   dataType: "json",  
	   data: params,    
	   success: function(data, status){  
		   callback(data);  
	   },   
	   error: function(err){  
			parent.Public.tips({type: 'error', content : '操作失败了哦，请检查您的网络链接！'});
	   }  
	});  
};

/*操作提示*/
Public.tips = function(type,content){
	if('error' == type)
	{
		$("#tips").addClass("frame-errortip");
	}else if('tips' == type){
		$("#tips").addClass("frame-errortip1");
	}else if('warning' == type){
		$("#tips").addClass("frame-errortip2");
	}
	$("#tips").text(content);
	if('' != content){
		setTimeout("Public.clearTips()", 15000);	
	}
};

Public.clearTips = function(){
	$("#tips").removeClass("frame-errortip").removeClass("frame-errortip1").removeClass("frame-errortip2").text("");
}

//屏蔽backspace键
function backspace(){
    /*if(event.keyCode!=8){
         event.returnValue=true;
         return;
    }else{
        event.returnValue=false;
        return;
    }*/
  //屏蔽  backspace  回退页面
    $("input[type='text'][readonly='readonly']").bind('keydown',function(){
        if(event.keyCode==8){
            event.keyCode=27;
          }
    }); 
    // 文本域时
    $("textarea").bind('keydown',function(){
          if(event.keyCode==8){
              event.keyCode=27;
            }
    }); 
}


/**
 * 放置输入控制相关函数
 */
Public.input={
		//只能输入数字
		moneyInput:function(input){	
				var val=event.keyCode;
				if((val>=48&&val<=57) || val == 46){//数字和小数点
					var str = input.split(".");
					if(typeof(str[1])!="undefined"){
						var lengthAfterDot = str[1].length;
						if(lengthAfterDot<2){//小数点后最多只能有2位
							window.event.returnValue = true;
						}else{
							window.event.returnValue = false;
						}
					}else{
						window.event.returnValue = true;
					}
				}else{			
					window.event.returnValue = false;
				}
		},
		//只能输入数字
		numberInput:function(input){						
				var val=event.keyCode;			
				if(val>=48&&val<=57){				
					window.event.returnValue = true;
				}else{			
					window.event.returnValue = false;
				}
		},//数字和小数点
		digitalInput:function(input){						
			var val=event.keyCode;			
			if((val>=48&&val<=57) || val == 46){				
				window.event.returnValue = true;
			}else{			
				window.event.returnValue = false;
			}
		},
		nozeroDigitalInput:function(input){						
			var val=event.keyCode;			
			if((val>48&&val<=57) || val == 46){				
				window.event.returnValue = true;
			}else{			
				window.event.returnValue = false;
			}
		}
		,//只能输入电话号码
		phoneInput:function (input){						
			var val=event.keyCode;			
			if(val>=48&&val<=57||val==45){				
				window.event.returnValue = true;
			}else{			
				window.event.returnValue = false;
			}
	},//自动剔除输入框的左右空格
	trimInputValue:function(input){		
		var inputObj=$(input);
		var inputVal=inputObj.val();		
		if(inputVal!=null){
			inputObj.val($.trim(inputVal));
		}
	},//checkbox全选按钮checkObj:控件本身,objName:需要选中的checkbox name名称
	 checkBoxAll:function(checkObj,objName){	
		if(checkObj.checked){
			$(":input[name='"+objName+"']").attr("checked","checked");
		}else{
			$(":input[name='"+objName+"']").attr("checked","");
		}
	}
}
