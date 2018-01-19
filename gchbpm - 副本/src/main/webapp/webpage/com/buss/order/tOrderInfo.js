$(function() {
	storage = $.localStorage;
	if (!storage)
		storage = $.cookieStorage;
	$('#tOrderInfoList')
			.datagrid(
					{
						idField : 'id',
						title : '订单明细',
						url : 'tOrderInfoController.do?datagrid&order_status=&field=id,orderNo,orderTime,quantityAmount,goodsAmount,fareAmount,orderAmount,userPhone,userName,payTime,payMethod,toGuideId,toRetailerId,orderStatus,',
						nowrap : false,
						fit : true,
						loadMsg : '数据加载中...',
						pageSize : 10,
						pagination : true,
						pageList : [ 10, 20, 30 ],
						sortName : 'createDate',
						sortOrder : 'desc',
						rownumbers : true,
						singleSelect : true,
						fitColumns : false,
						striped : true,
						showFooter : true,
						frozenColumns : [ [] ],
						columns : [ [
								{
									field : 'id',
									title : '主键',
									hidden : true,
									sortable : true
								},
								{
									field : 'orderNo',
									title : '订单号',
									width : 130,
									sortable : true
								},
								{
									field : 'orderTime',
									title : '下单时间',
									width : 130,
									sortable : true,
									formatter : function(value, rec, index) {
										return new Date().format(
												'yyyy-MM-dd hh:mm:ss', value);
									}
								},
								{
									field : 'quantityAmount',
									title : '数量',
									width : 60,
									sortable : true
								},
								{
									field : 'goodsAmount',
									title : '商品总价',
									width : 60,
									sortable : true
								},
								{
									field : 'fareAmount',
									title : '运费',
									width : 60,
									sortable : true
								},
								{
									field : 'orderAmount',
									title : '订单总价',
									width : 70,
									sortable : true
								},
								{
									field : 'userPhone',
									title : '买家手机',
									width : 100,
									sortable : true
								},
								{
									field : 'userName',
									title : '买家姓名',
									width : 100,
									sortable : true
								},
								{
									field : 'payTime',
									title : '支付时间',
									width : 130,
									sortable : true,
									formatter : function(value, rec, index) {
										return new Date().format(
												'yyyy-MM-dd hh:mm:ss', value);
									}
								},
								{
									field : 'payMethod',
									title : '支付方式',
									width : 60,
									sortable : true
								},
								{
									field : 'toGuideId',
									title : '导购',
									width : 60,
									sortable : true
								},
								{
									field : 'toRetailerId',
									title : '零售商',
									width : 100,
									sortable : true
								},
								{
									field : 'orderStatus',
									title : '订单状态',
									width : 60,
									sortable : true,
									formatter : function(value, rec, index) {
										if (value == undefined)
											return '';
										var valArray = value.split(',');
										if (valArray.length > 1) {
											var checkboxValue = '';
											for (var k = 0; k < valArray.length; k++) {
												if (valArray[k] == '0') {
													checkboxValue = checkboxValue
															+ '待给优惠' + ',';
												}
												if (valArray[k] == '1') {
													checkboxValue = checkboxValue
															+ '待付款' + ',';
												}
												if (valArray[k] == '2') {
													checkboxValue = checkboxValue
															+ '待发货' + ',';
												}
												if (valArray[k] == '3') {
													checkboxValue = checkboxValue
															+ '已发货' + ',';
												}
												if (valArray[k] == '4') {
													checkboxValue = checkboxValue
															+ '已完成' + ',';
												}
												if (valArray[k] == '8') {
													checkboxValue = checkboxValue
													+ '已退款' + ',';
												}
												if (valArray[k] == '9') {
													checkboxValue = checkboxValue
															+ '已取消' + ',';
												}
											}
											return checkboxValue.substring(0,
													checkboxValue.length - 1);
										} else {
											if (value == '0') {
												return '待给优惠';
											}
											if (value == '1') {
												return '待付款';
											}
											if (value == '2') {
												return '待发货';
											}
											if (value == '3') {
												return '已发货';
											}
											if (value == '4') {
												return '已完成';
											}
											if (value == '9') {
												return '已取消';
											}
											if (value == '9') {
												return '已关闭';
											} else {
												return value;
											}
										}
									}
								} ] ],
//						onLoadSuccess : function(data) {
//							$("#tOrderInfoList").datagrid("clearSelections");
//							$("#tOrderInfoList").datagrid("fixRownumber");
//						},
						onLoadSuccess : mergeColumsFun,
						view: detailview,//明细折叠视图
						 detailFormatter:function(rowIndex, rowData){    
						        return '<div id="ddv-' + rowIndex + '" style="padding:0px 3px;min-height:20px;"></div>';    
						 },
						 onExpandRow: function(index,row){
							 	var content = $('#ddv-'+index).html();
							 	if(content==""){
							 		var html = getOrderDetails(row.id);
							 		$('#ddv-'+index).html(html);
							 	}
						    	/*$('#ddv-'+index).panel({    
						            border:false,    
						            cache:false,    
						            href:'tChatWordsController.do?goAdd&itemid='+row.itemid,    
						            onload:function(){    
//						                $('#tOrderInfoList').datagrid('fixdetailrowheight',index);  
										$('#tOrderInfoList').datagrid('fixDetailRowHeight',index);//在加载爷爷列表明细（即：父列表）成功时，获取此时整个列表的高度，使其适应变化后的高度，此时的索引  
//							 			$('#tOrderInfoList').datagrid('fixRowHeight',index);//防止出现滑动条  
						            }    
						        }); */
						        $('#tOrderInfoList').datagrid('fixDetailRowHeight',index);//在加载爷爷列表明细（即：父列表）成功时，获取此时整个列表的高度，使其适应变化后的高度，此时的索引  
							 	$('#tOrderInfoList').datagrid('fixRowHeight',index);//防止出现滑动条  
					        },
						onClickRow : function(rowIndex, rowData) {
							rowid = rowData.id;
							gridname = 'tOrderInfoList';
						}
					});
	
	$('#tOrderInfoList').datagrid('getPager').pagination({
		beforePageText : '',
		afterPageText : '/{pages}',
		displayMsg : '{from}-{to}共 {total}条',
		showPageList : true,
		showRefresh : true
	});
	$('#tOrderInfoList').datagrid('getPager').pagination({
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
			$(this).pagination('loaded');
		}
	});
	try {
		restoreheader();
	} catch (ex) {
	}
});

/**
 * 合并列
 * @param data
 */
function mergeColumsFun(data){
	$("#tOrderInfoList").datagrid("clearSelections");
	$("#tOrderInfoList").datagrid("fixRownumber");
	/*var merges = [{
		index: 2,
		rowspan: 2
	},{
		index: 5,
		rowspan: 2
	},{
		index: 7,
		rowspan: 2
	}];
	for(var i=0; i<merges.length; i++){
		$(this).datagrid('mergeCells',{
			index: merges[i].index,
			field: 'productid',
			rowspan: merges[i].rowspan
		});
	}*/
	
	/*$('#tOrderInfoList').datagrid('mergeCells',{
		index: 2,
		field: 'orderNo',
		rowspan: 3
	});*/
}

/**
 * 获取订单明细
 * @param orderId 主订单id
 * @returns table结构的订单明细数据
 */
function getOrderDetails(orderId){
	var html = '';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : "tOrderInfoController.do?getOrderDetails&orderId="+orderId,// 请求的action路径
		error : function() {// 请求失败处理函数
		
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if(d.success){
				var arr = d.obj;
				if(arr.length>0){
					html = '\
						<table style="width:100%;" cellpadding="0" cellspacing="0">\
							<thead><tr>\
									<td width="30%">商品名称</td>\
									<td>款号</td>\
									<td>规格</td>\
									<td>原价</td>\
									<td>数量</td>\
									<td>商品金额</td>\
									<td>操作</td>\
								</tr>\
							</thead>\
							<tbody>\
						\
						'
						;
					for(var i in arr) {
						html+='\
							<tr>\
								<td>'+arr[i].goodsName+'</td>\
								<td>'+arr[i].goodsCode+'</td>\
								<td>'+arr[i].specInfo+'</td>\
								<td>'+arr[i].priceOriginal+'</td>\
								<td>'+arr[i].quantity+'</td>\
								<td>'+arr[i].goodsAmount+'</td>\
								<td><a href="#" onclick=goToDetail(\''+arr[i].id+'\')>[查看订单]</a></td>\
							</tr>\
								'
							;
//						console.log(arr[i]);
					}
					html+='</tbody></table>';
					return html;
				}
			}else{
				html+='<div>'+d.msg+'</div>';
			}
		}
	});
	return html;
}
/**
 * 查看订单明细
 * @param id
 */
function goToDetail(id){
	var url = "tOrderDetailController.do?goView&id="+id;
	 window.open(url,'new') 
}


function reloadTable() {
	try {
		$('#' + gridname).datagrid('reload');
	} catch (ex) {
	}
}
function reloadtOrderInfoList() {
	$('#tOrderInfoList').datagrid('reload');
}
function gettOrderInfoListSelected(field) {
	return getSelected(field);
}
function getSelected(field) {
	var row = $('#' + gridname).datagrid('getSelected');
	if (row != null) {
		value = row[field];
	} else {
		value = '';
	}
	return value;
}
function gettOrderInfoListSelections(field) {
	var ids = [];
	var rows = $('#tOrderInfoList').datagrid('getSelections');
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i][field]);
	}
	ids.join(',');
	return ids
};
function getSelectRows() {
	return $('#tOrderInfoList').datagrid('getChecked');
}
function saveHeader() {
	var columnsFields = null;
	var easyextends = false;
	try {
		columnsFields = $('#tOrderInfoList').datagrid('getColumns');
		easyextends = true;
	} catch (e) {
		columnsFields = $('#tOrderInfoList').datagrid('getColumnFields');
	}
	var cols = storage.get('tOrderInfoListhiddenColumns');
	var init = true;
	if (cols) {
		init = false;
	}
	var hiddencolumns = [];
	for (var i = 0; i < columnsFields.length; i++) {
		if (easyextends) {
			hiddencolumns.push({
				field : columnsFields[i].field,
				hidden : columnsFields[i].hidden
			});
		} else {
			var columsDetail = $('#tOrderInfoList').datagrid("getColumnOption",
					columnsFields[i]);
			if (init) {
				hiddencolumns.push({
					field : columsDetail.field,
					hidden : columsDetail.hidden,
					visible : (columsDetail.hidden == true ? false : true)
				});
			} else {
				for (var j = 0; j < cols.length; j++) {
					if (cols[j].field == columsDetail.field) {
						hiddencolumns.push({
							field : columsDetail.field,
							hidden : columsDetail.hidden,
							visible : cols[j].visible
						});
					}
				}
			}
		}
	}
	storage.set('tOrderInfoListhiddenColumns', JSON.stringify(hiddencolumns));
}
function restoreheader() {
	var cols = storage.get('tOrderInfoListhiddenColumns');
	if (!cols)
		return;
	for (var i = 0; i < cols.length; i++) {
		try {
			if (cols.visible != false)
				$('#tOrderInfoList').datagrid(
						(cols[i].hidden == true ? 'hideColumn' : 'showColumn'),
						cols[i].field);
		} catch (e) {
		}
	}
}
function resetheader() {
	var cols = storage.get('tOrderInfoListhiddenColumns');
	if (!cols)
		return;
	for (var i = 0; i < cols.length; i++) {
		try {
			$('#tOrderInfoList').datagrid(
					(cols.visible == false ? 'hideColumn' : 'showColumn'),
					cols[i].field);
		} catch (e) {
		}
	}
}
function tOrderInfoListsearch() {
	var queryParams = $('#tOrderInfoList').datagrid('options').queryParams;
	$('#tOrderInfoListtb').find('*').each(function() {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	$('#tOrderInfoList')
			.datagrid(
					{
						url : 'tOrderInfoController.do?datagrid&order_status=&field=id,orderNo,orderTime,orderTime_begin,orderTime_end,quantityAmount,goodsAmount,fareAmount,orderAmount,userPhone,userName,payTime,payTime_begin,payTime_end,payMethod,toGuideId,toRetailerId,orderStatus,',
						pageNumber : 1
					});
}
function dosearch(params) {
	var jsonparams = $.parseJSON(params);
	$('#tOrderInfoList')
			.datagrid(
					{
						url : 'tOrderInfoController.do?datagrid&order_status=&field=id,orderNo,orderTime,orderTime_begin,orderTime_end,quantityAmount,goodsAmount,fareAmount,orderAmount,userPhone,userName,payTime,payTime_begin,payTime_end,payMethod,toGuideId,toRetailerId,orderStatus,',
						queryParams : jsonparams
					});
}
function tOrderInfoListsearchbox(value, name) {
	var queryParams = $('#tOrderInfoList').datagrid('options').queryParams;
	queryParams[name] = value;
	queryParams.searchfield = name;
	$('#tOrderInfoList').datagrid('reload');
}
function EnterPress(e) {
	var e = e || window.event;
	if (e.keyCode == 13) {
		tOrderInfoListsearch();
	}
}
function searchReset(name) {
	$("#" + name + "tb").find(":input").val("");
	var queryParams = $('#tOrderInfoList').datagrid('options').queryParams;
	$('#tOrderInfoListtb').find('*').each(function() {
		queryParams[$(this).attr('name')] = $(this).val();
	});
	$('#tOrderInfoList')
			.datagrid(
					{
						url : 'tOrderInfoController.do?datagrid&order_status=&field=id,orderNo,orderTime,orderTime_begin,orderTime_end,quantityAmount,goodsAmount,fareAmount,orderAmount,userPhone,userName,payTime,payTime_begin,payTime_end,payMethod,toGuideId,toRetailerId,orderStatus,',
						pageNumber : 1
					});
}
