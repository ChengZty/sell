
$(document).ready(function () {
	//layer组件 添加扩展
	layer.config({
		extend: 'extend/layer.ext.js'
	});
	
	//找到easyui的iframe对象
	var dialogIframe = parent.document.getElementsByName(window.name)[0];
	var $dialogIframe = $(dialogIframe);
	//easyui 的确认按钮jquery对象
	var $btnConfirm = $dialogIframe.parents('.ui_dialog').find('.ui_state_highlight');
	//easyui 的关闭按钮jquery对象
	var $btnClose = $dialogIframe.parents('.ui_dialog').find('.ui_state_highlight').next("input");
	//隐藏确认,取消按钮
	$btnConfirm.hide();
	$btnClose.hide();
	
    var DialogBox = (function () {
        function DialogBox(xj_div_box){
            if(this instanceof DialogBox){
                this.$box = $(xj_div_box);
                this.$picList = this.$box.find('.pic_box .pic_ul .pic_item');
                this.$classify_item_box = this.$box.find('.classify .classify_item_box');
                this.$new_classify = this.$box.find('.classify .new_classify');
                this.$btn_cancel = this.$box.find('.classify .new_classify_button .btn_cancel');
                this.$btn_affirm = this.$box.find('.classify .new_classify_button .btn_affirm');
            }else{
                return new DialogBox(xj_div_box);
            }
        }
        DialogBox.prototype = {
            constructor: DialogBox,
            //li点击监听
            /*picListClickListener:function () {
                var _this = this;
                this.$picList.each(function (index,doc) {
                    var item = $(doc);
                    item.on('click',function () {
                        item.toggleClass('active');
                    })
                });
                this.$classify_item_box.on('click',function () {
                    _this.$new_classify.toggleClass('active');
                });
            },
            btnCancelListener:function () {
                var _this = this;
                this.$btn_cancel.on('click',function () {
                    _this.$new_classify.removeClass('active');
                })
            },
            btnAffirmlListener:function () {
                var _this = this;
                this.$btn_affirm.on('click',function () {
                    
                });
            }*/
        };
        return DialogBox;
    })();
//    var content = new DialogBox('.xj_div_box');
//    content.picListClickListener();
//    content.btnCancelListener();
//    content.btnAffirmlListener();
    //图片分类显示
    searchImgClass();
    
	//图片分页查询
    search();//进页面就默认查询一次
	//上一页
	$(".btn_up_box").on("click",goToUpPage);
	//下一页
	$(".btn_next_box").on("click",goToNextPage);
	//指定页
	$(".page_skip_box").on("click",goToPage);
	
	
	
	
	//上传图片
    $("#btn_uploading").click(function(){
    	$("#files").click();
    })
    
    //添加分类
    $(".xj_div_box").on('click','.classify_item_addClass',function () {
    	var count = $("#ImgClass").children().length;
    	if(count > 10){
    		alert("你不能创建更多的分类");
    		return;
    	}else{
        	$(".new_classify").toggleClass('active');
    	}
    });
    
   //点击图片分类
//   $(".xj_div_box").on("click", ".classify_item:not(.classify_item_addClass)", function(){
	   $(".xj_div_box").on("click", "li.classify_item", function(){
    	var $curLi = $(this);
		var isNoClassify = $curLi.attr('pic_id') === '177ed329ecc94a9b84bb7007241fef23';
    	$(".classify_item").css({"background-color":"#FFFFFF"});
    	$curLi.css({"background-color":"#E5E5E5"});
    	
		//如果是  未分组  则不做处理
		$(".del_img").css('display','none');
		$(".btn_edit_classify").css('display','none');
		if(!isNoClassify){
			$curLi.find('.del_img').css('display','block');
			$curLi.find('.btn_edit_classify').css('display','block');
		}
    	//chooseClass($curLi.attr('pic_id'));
    	
    	chooseClassEntity = $(this);
    	//赋值分类id并查询图片
    	$("#classId").val($curLi.attr('pic_id'));//如果是全部的话就传入0给后台
    	classId = $("#classId").val();
    	$("#page").val(1);//传值用
    	$("#curr_page").html(1);//显示用
    	search();//点击分类从第一页开始查询
    });
    
   	//选择图片
   $(".xj_div_box").on('click','.pic_item',function () {
		//图片的显示状态
	  	if($(this).hasClass('active')){
	  		$(this).removeClass('active');
	  	}else{
	  		// $(".xj_div_box").find('.pic_item').removeClass('active');  //不允许选择多张图片
	  		$(this).addClass('active');
	  	}
	  	
	  	//判断确认按钮的显示
	  	if($(this).hasClass('active')){
	  		choosePicID = $(this).attr('picid');
	  		choosePicUrl = $(this).attr('picUrl');
	  		chooseNumber = 1;
	  		$(".btn_affirm_upPic").css({"background":"#D0DDD9","color":"#243938"});
	  		$(".btn_affirm_upPic").removeAttr("disabled");
	  	}else{
	  		choosePicID = 0;
	  		choosePicUrl = "";
	  		chooseNumber = 0;
	  		$(".btn_affirm_upPic").attr("disabled", true);
	  		$(".btn_affirm_upPic").css({"background":"#CBCBCB","color":"#B2B2B2"});
	  	}
	  	choosePicEntity = $(this);
	  	//改变选择的个数
	  	$("#chooseNumber").html(chooseNumber);
	})
    //点击确定图片确认按钮的事件
    $(".xj_div_box").on('click','#upPicBtn',function () {
    	$(".new_classify").removeClass('active');
    	//确认弹框
    	$btnConfirm.click();  //确认弹框并关闭iframe
    })
    //点击添加分类取消按钮的事件
    $(".xj_div_box").on('click','.btn_cancel',function () {
    	$(".new_classify").removeClass('active');
    })
    //点击取消按钮的事件
    $(".xj_div_box").on('click','.btn_cancel_close',function () {
    	$btnClose.click();  //关闭iframe
    })
    //点击删除分类按钮的事件
    $(".xj_div_box").on('click','.del_img',function () {
    	var classId = $(this).attr('classId');
    	delPicClass(classId);
    })
	//点击编辑按钮
    $(".xj_div_box").on('click','.btn_edit_classify',function () {
		var $curButton = $(this);
		var $curLi = $curButton.parents('.classify_item');
		var $curName = $curLi.find('.classify_name');
		var nameStr = $curName.text().trim();
		var classId = $curLi.attr('pic_id');
    	editClassify(nameStr, classId);

		return false;  //不冒泡
    })

    //设置按钮
	$("#upPicBtn").attr("disabled", true);

	//绑定 删除图片 按钮
	$('.div_content').on('click', '.btn_delete_pic', function(){
		var $picActive = $('.pic_box').find('.pic_item.active');
		if($picActive.length > 0){
			var picIdStr = '';
			$picActive.each(function(index, domEle){
				picIdStr += $(domEle).attr('picid') + ',';
			});
			deleltePic(picIdStr.substring(0, picIdStr.length - 1));
		}
	});
});

function uploadCallback(){
	
}
//图片分类查询列表
function searchImgClass(){
   $.ajax({
		url : "tSmsPicClassController.do?getPicClass",
		type : 'get',
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			console.log(d);
			var obj = d.obj;//是object对象
			var map = d.attributes;
			totalCount = map.total;
			if (d.success) {
				var html = '<li class="classify_item classify_item_bg" pic_id="0" id="allPic">全部图片(<span id="allPic_count" >'+map.total
				+'</span>)</li>';
				for(var i in obj){//用javascript的for/in循环遍历对象的属性 
					if("未分组" == obj[i].name){
						weifenleiID = obj[i].id
					}
					html+='<li class="classify_item"  alt="" pic_id="'+obj[i].id+'"><div class="btn_edit_classify"></div><span class="classify_name">'+obj[i].name+'</span>(<span id="class_count_'+obj[i].id+'" >'+obj[i].count
						+'</span>)<img class="del_img" src="webpage/daggerSrc/choosePicture/img/delClass.png" '
						+'style="width:20px; height:20px; margin:10px 0px;float:right;display:none" classId="'+obj[i].id+'"></li>';
				}
             	$("#ImgClass").html(html);
			}
		}
	});
}


//图片查询列表
function search(page){
 	var params = $("#form").serialize();
   $.ajax({
		url : "tSmsPicInfoController.do?mydatagrid&"+params,
		type : 'post',
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			var obj = d.results;//是object对象
			if (d.success) {
				$("#total").text(d.total);//总记录数
				$("#totalPage").text(d.totalPage);//总页数
				$("#page_input").attr("max",d.totalPage);//页码输入限制
				$("#curr_page").text(d.page);//当前页数
				$("#page").val(d.page);//当前页数
				$("#rows").val(d.rows);//每页记录数
				var html = '';
				for(var i in obj){//用javascript的for/in循环遍历对象的属性 
					html+='<li class="pic_item fl"  picid="'+obj[i].id+'" picUrl="'+obj[i].pic_url+'"> <div ><input type="hidden" value="'+obj[i].id+'"/><img class="img_content" src="'+obj[i].pic_url+'" alt="">'
						+'<div class="img_name_box"><p class="img_name">'+obj[i].name+'</p></div>'
						+'<div class="mask"><img class="choose_img" src="webpage/daggerSrc/choosePicture/img/choose.png" alt=""></div>'
						+'</div></li>';
					}  
             	$("#dataBody").html(html);
			}
		}
	});
}

//上一页
function goToUpPage(){
	var curr_page = $("#curr_page").text()-0;
	if(curr_page > 1){
		$("#page").val(curr_page-1);
		search(curr_page-1);
	}
}
//下一页
function goToNextPage(){
	var curr_page = $("#curr_page").text()-0;
	var totalPage = $("#totalPage").text()-0;
	if(curr_page<totalPage){
		$("#page").val(curr_page+1);
		search(curr_page+1);
	}
}

//指定页
function goToPage(){
	var page_input = $("#page_input").val()-0;
	var totalPage = $("#totalPage").text()-0;
	if(page_input<=totalPage&&page_input>0){
		$("#page").val(page_input);
		search(page_input);
	}
}




//xh
var classId = 0;  //当前的图片类型
var nowPage = 1;  //当前的页数
var totalPage = 1;  //分类的总页数
var totalCount = 0;  //
var chooseNumber = 0;   //当前选择的图片数
var weifenleiID = 0;   //当前选择的图片数
var chooseClassEntity = null;  //当前选择的分类按钮
var choosePicEntity = null;  //当前选择的tupian
var choosePicID = 0;   //选择的图片
var choosePicUrl = "";   //选择的图片的url


//上传图片
function doChange(){
	var fileselect = $('#files')[0].files;
	for(var i = 0; i < fileselect.length; i++){ 
		var image = fileselect[i];
		var oMyForm = new FormData();
		oMyForm.append("fileselect", image);
		oMyForm.append("classId", classId);
		$.ajax({
			type : "POST",
			url : "tSmsPicInfoController.do?uploadClassPic&sessionId=${pageContext.session.id}",
			data : oMyForm,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			dataType : "json",
			success : function(data) {
				var picInfos = data.obj;
				var picStr="";
				picStr +='<li class="pic_item fl"  picid="'+picInfos.id+'" picUrl="'+picInfos.picUrl+'"><div><img class="img_content" src="'+picInfos.picUrl+'" alt="" img_id="'+picInfos.id+'">';
				picStr +='<div class="img_name_box" ><p class="img_name">'+picInfos.name+'</p></div>';
				picStr +='<div class="mask"><img class="choose_img" src="webpage/daggerSrc/choosePicture/img/choose.png" alt=""></div></div></li>';
				
				$("#dataBody").html(picStr + $("#dataBody").html());
				//$("#pic_ul").children('.pic_item').eq(0).insertBefore(picStr);
				if($("#dataBody").children('.pic_item').length > 10){
					$("#dataBody").children('.pic_item').eq(9).nextAll().hide();
				}
				totalCount = totalCount + 1 ;
				$("#allPic_count").html(totalCount);//总数
				
				//判断是否是全部图片的上传
				if(chooseClassEntity == null || chooseClassEntity.attr('pic_id') == "0"){
					var class_number = $("#class_count_" + weifenleiID).html();
					$("#class_count_" + weifenleiID).html(parseInt(class_number, 10) + 1);
				}else{
					var class_number = $("#class_count_" + chooseClassEntity.attr('pic_id')).html();
					$("#class_count_" + chooseClassEntity.attr('pic_id')).html(parseInt(class_number, 10) + 1);
				}
			}
		});
	}
	$("#files").val("");
}

//增加图片分类
function addPicClass(){
	var input_class_name = $("#input_class").val();
	if(input_class_name == "" || input_class_name == null){
    	$(".new_classify").removeClass('active');
	}else{
		var oMyForm = new FormData();
		oMyForm.append("classname", input_class_name);
		$.ajax({
			type : "POST",
			url : "tSmsPicClassController.do?addPicClass&sessionId=${pageContext.session.id}",
			data :oMyForm,
			async : true,
			cache : false,
			contentType : false,
			processData : false,
			dataType : "json",
			success : function(data) {
				if(data.msg == "false"){
				}else{
					var picClass = data.obj;
					var classStr= '<li class="classify_item" pic_id="'+picClass.id+'"><div class="btn_edit_classify"></div><span class="classify_name">'+picClass.name +'</span>(<span id="class_count_'+picClass.id+'"> '+picClass.count
					+' </span>)<img class="del_img" src="webpage/daggerSrc/choosePicture/img/delClass.png" '
					+'style="width:20px; height:20px; margin:10px 0px;float:right;display:none" classId="'+picClass.id+'"></li>'
					$("#ImgClass").append(classStr);
				}
			}
		});
		//动态绑定事件
	   $(".xj_div_box").on("click", ".classify_item:not(.classify_item_addClass)", function(){
	    	var $curLi = $(this);
	    	$(".classify_item").css({"background-color":"#FFFFFF"});
	    	$(this).css({"background-color":"#E5E5E5"});
	    	//chooseClass($curLi.attr('pic_id'));
	    	chooseClassEntity = $(this);
	    });
	    //点击删除分类按钮的事件
	    $(".xj_div_box").on('click','.del_img',function () {
	    	var classId = $(this).attr('classId');
	    	delPicClass(classId);
	    })
		$(".new_classify").removeClass('active');
	}
}

//删除图片分类
function delPicClass(classId){
	layer.confirm("确定要删除这个分组吗？", {
		  btn: ['确定','关闭'] //按钮
	  	  ,title :'提示信息'
		}, function(){
			layer.closeAll('dialog');	//关闭弹出的确认框
			var oMyForm = new FormData();
			oMyForm.append("classId", classId);
			oMyForm.append("unclassified", weifenleiID);
			$.ajax({
				type : "POST",
				url : "tSmsPicClassController.do?deletePicClass&sessionId=${pageContext.session.id}",
				data :oMyForm,
				async : true,
				cache : false,
				contentType : false,
				processData : false,
				dataType : "json",
				success : function(data) {
				    //图片分类显示
				    searchImgClass();
					//图片分页查询
				    search();//进页面就默认查询一次
				}
			});
		}, function(){});  
}

//删除图片
function deleltePic(picIdStr){
//	console.log(picIdStr);
	layer.confirm("确定要删除这些图片吗？", {
		  btn: ['确定','关闭'] //按钮
	  	  ,title :'提示信息'
	}, function(){
		layer.closeAll('dialog');	//关闭弹出的确认框
		$.ajax({
			url : "tSmsPicInfoController.do?doBatchDel",
			type : 'post',
			data :{
				ids : picIdStr
			},
			async : true,
			cache : false,
			success : function(data) {
				console.log(data);
				//图片分类显示
				searchImgClass();
				//图片分页查询
				search();//进页面就默认查询一次
			}
		});
	}, function(){});  
}

//编辑分类
function editClassify(oldClassName, classId){
	layer.prompt({title: '请输入新分类名称', formType: 1}, function(newClassName, index){
		layer.close(index);
		console.log(classId);
		console.log(newClassName);
		if(oldClassName == newClassName){
			return;
		}
		$.ajax({
			url : "tSmsPicClassController.do?doUpdatePicClassName&classId="+classId+"&name="+newClassName,
			type : 'get',
			async : true,
			cache : false,
			success : function(data) {
				console.log(data);
				//图片分类显示
				searchImgClass();
				//图片分页查询
				search();//进页面就默认查询一次
			}
		});
	});	
	//设置默认值
	$('.layui-layer-prompt .layui-layer-input')
		.val(oldClassName)
		.attr('type', 'text');
}