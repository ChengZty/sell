<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tFocusCustomerList" checkbox="true" fitColumns="true" title="待发展顾客列表" sortName="createDate" sortOrder="desc"
			actionUrl="tFocusCustomerController.do?datagridOfG&custType=other" idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="添加时间"  field="createDate"  query="true"  queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150"></t:dgCol>
			<t:dgCol title="姓名" field="name" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="性别" field="sex" query="true" queryMode="single" dictionary="sex" width="50"></t:dgCol>
			<t:dgCol title="手机号" field="phoneNo" query="true" queryMode="single" width="120"></t:dgCol>
			<%--    <t:dgCol title="生日"  field="birthday"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol> --%>
			<t:dgCol title="添加的导购ID" field="addGuideId" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="添加的导购" field="addGuideName" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="添加的零售商" field="addRetailerId" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="所属零售商" field="toRetailerId" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="分配导购" field="toGuideId" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="分配的导购" field="toGuideName" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="生日" field="birthday" formatter="yyyy-MM-dd" queryMode="single" width="130"></t:dgCol>
			<t:dgCol title="年龄段" field="birthdayRank" query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="星座" field="constellation" query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="生肖" field="zodiac" query="true" queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="登记地区" field="registerArea" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="登记店铺" field="phoneRegShop" dictionary="t_store,id,name, status='A' and retailer_id = '${retailer_Id}'" query="true" queryMode="single" width="120"></t:dgCol>
<%-- 			<t:dgCol title="手机识别地区" field="phoneArea" query="true" queryMode="single" width="120"></t:dgCol> --%>
			<t:dgCol title="推送次数" field="pushCount" query="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="点击次数" field="clickNumber" query="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="购买次数" field="buyCount" query="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="类型" field="type" query="true" replace="无效号码_0,无反应顾客_1,点击顾客_2,交易顾客_3" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="是否退订" field="unOrder" query="true" replace="是_1,否_0" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="备注" field="remark" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="120"></t:dgCol>
			<t:dgDelOpt title="删除" url="tFocusCustomerController.do?doDel&id={id}" />
			<%--    <t:dgFunOpt funname="goView(id)" title="查看"></t:dgFunOpt> --%>
			<t:dgFunOpt title="取消分配" exp="toGuideId#empty#false" funname="doCancelGive(id)" />
			<t:dgToolBar title="录入" icon="icon-add" url="tFocusCustomerController.do?goAdd" funname="add" width="700"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tFocusCustomerController.do?goUpdate" funname="update" width="700"></t:dgToolBar>
			<%--    <t:dgToolBar title="编辑" icon="icon-edit" onclick="updatebytab()"  funname="update"></t:dgToolBar> --%>
			<t:dgToolBar title="批量分配" icon="icon-edit" url="" onclick="findGuide('tFocusCustomerList')" funname="findGuide"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove" url="tFocusCustomerController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="tFocusCustomerController.do?goView" funname="detail" height="550"></t:dgToolBar>
			<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
			<t:dgToolBar title="顾客分析" icon="icon-search" onclick="reviewByTab()" funname="review"></t:dgToolBar>
			<t:dgToolBar title="删除全部无效顾客" icon="icon-remove" url="tFocusCustomerController.do?doBatchDelDeadCust" funname="deleteALLDeadCust"></t:dgToolBar>
			<t:dgToolBar title="删除全部非交易顾客" icon="icon-remove" url="tFocusCustomerController.do?doBatchDelNotTradedCust" funname="deleteALLNotTradedCust"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<div id='winPage' class='easyui-window' title=' '
	style='width: 320px; height: 220px;' closed='true' modal='true'
	collapsible='false' minimizable='false' maximizable='false'></div>

 <link rel="stylesheet" href="plug-in/html5uploader/html5uploader.css">
 <script src="plug-in/html5uploader/jquery.html5uploader.js"></script>	
<script type="text/javascript">
	$(document).ready(function() {
 		var html = "<span><span style='width:80px;text-align: right;display: inline-block;'>添加人：</span><select name='addType'><option value=''>---所有---</option><option value='1'>零售商添加</option><option value='2'>导购添加</option></select></span>";
 		html += "<span><span style='width:90px;text-align: right;display: inline-block;'>是否分配导购：</span><select name='haveGuide'><option value=''>---所有---</option><option value='1'>是</option><option value='2'>否</option></select></span>";
		html += "<span style='display:-moz-inline-box;display:inline-block;'><span style='width:80px;text-align: right;display: inline-block;'>所属导购：</span><input onkeypress='EnterPress(event)' onkeydown='EnterPress()' type='text' name='guideName' class='inuptxt' style='width: 100px'></span>";
		$("#tFocusCustomerListForm").append(html);
		$("#tFocusCustomerListtb").find("input[name^='createDate']").attr("class","Wdate").attr("style","height:25px;width:130px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	});

	function addbytab() {
		document.location = "tFocusCustomerController.do?goAdd";
	}

	function updatebytab() {
		var rowsData = $("#tFocusCustomerList").datagrid("getSelections");
		if (rowsData == '') {
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length > 1) {
			tip('只能选择一条记录');
			return;
		}
		var id = rowsData[0].id;
		document.location = "tFocusCustomerController.do?goUpdate&id=" + id;
	}

	//导入
// 	function ImportXls() {
// 		openuploadwin('Excel导入(单次不超过100000条)', 'tFocusCustomerController.do?upload&type=1',
// 				"tFocusCustomerList");
// 	}
	
	function ImportXls() {
	  	openuploadwinH5('待发展顾客导入(单次不超过10000条)', 'tFocusCustomerController.do?importExcel&type=1', "tFocusCustomerList", ExportXlsxByFailList);
	 }
	
	//导入的错误记录下载
	function ExportXlsxByFailList(data) {
		var key = data.obj;
		if(key!=null){
			//下载错误的记录
			window.location.href=key;//直接从七牛下载
		}
// 		JeecgExcelExport("tFocusCustomerController.do?exportXlsxByFailList&key="+key,"tFocusCustomerList");
	}

	//导出
	function ExportXls() {
		var total = $('#tFocusCustomerList').datagrid('getData').total;
		var pageNum = 5000;
		var url = "tFocusCustomerController.do?exportXls&exportType=g";
		var datagridId = "tFocusCustomerList";
		if (total - pageNum < 0) {
			JeecgExcelExportWithPars(url, datagridId, 1, total);
			return;
		} else {
			showWinPage(total, url, datagridId, pageNum);
		}
	}

	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tFocusCustomerController.do?exportXlsByT",
				"tFocusCustomerList");
	}

	//查询导购
	function findGuide(gname) {
		gridname = gname;
		var rows = $('#tFocusCustomerList').datagrid('getSelections');
		if (!rows || rows.length == 0) {
			tip('请选择一条记录');
			return;
		}
		var ids = [];
		var phoneNos = [];
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
			phoneNos.push(rows[i].phoneNo);
		}
/* 		var flag = false;
		flag = checkPhoneNos(phoneNos);
		if (flag) { */
			$.dialog.setting.zIndex = 300;
			if (typeof (windowapi) == 'undefined') {
				$.dialog({
					content : "url:userController.do?findGuideList",
					lock : true,
					title : "选择导购",
					width : 700,
					height : 550,
					cache : false,
					ok : function() {
						//					    	iframe = this.iframe.contentWindow;
						//					    	var selected = iframe.getSelectRows();
						iframe_doc = this.iframe.contentWindow.document;
						var sel_tr = $(iframe_doc)
								.find(
										".datagrid-view2 tr.datagrid-row-selected");
						if ($(sel_tr).length == 0) {
							alert("请选择导购");
							return false;
						} else {
							//批量分配导购
							var guideId = $(sel_tr).find( "td[field='id'] div").text();
							var url = "tFocusCustomerController.do?doBatchGive&guideId=" + guideId;
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
										$("#"+ gridname).datagrid('unselectAll');
										ids = '';
									}
								}
							});
						}
					},
					cancelVal : '关闭',
					cancel : true
				/*为true等价于function(){}*/
				}).zindex();
			} else {
				$.dialog({
					content : "url:" + url,
					lock : true,
					title : "选择导购",
					width : 700,
					height : 550,
					parent : windowapi,
					cache : false,
					ok : function() {
						iframe_doc = this.iframe.contentWindow.document;
						var sel_tr = $(iframe_doc).find(".datagrid-view2 tr.datagrid-row-selected");
						if ($(sel_tr).length == 0) {
							alert("请选择导购");
							return false;
						} else {
							//批量分配导购
							var guideId = $(sel_tr).find("td[field='id'] div").text();
							var url = "tFocusCustomerController.do?doBatchGive&guideId=" + guideId;
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
										$("#"+ gridname).datagrid('unselectAll');
										ids = '';
									}
								}
							});
						}
					},
					cancelVal : '关闭',
					cancel : true
				}).zindex();
			}
		/* } */
	}

	//校验号码是否为公有（导购没有录过）
	function checkPhoneNos(phoneNos) {
		var flag = false;
		var url = "tFocusCustomerController.do?checkPhoneNos";
		$.ajax({
			url : url,
			type : 'post',
			async : false,
			data : {
				phoneNos : phoneNos.join(',')
			},
			cache : false,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					flag = true;
				} else {
					tip(d.msg);
					flag = false;
				}
			}
		});
		return flag;
	}
	//取消分配
	function doCancelGive(id) {
		var url = "tFocusCustomerController.do?doCancelGive&id=" + id;
		var name = "tFocusCustomerList";
		gridname = name;
		createdialog('取消分配确认 ', '确定取消分配吗 ?', url, name);
	}

	function goView(title, addurl) {
		createwindow(title, addurl, 600, 600);
	}

	/**
	 * 查看顾客资料分析
	 */
	function reviewByTab() {
		document.location = "tFocusCustomerController.do?goReview&page=listOfRetailer";
	}

	//删除全部的无效顾客
	function deleteALLDeadCust(title, url, gname) {
		gridname = gname;
		$.dialog.confirm('你确定删除全部无效顾客吗?', function(r) {
			if (r) {
				$.ajax({
					url : url,
					type : 'post',
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							// 							$("#"+gname).datagrid('unselectAll');
						}
					}
				});
			}
		});
	}
	
	//删除全部的非交易顾客
	function deleteALLNotTradedCust(title, url, gname) {
		gridname = gname;
		$.dialog.confirm('你确定删除全部非交易顾客吗?', function(r) {
			if (r) {
				$.ajax({
					url : url,
					type : 'post',
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
						}
					}
				});
			}
		});
	}
</script>