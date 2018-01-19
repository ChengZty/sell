 function addbytab() {
		document.location="baseActivityController.do?goAdd";
}
 
 function updatebytab() {
	 var rowsData = $("#baseActivityList").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var id=rowsData[0].id;
	document.location ="baseActivityController.do?goUpdate&id="+id;
}
  

 function goView() {
	 var rowsData = $("#baseActivityList").datagrid("getSelections");
	if(rowsData==''){
		tip('请选择一行记录');
		return;
	}
	if (rowsData.length>1) {
		tip('只能选择一条记录');
		return;
	}
	var id=rowsData[0].id;
	window.open("baseActivityController.do?goView&id="+id,"new");
}
 
