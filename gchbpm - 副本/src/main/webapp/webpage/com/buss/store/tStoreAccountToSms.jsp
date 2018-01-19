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
	<t:formvalid formid="formobj" dialog="false" usePlugin="password"
		layout="table" action="tStoreAccountController.do?doInfoToSms" tiptype="4"  >
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
					<input id="retailerName" name="retailerName" value="${tStoreAccountInfo.retailerName }" type="text" style="width: 50%" maxlength="20" readonly="readonly">
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
					<select id="type" name="type" style="width: 100px">
						<option value="0" selected="selected">扣费</option>
					</select>
				</td>
				<td align="right" width="70px">
					<label class="Validform_label"> 摘要: </label>
				</td>
				<td class="value" width="400px">
					<select id="summary" name="summary"  style="width: 100px">
						<option value="7" selected="selected">短信条数充值</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" width="70px">
					<label class="Validform_label"> 充值条数: </label>
				</td>
				<td class="value" width="400px">
					<input id="smsNumber" name="smsNumber" type="text" style="width:50%" class="inputxt" maxlength="20" datatype="d" onchange="convertCurrency()">
					<span class="Validform_checktip"></span>
				</td>
				<td class="value" colspan="2">
					<label class="Validform_label"> 扣费金额：</label><span id="moneySpan"></span>元
					<input id="operateMoney" name="operateMoney" type="text" style="width:50%;display:none" class="inputxt"  >
				</td>
			</tr>
			<tr>
				<td align="right" width="70px">
					<label class="Validform_label">备注: </label>
				</td>
				<td class="value" colspan="3">
					<input id="remark" name="remark" type="text" style="width: 80%" class="inputxt" maxlength="20">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			
			<tr id="btntr" >
				<td class="value" align="center" colspan="4">
					<a href="#" class="easyui-linkbutton l-btn" id="btn" onclick="submit()" iconCls="icon-save">提交</a> 
				</td>
			</tr>
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
						<td id="pop_type">扣费</td>
					</tr>
					<tr>
						<td>摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
						<td id="pop_summary">短信条数充值</td>
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
	$(document).ready(function(){
		
	});
	
	function convertCurrency() {
	 	var $moneyInput = $("#smsNumber");
	 	var number = $moneyInput.val();
	 	var money = number*0.065;
	    //获取消费金额
	    $("#moneySpan").text(money);
	    $("#operateMoney").val(money);
	    $("#pop_money").text(money+"元");
	    
	    var remark = "短信条数充值"+number+" 条，扣费"+money+"元";
	    $("#remark").val(remark);
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
					       url: "tStoreAccountController.do?doInfoToSms",
					       data:$('#formobj').serialize(),
					       datatype: 'json',
					       async: false,
					       error: function(request) {
						       	tip("服务器连接错误！");
					       },
					       success: function(data) {
								var d = $.parseJSON(data);
								tip(d.msg);
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
    
    
    
</script>