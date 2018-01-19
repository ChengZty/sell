
var posterId;//海报id
var coverPic;//海报图片

$(function(){
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
	
	
	search();//进页面就默认查询一次
	//绑定查询
	$(".btn_search").on("click",function(){
		search();
	});
	//绑定复制链接
	$("#dataBody").on("click",'.btn_goods_link',function(){
		//海报id，海报图片
		var parent = $(this).parent();
		posterId = parent.find("#id").val();//海报id
		coverPic = parent.find(".goods_img").attr("src");//图片链接
		//确认弹框
    	$btnConfirm.click();  //确认弹框并关闭iframe
	});
	//上一页
	$(".btn_up_box").on("click",goToUpPage);
	//下一页
	$(".btn_next_box").on("click",goToNextPage);
	//指定页
	$(".page_skip_box").on("click",goToPage);
})

//查询列表
function search(page){
	if(typeof(page)=="undefined"){ 
		$("#page").val(1);//搜索重置当前页面
		$("#page_input").val("");//搜索重置当前页面
	} 
   	var params = $("#form").serialize();
     $.ajax({
		url : "tPosterController.do?mydatagrid&"+params,
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
					html+='<li class="goods_item"> <div class="goods_item_box"><input type="hidden" id="id" value="'+obj[i].id+'"/><img class="goods_img" src="'+obj[i].cover_pic+'" alt="">'
						+'<p class="goods_name">'+obj[i].title+'</p><button type="button" class="btn_goods_link">选择链接</button>'
//						+'<p class="goods_name">'+obj[i].title+'</p><p class="info_url">'+obj[i].url+'</p><button type="button" class="btn_goods_link">选择链接</button>'
						+'</div></li>';
					} 
               	$("#dataBody").html(html);
               	$("#page_input").attr("max",d.totalPage);//总页数限制
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

function copyUrl(obj){
	var id = $(obj).parent().find("input").val();
	var urlText=document.getElementById("urlText");
	urlText.value = urlPre+id;
	urlText.select();
	document.execCommand("Copy");
	alert("复制成功!");
}
function uploadCallback(){
	
}