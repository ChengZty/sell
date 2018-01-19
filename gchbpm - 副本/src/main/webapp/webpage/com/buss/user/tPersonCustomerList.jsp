<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  
  <t:datagrid name="tPersonList" checkbox="true" fitColumns="false" title="顾客信息表" actionUrl="tPersonController.do?datagrid&user_Type=04" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="头像"  field="photo"  image="true" imageSize="70,70" width="70"></t:dgCol>
    <t:dgCol title="姓名"  field="realName"   query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="昵称"  field="name"   query="true" queryMode="single"  width="80"></t:dgCol>
<!--    extend="{style:'width:200px'}" -->
   <t:dgCol title="性别"  field="sex"    queryMode="single" dictionary="sex" width="50"></t:dgCol>
   <t:dgCol title="手机号码"  field="phoneNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="生日"  field="birthday"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="所在省"  field="province"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所在市"  field="city"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="身份证号"  field="idCard"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="件数"  field="quantity"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="金额"  field="money" hidden="true"    queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="所属导购"  field="toGuideId"  dictionary="t_s_user,id,realname,user_type='03'"  queryMode="single"  width="120"></t:dgCol> --%>
   <t:dgCol title="所属导购"  field="toGuideName"   queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="所属零售商"  field="toRetailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="VIP等级"  field="vipLevel"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否绑定导购"  field="isBind"  query="true" queryMode="single" dictionary="is_bind" width="100" ></t:dgCol>
   <t:dgCol title="备注"  field="remark"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="goView(id)" title="查看"></t:dgFunOpt>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="导出问题信息" icon="icon-putout" funname="ExportQuestionXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <div id='winPage' class='easyui-window' title=' ' style='width:320px;height:220px;' closed='true' modal='true' collapsible='false' minimizable='false' maximizable='false'>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
//  			$("#tPersonListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tPersonListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
//  			$("#tPersonListtb").find("input[name='birthday']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function goView(id){
	 var url = "tPersonController.do?goViewCustomer&id="+id;
	 window.open(url,'new');
}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPersonController.do?upload', "tPersonList");
}

//导出
function ExportXls() {
	var total = $('#tPersonList').datagrid('getData').total;
	var pageNum = 5000;
	var url = "tPersonController.do?exportCustXls";
	var datagridId = "tPersonList";
	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
	}else{
		showWinPage(total,url,datagridId,pageNum);
	}
}

function ExportQuestionXls(){
	var total = $('#tPersonList').datagrid('getData').total;
	var pageNum = 5000;
	var url = "tPersonController.do?exportCustQuestionXls";
	var datagridId = "tPersonList";
	if(total - pageNum < 0){
		JeecgExcelExportWithPars(url,datagridId,1,total);
		return ;
	}else{
		showWinPage(total,url,datagridId,pageNum);
	}
}
</script>