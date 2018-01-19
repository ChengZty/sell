
function sub(){
	var pics_small_num = $("#main_pics_small img").length;
	 if(pics_small_num==0){
	 	alert("请上传组合小图");
	 	return false;
	 }
	 var main_pics_num = $("#dtl_pics img").length;
	 if(main_pics_num==0){
	 	alert("请添加单品");
	 	return false;
	 }
	 var main_pics_num = $("#main_pics img").length;
	 if(main_pics_num==0){
		 alert("请上传组合主图");
		 return false;
	 }
	  var input_keywords  = $("#add_TgoodsAttrDetail_table td input[name$='careValue']");
	  var n=0;
	  $(input_keywords).each(function(idx){
		  if($.trim($(this).val())!=""){
			  n++;
		  }
	  });
	  if(n==0){
		  $.messager.alert('提示信息', "请填写组合关键词");
		  return false;
	  }
	  initPicsOrder();
	$("#formobj").submit();
}

//存草稿
function save(){
	var pics_small_num = $("#main_pics_small img").length;
	 if(pics_small_num==0){
	 	alert("请上传组合小图");
	 	return false;
	 }
	 var main_pics_num = $("#dtl_pics img").length;
	 if(main_pics_num==0){
	 	alert("请添加单品");
	 	return false;
	 }
	 var main_pics_num = $("#main_pics img").length;
	 if(main_pics_num==0){
		 alert("请上传组合主图");
		 return false;
	 }
	  var input_keywords  = $("#add_TgoodsAttrDetail_table td input[name$='careValue']");
	  var n=0;
	  $(input_keywords).each(function(idx){
		  if($.trim($(this).val())!=""){
			  n++;
		  }
	  });
	  if(n==0){
		  $.messager.alert('提示信息', "请填写组合关键词");
		  return false;
	  }
	  initPicsOrder();
  	$("#publishStatus").val("2");
	$("#formobj").submit();
}

//查询单品
function findGoods() {
	var retailerId = $("#retailerId").val();
	var groupSource = $("#groupSource").val();
	if(retailerId==""&&groupSource=="2"){
		$.messager.alert('错误提示', '请选择零售商');
		return false;
	}
	  var url = "tGoodsController.do?detailList&retailer_Id="+retailerId+"&group_Source="+groupSource;
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
						    		var div_imgs = "<div class='brick2'><input type='hidden' class='d_g_v' value=''/><input type='hidden' class='p_u_v' value=''/><input type='hidden' class='l_p_v' value=''/><input type='hidden' class='g_p_v' value=''/><input type='hidden' class='c_p_v' value=''/>"+
	 					    		"<input type='hidden' class='g_c_v' value=''/><input type='hidden' class='o_p_v' value=''/><input type='hidden' class='d_g' value='"+domEle.id+"'/><input type='hidden' class='l_p' value='"+domEle.lowestPrice
	 					    		+"'/><input type='hidden' class='o_p' value='"+domEle.originalPrice+"'/><input type='hidden' class='c_p' value='"+domEle.currentPrice
	 					    		+"'/><input type='hidden' class='g_c' value='"+domEle.goodsCode+"'/><a class='delete2' onclick='delDtlPic(this)' href='#'>×</a><img src='"+domEle.smallPic
	 					    		+"' width='140' height='140' ></img><div>款号："+domEle.goodsCode+"</div><div><span>现价："+domEle.currentPrice+"</span></div><div>组合价：<input type='text' class='g_p' onclick='focus();' onblur='checkPrice(this)' onkeypress='Public.input.numberInput()' style='width:70px;height:20px' /></div></div>"
	 								$("#dtl_pics").append(div_imgs);
	 					    		calOrigPrice();
	 					    		$('#dtl_pics').gridly();
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
							    		var div_imgs = "<div class='brick2'><input type='hidden' class='d_g_v' value=''/><input type='hidden' class='p_u_v' value=''/><input type='hidden' class='l_p_v' value=''/><input type='hidden' class='g_p_v' value=''/><input type='hidden' class='c_p_v' value=''/>"+
		 					    		"<input type='hidden' class='g_c_v' value=''/><input type='hidden' class='o_p_v' value=''/><input type='hidden' class='d_g' value='"+domEle.id+"'/><input type='hidden' class='l_p' value='"+domEle.lowestPrice
		 					    		+"'/><input type='hidden' class='o_p' value='"+domEle.originalPrice+"'/><input type='hidden' class='c_p' value='"+domEle.currentPrice
		 					    		+"'/><input type='hidden' class='g_c' value='"+domEle.goodsCode+"'/><a class='delete2' onclick='delDtlPic(this)' href='#'>×</a><img src='"+domEle.smallPic
		 					    		+"' width='140' height='140' ></img><div>款号："+domEle.goodsCode+"</div><div><span>现价："+domEle.currentPrice+"</span></div><div>组合价：<input type='text' class='g_p' onclick='focus();' onblur='checkPrice(this)' onkeypress='Public.input.numberInput()' style='width:70px;height:20px' /></div></div>"
		 								$("#dtl_pics").append(div_imgs);
		 					    		calOrigPrice();
		 					    		$('#dtl_pics').gridly();
							    	});
						    }
				    },
				    cancelVal: '关闭',
				    cancel: true /*为true等价于function(){}*/
				}).zindex();
			}
	}

  
//选择零售商
  function findRetailers(){
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
  			    cancel: true /*为true等价于function(){}*/
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
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
  		}
}

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
		url = "tBrandShowController.do?findBrandList";
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
				title:"选择 品牌",
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


//拖动图片
$('.gridly').gridly({
    base: 60, // px 
    gutter: 20, // px
    columns: 15
  });
$('.gridly').css("height","200px");
//拖动图片
/*
$('.gridly2').gridly({
	base: 20, // px 
	gutter: 10, // px
	columns: 45
});
$('.gridly2').css("height","200px");
*/

//删除主图 
function delPic(obj){
   $(obj).parent().remove();
   	$("#templatePic_main").show();
   	m_pic_num--;
   	$('#templatePic_main').uploadify('settings','queueSizeLimit',5-m_pic_num);
    $('#main_pics').css("height","200px");
} 

//删除明细
function delDtlPic(obj){
	var groupPrice = $("#groupPrice").val() - $(obj).parent().find("input[class='g_p']").val();
	var originalPrice = $("#originalPrice").val() - $(obj).parent().find("input[class='o_p']").val();
	$("#groupPrice").val(groupPrice);
	$("#originalPrice").val(originalPrice);
	$(obj).parent().empty().remove();
	if($('#dtl_pics div').length==0){
		$('#dtl_pics').css("height","210px");
	}
} 

//初始化图片的name,按顺序排 (拖动的时候div位置未变，只是left和top值会变，单行的top为0，暂不考虑) 
function initPicsOrder(){
	//主图排序
 var $pics_main = $("#main_pics input[type='hidden']");
 var order = "";
 var left_ = "";
 var url = "";
 $.each( $pics_main, function(index, domEle){
	 if(0==index){
		 order = "One";
	 }else if(1==index){
		 order = "Two";
	 }else if(2==index){
		 order = "Three";
	 }else if(3==index){
		 order = "Four";
	 }else{
		 order = "Five";
	 }
	 left_ = index*240+"px";
	 var $divs = $("#main_pics div");
	 $.each( $divs, function(i, n){
		 if($(this).css("left")==left_){
			 url =  $(this).find("img").attr("src");
		 }
	 });
	 $(this).attr("name","pic"+order);
	 $(this).attr("value",url);
	});
 
 

	
	
	var $divs = $("#dtl_pics div[class='brick2']");
	var n = $divs.length;
	for(var i=0;i<n;i++){
		var div = $divs.get(i);
		var div_left = $(div).css("left");
		var div_top =  $(div).css("top");
//		var pic_url =  $(div).find("img").attr("src");
		var detail_id =  $(div).find("input[class='d_id']").val();
		var detail_goods_id =  $(div).find("input[class='d_g']").val();
//		var lowest_price =  $(div).find("input[class='l_p']").val();
		var group_price =  $(div).find("input[class='g_p']").val();
		var goods_code =  $(div).find("input[class='g_c']").val();
//		var original_price =  $(div).find("input[class='o_p']").val();
//		var current_price =  $(div).find("input[class='c_p']").val();
		var div_index = parseInt(div_left)/160 + (parseInt(div_top)/220)*6;
//		$(div).find("input[class='p_u_v']").attr("name","tGoodsPicDetails["+div_index+"].picUrl").val(pic_url);
		$(div).find("input[class='d_id_v']").attr("name","tGoodsPicDetails["+div_index+"].id").val(detail_id);
		$(div).find("input[class='d_g_v']").attr("name","tGoodsPicDetails["+div_index+"].detailGoodsId").val(detail_goods_id);
//		$(div).find("input[class='l_p_v']").attr("name","tGoodsPicDetails["+div_index+"].lowestPrice").val(lowest_price);
		$(div).find("input[class='g_p_v']").attr("name","tGoodsPicDetails["+div_index+"].groupPrice").val(group_price);
		$(div).find("input[class='g_c_v']").attr("name","tGoodsPicDetails["+div_index+"].goodsCode").val(goods_code);
//		$(div).find("input[class='o_p_v']").attr("name","tGoodsPicDetails["+div_index+"].originalPrice").val(original_price);
//		$(div).find("input[class='c_p_v']").attr("name","tGoodsPicDetails["+div_index+"].currentPrice").val(current_price);
	}
	
   return true;

}	

function checkDetailPic(){
	var exist = false;
	var num = $("#dtl_pics img").length;
	if(num>0){
		exist = true;
	}else{
		$.messager.alert('错误提示', '请选择单品');
	}
	return exist;
}


 //校验价格(20160914修改为组合中单品可以低于最低价)
 function checkPrice(obj){
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
 