<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>订单明细</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  function checkType(val,index){
	  if("0"==val){
		  $("#tr1").hide();
		  $("#tr2").hide();
		  $("#deliveryName"+index).attr("disabled","disabled");
		  $("#deliveryNo"+index).attr("disabled","disabled");
	  }else if("1"==val){
		  $("#tr1").show();
		  $("#tr2").show();
		  $("#deliveryName"+index).removeAttr("disabled");
		  $("#deliveryNo"+index).removeAttr("disabled");
	  }
  }
  
  function checkDeliveryNo(){
	  var deliveryType = $("input[name='deliveryType']:checked").val();
	  if(deliveryType=="1"){//需要物流必须要填物流号
		  var deliveryNo = $.trim($("#deliveryNo").val()) ;
	  	if(deliveryNo==""){
		  	alert("请填写物流单号");
		  	$("#deliveryNo").focus();
	  		return false;
	  	}
	  }
  	return true;
  }
  
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tOrderInfoController.do?doBatchFahuo"  tiptype="4">
		<table cellpadding="0" cellspacing="1" class="formtable">
				<thead>
					<th>订单号</th>
					<th>发货方式</th>
					<th>物流公司</th>
					<th>物流单号</th>
				</thead>
				<tbody>
					<c:forEach var="orderNo" items="${orderNos }" varStatus="sts">
						<tr>
							<td class="value">
								${orderNo }
								<input  name="deliveryList[${sts.index}].orderNo" type="hidden" value="${orderNo }" />
							</td>
							<td class="value">
							     <label><input id="deliveryType1${sts.index}" name="deliveryList[${sts.index}].deliveryType" type="radio" checked="checked" value="1" onclick="checkType(this.value,${sts.index})">使用物流</label>
						     	 <label><input id="deliveryType0${sts.index}" name="deliveryList[${sts.index}].deliveryType" type="radio"  value="0" onclick="checkType(this.value,${sts.index})">无需物流</label>
							</td>
							<td class="value">
							     <select name="deliveryList[${sts.index}].deliveryName" id="deliveryName${sts.index}">
									<c:forEach var="deliveris" items="${deliveryList }">
										<option value="${deliveris.deliveryCode },${deliveris.deliveryName }">${deliveris.deliveryName }</option>
									</c:forEach>
								</select>
							</td>
							<td class="value">
							     <input id="deliveryNo${sts.index}" name="deliveryList[${sts.index}].deliveryNo" type="text" style="width: 150px"   >
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</t:formvalid>
 </body>
