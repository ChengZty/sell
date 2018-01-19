$(function () {
	//上传小图
//     var P = new QiniuJsSDK();
//     var uploaderPic = P.uploader({
//         runtimes: 'html5,flash,html4',      // 上传模式，依次退化
//         browse_button: 'templatePic_main_small',         // 上传选择的点选按钮，必需
//         // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
//         // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
//         // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
// //        uptoken : uptoken, // uptoken是上传凭证，由其他程序生成
//          uptoken_url: 'systemController.do?getQNUptoken',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
// //         uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
// //            // do something
// //            return getUptoken();
// //         },
//         get_new_uptoken: true,             // 设置上传文件的时候是否每次都重新获取新的uptoken
//         // downtoken_url: '/downtoken',
//         // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
//         // unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
//         // save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
//         domain: domain,     // bucket域名，下载资源时用到，必需
//         container: 'container_pic',             // 上传区域DOM ID，默认是browser_button的父元素
//         max_file_size: '100kb',             // 最大文件体积限制
//         flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
//         max_retries: 3,                     // 上传失败最大重试次数
//         dragdrop: true,                     // 开启可拖曳上传
//         drop_element: 'container_pic',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
//         chunk_size: '4mb',                  // 分块上传时，每块的体积
//         auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
//         //x_vars : {
//         //    查看自定义变量
//         //    'time' : function(up,file) {
//         //        var time = (new Date()).getTime();
//                   // do something with 'time'
//         //        return time;
//         //    },
//         //    'size' : function(up,file) {
//         //        var size = file.size;
//                   // do something with 'size'
//         //        return size;
//         //    }
//         //},
//         filters : {
//             max_file_size : '100kb',
//             prevent_duplicates: false,  // 是否允许选取重复的文件,为true时表示不允许,为false时表示允许,默认为false
//             // Specify what files to browse for
//             mime_types: [
//                 {title : "Image files", extensions : "jpg,png,jpeg,bmp"} // 限定jpg,jpeg,png等后缀上传
//             ]
//         },
//         init: {
//             'FilesAdded': function(up, files) {
//                 plupload.each(files, function(file) {
//                     // 文件添加进队列后，处理相关的事情
//                 });
//             },
//             'BeforeUpload': function(up, file) {
//                    // 每个文件上传前，处理相关的事情
//             },
//             'UploadProgress': function(up, file) {
//                    // 每个文件上传时，处理相关的事情
//             },
//             'FileUploaded': function(up, file, info) {
//                    // 每个文件上传成功后，处理相关的事情
//                    // 其中info是文件上传成功后，服务端返回的json，形式如：
//                    // {
//                    //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
//                    //    "key": "gogopher.jpg"
//                    //  }
//                    // 查看简单反馈
//                    var domain = up.getOption('domain');
//                    var res = $.parseJSON(info);
//                    var sourceLink = domain + res.key; //获取上传成功后的文件的Url
//                    var div_imgs = "<div class='pic_div'><a class='delete' onclick='delSmallPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100' ></img></div>"
// 	   				$("#main_pics_small").html(div_imgs);
// 	   				$("#smallPic").val(sourceLink);
//             },
//             'Error': function(up, err, errTip) {
//                    //上传出错时，处理相关的事情
//             	alert(errTip);
//             },
//             'UploadComplete': function() {
//                    //队列文件处理完毕后，处理相关的事情
//             },
//             'Key': function(up, file) {
//                 // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
//                 // 该配置必须要在unique_names: false，save_key: false时才生效
// 	                var name = (new Date()).Format("MMddhhmmss")+getRandomStr();
//                 var time = new Date().Format("yyyyMMdd");
//                 var key = "smallPic/"+time+"/"+name;
//                 return key;
//             }
//         }
// 	});
	
	//七牛上传
	var paramsJson = {
		cropperWidth: 450, //裁剪宽度
		cropperHeight: 450, //裁剪高度
		moduleName: 'smallPic',  //模块名称
		dealSourceLinkFn: function(sourceLink){  //处理上传图片路径
			var div_imgs = "<div class='pic_div'><a class='delete' onclick='delSmallPic(this)' href='#'>×</a><img src='"+sourceLink+"'  height='100' ></img></div>"
			$("#main_pics_small").html(div_imgs);
			$("#smallPic").val(sourceLink);
		}
	}
	new BtnSelectFileVo('#templatePic_main_small', paramsJson);  //按钮点击选择
}); 

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

function sub(){
//	var pics_small_num = $("#main_pics_small img").length;
//	 if(pics_small_num==0){
//	 	alert("请上传组合小图");
//	 	return false;
//	 }
	 var main_pics_num = $("#dtl_pics img").length;
	 if(main_pics_num==0){
		 $.messager.alert('提示信息', "请添加单品");
	 	return false;
	 }
//	 if(checkBrands()){
		 /*var input_keywords  = $("#add_TgoodsAttrDetail_table td input[name$='careValue']");
		 var n=0;
		 $(input_keywords).each(function(idx){
			 if($.trim($(this).val())!=""){
				 n++;
			 }
		 });
		 if(n==0){
			 $.messager.alert('提示信息', "请填写组合关键词");
			 return false;
		 }*/
		 initPicsOrder();
		 $("#formobj").submit();
//	 }
}

//存草稿
function save(){
//	var pics_small_num = $("#main_pics_small img").length;
//	 if(pics_small_num==0){
//	 	alert("请上传组合小图");
//	 	return false;
//	 }
//	 var main_pics_num = $("#dtl_pics img").length;
//	 if(main_pics_num==0){
//	 	$.messager.alert('提示信息', "请添加单品");
//	 	return false;
//	 }
	 initPicsOrder();
	 $("#publishStatus").val("2");
	 $("#formobj").submit();
}

//校验品牌
/*function checkBrands(){
	var goodsType = $("#goodsType").val();
	if("2"==goodsType){
		var brands = $("#dtl_pics .brand");
		if($(brands).length>1){
			var arrsBrandCode= [];//goodsId的数组
			$(brands).each(function(idx,item){
				arrsBrandCode.push($(this).val());
	    	});
			var nary=arrsBrandCode.sort(); 
			for(var i=0;i<arrsBrandCode.length-1;i++){
				if (nary[i]!=nary[i+1]){ 
					alert("品牌不一致");
					return false;	
				} 
			}
		}
	}
	return true;
}*/

/** 判断数组中是否有重复项*/
function isRepeat(arr){
    var hash = {};
    for(var i in arr) {
        if(hash[arr[i]])
             return true;
        hash[arr[i]] = true;
    }
    return false;
}

function goAdd(data) {
	  if(data.success){
		document.location="tNewGoodsGroupController.do?goAdd";
	  }else{
			$.messager.alert('错误提示', data.msg);
	  }
}

//查询单品
function findGoods() {
	var retailerId = $("#retailerId").val();
	var goodsType = $("#goodsType").val();
	var groupSource = $("#groupSource").val();
	if(retailerId==""&&groupSource=="2"){
		$.messager.alert('错误提示', '请选择零售商');
		return false;
	}
	  var url = "tGoodsController.do?detailList&retailer_Id="+retailerId+"&group_Source="+groupSource+"&goods_Type="+goodsType;
		 $.dialog.setting.zIndex = 2001;
		 if(typeof(windowapi) == 'undefined'){
			 $.dialog({
					content: "url:"+url,
					lock : true,
					title:"选择商品",
					width:1000,
					height: 700,
					cache:false,
				    ok: function(){
				    	iframe = this.iframe.contentWindow;
				    	var selected = iframe.getSelectRows();
				    	if (selected == '' || selected == null ){
				    		$.messager.alert('错误提示', '请选择商品');
				    		return false;
					    }else {
					    	var n = $("#dtl_pics img").length;
					    	if(n+selected.length>15){
					    		$.messager.alert('错误提示', '单品不能超过15个');
					    		return false;
					    	}
//							  selected是要遍历的集合
//							  index就是索引值
//							  domEle 表示获取遍历每一个dom对
						    	$.each( selected, function(index, domEle){
						    		var div_imgs = getDivImgsHtml(domEle);
	 								$("#dtl_pics").append(div_imgs);
						    	});
					    }
				    },
				    cancelVal: '关闭',
				    cancel: true /*为true等价于function(){}*/
				}).zindex();
			} else{
				$.dialog({
					content: "url:"+url,
					lock : true,
					title:"选择商品",
					width:1000,
					height: 700,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe = this.iframe.contentWindow;
					    	var selected = iframe.getSelectRows();
					    	if (selected == '' || selected == null ){
					    		$.messager.alert('错误提示', '请选择商品');
					    		return false;
						    }else {
						    	var n = $("#dtl_pics img").length;
						    	if(n+selected.length>15){
						    		$.messager.alert('错误提示', '单品不能超过15个');
						    		return false;
						    	}
//								  selected是要遍历的集合
//								  index就是索引值
//								  domEle 表示获取遍历每一个dom对
							    	$.each( selected, function(index, domEle){
							    		var div_imgs = getDivImgsHtml(domEle);
		 								$("#dtl_pics").append(div_imgs);
							    	});
						    }
				    },
				    cancelVal: '关闭',
				    cancel: true /*为true等价于function(){}*/
				}).zindex();
			}
	}

//获取单品的图片html
function getDivImgsHtml(domEle){
	var div_imgs = "<div class='dtl_div'><input type='hidden' class='d_g' value='"+domEle.id+"'/><input type='hidden' class='g_c' value='"+domEle.goodsCode+"'/><a class='delete2' onclick='delDtlPic(this)' href='#'>×</a><img src='"+domEle.smallPic
//		+"' width='140' height='140' ></img><div  class='elips'>款号："+domEle.goodsCode+"</div><div><span>现价："+domEle.currentPrice+"</span></div><div>组合价：<input type='text' class='g_p' onclick='focus();' onblur='checkPrice(this)' onkeypress='Public.input.numberInput()' style='width:70px;height:20px' /></div></div>"
		+"'  height='160' ></img><div class='elips'>款号："+domEle.goodsCode+"</div><div><span>原价："+domEle.originalPrice+"</span></div></div>"
		return div_imgs;
}
  
//选择零售商
 /* function findRetailers(){
  	var url = "userController.do?storeUserList";
  	 $.dialog.setting.zIndex = 300;
  	 if(typeof(windowapi) == 'undefined'){
  		 $.dialog({
  				content: "url:"+url,
  				lock : true,
  				title:"选择零售商",
  				width:700,
  				height: 500,
  				cache:false,
  			    ok: function(){
  			    	iframe_doc = this.iframe.contentWindow.document;
  				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
  			    	if ($(sel_tr).length ==0 ){
  			    			$.messager.alert("请选择零售商");
  				    		return false;
  					 }else{
  						 if($(sel_tr).length>1){
  					    		$.messager.alert("只能选择一个零售商");
  					    		return false;
  					    }
  						var retailerId = $(sel_tr).find("td[field='id'] div").text();
						 var retailerName = $(sel_tr).find("td[field='realName'] div").text();
						 $("#retailerId").val(retailerId);
						 $("#retailerName").val(retailerName);
						 clearDetailGoods();
  					 }
  			    },
  			    cancelVal: '关闭',
  			    cancel: true 为true等价于function(){}
  			}).zindex();
  		} else{
  			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择零售商",
				width:700,
				height: 500,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
	  				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
	  			    	if ($(sel_tr).length ==0 ){
	  			    			$.messager.alert("请选择零售商");
	  				    		return false;
	  					 }else{
	  						 if($(sel_tr).length>1){
	  					    		$.messager.alert("只能选择一个零售商");
	  					    		return false;
	  					    }
	  						var retailerId = $(sel_tr).find("td[field='id'] div").text();
							 var retailerName = $(sel_tr).find("td[field='realName'] div").text();
							 $("#retailerId").val(retailerId);
							 $("#retailerName").val(retailerName);
							 clearDetailGoods();
	  					 }
			    },
			    cancelVal: '关闭',
			    cancel: true 为true等价于function(){}
			}).zindex();
  		}
}*/

//零售商名字
var name=$("#retailerName").val();
//清空商品明细
function clearDetailGoods(){
	var nameNew = $("#retailerName").val();
	if(name!=nameNew){
		$("#dtl_pics").empty();
		$("#originalPrice").val(0);
		$("#groupPrice").val(0);
	}
	name = $("#retailerName").val();
	
}

//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'), val = $this.val();
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
			if(id!=null){
				if (id.indexOf("#index#") >= 0){
					$this.attr("id",id.replace('#index#',i));
				}else{
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s+1,e);
					$this.attr("id",id.replace(new_id,i));
				}
			}
			if(onclick_str!=null){
				if (onclick_str.indexOf("#index#") >= 0){
					$this.attr("onclick",onclick_str.replace(/#index#/g,i));
				}else{
				}
			}
		});
		$(this).find('div[name=\'xh\']').html(i+1);
	});
}

//选择品牌
function findBrands(){
	var groupSource = $("#groupSource").val();
	var url ="";
	if("1"==groupSource){//零售商和云商选择品牌列表
//		url = "tBrandShowController.do?findBrandList&isSelfBrand=1";
		url = "baseBrandController.do?list&isForChoose=1";//查询列表
	}else{//后台选择品牌列表
		url = "baseBrandController.do?findBrandList";
	}
	 $.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择 品牌",
				width:700,
				height: 500,
				cache:false,
			    ok: function(){
//			    	iframe = this.iframe.contentWindow;
//			    	var selected = iframe.getSelectRows();
			    	iframe_doc = this.iframe.contentWindow.document;
				    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
			    	if ($(sel_tr).length ==0 ){
//				    		$.messager.alert('错误提示', '请选择品牌');
			    			alert("请选择品牌");
				    		return false;
					 }else{
						 if($(sel_tr).length>1){
//					    		$.messager.alert('错误提示', '只能选择一个品牌');
					    		alert("只能选择一个品牌");
					    		return false;
					    }
						 var brandId="";
					 	if("1"==groupSource){//零售商或云商
						 	brandId = $(sel_tr).find("td[field='brandId'] div").text();
						 	if(brandId == ''){
						 		brandId = $(sel_tr).find("td[field='id'] div").text();
						 	}
						}else{//后台
							brandId = $(sel_tr).find("td[field='id'] div").text();
						}
						 var brandName = $(sel_tr).find("td[field='brandName'] div").text();
						 var brandCode = $(sel_tr).find("td[field='brandCode'] div").text();
						 $("#brandId").val(brandId);
						 $("#brandCode").val(brandCode);
						 $("#brandName").val(brandName);
					 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择商品类别",
				width:700,
				height: 500,
				zIndex:1999,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
					    var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
				    	if ($(sel_tr).length ==0 ){
				    			alert("请选择品牌");
					    		return false;
						 }else{
							 if($(sel_tr).length>1){
						    		alert("只能选择一个品牌");
						    		return false;
						    }
							 var brandId="";
						 	if("1"==groupSource){//零售商或云商
							 	brandId = $(sel_tr).find("td[field='brandId'] div").text();
							 	if(brandId == ''){
							 		brandId = $(sel_tr).find("td[field='id'] div").text();
							 	}
							}else{//后台
								brandId = $(sel_tr).find("td[field='id'] div").text();
							}
							 var brandName = $(sel_tr).find("td[field='brandName'] div").text();
							 var brandCode = $(sel_tr).find("td[field='brandCode'] div").text();
							 $("#brandId").val(brandId);
							 $("#brandCode").val(brandCode);
							 $("#brandName").val(brandName);
						 }
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}

//初始化下标
function resetTdNum(tableId) {
	$tbody = $("#"+tableId);
	$tbody.find('input[type=text]').each(function(i){
//		$(':input', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id');
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
//		});
	});
}


//删除图片
function delPic(obj){
	   $(obj).parent().remove();
}
//删除小图
function delSmallPic(obj){
	$(obj).parent().remove();
	$("#smallPic").val("");
}

//删除单品明细
function delDtlPic(obj){
	$(obj).parent().empty().remove();
} 

/*
*初始化单品明细的name
*/
function initPicsOrder(){
	var $divs = $("#dtl_pics div.dtl_div");//单品div列表
	var goodsCode = "";
	$.each($divs, function(index, domEle){
		$(this).find("input[class='d_id']").attr("name","tGoodsPicDetails["+index+"].id");//明细ID
		$(this).find("input[class='d_g']").attr("name","tGoodsPicDetails["+index+"].detailGoodsId");//商品ID
		$(this).find("input[class='g_c']").attr("name","tGoodsPicDetails["+index+"].goodsCode");//商品款号
		var goods_code =  $(this).find("input[class='g_c']").val();
		goodsCode+=goods_code+"|";
	});
	if(goodsCode!=""){
		goodsCode = goodsCode.slice(0, -1);
	}
	$("#goodsCode").val(goodsCode);
   return true;
}	

 
 //校验价格(20160914修改为组合中单品可以低于最低价)
 /*function checkPrice(obj){
//	 var lowestPrice = $(obj).parent().parent().find("input[class='l_p']").val();
	 var originalPrice = $(obj).parent().parent().find("input[class='o_p']").val();
	 var price = $(obj).val();
//	 if((lowestPrice-price)>0){
//		 alert("输入价格低于商品最低价："+lowestPrice);
//		 $(obj).focus();
//	 }
	 if((price-originalPrice)>0){
		 alert("输入价格高于商品原价："+originalPrice);
		 $(obj).focus();
	 }
	 calGroupPrice();
	 calOrigPrice();
	//初始化图片和视频下标
	 initPicsAndVideos();
 }
 
 //计算组合价
 function calGroupPrice(){
	 var $group_prices = $("#dtl_pics input[class='g_p']");
	 var groupPriceAmout =0;
	 $.each( $group_prices, function(index, domEle){//循环赋值
		 groupPriceAmout +=$(this).val()*1;
	 });
	 $("#groupPrice").val(groupPriceAmout.toFixed(0));
	 
 }

 function calOrigPrice(){
	 var $original_price = $("#dtl_pics input[class='o_p']");
	 var originalPriceAmout =0;
	 $.each( $original_price, function(index, domEle){//循环赋值
		 originalPriceAmout +=$(this).val()*1;
	 });
	 $("#originalPrice").val(originalPriceAmout.toFixed(0));
 }
 */
 /*
 parentId:上级ID,lvl:类目级别，sel_id:展示列表的下拉框的ID
 */
 function getUnderCategories(parentId,lvl,sel_id){
	  if(parentId!=""){
		//获取城市
		  	var url = "categoryController.do?getUnderCategories&parentId="+parentId;
		  	$.ajax({
		  		async : false,
		  		cache : false,
		  		type : 'POST',
		  		url : url,// 请求的action路径
		  		error : function() {// 请求失败处理函数
		  		
		  		},
		  		success : function(data) {
		  			var d = $.parseJSON(data);
		  			if (d.success) {
		  				$("#"+sel_id).empty();
		  				var ops = "<option value=''>---请选择---</option>";
		  				for(i=0;i<d.obj.length;i++){
		  					if(lvl=="3"){
		  						if(d.obj[i].areaName=="组合"){
		  							ops+="<option value='"+d.obj[i].areaId+"'>"+d.obj[i].areaName+"</option>"
		  						}
		  					}else{
		  						ops+="<option value='"+d.obj[i].areaId+"'>"+d.obj[i].areaName+"</option>"
		  					}
		  				}
		  				$("#"+sel_id).append(ops).show();
		  				if(lvl=="2"){
		  					$("#thridCategoryId").empty().hide();
		  				}
		  			}
		  			
		  		}
		  	});
	  }else{
		  if(lvl=="3"){
			  $("#thridCategoryId").empty().hide();
		  }else if(lvl=="2"){
			  $("#subCategoryId").empty().hide();
			  $("#thridCategoryId").empty().hide();
		  }
		  
	  }
 }
 