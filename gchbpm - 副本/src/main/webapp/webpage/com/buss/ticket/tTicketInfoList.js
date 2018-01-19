//获取使用条件
function showCondition(value,row,index){
	var type = row.type;
	var useType = row.useType;
	if(type==1){//红包券
		return "无条件使用";
	}else{//优惠券，折扣券
		if(useType==1){
			return "满"+value+"元使用";
		}else if(useType==2){
			return "满"+value+"件使用";
		}
	}
} 

//获取使用条件
function showLimitSheet(value,row,index){
	if(value==0){
		return "无张数限制";
	}else{
		return "最多使用"+value+"张";
	}
} 	


//批量审核 
function doBatchAudit(title,url,gname){
	 var rows = $('#'+gname).datagrid('getSelections');
		if (!rows || rows.length==0) {
			tip('请选择一条记录');
			return;
		}
		var batchNos = [];
		for ( var i = 0; i < rows.length; i++) {
			batchNos.push(rows[i].batchNo);
		}
		 $.messager.confirm('提示信息', "确认审核？", function(r){
			  if (r) {
					$.ajax({
						url : url,
						type : 'post',
						data : {
							batchNos : batchNos.join(',')
						},
						cache : false,
						success : function(data) {
							var d = $.parseJSON(data);
							if (d.success) {
								var msg = d.msg;
								tip(msg);
								reloadTable();
								$("#"+gname).datagrid('unselectAll');
							}
						}
					});
				}
		  })
}

//批量删除
function doBatchDel(title,url,gname){
	var rows = $('#'+gname).datagrid('getSelections');
	if (!rows || rows.length==0) {
		tip('请选择一条记录');
		return;
	}
	var batchNos = [];
	for ( var i = 0; i < rows.length; i++) {
		batchNos.push(rows[i].batchNo);
	}
	$.messager.confirm('提示信息', "确认删除？", function(r){
		if (r) {
			$.ajax({
				url : url,
				type : 'post',
				data : {
					batchNos : batchNos.join(',')
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						reloadTable();
						$("#"+gname).datagrid('unselectAll');
					}
				}
			});
		}
	})
}

function doStop(id){
	$.messager.confirm('提示信息', "确认销毁？", function(r){
		if (r) {
			$.ajax({
				url : 'tTicketInfoController.do?doStop&id='+id,
				type : 'post',
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						reloadTable();
//						$("#"+gname).datagrid('unselectAll');
					}
				}
			});
		}
	})
}

//批量销毁 
function doBatchStop(title,url,gname){
	var rows = $('#'+gname).datagrid('getSelections');
	if (!rows || rows.length==0) {
		tip('请选择一条记录');
		return;
	}
	var batchNos = [];
	for ( var i = 0; i < rows.length; i++) {
		batchNos.push(rows[i].batchNo);
	}
	$.messager.confirm('提示信息', "确认销毁？", function(r){
		if (r) {
			$.ajax({
				url : url,
				type : 'post',
				data : {
					batchNos : batchNos.join(',')
				},
				cache : false,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						tip(msg);
						reloadTable();
						$("#"+gname).datagrid('unselectAll');
					}
				}
			});
		}
	})
}

/**
 * 查看选中的优惠券，通过batchNo查看
 */
function goViewBySelect(title,url,gname){
	 var rowsData = $('#'+gname).datagrid('getSelections');
		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看记录');
			return;
		}
		var batchNo = rowsData[0].batchNo;
		if (rowsData.length > 1) {
			var isSame = true;//多条明细的单号相同
			$.each(rowsData, function (index, rowData){
				if(batchNo !=rowData.batchNo){
					isSame = false;
				}
			});
			if(!isSame){
				tip('请选择一条记录再查看');
				return;
			}
		}
	    url += '&batchNo='+batchNo;
	    console.log(url);
	 window.open(url,'new') 
}