<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tFocusCustomerList" checkbox="true" fitColumns="true" title="待发展顾客列表(根据筛选条件添加顾客发送短信)" actionUrl="tFocusCustomerMiddleController.do?datagrid&batch_No=${batchNo}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="添加时间"  field="createDate"  query="true"  queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150"></t:dgCol>
   <t:dgCol title="姓名"  field="name"   query="true" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="性别"  field="sex"   query="true" queryMode="single" dictionary="sex" width="50"></t:dgCol>
   <t:dgCol title="手机号"  field="phoneNo"  query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="生日"  field="birthday" formatter="yyyy-MM-dd"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="年龄段"  field="birthdayRank" query="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="星座"  field="constellation" query="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="生肖"  field="zodiac" query="true"  queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="登记地区"  field="registerArea" query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="登记店铺"  field="phoneRegShop" dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" query="true"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="识别地区"  field="phoneArea" query="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="VIP等级"  field="vipLevel"  query="true" queryMode="single"  width="80"></t:dgCol> --%>
   <t:dgCol title="推送次数"  field="pushCount"  query="true"  queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="点击次数"  field="clickNumber"  query="true"  queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="购买次数"  field="buyCount"  query="true"  queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="备注"  field="remark" query="true" queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tFocusCustomerMiddleController.do?goView" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%-- 	<t:dgToolBar title="按搜索条件添加" icon="icon-add" funname="addCst"></t:dgToolBar> --%>
	<t:dgToolBar title="按搜索条件删除" icon="icon-remove" funname="delCst"></t:dgToolBar>
   <t:dgToolBar title="批量选择删除"  icon="icon-remove"  funname="deleteSelect"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <div id='winPage' class='easyui-window' title=' ' style='width:320px;height:220px;' closed='true' modal='true' collapsible='false' minimizable='false' maximizable='false'>
 
 </div>
 
 	<!--是 否  弹窗-->
	<div class="simple_popwindow">
		<div class="mask_simple_popwindow"></div>
		<div class="simple_popwindow_content">
			<p class="window_title"></p>
			<div class="btn_popwindow_box">
				<button type="button" class="simple_popwindow_no" id="simple_popwindow_no">否</button>
				<button type="button" class="simple_popwindow_yes" id="simple_popwindow_yes">是</button>
			</div>
		</div>
	</div>
	
 <script type="text/javascript">
 $(document).ready(function(){
	 //初始化查询按钮和重置按钮
	 initButton();
	if(<%=request.getAttribute("edition") %> =='2'){
		$("#tFocusCustomerListForm").append("<span><span style='width:90px;text-align: right;display: inline-block;'>所属导购：</span><input onkeypress='EnterPress(event)' onkeydown='EnterPress()' type='text' name='guideName' class='inuptxt' style='width: 100px'></span>");
	}
		
	 $("#tFocusCustomerListForm").append('<span style="display:-moz-inline-box;display:inline-block;">\
		 <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="类型">类型：\
		 </span>\
		 <input type="checkbox" id="type1" name="selectType" class="selectType" style="width: 20px" t_value="1">无反应顾客\
		 <input type="checkbox" id="type2" name="selectType" class="selectType" style="width: 20px" t_value="2">点击顾客\
		 <input type="checkbox" id="type3" name="selectType" class="selectType" style="width: 20px" t_value="3">交易顾客\
	 </span>\
		 ');
		 $("#tFocusCustomerListForm").append('<input type="hidden" id="unOrder" name="unOrder" value="0">');
		 $("#tFocusCustomerListForm").append('<input type="hidden" id="types" name="types" value="">');
		 $(".selectType").on("click",function(){//顾客类型，多选文本框，选中多值处理，用,拼装
			 var types = "";
			 var $obj = $("#tFocusCustomerListForm").find("input[name=selectType]:checked");
			  if($obj.length>0){
				  $obj.each(function(){
					  types += $(this).attr("t_value")+",";
				  }); 
				 $("#types").val(types); 
			 }else{
				 $("#types").val(""); 
			 }
		 });
		 $("#tFocusCustomerListtb").find("input[name^='createDate']").attr("class","Wdate").attr("style","height:25px;width:130px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
 });
 
 function initButton(){
	 //查询按钮a标签
	 var $searchA = $("#tFocusCustomerListtb .datagrid-toolbar>span:eq(1)>a:eq(0)");
	 $($searchA).attr("onclick","addCst()");
	 $($searchA).find("span.icon-search").text("查询并添加");
	 //重置按钮a标签
	 var $resetA = $("#tFocusCustomerListtb .datagrid-toolbar>span:eq(1)>a:eq(1)");
	 $($resetA).find("span.icon-reload").text("查询已选");
 }
 
//导出
function ExportXls(){
	var total = $('#tFocusCustomerList').datagrid('getData').total;
	var pageNum = 5000;
	var url = "tFocusCustomerMiddleController.do?exportXls&batchNo=${batchNo}";
	var datagridId = "tFocusCustomerList";
	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
	}else{
		showWinPage(total,url,datagridId,pageNum);
	}
}

function getContent(params){
// 	console.log(params);
	var content = '';
	var paramArr = params.split('&');
	for(var i=0; i < paramArr.length; i ++){
		var paramObj = paramArr[i];
		var valArr = paramObj.split('=');
		var pValue = decodeURI(valArr[1]);//参数值
		if(pValue != '' && "createDate_begin" == valArr[0]){
			pValue = pValue.replace("+"," ").replace("%3A",":").replace("%3A",":");
			content += ",添加时间>="+pValue;
		}
		if(pValue != '' && "createDate_end" == valArr[0]){
			pValue = pValue.replace("+"," ").replace("%3A",":").replace("%3A",":");
			if(content.indexOf("添加时间>=") != -1){
				content += "且<="+pValue ;
			}else{
				content += ",添加时间<="+pValue;
			}
		}
		if(pValue != '' && "name" == valArr[0]){
			content += ",昵称为"+pValue;
		}
		if(pValue != '' && "sex" == valArr[0]){
			if(pValue == '0'){
				content += ",性别为男";
			}else{
				content += ",性别为女";
			}
		}
		if(pValue != '' && "phoneNo" == valArr[0]){
			content += ",手机号码以"+pValue + "为前缀";
		}
		if(pValue != '' && "birthdayRank" == valArr[0]){
			content += ",年龄段为"+pValue;
		}
		if(pValue != '' && "constellation" == valArr[0]){
			content += ",星座为"+pValue;
		}
		if(pValue != '' && "zodiac" == valArr[0]){
			content += ",生肖为"+pValue;
		}
		if(pValue != '' && "registerArea" == valArr[0]){
			content += ",登记地区为"+pValue;
		}
		if(pValue != '' && "phoneRegShop" == valArr[0]){
			var text =$("#tFocusCustomerListForm select[name='phoneRegShop'] option[value='"+pValue+"']").text();
			content += ",登记店铺为"+text;
		}
		if(pValue != '' && "phoneArea" == valArr[0]){
			content += ",识别地区为"+pValue;
		}
		if(pValue != '' && "vipLevel" == valArr[0]){
			content += ",VIP等级为"+pValue;
		}
		if(pValue != '' && "pushCount_begin" == valArr[0]){
			content += ",推送次数>="+pValue;
		}
		if(pValue != '' && "pushCount_end" == valArr[0]){
			if(content.indexOf("推送次数>=") != -1){
				content += "且<="+pValue ;
			}else{
				content += ",推送次数<="+pValue;
			}
		}
		if(pValue != '' && "clickNumber_begin" == valArr[0]){
			content += ",点击次数>="+pValue;
		}
		if(pValue != '' && "clickNumber_end" == valArr[0]){
			if(content.indexOf("点击次数>=") != -1){
				content += "且<="+pValue ;
			}else{
				content += ",点击次数<="+pValue;
			}
		}
		if(pValue != '' && "buyCount_begin" == valArr[0]){
			content += ",购买次数>="+pValue;
		}
		if(pValue != '' && "buyCount_end" == valArr[0]){
			if(content.indexOf("购买次数>=") != -1){
				content += "且<="+pValue ;
			}else{
				content += ",购买次数<="+pValue;
			}
		}
		if(pValue != '' && "remark" == valArr[0]){
			content += ",备注中包含"+pValue;
		}
		if(pValue != '' && "types" == valArr[0]){
			content += ",顾客类型为";
			pValue = pValue.replace("%2C",",").replace("%2C",",").replace("%2C",",");
			if(pValue.indexOf('1,') != -1){
				content += " 无反应顾客";
			}
			if(pValue.indexOf('2,') != -1){
				content += " 点击顾客";
			}
			if(pValue.indexOf('3,') != -1){
				content += " 交易顾客";
			}
		}
	}
	if(content == ""){
		content = "全部";
	}else{
		content = content.substring(1);
	}
	return content;
}

//unicode转换为字符 
function unicode2Chr(str) { 
 if ('' != str) { 
  var st, t, i 
  st = ''; 
  for (i = 1; i <= str.length/4; i ++){ 
   t = str.slice(4*i-4, 4*i-2); 
   t = str.slice(4*i-2, 4*i).concat(t); 
   st = st.concat('%u').concat(t); 
  } 
  st = unescape(st); 
  return(st); 
 } 
 else 
  return(''); 
} 

//根据条件添加顾客
function addCst(){
	//createDate_begin=&createDate_end=&name=&sex=0&phoneNo=&birthdayRank=&constellation=&zodiac=&registerArea=&phoneArea=
	//&vipLevel=&pushCount_begin=&pushCount_end=&clickNumber_begin=&clickNumber_end=&buyCount_begin=&buyCount_end=&remark=&unOrder=0&types=
	var batchNo = '${batchNo}';//批次号
	var params = $("#tFocusCustomerListForm").serialize();
	var content = getContent(params);
	if(content=="全部"){
		var title = "确认添加全部顾客？"
		$.dialog.confirm(title, function(){
			doAdd(batchNo,params);
		}, function(){
		}).zindex(1999);
	}else{
		doAdd(batchNo,params);
	}
}

//按查询条件添加顾客
function doAdd(batchNo,params){
	var url = "tFocusCustomerMiddleController.do?addCst&"+params+"&batchNo="+batchNo;
	$.ajax({
		url : url,
		type : 'post',
		cache : false,
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				tFocusCustomerListsearch();
				//重置查询
//					resetSelect();
			}else{
				removeAttrDisplay();
			}
//				tip(d.msg);
		}
	});
}

//根据条件删除顾客
function delCst(){
	var batchNo = '${batchNo}';//批次号
	var params = $("#tFocusCustomerListForm").serialize();
	var content = getContent(params);
	var title = "确认按【"+content + "】搜索条件删除？"
	$.dialog.confirm(title, function(){
		var url = "tFocusCustomerMiddleController.do?delCst&"+params+"&batchNo="+batchNo;
		$.ajax({
			url : url,
			type : 'post',
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					//重置查询
					resetSelect();
				}else{
					removeAttrDisplay();
				}
				tip(d.msg);
			}
		});
	}, function(){
	}).zindex(1999);	
}

//选择删除顾客
function deleteSelect(){
	var ids = [];
	ids = getIds();
	if(!!ids && ids.length ==0){
		tip("请勾选要删除的顾客");
		return;
	}	
	var phoneNos = getPhoneNos();
	var phoneLength = phoneNos.length;
	if(phoneLength >= 5){
		phoneNos = phoneNos.slice(0,5);
		phoneNos += '等'+phoneLength+'条';
		console.log(phoneNos);
	}
	var title = "确认删除手机号码为"+phoneNos+"记录？";
	var url = "tFocusCustomerMiddleController.do?doBatchDel&ids="+ids;
	$.dialog.confirm(title, function(){
		$.ajax({
			url : url,
			type : 'post',
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					//重置查询
					resetSelect();
				}else{
					removeAttrDisplay();
				}
				tip(d.msg);
			}
		});
	}, function(){
	}).zindex(1999);
	
}

//获取选择行的id集合
function getIds() {
	var ids = [];
	var rows = $('#tFocusCustomerList').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		ids.push(rows[i]['id']);
	}
	ids.join(',');
	return ids;
};

//获取选择行的手机号码集合
function getPhoneNos() {
	var phoneNos = [];
	var rows = $('#tFocusCustomerList').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		phoneNos.push(rows[i]['phoneNo']);
	}
	phoneNos.join(',');
	return phoneNos;
};

//重置
function resetSelect(){
	//checkbox重置
	$("#types").val(""); 
	$(".selectType").removeAttr("checked");
	//重新按添加的条件查询
	searchReset("tFocusCustomerList");
}	
	
	
	
	
	
	
 </script>