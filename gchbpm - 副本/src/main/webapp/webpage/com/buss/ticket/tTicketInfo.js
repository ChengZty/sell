$(function(){
	//绑定点击事件
	bindTypeClick();
	//初始化上传图片
	uploadPic();
	
});

/**
 * 绑定点击事件
 */
function bindTypeClick(){
	$("input[name='type']").on("click",function(){
//		$("#type").on("click",function(){
		var type=$(this).val();
		if("1"==type){//红包
			$("#span1").show();
			$("#span2").hide();
			$("#leastMoney").val("");
			$("input[name='useType']").removeAttr("checked");
			$("#sheetLimit").val("1");
			$("#type_td").attr("colspan","3");
			$(".hide_td").hide();
		}else if("2"==type||"3"==type){//代金券，折扣券
			$("#span2").show();
			$("#span1").hide();
			$("input[name='useType']:eq(0)").attr("checked","checked");
			$("#sheetLimit").val("1");
			$("#type_td").removeAttr("colspan");
			$(".hide_td").show();
		}
	})
}

/**
 * 上传缩略图
 */
function uploadPic(){
    // var P = new QiniuJsSDK();
//     var uploaderPic = P.uploader({
//         runtimes: 'html5,flash,html4',      // 上传模式，依次退化
//         browse_button: 'pic_btn_div',         // 上传选择的点选按钮，必需
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
//         max_file_size: '50kb',             // 最大文件体积限制
//         flash_swf_url: 'plug-in/plupload/js/Moxie.swf',  //引入flash，相对路径
//         max_retries: 3,                     // 上传失败最大重试次数
//         dragdrop: true,                     // 开启可拖曳上传
//         drop_element: 'container_pic',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
//         chunk_size: '4mb',                  // 分块上传时，每块的体积
//         auto_start: true,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
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
//                    var div_imgs = "<img src='"+sourceLink+"'  height='100' ></img>"
// 	   				$("#pic_div").html(div_imgs);
// 	   				$("#picUrl").val(sourceLink);
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
//                 var key = directory+"ticketPic/"+time+"/"+name;
//                 return key
//             }
//         }
//     });

	//七牛上传
    var paramsJson = {
        cropperWidth: 180, //裁剪宽度
        cropperHeight: 180, //裁剪高度
        moduleName: 'ticketPic',  //模块名称
        dealSourceLinkFn: function(sourceLink){  //处理上传图片路径
			var div_imgs = "<img src='"+sourceLink+"'  height='100' ></img>"
			$("#pic_div").html(div_imgs);
			$("#picUrl").val(sourceLink);
        }
    }
    new BtnSelectFileVo('#pic_btn_div', paramsJson);  //按钮点击选择
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

//获取已选品牌列表
function getSelectedBrandsGoods(type,retailerId){
	var id = $("#id").val();
	var ticketStatus = $("#ticketStatus").val();
//	console.log(id);
	if(1==type){//商品
		title = "已选商品";
	}else if(2==type){//品牌
		title = "已选品牌";
	}
	$("#brand_goods").panel(
			{
				title : title,
				href:"tTicketGoodsController.do?list&ticket_Id="+id+"&tktStatus="+ticketStatus+"&rId="+retailerId+"&type="+type+"&isView="+isView
			}
	);
}

//查询店铺列表
function findStores(){
	var storeIds = $("#storeIds").val();
	var url = "tStoreController.do?tStoreForTicket&storeIds="+storeIds;
	$.dialog.setting.zIndex = 300;
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择店铺",
				width:600,
				height: 400,
				cache:false,
			    ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
			    	 var f = getStores(iframe_doc);
				     return f;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		} else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择店铺",
				width:600,
				height: 400,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	 iframe_doc = this.iframe.contentWindow.document;
			    	 var f = getStores(iframe_doc);
				     return f;
			    },
			    cancelVal: '关闭',
			    cancel: true 
			}).zindex();
		}
}

//选择店铺并且更新已选数量
function getStores(){
	var flag = false;
	var storeIds = "";
	var storeNames = "";
	var n=0;
	var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
		if ($(sel_tr).length ==0 ){
			alert("请选择店铺");
    		return flag;
	 }else{
			$(sel_tr).each(function(){
				var storeId = $(this).find("td[field='id'] div").text();
				var storeName = $(this).find("td[field='name'] div").text();
				storeIds+=storeId+",";
				storeNames+=storeName+",";
				n++;
			})
		  flag = true;
	 }
	 $("#storeIds").val(storeIds);
	 $("#store_num").text(n);
	return flag;
}

/**
 * 获取优惠券的总的品牌/商品数量
 * @param id
 * @param type
 */
function getTotalNum(id,type){
	$.ajax({
		url :"tTicketGoodsController.do?getTotalNum&ticketId="+id+"&type="+type,
		type : 'post',
		async: false,
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				var msg = d.msg;
				var resultNum = d.obj-0;
				var totalNum = 0;//品牌和商品的总个数
				if("1"==type){
					$("#goods_num").text(resultNum);
					totalNum = resultNum+($("#brands_num").text()-0);
				}else if("2"==type){
					$("#brands_num").text(resultNum);
					totalNum = resultNum+($("#goods_num").text()-0);
				}
				var oldUsingRange = $("#usingRange").val();//修改前的使用范围 
				var newUsingRange = "1";//修改后的使用范围  默认全馆
				if(totalNum>0){
					newUsingRange = "2"//品牌/商品
				}
				$("#usingRange").val(newUsingRange);
//				console.log("oldUsingRange:"+oldUsingRange+",newUsingRange:"+newUsingRange);
				if(oldUsingRange!=newUsingRange){
					if("edit"==pageAction){//修改才判断
						hasChanged = true;//使用范围发生变化
					}
				}
			}
		}
	});
}

/**
 * 校验数据
 * 折扣，使用条件，总张数，店铺，使用规则
 */
function checkData(){
	var flag = true;
	var type = $("input[name='type']:checked").val();
	if(type=="2"||type=="3"){//满减券，折扣券
		var discount = $("#faceValue").val();//面额/折扣
		var leastMoney = $("#leastMoney").val();//使用条件
		if(isNaN(leastMoney)){
			tip("使用条件不是有效值");
			$("#leastMoney").focus();
			flag = false;
		}
		if(leastMoney==""){
			tip("请填写使用条件");
			$("#leastMoney").focus();
			flag = false;
		}
		if(leastMoney!=""){
			var useType = $("input[name='useType']:checked").val();
			if(useType==undefined){
				tip("请选择使用条件的类型");
				flag = false;
			}
		}
		if(type=="2"){//满减券
			if(discount-0>leastMoney-0){
				var useType = $("input[name='useType']:checked").val();
				if(useType=="1"){
					tip("面额不能大于券的最低使用金额");
					$("#leastMoney").focus();
					flag = false;
				}
			}
		}
		/*if(type=="3"){//折扣券
			if(discount-0>10||discount-0<0){
				tip("折扣不是有效值");
				$("#faceValue").focus();
				flag = false;
			}
		}*/
	}
	var sheetLimit = $("#sheetLimit").val();//限制使用张数
	if(!/^\d+$/.test(sheetLimit)){//是否正整数
		tip("最多使用数量不是有效值"); 
		$("#sheetLimit").focus();
		flag = false;
	} 
	var sheetTotal = $("#sheetTotal").val();//总张数
	if(sheetTotal!=""&&!/^\d+$/.test(sheetTotal)){
		tip("总张数不是有效值");
		$("#sheetTotal").focus();
		flag = false;
	}
	var storeIds = $("#storeIds").val();//店铺IDs
	if(storeIds==""){
		tip("请选择店铺");
		flag = false;
	}
	var remark = $("#remark").val();//使用规则
	if(remark==""){
		tip("请填写使用规则");
		flag = false;
	}
	return flag;
}

/**
 * 保存
 */
function sub(){
	$("#ticketInfo").submit();
}

/**
 * 返回
 */
function back(){
	document.location="tTicketInfoController.do?listOfRetailer";
}

/**
 * 返回
 */
function back2(){
	//如果修改的时候添加了品牌或者商品却没有保存，使用范围的值变了(如从全馆变成商品的)，但是没有保存就点了返回，此时先调用返回
	if(hasChanged){
		console.log("hasChanged:"+hasChanged);
		hasChanged = false;
		sub();
	}else{
		document.location="tTicketInfoController.do?listOfRetailer";
	}
}