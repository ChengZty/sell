//获取海报列表  
function getPosterList(){
	  var width = 900;
		var height = 700;
		var title = "海报列表 ";
	  if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:tShopHomeTempController.do?tPosterLinkList',
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: height
			}).zindex();
		}else{
			$.dialog({
				content: 'url:tShopHomeTempController.do?tPosterLinkList',
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: height
			}).zindex();
		}
  }

//获取海报列表  
function getGoodsList(){
	  var width = 900;
		var height = 700;
		var title = "商品列表 ";
	  if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:tShopHomeTempController.do?tGoodsLinkList',
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: height
			}).zindex();
		}else{
			$.dialog({
				content: 'url:tShopHomeTempController.do?tGoodsLinkList',
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: height
			}).zindex();
		}
  }