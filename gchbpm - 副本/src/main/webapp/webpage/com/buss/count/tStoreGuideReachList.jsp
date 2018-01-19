<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tStoreGuideMonTargetList" checkbox="true" fitColumns="false" title="导购目标达成" sortName="targetMonth" sortOrder="desc"
  actionUrl="tStoreGuideMonTargetController.do?datagridForReach" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="月份"  field="targetMonth"  query="true" queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="店铺名称"  field="storeId"  dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="店铺月目标"  field="storeTargetMoney" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="店铺实际业绩"  field="storeTotalMoney"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="店铺目标达成率"  field="storeReachRate" formatterjs="fmtPersent"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="导购姓名"  field="guideName" query="true"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="导购月目标"  field="guideTargetMoney"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="导购实体店业绩"  field="offlineMoney"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="导购线上业绩"  field="onlineMoney"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="导购总业绩"  field="guideTotalMoney"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="导购目标达成率"  field="guideReachRate"  formatterjs="fmtPersent" queryMode="single"  width="90"></t:dgCol>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
	<input type="hidden" name="notInitSearch" value="1"/>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 	//给时间控件加上样式
	$("#tStoreGuideMonTargetListtb").find("input[name='targetMonth_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${targetMonth_begin}")
	.attr("id","targetMonth_begin").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'targetMonth_end\')}'});});
	$("#tStoreGuideMonTargetListtb").find("input[name='targetMonth_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").val("${targetMonth_end}")
	.attr("id","targetMonth_end").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'targetMonth_begin\')}'});});
 });

 function fmtPersent(value,row,index){
	 if(value==""||value=="null"){
		 return "";
	 }
	 return value+"%";
 }

//导出
function ExportXls() {
	JeecgExcelExport("tStoreGuideMonTargetController.do?exportXls","tStoreGuideMonTargetList");
}


 </script>