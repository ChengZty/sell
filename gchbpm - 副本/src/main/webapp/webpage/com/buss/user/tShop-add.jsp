<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>店铺</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script src = "plug-in\area\js\LAreaData.js"></script>	
  <script type="text/javascript">
  $(function(){
// 	  console.log(LAreaData);
	  initAreaData(LAreaData);
  })
  function initAreaData(LAreaData){
	  var strHtml = "";
	  var initProvince = "";
	  var initCity = "";
	  $.each(LAreaData, function(i,obj){
		  if(i==0){
			  initProvince = obj.name;
		  }
		  strHtml +="<option value='"+obj.name+"'>"+obj.name+"</option>";
	  });
	  $("#provinceId").html(strHtml);
	  var strHtml2 = "<select class='citySelect' name='cityId'>";
	  $.each(LAreaData[0].child, function(i,val){ 
		  if(i==0){
			  initCity = val.name;
		  }
		  strHtml2 +="<option value='"+val.name+"'>"+val.name+"</option>";
	  });
	  strHtml2 +="</select>";
	  $("#cityId").html(strHtml2);
	  $("#area").val(initProvince+initCity);
  }
  //校验帐号
//   function checkUserName(username){
// 	  $.ajax({
// 	  		async : false,
// 	  		cache : false,
// 	  		type : 'POST',
// 	  		url : 'userController.do?checkUserName&username='+username,// 请求的action路径
// 	  		error : function() {// 请求失败处理函数
	  		
// 	  		},
// 	  		success : function(data) {
// 	  			var d = $.parseJSON(data);
// 	  			if (!d.success) {
// 	  				tip(d.msg);
// 	  				$("#code").focus();
// 	  			}
// 	  		}
// 	  	});
//   }
  
//获取城市
  function getCitys(provinceId){
	var cityOps = "";
	  $.each(LAreaData, function(i,obj){
		  if(provinceId==obj.name){
			  $.each(obj.child, function(i,val){ 
				  cityOps +="<option value='"+val.name+"'>"+val.name+"</option>";
			  });
		  }
	  });
	  $("#cityId").html(cityOps);
	  fitName();
//   	var url = "territoryController.do?getCitys&areaId="+areaId;
//   	$.ajax({
//   		async : false,
//   		cache : false,
//   		type : 'POST',
//   		url : url,// 请求的action路径
//   		error : function() {// 请求失败处理函数
  		
//   		},
//   		success : function(data) {
//   			var d = $.parseJSON(data);
//   			if (d.success) {
//   				$("#cityId").empty();
//   				var ops = "<option value=''>---请选择---</option>";
//   				for(i=0;i<d.obj.length;i++){
//   					ops+="<option value='"+d.obj[i].areaId+"'>"+d.obj[i].areaName+"</option>"
//   				}
//   				$("#cityId").append(ops);
//   			}
//   		}
//   	});
  }

  function fitName(){
    	var provinceName = $("#provinceId").find("option:selected").text();
    	var cityName = $("#cityId").find("option:selected").text();
  		$("#area").val(provinceName+cityName);
//   		console.log(provinceName+cityName);
   }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tShopController.do?doAdd" tiptype="4">
					<input id="area" name="area" type="hidden">
					<input id="status" name="status" type="hidden" value="A">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							帐号:
						</label>
					</td>
					<td class="value">
						<input id="code" name="code" type="text" style="width: 150px" class="inputxt" validType="t_shop,status = 'A' and code"  datatype="s2-20">
						<span class="Validform_checktip">帐号为2-20位数字或字母(即登录的用户名，如果不存在则会生成零售商管理员帐号，初始密码默认为123456)</span>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">帐号</label>
						</td>
					</tr>
					<tr>
						<td align="right"  ><label class="Validform_label">用户编码: </label></td>
						<td class="value" >
			                <input id="userCode" type="text" class="inputxt" name="userCode" validType="t_s_user,status = 'A' and user_code is not null and user_code"   datatype="n6-6" errormsg="用户编码必须为6位数字" maxlength="6">
			                <span class="Validform_checktip">零售商用户编码必须为6位数字</span>
			                <label class="Validform_label" style="display: none;">用户编码</label>
			            </td>
					</tr>
					<tr>
						<td align="right"  ><label class="Validform_label">版本: </label></td>
						<td class="value" >
			                <select name="retailerEdition" id="retailerEdition">
								<option value="1">标准版</option>
								<option value="2">企业版</option>
							</select>
			            </td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							店铺名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="shopName" name="shopName" type="text" datatype="*" style="width: 150px" class="inputxt">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">店铺名称</label>
						</td>
					</tr>
				<tr>
				<td align="right">
						<label class="Validform_label">
							所在省市:
						</label>
					</td>
					<td class="value" id="aa">
						<select id="provinceId" name="provinceId" datatype="*" onchange="getCitys(this.value)" style="width: 100px">
<%-- 							<c:forEach items="${areaList}" var="area"> --%>
<%-- 								<option value="${area.areaId }" >${area.areaName}</option> --%>
<%-- 							</c:forEach> --%>
						</select>
						<select id="cityId" name="cityId" datatype="*"  style="width: 100px" onchange="fitName()">
<!-- 							<option value="">---请选择---</option> -->
						</select>
							<label class="Validform_label" style="display: none;">所在省市</label>
						</td>
						
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							详细地址:
						</label>
					</td>
					<td class="value">
					     	 <input id="detailAddress" name="detailAddress" type="text" style="width: 150px" class="inputxt" datatype="*">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">详细地址</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							电话:
						</label>
					</td>
					<td class="value">
					     	 <input id="phoneNo" name="phoneNo" type="text" style="width: 150px" class="inputxt" datatype="m" maxlength="11">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
				</tr>						
				<tr>
					<td align="right">
						<label class="Validform_label">
							有效期:
						</label>
					</td>
					<td class="value">
				     	 <input name="validPeriod" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()">
						<span class="Validform_checktip">留空表示不限时间</span>
						<label class="Validform_label" style="display: none;">有效期</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							店铺状态:
						</label>
					</td>
					<td class="value">
				     	 <select id="shopStatus" name="shopStatus"  style="width: 100px">
							<option value="1" >激活</option>
							<option value="0" >待激活</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否显示:
						</label>
					</td>
					<td class="value">
				     	 <select id="isShow" name="isShow"  style="width: 100px">
							<option value="1" >是</option>
							<option value="0" >否</option>
						</select>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
