function back() {
  document.location="tBrandShowController.do?tBrandShowListOfRetailer";
}

function backList(data) {
	if(data.success){
		document.location="tBrandShowController.do?tBrandShowListOfRetailer";
	}
}

//获取随机ID
function getRandomID(){
	  var str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	  var n = 8, s = "";
	  for(var i = 0; i < n; i++){
	      var rand = Math.floor(Math.random() * str.length);
	      s += str.charAt(rand);
	  }
	  return s;
}

//图片加商品tags(锚点图片)
function addTags(obj,id){
	 var src = $(obj).attr("src");
	 if(src!=""){
		 $("#pic_add_tags").attr("src",src);
		 var cnt = $("#"+id+"_cnt").val();
		 $("#addTagsDiv div.ndd-drawables-container").attr("id","cnt_"+id).empty().html(cnt);
		 $("#addTagsDiv").show();
	 }
}

//查询单品，包括云仓，排除相斥的(锚点图片)
function findGoods(obj) {
  var p_elmt = $(obj);//当前p标签
  var retailerId = $("#retailerId").val();
	  var url = "tGoodsController.do?singleList&retailer_Id="+retailerId;
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
				    		alert('请选择商品');
// 				    		$.messager.alert('错误提示', '请选择商品');
				    		return false;
					    }else {
					    	if(selected.length>1){
					    		alert('只能选择一个商品');
// 					    		$.messager.alert('错误提示', '只能选择一个商品');
					    		return false;
					    	}
					    	var goodsName = selected[0].goodsName.substring(0,5);
					    	var brandName = selected[0].brandName;
					    	var currentPrice = selected[0].currentPrice;
					    	$(p_elmt).html(goodsName+"...</br>"+brandName);
//					    	$(p_elmt).html(goodsName+"...</br>"+brandName+"</br>￥"+currentPrice+"RMB");
					    	$(p_elmt).parent().parent().attr("src",""+selected[0].id);
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
					width:1000,
					height: 700,
					zIndex:1999,
					parent:windowapi,
					cache:false,
				     ok: function(){
				    	 iframe = this.iframe.contentWindow;
					    	var selected = iframe.getSelectRows();
					    	if (selected == '' || selected == null ){
					    		alert('请选择商品');
//	 				    		$.messager.alert('错误提示', '请选择商品');
					    		return false;
						    }else {
						    	if(selected.length>1){
						    		alert('只能选择一个商品');
//	 					    		$.messager.alert('错误提示', '只能选择一个商品');
						    		return false;
						    	}
						    	var goodsName = selected[0].goodsName.substring(0,5);
						    	var brandName = selected[0].brandName;
						    	var currentPrice = selected[0].currentPrice;
						    	$(p_elmt).html(goodsName+"...</br>"+brandName);
//						    	$(p_elmt).html(goodsName+"...</br>"+brandName+"</br>￥"+currentPrice+"RMB");
						    	$(p_elmt).parent().parent().attr("src",""+selected[0].id);
							 }
				    },
				    cancelVal: '关闭',
				    cancel: true /*为true等价于function(){}*/
				}).zindex();
			}
	}
//确定(锚点图片)
function subPicMap(){
  closePicDiv();
  //TODO 传值html
  var container = $("#addTagsDiv div.ndd-drawables-container");
  var pic_maps = $(container).find("div.ndd-drawable");
  if($(pic_maps).length>0){
	  var n=0;
	   var content = '[';
	   $(pic_maps).each(function(){
		   var goodsId = $(this).find("a").attr("src");
		   if(goodsId!=""){
			   n++;
			   content+="{";
			   var id = $(this).attr("id");
			   var p = $(this).find("p").html();
			   var l = $(this).css("left");
			   var t = $(this).css("top");
			   if(l.indexOf("px")>0){
				   l=l.slice(0,-2);
				   t=t.slice(0,-2);
				   content+='"id":"'+id+'","l":"'+(l*100/div_width).toFixed(2)+'%","t":"'+(t*100/div_height).toFixed(2)+'%","goodsId":"'+goodsId+'","p":"'+p+'"';
			   }else if(l.indexOf("%")>0){
				   content+='"id":"'+id+'","l":"'+l+'","t":"'+t+'","goodsId":"'+goodsId+'","p":"'+p+'"';
			   }
		   		content+="},";
			 }
		   });
	   if(content.length>1){
		   content = content.slice(0,-1);
	   }
	   content+=']';
	   var container_div_id = $(container).attr("id");
	   var id = container_div_id.substring(4);//div单元的ID
	   if(n>0){
		   $("#"+id).find("input[class='picMapContent']").val(content);//用于保存黄点数据信息(只保存选择了的商品)
	   }else{
		   $("#"+id).find("input[class='picMapContent']").val("");
	   }
	   $("#"+id+"_cnt").val($(container).html());//暂存黄点信息到对应的div隐藏input中,用于回显
//	   $(container).empty();
  }
}

//关闭div(锚点图片)
function closePicDiv(){
  $("#addTagsDiv").hide();
}

//删除锚点图片
function delPic(obj){
	   $(obj).parent().remove();
	   var n = $("#aaaaaaa").find("div.pic_div").length;
	   if(n<5){
		   $("#pic_u").show();
	   }
}

//设置图片黄点信息
function setPicContent(){
	var flag = true;
	var pic_divs = $("#aaaaaaa").find("div.pic_div");
	if($(pic_divs).length>0){
		var n=0;
		$(pic_divs).each(function(){
			$(this).find("input.id").attr("name","detailPics["+n+"].id");
			$(this).find("input.remark").attr("name","detailPics["+n+"].remark");
			$(this).find("input.picUrl").attr("name","detailPics["+n+"].picUrl");
			$(this).find("input.picMapContent").attr("name","detailPics["+n+"].picMapContent");
			n++;
		 });
	}
	return flag;
}