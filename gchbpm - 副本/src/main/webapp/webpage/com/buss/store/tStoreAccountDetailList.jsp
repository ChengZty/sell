<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div id="accountInfo_div" style="padding:10px;">
  		<p>&nbsp;&nbsp;&nbsp;${rname }</p>
  		<p>&nbsp;&nbsp;&nbsp;${money }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前可用短信条数：${smsNumber }</p>
  </div>
  <div region="center" style="padding:1px;">
  
  <t:datagrid name="tStoreAccountDetailList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="账户余额明细" actionUrl="tStoreAccountController.do?datagridDetail&rid=${rid }"  
  			 idField="id" fit="true" queryMode="group" width="100%" onLoadSuccess="callBack">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="交易日期"  field="createDate" query="true" formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="status"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="客户ID"  field="retailerId" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作金额"  field="operateMoney"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="余额"  field="remainMoney"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务类型"  field="type" replace="充值_1,扣款_0" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="摘要"  field="summary"  replace="充值本金_0,充值赠送_1,其他充值_2,营销短信制作_3,短信链接资讯页制作_4,短信链接产品页制作_5,其他扣费_6,短信充值_7,端口扣费_8"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"    queryMode="single"  width="200"></t:dgCol>
   
   <t:dgToolBar title="导出"  icon="icon-putout" url="tStoreAccountController.do?exportDetailXls&rid=${rid }" funname="ExportDeatilXls"></t:dgToolBar>
  </t:datagrid>
  <input type="hidden" name="notInitSearch" value="1" />
  </div>
 </div>
 <script type="text/javascript">
  $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tStoreAccountDetailListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_begin}")
				.attr("id","createDate_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createDate_end\')}'});});
			$("#tStoreAccountDetailListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${searchTime_end}")
				.attr("id","createDate_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createDate_begin\')}'});});
 			//修改表格top，显示上方的div
 			$("#tStoreAccountDetailListtb").prepend($("#accountInfo_div"));
 }); 
//导出
  function ExportDeatilXls(title,url,gname) {
  	var total = $('#'+gname).datagrid('getData').total;
  	var pageNum = 5000;
  	if(total - pageNum < 0){
  		JeecgExcelExportWithPars(url,gname,1,total);
  		return ;
  	}else{
  		showWinPage(total,url,gname,pageNum);
  	}
  }
  
  //查询回调，修改footer的样式显示，统计信息
  function callBack(data){
// 	 console.log(data);
	 var footerTr = $("div .datagrid-view2 div.datagrid-footer tr");
	 var footerColTr = $("div .datagrid-view1 div.datagrid-footer tr");
	 $(footerTr).find("td[field='createDate']").remove();
	 $(footerTr).find("td[field='remainMoney']").remove();
	 $(footerTr).find("td[field='type']").remove();
	 $(footerTr).find("td[field='summary']").remove();
	 $(footerTr).find("td[field='remark']").remove();
	 $(footerTr).find("td[field='operateMoney']").attr("width","1000px");
	 for(var i = 1;i <=10;i++){
		 $(footerTr).find("div[class='datagrid-cell datagrid-cell-c"+i+"-operateMoney']").attr("style","width:100%;");
	 }
	 $(footerColTr).find("td[class='datagrid-td-rownumber']").remove();
  }

  
 </script>