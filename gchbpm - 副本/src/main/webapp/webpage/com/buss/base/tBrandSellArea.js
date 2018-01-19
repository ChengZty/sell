//获取城市
  function getCitys(areaId){
  	var url = "territoryController.do?getCitys&areaId="+areaId;
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
  				$("#cityId").empty();
  				var ops = "<option value=''>---请选择---</option>";
  				for(i=0;i<d.obj.length;i++){
  					ops+="<option value='"+d.obj[i].areaId+"'>"+d.obj[i].areaName+"</option>"
  				}
  				$("#cityId").append(ops);
  			}
  		}
  	});
  }