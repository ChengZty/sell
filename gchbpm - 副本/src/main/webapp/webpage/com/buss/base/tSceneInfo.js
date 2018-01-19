function sub(){
		$("#formobj").submit();
  }

function back(){
  document.location="tSceneInfoController.do?list";
}

function backList(data) {
	if(data.success){
		document.location="tSceneInfoController.do?list";
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

//查询上新单品，包括云仓，排除相斥的(锚点图片)
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
					    	var goodsName = selected[0].goodsName;
					    	var brandName = selected[0].brandName;
					    	var currentPrice = selected[0].currentPrice;
					    	var shortName = prompt("请修改为8字以内商品名称:",goodsName);
					    	if(shortName!=""&&shortName!=null){
					    		shortName = $.trim(shortName).substring(0,8);
					    		$(p_elmt).html("<p>"+shortName+"</p><p>"+brandName+"</p>");
//					    	$(p_elmt).html(goodsName+"...</br>"+brandName+"</br>￥"+currentPrice+"RMB");
					    		$(p_elmt).parent().parent().attr("src",""+selected[0].id);
					    	}
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
						    	var goodsName = selected[0].goodsName;
						    	var brandName = selected[0].brandName;
						    	var currentPrice = selected[0].currentPrice;
						    	var shortName = prompt("请修改为8字以内商品名称:",goodsName);
						    	if(shortName!=""&&shortName!=null){
						    		shortName = $.trim(shortName).substring(0,8);
						    		$(p_elmt).html("<p>"+shortName+"</p><p>"+brandName+"</p>");
//						    	$(p_elmt).html(goodsName+"...</br>"+brandName+"</br>￥"+currentPrice+"RMB");
						    		$(p_elmt).parent().parent().attr("src",""+selected[0].id);
						    	}
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
  var container_div_id = $(container).attr("id");
  var id = container_div_id.substring(4);//div单元的ID
  if($(pic_maps).length>0){
	  var n=0;
	   var content = '[';
	   $(pic_maps).each(function(){
		   var goodsId = $(this).find("a").attr("src");
		   if(goodsId!=""){
			   n++;
			   content+="{";
			   var id = $(this).attr("id");
			   var p = $(this).find("div.div_p").html();
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
	   
	   if(n>0){
		   $("#"+id).find("input[class='picMapContent']").val(content);//用于保存黄点数据信息(只保存选择了的商品)
	   }else{
		   $("#"+id).find("input[class='picMapContent']").val("");
	   }
	   $("#"+id+"_cnt").val($(container).html());//暂存黄点信息到对应的div隐藏input中,用于回显
//	   $(container).empty();
  }else{
	  $("#"+id).find("input[class='picMapContent']").val("");
	  $("#"+id+"_cnt").val("");//暂存黄点信息到对应的div隐藏input中,用于回显
  }
}

//关闭div(锚点图片)
function closePicDiv(){
  $("#addTagsDiv").hide();
}

//删除场景图片
function delPic(obj){
	   $(obj).parent().remove();
	   var n = $("#aaaaaaa").find("div.pic_div").length;
	   m_pic_num = m_pic_num-1;
	   if(n<10){
		   $("#pic_u").show();
	   }
}

//删除锚点图片
function delPicMap(obj){
	   $(obj).parent().parent().parent().remove();
}

function setDivH(n){
	var count = parseInt(n/5);
	if(n%5!=0){
		count = count+1;
	}
	$("#aaaaaaa").css("height",440*count+"px");
};

//设置图片黄点信息
function setPicContent(){
	var flag = true;
	var coverPic = $("#coverPic").val();
	if(coverPic==""){
		flag = false;
		$.messager.alert('错误提示', '请上传封面图');
	}
	var pic_divs = $("#aaaaaaa").find("div.pic_div");
	if($(pic_divs).length>0){
		var n=0;
		$(pic_divs).each(function(){
			$(this).find("input.id").attr("name","detailPics["+n+"].id");
//			$(this).find("input.remark").attr("name","detailPics["+n+"].remark");
			$(this).find("input.picUrl").attr("name","detailPics["+n+"].picUrl");
			$(this).find("input.picMapContent").attr("name","detailPics["+n+"].picMapContent");
			n++;
		 });
	}else{
		flag = false;
		$.messager.alert('错误提示', '请上传场景图片');
	}
	return flag;
}