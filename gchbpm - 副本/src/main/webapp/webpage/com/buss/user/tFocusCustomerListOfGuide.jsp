<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tFocusCustomerList" checkbox="false" fitColumns="true" title="待发展顾客列表"
			actionUrl="tFocusCustomerController.do?datagridForGuide&guideId=${guideId}" idField="id" fit="true" queryMode="group" >
			<t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="类型" field="type" formatterjs="getCustType" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="姓名" field="name" queryMode="single" width="90"></t:dgCol>
			<t:dgCol title="性别" field="sex" queryMode="single" dictionary="sex" width="60"></t:dgCol>
			<t:dgCol title="微信联系" field="isWxContact" queryMode="single" width="90"></t:dgCol>
			<t:dgCol title="实体店消费" field="isStoreConsum" queryMode="single" width="110"></t:dgCol>
<%-- 			<t:dgCol title="经济实力" field="finAbilityName" queryMode="single" width="90"></t:dgCol> --%>
			<t:dgCol title="手机号" field="phoneNo" queryMode="single" width="130"></t:dgCol>
			<t:dgCol title="生日" field="birthday" formatter="yyyy-MM-dd" queryMode="single" width="130"></t:dgCol>
			<t:dgCol title="年龄段" field="birthdayRank" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="星座" field="constellation" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="生肖" field="zodiac" queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="登记地区" field="registerArea" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="登记店铺" field="phoneRegShop" dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="手机识别地区" field="phoneArea" queryMode="single" width="120"></t:dgCol>
<%-- 			<t:dgCol title="顾客VIP等级" field="vipLevel" queryMode="single" width="100"></t:dgCol> --%>
<%-- 			<t:dgCol title="备注" field="remark" queryMode="single" width="120"></t:dgCol> --%>
		</t:datagrid>
		<input type="hidden" name="custListForGuide" value="1"/>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
});

function getCustType(value,row,index){
	if("3"==value){
		return "交易顾客";
	}else{
		return "待发展顾客";
	}
}
</script>