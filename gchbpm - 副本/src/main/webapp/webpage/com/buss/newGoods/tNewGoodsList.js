function goView(){
	 var rowsData = $('#tNewGoodsList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		window.open("tNewGoodsController.do?goView&id="+rowsData[0].id,"new");
// 	 window.location="tNewGoodsController.do?goView&id="+rowsData[0].id+"&goods_status=${goods_status }";
 }
 
//置顶
function doToTop(id){
	 var url = "tNewGoodsController.do?doToTop&id="+id;
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
					tip(d.msg);
					reloadTable();
				}

			}
		});
}

function doBatchDown(title,url) {
    var ids = [];
    var rows = $("#tNewGoodsList").datagrid('getSelections');
    if (rows.length > 0) {
    	$.dialog.confirm('确定批量下架?', function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#tNewGoodsList").datagrid('unselectAll');
							ids='';
						}
					}
				});
			}
		});
	} else {
		tip("请选择商品");
	}
}

function doBatchPublish(title,url) {
    var ids = [];
    var rows = $("#tNewGoodsList").datagrid('getSelections');
    var selectRows = rows.length;
    if(selectRows>0){
    	var msg = "";
    	var n = 0;//有图的总数
        for ( var i = 0; i < selectRows; i++) {
        	if(rows[i].smallPic){
        		n++;
    			ids.push(rows[i].id);
        	}else{
        		msg+=rows[i].goodsCode+",";
        	}
    	}
        if(msg!=""){
        	msg = "款号"+msg.substring(0,msg.length-1)+"没有商品图片";
        	tip(msg);
        }
        if (n > 0) {
        	$.dialog.confirm('确定批量发布?', function(r) {
    		   if (r) {
    				$.ajax({
    					url : url,
    					type : 'post',
    					data : {
    						ids : ids.join(',')
    					},
    					cache : false,
    					success : function(data) {
    						var d = $.parseJSON(data);
    						if (d.success) {
    							var msg = d.msg;
    							tip(msg);
    							reloadTable();
    							$("#tNewGoodsList").datagrid('unselectAll');
    							ids='';
    						}
    					}
    				});
    			}
    		});
    	}
    }else {
		tip("请选择商品");
	}
}

function doChangeProperty(title,url){
	var tipcontent = "请输入"+title;
	var limitLength = 14;
	if("商品名称"==title){
		limitLength = 20;
		tipcontent += "，20字以内(含)";
	}else if("短标题"==title){
		tipcontent += "，14字以内(含)";
	}
	var val = prompt(tipcontent,"");
	if(val!=null){
		val = $.trim(val);
		 if(val==""){
			 tip(title+"不能为空");
			 return false;
			 
		 }else if(val.length>limitLength){
			 tip(title+"长度不能超过"+limitLength+"字");
			 return false;
		 }else{
			 url +="&val="+val;
			 $.ajax({
					url : url,
					type : 'post',
					data : {},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#tNewGoodsList").datagrid('unselectAll');
						}
					}
				});
		 }
	}
}

function doChangePrice(title,url){
	var val = prompt("请输入"+title,"");
	if(val!=null){
		val = $.trim(val);
	 if(val!=""&&!isNaN(val)){
		 url +="&currPrice="+val;
		 $.ajax({
				url : url,
				type : 'post',
				data : {},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						reloadTable();
						$("#tNewGoodsList").datagrid('unselectAll');
					}
				}
			});
	 }else{
		 tip("请填写正确的金额");
		 return false;
	 }
	}
}

//上移
function doToUper(id,sortNum){
	changeSort(id,sortNum,"U");
}
//下移
function doToDown(id,sortNum){
	changeSort(id,sortNum,"D");
}
//改变排序
function changeSort(id,sortNum,type){
	var url = "tNewGoodsController.do?doChangeSort&type="+type+"&id="+id+"&sortNum="+sortNum;
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
					tip(d.msg);
					reloadTable();
				}

			}
		});
}

//推送信息
function doSend(id){
		window.open("tSmsSendInfoController.do?goAdd&pushId="+id+"&pushType="+1,"new");	
}

//判断是否选商品
function checkSelect(){
	var rows = $("#tNewGoodsList").datagrid('getSelections');
   if (rows.length > 0) {
   	return true;
   }else{
   	tip("请选择要改价的商品");
   	return false;
   }
}

//显示div修改原价
function goBatchChangeOrigPrice(title){
	 if(checkSelect()){
		 $("#price_title").text(title);
		 $("#priceField").val("1");
		 $("#price").val("");
		 $("#price_div").show();
	 }
}

//显示div修改最低价
function goBatchChangeLowestPrice(title){
	 if(checkSelect()){
		 $("#price_title").text(title);
		 $("#priceField").val("2");
		 $("#price").val("");
		 $("#price_div").show();
	 }
}

//点击确定改价按钮
function confirmDiv(){
	 var price = $("#price").val();
	 var priceType = $("input[name='priceType']:checked").val();//1：统一调价，2：折扣调价
	 if(priceType=="1"){//价格
		 if(price==""||isNaN(price)||price-0<0){
			 tip("价格不是有效数字");
			 return;
		 }
	 }
	 if(priceType=="2"){//折扣
		 if(price==""||isNaN(price)||price-0<0||price-0>10){
			 tip("折扣不是有效数字");
			 return;
		 }
	 }
	 if(priceType=="1"&&price-0<10){//防止误操作把统一价格录成了折扣
		 $.dialog.confirm('确定统一调价为'+price+'吗？', function(r) {
			 if (r) {
				 doBatchChangePrice();
				 closeDiv();
			 }else{
				 return;
			 }
		 });
	 }else{
		 doBatchChangePrice();
		 closeDiv(); 
	 }
}

//批量修改价格（原价,最低价）
function doBatchChangePrice(){
	 var url = "tNewGoodsController.do?doBatchChangePrice";
	 var rows = $("#tNewGoodsList").datagrid('getSelections');
	 var priceField = $("#priceField").val();//1：原价，2：最低价
	 var priceType = $("input[name='priceType']:checked").val();//1：统一调价，2：折扣调价
	 var price = $("#price").val();
	 var ids = [];
	 for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
		}
		$.ajax({
			url : url,
			type : 'post',
			data : {
				ids : ids.join(','),
				priceField : priceField,
				priceType : priceType,
				price : price
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
					reloadTable();
					$("#tNewGoodsList").datagrid('unselectAll');
					ids='';
				}
			}
		});
}

//关闭div
function closeDiv(){
	 $("#price_div").hide();
}