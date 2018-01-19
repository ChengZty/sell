<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>订单短信自动回复</title>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">
	  //提交信息
	  var clickFlag = true; //可点击
	  function sub(){
		  if(clickFlag&&checkPhoneNos()){
			  $("#formobj").submit();
		  }
	  }
	  
	//页面校验通过后，提交前会调用该方法
	  function clickDis(){
		clickFlag = false; //防止新增后ID没有回写过来，继续点击保存，会再次新增同样的数据
	  }
	  
	//显示提示信息
	  function showMsg(obj){
		  tip(obj.msg);
		  setTimeout(reloadTab,3000);//刷新页面，获取新增后的id
	  }
	  function reloadTab(){
		  location.reload();
	  }
	  
  $(function(){
	  
	  //绑定客服手机更改事件
	  $("#phone").change(function(){
		  var phoneNo = $(this).val();
		  $("div span.phone").each(function(index,domEle){
			  $(domEle).text(phoneNo);
			  var $divDom = $(domEle).parent();
			  $($divDom).prev().val($($divDom).html());//隐藏域赋值
		  })
	  });
	//绑定退货电话更改事件
	  $("#returnPhone").change(function(){
		  var phoneNo = $(this).val();
		  var $divDom = $("#order_apply_div");
		  $($divDom).find("span.returnPhone").text(phoneNo);
// 		  console.log($($divDom).html());
		  $($divDom).prev().val($($divDom).html());//隐藏域赋值
	  });
	//绑定退货电话更改事件
	  $("#returnAddress").change(function(){
		  var phoneNo = $(this).val();
		  var $divDom = $("#order_apply_div");
		  $($divDom).find("span.returnAddress").text(phoneNo);
		  $($divDom).prev().val($($divDom).html());//隐藏域赋值
	  });
  })
  
  //检查号码
  function checkPhoneNos(){
	  var flag = false;//默认手机号不合法
	  var kfPhone = $("#phone").val();
	  var thPhone = $("#returnPhone").val();
	  if(!checkPhone(kfPhone)&&!checkTel(kfPhone)){
		  tip("客服电话不正确，请检查");
		  $("#phone").focus();
		  return false;
	  }
	  if(!checkPhone(thPhone)&&!checkTel(thPhone)){
		  tip("退货电话不正确，请检查");
		  $("#returnPhone").focus();
		  return false;
	  }
	  return true;
  }
  
  function checkPhone(PhoneNo){ 
    if(!(/^1[34578]\d{9}$/.test(PhoneNo))){ 
//     	console.log("checkPhone---"+PhoneNo);
        return false; 
    }else{
//     	console.log("checkPhone"+PhoneNo);
    	return true;
    }
  }
 
	function checkTel(PhoneNo) {
		var is400 = false;
		var isTel = false;
		if (/^400(-)?[0-9]{7}/.test(PhoneNo)) {
			is400 = true;
		}
		if (/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(PhoneNo)) {
			isTel = true;
		}
		if(!is400&&!isTel){
// 			console.log("checkTel-----------"+PhoneNo);
			return false;
		}else{
		console.log("checkTel"+PhoneNo);
			return true;
		}
	}
		</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false"  layout="table" action="tOrderMsgController.do?doBatchSaveOrUpdate" beforeSubmit="clickDis" callback="showMsg" tiptype="4">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td colspan="6" align="left" class="value" style="height: 40px;padding-left: 20px;">
						<a href="#" class="easyui-linkbutton" onclick="sub()" iconCls="icon-save" >保存</a>
					</td>
				</tr>
				<tr>
					<td align="right" width="120">
						<label class="Validform_label">
							客服电话:
						</label>
					</td>
					<td class="value" width="300">
							<input name="paramList[0].id" type="hidden" value="${paramList[0].id }">
							<input name="paramList[0].paraCode" type="hidden" value="phone">
							<input name="paramList[0].paraName" type="hidden" value="客服电话">
							<input name="paramList[0].sortNum" type="hidden" value="0"><!-- 排序 -->
							<input name="paramList[0].paraType" type="hidden" value="1"><!-- 参数类型 -->
					     	<input name="paramList[0].paraValue" type="text" style="width: 100px" class="inputxt" id="phone" 
					     	value="${paramList[0].paraValue }">
					</td>
					<td align="right">
						<label class="Validform_label">
							退货电话:
						</label>
					</td>
					<td class="value" width="300">
							<input name="paramList[1].id" type="hidden" value="${paramList[1].id }">
							<input name="paramList[1].paraCode" type="hidden" value="return_phone">
							<input name="paramList[1].paraName" type="hidden" value="退货电话">
							<input name="paramList[1].sortNum" type="hidden" value="1"><!-- 排序 -->
							<input name="paramList[1].paraType" type="hidden" value="1"><!-- 参数类型 -->
					     	<input name="paramList[1].paraValue" type="text" style="width: 100px" class="inputxt" id="returnPhone" value="${paramList[1].paraValue }">
					</td>
					<td align="right">
						<label class="Validform_label">
							退货地址:
						</label>
					</td>
					<td class="value">
							<input name="paramList[2].id" type="hidden" value="${paramList[2].id }">
							<input name="paramList[2].paraCode" type="hidden" value="return_address">
							<input name="paramList[2].paraName" type="hidden" value="退货地址">
							<input name="paramList[2].sortNum" type="hidden" value="2"><!-- 排序 -->
							<input name="paramList[2].paraType" type="hidden" value="1"><!-- 参数类型 -->
					     	<input name="paramList[2].paraValue" type="text" style="width: 400px" class="inputxt" id="returnAddress" value="${paramList[2].paraValue }" maxlength="50" datatype="*" nullmsg="请填写退货地址！">
					     	<span class="Validform_checktip">退货地址在50字以内</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							下单短信提醒:
						</label>
					</td>
					<td class="value" colspan="5">
							<input name="paramList[3].id" type="hidden" value="${paramList[3].id }">
							<input name="paramList[3].paraCode" type="hidden" value="order_confirm">
							<input name="paramList[3].paraName" type="hidden" value="下单短信提醒">
							<input name="paramList[3].sortNum" type="hidden" value="3"><!-- 排序 -->
							<input name="paramList[3].paraType" type="hidden" value="1"><!-- 参数类型 -->
							<input name="paramList[3].paraValue" type="hidden" value='${paramList[3].paraValue }'><!-- 参数值 -->
							<div id="order_confirm_div">您好，您在尖刀零售商标准版的订单（订单号）将被保留一个小时。点击首页->我的->待付款订单，进行支付。如有疑问联系客服：<span class="phone">${paramList[0].paraValue }</span></div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							付完款短信提醒:
						</label>
					</td>
					<td class="value" colspan="5">
							<input name="paramList[4].id" type="hidden" value="${paramList[4].id }">
							<input name="paramList[4].paraCode" type="hidden" value="order_pay">
							<input name="paramList[4].paraName" type="hidden" value="付完款短信提醒">
							<input name="paramList[4].sortNum" type="hidden" value="4"><!-- 排序 -->
							<input name="paramList[4].paraType" type="hidden" value="1"><!-- 参数类型 -->
							<input name="paramList[4].paraValue" type="hidden" value='${paramList[4].paraValue }'><!-- 参数值 -->
							<div id="order_pay_div">您好，您在尖刀零售商标准版的订单（订单号）已经支付成功！订单详情页可关注物流状况。如有疑问联系客服：<span class="phone">${paramList[0].paraValue }</span></div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							已发货短信提醒:
						</label>
					</td>
					<td class="value" colspan="5">
							<input name="paramList[5].id" type="hidden" value="${paramList[5].id }">
							<input name="paramList[5].paraCode" type="hidden" value="order_send">
							<input name="paramList[5].paraName" type="hidden" value="已发货短信提醒">
							<input name="paramList[5].sortNum" type="hidden" value="5"><!-- 排序 -->
							<input name="paramList[5].paraType" type="hidden" value="1"><!-- 参数类型 -->
							<input name="paramList[5].paraValue" type="hidden" value='${paramList[5].paraValue }'><!-- 参数值 -->
							<div id="order_send_div">您好，您的订单（订单号）已发货（物流单号：（物流单号））如有疑问联系客服：<span class="phone">${paramList[0].paraValue }</span></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							申请退款成功短信提醒:
						</label>
					</td>
					<td class="value" colspan="5">
							<input name="paramList[6].id" type="hidden" value="${paramList[6].id }">
							<input name="paramList[6].paraCode" type="hidden" value="order_apply">
							<input name="paramList[6].paraName" type="hidden" value="申请退款成功短信提醒">
							<input name="paramList[6].sortNum" type="hidden" value="6"><!-- 排序 -->
							<input name="paramList[6].paraType" type="hidden" value="1"><!-- 参数类型 -->
							<input name="paramList[6].paraValue" type="hidden" value='${paramList[6].paraValue }'><!-- 参数值 -->
							<div id="order_apply_div">您好，您的退货申请已通过。如尚未收货，我们将尝试追回商品，如无法追回，请您拒收。如您已收货，请将退货商品寄到：<span class="returnAddress">${paramList[2].paraValue }</span>，退货组收，电话：<span class="returnPhone">${paramList[1].paraValue }</span>。请将发货单或联系方式写明放入寄回的商品中，且快递不要选择到付。仓库收到商品后，将为你退款。如有疑问联系客服：<span class="phone">${paramList[0].paraValue }</span></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">
						<label class="Validform_label">
							退款已打短信提醒:
						</label>
					</td>
					<td class="value" colspan="5">
							<input name="paramList[7].id" type="hidden" value="${paramList[7].id }">
							<input name="paramList[7].paraCode" type="hidden" value="order_returnSucc">
							<input name="paramList[7].paraName" type="hidden" value="退款已打短信提醒">
							<input name="paramList[7].sortNum" type="hidden" value="7"><!-- 排序 -->
							<input name="paramList[7].paraType" type="hidden" value="1"><!-- 参数类型 -->
							<input name="paramList[7].paraValue" type="hidden" value='${paramList[7].paraValue }'><!-- 参数值 -->
							<div id="order_returnSucc_div">您好，您的订单（订单号）退款申请已处理，商品退款：（退款金额）元，支付宝/财付通/银行卡原路退款预计1-7个工作日到账，请您留意最近的账户资金变化。如有疑问联系客服：<span class="phone">${paramList[0].paraValue }</span></div>
					</td>
				</tr>
				
			</table>
		</t:formvalid>
 </body>
