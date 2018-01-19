<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,layer"></t:base>
<style>
	.easyui-layout{
		max-height: 460px;
	}
	.send_msg_box{
		position: relative;
		width: 100%;
	}
	.send_msg_box .msg_box{
		position: absolute;
		left:50%;
		top:0;
		margin: 20px 0 0 -290px;
		width: 580px;
	}
	.send_msg_box .text_msg{
		width: 100%;
		height: 150px;
		font-size: 16px;
		line-height: 1.6;
	}
	.send_msg_box .btn_box{
		margin: 20px 0 0 155px;
	}

	.send_msg_box .msg_btn{
		display: inline-block;
		margin-right: 20px;
		width: 70px;
		height: 30px;
		line-height: 30px;
		font-size: 16px;
		border: 1px solid #626262;
		border-radius: 4px;
		text-align: center;
		user-select: none;
		cursor: pointer;
	}
	.send_msg_box .msg_btn:active{
		background: #626262;
		color: #fff;
	}
	
</style>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
  <t:datagrid name="wechatList" checkbox="true" fitColumns="false" title="联系人列表" actionUrl="wx.do?getContact" idField="id" fit="true" queryMode="group">
           <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="用户名"  field="UserName" hidden="true"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="头像"  field="HeadImgUrl" hidden="true"  image="true" imageSize="50,50" queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="备注名"  field="RemarkName"    queryMode="single"  width="100"></t:dgCol>
			<t:dgCol title="昵称"  field="NickName"    queryMode="single"  width="100"></t:dgCol>
			<t:dgCol title="性别"  field="Sex" replace="未知_0,男_1,女_2"  queryMode="single"  width="50"></t:dgCol>
			<t:dgCol title="省"  field="Province"    queryMode="single"  width="80"></t:dgCol>
			<t:dgCol title="市"  field="City"    queryMode="single"  width="80"></t:dgCol>
			<t:dgCol title="签名"  field="Signature"    queryMode="single"  width="500"></t:dgCol>
			<t:dgToolBar title="全选" icon="icon-add" funname="selectAll"></t:dgToolBar>
  </t:datagrid>
  	</div>
</div>

<script>
$(function(){
	//增加分页选择
	var optionsHtml = "<option>100</option><option>500</option><option>1000</option><option>5000</option>";
	$("select.pagination-page-list").append(optionsHtml);
})

//全选
function selectAll(){
	var $allCheck = $("div.datagrid-view div.datagrid-header-check input[type='checkbox']");
	  
	var $allTr = $("div.datagrid-body table tr");
	if($allCheck.is(':checked')){
		$allCheck.prop("checked", false);
		$("#wechatList").datagrid('clearSelections');
// 		$allTr.removeClass("datagrid-row-selected");
	}else{
		$allCheck.prop("checked", true);
		$("#wechatList").datagrid('selectAll');
// 		$allTr.addClass("datagrid-row-selected");
	}
// 	$allCheck.click();
}


// 	var hintWords = '请输入需要发送的文字... ';
	var msgDomStr = '\
		<div class="send_msg_box">\
			<div class="msg_box">\
				<textarea class="text_msg" placeholder="请输入需要发送的文字..."></textarea>\
				<div class="btn_box">\
					<div class="msg_btn btn_exit_wechat">退出</div>\
					<div class="msg_btn btn_reset_msg">重置</div>\
					<div class="msg_btn btn_send_msg">发送</div>\
				</div>\
			</div>\
		</div>\
	';
	$('body').append(msgDomStr);

	//点击退出
	$('.btn_exit_wechat').click(function(){
		wxLogout();
	});

	//点击重置
	$('.btn_reset_msg').click(function(){
		$('.text_msg').val('').focus();
	});

	//textarea提示处理
	$('.text_msg').blur(function(){
		var $curText = $(this);
// 		if($curText.val() === ''){
// 			$curText.val(hintWords);
// 		}
	});
	$('.text_msg').focus(function(){
		var $curText = $(this);
// 		if($curText.val() === hintWords){
// 			$curText.val('');
// 		}
	});

	//点击发送
	$('.btn_send_msg').click(function(){
		var msg = $('.text_msg').val();
		var rowsData = $("#wechatList").datagrid("getSelections");  //多条选择
		var users = [];//用户
		//判断是否有勾选
		if (rowsData.length > 0) {
			for ( var i = 0; i < rowsData.length; i++) {
				users.push(rowsData[i].UserName);
			}
		}else{
			tip('请选择好友');
			return;
		}
// 		console.log(users);	
		//判断空
		if(msg.trim() === ''){
// 		if(msg.trim() === '' || msg === hintWords){
			tip('请输入文字');
			return;
		}else{
			sendMsg(msg.trim(),users);
		}
		
	});	

	//发送消息(内容，用户)
	function sendMsg(content,users){
		$.ajax({
			type: "POST",
			url: "wx.do?sendMsg",
			data: {'content':content,'users':users.join(',')},
			dataType: "json",
			success: function(data){
					if(data == "success")
					{
						tip('发送成功!');
					}else{
						tip('发送失败啦，请联系系统管理员!');
					}
			},
		error: function(XMLHttpRequest, textStatus, errorThrown) 
		{
				tip('网络连接失败'+errorThrown);
			}
		})
	}
	//退出微信
	function wxLogout(){
		$.ajax({
			type: "POST",
			url: "wx.do?wxLogout",
			data: {},
			dataType: "json",
			success: function(data){
				window.location.href="wx.do?index"
			},
		error: function(XMLHttpRequest, textStatus, errorThrown) 
		{
				tip('网络连接失败'+errorThrown);
			}
		})
	}
</script>
