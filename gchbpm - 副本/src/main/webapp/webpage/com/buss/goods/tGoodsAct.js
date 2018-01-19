//保存
function save(){
	  $("#formobj").submit();
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




//返回
function goBack(){
	if("audit"==page){//返回审核列表
		window.location.href="tGoodsActController.do?auditList";
	}else{
		window.location.href="tGoodsActController.do?list";
	}
}

//新增或者更新后返回话题列表
function backList(data) {
	if(data.success){
		window.location.href="tGoodsActController.do?list&downloadKey="+data.obj+"&msg="+data.msg;
	}
}
 
  
  $(function(){
	  $("#act_goods_list").panel(
		{
			title : "活动商品明细",
			href:"tGoodsActDetailController.do?list&goodsActId="+goodsActId+"&view="+view+"&add="+add
			}
		);
  })
 
  //获取活动模版
 function getGoodsActTemplate(){
	  var actType = $("#actType").val();
	  var url = "tGoodsActController.do?getGoodsActTemplate&actType="+actType;
		 $.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : url,// 请求的action路径
				error : function() {// 请求失败处理函数
				},
				success : function(data) {
					var d = $.parseJSON(data);
					console.log(d);

				}
			});
 }

  //商品活动添加和更新页面中动态选择actType
  function selectActType(actType){
		$("#actType").val(actType);
		if(actType == "XSZK"){
			$("#zb").attr("style","display: none;");
			$("#actType_XSZK").attr("class","selected");
			$("#actType_MS").attr("class","unselected");
			$("#actType_ZB").attr("class","unselected");
		}else if(actType == "ZB"){
			$("#zb").removeAttr("style");
			$("#actType_XSZK").attr("class","unselected");
			$("#actType_MS").attr("class","unselected");
			$("#actType_ZB").attr("class","selected");
		}else if(actType == "MS"){
			$("#zb").attr("style","display: none;");
			$("#actType_XSZK").attr("class","unselected");
			$("#actType_MS").attr("class","selected");
			$("#actType_ZB").attr("class","unselected");
		}
}
