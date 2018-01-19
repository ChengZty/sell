<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

	<t:datagrid name="wordsGoodsList" checkbox="false" fitColumns="true" extendParams="nowrap:false,"  title="关联商品列表" sortName="sortNum" sortOrder="desc" actionUrl="tFinActivityWordsController.do?datagridWordsGoods&wordsId=${wordsId }" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="图片"  field="smallPic" image="true" imageSize="100,80"  queryMode="single" frozenColumn="true" ></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"  queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="商品款号"  field="goodsCode"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="商品状态"  field="goodsStatus"  replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true" queryMode="single"  width="90"></t:dgCol>
		<%-- <t:dgCol title="品牌"  field="brandName"   query="true" queryMode="single"  width="120"></t:dgCol>
		<t:dgCol title="类目"  field="thridCategoryId"  dictionary="t_s_category,id,name,level = '3' and status = 'A' " queryMode="single"  width="80"></t:dgCol>
		<t:dgCol title="商品状态"  field="goodsStatus"  replace="草稿箱中_0,待上架_3,销售中_4,已下架_5" query="true" queryMode="single"  width="90"></t:dgCol> --%>
		<t:dgCol title="操作" field="opt" width="70"></t:dgCol>
		<t:dgDelOpt title="删除" url="tFinActivityWordsController.do?doDelWordsGoods&id={id}&wordsId=${wordsId }" />
		<t:dgToolBar title="选择" icon="icon-add" url="templateWordsGoodsController.do?wordsAddGoodsList&wordsId=${wordsId }" funname="chooseWords" ></t:dgToolBar>
		<%-- <t:dgToolBar title="删除"  icon="icon-remove" url="tFinActivityWordsController.do?doBatchDel?wordsId=${wordsId }" funname="deleteALLSelect"></t:dgToolBar> --%>
		<t:dgToolBar title="查看" icon="icon-search" url="" onclick="goView()" funname="detail"></t:dgToolBar>
	</t:datagrid>
 
 <script type="text/javascript">
 gridname = "wordsGoodsList";
 function goView(){
	 var rowsData = $('#wordsGoodsList').datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData.length>1) {
			tip('只能选择一条记录');
			return;
		}
		window.open("tNewGoodsController.do?goView&id="+rowsData[0].id,"new");
 }
//选择话术商品
 function chooseWords(title,url) {
 		$.dialog.setting.zIndex = 100;
 		$.dialog({
			content: "url:"+url,
			lock : true,
			title:"选择商品话术",
			width:700,
			height: 500,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	var selected = iframe.getSelectRows();
		    	if (selected == '' || selected == null ){
		    		alertTip('请选择商品话术');
		    		return false;
			    }else {
			    	var goodsIds = "";
			    	$.each(selected,function (index,domEle){
			    		goodsIds += domEle.id + ",";
					});
					var url = "tFinActivityWordsController.do?doBatchAddWordsGoods";
				$.ajax({
					url : url,
					type : 'post',
					data : {
						goodsIds : goodsIds,
						wordsId : '${wordsId }'
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gridname).datagrid('unselectAll');
						}
					}
				});
			    }
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
 	}
 </script>