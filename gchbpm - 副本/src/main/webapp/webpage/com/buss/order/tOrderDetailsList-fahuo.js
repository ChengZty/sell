//更新待发货件数
 function updateToBeFahuoCount(){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : "tOrderInfoController.do?getToBeFahuoCount",// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if(d.success){
					var parent_div = parent.document.getElementById("tt");
		 	 		$(parent_div).find("ul li").eq(1).find("span.badge").text(d.msg);
				}else{
				}
			}
		});
 }
 
 function goView(orderId){
	 var url = "tOrderInfoController.do.do?goView&id="+orderId;
	 window.open(url,'new') 
 }

//导入
// function ImportXls(title,url,gname) {
// 	openuploadwin(title, 'tOrderInfoController.do?upload', gname);
// }
 
//模板下载
 function downLoadFahuoT(title,url,gname) {
 	JeecgExcelExport("tOrderInfoController.do?downLoadFahuoT",gname);
 }
 
//导出发货明细
 function ExportXls(title,url,gname) {
 	JeecgExcelExport("tOrderDetailController.do?exportXlsForFahuo",gname);
 }
 
 //批量发货
function fahuoALLSelect(title,url,gname,width,height) {
	var rowsData = $('#'+gname).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择发货订单');
		return;
	}
	var orderNos = "";
//			  d是要遍历的集合
//			  index就是索引值
//			  domEle 表示获取遍历每一个dom对
	$.each(rowsData,function (index,domEle){
		orderNos += domEle.orderNo + ",";
		});
// 	if(isRestful!='undefined'&&isRestful){
// 		url += '/'+rowsData[0].id;
// 	}else{
		url += '&orderNos='+orderNos;
// 	}
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				lock : true,
				zIndex: 300,
				width:width,
				height:height,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					setTimeout("updateToBeFahuoCount()",800);
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
		}else{
			W.$.dialog({
				content: 'url:'+url,
				lock : true,
				width:width,
				zIndex:300,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					setTimeout("updateToBeFahuoCount()",800);
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
		}
}
 
//修改地址
function goChangeAddress(orderId){
	var url = "tOrderInfoController.do?goChangeAddress&orderId="+orderId;
	createwindow('修改地址',url,500,250);
}

