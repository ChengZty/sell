
//批量设置可见
 function doShowAllSelect(title,url,gname) {
		 gridname = gname;
	 	var rows = $("#"+gridname).datagrid('getSelections');
		if(rows.length > 0){
			var wordsIds = [];
			for ( var i = 0; i < rows.length; i++) {
				if(rows[i].visible=='0'){
					wordsIds.push(rows[i].id);
				}
			}
			if(wordsIds.length>0){
				$.ajax({
					url : url,
					type : 'post',
					data : {
						wordsIds : wordsIds.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gridname).datagrid('unselectAll');
							wordsIds='';
						}
					}
				});
			}
		}else{
			tip("请选择需要设置可见的数据");
		}
	}
 
//批量设置不可见
 function doHideAllSelect(title,url,gname) {
	 	gridname = gname;
	 	var rows = $("#"+gname).datagrid('getSelections');
		if(rows.length > 0){
			var ids = [];
			for ( var i = 0; i < rows.length; i++) {
				if(rows[i].visible=='1'){
					ids.push(rows[i].wordsId);
				}
			}
			if(ids.length>0){
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}
					}
				});
			}
		}else{
			tip("请选择需要设置可见的数据");
		}
	}
 

 
//删除可见话术
 function doHide(id){
	 var url = "tVisibleWordsController.do?doDel";
	 $.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,// 请求的action路径
			data : {
				id : id
			},
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					reloadTable();
				}
			}
		});
 }