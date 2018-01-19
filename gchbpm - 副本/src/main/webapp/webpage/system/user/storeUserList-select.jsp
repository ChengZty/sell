<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="userList" title="选择用户" actionUrl="tPersonController.do?datagridGuide&noticeId=${noticeId}" fitColumns="false" idField="id" checkbox="true" queryMode="group" sortName="createDate" sortOrder="desc">
			<t:dgCol title="ID" field="id" hidden="true"></t:dgCol>
<%-- 			<t:dgCol title="头像"  field="photo"  image="true" imageSize="70,70" width="70"></t:dgCol> --%>
			<t:dgCol title="姓名" field="realName" query="true" width="90"></t:dgCol>
			<t:dgCol title="手机号码"  field="phoneNo"   query="true" queryMode="single"  width="120"></t:dgCol>
<%-- 			<t:dgCol title="所属区域"  field="area"   queryMode="single"  width="120"></t:dgCol> --%>
			<t:dgCol title="所属店铺"  field="storeId" dictionary="t_store,id,name, status='A' and retailer_id = '${retailerId}'" query="true"  queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="用户ID" field="userId" hidden="true"></t:dgCol>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
  	function getUserIds(){
  		var rowsData = $("#userList").datagrid("getChecked");
  		var ids = "";
  		if(rowsData.length==0){
  			tip('请选择用户');
  			return "";
  		}else{
  			var k = rowsData.length;
  			for(i=0;i<k;i++){
  				ids += rowsData[i].userId +",";			
  			}
  		}
  		return ids;
  	}
  </script>
