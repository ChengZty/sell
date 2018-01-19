<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<t:base type="jquery,easyui,tools"></t:base>
<style type="text/css">

.pop_window_mask{
    position: fixed;
    z-index: 2000;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: #DCE2F1;
    opacity: 0.6;
    filter: alpha(opacity=60);
}
.add_sort_window{
    position: fixed;
    z-index: 2001;
    top: 50%;
    left: 50%;
    -webkit-transform:translate(-50%,-50%);
    transform:translate(-50%,-50%); 
    width: 300px;
    height: 200px;
    border-radius: 4px;
    background-color: #fff;
    border: 1px solid #aaa;
}
.add_sort_window .add_sort_title{
    height: 20px;
    line-height: 20px;
    text-align: center;
    font-size: 14px;
}
.add_sort_window .add_sort_table{
    margin-left: 30px;
}
.add_sort_window .input_sort_name{
    display: inline-block;
    margin-left: 25px;
    width: 240px;
    height: 30px;
    text-align: center;
    line-height: 30px;
    font-size: 16px;
}
.add_sort_window .btns{
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 35px;
    border-top: 1px solid #aaa;
}
.add_sort_window .btns button{
    width: 50%;
    height: 100%;
    font-size: 13px;
    cursor: pointer;
}
.add_sort_window .btns button:active{
    background-color: #548086;
}
.add_sort_window .btn_sort_cancle{
    position: absolute;
    left: 0;
    bottom: 0;
    border-right: 1px solid #aaa;
}
.add_sort_window .btn_sort_confirm{
    position: absolute;
    right: 0;
    bottom: 0;
}
</style>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tStoreAccountController.do?doInfoUpdate" tiptype="4">
		<input id="id" name="id" type="hidden" >
		<input id="createName" name="createName" type="hidden" >
		<input id="createBy" name="createBy" type="hidden" >
		<input id="createDate" name="createDate" type="hidden" >
		<input id="updateName" name="updateName" type="hidden" >
		<input id="updateBy" name="updateBy" type="hidden" >
		<input id="updateDate" name="updateDate" type="hidden" >
		<input id="status" name="status" type="hidden" value="A">
		<input id="retailerId" name="retailerId" type="hidden" value="${tStoreAccountInfo.retailerId }">
		<table style="width: 990px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right" width="70px">
					<label class="Validform_label"> 客户账号: </label>
				</td>
				<td class="value" colspan="3">
					<input id="retailerName" name="retailerName" value="${tStoreAccountInfo.retailerName }" onchange="getAccountInfo()" type="text" style="width: 50%" maxlength="20" datatype="*">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right" width="70px">
					<label class="Validform_label"> 客户名称: </label>
				</td>
				<td class="value" width="400px">
					<input id="retailerRealname" name="retailerRealname" value="${tStoreAccountInfo.retailerRealname }" type="text" style="width: 50%" readonly="readonly"  />
				</td>
				<td align="right" width="70px">
					<label class="Validform_label"> 当前余额: </label>
				</td>
				<td class="value" width="400px">
					<label class="Validform_label"> <span id="remainMoney"> ${tStoreAccountInfo.remainMoney }</span>元</label>
				</td>
			</tr>
			<tr>
				<td align="right" width="70px">
					<label class="Validform_label"> 业务类型: </label>
				</td>
				<td class="value" width="400px">
					<select id="type"  style="width: 100px" onchange="changeType()" disabled="disabled" >
						<option value="1" >充值</option>
						<option value="0" >扣费</option>
					</select>
					<input name="type" value="${type }" type="hidden" style="width: 50%" />
				</td>
				<td align="right" width="70px">
					<label class="Validform_label"> 摘要: </label>
				</td>
				<td class="value" width="400px">
					<select id="summary" name="summary"  onchange="changeSummary()" style="width: 100px">
						
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" width="70px">
					<label class="Validform_label"> 操作金额: </label>
				</td>
				<td class="value" width="400px">
					<input id="operateMoney" name="operateMoney" type="text" style="width:50%" class="inputxt" maxlength="20" datatype="d" onchange="convertCurrency()">
					<span class="Validform_checktip"></span>
				</td>
				<td class="value" colspan="2">
					<label class="Validform_label">(人民币：<span id="capital" > </span>) </label>
				</td>
			</tr>
			<tr>
				<td align="right" width="70px">
					<label class="Validform_label">备注: </label>
				</td>
				<td class="value" colspan="3">
					<input id="remark" name="remark" type="text" style="width: 80%" class="inputxt" maxlength="20" datatype="*">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			
<!-- 			<tr id="btntr"> -->
<!-- 				<td class="value" align="center" colspan="4"> -->
<!-- 					<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="submit()" iconCls="icon-save">提交</a>  -->
<!-- 				</td> -->
<!-- 			</tr> -->
		</table>
	</t:formvalid>
	
	<div class="pop_window">
		<!--添加分类-->
		<div id="id_add_sort" style="display: none;">
			<div class="pop_window_mask"></div>
			<div class="add_sort_window">
				<p class="add_sort_title">提示:</p>
				<table class="add_sort_table">
					<tr>
						<td>客户名称：</td>
						<td id="pop_rname">${tStoreAccountInfo.retailerRealname }</td>
					</tr>
					<tr>
						<td>业务类型：</td>
						<td id="pop_type"></td>
					</tr>
					<tr>
						<td>摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
						<td id="pop_summary"></td>
					</tr>
					<tr>
						<td>消费金额：</td>
						<td id="pop_money"></td>
					</tr>
				</table>
				<div class="btns">
					<button type="button" class="btn_sort_cancle">取消</button>
					<button type="button" class="btn_sort_confirm">确认</button>
				</div>
			</div>
		</div>
	</div>
</body>

<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.config.js?20160607"></script>
<script type="text/javascript" charset="utf-8" src="plug-in/Formdesign/js/ueditor/ueditor.all.js?20160607">
	
</script>
<script type="text/javascript">
	var type = "${type}";
	$(document).ready(function(){
		$("#type").val(type);
// 		document.getElementById("type")[${type}].selected=true;
		changeType();
		var rid = "${tStoreAccountInfo.retailerName }";
		
	});
	
	function changeType(){
		if(type == "1"){ //充值的摘要
			var summary = '<option value="0" >充值本金</option>\
				<option value="1" >充值赠送</option>\
				<option value="2" >其他充值</option>\
			';
			$("#summary").html(summary);
			/* 更新提示框中的信息 */
			$("#pop_type").html('充值');
			$("#pop_summary").html('充值本金');
		}
		if(type == "0"){//扣费的摘要
			var summary = '<option value="3" >营销短信制作</option>\
				<option value="4" >短信链接资讯页制作</option>\
				<option value="5" >短信链接产品页制作</option>\
				<option value="6" >其他扣费</option>\
			';
			$("#summary").html(summary);
			/* 更新提示框中的信息 */
			$("#pop_type").html("消费");
			$("#pop_summary").html('营销短信制作');
		}
	}
	function changeSummary(){
		var summary = $("#summary").find("option:selected").text();
		$("#pop_summary").html(summary);
	}

	//通过客户账号查询客户的账户信息
	function getAccountInfo(){
		var retailerName = $("#retailerName").val();
		var url = "tStoreAccountController.do?getTStoreAccountInfo&retailerName=" + retailerName;
		$.ajax({
			url : url,
			type : 'post',
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var tStoreAccount = d.obj;
					$("#retailerId").val(tStoreAccount.retailerId);
					$("#retailerRealname").val(tStoreAccount.retailerRealname);
					$("#remainMoney").text(tStoreAccount.remainMoney);
					/* 更新提示框中的客户名称 */
					$("#pop_rname").html(tStoreAccount.retailerRealname);
				} else{
					tip(d.msg);
				}
			}
		});
	}
	
	
	//保存
	function submit(){
		var money = $("#operateMoney").val();
		if(money == ""){
			tip("金额为空，请填写金额！");
			return false;
		}
		addSortWindowVo.show();
	}
	
	/** AddSortWindowVo start */
    var AddSortWindowVo = (function(){
        function AddSortWindowVo(box){
            if(this instanceof AddSortWindowVo){
                this.$box= $(box);
                this.$input = this.$box.find('.input_sort_name');
                this.$btnCancel = this.$box.find('.btn_sort_cancle');
                this.$btnConfirm = this.$box.find('.btn_sort_confirm');

                this.initView();
            }else{
                return new AddSortWindowVo(box);
            }
        }

        AddSortWindowVo.prototype = {
            constructor: AddSortWindowVo,
            initView: function(){
                this.bindClickCancle();
                this.bindClickConfirm();
            },
            show: function(){
                this.$box.fadeIn();
            },
            hide: function(){
                this.$box.fadeOut();
            },
            bindClickCancle: function(){
                var _this = this;
                _this.$btnCancel.click(function(){
                    _this.hide();
                });
            },
            bindClickConfirm: function(){
                var _this = this;
                _this.$btnConfirm.click(function(){
                	/* $("#formobj").submit(); */
					$.ajax({
					       cache: true,
					       type: "POST",
					       url: "tStoreAccountController.do?doInfoUpdate",
					       data:$('#formobj').serialize(),
					       datatype: 'json',
					       async: false,
					       error: function(request) {
						       	tip("服务器连接错误！");
					       },
					       success: function(data) {
								var d = $.parseJSON(data);
								tip(d.msg);
								$(window.parent.document).find('table.ui_dialog tr input[value="关闭"]').click();
					       }
					});
                    _this.hide();
                });
            }
        };

        return AddSortWindowVo;
    })();
    /** AddSortWindowVo end */
    
    var addSortWindowVo = new AddSortWindowVo('#id_add_sort');
    
    
	//////////////////////////////////////////////////////////////////////
	//BLL.JS 中数字字符转换成大写人民币通用方法封装
	//////////////////////////////////////////////////////////////////////
	function convertCurrency() {
	 	var $moneyInput = $("#operateMoney");
	 	var currencyDigits = $moneyInput.val();
	
		/* 更新提示框中的金额信息 */
	    $("#pop_money").text(currencyDigits+"元");
	 	console.log(typeof currencyDigits);
	 	console.log(currencyDigits);
	    var MAXIMUM_NUMBER = 99999999999.99;  //最大值
	    // 定义转移字符
	    var CN_ZERO = "零";
	    var CN_ONE = "壹";
	    var CN_TWO = "贰";
	    var CN_THREE = "叁";
	    var CN_FOUR = "肆";
	    var CN_FIVE = "伍";
	    var CN_SIX = "陆";
	    var CN_SEVEN = "柒";
	    var CN_EIGHT = "捌";
	    var CN_NINE = "玖";
	    var CN_TEN = "拾";
	    var CN_HUNDRED = "佰";
	    var CN_THOUSAND = "仟";
	    var CN_TEN_THOUSAND = "万";
	    var CN_HUNDRED_MILLION = "亿";
	    var CN_DOLLAR = "元";
	    var CN_TEN_CENT = "角";
	    var CN_CENT = "分";
	    var CN_INTEGER = "整";
	 
	    // 初始化验证:
	    var integral, decimal, outputCharacters, parts;
	    var digits, radices, bigRadices, decimals;
	    var zeroCount;
	    var i, p, d;
	    var quotient, modulus;
	 
	    currencyDigits = currencyDigits.replace(/,/g, ""); 
	    currencyDigits = currencyDigits.replace(/^0+/, ""); 
	    //判断输入的数字是否大于定义的数值
	    if (Number(currencyDigits) > MAXIMUM_NUMBER) {
	        alert("您输入的数字太大了");
	        $moneyInput.focus();
	        return;
	    }
	     
	    parts = currencyDigits.split(".");
	    if (parts.length > 1) {
	        integral = parts[0];
	        decimal = parts[1];
	        decimal = decimal.substr(0, 2);
	    }
	    else {
	        integral = parts[0];
	        decimal = "";
	    }
	    // 实例化字符大写人民币汉字对应的数字
	    digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
	    radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
	    bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
	    decimals = new Array(CN_TEN_CENT, CN_CENT);
	    
	    outputCharacters = "";
	    //大于零处理逻辑
	    if (Number(integral) > 0) {
	        zeroCount = 0;
	        for (i = 0; i < integral.length; i++) {
	            p = integral.length - i - 1;
	            d = integral.substr(i, 1);
	            quotient = p / 4;
	            modulus = p % 4;
	            if (d == "0") {
	                zeroCount++;
	            }
	            else {
	                if (zeroCount > 0) {
	                    outputCharacters += digits[0];
	                }
	                zeroCount = 0;
	                outputCharacters += digits[Number(d)] + radices[modulus];
	            }
	            if (modulus == 0 && zeroCount < 4) {
	                outputCharacters += bigRadices[quotient];
	            }
	        }
	        outputCharacters += CN_DOLLAR;
	    }
	    // 包含小数部分处理逻辑
	    if (decimal != "") {
	        for (i = 0; i < decimal.length; i++) {
	            d = decimal.substr(i, 1);
	            if (d != "0") {
	                outputCharacters += digits[Number(d)] + decimals[i];
	            }
	        }
	    }
	    //确认并返回最终的输出字符串
	    if (outputCharacters == "") {
	        outputCharacters = CN_ZERO + CN_DOLLAR;
	    }
	    if (decimal == "") {
	        outputCharacters += CN_INTEGER;
	    }
	 
	    //获取人民币大写
	    $("#capital").text(outputCharacters);
	    console.log(outputCharacters);
	}
</script>