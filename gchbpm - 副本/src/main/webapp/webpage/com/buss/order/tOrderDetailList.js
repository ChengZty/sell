
/**
 * 查看选中的订单
 */
function goViewBySelect(title,url,gname){
	 var rowsData = $('#'+gname).datagrid('getSelections');
		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看记录');
			return;
		}
		if (rowsData.length > 1) {
			var isSame = true;//多条明细的单号相同
			var orderNo = rowsData[0].orderNo;
			$.each(rowsData, function (index, rowData){
				if(orderNo !=rowData.orderNo){
					isSame = false;
				}
			});
			if(!isSame){
				tip('请选择一条记录再查看');
				return;
			}
		}
	    url += '&id='+rowsData[0].orderId;
//	 var url = "tOrderInfoController.do?goView&id="+orderId;
	 window.open(url,'new') 
}

/**
 * 导出订单明细
 */
function ExportDetailsXls(title,url,gname) {
	JeecgExcelExport(url,gname);
}

/**
 * 导出订单主表
 */
function ExportInfoXls(title,url,gname) {
	JeecgExcelExport(url,gname);
}

