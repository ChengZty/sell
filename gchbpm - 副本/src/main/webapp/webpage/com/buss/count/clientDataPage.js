//获取导购的统计信息
function getGuideCount(start_time,end_time){
	$.ajax({
		cache: true,
		type: "POST",
		url: "clientDataCountController.do?getGuideCountInfo",
		data:{"start_time":start_time,"end_time":end_time},
		datatype: 'json',
		async: false,
		error: function(request) {
			tip("Connection error");
		},
		success: function(data) {
			var d = $.parseJSON(data);
			var allGuideList = d.obj["allGuideList"];
			var guideNumList = d.obj["guideNumList"];
			var addGuideNumList = d.obj["addGuideNumList"];
			var outGuideNumList = d.obj["outGuideNumList"];
			var clickGuideNumList = d.obj["clickGuideNumList"];
			var jobGuideNumList = d.obj["finishJobGuideNumList"];
			var payGuideNumList = d.obj["payGuideNumList"];

			
			for(var i=0;i<allGuideList.length;i++){
			    $("#allGuide"+i).html(allGuideList[i].count);
			}
			$("#allGuideTotal").html(allGuideList[allGuideList.length-1].totalNum);
			for(var i=0;i<guideNumList.length;i++){
			    $("#useGuide"+i).html(guideNumList[i].count);
			}
			$("#useGuideTotal").html(guideNumList[guideNumList.length-1].totalNum);
			for(var i=0;i<addGuideNumList.length;i++){
			    $("#addGuide"+i).html(addGuideNumList[i].count);
			}
			$("#addGuideTotal").html(addGuideNumList[addGuideNumList.length-1].totalNum);
			for(var i=0;i<outGuideNumList.length;i++){
			    $("#outGuide"+i).html(outGuideNumList[i].count);
			}
			$("#outGuideTotal").html(outGuideNumList[outGuideNumList.length-1].totalNum);
			for(var i=0;i<clickGuideNumList.length;i++){
			    $("#clickGuide"+i).html(clickGuideNumList[i].count);
			}
			$("#clickGuideTotal").html(clickGuideNumList[clickGuideNumList.length-1].totalNum);
			for(var i=0;i<jobGuideNumList.length;i++){
			    $("#jobGuide"+i).html(jobGuideNumList[i].percent);
			}
			$("#jobGuideTotal").html(jobGuideNumList[jobGuideNumList.length-1].percent);
			for(var i=0;i<payGuideNumList.length;i++){
			    $("#payGuide"+i).html(payGuideNumList[i].count);
			}
			$("#payGuideTotal").html(payGuideNumList[payGuideNumList.length-1].totalNum);
		}
	});
}

//获取成交的统计信息
function getOrderCount(start_time,end_time){
	$.ajax({
		cache: true,
		type: "POST",
		url: "clientDataCountController.do?getOrderCountInfo",
		data:{"start_time":start_time,"end_time":end_time},
		datatype: 'json',
		async: false,
		error: function(request) {
			tip("Connection error");
		},
		success: function(data) {
			var d = $.parseJSON(data);
			var payOrderNumList = d.obj["payOrderNumList"];
			var orderGoodsNumList = d.obj["orderGoodsNumList"];
			var orderMoneyNumList = d.obj["orderMoneyNumList"];
			var orderAverNumList = d.obj["orderAverNumList"];
			var outTimeOrderNumList = d.obj["outTimeOrderNumList"];
			
			for(var i=0;i<payOrderNumList.length;i++){
			    $("#payOrder"+i).html(payOrderNumList[i].orderNum);
			    $("#onlineNum"+i).html(payOrderNumList[i].quantityAmount);
			    $("#onlineMoney"+i).html(payOrderNumList[i].payAmount);
			    $("#orderAver"+i).html(payOrderNumList[i].orderAver);
			}
			$("#payOrderTotal").html(payOrderNumList[payOrderNumList.length-1].payOrderTotal);
			$("#onlineNumTotal").html(payOrderNumList[payOrderNumList.length-1].orderGoodsTotal);
			$("#onlineMoneyTotal").html(payOrderNumList[payOrderNumList.length-1].orderMoneyTotal);
			$("#orderAverTotal").html(payOrderNumList[payOrderNumList.length-1].orderAverTotal);
			for(var i=0;i<outTimeOrderNumList.length;i++){
			    $("#outTimePay"+i).html(outTimeOrderNumList[i].count);
			}
			$("#outTimePayTotal").html(outTimeOrderNumList[outTimeOrderNumList.length-1].totalNum);
		}
	});
}

//获取顾客的统计信息
function getCustomerCount(start_time,end_time){
	$.ajax({
		cache: true,
		type: "POST",
		url: "clientDataCountController.do?getCustomerInfo",
		data:{"start_time":start_time,"end_time":end_time},
		datatype: 'json',
		async: false,
		error: function(request) {
			tip("Connection error");
		},
		success: function(data) {
			var d = $.parseJSON(data);
			var allCustomer = d.obj["allCustomer"];
			var waitCustomer = d.obj["waitCustomer"];
			var hasTrasactCustomer = d.obj["hasTrasactCustomer"];
			var zeroToTwenty = d.obj["zeroToTwenty"];
			var TwentyToFourty = d.obj["TwentyToFourty"];
			var fourtyToSixty = d.obj["fourtyToSixty"];
			var sixtyToEighty = d.obj["sixtyToEighty"];
			var eightyToHundred = d.obj["eightyToHundred"];
			
			for(var i=0;i<allCustomer.length;i++){
			    $("#clientNum"+i).html(allCustomer[i].count);
			}
			$("#clientNumTotal").html(allCustomer[allCustomer.length-1].totalNum);
			for(var i=0;i<waitCustomer.length;i++){
			    $("#nopayClientNum"+i).html(waitCustomer[i].count);
			}
			$("#nopayClientNumTotal").html(waitCustomer[waitCustomer.length-1].totalNum);
			for(var i=0;i<hasTrasactCustomer.length;i++){
			    $("#payClientNum"+i).html(hasTrasactCustomer[i].count);
			}
			$("#payClientNumTotal").html(hasTrasactCustomer[hasTrasactCustomer.length-1].totalNum);
			for(var i=0;i<zeroToTwenty.length;i++){
			    $("#zeroToTwenty"+i).html(zeroToTwenty[i].count);
			}
			$("#zeroToTwentyTotal").html(zeroToTwenty[zeroToTwenty.length-1].totalNum);
			for(var i=0;i<TwentyToFourty.length;i++){
			    $("#TwentyToFourty"+i).html(TwentyToFourty[i].count);
			}
			$("#TwentyToFourtyTotal").html(TwentyToFourty[TwentyToFourty.length-1].totalNum);
			for(var i=0;i<fourtyToSixty.length;i++){
			    $("#fourtyToSixty"+i).html(fourtyToSixty[i].count);
			}
			$("#fourtyToSixtyTotal").html(fourtyToSixty[fourtyToSixty.length-1].totalNum);
			for(var i=0;i<sixtyToEighty.length;i++){
			    $("#sixtyToEighty"+i).html(sixtyToEighty[i].count);
			}
			$("#sixtyToEightyTotal").html(sixtyToEighty[sixtyToEighty.length-1].totalNum);
			for(var i=0;i<eightyToHundred.length;i++){
			    $("#eightyToHundred"+i).html(eightyToHundred[i].count);
			}
			$("#eightyToHundredTotal").html(eightyToHundred[eightyToHundred.length-1].totalNum);
		
		}
	});
}

//获取商品的统计信息
function getGoodsCount(start_time,end_time){
	$.ajax({
		cache: true,
		type: "POST",
		url: "clientDataCountController.do?countGoodsInfo",
		data:{"start_time":start_time,"end_time":end_time},
		datatype: 'json',
		async: false,
		error: function(request) {
			tip("Connection error");
		},
		success: function(data) {
			var d = $.parseJSON(data);
			var waitPublish = d.obj["waitPublish"];
			var hasPublish = d.obj["hasPublish"];
			var allGoods = d.obj["allGoods"];
			var hasWordsPercent = d.obj["hasWordsPercent"];
			
			for(var i=0;i<waitPublish.length;i++){
			    $("#waitPublish"+i).html(waitPublish[i].count);
			}
			$("#waitPublishTotal").html(waitPublish[waitPublish.length-1].totalNum);
			for(var i=0;i<hasPublish.length;i++){
			    $("#hasPublish"+i).html(hasPublish[i].count);
			    $("#hasWordsPercent"+i).html(hasPublish[hasPublish.length-1].percent);
			}
			$("#hasPublishTotal").html(hasPublish[hasPublish.length-1].totalNum);
			$("#hasWordsPercentTotal").html(hasPublish[hasPublish.length-1].percent);
			for(var i=0;i<allGoods.length;i++){
			    $("#allGoods"+i).html(allGoods[i].count);
			}
			$("#allGoodsTotal").html(allGoods[allGoods.length-1].totalNum);
			
			/*var percent = hasWordsPercent[hasWordsPercent.length-1].percent;
			for(var i=0;i<hasWordsPercent.length;i++){
			    $("#hasWordsPercent"+i).html(percent);
			}
			$("#hasWordsPercentTotal").html(percent);*/
		}
	});
}

//获取话题的统计信息
function getNewsCount(start_time,end_time){
	$.ajax({
		cache: true,
		type: "POST",
		url: "clientDataCountController.do?getNewsCountInfo",
		data:{"start_time":start_time,"end_time":end_time},
		datatype: 'json',
		async: false,
		error: function(request) {
			tip("Connection error");
		},
		success: function(data) {
			var d = $.parseJSON(data);
			var uploadedList = d.obj["uploadedList"];
			var clickList = d.obj["clickList"];
			var issendList = d.obj["issendList"];
			
			for(var i=0;i<uploadedList.length;i++){
			    $("#newsNum"+i).html(uploadedList[i].count);
			}
			$("#newsNumTotal").html(uploadedList[uploadedList.length-1].totalNum);
			for(var i=0;i<clickList.length;i++){
			    $("#clickNewsNum"+i).html(clickList[i].count);
			}
			$("#clickNewsNumTotal").html(clickList[clickList.length-1].totalNum);
			for(var i=0;i<issendList.length;i++){
			    $("#pushNewsNum"+i).html(issendList[i].count);
			}
			$("#pushNewsNumTotal").html(issendList[issendList.length-1].totalNum);
		}
	});
}

//获取话术的统计信息
function getWordsCount(start_time,end_time){
	$.ajax({
		cache: true,
		type: "POST",
		url: "clientDataCountController.do?getWordsCountInfo",
		data:{"start_time":start_time,"end_time":end_time},
		datatype: 'json',
		async: false,
		error: function(request) {
			tip("Connection error");
		},
		success: function(data) {
			var d = $.parseJSON(data);
			var custWordsList = d.obj["custWordsList"];
			/*var pushWordsList = d.obj["pushWordsList"];*/
			
			for(var i=0;i<custWordsList.length;i++){
			    $("#chartWordsNum"+i).html(custWordsList[i].count);
			}
			$("#chartWordsNumTotal").html(custWordsList[custWordsList.length-1].totalNum);
			/*for(var i=0;i<pushWordsList.length;i++){
			    $("#pushWordsNum"+i).html(pushWordsList[i].count);
			}
			$("#pushWordsNumTotal").html(pushWordsList[pushWordsList.length-1].totalNum);*/
		}
	});
}

//获取成交的统计信息
function getJobsCount(){
	$.ajax({
		cache: true,
		type: "POST",
		url: "clientDataCountController.do?countJobsInfo",
		data:{"start_time":start_time,"end_time":end_time},
		datatype: 'json',
		async: false,
		error: function(request) {
			tip("Connection error");
		},
		success: function(data) {
			var d = $.parseJSON(data);
			var onlineJobsList = d.obj["onlineJobsList"];
			var jobParamCountList = d.obj["jobParamCountList"];
			/*var orderMoneyNumList = d.obj["orderMoneyNumList"];
			var orderAverNumList = d.obj["orderAverNumList"];
			var outTimeOrderNumList = d.obj["outTimeOrderNumList"];*/
			for(var i=0;i<onlineJobsList.length;i++){
			    $("#onLineJob"+i).html(onlineJobsList[i].count);
			}
			$("#onLineJobTotal").html(onlineJobsList[onlineJobsList.length-1].totalNum);
			
			for(var i=0;i<jobParamCountList.length;i++){
				var storeId = jobParamCountList[i].storeId;
				var jobId = jobParamCountList[i].jobId;
				$("#"+storeId+"_"+jobId).html(jobParamCountList[i].percent);
			}
		}
	});
}