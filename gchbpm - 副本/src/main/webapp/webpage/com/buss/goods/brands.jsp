<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>品牌列表</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid   name="brandList" title="品牌列表"  actionUrl="baseBrandController.do?datagrid&&retailer_Id=${retailer_Id }" idField="id" checkbox="true" showRefresh="false"  fit="true" onClick="getID"  queryMode="group" onLoadSuccess="initCheck">
	<t:dgCol title="品牌ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="品牌编码" field="brandCode" width="100" query="true" ></t:dgCol>
	<t:dgCol title="品牌名称" field="brandName" width="120" query="true" ></t:dgCol>
</t:datagrid>
<input id="checkedIds" type="hidden" value="${ids}"/>
<input id="checkedNames" type="hidden" value="${brandNames}"/>
</body>
</html>
<script type="text/javascript">
function initCheck(data){
	var ids = "${ids}";
	var idArr = ids.split(",");
	for(var i=0;i<idArr.length;i++){
		if(idArr[i]!=""){
			$("#brandList").datagrid("selectRecord",idArr[i]);
		}
	}
}

function getBrandIds(){
		var rowsData = $("#brandList").datagrid("getChecked");
		var ids = "";
		if(rowsData.length==0){
			tip('请选择品牌');
			return "";
		}else{
			var k = rowsData.length;
			for(i=0;i<k;i++){
				ids += rowsData[i].id +",";			
			}
		}
		return ids;
	}
	
function getID(rowIndex,rowData){
	var ids = $("#checkedIds").val();
	var brandNames = $("#checkedNames").val();
	if(ids!=""&&ids.indexOf(",")!=0){
		ids = "," + ids ;
		brandNames = "," + brandNames ;
	}
	if($("#datagrid-row-r1-2-"+rowIndex).hasClass("datagrid-row-selected")){
		ids = ids + "," + rowData.id;
		brandNames = brandNames + "," + rowData.brandName;
		$("#checkedIds").val(ids);
		$("#checkedNames").val(brandNames);
	}else{
		ids = ids.replace(","+rowData.id,"");
		brandNames = brandNames.replace(","+rowData.brandName,"");
		$("#checkedIds").val(ids);
		$("#checkedNames").val(brandNames);
	}
	}
</script>