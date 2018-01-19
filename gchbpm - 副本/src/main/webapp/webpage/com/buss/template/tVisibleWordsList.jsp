<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tVisibleWordsList" checkbox="true" fitColumns="false" extendParams="" title="顾客话术" 
  actionUrl="visibleTemplateWordsController.do?datagridRetailerVisible" idField="id" fit="true" queryMode="group">
   	<t:dgCol title="话术ID"  field="id" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   	<t:dgCol title="ID"  field="wordsId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   	<t:dgCol title="一级分类"  field="topTypeId" dictionary="t_template_type,id,name,status='A' and level='1' and template_type='1'"  queryMode="single"  width="120"></t:dgCol>
	<t:dgCol title="二级分类"  field="subTypeId" dictionary="t_template_type,id,name,status='A' and level='2' and template_type='1'"  queryMode="single"  width="120"></t:dgCol>
	<t:dgCol title="三级分类"  field="thridTypeId" dictionary="t_template_type,id,name,status='A' and level='3' and template_type='1'"  queryMode="single"  width="120"></t:dgCol>
	<t:dgCol title="关联行业"  field="tradeName" queryMode="single" width="100"></t:dgCol>
	<t:dgCol title="话术类型"  field="type" replace="图片话术_2,文字话术_1" queryMode="single" query="true" width="100"></t:dgCol>
	<t:dgCol title="内容"  field="content" formatterjs="checkPicShow"  query="true"  queryMode="single"  width="500"></t:dgCol>
	<t:dgCol title="是否可见"  field="visible"  query="true" replace="是_1,否_0"  queryMode="single"  width="80"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgFunOpt funname="doShow(id,subTypeId)" title="设置可见" exp="visible#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="doHide(wordsId)" title="设置不可见" exp="visible#eq#1"></t:dgFunOpt>
	<t:dgToolBar title="查看" icon="icon-search"  funname="reviewbytab"></t:dgToolBar>
	<t:dgToolBar title="批量设置可见" icon="icon-add" url="visibleTemplateWordsController.do?doBatchAddCustWords" funname="doShowAllSelectCustWords"></t:dgToolBar>
	<t:dgToolBar title="批量设置不可见" icon="icon-remove" url="visibleTemplateWordsController.do?doBatchDel"  funname="doHideAllSelect"></t:dgToolBar>
  </t:datagrid>
  </div>
  
  <div id="tempSearchColums" style="display: none">
	    <div name="searchColums">
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="一级分类">
	                	一级分类：
	            </span>
	            <select id="topTypeId" name="topTypeId" onchange="getSubList(this.value)"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">---所有---</option>
						 <c:forEach var="obj" items="${topTypeList}" >
								<option value="${obj.id}">${obj.name}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="二级分类">
	                	二级分类：
	            </span>
	            <select id="subTypeId" name="subTypeId" onchange="getSubList(this.value)" WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
				</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="三级分类">
	                	三级分类：
	            </span>
	            <select id="thridTypeId" name="thridTypeId"  WIDTH="100" style="width: 104px;height: 26px;">
					<option value="">---所有---</option>
				</select>
	        </span>
	        <span style="display:-moz-inline-box;display:inline-block;">
	            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;width: 80px;" title="关联行业">
	                	关联行业：
	            </span>
	            <select id="templateTrade" name="templateTrade"  WIDTH="100" style="width: 104px;height: 26px;">
						 <option value="">---所有---</option>
						 <c:forEach var="obj" items="${templateTrade}" >
								<option value="${obj.id}">${obj.name}</option>					 	
			              </c:forEach>
					</select>
	        </span>
	    </div>
	</div>
 </div>
<script src = "webpage/com/buss/template/templateWords.js?v=1.13"></script>
 <script type="text/javascript">
 $(document).ready(function(){
	 $("#tVisibleWordsListForm span").eq(0).before($("#tempSearchColums div[name='searchColums']").html());
 });
 
 function reviewbytab() {
		var rowsData = $("#tVisibleWordsList").datagrid("getSelections");
		if(rowsData==''){
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		var id=rowsData[0].id;
		createdetailwindow("顾客话术","visibleTemplateWordsController.do?goUpdate&load=detail&id="+id,700,400);
	}
 //新增可见话术(wordsType 1:顾客，2：商品，3：活动)
 function doShow(wordsId,subTypeId){
	 var url = "visibleTemplateWordsController.do?doAdd";
	 $.ajax({
			type : 'POST',
			url : url,// 请求的action路径
			data : {
				wordsType : 1,
				wordsId : wordsId,
				subTypeId : subTypeId
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
 
//判断是否显示图片
 function checkPicShow(value,row,index){
 	 var type = row.type;//1:文字，2：图片
 	 if("2"==type){
 		 if(value!=""){
 			 return '<img width="70" height="70" border="0" src="'+value+'">';
 		 }else{
 			 return '';
 		 }
 	 }else{
 		 return value;
 	 }
 }
 
//批量设置可见
 function doShowAllSelectCustWords(title,url,gname) {
		 gridname = gname;
	 	var rows = $("#"+gridname).datagrid('getSelections');
		if(rows.length > 0){
			var wordsIds = [];
			for ( var i = 0; i < rows.length; i++) {
				if(rows[i].visible=='0'){//不可见的才设置可见
					wordsIds.push(rows[i].id+"_"+rows[i].subTypeId);
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
	 
 </script>