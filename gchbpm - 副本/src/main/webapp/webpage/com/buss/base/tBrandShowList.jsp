<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="tBrandShowList" checkbox="true" fitColumns="false" extendParams="nowrap:false," title="零售商品牌列表" actionUrl="tBrandShowController.do?datagrid&retailer_id=${retailer_id }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="零售商ID"  field="retailerId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌ID"  field="brandId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌图片"  field="brandPic" image="true" imageSize="80,80"   queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="品牌名称"  field="brandName"   query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="品牌编码"  field="brandCode"    queryMode="single"  width="00"></t:dgCol>
   <t:dgCol title="排序"  field="orderNum"    queryMode="single"  width="40"></t:dgCol>
   <t:dgCol title="满邮金额"  field="freeAmount"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="基础运费"  field="fare"    queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="是签约品牌"  field="isSelfBrand" replace="是_1,否_0" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgConfOpt title="设为签约品牌" url="tBrandShowController.do?doSelfBrand&type=1&id={id}" message="确认设为签约品牌？" exp="isSelfBrand#eq#0"/>
   <t:dgConfOpt title="取消签约品牌" url="tBrandShowController.do?doSelfBrand&type=0&id={id}" message="确认取消签约品牌？" exp="isSelfBrand#eq#1"/>
   <t:dgDelOpt title="删除" url="tBrandShowController.do?doDel&id={id}" />
   <t:dgToolBar title="添加品牌" icon="icon-add" url="tBrandShowController.do?doAdd&retailerId=${retailer_id }" funname="addBrands"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tBrandShowController.do?goUpdate" funname="update"></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBrandShowController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
 <script type="text/javascript">
//选择品牌
 function addBrands(title,url, id){
 		$.dialog({
 			width:600,
 			height:530,
 	        id: 'tBrandShowList',
 	        title: "添加品牌",
 	        max: false,
 	        min: false,
 	        resize: false,
 	        content: 'url:tExcludeRuleController.do?findBrands&retailer_Id=${retailer_id }',
 	        lock:true,
 	        ok: function(){
 		    	iframe = this.iframe.contentWindow;
 		    	var brandIds = iframe.getBrandIds();
 		    	if(brandIds==""){
 		    		return false;
 		    	}else{
 					url += '&brandIds='+brandIds;
 					doAjax(url);
 		    	}
 		    },
 	        close: function(){
 	        }
 	    });
 }
 
 function doAjax(url) {
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					tip(d.msg);
					$('#tBrandShowList').datagrid('reload',{});
				}		
			}
		});
	}
 

 </script>