//$(function(){
	//获取订单回复模板信息
	  function getOrderMsgTemp(typecode){
		var phone = $("#phone").val();
		var returnPhone = $("#returnPhone").val();
		var returnAddress = $("#returnAddress").val();
	  	var url = "tOrderMsgController.do?getOrderMsgTemp";
	  	$.ajax({
	  		async : false,
	  		cache : false,
	  		data:{"typecode":typecode,"phone":phone,"returnPhone":returnPhone,"returnAddress":returnAddress},
	  		type : 'POST',
	  		url : url,// 请求的action路径
	  		error : function() {// 请求失败处理函数
	  		},
	  		success : function(data) {
	  			var d = $.parseJSON(data);
	  			if (d.success) {
	  				$("#paraCode").val(typecode);
	  				$("#paraValue").html(d.obj);
	  				$(".input_paraValue").val(d.obj);
	  			}
//	  			dealReturnDom(typecode);
	  		}
	  		
	  	});
		 
	  }
	  //短信内容的拼接
	  $(".change_template").on('input propertychange', function(){
		  var tempNum = $(this).val().trim();
		  var name = $(this).attr("name");
		  $("p.paraValue span["+name+"]").text(tempNum);
		  $('input.input_paraValue').val($('p.paraValue').html());
	  });
	  
//	  function dealReturnDom(typecode){
//		  console.log(typecode);
//		  //如果是  order_apply 则显示退货电话和退货地址
//		  if(typecode == 'order_apply'){
//			$('.return_address, .return_phone').show();
//			$('.return_address, .return_phone').find('input.change_template').attr('datatype', 's2-20');
//		  }else{
//			$('.return_address, .return_phone').hide();
//			$('.return_address, .return_phone').find('input.change_template').removeAttr('datatype');
//		  }
//	  }
	  
//	  dealReturnDom(typecode);
//});