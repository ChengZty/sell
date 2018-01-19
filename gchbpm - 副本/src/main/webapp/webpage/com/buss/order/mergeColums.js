/**
 * 合并列
 * @param arr json数组
 * @param field 判断合并的字段（连续并且相同则合并）
 * @param mergeFields 合并的字段的数组
 * @param gridname grid表格name
 */
function mergeColums(arr,field,mergeFields,gridname){
	var initData = "";//初始化的对比数据
	var rowIndex = 0;//合并的下标
	var mergeRowspan = 1;//合并行数
	var merges = [];//需要合并的json数组
	for(var i=0; i<arr.length; i++){
		if(i>0&&initData==arr[i][field]){
			
			mergeRowspan++;
		}else{
			if(mergeRowspan>1){//有相同的需要合并的
				merges.push({index: rowIndex, rowspan: mergeRowspan});
			}
			rowIndex = i;
			initData = arr[i][field];//初始化数据
			mergeRowspan = 1;//初始化合并行数
		}
	}
	if(mergeRowspan>1){//最后一次可能没有添加上
		merges.push({index: rowIndex, rowspan: mergeRowspan});
	}
	for(var i=0; i<merges.length; i++){
		for(var n=0; n<mergeFields.length; n++){
			$('#'+gridname).datagrid('mergeCells',{
				index: merges[i].index,
				field: mergeFields[n],
				rowspan: merges[i].rowspan
			});
		}
	}
}

/**
 * 绑定多行明细的选中事件
 * orderNo 相同的用于对比的字段
 */
function dealMultiSelect(orderNo){
	//点击某一行，选中相同的行
	$('.datagrid-view .datagrid-body tr.datagrid-row').on('click', dealClickTr);   //绑定事件
	function dealClickTr(){
		var $allTr = $('.datagrid-view .datagrid-body tr.datagrid-row');  //找到所有的tr
		//将左右侧的tr分开
//		var leftTrArr = [];
//		var rightTrArr = [];
		var leftTrArr = $('.datagrid-view .datagrid-view1 .datagrid-body tr.datagrid-row');  //左边的tr
		var rightTrArr = $('.datagrid-view .datagrid-view2 .datagrid-body tr.datagrid-row');  //左边的tr
//		$allTr.each(function (index, domEle){
//			var $tempTr = $(domEle);
//			if($tempTr.find('div.datagrid-cell-check').length > 0){  //如果含有checkbox，则是左侧
//				leftTrArr.push($tempTr);
//			}else{
//				rightTrArr.push($tempTr);
//			}
//		});

		var $curTr = $(this);
		var clickOrderNo = '';  //点击  订单号
		var clickRowIndex = $curTr.attr('datagrid-row-index');  //点击  所在行
		
		//查找点击的订单号
		var $clickOrderNoDiv = $curTr.find('td[field=\''+orderNo+'\'] div');
		if($clickOrderNoDiv.length > 0){  //如果点击的正好是右侧的tr（含有订单号）
			clickOrderNo = $clickOrderNoDiv.text();
		}else{
			//循环右侧的tr
			$.each(rightTrArr, function (index, tempTr){
				var $orderNoDiv = $(tempTr).find('td[field=\''+orderNo+'\'] div');
				var rowIndex = $(tempTr).attr('datagrid-row-index');
				if(rowIndex === clickRowIndex){  //如果行号相等，则是同一行
					clickOrderNo = $orderNoDiv.text();
					$curTr = $(tempTr);  //将当前的点击对象，转移到有订单号的一侧
				}
			});
		}
		
		$allTr.off('click', dealClickTr);  //解绑点击事件
		
		//循环右侧的tr，找到相同的订单号，点击tr
		$.each(rightTrArr, function (index, tempTr){
			var $orderNoDiv = $(tempTr).find('td[field=\''+orderNo+'\'] div');
			var tempOrderNo = $orderNoDiv.text();

			if($curTr.attr('id') !== $(tempTr).attr('id')){  //去除已点击的tr
				if(tempOrderNo === clickOrderNo){
					//多选（checkbox）
					$(tempTr).click();
				}
			}
		});

		setTimeout(function (){
			$allTr.on('click', dealClickTr);  //重新添加点击事件
		}, 60);
	}
}